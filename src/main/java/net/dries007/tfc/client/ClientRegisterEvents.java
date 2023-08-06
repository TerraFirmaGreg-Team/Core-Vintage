package net.dries007.tfc.client;

import com.google.common.collect.ImmutableMap;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.metal.util.IMetalBlock;
import net.dries007.tfc.api.types.wood.util.IWoodBlock;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.client.render.*;
import net.dries007.tfc.compat.gregtech.oreprefix.IOrePrefixExtension;
import net.dries007.tfc.objects.blocks.BlockThatchBed;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeLeaves;
import net.dries007.tfc.objects.blocks.soil.BlockSoilFarmland;
import net.dries007.tfc.objects.blocks.wood.tree.BlockWoodLeaves;
import net.dries007.tfc.objects.items.ItemAnimalHide;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.ceramics.ItemMold;
import net.dries007.tfc.objects.te.*;
import net.dries007.tfc.test.blocks.TFCBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.*;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.plant.PlantVariant.SHORT_GRASS;
import static net.dries007.tfc.api.types.plant.PlantVariant.TALL_GRASS;
import static net.dries007.tfc.api.types.soil.SoilVariant.FARMLAND;
import static net.dries007.tfc.api.types.wood.WoodVariant.LEAVES;
import static net.dries007.tfc.api.types.wood.WoodVariant.SAPLING;
import static net.dries007.tfc.objects.blocks.BlockPlacedHide.SIZE;
import static net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC.WILD;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MOD_ID)
public final class ClientRegisterEvents {


    public static final IItemColor moldItemColors = (stack, tintIndex) -> {
        if (tintIndex != 1) return 0xFFFFFF;

        IFluidHandler cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
        if (cap != null) {
            if (cap instanceof IMoldHandler) {
                var material = ((IMoldHandler) cap).getMaterial();
                if (material != null) {
                    return material.getMaterialRGB();
                }
            }
        }
        return 0xFFFFFF;
    };

    @SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public static void registerModels(ModelRegistryEvent event) {

        //=== BLOCKS =================================================================================================//

        TFCStorage.SOIL_BLOCKS.values().forEach(IHasModel::onModelRegister);
        TFCStorage.ROCK_BLOCKS.values().forEach(IHasModel::onModelRegister);
        TFCStorage.PLANT_BLOCKS.values().forEach(IHasModel::onModelRegister);
        TFCStorage.WOOD_BLOCKS.values().forEach(IHasModel::onModelRegister);
        TFCStorage.ALABASTER_BLOCK.values().forEach(IHasModel::onModelRegister);
        TFCStorage.GROUNDCOVER_BLOCK.values().forEach(IHasModel::onModelRegister);
        TFCStorage.METAL_BLOCKS.values().forEach(IHasModel::onModelRegister);


        for (ItemBlock itemBlock : TFCStorage.ITEM_BLOCKS)
            ModelLoader.setCustomModelResourceLocation(itemBlock, 0, new ModelResourceLocation(itemBlock.getRegistryName(), "normal"));

        for (Block block : TFCStorage.FLUID)
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());


        //=== ITEMS ==================================================================================================//

