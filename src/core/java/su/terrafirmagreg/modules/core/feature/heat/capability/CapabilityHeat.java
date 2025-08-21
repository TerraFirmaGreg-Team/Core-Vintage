package su.terrafirmagreg.modules.core.feature.heat.capability;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.data.ingredient.IIngredient;
import su.terrafirmagreg.modules.core.init.ItemsCore;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.dries007.firmalife.registry.ItemsFL;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.objects.blocks.BlocksTFCF;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFCF;
import net.dries007.tfc.objects.items.ItemsTFCF;
import net.dries007.tfc.objects.items.rock.ItemRock;
import net.dries007.tfc.types.DefaultPlants;
import net.dries007.tfc.types.PlantsTFCF;
import net.dries007.tfc.types.TreesTFCF;

import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public final class CapabilityHeat {

  public static final ResourceLocation KEY = ModUtils.resource("heat_capability");

  @CapabilityInject(ICapabilityHeat.class)
  public static final Capability<ICapabilityHeat> CAPABILITY = ModUtils.getNull();

  public static void register() {
    CapabilityManager.INSTANCE.register(ICapabilityHeat.class, new CapabilityStorageHeat(), CapabilityProviderHeat::new);

  }

  public static ICapabilityHeat get(ItemStack itemStack) {
    return itemStack.getCapability(CAPABILITY, null);
  }

  public static boolean has(ItemStack itemStack) {
    return itemStack.hasCapability(CAPABILITY, null);
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
   * Call this from within {@link ICapabilityHeat#getTemperature()}
   */
  public static float adjustTemp(float temp, float heatCapacity, long ticksSinceUpdate) {
    if (ticksSinceUpdate <= 0) {
      return temp;
    }
    final float newTemp = temp - heatCapacity * (float) ticksSinceUpdate * (float) ConfigCore.FEATURE.HEAT.globalModifier;
    return newTemp < 0 ? 0 : newTemp;
  }

  public static void addTemp(ICapabilityHeat instance) {
    // Default modifier = 3 (2x normal cooling)
    addTemp(instance, 3);
  }

  /**
   * Use this to increase the heat on an IItemHeat instance.
   *
   * @param modifier the modifier for how much this will heat up: 0 - 1 slows down cooling, 1 = no heating or cooling, > 1 heats, 2 heats at the same rate of normal cooling, 2+ heats faster
   */
  public static void addTemp(ICapabilityHeat instance, float modifier) {
    final float temp = instance.getTemperature() + modifier * instance.getHeatCapacity() * (float) ConfigCore.FEATURE.HEAT.globalModifier;
    instance.setTemperature(temp);
  }

  public static float adjustToTargetTemperature(float temp, float burnTemp, int airTicks, int maxTempBonus) {
    boolean hasAir = airTicks > 0;
    float targetTemperature = burnTemp + (hasAir ? MathHelper.clamp(burnTemp, 0, maxTempBonus) : 0);
    if (temp != targetTemperature) {
      float delta = (float) ConfigCore.FEATURE.HEAT.heatingModifier;
      return adjustTempTowards(temp, targetTemperature, delta * (hasAir ? 2 : 1), delta * (hasAir ? 0.5f : 1));
    }
    return temp;
  }

  @Nullable
  public static ICapabilityProvider getCustom(ItemStack stack) {
    for (var entry : Handler.CUSTOM_ITEMS.entrySet()) {
      if (entry.getKey().testIgnoreCount(stack)) {
        return entry.getValue().get();
      }
    }
    // Check for generic item types
    Item item = stack.getItem();
    if (item instanceof ItemRock) {
      return new CapabilityProviderHeat(stack.getTagCompound(), 0.2f, 2000f);
    }
    return null;
  }

  public static class Handler {

    //Used inside CT, set custom IItemHeat for items outside TFC
    public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new Object2ObjectOpenHashMap<>();

    public static void init() {

      CUSTOM_ITEMS.put(IIngredient.of(Items.STICK), () -> CapabilityProviderHeat.of(1, 40));

      CUSTOM_ITEMS.put(IIngredient.of(ItemsFL.HONEYCOMB), () -> CapabilityProviderHeat.of(1, 600));

      CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.ARTISTS_CONK))), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SULPHUR_SHELF))), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.TURKEY_TAIL))), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.PORCINI))), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.AMANITA))), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BLACK_POWDERPUFF))), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CHANTERELLE))), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.DEATH_CAP))), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GIANT_CLUB))), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.PARASOL_MUSHROOM))), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.STINKHORN))), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WEEPING_MILK_CAP))), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WOOD_BLEWIT))), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WOOLLY_GOMPHUS))), () -> CapabilityProviderHeat.of(1, 480));

      CUSTOM_ITEMS.put(IIngredient.of(Items.CLAY_BALL), () -> CapabilityProviderHeat.of(1, 1599));
      CUSTOM_ITEMS.put(IIngredient.of(BlockLogTFCF.get(TFCRegistries.TREES.getValue(TreesTFCF.EUCALYPTUS))), () -> CapabilityProviderHeat.of(1, 1599));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsCore.STRAW), () -> CapabilityProviderHeat.of(1, 30));
      CUSTOM_ITEMS.put(IIngredient.of(BlocksTFCF.TWIG), () -> CapabilityProviderHeat.of(1, 50));
      CUSTOM_ITEMS.put(IIngredient.of(BlocksTFCF.DRIFTWOOD), () -> CapabilityProviderHeat.of(1, 60));
      CUSTOM_ITEMS.put(IIngredient.of(BlocksTFCF.BONES), () -> CapabilityProviderHeat.of(1, 425));

      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.BLACK_TEA), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.GREEN_TEA), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.WHITE_TEA), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.CANNABIS_BUD), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.CANNABIS_LEAF), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.COCA_LEAF), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.OPIUM_POPPY_BULB), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.PEYOTE), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.TOBACCO_LEAF), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_COFFEA_CHERRIES), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.CHAMOMILE_HEAD), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DANDELION_HEAD), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.LABRADOR_TEA_HEAD), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.SUNFLOWER_HEAD), () -> CapabilityProviderHeat.of(1, 480));

      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_BLACK_TEA), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_GREEN_TEA), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_WHITE_TEA), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_CANNABIS_BUD), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_CANNABIS_LEAF), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_COCA_LEAF), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_OPIUM_POPPY_BULB), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_PEYOTE), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_TOBACCO_LEAF), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.ROASTED_COFFEE_BEANS), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_CHAMOMILE_HEAD), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_DANDELION_HEAD), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_LABRADOR_TEA_HEAD), () -> CapabilityProviderHeat.of(1, 480));
      CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_SUNFLOWER_HEAD), () -> CapabilityProviderHeat.of(1, 480));
    }

  }
}
