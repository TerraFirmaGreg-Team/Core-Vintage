/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.blocks;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Materials;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.api.types2.rock.RockBlockType;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.types2.soil.SoilType;
import net.dries007.tfc.api.types2.soil.SoilVariant;
import net.dries007.tfc.api.types2.rock.util.IRockTypeBlock;
import net.dries007.tfc.api.types2.soil.util.ISoilTypeBlock;
import net.dries007.tfc.compat.gregtech.material.TFGMaterialFlags;
import net.dries007.tfc.objects.blocks.agriculture.*;
import net.dries007.tfc.objects.blocks.devices.*;
import net.dries007.tfc.objects.blocks.metal.*;
import net.dries007.tfc.objects.blocks.plants.BlockFloatingWaterTFC;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.blocks.soil.BlockPeat;
import net.dries007.tfc.objects.blocks.soil.BlockPeatGrass;
import net.dries007.tfc.objects.blocks.wood.*;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import net.dries007.tfc.objects.items.itemblock.*;
import net.dries007.tfc.objects.te.*;
import net.dries007.tfc.util.agriculture.BerryBush;
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.agriculture.FruitTree;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGravel;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types2.rock.RockVariant.GRAVEL;
import static net.dries007.tfc.api.types2.rock.RockVariant.RAW;
import static net.dries007.tfc.api.types2.soil.SoilVariant.DIRT;
import static net.dries007.tfc.api.types2.soil.SoilVariant.DRY_GRASS;
import static net.dries007.tfc.objects.CreativeTabsTFC.*;
import static net.dries007.tfc.objects.blocks.soil.BlockSoil.BLOCK_SOIL_MAP;
import static net.dries007.tfc.util.Helpers.getNull;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MOD_ID)
@GameRegistry.ObjectHolder(MOD_ID)
public final class BlocksTFC {


	@GameRegistry.ObjectHolder("ceramics/fired/large_vessel")
	public static final BlockLargeVessel FIRED_LARGE_VESSEL = getNull();

	@GameRegistry.ObjectHolder("alabaster/bricks/plain")
	public static final BlockDecorativeStone ALABASTER_BRICKS_PLAIN = getNull();
	@GameRegistry.ObjectHolder("alabaster/polished/plain")
	public static final BlockDecorativeStone ALABASTER_POLISHED_PLAIN = getNull();
	@GameRegistry.ObjectHolder("alabaster/raw/plain")
	public static final BlockDecorativeStone ALABASTER_RAW_PLAIN = getNull();

	public static final BlockDebug DEBUG = getNull();
	public static final BlockPeat PEAT = getNull();
	public static final BlockPeat PEAT_GRASS = getNull();
	public static final BlockFirePit FIREPIT = getNull();
	public static final BlockThatch THATCH = getNull();
	public static final BlockThatchBed THATCH_BED = getNull();
	public static final BlockPitKiln PIT_KILN = getNull();
	public static final BlockPlacedItemFlat PLACED_ITEM_FLAT = getNull();
	public static final BlockPlacedItem PLACED_ITEM = getNull();
	public static final BlockPlacedHide PLACED_HIDE = getNull();
	public static final BlockCharcoalPile CHARCOAL_PILE = getNull();
	public static final BlockNestBox NEST_BOX = getNull();
	public static final BlockLogPile LOG_PILE = getNull();
	public static final BlockIngotPile INGOT_PILE = getNull();
	public static final BlockCharcoalForge CHARCOAL_FORGE = getNull();
	public static final BlockCrucible CRUCIBLE = getNull();
	public static final BlockMolten MOLTEN = getNull();
	public static final BlockBlastFurnace BLAST_FURNACE = getNull();
	public static final BlockBloom BLOOM = getNull();
	public static final BlockBloomery BLOOMERY = getNull();
	public static final BlockQuern QUERN = getNull();
	public static final BlockIceTFC SEA_ICE = getNull();
	public static final BlockPowderKeg POWDERKEG = getNull();
	public static final BlockGravel AGGREGATE = getNull();
	public static final Block FIRE_BRICKS = getNull();
	// All these are for use in model registration. Do not use for block lookups.
	// Use the static get methods in the classes instead.
	private static ImmutableList<ItemBlock> allNormalItemBlocks;
	private static ImmutableList<ItemBlock> allInventoryItemBlocks;
	private static ImmutableList<ItemBlockBarrel> allBarrelItemBlocks;
	private static ImmutableList<BlockFluidBase> allFluidBlocks;
	private static ImmutableList<BlockLogTFC> allLogBlocks;
	private static ImmutableList<BlockLeavesTFC> allLeafBlocks;
	private static ImmutableList<BlockFenceGateTFC> allFenceGateBlocks;
	private static ImmutableList<BlockSaplingTFC> allSaplingBlocks;
	private static ImmutableList<BlockDoorTFC> allDoorBlocks;
	private static ImmutableList<BlockTrapDoorWoodTFC> allTrapDoorWoodBlocks;
	private static ImmutableList<BlockTrapDoorMetalTFC> allTrapDoorMetalBlocks;
	private static ImmutableList<BlockStairsTFC> allStairsBlocks;
	private static ImmutableList<BlockWoodSlabTFC.Half> allSlabBlocks;
	private static ImmutableList<BlockChestTFC> allChestBlocks;
	private static ImmutableList<BlockAnvilTFC> allAnvils;
	private static ImmutableList<BlockMetalCladding> allCladdings;
	private static ImmutableList<BlockMetalLamp> allLamps;
	private static ImmutableList<BlockToolRack> allToolRackBlocks;
	private static ImmutableList<BlockCropTFC> allCropBlocks;
	private static ImmutableList<BlockCropDead> allDeadCropBlocks;
	private static ImmutableList<BlockPlantTFC> allPlantBlocks;
	private static ImmutableList<BlockPlantTFC> allGrassBlocks;
	private static ImmutableList<BlockLoom> allLoomBlocks;
	private static ImmutableList<BlockSupport> allSupportBlocks;
	private static ImmutableList<BlockFlowerPotTFC> allFlowerPots;
	private static ImmutableList<BlockFruitTreeSapling> allFruitTreeSaplingBlocks;
	private static ImmutableList<BlockFruitTreeTrunk> allFruitTreeTrunkBlocks;
	private static ImmutableList<BlockFruitTreeBranch> allFruitTreeBranchBlocks;
	private static ImmutableList<BlockFruitTreeLeaves> allFruitTreeLeavesBlocks;
	private static ImmutableList<BlockBerryBush> allBerryBushBlocks;

