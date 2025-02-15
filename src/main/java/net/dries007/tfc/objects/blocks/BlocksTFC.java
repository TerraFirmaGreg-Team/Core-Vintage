package net.dries007.tfc.objects.blocks;

import su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager;
import su.terrafirmagreg.modules.core.init.FluidsCore;
import su.terrafirmagreg.modules.device.object.block.BlockFirePit;
import su.terrafirmagreg.modules.device.object.block.BlockLogPile;
import su.terrafirmagreg.modules.device.object.block.BlockPitKiln;
import su.terrafirmagreg.modules.device.object.block.BlockPowderKeg;
import su.terrafirmagreg.modules.device.object.block.BlockQuern;
import su.terrafirmagreg.modules.device.object.block.BlockSluice;
import su.terrafirmagreg.modules.device.object.block.BlockThatchBed;
import su.terrafirmagreg.modules.device.object.tile.TileFirePit;
import su.terrafirmagreg.modules.device.object.tile.TileLogPile;
import su.terrafirmagreg.modules.device.object.tile.TilePitKiln;
import su.terrafirmagreg.modules.device.object.tile.TilePowderKeg;
import su.terrafirmagreg.modules.device.object.tile.TileQuern;
import su.terrafirmagreg.modules.device.object.tile.TileSluice;

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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.blocks.agriculture.BlockBerryBush;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropDead;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeBranch;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeLeaves;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeSapling;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeTrunk;
import net.dries007.tfc.objects.blocks.metal.BlockAnvilTFC;
import net.dries007.tfc.objects.blocks.metal.BlockIngotPile;
import net.dries007.tfc.objects.blocks.metal.BlockMetalLamp;
import net.dries007.tfc.objects.blocks.metal.BlockMetalSheet;
import net.dries007.tfc.objects.blocks.metal.BlockTrapDoorMetalTFC;
import net.dries007.tfc.objects.blocks.plants.BlockFloatingWaterTFC;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.blocks.stone.BlockButtonStoneTFC;
import net.dries007.tfc.objects.blocks.stone.BlockOreTFC;
import net.dries007.tfc.objects.blocks.stone.BlockPressurePlateTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockSmooth;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.objects.blocks.stone.BlockWallTFC;
import net.dries007.tfc.objects.blocks.stone.BlockWoodPressurePlateTFC;
import net.dries007.tfc.objects.blocks.wood.BlockBarrel;
import net.dries007.tfc.objects.blocks.wood.BlockBookshelfTFC;
import net.dries007.tfc.objects.blocks.wood.BlockButtonWoodTFC;
import net.dries007.tfc.objects.blocks.wood.BlockChestTFC;
import net.dries007.tfc.objects.blocks.wood.BlockDoorTFC;
import net.dries007.tfc.objects.blocks.wood.BlockFenceGateTFC;
import net.dries007.tfc.objects.blocks.wood.BlockFenceTFC;
import net.dries007.tfc.objects.blocks.wood.BlockLeavesTFC;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;
import net.dries007.tfc.objects.blocks.wood.BlockLoom;
import net.dries007.tfc.objects.blocks.wood.BlockPlanksTFC;
import net.dries007.tfc.objects.blocks.wood.BlockSaplingTFC;
import net.dries007.tfc.objects.blocks.wood.BlockSupport;
import net.dries007.tfc.objects.blocks.wood.BlockToolRack;
import net.dries007.tfc.objects.blocks.wood.BlockTrapDoorWoodTFC;
import net.dries007.tfc.objects.blocks.wood.BlockWorkbenchTFC;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import net.dries007.tfc.objects.items.itemblock.ItemBlockBarrel;
import net.dries007.tfc.objects.items.itemblock.ItemBlockFloatingWaterTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockHeat;
import net.dries007.tfc.objects.items.itemblock.ItemBlockLargeVessel;
import net.dries007.tfc.objects.items.itemblock.ItemBlockPlant;
import net.dries007.tfc.objects.items.itemblock.ItemBlockPowderKeg;
import net.dries007.tfc.objects.items.itemblock.ItemBlockSaplingTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockSluice;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.objects.te.TEAnvilTFC;
import net.dries007.tfc.objects.te.TEBarrel;
import net.dries007.tfc.objects.te.TEChestTFC;
import net.dries007.tfc.objects.te.TECropBase;
import net.dries007.tfc.objects.te.TECropSpreading;
import net.dries007.tfc.objects.te.TEIngotPile;
import net.dries007.tfc.objects.te.TELamp;
import net.dries007.tfc.objects.te.TELargeVessel;
import net.dries007.tfc.objects.te.TELoom;
import net.dries007.tfc.objects.te.TEMetalSheet;
import net.dries007.tfc.objects.te.TEPlacedHide;
import net.dries007.tfc.objects.te.TEPlacedItem;
import net.dries007.tfc.objects.te.TEPlacedItemFlat;
import net.dries007.tfc.objects.te.TETickCounter;
import net.dries007.tfc.objects.te.TEToolRack;
import net.dries007.tfc.util.agriculture.BerryBush;
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.agriculture.FruitTree;

