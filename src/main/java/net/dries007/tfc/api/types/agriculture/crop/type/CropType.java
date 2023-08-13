package net.dries007.tfc.api.types.agriculture.crop.type;

import net.dries007.tfc.api.types.agriculture.crop.category.CropCategory;
import net.dries007.tfc.api.types.food.type.FoodType;
import net.dries007.tfc.api.types.food.type.FoodTypes;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.minecraft.item.ItemStack;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;

public class CropType {
    private static final Set<CropType> CROP_TYPES = new LinkedHashSet<>();

    private final String name;
    private final CropCategory cropCategory;
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

    public CropType(String name, CropCategory cropCategory,
                    Supplier<ItemStack> foodDrop, Supplier<ItemStack> foodDropEarly,
                    int growthStages, float growthTime,
                    float tempMinGrow, float tempMaxGrow, float tempMinAlive, float tempMaxAlive,
                    float rainMinGrow, float rainMaxGrow, float rainMinAlive, float rainMaxAlive) {

        this.name = name;
        this.cropCategory = cropCategory;
        this.foodDrop = foodDrop;
        this.foodDropEarly = foodDropEarly;

        this.growthStages = growthStages;
        this.growthTime = growthTime;

        this.tempMinGrow = tempMinGrow;
        this.tempMaxGrow = tempMaxGrow;
        this.tempMinAlive = tempMinAlive;
        this.tempMaxAlive = tempMaxAlive;

        this.rainMinGrow = rainMinGrow;
        this.rainMaxGrow = rainMaxGrow;
        this.rainMinAlive = rainMinAlive;
        this.rainMaxAlive = rainMaxAlive;
    }

    /**
     * Возвращает набор всех типов культур.
     *
     * @return Набор всех типов культур.
     */
    public static Set<CropType> getCropTypes() {
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

    public CropCategory getCropCategory() {
        return cropCategory;
    }

    public static class Builder {

        private final String name;
        private CropCategory category;
        private Supplier<ItemStack> foodDrop;
        private Supplier<ItemStack> foodDropEarly;
        private int growthStages;
        private float growthTime;
        private float tempMinGrow;
        private float tempMaxGrow;
        private float tempMinAlive;
        private float tempMaxAlive;
        private float rainMinGrow;
        private float rainMaxGrow;
        private float rainMinAlive;
        private float rainMaxAlive;

        public Builder(String name) {
            this.name = name;
            this.foodDrop = () -> ItemStack.EMPTY;
            this.foodDropEarly = () -> ItemStack.EMPTY;
        }

        public Builder setCategory(CropCategory category) {
            this.category = category;
            return this;
        }

        public Builder setDrop (FoodType foodTypes) {
            this.foodDrop = () -> new ItemStack(ItemFoodTFC.get(foodTypes));
            return this;
        }

        public Builder setGrowthStages (int growthStages) {
            this.growthStages = growthStages;
            return this;
        }

        public Builder setGrowthTime (int growthTime) {
            this.growthTime = growthTime;
            return this;
        }

        public Builder setDropEarly (FoodType foodTypes) {
            this.foodDropEarly = () -> new ItemStack(ItemFoodTFC.get(foodTypes));
            return this;
        }

        public Builder setTempGrow (float tempMinGrow, float tempMaxGrow) {
            this.tempMinGrow = tempMinGrow;
            this.tempMaxGrow = tempMaxGrow;
            return this;
        }

        public Builder setTempAlive (float tempMinAlive, float tempMaxAlive) {
            this.tempMinAlive = tempMinGrow;
            this.tempMaxAlive = tempMaxGrow;
            return this;
        }

        public Builder setRainGrow (float rainMinGrow, float rainMaxGrow) {
            this.rainMinGrow = rainMinGrow;
            this.rainMaxGrow = rainMaxGrow;
            return this;
        }

        public Builder setRainAlive (float rainMinAlive, float rainMaxAlive) {
            this.rainMinAlive = rainMinAlive;
            this.rainMaxAlive = rainMaxAlive;
            return this;
        }


        public CropType build() {
            return new CropType(
                    name, category, foodDrop, foodDropEarly,
                    growthStages, growthTime,
                    tempMinGrow, tempMaxGrow, tempMinAlive, tempMaxAlive,
                    rainMinGrow, rainMaxGrow, rainMinAlive, rainMaxAlive);
        }
    }
}
