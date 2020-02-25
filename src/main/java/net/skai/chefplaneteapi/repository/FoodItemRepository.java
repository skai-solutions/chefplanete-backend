package net.skai.chefplaneteapi.repository;

import net.skai.chefplaneteapi.domain.FoodItem;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemRepository extends MongoRepository<FoodItem, String> {

    @NotNull
    List<FoodItem> findFoodItemsByFoodNameMatchesRegex(@NotNull String regex);
}