	public static ImmutableList<ItemBlock> getAllNormalItemBlocks() {
		return allNormalItemBlocks;
	}

	public static ImmutableList<ItemBlock> getAllInventoryItemBlocks() {
		return allInventoryItemBlocks;
	}

	public static ImmutableList<ItemBlockBarrel> getAllBarrelItemBlocks() {
		return allBarrelItemBlocks;
	}

	public static ImmutableList<BlockFluidBase> getAllFluidBlocks() {
		return allFluidBlocks;
	}

	public static ImmutableList<BlockLogTFC> getAllLogBlocks() {
		return allLogBlocks;
	}

	public static ImmutableList<BlockLeavesTFC> getAllLeafBlocks() {
		return allLeafBlocks;
	}

	public static ImmutableList<BlockFenceGateTFC> getAllFenceGateBlocks() {
		return allFenceGateBlocks;
	}

	public static ImmutableList<BlockSaplingTFC> getAllSaplingBlocks() {
		return allSaplingBlocks;
	}

	public static ImmutableList<BlockDoorTFC> getAllDoorBlocks() {
		return allDoorBlocks;
	}

	public static ImmutableList<BlockTrapDoorWoodTFC> getAllTrapDoorWoodBlocks() {
		return allTrapDoorWoodBlocks;
	}

	public static ImmutableList<BlockTrapDoorMetalTFC> getAllTrapDoorMetalBlocks() {
		return allTrapDoorMetalBlocks;
	}

	public static ImmutableList<BlockStairsTFC> getAllStairsBlocks() {
		return allStairsBlocks;
	}

	public static ImmutableList<BlockWoodSlabTFC.Half> getAllSlabBlocks() {
		return allSlabBlocks;
	}

	public static ImmutableList<BlockChestTFC> getAllChestBlocks() {
		return allChestBlocks;
	}

	public static ImmutableList<BlockAnvilTFC> getAllAnvils() {
		return allAnvils;
	}

	public static ImmutableList<BlockMetalCladding> getAllCladdings() {
		return allCladdings;
	}

	public static ImmutableList<BlockMetalLamp> getAllLamps() {
		return allLamps;
	}

	public static ImmutableList<BlockToolRack> getAllToolRackBlocks() {
		return allToolRackBlocks;
	}

	public static ImmutableList<BlockCropTFC> getAllCropBlocks() {
		return allCropBlocks;
	}

	public static ImmutableList<BlockCropDead> getAllDeadCropBlocks() {
		return allDeadCropBlocks;
	}

	public static ImmutableList<BlockPlantTFC> getAllPlantBlocks() {
		return allPlantBlocks;
	}

	public static ImmutableList<BlockPlantTFC> getAllGrassBlocks() {
		return allGrassBlocks;
	}

	public static ImmutableList<BlockLoom> getAllLoomBlocks() {
		return allLoomBlocks;
	}

	public static ImmutableList<BlockSupport> getAllSupportBlocks() {
		return allSupportBlocks;
	}

	public static ImmutableList<BlockFlowerPotTFC> getAllFlowerPots() {
		return allFlowerPots;
	}

	public static ImmutableList<BlockFruitTreeSapling> getAllFruitTreeSaplingBlocks() {
		return allFruitTreeSaplingBlocks;
	}

	public static ImmutableList<BlockFruitTreeTrunk> getAllFruitTreeTrunkBlocks() {
		return allFruitTreeTrunkBlocks;
	}

	public static ImmutableList<BlockFruitTreeBranch> getAllFruitTreeBranchBlocks() {
		return allFruitTreeBranchBlocks;
	}

