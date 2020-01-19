package net.skai.chefplaneteapi.service;

import net.skai.chefplaneteapi.domain.FoodItem;
import net.skai.chefplaneteapi.repository.FoodItemRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemMatchingService implements FoodItemService {

    private final FoodItemRepository foodItemRepository;

    public FoodItemMatchingService(@NotNull final FoodItemRepository foodItemRepository) {
        this.foodItemRepository = foodItemRepository;
    }

    @Override
    public List<FoodItem> findFoodItemsAlphabeticallyBetween(final char startRange, final char endRange) {
        final String range = startRange + "-" + endRange;
        return foodItemRepository.findFoodItemsByFoodNameMatchesRegex("^[" + range + range.toUpperCase() + "]");
    }
}
