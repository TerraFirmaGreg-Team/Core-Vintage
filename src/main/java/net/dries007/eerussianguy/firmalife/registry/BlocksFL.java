package net.dries007.eerussianguy.firmalife.registry;

import su.terrafirmagreg.modules.animal.init.ItemsAnimal;
import su.terrafirmagreg.modules.core.init.FluidsCore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import net.dries007.eerussianguy.firmalife.init.BushFL;
import net.dries007.eerussianguy.firmalife.init.FruitTreeFL;
import net.dries007.eerussianguy.firmalife.init.StemCrop;
import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.objects.blocks.BlockBeeNest;
import net.dries007.tfc.objects.blocks.BlockBeehive;
import net.dries007.tfc.objects.blocks.BlockBonsai;
import net.dries007.tfc.objects.blocks.BlockBumper;
import net.dries007.tfc.objects.blocks.BlockBushTrellis;
import net.dries007.tfc.objects.blocks.BlockCheesewheel;
import net.dries007.tfc.objects.blocks.BlockCinnamonLeaves;
import net.dries007.tfc.objects.blocks.BlockCinnamonLog;
import net.dries007.tfc.objects.blocks.BlockCinnamonSapling;
import net.dries007.tfc.objects.blocks.BlockClimateStation;
import net.dries007.tfc.objects.blocks.BlockHangingPlanter;
import net.dries007.tfc.objects.blocks.BlockJackOLantern;
import net.dries007.tfc.objects.blocks.BlockJars;
import net.dries007.tfc.objects.blocks.BlockLargePlanter;
import net.dries007.tfc.objects.blocks.BlockLeafMat;
import net.dries007.tfc.objects.blocks.BlockQuadPlanter;
import net.dries007.tfc.objects.blocks.BlockSpout;
import net.dries007.tfc.objects.blocks.BlockStemCrop;
import net.dries007.tfc.objects.blocks.BlockStemFruit;
import net.dries007.tfc.objects.blocks.BlockString;
import net.dries007.tfc.objects.blocks.BlockTorchTFC;
import net.dries007.tfc.objects.blocks.BlockTrellis;
import net.dries007.tfc.objects.blocks.BlockTurntable;
import net.dries007.tfc.objects.blocks.agriculture.BlockBerryBush;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropDead;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeBranch;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeLeaves;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeSapling;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeTrunk;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.items.ItemBlockRot;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.objects.te.TEClimateStation;
import net.dries007.tfc.objects.te.TEHangingPlanter;
import net.dries007.tfc.objects.te.TELeafMat;
import net.dries007.tfc.objects.te.TEPlanter;
import net.dries007.tfc.objects.te.TEStemCrop;
import net.dries007.tfc.objects.te.TEString;
import net.dries007.tfc.objects.te.TETurntable;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.agriculture.BerryBush;
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.agriculture.Food;
import net.dries007.tfc.util.agriculture.FruitTree;

import lombok.Getter;

import java.util.Optional;

import static net.dries007.tfc.objects.CreativeTabsTFC.CT_FLORA;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_FOOD;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_MISC;
import static net.dries007.tfc.objects.CreativeTabsTFC.CT_WOOD;
import static su.terrafirmagreg.api.data.Reference.MODID_FL;

@Mod.EventBusSubscriber(modid = MODID_FL)
@GameRegistry.ObjectHolder(MODID_FL)
public class BlocksFL {

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

  @Getter
  private static ImmutableList<ItemBlock> allIBs;
  @Getter
  private static ImmutableList<Block> allNormalIBs = Helpers.getNull();
  @Getter
  private static ImmutableList<Block> allFoodIBs = Helpers.getNull();
  @Getter
  private static ImmutableList<BlockFruitTreeLeaves> allFruitLeaves = Helpers.getNull();
  @Getter
  private static ImmutableList<BlockFruitTreeSapling> allFruitSaps = Helpers.getNull();
  @Getter
  private static ImmutableList<BlockCropDead> allDeadCrops = Helpers.getNull();
  @Getter
  private static ImmutableList<BlockStemCrop> allCropBlocks = Helpers.getNull();
  @Getter
  private static ImmutableList<BlockJackOLantern> allJackOLanterns = Helpers.getNull();
  @Getter
  private static ImmutableList<Block> allInventoryIBs = Helpers.getNull();
  @Getter
  private static ImmutableList<BlockBonsai> allBonsai = Helpers.getNull();