import lombok.Getter;

import static net.dries007.tfc.api.types.Rock.Type.ANVIL;
import static net.dries007.tfc.api.types.Rock.Type.BRICKS;
import static net.dries007.tfc.api.types.Rock.Type.CLAY;
import static net.dries007.tfc.api.types.Rock.Type.CLAY_GRASS;
import static net.dries007.tfc.api.types.Rock.Type.COBBLE;
import static net.dries007.tfc.api.types.Rock.Type.DIRT;
import static net.dries007.tfc.api.types.Rock.Type.DRY_GRASS;
import static net.dries007.tfc.api.types.Rock.Type.GRASS;
import static net.dries007.tfc.api.types.Rock.Type.GRAVEL;
import static net.dries007.tfc.api.types.Rock.Type.RAW;
import static net.dries007.tfc.api.types.Rock.Type.SAND;
import static net.dries007.tfc.api.types.Rock.Type.SMOOTH;
import static net.dries007.tfc.api.types.Rock.Type.SPIKE;
import static net.dries007.tfc.api.types.Rock.Type.values;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_FLORA;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_FOOD;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_METAL;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_MISC;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_POTTERY;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_ROCK;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_WOOD;
import static net.dries007.tfc.util.Helpers.getNull;
import static su.terrafirmagreg.api.data.enums.Mods.Names.TFC;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = TFC)
@GameRegistry.ObjectHolder(TFC)
public final class BlocksTFC {

  @GameRegistry.ObjectHolder("ceramics/fired/large_vessel")
  public static final BlockLargeVessel FIRED_LARGE_VESSEL = getNull();

  @GameRegistry.ObjectHolder("alabaster/bricks/plain")
  public static final BlockDecorativeStone ALABASTER_BRICKS_PLAIN = getNull();
  @GameRegistry.ObjectHolder("alabaster/polished/plain")
  public static final BlockDecorativeStone ALABASTER_POLISHED_PLAIN = getNull();
  @GameRegistry.ObjectHolder("alabaster/raw/plain")
  public static final BlockDecorativeStone ALABASTER_RAW_PLAIN = getNull();

  public static final BlockPeat PEAT = getNull();
  public static final BlockPeat PEAT_GRASS = getNull();
  public static final BlockFirePit FIRE_PIT = getNull();
  public static final BlockThatch THATCH = getNull();
  public static final BlockThatchBed THATCH_BED = getNull();
  public static final BlockPitKiln PIT_KILN = getNull();
  public static final BlockPlacedItemFlat PLACED_ITEM_FLAT = getNull();
  public static final BlockPlacedItem PLACED_ITEM = getNull();
  public static final BlockPlacedHide PLACED_HIDE = getNull();
  public static final BlockLogPile LOG_PILE = getNull();
  public static final BlockIngotPile INGOT_PILE = getNull();
  public static final BlockQuern QUERN = getNull();
  public static final BlockSeaIce SEA_ICE = getNull();
  public static final BlockPowderKeg POWDERKEG = getNull();
  public static final BlockGravel AGGREGATE = getNull();

  // All these are for use in model registration. Do not use for block lookups.
  // Use the static get methods in the classes instead.
  @Getter
  private static ImmutableList<ItemBlock> allNormalItemBlocks;
  @Getter
  private static ImmutableList<ItemBlock> allInventoryItemBlocks;
  @Getter
  private static ImmutableList<ItemBlockBarrel> allBarrelItemBlocks;

