package net.dries007.tfc.objects.blocks;

import su.terrafirmagreg.api.base.tile.BaseTileTickCounter;
import su.terrafirmagreg.modules.core.init.FluidsCore;
import su.terrafirmagreg.modules.flora.object.block.BlockPlant;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantFloatingWater;
import su.terrafirmagreg.modules.flora.object.block.BlockPlantFlowerPot;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
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
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.blocks.agriculture.BlockBerryBush;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropDead;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeBranch;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeLeaves;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeSapling;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeTrunk;
import net.dries007.tfc.objects.blocks.metal.BlockMetalSheet;
import net.dries007.tfc.objects.blocks.metal.BlockTrapDoorMetalTFC;
import net.dries007.tfc.objects.blocks.wood.BlockLeavesTFC;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;
import net.dries007.tfc.objects.blocks.wood.BlockSaplingTFC;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import net.dries007.tfc.objects.items.itemblock.ItemBlockFloatingWaterTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockLargeVessel;
import net.dries007.tfc.objects.items.itemblock.ItemBlockPlant;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.objects.te.TECropBase;
import net.dries007.tfc.objects.te.TECropSpreading;
import net.dries007.tfc.objects.te.TELargeVessel;
import net.dries007.tfc.objects.te.TEMetalSheet;
import net.dries007.tfc.objects.te.TEPlacedHide;
import net.dries007.tfc.objects.te.TEPlacedItem;
import net.dries007.tfc.objects.te.TEPlacedItemFlat;
import net.dries007.tfc.util.agriculture.BerryBush;
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.agriculture.FruitTree;

import lombok.Getter;

import static net.dries007.tfc.objects.CreativeTabsTFC.CT_FOOD;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_METAL;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_MISC;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_POTTERY;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_WOOD;
import static net.dries007.tfc.util.Helpers.getNull;
import static su.terrafirmagreg.api.data.Reference.MODID_TFC;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID_TFC)
@GameRegistry.ObjectHolder(MODID_TFC)
public final class BlocksTFC {

  @GameRegistry.ObjectHolder("ceramics/fired/large_vessel")
  public static final BlockLargeVessel FIRED_LARGE_VESSEL = getNull();
  public static final BlockPlacedItemFlat PLACED_ITEM_FLAT = getNull();
  public static final BlockPlacedItem PLACED_ITEM = getNull();
  public static final BlockPlacedHide PLACED_HIDE = getNull();
  public static final BlockIceTFC SEA_ICE = getNull();

  // All these are for use in model registration. Do not use for block lookups.
  // Use the static get methods in the classes instead.
  @Getter
  private static ImmutableList<ItemBlock> allNormalItemBlocks;
  @Getter
  private static ImmutableList<ItemBlock> allInventoryItemBlocks;

  @Getter
  private static ImmutableList<BlockFluidBase> allFluidBlocks;
  @Getter
  private static ImmutableList<BlockLogTFC> allLogBlocks;
  @Getter
  private static ImmutableList<BlockLeavesTFC> allLeafBlocks;
  @Getter
  private static ImmutableList<BlockSaplingTFC> allSaplingBlocks;
  @Getter
  private static ImmutableList<BlockTrapDoorMetalTFC> allTrapDoorMetalBlocks;
  @Getter
  private static ImmutableList<BlockMetalSheet> allSheets;
  @Getter
  private static ImmutableList<BlockCropTFC> allCropBlocks;
  @Getter
  private static ImmutableList<BlockCropDead> allDeadCropBlocks;
  @Getter
  private static ImmutableList<BlockPlant> allPlantBlocks;
  @Getter
  private static ImmutableList<BlockPlant> allGrassBlocks;
  @Getter
  private static ImmutableList<BlockPlantFlowerPot> allFlowerPots;

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

    normalItemBlocks.add(new ItemBlockTFC(register(r, "sea_ice", new BlockIceTFC(FluidsCore.SALT_WATER.get()), CT_MISC)));

    normalItemBlocks.add(new ItemBlockLargeVessel(register(r, "ceramics/fired/large_vessel", new BlockLargeVessel(), CT_POTTERY)));

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

