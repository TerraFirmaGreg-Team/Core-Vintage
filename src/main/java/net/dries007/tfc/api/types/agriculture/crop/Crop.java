package net.dries007.tfc.api.types.agriculture.crop;

import net.dries007.tfc.api.types.agriculture.crop.category.CropCategory;
import net.dries007.tfc.api.types.food.type.FoodType;
import net.dries007.tfc.api.types.food.type.FoodTypes;
import net.dries007.tfc.common.objects.blocks.agriculture.BlockCropDead;
import net.dries007.tfc.common.objects.blocks.agriculture.BlockCropSimple;
import net.dries007.tfc.common.objects.blocks.agriculture.BlockCropSpreading;
import net.dries007.tfc.common.objects.blocks.agriculture.BlockCropTFC;
import net.dries007.tfc.common.objects.items.ItemsTFC;
import net.dries007.tfc.common.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.skills.Skill;
import net.dries007.tfc.util.skills.SkillTier;
import net.dries007.tfc.world.classic.worldgen.WorldGenWildCrops;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import static net.dries007.tfc.api.types.agriculture.crop.category.CropCategories.*;

public enum Crop implements ICrop {
    // these definitions are defined in the spreadsheet at
    // https://docs.google.com/spreadsheets/d/1Ghw3dCmVO5Gv0MMGBydUxox_nwLYmmcZkGSbbf0QSAE/edit#gid=893781093
    // It should be modified first, and then the resulting definitions copied to this space here
    BARLEY(FoodTypes.BARLEY, 0f, 1f, 26f, 33f, 50f, 70f, 310f, 330f, 8, 0.4f, SIMPLE),
    MAIZE(FoodTypes.MAIZE, 10f, 19f, 40f, 45f, 110f, 140f, 400f, 450f, 6, 0.6f, SIMPLE),
    OAT(FoodTypes.OAT, 0f, 3f, 30f, 34f, 50f, 100f, 350f, 400f, 8, 0.5f, SIMPLE),
    RICE(FoodTypes.RICE, 20f, 22f, 40f, 45f, 250f, 300f, 450f, 500f, 8, 0.6f, SIMPLE),
    RYE(FoodTypes.RYE, 0f, 4f, 35f, 40f, 50f, 100f, 400f, 450f, 8, 0.5f, SIMPLE),
    WHEAT(FoodTypes.WHEAT, 0f, 3f, 30f, 34f, 50f, 100f, 350f, 400f, 8, 0.5f, SIMPLE),
    BEET(FoodTypes.BEET, -5f, 0f, 20f, 25f, 50f, 70f, 300f, 320f, 7, 0.6f, SIMPLE),
    CABBAGE(FoodTypes.CABBAGE, -10f, 0f, 27f, 33f, 50f, 60f, 280f, 300f, 6, 0.6f, SIMPLE),
    CARROT(FoodTypes.CARROT, 3f, 10f, 30f, 36f, 50f, 100f, 400f, 450f, 5, 0.6f, SIMPLE),
    GARLIC(FoodTypes.GARLIC, -20f, -1f, 18f, 26f, 50f, 60f, 310f, 340f, 5, 0.65f, SIMPLE),
    GREEN_BEAN(FoodTypes.GREEN_BEAN, 2f, 9f, 35f, 41f, 70f, 150f, 410f, 450f, 7, 0.45f, PICKABLE),
    ONION(FoodTypes.ONION, -1f, 10f, 37f, 40f, 70f, 200f, 410f, 450f, 7, 0.4f, SIMPLE),
    POTATO(FoodTypes.POTATO, 0f, 4f, 30f, 35f, 50f, 100f, 390f, 440f, 7, 0.55f, SIMPLE),
    SOYBEAN(FoodTypes.SOYBEAN, 8f, 12f, 30f, 36f, 55f, 160f, 410f, 450f, 7, 0.5f, SIMPLE),
    SQUASH(FoodTypes.SQUASH, 5f, 14f, 33f, 37f, 45f, 90f, 390f, 440f, 8, 0.5f, SIMPLE),
    SUGARCANE(FoodTypes.SUGARCANE, 12f, 20f, 38f, 45f, 50f, 160f, 410f, 450f, 8, 0.5f, SIMPLE),
    TOMATO(FoodTypes.TOMATO, 0f, 8f, 36f, 40f, 50f, 120f, 390f, 430f, 8, 0.45f, PICKABLE),
    RED_BELL_PEPPER(() -> new ItemStack(ItemFoodTFC.get(FoodTypes.RED_BELL_PEPPER)), () -> new ItemStack(ItemFoodTFC.get(FoodTypes.GREEN_BELL_PEPPER)), 4f, 12f, 32f, 38f, 50f, 100f, 400f, 450f, 7, 0.55f, PICKABLE),
    YELLOW_BELL_PEPPER(() -> new ItemStack(ItemFoodTFC.get(FoodTypes.YELLOW_BELL_PEPPER)), () -> new ItemStack(ItemFoodTFC.get(FoodTypes.GREEN_BELL_PEPPER)), 4f, 12f, 32f, 38f, 50f, 100f, 400f, 450f, 7, 0.55f, PICKABLE),
    JUTE(() -> new ItemStack(ItemsTFC.JUTE), () -> ItemStack.EMPTY, 5f, 11f, 37f, 42f, 50f, 100f, 410f, 450f, 6, 0.5f, SIMPLE);

