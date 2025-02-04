package net.dries007.firmalife.registry;

import su.terrafirmagreg.modules.animal.init.ItemsAnimal;
import su.terrafirmagreg.modules.core.init.FluidsCore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import net.dries007.firmalife.blocks.BlockBeeNest;
import net.dries007.firmalife.blocks.BlockBeehive;
import net.dries007.firmalife.blocks.BlockBonsai;
import net.dries007.firmalife.blocks.BlockBumper;
import net.dries007.firmalife.blocks.BlockBushTrellis;
import net.dries007.firmalife.blocks.BlockCheesewheel;
import net.dries007.firmalife.blocks.BlockCinnamonLeaves;
import net.dries007.firmalife.blocks.BlockCinnamonLog;
import net.dries007.firmalife.blocks.BlockCinnamonSapling;
import net.dries007.firmalife.blocks.BlockClimateStation;
import net.dries007.firmalife.blocks.BlockFruitDoor;
import net.dries007.firmalife.blocks.BlockFruitFence;
import net.dries007.firmalife.blocks.BlockFruitFenceGate;
import net.dries007.firmalife.blocks.BlockFruitTrapDoor;
import net.dries007.firmalife.blocks.BlockGreenhouseDoor;
import net.dries007.firmalife.blocks.BlockGreenhouseRoof;
import net.dries007.firmalife.blocks.BlockGreenhouseWall;
import net.dries007.firmalife.blocks.BlockHangingPlanter;
import net.dries007.firmalife.blocks.BlockJackOLantern;
import net.dries007.firmalife.blocks.BlockJars;
import net.dries007.firmalife.blocks.BlockLargePlanter;
import net.dries007.firmalife.blocks.BlockLeafMat;
import net.dries007.firmalife.blocks.BlockOven;
import net.dries007.firmalife.blocks.BlockOvenChimney;
import net.dries007.firmalife.blocks.BlockOvenWall;
import net.dries007.firmalife.blocks.BlockQuadPlanter;
import net.dries007.firmalife.blocks.BlockSpout;
import net.dries007.firmalife.blocks.BlockStemCrop;
import net.dries007.firmalife.blocks.BlockStemFruit;
import net.dries007.firmalife.blocks.BlockString;
import net.dries007.firmalife.blocks.BlockTrellis;
import net.dries007.firmalife.blocks.BlockTurntable;
import net.dries007.firmalife.init.BushFL;
import net.dries007.firmalife.init.FruitTreeFL;
import net.dries007.firmalife.init.StemCrop;
import net.dries007.firmalife.items.ItemBlockRot;
import net.dries007.firmalife.te.TEClimateStation;
import net.dries007.firmalife.te.TEHangingPlanter;
import net.dries007.firmalife.te.TELeafMat;
import net.dries007.firmalife.te.TEOven;
import net.dries007.firmalife.te.TEPlanter;
import net.dries007.firmalife.te.TEStemCrop;
import net.dries007.firmalife.te.TEString;
import net.dries007.firmalife.te.TETurntable;
import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.objects.blocks.BlockFluidTFC;
import net.dries007.tfc.objects.blocks.BlockTorchTFC;
import net.dries007.tfc.objects.blocks.agriculture.BlockBerryBush;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropDead;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeBranch;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeLeaves;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeSapling;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeTrunk;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.agriculture.BerryBush;
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.agriculture.Food;
import net.dries007.tfc.util.agriculture.FruitTree;

import java.util.Optional;

import static net.dries007.firmalife.FirmaLife.MOD_ID;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_FLORA;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_FOOD;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_MISC;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_WOOD;

@Mod.EventBusSubscriber(modid = MOD_ID)
@GameRegistry.ObjectHolder(MOD_ID)
public class BlocksFL {

