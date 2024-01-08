package BananaFructa.floraefixes;

import com.eerussianguy.firmalife.init.RegistriesFL;
import com.eerussianguy.firmalife.recipe.PlanterRecipe;
import net.dries007.tfc.objects.blocks.stone.BlockFarmlandTFC;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.util.agriculture.Crop;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;
import su.terrafirmagreg.Tags;
import tfcflorae.objects.blocks.blocktype.farmland.FarmlandTFCF;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.objects.items.food.ItemFoodTFCF;
import tfcflorae.util.agriculture.CropTFCF;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Supplier;

import static net.minecraftforge.fml.common.eventhandler.EventPriority.LOW;
import static su.terrafirmagreg.Constants.*;

@Mod(modid = MODID_FF, name = FloraeFixes.name, version = Tags.VERSION, dependencies = "after:firmalife;after:tfcflorae")
public class FloraeFixes {
	public static final String name = "TFC Florae Fixes";

	public FloraeFixes() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static void removeRecipe(Item i) {
		ItemStack output = new ItemStack(i);
		ArrayList<PlanterRecipe> removeList = new ArrayList();
		RegistriesFL.PLANTER_QUAD.getValuesCollection().stream().filter((x) -> {
			return x.getOutputItem(ItemStack.EMPTY).isItemEqual(output);
		}).forEach(removeList::add);
		Iterator var2 = removeList.iterator();

		while (var2.hasNext()) {
			final PlanterRecipe recipe = (PlanterRecipe) var2.next();
			IForgeRegistryModifiable<PlanterRecipe> Planter = (IForgeRegistryModifiable) RegistriesFL.PLANTER_QUAD;
			Planter.remove(recipe.getRegistryName());
		}
	}

	public void fixCropItemSupplierTFCFlorae(Class<?> objType, Object obj, ItemFoodTFCF food) {
		Supplier<ItemStack> foodDrop = () -> {
			return new ItemStack(food);
		};
		Utils.writeDeclaredField(objType, obj, "foodDrop", foodDrop, true);
	}

	public void fixCropPartialItemSupplierTFCFlorae(Class<?> objType, Object obj, ItemFoodTFCF food) {
		Supplier<ItemStack> foodDrop = () -> {
			return new ItemStack(food);
		};
		Utils.writeDeclaredField(objType, obj, "foodDropEarly", foodDrop, true);
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Config.init(event.getModConfigurationDirectory());
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		if (!Config.cropSupplier) return;
		fixCropItemSupplierTFCFlorae(CropTFCF.AMARANTH.getClass(), CropTFCF.AMARANTH, ItemsTFCF.AMARANTH);
		fixCropItemSupplierTFCFlorae(CropTFCF.BUCKWHEAT.getClass(), CropTFCF.BUCKWHEAT, ItemsTFCF.BUCKWHEAT);
		fixCropItemSupplierTFCFlorae(CropTFCF.FONIO.getClass(), CropTFCF.FONIO, ItemsTFCF.FONIO);
		fixCropItemSupplierTFCFlorae(CropTFCF.MILLET.getClass(), CropTFCF.MILLET, ItemsTFCF.MILLET);
		fixCropItemSupplierTFCFlorae(CropTFCF.QUINOA.getClass(), CropTFCF.QUINOA, ItemsTFCF.QUINOA);
		fixCropItemSupplierTFCFlorae(CropTFCF.SPELT.getClass(), CropTFCF.SPELT, ItemsTFCF.SPELT);
		fixCropItemSupplierTFCFlorae(CropTFCF.BLACK_EYED_PEAS.getClass(), CropTFCF.BLACK_EYED_PEAS, ItemsTFCF.BLACK_EYED_PEAS);

		fixCropItemSupplierTFCFlorae(CropTFCF.CAYENNE_PEPPER.getClass(), CropTFCF.CAYENNE_PEPPER, ItemsTFCF.RED_CAYENNE_PEPPER);
		fixCropPartialItemSupplierTFCFlorae(CropTFCF.CAYENNE_PEPPER.getClass(), CropTFCF.CAYENNE_PEPPER, ItemsTFCF.GREEN_CAYENNE_PEPPER);

		fixCropItemSupplierTFCFlorae(CropTFCF.GINGER.getClass(), CropTFCF.GINGER, ItemsTFCF.GINGER);
		fixCropItemSupplierTFCFlorae(CropTFCF.GINSENG.getClass(), CropTFCF.GINSENG, ItemsTFCF.GINSENG);
		fixCropItemSupplierTFCFlorae(CropTFCF.RUTABAGA.getClass(), CropTFCF.RUTABAGA, ItemsTFCF.RUTABAGA);
		fixCropItemSupplierTFCFlorae(CropTFCF.TURNIP.getClass(), CropTFCF.TURNIP, ItemsTFCF.TURNIP);
		fixCropItemSupplierTFCFlorae(CropTFCF.SUGAR_BEET.getClass(), CropTFCF.SUGAR_BEET, ItemsTFCF.SUGAR_BEET);
		fixCropItemSupplierTFCFlorae(CropTFCF.PURPLE_GRAPE.getClass(), CropTFCF.PURPLE_GRAPE, ItemsTFCF.PURPLE_GRAPE);
		fixCropItemSupplierTFCFlorae(CropTFCF.GREEN_GRAPE.getClass(), CropTFCF.GREEN_GRAPE, ItemsTFCF.GREEN_GRAPE);
		fixCropItemSupplierTFCFlorae(CropTFCF.LIQUORICE_ROOT.getClass(), CropTFCF.LIQUORICE_ROOT, ItemsTFCF.LIQUORICE_ROOT);
	}

