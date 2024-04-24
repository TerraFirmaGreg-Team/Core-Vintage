package com.eerussianguy.firmalife.registry;

import su.terrafirmagreg.modules.animal.init.ItemsAnimal;

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


import com.eerussianguy.firmalife.blocks.BlockBeeNest;
import com.eerussianguy.firmalife.blocks.BlockBeehive;
import com.eerussianguy.firmalife.blocks.BlockBonsai;
import com.eerussianguy.firmalife.blocks.BlockBumper;
import com.eerussianguy.firmalife.blocks.BlockBushTrellis;
import com.eerussianguy.firmalife.blocks.BlockCheesewheel;
import com.eerussianguy.firmalife.blocks.BlockCinnamonLeaves;
import com.eerussianguy.firmalife.blocks.BlockCinnamonLog;
import com.eerussianguy.firmalife.blocks.BlockCinnamonSapling;
import com.eerussianguy.firmalife.blocks.BlockClimateStation;
import com.eerussianguy.firmalife.blocks.BlockGreenhouseDoor;
import com.eerussianguy.firmalife.blocks.BlockGreenhouseRoof;
import com.eerussianguy.firmalife.blocks.BlockGreenhouseWall;
import com.eerussianguy.firmalife.blocks.BlockHangingPlanter;
import com.eerussianguy.firmalife.blocks.BlockJackOLantern;
import com.eerussianguy.firmalife.blocks.BlockJars;
import com.eerussianguy.firmalife.blocks.BlockLargePlanter;
import com.eerussianguy.firmalife.blocks.BlockLeafMat;
import com.eerussianguy.firmalife.blocks.BlockOven;
import com.eerussianguy.firmalife.blocks.BlockOvenChimney;
import com.eerussianguy.firmalife.blocks.BlockOvenWall;
import com.eerussianguy.firmalife.blocks.BlockQuadPlanter;
import com.eerussianguy.firmalife.blocks.BlockSpout;
import com.eerussianguy.firmalife.blocks.BlockStemCrop;
import com.eerussianguy.firmalife.blocks.BlockStemFruit;
import com.eerussianguy.firmalife.blocks.BlockString;
import com.eerussianguy.firmalife.blocks.BlockTrellis;
import com.eerussianguy.firmalife.blocks.BlockTurntable;
import com.eerussianguy.firmalife.init.BushFL;
import com.eerussianguy.firmalife.init.FruitTreeFL;
import com.eerussianguy.firmalife.init.StemCrop;
import com.eerussianguy.firmalife.items.ItemBlockRot;
import com.eerussianguy.firmalife.te.TEClimateStation;
import com.eerussianguy.firmalife.te.TEHangingPlanter;
import com.eerussianguy.firmalife.te.TELeafMat;
import com.eerussianguy.firmalife.te.TEOven;
import com.eerussianguy.firmalife.te.TEPlanter;
import com.eerussianguy.firmalife.te.TEStemCrop;
import com.eerussianguy.firmalife.te.TEString;
import com.eerussianguy.firmalife.te.TETurntable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.objects.blocks.BlockTorchTFC;
import net.dries007.tfc.objects.blocks.agriculture.BlockBerryBush;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropDead;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeBranch;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeLeaves;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeSapling;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeTrunk;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.agriculture.BerryBush;
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.agriculture.Food;
import net.dries007.tfc.util.agriculture.FruitTree;

import java.util.Optional;

import static net.dries007.tfc.objects.CreativeTabsTFC.*;
import static su.terrafirmagreg.api.lib.Constants.MODID_FL;

@Mod.EventBusSubscriber(modid = MODID_FL)
@GameRegistry.ObjectHolder(MODID_FL)
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

    public static ImmutableList<BlockCropDead> getAllDeadCrops() {
        return allDeadCrops;
    }

    public static ImmutableList<BlockStemCrop> getAllCropBlocks() {
        return allCropBlocks;
    }

    public static ImmutableList<BlockJackOLantern> getAllJackOLanterns() {
        return allJackOLanterns;
    }

    public static ImmutableList<Block> getAllInventoryIBs() {
        return allInventoryIBs;
    }

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

        normalIBs.add(register(r, "oven", new BlockOven(), CT_MISC));
        normalIBs.add(register(r, "oven_wall", new BlockOvenWall(), CT_MISC));
        normalIBs.add(register(r, "oven_chimney", new BlockOvenChimney(), CT_MISC));
        normalIBs.add(register(r, "leaf_mat", new BlockLeafMat(), CT_MISC));
        normalIBs.add(register(r, "cinnamon_log", new BlockCinnamonLog(), CT_WOOD));
        normalIBs.add(register(r, "cinnamon_leaves", new BlockCinnamonLeaves(), CT_WOOD));
        register(r, "cinnamon_sapling", new BlockCinnamonSapling(), CT_WOOD);
        normalIBs.add(register(r, "greenhouse_wall", new BlockGreenhouseWall(), CT_MISC));
        normalIBs.add(register(r, "greenhouse_roof", new BlockGreenhouseRoof(), CT_MISC));
        register(r, "greenhouse_door", new BlockGreenhouseDoor(), CT_MISC);
        normalIBs.add(register(r, "climate_station", new BlockClimateStation(0), CT_MISC));
        for (int i = 1; i < 6; i++)
            normalIBs.add(register(r, "climate_station_" + i, new BlockClimateStation(i), CT_MISC));
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

        register(TEOven.class, "oven");
        register(TEPlanter.class, "quad_planter");
        register(TELeafMat.class, "leaf_mat");
        register(TEHangingPlanter.class, "hanging_planter");
        register(TEString.class, "string");
        register(TEStemCrop.class, "stem_crop");
        register(TEClimateStation.class, "climate_station");
        register(TETurntable.class, "turntable");
        //needs fix
        FluidsTFC.getWrapper(FluidsTFC.COCONUT_MILK.get());
        FluidsTFC.getWrapper(FluidsTFC.YAK_MILK.get());
        FluidsTFC.getWrapper(FluidsTFC.GOAT_MILK.get());
        FluidsTFC.getWrapper(FluidsTFC.ZEBU_MILK.get());
    }

    private static void doFruitAdditions(IForgeRegistry<Block> r, String name,
                                         Builder<BlockBonsai> bonsais,
                                         Optional<IFruitTree> optionalTree) {
        optionalTree.ifPresent(tree -> bonsais.add(register(r, name + "_bonsai_pot", new BlockBonsai(() -> tree.getFoodDrop()
                .getItem(), () -> Item.getItemFromBlock(BlockFruitTreeSapling.get(tree)), 19, 4, Material.CLAY), CT_MISC))
        );
    }

    private static <T extends Block> T register(IForgeRegistry<Block> r, String name, T block, CreativeTabs ct) {
        block.setCreativeTab(ct);
        return register(r, name, block);
    }

    private static <T extends Block> T register(IForgeRegistry<Block> r, String name, T block) {
        block.setRegistryName(MODID_FL, name);
        block.setTranslationKey(MODID_FL + "." + name.replace('/', '.'));
        r.register(block);
        return block;
    }

    private static <T extends TileEntity> void register(Class<T> te, String name) {
        TileEntity.register(MODID_FL + ":" + name, te);
    }
}