	public static ImmutableList<BlockFruitTreeLeaves> getAllFruitTreeLeavesBlocks() {
		return allFruitTreeLeavesBlocks;
	}

	public static ImmutableList<BlockBerryBush> getAllBerryBushBlocks() {
		return allBerryBushBlocks;
	}

	@SubscribeEvent
	@SuppressWarnings("ConstantConditions")
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		// This is called here because it needs to wait until Metal registry has fired
		FluidsTFC.registerFluids();

		IForgeRegistry<Block> r = event.getRegistry();

		Builder<ItemBlock> normalItemBlocks = ImmutableList.builder();
		Builder<ItemBlock> inventoryItemBlocks = ImmutableList.builder();


		//=== Rock ===================================================================================================//

		for (RockType rockType : RockType.values()) {
			for (RockBlockType rockBlockType : RockBlockType.values()) {
				for (RockVariant rockVariant : rockBlockType.getRockVariants()) {
					Block block = (Block) rockBlockType.createBlockType(rockVariant, rockType);

					TerraFirmaCraft.getLog().debug("Registering block: {}", block.getRegistryName());
					r.register(block);
				}
			}
		}
		for (IRockTypeBlock stoneTypeBlock : TFCStorage.ROCK_BLOCKS.values()) {
			ItemBlock itemBlock = stoneTypeBlock.getItemBlock();
			if (itemBlock != null)
				normalItemBlocks.add(itemBlock);
		}

		//=== Soil ===================================================================================================//

		for (SoilType soilType : SoilType.values()) {
			for (SoilVariant soilVariant : SoilVariant.values()) {
				Block block = (Block) soilVariant.createBlock(soilType);

				TerraFirmaCraft.getLog().debug("Registering block: {}", block.getRegistryName());
				r.register(block);
			}
		}
		for (ISoilTypeBlock soilTypeBlock : BLOCK_SOIL_MAP.values()) {
			ItemBlock itemBlock = soilTypeBlock.getItemBlock();
			if (itemBlock != null)
				normalItemBlocks.add(itemBlock);
		}

		//=== Other ==================================================================================================//

		normalItemBlocks.add(new ItemBlockTFC(register(r, "debug", new BlockDebug(), MISC)));

		normalItemBlocks.add(new ItemBlockTFC(register(r, "aggregate", new BlockAggregate(), ROCK_STUFFS)));
		normalItemBlocks.add(new ItemBlockTFC(register(r, "fire_clay_block", new BlockFireClay(), ROCK_STUFFS)));

		normalItemBlocks.add(new ItemBlockTFC(register(r, "peat", new BlockPeat(Material.GROUND), EARTH)));
		normalItemBlocks.add(new ItemBlockTFC(register(r, "peat_grass", new BlockPeatGrass(Material.GRASS), EARTH)));

		normalItemBlocks.add(new ItemBlockTFC(register(r, "thatch", new BlockThatch(), DECORATIONS)));
		normalItemBlocks.add(new ItemBlockTFC(register(r, "fire_bricks", new BlockFireBrick(), DECORATIONS)));

		normalItemBlocks.add(new ItemBlockTFC(register(r, "quern", new BlockQuern(), MISC)));
		normalItemBlocks.add(new ItemBlockCrucible(register(r, "crucible", new BlockCrucible(), MISC)));
		normalItemBlocks.add(new ItemBlockTFC(register(r, "blast_furnace", new BlockBlastFurnace(), MISC)));
		inventoryItemBlocks.add(new ItemBlockTFC(register(r, "bellows", new BlockBellows(), MISC)));
		inventoryItemBlocks.add(new ItemBlockTFC(register(r, "bloomery", new BlockBloomery(), MISC)));
		inventoryItemBlocks.add(new ItemBlockTFC(register(r, "nest_box", new BlockNestBox(), MISC)));
		inventoryItemBlocks.add(new ItemBlockSluice(register(r, "sluice", new BlockSluice(), MISC)));

		normalItemBlocks.add(new ItemBlockTFC(register(r, "sea_ice", new BlockIceTFC(FluidsTFC.SALT_WATER.get()), MISC)));

		normalItemBlocks.add(new ItemBlockLargeVessel(register(r, "ceramics/fired/large_vessel", new BlockLargeVessel(), POTTERY)));
		normalItemBlocks.add(new ItemBlockPowderKeg(register(r, "powderkeg", new BlockPowderKeg(), WOOD)));

		normalItemBlocks.add(new ItemBlockTFC(register(r, "alabaster/raw/plain", new BlockDecorativeStone(MapColor.SNOW), DECORATIONS)));
		normalItemBlocks.add(new ItemBlockTFC(register(r, "alabaster/polished/plain", new BlockDecorativeStone(MapColor.SNOW), DECORATIONS)));
		normalItemBlocks.add(new ItemBlockTFC(register(r, "alabaster/bricks/plain", new BlockDecorativeStone(MapColor.SNOW), DECORATIONS)));