  @GameRegistry.ObjectHolder("oven")
  public static final BlockOven OVEN = Helpers.getNull();
  @GameRegistry.ObjectHolder("oven_wall")
  public static final BlockOvenWall OVEN_WALL = Helpers.getNull();
  @GameRegistry.ObjectHolder("oven_chimney")
  public static final BlockOvenChimney OVEN_CHIMNEY = Helpers.getNull();
  @GameRegistry.ObjectHolder("pumpkin_fruit")
  public static final BlockStemFruit PUMPKIN_FRUIT = Helpers.getNull();
  @GameRegistry.ObjectHolder("melon_fruit")
  public static final BlockStemFruit MELON_FRUIT = Helpers.getNull();
  @GameRegistry.ObjectHolder("leaf_mat")
  public static final BlockLeafMat LEAF_MAT = Helpers.getNull();
  @GameRegistry.ObjectHolder("cinnamon_log")
  public static final BlockCinnamonLog CINNAMON_LOG = Helpers.getNull();
  @GameRegistry.ObjectHolder("cinnamon_leaves")
  public static final BlockCinnamonLeaves CINNAMON_LEAVES = Helpers.getNull();
  @GameRegistry.ObjectHolder("cinnamon_sapling")
  public static final BlockCinnamonSapling CINNAMON_SAPLING = Helpers.getNull();
  @GameRegistry.ObjectHolder("greenhouse_door")
  public static final BlockGreenhouseDoor GREENHOUSE_DOOR = Helpers.getNull();
  @GameRegistry.ObjectHolder("greenhouse_roof")
  public static final BlockGreenhouseRoof GREENHOUSE_ROOF = Helpers.getNull();
  @GameRegistry.ObjectHolder("greenhouse_wall")
  public static final BlockGreenhouseWall GREENHOUSE_WALL = Helpers.getNull();
  @GameRegistry.ObjectHolder("quad_planter")
  public static final BlockQuadPlanter QUAD_PLANTER = Helpers.getNull();
  @GameRegistry.ObjectHolder("large_planter")
  public static final BlockLargePlanter LARGE_PLANTER = Helpers.getNull();
  @GameRegistry.ObjectHolder("wool_string")
  public static final BlockString WOOL_STRING = Helpers.getNull();
  @GameRegistry.ObjectHolder("trellis")
  public static final BlockTrellis TRELLIS = Helpers.getNull();
  @GameRegistry.ObjectHolder("honey_jar")
  public static final BlockJars HONEY_JAR = Helpers.getNull();
  @GameRegistry.ObjectHolder("gouda_wheel")
  public static final BlockCheesewheel GOUDA_WHEEL = Helpers.getNull();
  @GameRegistry.ObjectHolder("feta_wheel")
  public static final BlockCheesewheel FETA_WHEEL = Helpers.getNull();
  @GameRegistry.ObjectHolder("shosha_wheel")
  public static final BlockCheesewheel SHOSHA_WHEEL = Helpers.getNull();
  @GameRegistry.ObjectHolder("bee_nest")
  public static final BlockBeeNest BEE_NEST = Helpers.getNull();
  @GameRegistry.ObjectHolder("spout")
  public static final BlockSpout SPOUT = Helpers.getNull();
  @GameRegistry.ObjectHolder("sprinkler")
  public static final BlockSpout SPRINKLER = Helpers.getNull();
  @GameRegistry.ObjectHolder("turntable")
  public static final BlockTurntable TURNTABLE = Helpers.getNull();

  private static ImmutableList<ItemBlock> allIBs;
  private static ImmutableList<Block> allNormalIBs = Helpers.getNull();
  private static ImmutableList<Block> allFoodIBs = Helpers.getNull();
  private static ImmutableList<BlockFruitTreeLeaves> allFruitLeaves = Helpers.getNull();
  private static ImmutableList<BlockFruitTreeSapling> allFruitSaps = Helpers.getNull();
  private static ImmutableList<BlockFruitFence> allFruitFences = Helpers.getNull();
  private static ImmutableList<BlockFruitFenceGate> allFruitFenceGates = Helpers.getNull();
  private static ImmutableList<BlockFruitDoor> allFruitDoors = Helpers.getNull();
  private static ImmutableList<BlockFruitTrapDoor> allFruitTrapDoors = Helpers.getNull();
  private static ImmutableList<BlockFluidBase> allFluidBlocks = Helpers.getNull();
  private static ImmutableList<BlockCropDead> allDeadCrops = Helpers.getNull();
  private static ImmutableList<BlockStemCrop> allCropBlocks = Helpers.getNull();
  private static ImmutableList<BlockJackOLantern> allJackOLanterns = Helpers.getNull();
  private static ImmutableList<Block> allInventoryIBs = Helpers.getNull();
  private static ImmutableList<BlockBonsai> allBonsai = Helpers.getNull();

  public static ImmutableList<ItemBlock> getAllIBs() {
    return allIBs;
  }

  public static ImmutableList<Block> getAllNormalIBs() {
    return allNormalIBs;
  }

