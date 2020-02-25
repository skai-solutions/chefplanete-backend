package net.skai.chefplaneteapi.service;

import net.skai.chefplaneteapi.domain.FoodItem;

import java.util.Collection;
import java.util.List;

public interface FoodItemService {

    List<FoodItem> findFoodItemsAlphabeticallyBetween(final char startRange, final char endRange);

    List<FoodItem> findFoodItemsStartingWith(final Collection<Character> startingLetters);

    // warning: very slow
    List<FoodItem> getAllFoodItems();
}