		for (EnumDyeColor dyeColor : EnumDyeColor.values()) {
			BlockDecorativeStone polished = new BlockDecorativeStone(MapColor.getBlockColor(dyeColor));
			BlockDecorativeStone bricks = new BlockDecorativeStone(MapColor.getBlockColor(dyeColor));
			BlockDecorativeStone raw = new BlockDecorativeStone(MapColor.getBlockColor(dyeColor));

			normalItemBlocks.add(new ItemBlockTFC(register(r, "alabaster/polished/" + dyeColor.getName(), polished, DECORATIONS)));
			normalItemBlocks.add(new ItemBlockTFC(register(r, "alabaster/bricks/" + dyeColor.getName(), bricks, DECORATIONS)));
			normalItemBlocks.add(new ItemBlockTFC(register(r, "alabaster/raw/" + dyeColor.getName(), raw, DECORATIONS)));

			BlockDecorativeStone.ALABASTER_POLISHED.put(dyeColor, polished);
			BlockDecorativeStone.ALABASTER_BRICKS.put(dyeColor, bricks);
			BlockDecorativeStone.ALABASTER_RAW.put(dyeColor, raw);
		}

		{
			// Apparently this is the way we're supposed to do things even though the fluid registry defaults. So we'll do it this way.
			Builder<BlockFluidBase> b = ImmutableList.builder();
			b.add(
					register(r, "fluid/hot_water", new BlockFluidHotWater()),
					register(r, "fluid/fresh_water", new BlockFluidWater(FluidsTFC.FRESH_WATER.get(), Material.WATER, false)),
					register(r, "fluid/salt_water", new BlockFluidWater(FluidsTFC.SALT_WATER.get(), Material.WATER, true))
			);
			for (FluidWrapper wrapper : FluidsTFC.getAllAlcoholsFluids()) {
				b.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER)));
			}
			for (FluidWrapper wrapper : FluidsTFC.getAllOtherFiniteFluids()) {
				b.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER)));
			}
			for (FluidWrapper wrapper : FluidsTFC.getAllMetalFluids()) {
				b.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.LAVA)));
			}
			for (EnumDyeColor color : EnumDyeColor.values()) {
				FluidWrapper wrapper = FluidsTFC.getFluidFromDye(color);
				b.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER)));
			}
			allFluidBlocks = b.build();
		}