  @Getter
  private static ImmutableList<BlockFluidBase> allFluidBlocks;
  @Getter
  private static ImmutableList<BlockRockVariant> allBlockRockVariants;
  @Getter
  private static ImmutableList<BlockOreTFC> allOreBlocks;
  @Getter
  private static ImmutableList<BlockWallTFC> allWallBlocks;
  @Getter
  private static ImmutableList<BlockLogTFC> allLogBlocks;
  @Getter
  private static ImmutableList<BlockLeavesTFC> allLeafBlocks;
  @Getter
  private static ImmutableList<BlockFenceGateTFC> allFenceGateBlocks;
  @Getter
  private static ImmutableList<BlockSaplingTFC> allSaplingBlocks;
  @Getter
  private static ImmutableList<BlockDoorTFC> allDoorBlocks;
  @Getter
  private static ImmutableList<BlockTrapDoorWoodTFC> allTrapDoorWoodBlocks;
  @Getter
  private static ImmutableList<BlockTrapDoorMetalTFC> allTrapDoorMetalBlocks;
  @Getter
  private static ImmutableList<BlockStairsTFC> allStairsBlocks;
  @Getter
  private static ImmutableList<BlockSlabTFC.Half> allSlabBlocks;
  @Getter
  private static ImmutableList<BlockChestTFC> allChestBlocks;
  @Getter
  private static ImmutableList<BlockAnvilTFC> allAnvils;
  @Getter
  private static ImmutableList<BlockMetalSheet> allSheets;
  @Getter
  private static ImmutableList<BlockMetalLamp> allLamps;
  @Getter
  private static ImmutableList<BlockToolRack> allToolRackBlocks;
  @Getter
  private static ImmutableList<BlockCropTFC> allCropBlocks;
  @Getter
  private static ImmutableList<BlockCropDead> allDeadCropBlocks;
  @Getter
  private static ImmutableList<BlockPlantTFC> allPlantBlocks;
  @Getter
  private static ImmutableList<BlockPlantTFC> allGrassBlocks;
  @Getter
  private static ImmutableList<BlockLoom> allLoomBlocks;
  @Getter
  private static ImmutableList<BlockSupport> allSupportBlocks;
  @Getter
  private static ImmutableList<BlockFlowerPotTFC> allFlowerPots;

  @Getter
  private static ImmutableList<BlockFruitTreeSapling> allFruitTreeSaplingBlocks;
  @Getter
  private static ImmutableList<BlockFruitTreeTrunk> allFruitTreeTrunkBlocks;
  @Getter
  private static ImmutableList<BlockFruitTreeBranch> allFruitTreeBranchBlocks;
  @Getter
  private static ImmutableList<BlockFruitTreeLeaves> allFruitTreeLeavesBlocks;

  @Getter
  private static ImmutableList<BlockBerryBush> allBerryBushBlocks;