  public static ImmutableList<Block> getAllFoodIBs() {
    return allFoodIBs;
  }

  public static ImmutableList<BlockFruitTreeLeaves> getAllFruitLeaves() {
    return allFruitLeaves;
  }

  public static ImmutableList<BlockBonsai> getAllBonsai() {
    return allBonsai;
  }

  public static ImmutableList<BlockFruitTreeSapling> getAllFruitSaps() {
    return allFruitSaps;
  }

  public static ImmutableList<BlockFruitFence> getAllFruitFences() {return allFruitFences;}

  public static ImmutableList<BlockFruitFenceGate> getAllFruitFenceGates() {return allFruitFenceGates;}

  public static ImmutableList<BlockFruitDoor> getAllFruitDoors() {return allFruitDoors;}

  public static ImmutableList<BlockFruitTrapDoor> getAllFruitTrapdoors() {return allFruitTrapDoors;}

  public static ImmutableList<BlockCropDead> getAllDeadCrops() {
    return allDeadCrops;
  }

  public static ImmutableList<BlockStemCrop> getAllCropBlocks() {
    return allCropBlocks;
  }

  public static ImmutableList<BlockFluidBase> getAllFluidBlocks() {
    return allFluidBlocks;
  }

  public static ImmutableList<BlockJackOLantern> getAllJackOLanterns() {
    return allJackOLanterns;
  }

  public static ImmutableList<Block> getAllInventoryIBs() {
    return allInventoryIBs;
  }