//		{
//			// Add resultingState to the registered collapsable blocks.
//			for (Rock rock : TFCRegistries.ROCKS.getValuesCollection()) {
//				for (Rock.Type type : Rock.Type.values()) {
//					FallingBlockManager.Specification spec = type.getFallingSpecification();
//					switch (type) {
//						case ANVIL:
//							if (!rock.getRockCategory().hasAnvil()) {
//								break;
//							}
//						case RAW:
//							spec = new FallingBlockManager.Specification(spec);
//							spec.setResultingState(BlockRockVariant.get(rock, COBBLE).getDefaultState());
//							FallingBlockManager.registerFallable(BlockRockVariant.get(rock, RAW), spec);
//							break;
//						case SMOOTH:
//							spec = new FallingBlockManager.Specification(spec);
//							spec.setResultingState(BlockRockVariant.get(rock, COBBLE).getDefaultState());
//							FallingBlockManager.registerFallable(BlockRockVariant.get(rock, SMOOTH).getDefaultState().withProperty(BlockRockSmooth.CAN_FALL, true), spec);
//							break;
//						default:
//							Rock.Type nonGrassType = type.getNonGrassVersion();
//							if (nonGrassType != type) {
//								spec = new FallingBlockManager.Specification(spec);
//								spec.setResultingState(BlockRockVariant.get(rock, nonGrassType).getDefaultState());
//							}
//							FallingBlockManager.registerFallable(BlockRockVariant.get(rock, type), spec);
//					}
//				}
//			}
//		}

		{
			Builder<BlockLogTFC> logs = ImmutableList.builder();
			Builder<BlockLeavesTFC> leaves = ImmutableList.builder();
			Builder<BlockFenceGateTFC> fenceGates = ImmutableList.builder();
			Builder<BlockSaplingTFC> saplings = ImmutableList.builder();
			Builder<BlockDoorTFC> doors = ImmutableList.builder();
			Builder<BlockTrapDoorWoodTFC> trapDoors = ImmutableList.builder();
			Builder<BlockChestTFC> chests = ImmutableList.builder();
			Builder<BlockToolRack> toolRacks = ImmutableList.builder();
			Builder<ItemBlockBarrel> barrelItems = ImmutableList.builder();
			Builder<BlockPlantTFC> plants = ImmutableList.builder();
			Builder<BlockLoom> looms = ImmutableList.builder();
			Builder<BlockSupport> supports = ImmutableList.builder();

			// This loop is split up to organize the ordering of the creative tab
			// Do not optimize these loops back together
			// All bookshelves + item blocks
			for (Tree wood : TFCRegistries.TREES.getValuesCollection())
				normalItemBlocks.add(new ItemBlockTFC(register(r, "wood/bookshelf/" + wood.getRegistryName().getPath(), new BlockBookshelfTFC(wood), DECORATIONS)));
			// All workbenches + item blocks
			for (Tree wood : TFCRegistries.TREES.getValuesCollection())
				normalItemBlocks.add(new ItemBlockTFC(register(r, "wood/workbench/" + wood.getRegistryName().getPath(), new BlockWorkbenchTFC(wood), DECORATIONS)));
			// All fences + item blocks
			for (Tree wood : TFCRegistries.TREES.getValuesCollection())
				inventoryItemBlocks.add(new ItemBlockTFC(register(r, "wood/fence/" + wood.getRegistryName().getPath(), new BlockFenceTFC(wood), DECORATIONS)));
			// All buttons + item blocks
			for (Tree wood : TFCRegistries.TREES.getValuesCollection())
				inventoryItemBlocks.add(new ItemBlockTFC(register(r, "wood/button/" + wood.getRegistryName().getPath(), new BlockButtonWoodTFC(wood), DECORATIONS)));
			// All pressure plates + item blocks
			for (Tree wood : TFCRegistries.TREES.getValuesCollection())
				inventoryItemBlocks.add(new ItemBlockTFC(register(r, "wood/pressure_plate/" + wood.getRegistryName().getPath().toLowerCase(), new BlockWoodPressurePlateTFC(wood), DECORATIONS)));

			// Other blocks that don't have specific order requirements
			for (Tree wood : TFCRegistries.TREES.getValuesCollection()) {
				// Only block in the decorations category
				normalItemBlocks.add(new ItemBlockTFC(register(r, "wood/planks/" + wood.getRegistryName().getPath(), new BlockPlanksTFC(wood), WOOD)));
				// Blocks with specific block collections don't matter
				logs.add(register(r, "wood/log/" + wood.getRegistryName().getPath(), new BlockLogTFC(wood), WOOD));
				leaves.add(register(r, "wood/leaves/" + wood.getRegistryName().getPath(), new BlockLeavesTFC(wood), WOOD));
				fenceGates.add(register(r, "wood/fence_gate/" + wood.getRegistryName().getPath(), new BlockFenceGateTFC(wood), DECORATIONS));
				saplings.add(register(r, "wood/sapling/" + wood.getRegistryName().getPath(), new BlockSaplingTFC(wood), WOOD));
				doors.add(register(r, "wood/door/" + wood.getRegistryName().getPath(), new BlockDoorTFC(wood), DECORATIONS));
				trapDoors.add(register(r, "wood/trapdoor/" + wood.getRegistryName().getPath(), new BlockTrapDoorWoodTFC(wood), DECORATIONS));
				chests.add(register(r, "wood/chest/" + wood.getRegistryName().getPath(), new BlockChestTFC(BlockChestTFC.TFCBASIC, wood), DECORATIONS));
				chests.add(register(r, "wood/chest_trap/" + wood.getRegistryName().getPath(), new BlockChestTFC(BlockChestTFC.TFCTRAP, wood), DECORATIONS));

				toolRacks.add(register(r, "wood/tool_rack/" + wood.getRegistryName().getPath(), new BlockToolRack(wood), DECORATIONS));
				barrelItems.add(new ItemBlockBarrel(register(r, "wood/barrel/" + wood.getRegistryName().getPath(), new BlockBarrel(), DECORATIONS)));

				looms.add(register(r, "wood/loom/" + wood.getRegistryName().getPath(), new BlockLoom(wood), WOOD));
				supports.add(register(r, "wood/support/" + wood.getRegistryName().getPath(), new BlockSupport(wood), WOOD));
			}

			allLogBlocks = logs.build();
			allLeafBlocks = leaves.build();
			allFenceGateBlocks = fenceGates.build();
			allSaplingBlocks = saplings.build();
			allDoorBlocks = doors.build();
			allTrapDoorWoodBlocks = trapDoors.build();
			allChestBlocks = chests.build();
			allToolRackBlocks = toolRacks.build();
			allLoomBlocks = looms.build();
			allSupportBlocks = supports.build();

			allBarrelItemBlocks = barrelItems.build();

			//logs are special
			allLeafBlocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
			allFenceGateBlocks.forEach(x -> inventoryItemBlocks.add(new ItemBlockTFC(x)));

			allSaplingBlocks.forEach(x -> inventoryItemBlocks.add(new ItemBlockSaplingTFC(x)));

			// doors are special
			allTrapDoorWoodBlocks.forEach(x -> inventoryItemBlocks.add(new ItemBlockTFC(x)));
			allChestBlocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
			allToolRackBlocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
			allLoomBlocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
			allSupportBlocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
		}

		{
			Builder<BlockStairsTFC> stairs = new Builder<>();
			Builder<BlockWoodSlabTFC.Half> slab = new Builder<>();

			for (Tree wood : TFCRegistries.TREES.getValuesCollection())
				stairs.add(register(r, "stairs/wood/" + wood.getRegistryName().getPath(), new BlockStairsTFC(wood), DECORATIONS));

			// Full slabs are the same as full blocks, they are not saved to a list, they are kept track of by the halfslab version.
			for (Tree wood : TFCRegistries.TREES.getValuesCollection())
				register(r, "double_slab/wood/" + wood.getRegistryName().getPath(), new BlockWoodSlabTFC.Double(wood));

			// Slabs
			for (Tree wood : TFCRegistries.TREES.getValuesCollection())
				slab.add(register(r, "slab/wood/" + wood.getRegistryName().getPath(), new BlockWoodSlabTFC.Half(wood), DECORATIONS));


			allStairsBlocks = stairs.build();
			allSlabBlocks = slab.build();
			allStairsBlocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
		}

		{
			Builder<BlockAnvilTFC> anvils = ImmutableList.builder();
			Builder<BlockMetalCladding> claddings = ImmutableList.builder();
			Builder<BlockMetalLamp> lamps = ImmutableList.builder();
			Builder<BlockTrapDoorMetalTFC> metalTrapdoors = ImmutableList.builder();

			for (var material : GregTechAPI.materialManager.getRegistry("gregtech")) {
				if (material.hasFlag(TFGMaterialFlags.GENERATE_ANVIL))
					anvils.add(register(r, "anvil/" + material.getName(), new BlockAnvilTFC(material), METAL));

				if (material == Materials.Iron)
					claddings.add(register(r, "cladding/" + material.getName(), new BlockMetalCladding(material), METAL));
			}
            /*
			for (Metal metal : TFCRegistries.METALS.getValuesCollection()) {
				if (Metal.ItemType.ANVIL.hasType(metal))

				if (Metal.ItemType.SHEET.hasType(metal)) {
					sheets.add(register(r, "sheet/" + metal.getRegistryName().getPath(), new BlockMetalSheet(metal), METAL));
					metalTrapdoors.add(register(r, "trapdoor/" + metal.getRegistryName().getPath(), new BlockTrapDoorMetalTFC(metal), METAL));
				}
				if (Metal.ItemType.LAMP.hasType(metal))
					lamps.add(register(r, "lamp/" + metal.getRegistryName().getPath(), new BlockMetalLamp(metal), METAL));

			}*/

			allAnvils = anvils.build();
			allCladdings = claddings.build();
			allLamps = lamps.build();
			allTrapDoorMetalBlocks = metalTrapdoors.build();
		}

		{
			Builder<BlockCropTFC> b = ImmutableList.builder();

			for (Crop crop : Crop.values()) {
				b.add(register(r, "crop/" + crop.name().toLowerCase(), crop.createGrowingBlock()));
			}

			allCropBlocks = b.build();
		}

		{
			Builder<BlockCropDead> b = ImmutableList.builder();

			for (Crop crop : Crop.values()) {
				b.add(register(r, "dead_crop/" + crop.name().toLowerCase(), crop.createDeadBlock()));
			}

			allDeadCropBlocks = b.build();
		}

		{
			Builder<BlockFruitTreeSapling> fSaplings = ImmutableList.builder();
			Builder<BlockFruitTreeTrunk> fTrunks = ImmutableList.builder();
			Builder<BlockFruitTreeBranch> fBranches = ImmutableList.builder();
			Builder<BlockFruitTreeLeaves> fLeaves = ImmutableList.builder();

			for (FruitTree tree : FruitTree.values()) {
				fSaplings.add(register(r, "fruit_trees/sapling/" + tree.name().toLowerCase(), new BlockFruitTreeSapling(tree), WOOD));
				fTrunks.add(register(r, "fruit_trees/trunk/" + tree.name().toLowerCase(), new BlockFruitTreeTrunk(tree)));
				fBranches.add(register(r, "fruit_trees/branch/" + tree.name().toLowerCase(), new BlockFruitTreeBranch(tree)));
				fLeaves.add(register(r, "fruit_trees/leaves/" + tree.name().toLowerCase(), new BlockFruitTreeLeaves(tree), WOOD));
			}

			allFruitTreeSaplingBlocks = fSaplings.build();
			allFruitTreeTrunkBlocks = fTrunks.build();
			allFruitTreeBranchBlocks = fBranches.build();
			allFruitTreeLeavesBlocks = fLeaves.build();

			Builder<BlockBerryBush> fBerry = ImmutableList.builder();

			for (BerryBush bush : BerryBush.values()) {
				fBerry.add(register(r, "berry_bush/" + bush.name().toLowerCase(), new BlockBerryBush(bush), FOOD));
			}

			allBerryBushBlocks = fBerry.build();

			// Add ItemBlocks
			allFruitTreeSaplingBlocks.forEach(x -> inventoryItemBlocks.add(new ItemBlockTFC(x)));
			allFruitTreeLeavesBlocks.forEach(x -> inventoryItemBlocks.add(new ItemBlockTFC(x)));
			allBerryBushBlocks.forEach(x -> inventoryItemBlocks.add(new ItemBlockTFC(x)));
		}

		{

			Builder<BlockPlantTFC> b = ImmutableList.builder();
			Builder<BlockFlowerPotTFC> pots = ImmutableList.builder();
			for (Plant plant : TFCRegistries.PLANTS.getValuesCollection()) {
				if (plant.getPlantType() != Plant.PlantType.SHORT_GRASS && plant.getPlantType() != Plant.PlantType.TALL_GRASS)
					b.add(register(r, "plants/" + plant.getRegistryName().getPath(), plant.getPlantType().create(plant), FLORA));
				if (plant.canBePotted())
					pots.add(register(r, "flowerpot/" + plant.getRegistryName().getPath(), new BlockFlowerPotTFC(plant)));
			}
			allPlantBlocks = b.build();
			allFlowerPots = pots.build();

			for (BlockPlantTFC blockPlant : allPlantBlocks) {
				if (blockPlant instanceof BlockFloatingWaterTFC) {
					inventoryItemBlocks.add(new ItemBlockFloatingWaterTFC((BlockFloatingWaterTFC) blockPlant));
				} else if (blockPlant.getPlant().canBePotted()) {
					normalItemBlocks.add(new ItemBlockPlant(blockPlant, blockPlant.getPlant()));
				} else {
					normalItemBlocks.add(new ItemBlockTFC(blockPlant));
				}
			}
		}

		{
			Builder<BlockPlantTFC> b = ImmutableList.builder();
			for (Plant plant : TFCRegistries.PLANTS.getValuesCollection()) {
				if (plant.getPlantType() == Plant.PlantType.SHORT_GRASS || plant.getPlantType() == Plant.PlantType.TALL_GRASS)
					b.add(register(r, "plants/" + plant.getRegistryName().getPath(), plant.getPlantType().create(plant), FLORA));
			}
			allGrassBlocks = b.build();
			for (BlockPlantTFC blockPlant : allGrassBlocks) {
				normalItemBlocks.add(new ItemBlockTFC(blockPlant));
			}
		}

		// Registering JEI only blocks (for info)
		inventoryItemBlocks.add(new ItemBlock(register(r, "firepit", new BlockFirePit())));
		inventoryItemBlocks.add(new ItemBlock(register(r, "charcoal_forge", new BlockCharcoalForge())));
		inventoryItemBlocks.add(new ItemBlock(register(r, "pit_kiln", new BlockPitKiln())));
		inventoryItemBlocks.add(new ItemBlock(register(r, "placed_item", new BlockPlacedItem())));
		// technical blocks
		// These have no ItemBlock or Creative Tab
		register(r, "placed_item_flat", new BlockPlacedItemFlat());
		register(r, "placed_hide", new BlockPlacedHide());
		register(r, "charcoal_pile", new BlockCharcoalPile());
		register(r, "ingot_pile", new BlockIngotPile());
		register(r, "log_pile", new BlockLogPile());
		register(r, "molten", new BlockMolten());
		register(r, "bloom", new BlockBloom());
		register(r, "thatch_bed", new BlockThatchBed());

		// Note: if you add blocks you don't need to put them in this list of todos. Feel free to add them where they make sense :)

		// todo: smoke rack (placed with any string, so event based?) + smoke blocks or will we use particles?

		allNormalItemBlocks = normalItemBlocks.build();
		allInventoryItemBlocks = inventoryItemBlocks.build();

		// Register Tile Entities
		// Putting tile entity registration in the respective block can call it multiple times. Just put here to avoid duplicates

		register(TETickCounter.class, "tick_counter");
		register(TEPlacedItem.class, "placed_item");
		register(TEPlacedItemFlat.class, "placed_item_flat");
		register(TEPlacedHide.class, "placed_hide");
		register(TEPitKiln.class, "pit_kiln");
		register(TEChestTFC.class, "chest");
		register(TENestBox.class, "nest_box");
		register(TELogPile.class, "log_pile");
		register(TEIngotPile.class, "ingot_pile");
		register(TEFirePit.class, "fire_pit");
		register(TEToolRack.class, "tool_rack");
		register(TELoom.class, "loom");
		register(TELamp.class, "lamp");
		register(TEBellows.class, "bellows");
		register(TEBarrel.class, "barrel");
		register(TECharcoalForge.class, "charcoal_forge");
		register(TEAnvilTFC.class, "anvil");
		register(TECrucible.class, "crucible");
		register(TECropBase.class, "crop_base");
		register(TECropSpreading.class, "crop_spreading");
		register(TEBlastFurnace.class, "blast_furnace");
		register(TEBloomery.class, "bloomery");
		register(TEBloom.class, "bloom");
		register(TEMetalSheet.class, "metal_sheet");
		register(TEQuern.class, "quern");
		register(TELargeVessel.class, "large_vessel");
		register(TEPowderKeg.class, "powderkeg");
		register(TESluice.class, "sluice");
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void registerVanillaOverrides(RegistryEvent.Register<Block> event) {
		// Vanilla Overrides. Used for small tweaks on vanilla items, rather than replacing them outright
		if (ConfigTFC.General.OVERRIDES.enableFrozenOverrides) {
			TerraFirmaCraft.getLog().info("The below warnings about unintended overrides are normal. The override is intended. ;)");
			event.getRegistry().registerAll(
					new BlockIceTFC(FluidsTFC.FRESH_WATER.get()).setRegistryName("minecraft", "ice").setTranslationKey("ice"),
					new BlockSnowTFC().setRegistryName("minecraft", "snow_layer").setTranslationKey("snow")
			);
		}

		if (ConfigTFC.General.OVERRIDES.enableTorchOverride) {
			event.getRegistry().register(new BlockTorchTFC().setRegistryName("minecraft", "torch").setTranslationKey("torch"));
		}
	}

	public static boolean isWater(IBlockState current) {
		return current.getMaterial() == Material.WATER;
	}

	public static boolean isFreshWater(IBlockState current) {
		return current == FluidsTFC.FRESH_WATER.get().getBlock().getDefaultState();
	}

	public static boolean isSaltWater(IBlockState current) {
		return current == FluidsTFC.SALT_WATER.get().getBlock().getDefaultState();
	}

	public static boolean isFreshWaterOrIce(IBlockState current) {
		return current.getBlock() == Blocks.ICE || isFreshWater(current);
	}

	public static boolean isRawStone(IBlockState current) {
		if (current.getBlock() instanceof IRockTypeBlock rockTypeBlock)
			return rockTypeBlock.getRockVariant() == RAW;
		return false;
	}

	public static boolean isClay(IBlockState current) {
		if (current.getBlock() instanceof ISoilTypeBlock soilTypeBlock)
			switch (soilTypeBlock.getSoilVariant()) {
				case CLAY, CLAY_GRASS -> {
					return true;
				}
			}
		return false;
	}

	public static boolean isDirt(IBlockState current) {
		if (current.getBlock() instanceof ISoilTypeBlock soilTypeBlock)
			return soilTypeBlock.getSoilVariant() == DIRT;
		return false;
	}

	public static boolean isSand(IBlockState current) {
		if (current.getBlock() instanceof IRockTypeBlock rockTypeBlock) {
			return rockTypeBlock.getRockVariant() == RockVariant.SAND;
		}
		return false;
	}

	public static boolean isSoil(IBlockState current) {
		if (current.getBlock() instanceof BlockPeat) return true;
		if (current.getBlock() instanceof ISoilTypeBlock soilTypeBlock)
			switch (soilTypeBlock.getSoilVariant()) {
				case GRASS, DRY_GRASS, DIRT, CLAY, CLAY_GRASS -> {
					return true;
				}
			}
		return false;
	}

	public static boolean isGrowableSoil(IBlockState current) {
		if (current.getBlock() instanceof ISoilTypeBlock soilTypeBlock)
			switch (soilTypeBlock.getSoilVariant()) {
				case GRASS, DRY_GRASS, DIRT, CLAY, CLAY_GRASS -> {
					return true;
				}
			}
		return false;
	}

	public static boolean isSoilOrGravel(IBlockState current) {
		if (current.getBlock() instanceof BlockPeat) return true;
		if (current.getBlock() instanceof ISoilTypeBlock soilTypeBlock)
			switch (soilTypeBlock.getSoilVariant()) {
				case GRASS, DRY_GRASS, DIRT -> {
					return true;
				}
			}
		if (current.getBlock() instanceof IRockTypeBlock rockTypeBlock)
			return rockTypeBlock.getRockVariant() == GRAVEL;
		return false;
	}

	public static boolean isGrass(IBlockState current) {
		if (current.getBlock() instanceof BlockPeatGrass) return true;
		if (current.getBlock() instanceof ISoilTypeBlock soilTypeBlock)
			switch (soilTypeBlock.getSoilVariant()) {
				case GRASS, DRY_GRASS, CLAY_GRASS -> {
					return true;
				}
			}
		return false;
	}

	public static boolean isDryGrass(IBlockState current) {
		if (current.getBlock() instanceof ISoilTypeBlock soilTypeBlock)
			return soilTypeBlock.getSoilVariant() == DRY_GRASS;
		return false;
	}

	public static boolean isGround(IBlockState current) {
		if (current.getBlock() instanceof IRockTypeBlock rockTypeBlock)
			switch (rockTypeBlock.getRockVariant()) {
				case GRAVEL, SAND, RAW -> {
					return true;
				}
			}
		if (current.getBlock() instanceof ISoilTypeBlock soilTypeBlock)
			switch (soilTypeBlock.getSoilVariant()) {
				case GRASS, DRY_GRASS, DIRT -> {
					return true;
				}
			}
		return false;
	}

	private static <T extends Block> T register(IForgeRegistry<Block> r, String name, T block, CreativeTabs ct) {
		block.setCreativeTab(ct);
		return register(r, name, block);
	}

	private static <T extends Block> T register(IForgeRegistry<Block> r, String name, T block) {
		block.setRegistryName(MOD_ID, name);
		block.setTranslationKey(MOD_ID + "." + name.replace('/', '.'));
		r.register(block);
		return block;
	}

	private static <T extends TileEntity> void register(Class<T> te, String name) {
		TileEntity.register(MOD_ID + ":" + name, te);
	}
}