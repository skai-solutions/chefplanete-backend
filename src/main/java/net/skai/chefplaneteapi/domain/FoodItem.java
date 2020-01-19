package net.skai.chefplaneteapi.domain;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class FoodItem {

    @Id
    private String id;

    @Field("product_name_en")
    private String foodName;

    public FoodItem(@NotNull final String id,
                    @NotNull final String foodName) {
        this.id = id;
        this.foodName = foodName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
}
