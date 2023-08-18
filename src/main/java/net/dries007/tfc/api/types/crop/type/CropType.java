package net.dries007.tfc.api.types.crop.type;

import net.dries007.tfc.api.types.crop.category.CropCategory;
import net.dries007.tfc.api.types.food.variant.FoodVariant;
import net.dries007.tfc.common.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.skills.Skill;
import net.dries007.tfc.util.skills.SkillTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Random;
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
                    float tempMinAlive, float tempMinGrow, float tempMaxGrow, float tempMaxAlive,
                    float rainMinAlive, float rainMinGrow, float rainMaxGrow, float rainMaxAlive) {

        this.name = name;
        this.cropCategory = cropCategory;
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

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("CropType name must contain any character: [%s]", name));
        }

        if (!CROP_TYPES.add(this)) {
            throw new RuntimeException(String.format("CropType: [%s] already exists!", name));
        }
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
     * the count to add to the amount of food dropped when applying the skill bonus
     *
     * @param skill  agriculture skill of the harvester
     * @param random random instance to use, generally Block.RANDOM
     * @return amount to add to item stack count
     */
    public static int getSkillFoodBonus(Skill skill, Random random) {
        return random.nextInt(2 + (int) (6 * skill.getTotalLevel()));
    }

    /**
     * the count to add to the amount of seeds dropped when applying the skill bonus
     *
     * @param skill  agriculture skill of the harvester
     * @param random random instance to use, generally Block.RANDOM
     * @return amount to add to item stack count
     */
    public static int getSkillSeedBonus(Skill skill, Random random) {
        if (skill.getTier().isAtLeast(SkillTier.ADEPT) && random.nextInt(10 - 2 * skill.getTier().ordinal()) == 0)
            return 1;
        else
            return 0;
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

    public long getGrowthTicks() {
        return (long) (growthTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.TICKS_IN_DAY);
    }

    public int getMaxStage() {
        return growthStages - 1;
    }

    public boolean isValidConditions(float temperature, float rainfall) {
        return tempMinAlive < temperature && temperature < tempMaxAlive && rainMinAlive < rainfall && rainfall < rainMaxAlive;
    }

    public boolean isValidForGrowth(float temperature, float rainfall) {
        return tempMinGrow < temperature && temperature < tempMaxGrow && rainMinGrow < rainfall && rainfall < rainMaxGrow;
    }

    @Nonnull
    public ItemStack getFoodDrop(int currentStage) {
        if (currentStage == getMaxStage()) {
            return foodDrop.get();
        } else if (currentStage == getMaxStage() - 1) {
            return foodDropEarly.get();
        }
        return ItemStack.EMPTY;
    }

    public static class Builder {

        private final String name;
        private CropCategory category;
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

        public Builder setType(CropCategory category) {
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


        public CropType build() {
            return new CropType(
                    name, category, foodDrop, foodDropEarly,
                    growthStages, growthTime,
                    tempMinAlive, tempMinGrow, tempMaxGrow, tempMaxAlive,
                    rainMinAlive, rainMinGrow, rainMaxGrow, rainMaxAlive);
        }
    }
}