  @SubscribeEvent
  public static void registerBlocks(RegistryEvent.Register<Block> event) {
    FluidsFL.registerFluids();//this has to come first
    IForgeRegistry<Block> r = event.getRegistry();

    Builder<ItemBlock> IBs = ImmutableList.builder();
    Builder<Block> normalIBs = ImmutableList.builder();
    Builder<Block> foodIBs = ImmutableList.builder();
    Builder<BlockFruitTreeLeaves> fruitLeaves = ImmutableList.builder();
    Builder<BlockFruitTreeSapling> fruitSaps = ImmutableList.builder();
    Builder<BlockFruitFence> fruitFences = ImmutableList.builder();
    Builder<BlockFruitFenceGate> fruitFenceGates = ImmutableList.builder();
    Builder<BlockFruitDoor> fruitDoors = ImmutableList.builder();
    Builder<BlockFruitTrapDoor> fruitTrapdoors = ImmutableList.builder();
    Builder<BlockCropDead> deadCrops = ImmutableList.builder();
    Builder<BlockStemCrop> cropBlocks = ImmutableList.builder();
    Builder<BlockJackOLantern> jackOLanterns = ImmutableList.builder();
    Builder<Block> invIBs = ImmutableList.builder();
    Builder<BlockBonsai> bonsais = ImmutableList.builder();

    for (FruitTreeFL fruitTree : FruitTreeFL.values()) {
      String name = fruitTree.getName().toLowerCase();
      register(r, name + "_branch", new BlockFruitTreeBranch(fruitTree));
      fruitLeaves.add(register(r, name + "_leaves", new BlockFruitTreeLeaves(fruitTree), CT_WOOD));
      fruitSaps.add(register(r, name + "_sapling", new BlockFruitTreeSapling(fruitTree), CT_WOOD));
      register(r, name + "_trunk", new BlockFruitTreeTrunk(fruitTree));
      doFruitAdditions(r, name, fruitFences, fruitFenceGates, fruitDoors, fruitTrapdoors, bonsais, Optional.of(fruitTree));
    }
    doFruitAdditions(r, "cinnamon", fruitFences, fruitFenceGates, fruitDoors, fruitTrapdoors, bonsais, Optional.empty());
    for (IFruitTree fruitTree : FruitTree.values()) {
      String name = fruitTree.getName().toLowerCase();
      doFruitAdditions(r, name, fruitFences, fruitFenceGates, fruitDoors, fruitTrapdoors, bonsais, Optional.of(fruitTree));
    }

    normalIBs.add(register(r, "oven", new BlockOven(), CT_MISC));
    normalIBs.add(register(r, "oven_wall", new BlockOvenWall(), CT_MISC));
    normalIBs.add(register(r, "oven_chimney", new BlockOvenChimney(), CT_MISC));
    normalIBs.add(register(r, "leaf_mat", new BlockLeafMat(), CT_MISC));
    normalIBs.add(register(r, "cinnamon_log", new BlockCinnamonLog(), CT_WOOD));
    normalIBs.add(register(r, "cinnamon_leaves", new BlockCinnamonLeaves(), CT_WOOD));
    register(r, "cinnamon_sapling", new BlockCinnamonSapling(), CT_WOOD);
    normalIBs.add(register(r, "greenhouse_wall", new BlockGreenhouseWall(), CT_WOOD));
    normalIBs.add(register(r, "greenhouse_roof", new BlockGreenhouseRoof(), CT_WOOD));
    register(r, "greenhouse_door", new BlockGreenhouseDoor(), CT_WOOD);
    normalIBs.add(register(r, "climate_station", new BlockClimateStation(0), CT_MISC));
    for (int i = 1; i < 6; i++) {normalIBs.add(register(r, "climate_station_" + i, new BlockClimateStation(i), CT_MISC));}
    register(r, "quad_planter", new BlockQuadPlanter(), CT_MISC);
    register(r, "large_planter", new BlockLargePlanter(), CT_MISC);
    normalIBs.add(register(r, "pumpkin_hanging_planter", new BlockHangingPlanter(() -> Item.getItemFromBlock(BlocksFL.PUMPKIN_FRUIT), () -> ItemSeedsTFC.get(StemCrop.PUMPKIN), 13), CT_MISC));
    normalIBs.add(register(r, "melon_hanging_planter", new BlockHangingPlanter(() -> Item.getItemFromBlock(BlocksFL.MELON_FRUIT), () -> ItemSeedsTFC.get(StemCrop.MELON), 13), CT_MISC));
    normalIBs.add(register(r, "squash_hanging_planter", new BlockHangingPlanter(() -> ItemFoodTFC.get(Food.SQUASH), () -> ItemSeedsTFC.get(Crop.SQUASH), 13), CT_MISC));
    register(r, "wool_string", new BlockString(() -> ItemsAnimal.WOOL_YARN.get()));
    normalIBs.add(register(r, "trellis", new BlockTrellis(), CT_MISC));
    normalIBs.add(register(r, "beehive", new BlockBeehive(), CT_MISC));
    register(r, "honey_jar", new BlockJars(() -> ItemsFL.HONEY_JAR), CT_FOOD);
    normalIBs.add(register(r, "bumper", new BlockBumper(), CT_MISC));
    normalIBs.add(register(r, "candle", new BlockTorchTFC(), CT_MISC));
    register(r, "bee_nest", new BlockBeeNest(), CT_MISC);
    normalIBs.add(register(r, "spout", new BlockSpout(false), CT_MISC));
    normalIBs.add(register(r, "sprinkler", new BlockSpout(true), CT_MISC));
    normalIBs.add(register(r, "turntable", new BlockTurntable(), CT_MISC));

    normalIBs.add(register(r, "cheddar_wheel", new BlockCheesewheel(() -> ItemsFL.CHEDDAR), CT_FOOD));
    normalIBs.add(register(r, "chevre_wheel", new BlockCheesewheel(() -> ItemsFL.CHEVRE), CT_FOOD));
    normalIBs.add(register(r, "rajya_metok_wheel", new BlockCheesewheel(() -> ItemsFL.RAJYA_METOK), CT_FOOD));
    normalIBs.add(register(r, "gouda_wheel", new BlockCheesewheel(() -> ItemsFL.GOUDA), CT_FOOD));
    normalIBs.add(register(r, "feta_wheel", new BlockCheesewheel(() -> ItemsFL.FETA), CT_FOOD));
    normalIBs.add(register(r, "shosha_wheel", new BlockCheesewheel(() -> ItemsFL.SHOSHA), CT_FOOD));

    for (BerryBush bush : BerryBush.values()) {
      normalIBs.add(register(r, bush.name().toLowerCase() + "_trellis", new BlockBushTrellis(bush), CT_MISC));
    }

    for (BushFL bush : BushFL.values()) {
      normalIBs.add(register(r, bush.name().toLowerCase() + "_bush", new BlockBerryBush(bush), CT_FLORA));
      normalIBs.add(register(r, bush.name().toLowerCase() + "_trellis", new BlockBushTrellis(bush), CT_MISC));
    }

    for (BlockJackOLantern.Carving carving : BlockJackOLantern.Carving.values()) {
      jackOLanterns.add(register(r, "lit_pumpkin_" + carving.getName(), new BlockJackOLantern(carving), CT_MISC));
    }

    foodIBs.add(register(r, "pumpkin_fruit", new BlockStemFruit(), CT_FLORA));
    foodIBs.add(register(r, "melon_fruit", new BlockStemFruit(), CT_FLORA));

    for (StemCrop crop : StemCrop.values()) {
      deadCrops.add(register(r, "dead_crop/" + crop.name().toLowerCase(), new BlockCropDead(crop)));
      cropBlocks.add(register(r, "crop/" + crop.name().toLowerCase(), BlockStemCrop.create(crop)));
    }

    Builder<BlockFluidBase> fluids = ImmutableList.builder();
    for (FluidWrapper wrapper : FluidsFL.getAllFiniteFluids()) {
      fluids.add(register(r, wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER)));
    }
    allFluidBlocks = fluids.build();
    allNormalIBs = normalIBs.build();
    allNormalIBs.forEach((x) -> {
      IBs.add(new ItemBlockTFC(x));
    });
    allInventoryIBs = invIBs.build();
    allFoodIBs = foodIBs.build();
    allFoodIBs.forEach((x) -> {
      IBs.add(new ItemBlockRot(x));
    });
    allJackOLanterns = jackOLanterns.build();
    allJackOLanterns.forEach((x) -> {
      IBs.add(new ItemBlockTFC(x));
    });
    allFruitLeaves = fruitLeaves.build();
    allFruitLeaves.forEach((x) -> {
      IBs.add(new ItemBlockTFC(x));
    });
    allBonsai = bonsais.build();
    allBonsai.forEach((x) -> {
      IBs.add(new ItemBlockTFC(x));
    });
    allFruitSaps = fruitSaps.build();
    allFruitSaps.forEach((x) -> {
      ItemBlock ib = new ItemBlockTFC(x);
      IBs.add(ib);
    });
    allFruitFences = fruitFences.build();
    allFruitFences.forEach((x) -> {
      IBs.add(new ItemBlockTFC(x));
    });
    allFruitFenceGates = fruitFenceGates.build();
    allFruitFenceGates.forEach((x) -> {
      IBs.add(new ItemBlockTFC(x));
    });
    allFruitDoors = fruitDoors.build();