	@SubscribeEvent
	public void onBlockClick(PlayerInteractEvent.RightClickBlock event) {
		if (!Config.ricePlacing) return;
		Block b = event.getWorld().getBlockState(event.getPos()).getBlock();
		if (!event.getWorld().isRemote) {
			if (b instanceof FarmlandTFCF) {
				// if it's rice and the top block is not water
				if (ItemSeedsTFC.get(Crop.RICE) == event.getEntityPlayer()
				                                        .getHeldItem(EnumHand.MAIN_HAND)
				                                        .getItem() && event.getWorld()
				                                                           .getBlockState(event.getPos().up())
				                                                           .getMaterial() != Material.WATER) {
					event.setCanceled(true);
				}
			}
		}
	}

	/**
	 * Fixed TFCF rice placement
	 */
	@SubscribeEvent
	public void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
		if (!Config.ricePlacing) return;
		if (event.getItemStack().getItem() instanceof ItemSeedsTFC) {
			if (ItemSeedsTFC.get(Crop.RICE) == event.getItemStack().getItem()) {
				event.setCanceled(true);
				RayTraceResult result = Utils.rayTrace(event.getWorld(), event.getEntityPlayer(), false);
				if (result != null) {
					BlockPos pos = result.getBlockPos();
					Block down = event.getWorld().getBlockState(pos).getBlock();
					if ((down instanceof BlockFarmlandTFC || down instanceof FarmlandTFCF) && event.getWorld()
					                                                                               .getBlockState(pos.up())
					                                                                               .getBlock()
					                                                                               .getMaterial(null) == Material.WATER) {
						Utils.ricePlaceFixed(Crop.RICE, event.getItemStack()
						                                     .getItem(), event.getWorld(), event.getEntityPlayer(), event.getHand());
					}
				}
			}
		}
	}

	@SubscribeEvent(priority = LOW)
	public void onRecipeRegister(RegistryEvent.Register<IRecipe> event) {
		if (!Config.sandwichRecipes) return;
		String[] sandwiches = {
				"food/sandwich_slice/amaranth",
				"food/sandwich_slice/buckwheat",
				"food/sandwich_slice/fonio",
				"food/sandwich_slice/millet",
				"food/sandwich_slice/quinoa",
				"food/sandwich_slice/spelt",
				"food/sandwich_slice/wild_rice"
		};
		IForgeRegistryModifiable registry;
		registry = (IForgeRegistryModifiable) event.getRegistry();
		for (String s : sandwiches) {
			IRecipe recipe = (IRecipe) registry.getValue(new ResourceLocation(MODID_TFCF, s));
			if (recipe != null) {
				registry.remove(recipe.getRegistryName());
			}
		}
	}

	@SubscribeEvent
	public void onRegisterPlanterEvent(RegistryEvent.Register<PlanterRecipe> event) {
		if (!Config.planterRecipes) return;
		Item[] preAdded = new Item[]{
				ItemsTFCF.BLACK_EYED_PEAS,
				ItemsTFCF.RED_CAYENNE_PEPPER,
				ItemsTFCF.GINSENG,
				ItemsTFCF.RUTABAGA,
				ItemsTFCF.TURNIP,
				ItemsTFCF.SUGAR_BEET,
				ItemsTFCF.PURPLE_GRAPE,
				ItemsTFCF.GREEN_GRAPE,
				ItemsTFCF.LIQUORICE_ROOT,
				ItemsTFCF.COFFEA_CHERRIES,
				ItemsTFCF.AGAVE,
				ItemsTFCF.COCA_LEAF,
				ItemsTFCF.COTTON_BOLL,
				ItemsTFCF.FLAX,
				ItemsTFCF.HEMP,
				ItemsTFCF.HOPS,
				ItemsTFCF.INDIGO,
				ItemsTFCF.MADDER,
				ItemsTFCF.OPIUM_POPPY_BULB,
				ItemsTFCF.RAPE,
				ItemsTFCF.WELD,
				ItemsTFCF.WOAD,
				ItemsTFCF.TOBACCO_LEAF

		};
		for (Item s : preAdded) {
			removeRecipe(s);
		}
		IForgeRegistry<PlanterRecipe> r = event.getRegistry();
		registerPlanterRecipe(r, CropTFCF.AGAVE, ItemsTFCF.AGAVE, 5, false, "agave");
		registerPlanterRecipe(r, CropTFCF.AMARANTH, ItemsTFCF.AMARANTH, 7, true, "amaranth");
		registerPlanterRecipe(r, CropTFCF.BLACK_EYED_PEAS, ItemsTFCF.BLACK_EYED_PEAS, 6, true, "black_eyed_peas");
		registerPlanterRecipe(r, CropTFCF.BUCKWHEAT, ItemsTFCF.BUCKWHEAT, 7, true, "buckwheat");
		registerPlanterRecipe(r, CropTFCF.CAYENNE_PEPPER, ItemsTFCF.RED_CAYENNE_PEPPER, 6, true, "cayenne_pepper"); // check planter
		registerPlanterRecipe(r, CropTFCF.COCA, ItemsTFCF.COCA_LEAF, 5, true, "coca");
		registerPlanterRecipe(r, CropTFCF.COFFEA, ItemsTFCF.COFFEA_CHERRIES, 7, false, "coffea"); // dead missing texture
		registerPlanterRecipe(r, CropTFCF.COTTON, ItemsTFCF.COTTON_BOLL, 5, true, "cotton");
		registerPlanterRecipe(r, CropTFCF.FLAX, ItemsTFCF.FLAX, 6, true, "flax");
		registerPlanterRecipe(r, CropTFCF.FONIO, ItemsTFCF.FONIO, 7, true, "fonio");
		registerPlanterRecipe(r, CropTFCF.GINGER, ItemsTFCF.GINGER, 6, false, "ginger");
		registerPlanterRecipe(r, CropTFCF.GINSENG, ItemsTFCF.GINSENG, 4, false, "ginseng");
		registerPlanterRecipe(r, CropTFCF.GREEN_GRAPE, ItemsTFCF.GREEN_GRAPE, 8, true, "green_grape");
		registerPlanterRecipe(r, CropTFCF.HEMP, ItemsTFCF.HEMP, 4, true, "hemp");
		registerPlanterRecipe(r, CropTFCF.HOP, ItemsTFCF.HOPS, 5, true, "hop");
		registerPlanterRecipe(r, CropTFCF.INDIGO, ItemsTFCF.INDIGO, 5, true, "indigo");
		registerPlanterRecipe(r, CropTFCF.LIQUORICE_ROOT, ItemsTFCF.LIQUORICE_ROOT, 7, false, "liquorice_root");
		registerPlanterRecipe(r, CropTFCF.MADDER, ItemsTFCF.MADDER, 4, false, "madder");
		registerPlanterRecipe(r, CropTFCF.MILLET, ItemsTFCF.MILLET, 7, true, "millet");
		registerPlanterRecipe(r, CropTFCF.PURPLE_GRAPE, ItemsTFCF.PURPLE_GRAPE, 8, true, "purple_grape");
		registerPlanterRecipe(r, CropTFCF.QUINOA, ItemsTFCF.QUINOA, 7, true, "quinoa");
		registerPlanterRecipe(r, CropTFCF.RAPE, ItemsTFCF.RAPE, 5, false, "rape");
		registerPlanterRecipe(r, CropTFCF.RUTABAGA, ItemsTFCF.RUTABAGA, 6, false, "rutabaga");
		registerPlanterRecipe(r, CropTFCF.SPELT, ItemsTFCF.SPELT, 7, true, "spelt");
		registerPlanterRecipe(r, CropTFCF.SUGAR_BEET, ItemsTFCF.SUGAR_BEET, 6, true, "sugar_beet");
		registerPlanterRecipe(r, CropTFCF.TOBACCO, ItemsTFCF.TOBACCO_LEAF, 6, true, "tobacco");
		registerPlanterRecipe(r, CropTFCF.TURNIP, ItemsTFCF.TURNIP, 7, false, "turnip");
		registerPlanterRecipe(r, CropTFCF.WELD, ItemsTFCF.WELD, 4, false, "weld");
		registerPlanterRecipe(r, CropTFCF.WOAD, ItemsTFCF.WOAD, 5, false, "woad");
	}

	private void registerPlanterRecipe(IForgeRegistry<PlanterRecipe> r, CropTFCF crop, Item i, int s, boolean p, String name) {
		r.registerAll((PlanterRecipe) (new PlanterRecipe(IIngredient.of(ItemSeedsTFC.get(crop)), new ItemStack(i), s, p)).setRegistryName(MODID_FL, name));
	}

}