  @SubscribeEvent
  @SuppressWarnings("ConstantConditions")
  public static void registerBlocks(RegistryEvent.Register<Block> event) {
    // This is called here because it needs to wait until Metal registry has fired
    FluidsTFC.registerFluids();

    IForgeRegistry<Block> r = event.getRegistry();

    Builder<ItemBlock> normalItemBlocks = ImmutableList.builder();
    Builder<ItemBlock> inventoryItemBlocks = ImmutableList.builder();

    normalItemBlocks.add(new ItemBlockTFC(register(r, "aggregate", new BlockAggregate(), CT_ROCK)));
    normalItemBlocks.add(new ItemBlockTFC(register(r, "fire_clay_block", new BlockFireClay(), CT_ROCK)));

    normalItemBlocks.add(new ItemBlockTFC(register(r, "peat", new BlockPeat(Material.GROUND), CT_ROCK)));
    normalItemBlocks.add(new ItemBlockTFC(register(r, "peat_grass", new BlockPeatGrass(Material.GRASS), CT_ROCK)));

    normalItemBlocks.add(new ItemBlockTFC(register(r, "thatch", new BlockThatch(), CT_MISC)));

    normalItemBlocks.add(new ItemBlockTFC(register(r, "quern", new BlockQuern(), CT_MISC)));

    inventoryItemBlocks.add(new ItemBlockSluice(register(r, "sluice", new BlockSluice(), CT_MISC)));
    normalItemBlocks.add(new ItemBlockTFC(register(r, "sea_ice", new BlockSeaIce(), CT_MISC)));

    normalItemBlocks.add(new ItemBlockLargeVessel(register(r, "ceramics/fired/large_vessel", new BlockLargeVessel(), CT_POTTERY)));
    normalItemBlocks.add(new ItemBlockPowderKeg(register(r, "powderkeg", new BlockPowderKeg(), CT_WOOD)));

    normalItemBlocks.add(new ItemBlockTFC(register(r, "alabaster/raw/plain", new BlockDecorativeStone(MapColor.SNOW), CT_MISC)));
    normalItemBlocks.add(new ItemBlockTFC(register(r, "alabaster/polished/plain", new BlockDecorativeStone(MapColor.SNOW), CT_MISC)));
    normalItemBlocks.add(new ItemBlockTFC(register(r, "alabaster/bricks/plain", new BlockDecorativeStone(MapColor.SNOW), CT_MISC)));

    for (EnumDyeColor dyeColor : EnumDyeColor.values()) {
      BlockDecorativeStone polished = new BlockDecorativeStone(MapColor.getBlockColor(dyeColor));
      BlockDecorativeStone bricks = new BlockDecorativeStone(MapColor.getBlockColor(dyeColor));
      BlockDecorativeStone raw = new BlockDecorativeStone(MapColor.getBlockColor(dyeColor));

      normalItemBlocks.add(new ItemBlockTFC(register(r, "alabaster/polished/" + dyeColor.getName(), polished, CT_MISC)));
      normalItemBlocks.add(new ItemBlockTFC(register(r, "alabaster/bricks/" + dyeColor.getName(), bricks, CT_MISC)));
      normalItemBlocks.add(new ItemBlockTFC(register(r, "alabaster/raw/" + dyeColor.getName(), raw, CT_MISC)));

      BlockDecorativeStone.ALABASTER_POLISHED.put(dyeColor, polished);
      BlockDecorativeStone.ALABASTER_BRICKS.put(dyeColor, bricks);
      BlockDecorativeStone.ALABASTER_RAW.put(dyeColor, raw);
    }

    {
      // Apparently this is the way we're supposed to do things even though the fluid registry defaults. So we'll do it this way.
      Builder<BlockFluidBase> b = ImmutableList.builder();
      b.add(
        register(r, "fluid/hot_water", new BlockFluidHotWater()),
        register(r, "fluid/fresh_water", new BlockFluidWater(FluidsCore.FRESH_WATER.get(), Material.WATER, false)),
        register(r, "fluid/salt_water", new BlockFluidWater(FluidsCore.SALT_WATER.get(), Material.WATER, true))
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

    {
      Builder<BlockRockVariant> b = ImmutableList.builder();
      for (Rock.Type type : values()) {
        for (Rock rock : TFCRegistries.ROCKS.getValuesCollection()) {
          if (type != ANVIL) {
            b.add(register(r, type.name().toLowerCase() + "/" + rock.getRegistryName().getPath(), BlockRockVariant.create(rock, type), CT_ROCK));
          } else if (rock.getRockCategory().hasAnvil()) {
            // Anvil registration is special, is has it's own folder
            register(r, "anvil/" + rock.getRegistryName().getPath(), BlockRockVariant.create(rock, type));
          }
        }
      }
      allBlockRockVariants = b.build();
      allBlockRockVariants.forEach(x ->
      {
        if (x.getType() == SAND) {
          normalItemBlocks.add(new ItemBlockHeat(x, 1, 600));
        } else if (x.getType() != SPIKE && x.getType() != ANVIL) {
          normalItemBlocks.add(new ItemBlockTFC(x));
        }
      });
    }

    {
      // Add resultingState to the registered collapsable blocks.
      for (Rock rock : TFCRegistries.ROCKS.getValuesCollection()) {
        for (Rock.Type type : values()) {
          FallingBlockManager.Specification spec = type.getFallingSpecification();
          switch (type) {
            case ANVIL:
              if (!rock.getRockCategory().hasAnvil()) {
                break;
              }
            case RAW:
              spec = new FallingBlockManager.Specification(spec);
              spec.setResultingState(BlockRockVariant.get(rock, COBBLE).getDefaultState());
              FallingBlockManager.registerFallable(BlockRockVariant.get(rock, RAW), spec);
              break;
            case SMOOTH:
              spec = new FallingBlockManager.Specification(spec);
              spec.setResultingState(BlockRockVariant.get(rock, COBBLE).getDefaultState());
              FallingBlockManager.registerFallable(BlockRockVariant.get(rock, SMOOTH).getDefaultState().withProperty(BlockRockSmooth.CAN_FALL, true), spec);
              break;
            default:
              Rock.Type nonGrassType = type.getNonGrassVersion();
              if (nonGrassType != type) {
                spec = new FallingBlockManager.Specification(spec);
                spec.setResultingState(BlockRockVariant.get(rock, nonGrassType).getDefaultState());
              }
              FallingBlockManager.registerFallable(BlockRockVariant.get(rock, type), spec);
          }
        }
      }
    }

    {
      Builder<BlockOreTFC> b = ImmutableList.builder();
      for (Ore ore : TFCRegistries.ORES.getValuesCollection()) {
        for (Rock rock : TFCRegistries.ROCKS.getValuesCollection()) {
          b.add(register(r, ("ore/" + ore.getRegistryName().getPath() + "/" + rock.getRegistryName()
            .getPath()).toLowerCase(), new BlockOreTFC(ore, rock), CT_ROCK));
        }
      }
      allOreBlocks = b.build();
      allOreBlocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
    }

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
      for (Tree wood : TFCRegistries.TREES.getValuesCollection()) {
        normalItemBlocks.add(new ItemBlockTFC(register(r, "wood/bookshelf/" + wood.getRegistryName().getPath(), new BlockBookshelfTFC(wood), CT_WOOD)));
      }
      // All workbenches + item blocks
      for (Tree wood : TFCRegistries.TREES.getValuesCollection()) {
        normalItemBlocks.add(new ItemBlockTFC(register(r, "wood/workbench/" + wood.getRegistryName().getPath(), new BlockWorkbenchTFC(wood), CT_WOOD)));
      }
      // All fences + item blocks
      for (Tree wood : TFCRegistries.TREES.getValuesCollection()) {
        inventoryItemBlocks.add(new ItemBlockTFC(register(r, "wood/fence/" + wood.getRegistryName().getPath(), new BlockFenceTFC(wood), CT_WOOD)));
      }
      // All buttons + item blocks
      for (Tree wood : TFCRegistries.TREES.getValuesCollection()) {
        inventoryItemBlocks.add(new ItemBlockTFC(register(r, "wood/button/" + wood.getRegistryName().getPath(), new BlockButtonWoodTFC(wood), CT_WOOD)));
      }
      // All pressure plates + item blocks
      for (Tree wood : TFCRegistries.TREES.getValuesCollection()) {
        inventoryItemBlocks.add(new ItemBlockTFC(register(r, "wood/pressure_plate/" + wood.getRegistryName().getPath()
          .toLowerCase(), new BlockWoodPressurePlateTFC(wood), CT_WOOD)));
      }

      // Other blocks that don't have specific order requirements
      for (Tree wood : TFCRegistries.TREES.getValuesCollection()) {
        // Only block in the decorations category
        normalItemBlocks.add(new ItemBlockTFC(register(r, "wood/planks/" + wood.getRegistryName().getPath(), new BlockPlanksTFC(wood), CT_WOOD)));
        // Blocks with specific block collections don't matter
        logs.add(register(r, "wood/log/" + wood.getRegistryName().getPath(), new BlockLogTFC(wood), CT_WOOD));
        leaves.add(register(r, "wood/leaves/" + wood.getRegistryName().getPath(), new BlockLeavesTFC(wood), CT_WOOD));
        fenceGates.add(register(r, "wood/fence_gate/" + wood.getRegistryName().getPath(), new BlockFenceGateTFC(wood), CT_WOOD));
        saplings.add(register(r, "wood/sapling/" + wood.getRegistryName().getPath(), new BlockSaplingTFC(wood), CT_WOOD));
        doors.add(register(r, "wood/door/" + wood.getRegistryName().getPath(), new BlockDoorTFC(wood), CT_WOOD));
        trapDoors.add(register(r, "wood/trapdoor/" + wood.getRegistryName().getPath(), new BlockTrapDoorWoodTFC(wood), CT_WOOD));
        chests.add(register(r, "wood/chest/" + wood.getRegistryName().getPath(), new BlockChestTFC(BlockChestTFC.TFCBASIC, wood), CT_WOOD));
        chests.add(register(r, "wood/chest_trap/" + wood.getRegistryName().getPath(), new BlockChestTFC(BlockChestTFC.TFCTRAP, wood), CT_WOOD));

        toolRacks.add(register(r, "wood/tool_rack/" + wood.getRegistryName().getPath(), new BlockToolRack(wood), CT_WOOD));
        barrelItems.add(new ItemBlockBarrel(register(r, "wood/barrel/" + wood.getRegistryName().getPath(), new BlockBarrel(), CT_WOOD)));

        looms.add(register(r, "wood/loom/" + wood.getRegistryName().getPath(), new BlockLoom(wood), CT_WOOD));
        supports.add(register(r, "wood/support/" + wood.getRegistryName().getPath(), new BlockSupport(wood), CT_WOOD));
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
      Builder<BlockWallTFC> b = ImmutableList.builder();
      Builder<BlockStairsTFC> stairs = new Builder<>();
      Builder<BlockSlabTFC.Half> slab = new Builder<>();

      // Walls
      for (Rock.Type type : new Rock.Type[]{SMOOTH, COBBLE, BRICKS}) {
        for (Rock rock : TFCRegistries.ROCKS.getValuesCollection()) {
          b.add(register(r, ("wall/" + type.name() + "/" + rock.getRegistryName()
            .getPath()).toLowerCase(), new BlockWallTFC(BlockRockVariant.get(rock, type)), CT_ROCK));
        }
      }
      // Stairs
      for (Rock.Type type : new Rock.Type[]{SMOOTH, COBBLE, BRICKS}) {
        for (Rock rock : TFCRegistries.ROCKS.getValuesCollection()) {
          stairs.add(register(r, "stairs/" + (type.name() + "/" + rock.getRegistryName()
            .getPath()).toLowerCase(), new BlockStairsTFC(rock, type), CT_ROCK));
        }
      }
      for (Tree wood : TFCRegistries.TREES.getValuesCollection()) {
        stairs.add(register(r, "stairs/wood/" + wood.getRegistryName().getPath(), new BlockStairsTFC(wood), CT_WOOD));
      }

      // Full slabs are the same as full blocks, they are not saved to a list, they are kept track of by the halfslab version.
      for (Rock.Type type : new Rock.Type[]{SMOOTH, COBBLE, BRICKS}) {
        for (Rock rock : TFCRegistries.ROCKS.getValuesCollection()) {
          register(r, "double_slab/" + (type.name() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockSlabTFC.Double(rock, type));
        }
      }
      for (Tree wood : TFCRegistries.TREES.getValuesCollection()) {
        register(r, "double_slab/wood/" + wood.getRegistryName().getPath(), new BlockSlabTFC.Double(wood));
      }

      // Slabs
      for (Rock.Type type : new Rock.Type[]{SMOOTH, COBBLE, BRICKS}) {
        for (Rock rock : TFCRegistries.ROCKS.getValuesCollection()) {
          slab.add(register(r,
            "slab/" + (type.name() + "/" + rock.getRegistryName().getPath()).toLowerCase(), new BlockSlabTFC.Half(rock, type), CT_ROCK));
        }
      }
      for (Tree wood : TFCRegistries.TREES.getValuesCollection()) {
        slab.add(register(r, "slab/wood/" + wood.getRegistryName().getPath(), new BlockSlabTFC.Half(wood), CT_WOOD));
      }

      for (Rock rock : TFCRegistries.ROCKS.getValuesCollection()) {
        // Redstone things
        inventoryItemBlocks.add(new ItemBlockTFC(register(r, "stone/button/" + rock.getRegistryName().getPath()
          .toLowerCase(), new BlockButtonStoneTFC(rock), CT_ROCK)));
        inventoryItemBlocks.add(new ItemBlockTFC(register(r, "stone/pressure_plate/" + rock.getRegistryName().getPath()
          .toLowerCase(), new BlockPressurePlateTFC(rock), CT_ROCK)));
      }

      allWallBlocks = b.build();
      allStairsBlocks = stairs.build();
      allSlabBlocks = slab.build();
      allWallBlocks.forEach(x -> inventoryItemBlocks.add(new ItemBlockTFC(x)));
      allStairsBlocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));
    }

    {
      Builder<BlockAnvilTFC> anvils = ImmutableList.builder();
      Builder<BlockMetalSheet> sheets = ImmutableList.builder();
      Builder<BlockMetalLamp> lamps = ImmutableList.builder();
      Builder<BlockTrapDoorMetalTFC> metalTrapdoors = ImmutableList.builder();

      for (Metal metal : TFCRegistries.METALS.getValuesCollection()) {
        if (Metal.ItemType.ANVIL.hasType(metal)) {anvils.add(register(r, "anvil/" + metal.getRegistryName().getPath(), new BlockAnvilTFC(metal), CT_METAL));}
        if (Metal.ItemType.SHEET.hasType(metal)) {
          sheets.add(register(r, "sheet/" + metal.getRegistryName().getPath(), new BlockMetalSheet(metal), CT_METAL));
          metalTrapdoors.add(register(r, "trapdoor/" + metal.getRegistryName().getPath(), new BlockTrapDoorMetalTFC(metal), CT_METAL));
        }
        if (Metal.ItemType.LAMP.hasType(metal)) {lamps.add(register(r, "lamp/" + metal.getRegistryName().getPath(), new BlockMetalLamp(metal), CT_METAL));}

      }

      allAnvils = anvils.build();
      allSheets = sheets.build();
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
        fSaplings.add(register(r, "fruit_trees/sapling/" + tree.name().toLowerCase(), new BlockFruitTreeSapling(tree), CT_WOOD));
        fTrunks.add(register(r, "fruit_trees/trunk/" + tree.name().toLowerCase(), new BlockFruitTreeTrunk(tree), CT_WOOD));
        fBranches.add(register(r, "fruit_trees/branch/" + tree.name().toLowerCase(), new BlockFruitTreeBranch(tree), CT_WOOD));
        fLeaves.add(register(r, "fruit_trees/leaves/" + tree.name().toLowerCase(), new BlockFruitTreeLeaves(tree), CT_WOOD));
      }

      allFruitTreeSaplingBlocks = fSaplings.build();
      allFruitTreeTrunkBlocks = fTrunks.build();
      allFruitTreeBranchBlocks = fBranches.build();
      allFruitTreeLeavesBlocks = fLeaves.build();

      Builder<BlockBerryBush> fBerry = ImmutableList.builder();

      for (BerryBush bush : BerryBush.values()) {
        fBerry.add(register(r, "berry_bush/" + bush.name().toLowerCase(), new BlockBerryBush(bush), CT_FOOD));
      }

      allBerryBushBlocks = fBerry.build();

      //Add ItemBlocks
      allFruitTreeSaplingBlocks.forEach(x -> inventoryItemBlocks.add(new ItemBlockTFC(x)));
      allFruitTreeLeavesBlocks.forEach(x -> inventoryItemBlocks.add(new ItemBlockTFC(x)));
      allBerryBushBlocks.forEach(x -> inventoryItemBlocks.add(new ItemBlockTFC(x)));
    }

    {

      Builder<BlockPlantTFC> b = ImmutableList.builder();
      Builder<BlockFlowerPotTFC> pots = ImmutableList.builder();
      for (Plant plant : TFCRegistries.PLANTS.getValuesCollection()) {
        if (plant.getPlantType() != Plant.PlantType.SHORT_GRASS && plant.getPlantType() != Plant.PlantType.TALL_GRASS) {
          b.add(register(r, "plants/" + plant.getRegistryName().getPath(), plant.getPlantType().create(plant), CT_FLORA));
        }
        if (plant.canBePotted()) {pots.add(register(r, "flowerpot/" + plant.getRegistryName().getPath(), new BlockFlowerPotTFC(plant)));}
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
        if (plant.getPlantType() == Plant.PlantType.SHORT_GRASS || plant.getPlantType() == Plant.PlantType.TALL_GRASS) {
          b.add(register(r, "plants/" + plant.getRegistryName().getPath(), plant.getPlantType().create(plant), CT_FLORA));
        }
      }
      allGrassBlocks = b.build();
      for (BlockPlantTFC blockPlant : allGrassBlocks) {
        normalItemBlocks.add(new ItemBlockTFC(blockPlant));
      }
    }

    // Registering JEI only blocks (for info)
    inventoryItemBlocks.add(new ItemBlock(register(r, "firepit", new BlockFirePit())));
    inventoryItemBlocks.add(new ItemBlock(register(r, "pit_kiln", new BlockPitKiln())));
    inventoryItemBlocks.add(new ItemBlock(register(r, "placed_item", new BlockPlacedItem())));
    // technical blocks
    // These have no ItemBlock or Creative Tab
    register(r, "placed_item_flat", new BlockPlacedItemFlat());
    register(r, "placed_hide", new BlockPlacedHide());
    register(r, "ingot_pile", new BlockIngotPile());
    register(r, "log_pile", new BlockLogPile());
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
    register(TilePitKiln.class, "pit_kiln");
    register(TEChestTFC.class, "chest");
    register(TileLogPile.class, "log_pile");
    register(TEIngotPile.class, "ingot_pile");
    register(TileFirePit.class, "fire_pit");
    register(TEToolRack.class, "tool_rack");
    register(TELoom.class, "loom");
    register(TELamp.class, "lamp");
    register(TEBarrel.class, "barrel");
    register(TEAnvilTFC.class, "anvil");
    register(TECropBase.class, "crop_base");
    register(TECropSpreading.class, "crop_spreading");
    register(TEMetalSheet.class, "metal_sheet");
    register(TileQuern.class, "quern");
    register(TELargeVessel.class, "large_vessel");
    register(TilePowderKeg.class, "powderkeg");
    register(TileSluice.class, "sluice");
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void registerVanillaOverrides(RegistryEvent.Register<Block> event) {
    // Vanilla Overrides. Used for small tweaks on vanilla items, rather than replacing them outright
    if (ConfigTFC.General.OVERRIDES.enableFrozenOverrides) {
      TerraFirmaCraft.getLog().info("The below warnings about unintended overrides are normal. The override is intended. ;)");
      event.getRegistry().registerAll(
        new BlockIceTFC(FluidsCore.FRESH_WATER.get()).setRegistryName("minecraft", "ice").setTranslationKey("ice"),
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
    return current == FluidsCore.FRESH_WATER.get().getBlock().getDefaultState();
  }

  public static boolean isSaltWater(IBlockState current) {
    return current == FluidsCore.SALT_WATER.get().getBlock().getDefaultState();
  }

  public static boolean isFreshWaterOrIce(IBlockState current) {
    return current.getBlock() == Blocks.ICE || isFreshWater(current);
  }

  public static boolean isRawStone(IBlockState current) {
    if (!(current.getBlock() instanceof BlockRockVariant)) {return false;}
    Rock.Type type = ((BlockRockVariant) current.getBlock()).getType();
    return type == RAW;
  }

  public static boolean isClay(IBlockState current) {
    if (!(current.getBlock() instanceof BlockRockVariant)) {return false;}
    Rock.Type type = ((BlockRockVariant) current.getBlock()).getType();
    return type == CLAY || type == CLAY_GRASS;
  }

  public static boolean isDirt(IBlockState current) {
    if (!(current.getBlock() instanceof BlockRockVariant)) {return false;}
    Rock.Type type = ((BlockRockVariant) current.getBlock()).getType();
    return type == DIRT;
  }

  public static boolean isSand(IBlockState current) {
    if (!(current.getBlock() instanceof BlockRockVariant)) {return false;}
    Rock.Type type = ((BlockRockVariant) current.getBlock()).getType();
    return type == SAND;
  }

  // todo: change to property of type? (soil & stone maybe?)

  public static boolean isSoil(IBlockState current) {
    if (current.getBlock() instanceof BlockPeat) {return true;}
    if (!(current.getBlock() instanceof BlockRockVariant)) {return false;}
    Rock.Type type = ((BlockRockVariant) current.getBlock()).getType();
    return type == GRASS || type == DRY_GRASS || type == DIRT || type == CLAY || type == CLAY_GRASS;
  }

  public static boolean isGrowableSoil(IBlockState current) {
    if (current.getBlock() instanceof BlockPeat) {return false;}
    if (!(current.getBlock() instanceof BlockRockVariant)) {return false;}
    Rock.Type type = ((BlockRockVariant) current.getBlock()).getType();
    return type == GRASS || type == DRY_GRASS || type == DIRT || type == CLAY || type == CLAY_GRASS;
  }

  public static boolean isSoilOrGravel(IBlockState current) {
    if (current.getBlock() instanceof BlockPeat) {return true;}
    if (!(current.getBlock() instanceof BlockRockVariant)) {return false;}
    Rock.Type type = ((BlockRockVariant) current.getBlock()).getType();
    return type == GRASS || type == DRY_GRASS || type == DIRT || type == GRAVEL || type == CLAY;
  }

  public static boolean isGrass(IBlockState current) {
    if (current.getBlock() instanceof BlockPeatGrass) {return true;}
    if (!(current.getBlock() instanceof BlockRockVariant)) {return false;}
    Rock.Type type = ((BlockRockVariant) current.getBlock()).getType();
    return type.isGrass;
  }

  public static boolean isDryGrass(IBlockState current) {
    if (!(current.getBlock() instanceof BlockRockVariant)) {return false;}
    Rock.Type type = ((BlockRockVariant) current.getBlock()).getType();
    return type == DRY_GRASS;
  }

  public static boolean isGround(IBlockState current) {
    if (!(current.getBlock() instanceof BlockRockVariant)) {return false;}
    Rock.Type type = ((BlockRockVariant) current.getBlock()).getType();
    return type == GRASS || type == DRY_GRASS || type == DIRT || type == GRAVEL || type == RAW || type == SAND;
  }

  private static <T extends Block> T register(IForgeRegistry<Block> r, String name, T block, CreativeTabs ct) {
    block.setCreativeTab(ct);
    return register(r, name, block);
  }

  private static <T extends Block> T register(IForgeRegistry<Block> r, String name, T block) {
    block.setRegistryName(TFC, name);
    block.setTranslationKey(TFC + "." + name.replace('/', '.'));
    r.register(block);
    return block;
  }

  private static <T extends TileEntity> void register(Class<T> te, String name) {
    TileEntity.register(TFC + ":" + name, te);
  }
}