  @SubscribeEvent
  public static void registerBlocks(RegistryEvent.Register<Block> event) {
    IForgeRegistry<Block> r = event.getRegistry();

    Builder<ItemBlock> IBs = ImmutableList.builder();
    Builder<Block> normalIBs = ImmutableList.builder();
    Builder<Block> foodIBs = ImmutableList.builder();
    Builder<BlockFruitTreeLeaves> fruitLeaves = ImmutableList.builder();
    Builder<BlockFruitTreeSapling> fruitSaps = ImmutableList.builder();
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
      doFruitAdditions(r, name, bonsais, Optional.of(fruitTree));
    }
    doFruitAdditions(r, "cinnamon", bonsais, Optional.empty());
    for (IFruitTree fruitTree : FruitTree.values()) {
      String name = fruitTree.getName().toLowerCase();
      doFruitAdditions(r, name, bonsais, Optional.of(fruitTree));
    }

    normalIBs.add(register(r, "leaf_mat", new BlockLeafMat(), CT_MISC));
    normalIBs.add(register(r, "cinnamon_log", new BlockCinnamonLog(), CT_WOOD));
    normalIBs.add(register(r, "cinnamon_leaves", new BlockCinnamonLeaves(), CT_WOOD));
    register(r, "cinnamon_sapling", new BlockCinnamonSapling(), CT_WOOD);
    normalIBs.add(register(r, "climate_station", new BlockClimateStation(0), CT_MISC));
    for (int i = 1; i < 6; i++) {
      normalIBs.add(register(r, "climate_station_" + i, new BlockClimateStation(i), CT_MISC));
    }
    register(r, "quad_planter", new BlockQuadPlanter(), CT_MISC);
    register(r, "large_planter", new BlockLargePlanter(), CT_MISC);
    normalIBs.add(register(r, "pumpkin_hanging_planter",
                           new BlockHangingPlanter(() -> Item.getItemFromBlock(BlocksFL.PUMPKIN_FRUIT), () -> ItemSeedsTFC.get(StemCrop.PUMPKIN), 13),
                           CT_MISC));
    normalIBs.add(register(r, "melon_hanging_planter",
                           new BlockHangingPlanter(() -> Item.getItemFromBlock(BlocksFL.MELON_FRUIT), () -> ItemSeedsTFC.get(StemCrop.MELON), 13),
                           CT_MISC));
    normalIBs.add(register(r, "squash_hanging_planter",
                           new BlockHangingPlanter(() -> ItemFoodTFC.get(Food.SQUASH), () -> ItemSeedsTFC.get(Crop.SQUASH), 13), CT_MISC));
    register(r, "wool_string", new BlockString(() -> ItemsAnimal.WOOL_YARN));
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
      normalIBs.add(register(r, bush.name()
                                    .toLowerCase() + "_trellis", new BlockBushTrellis(bush), CT_MISC));
    }

    for (BushFL bush : BushFL.values()) {
      normalIBs.add(register(r, bush.name().toLowerCase() + "_bush", new BlockBerryBush(bush), CT_FLORA));
      normalIBs.add(register(r, bush.name()
                                    .toLowerCase() + "_trellis", new BlockBushTrellis(bush), CT_MISC));
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
    allIBs = IBs.build();
    allDeadCrops = deadCrops.build();
    allCropBlocks = cropBlocks.build();

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

  private static <T extends Block> T register(IForgeRegistry<Block> r, String name, T block) {
    block.setRegistryName(MODID_FL, name);
    block.setTranslationKey(MODID_FL + "." + name.replace('/', '.'));
    r.register(block);
    return block;
  }

  private static <T extends Block> T register(IForgeRegistry<Block> r, String name, T block, CreativeTabs ct) {
    block.setCreativeTab(ct);
    return register(r, name, block);
  }

  private static void doFruitAdditions(IForgeRegistry<Block> r, String name,
                                       Builder<BlockBonsai> bonsais,
                                       Optional<IFruitTree> optionalTree) {
    optionalTree.ifPresent(tree -> bonsais.add(register(r, name + "_bonsai_pot", new BlockBonsai(() -> tree.getFoodDrop()
                                                                                                           .getItem(), () -> Item.getItemFromBlock(BlockFruitTreeSapling.get(tree)), 19, 4, Material.CLAY), CT_MISC))
    );
  }

  private static <T extends TileEntity> void register(Class<T> tile, String name) {
    TileEntity.register(MODID_FL + ":" + name, tile);
  }
}
