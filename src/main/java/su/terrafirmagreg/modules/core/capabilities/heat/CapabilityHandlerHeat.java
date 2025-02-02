package su.terrafirmagreg.modules.core.capabilities.heat;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.firmalife.registry.ItemsFL;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.types.DefaultPlants;
import net.dries007.tfcflorae.objects.blocks.BlocksTFCF;
import net.dries007.tfcflorae.objects.blocks.wood.BlockLogTFCF;
import net.dries007.tfcflorae.objects.items.ItemsTFCF;
import net.dries007.tfcflorae.types.PlantsTFCF;
import net.dries007.tfcflorae.types.TreesTFCF;

import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public class CapabilityHandlerHeat {

  //Used inside CT, set custom IItemHeat for items outside TFC
  public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new Object2ObjectOpenHashMap<>();

  public static void init() {

    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsFL.HONEYCOMB), () -> new CapabilityProviderHeat(null, 1, 600));

    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.ARTISTS_CONK))), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.SULPHUR_SHELF))), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.TURKEY_TAIL))), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.PORCINI))), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.AMANITA))), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.BLACK_POWDERPUFF))), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.CHANTERELLE))), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.DEATH_CAP))), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.GIANT_CLUB))), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.PARASOL_MUSHROOM))), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.STINKHORN))), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WEEPING_MILK_CAP))), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WOOD_BLEWIT))), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.WOOLLY_GOMPHUS))), () -> new CapabilityProviderHeat(null, 1, 480));

    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(Items.CLAY_BALL), () -> new CapabilityProviderHeat(null, 1, 1599));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(BlockLogTFCF.get(TFCRegistries.TREES.getValue(TreesTFCF.EUCALYPTUS))), () -> new CapabilityProviderHeat(null, 1, 1599));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFC.STRAW), () -> new CapabilityProviderHeat(null, 1, 30));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(BlocksTFCF.TWIG), () -> new CapabilityProviderHeat(null, 1, 50));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(BlocksTFCF.DRIFTWOOD), () -> new CapabilityProviderHeat(null, 1, 60));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(BlocksTFCF.BONES), () -> new CapabilityProviderHeat(null, 1, 425));

    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.BLACK_TEA), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.GREEN_TEA), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.WHITE_TEA), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.CANNABIS_BUD), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.CANNABIS_LEAF), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.COCA_LEAF), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.OPIUM_POPPY_BULB), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.PEYOTE), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.TOBACCO_LEAF), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_COFFEA_CHERRIES), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.CHAMOMILE_HEAD), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DANDELION_HEAD), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.LABRADOR_TEA_HEAD), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.SUNFLOWER_HEAD), () -> new CapabilityProviderHeat(null, 1, 480));

    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_BLACK_TEA), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_GREEN_TEA), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_WHITE_TEA), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_CANNABIS_BUD), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_CANNABIS_LEAF), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_COCA_LEAF), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_OPIUM_POPPY_BULB), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_PEYOTE), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_TOBACCO_LEAF), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.ROASTED_COFFEE_BEANS), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_CHAMOMILE_HEAD), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_DANDELION_HEAD), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_LABRADOR_TEA_HEAD), () -> new CapabilityProviderHeat(null, 1, 480));
    CapabilityHandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsTFCF.DRIED_SUNFLOWER_HEAD), () -> new CapabilityProviderHeat(null, 1, 480));
  }

  @Nullable
  public static ICapabilityProvider getCustom(ItemStack stack) {
    for (var entry : CUSTOM_ITEMS.entrySet()) {
      if (entry.getKey().testIgnoreCount(stack)) {
        return entry.getValue().get();
      }
    }
    return null;
  }
}