        for (Item item : TFCStorage.ROCK_ITEM.values())
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));

        for (Item item : TFCStorage.BRICK_ITEM.values())
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));

        for (Item item : TFCStorage.ITEM)
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));

        //=== TESRs ==================================================================================================//

        ClientRegistry.bindTileEntitySpecialRenderer(TEChestTFC.class, new TESRChestTFC());
        ClientRegistry.bindTileEntitySpecialRenderer(TEToolRack.class, new TESRToolRack());
        ClientRegistry.bindTileEntitySpecialRenderer(TEPitKiln.class, new TESRPitKiln());
        ClientRegistry.bindTileEntitySpecialRenderer(TEPlacedItemFlat.class, new TESRPlacedItemFlat());
        ClientRegistry.bindTileEntitySpecialRenderer(TEPlacedItem.class, new TESRPlacedItem());
        ClientRegistry.bindTileEntitySpecialRenderer(TEPlacedHide.class, new TESRPlacedHide());
        ClientRegistry.bindTileEntitySpecialRenderer(TEQuern.class, new TESRQuern());
        ClientRegistry.bindTileEntitySpecialRenderer(TEBellows.class, new TESRBellows());
        ClientRegistry.bindTileEntitySpecialRenderer(TEBarrel.class, new TESRBarrel());
        ClientRegistry.bindTileEntitySpecialRenderer(TEAnvilTFC.class, new TESRAnvil());
        ClientRegistry.bindTileEntitySpecialRenderer(TELoom.class, new TESRLoom());
        ClientRegistry.bindTileEntitySpecialRenderer(TECrucible.class, new TESRCrucible());
        ClientRegistry.bindTileEntitySpecialRenderer(TEFirePit.class, new TESRFirePit());


        // Registering fluid containers
        ModelLoader.setCustomModelResourceLocation(ItemsTFC.WOODEN_BUCKET, 0, new ModelResourceLocation(ItemsTFC.WOODEN_BUCKET.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(ItemsTFC.FIRED_JUG, 0, new ModelResourceLocation(ItemsTFC.FIRED_JUG.getRegistryName(), "inventory"));
        //ModelLoader.setCustomModelResourceLocation(ItemsTFC.BLUE_STEEL_BUCKET, 0, new ModelResourceLocation(ItemsTFC.BLUE_STEEL_BUCKET.getRegistryName(), "inventory"));
        //ModelLoader.setCustomModelResourceLocation(ItemsTFC.RED_STEEL_BUCKET, 0, new ModelResourceLocation(ItemsTFC.RED_STEEL_BUCKET.getRegistryName(), "inventory"));

        // Simple Items
        for (Item item : ItemsTFC.getAllSimpleItems())
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));

        // Dye color Items
        for (EnumDyeColor color : EnumDyeColor.values()) {
            ModelLoader.setCustomModelResourceLocation(ItemsTFC.UNFIRED_VESSEL_GLAZED, color.getDyeDamage(), new ModelResourceLocation(ItemsTFC.UNFIRED_VESSEL_GLAZED.getRegistryName().toString()));
            ModelLoader.setCustomModelResourceLocation(ItemsTFC.FIRED_VESSEL_GLAZED, color.getDyeDamage(), new ModelResourceLocation(ItemsTFC.FIRED_VESSEL_GLAZED.getRegistryName().toString()));
        }

        // Ceramic Molds
        for (var orePrefix : OrePrefix.values()) {
            var extendedOrePrefix = (IOrePrefixExtension) orePrefix;

            if (extendedOrePrefix.getHasMold()) {
                var clayMold = ItemMold.get(orePrefix);

                ModelBakery.registerItemVariants(clayMold, new ModelResourceLocation(clayMold.getRegistryName().toString() + "_empty"));
                ModelBakery.registerItemVariants(clayMold, new ModelResourceLocation(clayMold.getRegistryName().toString() + "_filled"));

                ModelLoader.setCustomMeshDefinition(clayMold, new ItemMeshDefinition() {
                    @Override
                    @Nonnull
                    public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {
                        IFluidHandler cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
                        if (cap instanceof IMoldHandler) {
                            Material material = ((IMoldHandler) cap).getMaterial();
                            if (material != null) {
                                return new ModelResourceLocation(clayMold.getRegistryName().toString() + "_filled");
                            }
                        }
                        return new ModelResourceLocation(clayMold.getRegistryName().toString() + "_empty");
                    }
                });
            }
        }

        // Item Blocks
        for (ItemBlock item : BlocksTFC.getAllNormalItemBlocks())
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "normal"));

        for (ItemBlock item : BlocksTFC.getAllInventoryItemBlocks())
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));


        // Blocks with Ignored Properties


        for (Block block : BlocksTFC.getAllCropBlocks())
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(WILD).build());

        for (Block block : BlocksTFC.getAllFruitTreeLeavesBlocks())
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockFruitTreeLeaves.DECAYABLE).ignore(BlockFruitTreeLeaves.HARVESTABLE).build());

