package net.skai.chefplaneteapi.service;

import net.skai.chefplaneteapi.domain.FoodItem;

import java.util.List;

public interface FoodItemService {

    List<FoodItem> findFoodItemsAlphabeticallyBetween(final char startRange, final char endRange);
}