    // Tree
    {
      Builder<BlockLogTFC> logs = ImmutableList.builder();
      Builder<BlockLeavesTFC> leaves = ImmutableList.builder();
      Builder<BlockSaplingTFC> saplings = ImmutableList.builder();
      Builder<BlockPlant> plants = ImmutableList.builder();

      // Other blocks that don't have specific order requirements
      for (Tree wood : TFCRegistries.TREES.getValuesCollection()) {
        // Blocks with specific block collections don't matter
        logs.add(register(r, "wood/log/" + wood.getRegistryName().getPath(), new BlockLogTFC(wood),
                          CT_WOOD));
        leaves.add(
          register(r, "wood/leaves/" + wood.getRegistryName().getPath(), new BlockLeavesTFC(wood),
                   CT_WOOD));
        saplings.add(register(r, "wood/sapling/" + wood.getRegistryName().getPath(),
                              new BlockSaplingTFC(wood), CT_WOOD));
      }

      allLogBlocks = logs.build();
      allLeafBlocks = leaves.build();
      allSaplingBlocks = saplings.build();

      //logs are special
      allLeafBlocks.forEach(x -> normalItemBlocks.add(new ItemBlockTFC(x)));

    }

    //Metal
    {
      Builder<BlockMetalSheet> sheets = ImmutableList.builder();
      Builder<BlockTrapDoorMetalTFC> metalTrapdoors = ImmutableList.builder();

      for (Metal metal : TFCRegistries.METALS.getValuesCollection()) {
        if (Metal.ItemType.SHEET.hasType(metal)) {
          sheets.add(register(r, "sheet/" + metal.getRegistryName()
                                                 .getPath(), new BlockMetalSheet(metal), CT_METAL));
          metalTrapdoors.add(register(r, "trapdoor/" + metal.getRegistryName()
                                                            .getPath(), new BlockTrapDoorMetalTFC(metal), CT_METAL));
        }

      }

      allSheets = sheets.build();
      allTrapDoorMetalBlocks = metalTrapdoors.build();
    }

    // Crops
    {
      Builder<BlockCropTFC> b = ImmutableList.builder();

      for (Crop crop : Crop.values()) {
        b.add(register(r, "crop/" + crop.name().toLowerCase(), crop.createGrowingBlock()));
      }

      allCropBlocks = b.build();
    }

    // Dead crops
    {
      Builder<BlockCropDead> b = ImmutableList.builder();

      for (Crop crop : Crop.values()) {
        b.add(register(r, "dead_crop/" + crop.name().toLowerCase(), crop.createDeadBlock()));
      }

      allDeadCropBlocks = b.build();
    }

    // Fruit trees
    {
      Builder<BlockFruitTreeSapling> fSaplings = ImmutableList.builder();
      Builder<BlockFruitTreeTrunk> fTrunks = ImmutableList.builder();
      Builder<BlockFruitTreeBranch> fBranches = ImmutableList.builder();
      Builder<BlockFruitTreeLeaves> fLeaves = ImmutableList.builder();

      for (FruitTree tree : FruitTree.values()) {
        fSaplings.add(register(r, "fruit_trees/sapling/" + tree.name()
                                                               .toLowerCase(), new BlockFruitTreeSapling(tree), CT_WOOD));
        fTrunks.add(register(r, "fruit_trees/trunk/" + tree.name()
                                                           .toLowerCase(), new BlockFruitTreeTrunk(tree)));
        fBranches.add(register(r, "fruit_trees/branch/" + tree.name()
                                                              .toLowerCase(), new BlockFruitTreeBranch(tree)));
        fLeaves.add(register(r, "fruit_trees/leaves/" + tree.name()
                                                            .toLowerCase(), new BlockFruitTreeLeaves(tree), CT_WOOD));
      }

      allFruitTreeSaplingBlocks = fSaplings.build();
      allFruitTreeTrunkBlocks = fTrunks.build();
      allFruitTreeBranchBlocks = fBranches.build();
      allFruitTreeLeavesBlocks = fLeaves.build();

      Builder<BlockBerryBush> fBerry = ImmutableList.builder();

      for (BerryBush bush : BerryBush.values()) {
        fBerry.add(register(r, "berry_bush/" + bush.name().toLowerCase(), new BlockBerryBush(bush),
                            CT_FOOD));
      }

      allBerryBushBlocks = fBerry.build();

      //Add ItemBlocks
      allFruitTreeSaplingBlocks.forEach(x -> inventoryItemBlocks.add(new ItemBlockTFC(x)));
      allFruitTreeLeavesBlocks.forEach(x -> inventoryItemBlocks.add(new ItemBlockTFC(x)));
      allBerryBushBlocks.forEach(x -> inventoryItemBlocks.add(new ItemBlockTFC(x)));
    }