    allFruitTrapDoors = fruitTrapdoors.build();
    allFruitTrapDoors.forEach((x) -> {
      IBs.add(new ItemBlockTFC(x));
    });
    allIBs = IBs.build();
    allDeadCrops = deadCrops.build();
    allCropBlocks = cropBlocks.build();

    register(TEOven.class, "oven");
    register(TEPlanter.class, "quad_planter");
    register(TELeafMat.class, "leaf_mat");
    register(TEHangingPlanter.class, "hanging_planter");
    register(TEString.class, "string");
    register(TEStemCrop.class, "stem_crop");
    register(TEClimateStation.class, "climate_station");
    register(TETurntable.class, "turntable");
    //needs fix
    FluidsTFC.getWrapper(FluidsCore.COCONUT_MILK.get());
    FluidsTFC.getWrapper(FluidsCore.YAK_MILK.get());
    FluidsTFC.getWrapper(FluidsCore.GOAT_MILK.get());
    FluidsTFC.getWrapper(FluidsCore.ZEBU_MILK.get());
  }

  private static void doFruitAdditions(IForgeRegistry<Block> r, String name, Builder<BlockFruitFence> fruitFences, Builder<BlockFruitFenceGate> fruitFenceGates, Builder<BlockFruitDoor> fruitDoors, Builder<BlockFruitTrapDoor> fruitTrapdoors, Builder<BlockBonsai> bonsais, Optional<IFruitTree> optionalTree) {
    fruitFences.add(register(r, name + "_fence", new BlockFruitFence(), CT_WOOD));
    fruitFenceGates.add(register(r, name + "_fence_gate", new BlockFruitFenceGate(), CT_WOOD));
    fruitDoors.add(register(r, name + "_door", new BlockFruitDoor(), CT_WOOD));
    fruitTrapdoors.add(register(r, name + "_trapdoor", new BlockFruitTrapDoor(), CT_WOOD));
    optionalTree.ifPresent(tree -> bonsais.add(register(r, name + "_bonsai_pot", new BlockBonsai(() -> tree.getFoodDrop()
      .getItem(), () -> Item.getItemFromBlock(BlockFruitTreeSapling.get(tree)), 19, 4, Material.CLAY), CT_WOOD))
    );
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