//        BlocksTFC.getAllBlockRockVariants().forEach(e -> {
////            if (e.getType() == Rock.Type.FARMLAND)
////            {
////                ModelLoader.setCustomStateMapper(e, new StateMap.Builder().ignore(BlockFarmlandTFC.MOISTURE).build());
////            }
//            if (e.getRockVariant() == RAW)
//            {
//                ModelLoader.setCustomStateMapper((Block) e, new StateMap.Builder().ignore(BlockRockRaw.CAN_FALL).build());
//            }
//            else if (e.getRockVariant() == SMOOTH)
//            {
//                ModelLoader.setCustomStateMapper((Block) e, new StateMap.Builder().ignore(BlockRockSmooth.CAN_FALL).build());
//            }
//        });

        ModelLoader.setCustomStateMapper(TFCBlocks.THATCH_BED, new StateMap.Builder().ignore(BlockThatchBed.OCCUPIED).build());

        // Empty Models
        final ModelResourceLocation empty = new ModelResourceLocation(MOD_ID + ":empty");
        // todo: switch to hide rack (involves changing mechanics, etc)
        final ModelResourceLocation hideRack = new ModelResourceLocation(MOD_ID + ":hide_rack");

        ModelLoader.setCustomStateMapper(TFCBlocks.PIT_KILN, blockIn -> ImmutableMap.of(TFCBlocks.PIT_KILN.getDefaultState(), empty));
        ModelLoader.setCustomStateMapper(TFCBlocks.PLACED_ITEM_FLAT, blockIn -> ImmutableMap.of(TFCBlocks.PLACED_ITEM_FLAT.getDefaultState(), empty));
        ModelLoader.setCustomStateMapper(TFCBlocks.PLACED_ITEM, blockIn -> ImmutableMap.of(TFCBlocks.PLACED_ITEM.getDefaultState(), empty));
        ModelLoader.setCustomStateMapper(TFCBlocks.PLACED_HIDE, blockIn -> ImmutableMap.of(TFCBlocks.PLACED_HIDE.getDefaultState()
                .withProperty(SIZE, ItemAnimalHide.HideSize.SMALL), empty, TFCBlocks.PLACED_HIDE.getDefaultState()
                .withProperty(SIZE, ItemAnimalHide.HideSize.MEDIUM), empty, TFCBlocks.PLACED_HIDE.getDefaultState()
                .withProperty(SIZE, ItemAnimalHide.HideSize.LARGE), empty));

    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerColorHandlerBlocks(ColorHandlerEvent.Block event) {
        BlockColors blockColors = event.getBlockColors();

        // Grass Colors
        IBlockColor grassColor = GrassColorHandler::computeGrassColor;

        // Foliage Color
        // todo: do something different for conifers - they should have a different color mapping through the seasons
        IBlockColor foliageColor = GrassColorHandler::computeGrassColor;

        //=== Soil ===================================================================================================//

        blockColors.registerBlockColorHandler(grassColor, TFCStorage.SOIL_BLOCKS.values()
                .stream()
                .filter(x -> x.getSoilVariant().isGrass())
                .map(s -> (Block) s)
                .toArray(Block[]::new));

        blockColors.registerBlockColorHandler((state, worldIn, pos, tintIndex) ->
                BlockSoilFarmland.TINT[state.getValue(BlockSoilFarmland.MOISTURE)], TFCStorage.SOIL_BLOCKS.values()
                .stream()
                .filter(x -> x.getSoilVariant() == FARMLAND)
                .map(s -> (Block) s)
                .toArray(Block[]::new));

        blockColors.registerBlockColorHandler(grassColor, TFCBlocks.PEAT_GRASS);

        //=== Plant ==================================================================================================//

        blockColors.registerBlockColorHandler(grassColor, TFCStorage.PLANT_BLOCKS.values()
                .stream()
                .map(s -> (Block) s)
                .toArray(Block[]::new));

        //=== Wood ===================================================================================================//

        blockColors.registerBlockColorHandler((s, w, p, i) -> i == 0 ? ((IWoodBlock) s.getBlock()).getWood().getColor() : 0xFFFFFF,
                TFCStorage.WOOD_BLOCKS.values()
                        .stream()
                        .filter(block -> block.getWoodVariant() != LEAVES && block.getWoodVariant() != SAPLING)
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        blockColors.registerBlockColorHandler(foliageColor,
                TFCStorage.WOOD_BLOCKS.values()
                        .stream()
                        .filter(x -> x.getWoodVariant() == LEAVES)
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        //=== Metal ==================================================================================================//

        blockColors.registerBlockColorHandler((s, w, p, i) -> i == 0 ? ((IMetalBlock) s.getBlock()).getMaterial().getMaterialRGB() : 0xFFFFFF,
                TFCStorage.METAL_BLOCKS.values()
                        .stream()
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));


        // This is talking about tall grass vs actual grass blocks

//		blockColors.registerBlockColorHandler(grassColor, BlocksTFC.getAllGrassBlocks().toArray(new BlockPlantTFC[0]));
//
//
//				blockColors.registerBlockColorHandler(foliageColor, BlocksTFC.getAllLeafBlocks().toArray(new Block[0]));
//		blockColors.registerBlockColorHandler(foliageColor, BlocksTFC.getAllPlantBlocks().toArray(new BlockPlantTFC[0]));
//
        blockColors.registerBlockColorHandler(foliageColor, BlocksTFC.getAllFruitTreeLeavesBlocks().toArray(new Block[0]));
//
//		blockColors.registerBlockColorHandler(foliageColor, BlocksTFC.getAllFlowerPots().toArray(new Block[0]));


    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public static void registerColorHandlerItems(ColorHandlerEvent.Item event) {
        ItemColors itemColors = event.getItemColors();

        //=== Soil ===================================================================================================//

        itemColors.registerItemColorHandler((s, i) -> event.getBlockColors().colorMultiplier(((ItemBlock) s.getItem()).getBlock().getStateFromMeta(s.getMetadata()), null, null, i),
                TFCStorage.SOIL_BLOCKS.values()
                        .stream()
                        .filter(x -> x.getSoilVariant().isGrass())
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        itemColors.registerItemColorHandler((s, i) -> event.getBlockColors().colorMultiplier(((ItemBlock) s.getItem()).getBlock().getStateFromMeta(s.getMetadata()), null, null, i),
                TFCBlocks.PEAT_GRASS);

        //=== Plant ==================================================================================================//

        itemColors.registerItemColorHandler((s, i) -> event.getBlockColors().colorMultiplier(((ItemBlock) s.getItem()).getBlock().getStateFromMeta(s.getMetadata()), null, null, i),
                TFCStorage.PLANT_BLOCKS.values()
                        .stream()
                        .filter(x -> x.getPlantVariant() == SHORT_GRASS || x.getPlantVariant() == TALL_GRASS)
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        //=== Wood ===================================================================================================//

        itemColors.registerItemColorHandler((s, i) -> i == 0 ? ((IWoodBlock) ((ItemBlock) s.getItem()).getBlock()).getWood().getColor() : 0xFFFFFF,
                TFCStorage.WOOD_BLOCKS.values()
                        .stream()
                        .filter(x -> x.getWoodVariant() != LEAVES && x.getWoodVariant() != SAPLING)
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        itemColors.registerItemColorHandler((s, i) -> event.getBlockColors().colorMultiplier(((ItemBlock) s.getItem()).getBlock().getStateFromMeta(s.getMetadata()), null, null, i),
                TFCStorage.WOOD_BLOCKS.values()
                        .stream()
                        .filter(x -> x.getWoodVariant() == LEAVES)
                        .map(s -> (BlockWoodLeaves) s)
                        .toArray(Block[]::new));

        itemColors.registerItemColorHandler((s, i) -> event.getBlockColors().colorMultiplier(((ItemBlock) s.getItem()).getBlock().getStateFromMeta(s.getMetadata()), null, null, i),
                BlocksTFC.getAllFruitTreeLeavesBlocks()
                        .toArray(new BlockFruitTreeLeaves[0]));

        //=== Metal ==================================================================================================//

        itemColors.registerItemColorHandler((s, i) -> i == 0 ? ((IMetalBlock) ((ItemBlock) s.getItem()).getBlock()).getMaterial().getMaterialRGB() : 0xFFFFFF,
                TFCStorage.METAL_BLOCKS.values()
                        .stream()
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        //=== Other ==================================================================================================//


        itemColors.registerItemColorHandler((stack, tintIndex) -> tintIndex == 1 ? EnumDyeColor.byDyeDamage(stack.getItemDamage()).getColorValue() : 0xFFFFFF,
                ItemsTFC.UNFIRED_VESSEL_GLAZED, ItemsTFC.FIRED_VESSEL_GLAZED);

        itemColors.registerItemColorHandler((stack, tintIndex) -> {
            IFood food = stack.getCapability(CapabilityFood.CAPABILITY, null);
            if (food != null) {
                return food.isRotten() ? ConfigTFC.Client.DISPLAY.rottenFoodOverlayColor : 0xFFFFFF;
            }
            return 0xFFFFFF;
        }, ForgeRegistries.ITEMS.getValuesCollection().stream().filter(x -> x instanceof ItemFood).toArray(Item[]::new));

        // Colorize clay molds
        itemColors.registerItemColorHandler(moldItemColors, ItemsTFC.getAllMoldItems().toArray(new Item[0]));
    }
}
