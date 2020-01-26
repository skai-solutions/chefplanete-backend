package net.skai.chefplaneteapi.service;

import me.xdrop.fuzzywuzzy.Applicable;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.algorithms.TokenSort;
import me.xdrop.fuzzywuzzy.algorithms.WeightedRatio;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;
import net.skai.chefplaneteapi.domain.FoodItem;
import net.skai.chefplaneteapi.domain.scanning.IdentifiedIngredient;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReceiptScanningService {

    // These are not ignored in the intiial TokenSort fuzzy search, only in the second one
    private final static List<String> IGNORED_WORDS = Arrays.asList(
            "LARGE",
            "SMALL",
            "MEDIUM",
            "SUBTOTAL",
            "TOTAL",
            "TRANSACTION"
    );

    private final CloudVisionTemplate cloudVisionTemplate;

    private final FoodItemService foodItemService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ReceiptScanningService(@NotNull final CloudVisionTemplate cloudVisionTemplate,
                                  @NotNull final FoodItemService foodItemService) {
        this.cloudVisionTemplate = cloudVisionTemplate;
        this.foodItemService = foodItemService;
    }

    /**
     * TODO:
     * This function needs to split the single string received from extractTextFromimage() into multiple strings
     * based on new lines ("\n"). It then needs to read each line and split it into words. Then, using the foodItemService
     * it will compare the each line against the food database to find matches, implementation is up to you.
     */
    @NotNull
    public List<IdentifiedIngredient> identifyIngredientsFromImage(@NotNull final String base64EncodedImage){
        final byte[] decodedImage = Base64.getDecoder().decode(base64EncodedImage);
        final String textInReceipt = cloudVisionTemplate.extractTextFromImage(new ByteArrayResource(decodedImage));
        final List<String> potentialReceiptItems = organizeText(textInReceipt);
        final Collection<Character> letterRange = new HashSet<>();
        potentialReceiptItems.forEach(item -> {
             List<String> words = Arrays.asList(item.split(" "));
             words.stream().filter(word -> !word.isEmpty()).forEach(word -> letterRange.add(Character.toUpperCase(word.charAt(0))));
        });
        final List<FoodItem> foodItems = foodItemService.findFoodItemsStartingWith(letterRange);
        final Collection<String> foodItemNames = foodItems.stream().map(FoodItem::getFoodName).collect(Collectors.toSet());
        final Map<Character, Set<String>> foodItemsByFirstCharacter = foodItemNames.stream().collect(Collectors.groupingBy(name -> Character.toUpperCase(name.charAt(0)), Collectors.toSet()));
        final Set<IdentifiedIngredient> identifiedIngredients = new HashSet<>();
        for (String potentialItem : potentialReceiptItems) {
            Set<String> sameStartStrings = foodItemsByFirstCharacter.get(Character.toUpperCase(potentialItem.charAt(0)));
            ExtractedResult bestMatch = null;
            if (sameStartStrings != null) {
                bestMatch = FuzzySearch.extractOne(potentialItem, sameStartStrings, new TokenSort());
            }
            if (sameStartStrings != null && bestMatch.getScore() >= 80){
                identifiedIngredients.add(new IdentifiedIngredient(bestMatch.getString(), false));
                continue;
            }
            if (sameStartStrings == null || bestMatch.getScore() < 80) {
                final List<String> words = Arrays.asList(potentialItem.split(" "));
                Collections.reverse(words);
                for (String word : words.stream().filter(word -> word.length() >= 3 && !isIgnoredWord(word)).collect(Collectors.toList())) {
                    sameStartStrings = foodItemsByFirstCharacter.get(Character.toUpperCase(word.charAt(0)));
                    if (sameStartStrings == null) {
                        continue;
                    }
                    sameStartStrings = sameStartStrings
                            .stream()
                            .filter(name -> word.length() <= name.length())
                            .collect(Collectors.toSet());
                    if (sameStartStrings.isEmpty()) continue;
                    bestMatch = FuzzySearch.extractOne(word, sameStartStrings);
                    if (bestMatch.getScore() >= 80) {
                        identifiedIngredients.add(new IdentifiedIngredient(bestMatch.getString(), false));
                        break;
                    }
                }
            }
        }
        return new ArrayList<>(identifiedIngredients);
    }

    @NotNull
    protected static boolean isIgnoredWord(@NotNull final String word) {
        return IGNORED_WORDS.stream().anyMatch(ignoredWord -> ignoredWord.equalsIgnoreCase(word));
    }

    @NotNull
    private List<String> organizeText(@NotNull final String receiptText){
        final List<String> splitByNewLine = Arrays.asList(receiptText.split("\n"));
        return splitByNewLine
                .stream()
                .map(text -> text
                        .replace("-", "")
                        .replace("*", "")
                        .replace("&", "")
                        .replace("#", "")
                        .replace("@", "")
                        .replace("/", "")
                        .replace("=", "")
                        .replace("$", "")
                        .replace("%", ""))
                .map(text -> {
                    List<String> tokenStrings = new ArrayList<>(Arrays.asList(text.split(" ")));
                    tokenStrings.removeIf(token -> token.matches(".*\\d.*"));
                    return String.join(" ", tokenStrings);
                })
                .filter(text -> !text.isEmpty() && text.length() >= 3)
                .collect(Collectors.toList());
    }
}
