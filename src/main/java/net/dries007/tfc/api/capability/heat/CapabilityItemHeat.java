package net.dries007.tfc.api.capability.heat;

import su.terrafirmagreg.modules.core.init.ItemsCore;
import su.terrafirmagreg.modules.wood.init.ItemsWood;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.capability.DumbStorage;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.types.DefaultPlants;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.blocks.wood.BlockLogTFCF;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.types.PlantsTFCF;
import tfcflorae.types.TreesTFCF;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;

public final class CapabilityItemHeat {

    public static final ResourceLocation KEY = new ResourceLocation(MODID_TFC, "item_heat");
    public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new HashMap<>(); //Used inside CT, set custom IItemHeat for items outside TFC
    @CapabilityInject(IItemHeat.class)
    public static Capability<IItemHeat> ITEM_HEAT_CAPABILITY;

    public static void preInit() {
        CapabilityManager.INSTANCE.register(IItemHeat.class, new DumbStorage<>(), ItemHeatHandler::new);

    }

    public static void init() {
        //register heat on vanilla egg for cooking
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(Items.EGG), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of("blockClay"), () -> new ItemHeatHandler(null, 1, 600));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsCore.GLASS_SHARD), () -> new ItemHeatHandler(null, 1, 1000));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsWood.STICK_BUNCH), () -> new ItemHeatHandler(null, 1, 200));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of("terracotta"), () -> new ItemHeatHandler(null, 1, 1200));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(Blocks.IRON_BARS),
                () -> new ItemHeatHandler(null, Metal.WROUGHT_IRON.getSpecificHeat(), Metal.WROUGHT_IRON.getMeltTemp()));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.ARTISTS_CONK))),
                () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SULPHUR_SHELF))),
                () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.TURKEY_TAIL))),
                () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.PORCINI))),
                () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.AMANITA))),
                () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BLACK_POWDERPUFF))),
                () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CHANTERELLE))),
                () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.DEATH_CAP))),
                () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GIANT_CLUB))),
                () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.PARASOL_MUSHROOM))),
                () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.STINKHORN))),
                () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WEEPING_MILK_CAP))),
                () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WOOD_BLEWIT))),
                () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WOOLLY_GOMPHUS))),
                () -> new ItemHeatHandler(null, 1, 480));

        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(Items.CLAY_BALL), () -> new ItemHeatHandler(null, 1, 1599));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.EARTHENWARE_CLAY), () -> new ItemHeatHandler(null, 1, 1599));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.KAOLINITE_CLAY), () -> new ItemHeatHandler(null, 1, 1599));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.STONEWARE_CLAY), () -> new ItemHeatHandler(null, 1, 1599));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockLogTFCF.get(TFCRegistries.TREES.getValue(TreesTFCF.EUCALYPTUS))),
                () -> new ItemHeatHandler(null, 1, 1599));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsCore.STRAW), () -> new ItemHeatHandler(null, 1, 30));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlocksTFCF.TWIG), () -> new ItemHeatHandler(null, 1, 50));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlocksTFCF.DRIFTWOOD), () -> new ItemHeatHandler(null, 1, 60));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(BlocksTFCF.BONES), () -> new ItemHeatHandler(null, 1, 425));

        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.BLACK_TEA), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.GREEN_TEA), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.WHITE_TEA), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.CANNABIS_BUD), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.CANNABIS_LEAF), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.COCA_LEAF), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.OPIUM_POPPY_BULB), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.PEYOTE), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.TOBACCO_LEAF), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_COFFEA_CHERRIES), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.CHAMOMILE_HEAD), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DANDELION_HEAD), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.LABRADOR_TEA_HEAD), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.SUNFLOWER_HEAD), () -> new ItemHeatHandler(null, 1, 480));

        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_BLACK_TEA), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_GREEN_TEA), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_WHITE_TEA), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_CANNABIS_BUD), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_CANNABIS_LEAF), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_COCA_LEAF), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_OPIUM_POPPY_BULB), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_PEYOTE), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_TOBACCO_LEAF), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.ROASTED_COFFEE_BEANS), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_CHAMOMILE_HEAD), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_DANDELION_HEAD), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_LABRADOR_TEA_HEAD), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_SUNFLOWER_HEAD), () -> new ItemHeatHandler(null, 1, 480));
    }

    /**
     * Helper method to adjust temperature towards a value, without overshooting or stuttering
     */
    public static float adjustTempTowards(float temp, float target, float delta) {
        return adjustTempTowards(temp, target, delta, delta);
    }

    public static float adjustTempTowards(float temp, float target, float deltaPositive, float deltaNegative) {
        if (temp < target) {
            return Math.min(temp + deltaPositive, target);
        } else if (temp > target) {
            return Math.max(temp - deltaNegative, target);
        } else {
            return target;
        }
    }

    /**
     * Call this from within {@link IItemHeat#getTemperature()}
     */
    public static float adjustTemp(float temp, float heatCapacity, long ticksSinceUpdate) {
        if (ticksSinceUpdate <= 0) return temp;
        final float newTemp = temp - heatCapacity * (float) ticksSinceUpdate * (float) ConfigTFC.Devices.TEMPERATURE.globalModifier;
        return newTemp < 0 ? 0 : newTemp;
    }

    public static void addTemp(IItemHeat instance) {
        // Default modifier = 3 (2x normal cooling)
        addTemp(instance, 3);
    }

    /**
     * Use this to increase the heat on an IItemHeat instance.
     *
     * @param modifier the modifier for how much this will heat up: 0 - 1 slows down cooling, 1 = no heating or cooling, > 1 heats, 2 heats at the
     *                 same rate of normal cooling, 2+ heats faster
     */
    public static void addTemp(IItemHeat instance, float modifier) {
        final float temp = instance.getTemperature() + modifier * instance.getHeatCapacity() * (float) ConfigTFC.Devices.TEMPERATURE.globalModifier;
        instance.setTemperature(temp);
    }

    public static float adjustToTargetTemperature(float temp, float burnTemp, int airTicks, int maxTempBonus) {
        boolean hasAir = airTicks > 0;
        float targetTemperature = burnTemp + (hasAir ? MathHelper.clamp(burnTemp, 0, maxTempBonus) : 0);
        if (temp != targetTemperature) {
            float delta = (float) ConfigTFC.Devices.TEMPERATURE.heatingModifier;
            return adjustTempTowards(temp, targetTemperature, delta * (hasAir ? 2 : 1), delta * (hasAir ? 0.5f : 1));
        }
        return temp;
    }

    @Nullable
    public static ICapabilityProvider getCustomHeat(ItemStack stack) {
        Set<IIngredient<ItemStack>> itemItemSet = CUSTOM_ITEMS.keySet();
        for (IIngredient<ItemStack> ingredient : itemItemSet) {
            if (ingredient.testIgnoreCount(stack)) {
                return CUSTOM_ITEMS.get(ingredient).get();
            }
        }
        return null;
    }
}
