package net.skai.chefplaneteapi.service;

import net.skai.chefplaneteapi.domain.FoodItem;
import net.skai.chefplaneteapi.repository.FoodItemRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class FoodItemMatchingService implements FoodItemService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final FoodItemRepository foodItemRepository;

    public FoodItemMatchingService(@NotNull final FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    @Override
    public List<FoodItem> findFoodItemsAlphabeticallyBetween(final char startRange, final char endRange) {
        final String range = startRange + "-" + endRange;
        final String regexQuery = "^[" + range.toLowerCase() + range.toUpperCase() + "]";
        LOGGER.info("findFoodItemsAlphabeticallyBetween: " + regexQuery);
        return foodItemRepository.findFoodItemsByFoodNameMatchesRegex(regexQuery);
    }

    @Override
    public List<FoodItem> findFoodItemsStartingWith(@NotNull final Collection<Character> startingLetters) {
        final StringBuilder letterRange = new StringBuilder();
        for(char letter : startingLetters) {
            letterRange.append("" + Character.toLowerCase(letter) + Character.toUpperCase(letter));
        }
        final String regexQuery = "^[" + letterRange.toString() + "]";
        LOGGER.info("findFoodItemsStartingWith: " + regexQuery);
        return foodItemRepository.findFoodItemsByFoodNameMatchesRegex(regexQuery);
    }

    // warning: very slow
    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemRepository.findAll();
    }
}
