package net.skai.chefplaneteapi.domain;

import org.jetbrains.annotations.NotNull;

public class Ingredient {

    private final String name;

    private final String unitName;

    private float quantity;

    public Ingredient(@NotNull final String name,
                      @NotNull final String unitName,
                      final float quantity) {
        this.name = name;
        this.quantity = quantity;
        this.unitName = unitName;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public String getUnitName() {
        return unitName;
    }

    @NotNull
    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(final float quantity) {
        this.quantity = quantity;
    }
}
