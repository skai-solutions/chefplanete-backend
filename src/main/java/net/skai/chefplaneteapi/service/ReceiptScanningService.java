package net.skai.chefplaneteapi.service;

import net.skai.chefplaneteapi.domain.FoodItem;
import net.skai.chefplaneteapi.domain.scanning.IdentifiedIngredient;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceiptScanningService {

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
        LOGGER.info(foodItemService.findFoodItemsAlphabeticallyBetween('a', 'b').stream().map(FoodItem::getFoodName).collect(Collectors.joining()));
        return Collections.singletonList(new IdentifiedIngredient(cloudVisionTemplate.extractTextFromImage(new ByteArrayResource(decodedImage)), true));
    }

    @NotNull
    private List<List<String>> organizeText(@NotNull final String receiptText){
        return Collections.emptyList();
    }
}
