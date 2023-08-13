package net.dries007.tfc.api.types.fruit;

import net.dries007.tfc.api.types.food.variant.FoodVariant;
import net.dries007.tfc.api.types.food.variant.FoodVariants;
import net.dries007.tfc.common.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.world.classic.worldgen.WorldGenFruitTrees;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public enum FruitTree implements IFruitTree {
    BANANA(FoodVariants.BANANA, Month.APRIL, 2, Month.SEPTEMBER, 1, 23f, 35f, 280f, 400f, 0.33f),
    CHERRY(FoodVariants.CHERRY, Month.APRIL, 1, Month.JUNE, 1, 5f, 21f, 100f, 350f, 0.33f),
    GREEN_APPLE(FoodVariants.GREEN_APPLE, Month.MAY, 2, Month.OCTOBER, 2, 8f, 25f, 110f, 280f, 0.33f),
    LEMON(FoodVariants.LEMON, Month.MAY, 2, Month.AUGUST, 1, 10f, 30f, 180f, 400f, 0.33f),
    OLIVE(FoodVariants.OLIVE, Month.JUNE, 1, Month.OCTOBER, 2, 13f, 30f, 150f, 380f, 0.33f),
    ORANGE(FoodVariants.ORANGE, Month.FEBRUARY, 3, Month.NOVEMBER, 1, 23f, 36f, 250f, 400f, 0.33f),
    PEACH(FoodVariants.PEACH, Month.APRIL, 2, Month.SEPTEMBER, 1, 9f, 27f, 60f, 230f, 0.33f),
    PLUM(FoodVariants.PLUM, Month.MAY, 2, Month.JULY, 2, 18f, 31f, 250f, 400f, 0.33f),
    RED_APPLE(FoodVariants.RED_APPLE, Month.MAY, 2, Month.OCTOBER, 2, 9f, 25f, 100f, 280f, 0.33f);

    static {
        for (IFruitTree tree : values()) {
            WorldGenFruitTrees.register(tree);
        }
    }

    private final FoodVariant fruit;
    private final Month flowerMonthStart;
    private final int floweringMonths;
    private final Month harvestMonthStart;
    private final int harvestingMonths;
    private final float growthTime;
    private final float minTemp;
    private final float maxTemp;
    private final float minRain;
    private final float maxRain;

    FruitTree(FoodVariant fruit, Month flowerMonthStart, int floweringMonths, Month harvestMonthStart, int harvestingMonths, float minTemp, float maxTemp, float minRain, float maxRain, float growthTime) {
        this.fruit = fruit;
        this.flowerMonthStart = flowerMonthStart;
        this.floweringMonths = floweringMonths;
        this.harvestMonthStart = harvestMonthStart;
        this.harvestingMonths = harvestingMonths;
        this.growthTime = growthTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.HOURS_IN_DAY;

        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.minRain = minRain;
        this.maxRain = maxRain;
    }

    public FoodVariant getFruit() {
        return this.fruit;
    }

    @Override
    public float getGrowthTime() {
        return this.growthTime;
    }

    @Override
    public boolean isFlowerMonth(Month month) {
        Month testing = this.flowerMonthStart;
        for (int i = 0; i < this.floweringMonths; i++) {
            if (testing.equals(month)) return true;
            testing = testing.next();
        }
        return false;
    }

    @Override
    public boolean isHarvestMonth(Month month) {
        Month testing = this.harvestMonthStart;
        for (int i = 0; i < this.harvestingMonths; i++) {
            if (testing.equals(month)) return true;
            testing = testing.next();
        }
        return false;
    }

    @Override
    public boolean isValidConditions(float temperature, float rainfall) {
        return minTemp - 5 < temperature && temperature < maxTemp + 5 && minRain - 50 < rainfall && rainfall < maxRain + 50;
    }

    @Override
    public boolean isValidForGrowth(float temperature, float rainfall) {
        return minTemp < temperature && temperature < maxTemp && minRain < rainfall && rainfall < maxRain;
    }

    @Override
    public ItemStack getFoodDrop() {
        return new ItemStack(ItemFoodTFC.get(this.getFruit()));
    }

    @Override
    public String getName() {
        return this.name();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (GuiScreen.isShiftKeyDown()) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.climate_info"));
            tooltip.add(TextFormatting.BLUE + I18n.format("tfc.tooltip.climate_info_rainfall", (int) minRain, (int) maxRain));
            tooltip.add(TextFormatting.GOLD + I18n.format("tfc.tooltip.climate_info_temperature", String.format("%.1f", minTemp), String.format("%.1f", maxTemp)));
        } else {
            tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.hold_shift_for_climate_info"));
        }
    }
}
