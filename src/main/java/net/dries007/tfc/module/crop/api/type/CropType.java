package net.dries007.tfc.module.crop.api.type;

import net.dries007.tfc.module.crop.api.category.CropCategory;
import net.dries007.tfc.api.types.food.type.FoodType;
import net.dries007.tfc.common.objects.items.TFCItems;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.skills.Skill;
import net.dries007.tfc.util.skills.SkillTier;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

import static net.dries007.tfc.api.types.food.variant.Item.FoodItemVariants.INGREDIENT;

public class CropType {
    private static final Set<CropType> CROP_TYPES = new LinkedHashSet<>();

    private final String name;
    private final CropCategory cropCategory;
    private final Supplier<ItemStack> foodDrop;
    private final Supplier<ItemStack> seedDrop;
    private final float growthTime;
    private final float tempMinGrow;
    private final float tempMaxGrow;
    private final float tempMinAlive;
    private final float tempMaxAlive;
    private final float rainMinGrow;
    private final float rainMaxGrow;
    private final float rainMinAlive;
    private final float rainMaxAlive;

    private CropType(Builder builder) {

        this.name = builder.name;
        this.cropCategory = builder.category;
        this.foodDrop = builder.foodDrop;
        this.seedDrop = builder.seedDrop;

        this.growthTime = builder.growthTime;

        this.tempMinAlive = builder.tempMinAlive;
        this.tempMinGrow = builder.tempMinGrow;
        this.tempMaxGrow = builder.tempMaxGrow;
        this.tempMaxAlive = builder.tempMaxAlive;

        this.rainMinAlive = builder.rainMinAlive;
        this.rainMinGrow = builder.rainMinGrow;
        this.rainMaxGrow = builder.rainMaxGrow;
        this.rainMaxAlive = builder.rainMaxAlive;

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

    public ItemStack getDropSeed() {
        return seedDrop.get();
    }

    public ItemStack getDropFood() {
        return foodDrop.get();
    }

    public long getGrowthTicks() {
        return (long) (growthTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.TICKS_IN_DAY);
    }


    public boolean isValidConditions(float temperature, float rainfall) {
        return tempMinAlive < temperature && temperature < tempMaxAlive && rainMinAlive < rainfall && rainfall < rainMaxAlive;
    }

    public boolean isValidForGrowth(float temperature, float rainfall) {
        return tempMinGrow < temperature && temperature < tempMaxGrow && rainMinGrow < rainfall && rainfall < rainMaxGrow;
    }

    @SideOnly(Side.CLIENT)
    public void addInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (GuiScreen.isShiftKeyDown()) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.climate_info"));
            tooltip.add(TextFormatting.BLUE + I18n.format("tfc.tooltip.climate_info_rainfall", (int) rainMinGrow, (int) rainMaxGrow));
            tooltip.add(TextFormatting.GOLD + I18n.format("tfc.tooltip.climate_info_temperature", String.format("%.1f", tempMinGrow), String.format("%.1f", tempMaxGrow)));
        } else {
            tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.hold_shift_for_climate_info"));
        }
    }

    public static class Builder {
        private final String name;
        private CropCategory category;
        private Supplier<ItemStack> foodDrop;
        private Supplier<ItemStack> seedDrop;
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
            this.seedDrop = () -> ItemStack.EMPTY;
        }

        public Builder setCategory(CropCategory category) {
            this.category = category;
            return this;
        }

        public Builder setFoodDrop(FoodType type) {
            this.foodDrop = () -> new ItemStack(TFCItems.getFoodItem(INGREDIENT, type));
            return this;
        }

        public Builder setFoodDrop(Item item) {
            this.foodDrop = () -> new ItemStack(item);
            return this;
        }

        public Builder setSeed(Item item) {
            this.seedDrop = () -> new ItemStack(item);
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
            return new CropType(this);
        }
    }
}