    static {
        for (ICrop crop : values()) {
            WorldGenWildCrops.register(crop);
        }
    }

    // how this crop generates food items
    private final Supplier<ItemStack> foodDrop;
    private final Supplier<ItemStack> foodDropEarly;
    // temperature compatibility range
    private final float tempMinAlive, tempMinGrow, tempMaxGrow, tempMaxAlive;
    // rainfall compatibility range
    private final float rainMinAlive, rainMinGrow, rainMaxGrow, rainMaxAlive;
    // growth
    private final int growthStages; // the number of blockstates the crop has for growing, ignoring wild state
    private final float growthTime; // Time is measured in % of months, scales with calendar month length
    // which crop block behavior implementation is used
    private final CropCategory cropCategory;

    Crop(FoodType foodOldDrop, float tempMinAlive, float tempMinGrow, float tempMaxGrow, float tempMaxAlive, float rainMinAlive, float rainMinGrow, float rainMaxGrow, float rainMaxAlive, int growthStages, float growthTime, CropCategory cropCategory) {
        this(() -> new ItemStack(ItemFoodTFC.get(foodOldDrop)), () -> ItemStack.EMPTY, tempMinAlive, tempMinGrow, tempMaxGrow, tempMaxAlive, rainMinAlive, rainMinGrow, rainMaxGrow, rainMaxAlive, growthStages, growthTime, cropCategory);
    }

    Crop(Supplier<ItemStack> foodDrop, Supplier<ItemStack> foodDropEarly, float tempMinAlive, float tempMinGrow, float tempMaxGrow, float tempMaxAlive, float rainMinAlive, float rainMinGrow, float rainMaxGrow, float rainMaxAlive, int growthStages, float growthTime, CropCategory cropCategory) {
        this.foodDrop = foodDrop;
        this.foodDropEarly = foodDropEarly;

        this.tempMinAlive = tempMinAlive;
        this.tempMinGrow = tempMinGrow;
        this.tempMaxGrow = tempMaxGrow;
        this.tempMaxAlive = tempMaxAlive;

        this.rainMinAlive = rainMinAlive;
        this.rainMinGrow = rainMinGrow;
        this.rainMaxGrow = rainMaxGrow;
        this.rainMaxAlive = rainMaxAlive;

        this.growthStages = growthStages;
        this.growthTime = growthTime; // This is measured in % of months

        this.cropCategory = cropCategory;
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

    @Override
    public long getGrowthTicks() {
        return (long) (growthTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.TICKS_IN_DAY);
    }

    @Override
    public int getMaxStage() {
        return growthStages - 1;
    }

    @Override
    public boolean isValidConditions(float temperature, float rainfall) {
        return tempMinAlive < temperature && temperature < tempMaxAlive && rainMinAlive < rainfall && rainfall < rainMaxAlive;
    }

    @Override
    public boolean isValidForGrowth(float temperature, float rainfall) {
        return tempMinGrow < temperature && temperature < tempMaxGrow && rainMinGrow < rainfall && rainfall < rainMaxGrow;
    }

    @Nonnull
    @Override
    public ItemStack getFoodDrop(int currentStage) {
        if (currentStage == getMaxStage()) {
            return foodDrop.get();
        } else if (currentStage == getMaxStage() - 1) {
            return foodDropEarly.get();
        }
        return ItemStack.EMPTY;
    }

    @SuppressWarnings("deprecation")
    public BlockCropTFC createGrowingBlock() {
        if (cropCategory == SIMPLE || cropCategory == PICKABLE) {
            return BlockCropSimple.create(this, cropCategory == PICKABLE);
        } else if (cropCategory == SPREADING) {
            return BlockCropSpreading.create(this);
        }
        throw new IllegalStateException("Invalid growthstage property " + growthStages + " for crop");
    }

    public BlockCropDead createDeadBlock() {
        return new BlockCropDead(this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (GuiScreen.isShiftKeyDown()) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.climate_info"));
            tooltip.add(TextFormatting.BLUE + I18n.format("tfc.tooltip.climate_info_rainfall", (int) rainMinGrow, (int) rainMaxGrow));
            tooltip.add(TextFormatting.GOLD + I18n.format("tfc.tooltip.climate_info_temperature", String.format("%.1f", tempMinGrow), String.format("%.1f", tempMaxGrow)));
        } else {
            tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.hold_shift_for_climate_info"));
        }
    }
}
