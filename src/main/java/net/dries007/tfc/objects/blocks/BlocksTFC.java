package net.dries007.tfc.objects.blocks;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.agriculture.BerryBush;
import net.dries007.tfc.api.types.agriculture.Crop;
import net.dries007.tfc.api.types.agriculture.FruitTree;
import net.dries007.tfc.api.types.rock.util.IRockBlock;
import net.dries007.tfc.api.types.soil.util.ISoilBlock;
import net.dries007.tfc.objects.blocks.agriculture.*;
import net.dries007.tfc.objects.blocks.fluid.BlockFluidHotWater;
import net.dries007.tfc.objects.blocks.fluid.BlockFluidTFC;
import net.dries007.tfc.objects.blocks.fluid.BlockFluidWater;
import net.dries007.tfc.objects.blocks.metal.BlockMetalAnvil;
import net.dries007.tfc.objects.blocks.metal.BlockMetalCladding;
import net.dries007.tfc.objects.blocks.soil.BlockSoilPeat;
import net.dries007.tfc.objects.blocks.soil.BlockSoilPeatGrass;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.fluids.properties.FluidWrapper;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.rock.RockVariant.*;
import static net.dries007.tfc.api.types.soil.SoilVariant.DIRT;
import static net.dries007.tfc.api.types.soil.SoilVariant.DRY_GRASS;
import static net.dries007.tfc.objects.CreativeTabsTFC.*;
import static net.dries007.tfc.util.Helpers.getNull;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MOD_ID)
@GameRegistry.ObjectHolder(MOD_ID)
public final class BlocksTFC {
    public static final BlockIceTFC SEA_ICE = getNull();
    // All these are for use in model registration. Do not use for block lookups.
    // Use the static get methods in the classes instead.
    private static ImmutableList<ItemBlock> allNormalItemBlocks;
    private static ImmutableList<ItemBlock> allInventoryItemBlocks;
    private static ImmutableList<BlockFluidBase> allFluidBlocks;
    private static ImmutableList<BlockMetalAnvil> allAnvils;
    private static ImmutableList<BlockMetalCladding> allCladdings;
    private static ImmutableList<BlockCropTFC> allCropBlocks;
    private static ImmutableList<BlockCropDead> allDeadCropBlocks;
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


    public static ImmutableList<BlockFluidBase> getAllFluidBlocks() {
        return allFluidBlocks;
    }

    public static ImmutableList<BlockMetalAnvil> getAllAnvils() {
        return allAnvils;
    }

    public static ImmutableList<BlockMetalCladding> getAllCladdings() {
        return allCladdings;
    }

    public static ImmutableList<BlockCropTFC> getAllCropBlocks() {
        return allCropBlocks;
    }

    public static ImmutableList<BlockCropDead> getAllDeadCropBlocks() {
        return allDeadCropBlocks;
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


        //=== Other ==================================================================================================//


        normalItemBlocks.add(new ItemBlockTFC(register(r, "sea_ice", new BlockIceTFC(FluidsTFC.SALT_WATER.get()), MISC)));


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
            for (EnumDyeColor color : EnumDyeColor.values()) {
                FluidWrapper wrapper = FluidsTFC.getFluidFromDye(color);
                b.add(register(r, "fluid/" + wrapper.get().getName(), new BlockFluidTFC(wrapper.get(), Material.WATER)));
            }
            allFluidBlocks = b.build();
        }

        {
//            Builder<BlockAnvilTFC> anvils = ImmutableList.builder();
//            Builder<BlockMetalCladding> claddings = ImmutableList.builder();
//
//            for (var material : GregTechAPI.materialManager.getRegistry("gregtech")) {
//                if (material.hasFlag(TFGMaterialFlags.GENERATE_ANVIL))
//                    anvils.add(register(r, "anvil/" + material.getName(), new BlockAnvilTFC(material), METAL));
//
//                if (material == Materials.Iron)
//                    claddings.add(register(r, "cladding/" + material.getName(), new BlockMetalCladding(material), METAL));
//            }
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

//            allAnvils = anvils.build();
//            allCladdings = claddings.build();
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

        // Note: if you add blocks you don't need to put them in this list of todos. Feel free to add them where they make sense :)

        // todo: smoke rack (placed with any string, so event based?) + smoke blocks or will we use particles?

        allNormalItemBlocks = normalItemBlocks.build();
        allInventoryItemBlocks = inventoryItemBlocks.build();
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
        if (current.getBlock() instanceof IRockBlock rockTypeBlock)
            return rockTypeBlock.getRockVariant() == RAW;
        return false;
    }

    public static boolean isClay(IBlockState current) {
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock)
            switch (soilTypeBlock.getSoilVariant()) {
                case CLAY, CLAY_GRASS -> {
                    return true;
                }
            }
        return false;
    }

    public static boolean isDirt(IBlockState current) {
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock)
            return soilTypeBlock.getSoilVariant() == DIRT;
        return false;
    }

    public static boolean isSand(IBlockState current) {
        if (current.getBlock() instanceof IRockBlock rockTypeBlock) {
            return rockTypeBlock.getRockVariant() == SAND;
        }
        return false;
    }

    public static boolean isGravel(IBlockState current) {
        if (current.getBlock() instanceof IRockBlock rockTypeBlock) {
            return rockTypeBlock.getRockVariant() == GRAVEL;
        }
        return false;
    }

    public static boolean isSoil(IBlockState current) {
        if (current.getBlock() instanceof BlockSoilPeat) return true;
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock)
            switch (soilTypeBlock.getSoilVariant()) {
                case GRASS, DRY_GRASS, DIRT, CLAY, CLAY_GRASS -> {
                    return true;
                }
            }
        return false;
    }

    public static boolean isGrowableSoil(IBlockState current) {
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock)
            switch (soilTypeBlock.getSoilVariant()) {
                case GRASS, DRY_GRASS, DIRT, CLAY, CLAY_GRASS -> {
                    return true;
                }
            }
        return false;
    }

    public static boolean isSoilOrGravel(IBlockState current) {
        if (current.getBlock() instanceof BlockSoilPeat) return true;
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock)
            switch (soilTypeBlock.getSoilVariant()) {
                case GRASS, DRY_GRASS, DIRT -> {
                    return true;
                }
            }
        if (current.getBlock() instanceof IRockBlock rockTypeBlock)
            return rockTypeBlock.getRockVariant() == GRAVEL;
        return false;
    }

    public static boolean isGrass(IBlockState current) {
        if (current.getBlock() instanceof BlockSoilPeatGrass) return true;
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock)
            switch (soilTypeBlock.getSoilVariant()) {
                case GRASS, DRY_GRASS, CLAY_GRASS -> {
                    return true;
                }
            }
        return false;
    }

    public static boolean isDryGrass(IBlockState current) {
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock)
            return soilTypeBlock.getSoilVariant() == DRY_GRASS;
        return false;
    }

    public static boolean isGround(IBlockState current) {
        if (current.getBlock() instanceof IRockBlock rockTypeBlock)
            switch (rockTypeBlock.getRockVariant()) {
                case GRAVEL, SAND, RAW -> {
                    return true;
                }
            }
        if (current.getBlock() instanceof ISoilBlock soilTypeBlock)
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
}
