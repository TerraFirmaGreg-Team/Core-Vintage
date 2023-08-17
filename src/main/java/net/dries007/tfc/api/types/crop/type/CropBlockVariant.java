package net.dries007.tfc.api.types.crop.type;

import net.dries007.tfc.api.types.crop.category.CropType;
import net.dries007.tfc.api.types.food.variant.FoodVariant;
import net.dries007.tfc.common.objects.items.food.ItemFoodTFC;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;

public class CropBlockVariant {
    private static final Set<CropBlockVariant> CROP_TYPES = new LinkedHashSet<>();

    private final String name;
    private final CropType cropType;
    private final Supplier<ItemStack> foodDrop;
    private final Supplier<ItemStack> foodDropEarly;
    private final int growthStages;
    private final float growthTime;
    private final float tempMinGrow;
    private final float tempMaxGrow;
    private final float tempMinAlive;
    private final float tempMaxAlive;
    private final float rainMinGrow;
    private final float rainMaxGrow;
    private final float rainMinAlive;
    private final float rainMaxAlive;

    public CropBlockVariant(String name, CropType cropType,
                            Supplier<ItemStack> foodDrop, Supplier<ItemStack> foodDropEarly,
                            int growthStages, float growthTime,
                            float tempMinAlive, float tempMinGrow, float tempMaxGrow, float tempMaxAlive,
                            float rainMinAlive, float rainMinGrow, float rainMaxGrow, float rainMaxAlive) {

        this.name = name;
        this.cropType = cropType;
        this.foodDrop = foodDrop;
        this.foodDropEarly = foodDropEarly;

        this.growthStages = growthStages;
        this.growthTime = growthTime;

        this.tempMinAlive = tempMinAlive;
        this.tempMinGrow = tempMinGrow;
        this.tempMaxGrow = tempMaxGrow;
        this.tempMaxAlive = tempMaxAlive;

        this.rainMinAlive = rainMinAlive;
        this.rainMinGrow = rainMinGrow;
        this.rainMaxGrow = rainMaxGrow;
        this.rainMaxAlive = rainMaxAlive;
    }

    /**
     * Возвращает набор всех типов культур.
     *
     * @return Набор всех типов культур.
     */
    public static Set<CropBlockVariant> getCropTypes() {
        return CROP_TYPES;
    }

    /**
     * Возвращает строковое представление типов культур.
     *
     * @return Строковое представление типов культур.
     */
    @Override
    public String toString() {
        return name;
    }

    public CropType getCropCategory() {
        return cropType;
    }

    public static class Builder {

        private final String name;
        private CropType category;
        private Supplier<ItemStack> foodDrop;
        private Supplier<ItemStack> foodDropEarly;
        private int growthStages;
        private float growthTime;

        private float tempMinAlive;
        private float tempMinGrow;
        private float tempMaxGrow;
        private float tempMaxAlive;

        private float rainMinAlive;
        private float rainMinGrow;
        private float rainMaxGrow;
        private float rainMaxAlive;

        public Builder(String name) {
            this.name = name;
            this.foodDrop = () -> ItemStack.EMPTY;
            this.foodDropEarly = () -> ItemStack.EMPTY;
        }

        public Builder setType(CropType category) {
            this.category = category;
            return this;
        }

        public Builder setDrop(FoodVariant foodTypes) {
            this.foodDrop = () -> new ItemStack(ItemFoodTFC.get(foodTypes));
            return this;
        }

        public Builder setDrop(Item item) {
            this.foodDrop = () -> new ItemStack(item);
            return this;
        }

        public Builder setDropEarly(FoodVariant foodTypes) {
            this.foodDropEarly = () -> new ItemStack(ItemFoodTFC.get(foodTypes));
            return this;
        }

        public Builder setDropEarly(Item item) {
            this.foodDropEarly = () -> new ItemStack(item);
            return this;
        }

        public Builder setGrowthStages(int growthStages) {
            this.growthStages = growthStages;
            return this;
        }

        public Builder setGrowthTime(float growthTime) {
            this.growthTime = growthTime;
            return this;
        }

        public Builder setTemp(float tempMinAlive, float tempMinGrow, float tempMaxGrow, float tempMaxAlive) {
            this.tempMinAlive = tempMinAlive;
            this.tempMinGrow = tempMinGrow;
            this.tempMaxGrow = tempMaxGrow;
            this.tempMaxAlive = tempMaxAlive;
            return this;
        }

        public Builder setRain(float rainMinAlive, float rainMinGrow, float rainMaxGrow, float rainMaxAlive) {
            this.rainMinAlive = rainMinAlive;
            this.rainMinGrow = rainMinGrow;
            this.rainMaxGrow = rainMaxGrow;
            this.rainMaxAlive = rainMaxAlive;
            return this;
        }


        public CropBlockVariant build() {
            return new CropBlockVariant(
                    name, category, foodDrop, foodDropEarly,
                    growthStages, growthTime,
                    tempMinAlive, tempMinGrow, tempMaxGrow, tempMaxAlive,
                    rainMinAlive, rainMinGrow, rainMaxGrow, rainMaxAlive);
        }
    }
}