    {

      Builder<BlockPlant> b = ImmutableList.builder();
      Builder<BlockPlantFlowerPot> pots = ImmutableList.builder();
//      for (PlantType plant : PlantType.getTypes()) {
//        if (plant.getCategory() != PlantCategories.SHORT_GRASS && plant.getCategory() != PlantCategories.TALL_GRASS) {
//          b.add(register(r, "plants/" + plant.getName(), plant.getCategory().create(plant), CT_FLORA));
//        }
//        if (plant.canBePotted()) {
//          pots.add(register(r, "flowerpot/" + plant.getName(), new BlockPlantFlowerPot(plant)));
//        }
//      }
      allPlantBlocks = b.build();
      allFlowerPots = pots.build();

      for (BlockPlant blockPlant : allPlantBlocks) {
        if (blockPlant instanceof BlockPlantFloatingWater blockPlantFloatingWater) {
          inventoryItemBlocks.add(new ItemBlockFloatingWaterTFC(blockPlantFloatingWater));
        } else if (blockPlant.getType().canBePotted()) {
          normalItemBlocks.add(new ItemBlockPlant(blockPlant, blockPlant.getType()));
        } else {
          normalItemBlocks.add(new ItemBlockTFC(blockPlant));
        }
      }
    }

    {
      Builder<BlockPlant> b = ImmutableList.builder();
//      for (PlantType plant : PlantType.getTypes()) {
//        if (plant.getCategory() == PlantCategories.SHORT_GRASS || plant.getCategory() == PlantCategories.TALL_GRASS) {
//          b.add(register(r, "plants/" + plant.getName(), plant.getCategory().create(plant), CT_FLORA));
//        }
//      }
      allGrassBlocks = b.build();
      for (BlockPlant blockPlant : allGrassBlocks) {
        normalItemBlocks.add(new ItemBlockTFC(blockPlant));
      }
    }

    // Registering JEI only blocks (for info)
    inventoryItemBlocks.add(new ItemBlock(register(r, "placed_item", new BlockPlacedItem())));
    // technical blocks
    // These have no ItemBlock or Creative Tab
    register(r, "placed_item_flat", new BlockPlacedItemFlat());
    register(r, "placed_hide", new BlockPlacedHide());

    // Note: if you add blocks you don't need to put them in this list of todos. Feel free to add them where they make sense :)

    // todo: smoke rack (placed with any string, so event based?) + smoke blocks or will we use particles?

    allNormalItemBlocks = normalItemBlocks.build();
    allInventoryItemBlocks = inventoryItemBlocks.build();

    // Register Tile Entities
    // Putting tile entity registration in the respective block can call it multiple times. Just put here to avoid duplicates

    register(BaseTileTickCounter.class, "tick_counter");
    register(TEPlacedItem.class, "placed_item");
    register(TEPlacedItemFlat.class, "placed_item_flat");
    register(TEPlacedHide.class, "placed_hide");
    register(TECropBase.class, "crop_base");
    register(TECropSpreading.class, "crop_spreading");
    register(TEMetalSheet.class, "metal_sheet");
    register(TELargeVessel.class, "large_vessel");
  }

  private static <T extends Block> T register(IForgeRegistry<Block> r, String name, T block,
                                              CreativeTabs ct) {
    block.setCreativeTab(ct);
    return register(r, name, block);
  }

  // todo: change to property of type? (soil & stone maybe?)

  private static <T extends Block> T register(IForgeRegistry<Block> r, String name, T block) {
    block.setRegistryName(MODID_TFC, name);
    block.setTranslationKey(MODID_TFC + "." + name.replace('/', '.'));
    r.register(block);
    return block;
  }

  private static <T extends TileEntity> void register(Class<T> te, String name) {
    TileEntity.register(MODID_TFC + ":" + name, te);
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void registerVanillaOverrides(RegistryEvent.Register<Block> event) {
    // Vanilla Overrides. Used for small tweaks on vanilla items, rather than replacing them outright
    if (ConfigTFC.General.OVERRIDES.enableFrozenOverrides) {
      TerraFirmaCraft.getLog().info(
        "The below warnings about unintended overrides are normal. The override is intended. ;)");
      event.getRegistry().registerAll(
        new BlockIceTFC().setRegistryName("minecraft", "ice").setTranslationKey("ice"),
        new BlockSnowTFC().setRegistryName("minecraft", "snow_layer").setTranslationKey("snow")
      );
    }

    if (ConfigTFC.General.OVERRIDES.enableTorchOverride) {
      event.getRegistry()
           .register(
             new BlockTorchTFC().setRegistryName("minecraft", "torch").setTranslationKey("torch"));
    }
  }
}
