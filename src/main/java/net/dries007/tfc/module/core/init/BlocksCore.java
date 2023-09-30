package net.dries007.tfc.module.core.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import com.codetaylor.mc.athenaeum.util.ModelRegistrationHelper;
import com.google.common.collect.ImmutableMap;
import net.dries007.tfc.Tags;
import net.dries007.tfc.api.types.GroundcoverType;
import net.dries007.tfc.api.util.RegistryHelper;
import net.dries007.tfc.common.objects.entity.EntityFallingBlockTFC;
import net.dries007.tfc.compat.dynamictrees.blocks.BlockTreeRootyMimic;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.module.animal.client.render.TESRPlacedHide;
import net.dries007.tfc.module.animal.common.blocks.BlockPlacedHide;
import net.dries007.tfc.module.animal.common.items.ItemAnimalHide;
import net.dries007.tfc.module.animal.common.tiles.TEPlacedHide;
import net.dries007.tfc.module.ceramic.common.tiles.TELargeVessel;
import net.dries007.tfc.module.core.ModuleCore;
import net.dries007.tfc.module.core.client.render.TESRPlacedItem;
import net.dries007.tfc.module.core.client.render.TESRPlacedItemFlat;
import net.dries007.tfc.module.core.objects.blocks.*;
import net.dries007.tfc.module.core.objects.blocks.fluid.BlockFluidBase;
import net.dries007.tfc.module.core.objects.blocks.fluid.BlockFluidHotWater;
import net.dries007.tfc.module.core.objects.blocks.fluid.BlockFluidWater;
import net.dries007.tfc.module.core.objects.tiles.TEPlacedItem;
import net.dries007.tfc.module.core.objects.tiles.TEPlacedItemFlat;
import net.dries007.tfc.module.core.objects.tiles.TEPowderKeg;
import net.dries007.tfc.module.core.objects.tiles.TETickCounter;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.entity.RenderFallingBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.dries007.tfc.module.core.StorageCore.GROUNDCOVER_BLOCKS;
import static net.minecraft.block.material.Material.WATER;

public class BlocksCore {
    public static BlockDebug DEBUG;
    public static BlockAggregate AGGREGATE;
    public static BlockThatch THATCH;

    public static BlockThatchBed THATCH_BED;

    public static BlockPlacedItemFlat PLACED_ITEM_FLAT;
    public static BlockPlacedItem PLACED_ITEM;
    public static BlockPlacedHide PLACED_HIDE;
    public static BlockPowderKeg POWDERKEG;

    public static BlockSeaIce SEA_ICE;
    public static BlockTreeRootyMimic ROOTY_DIRT_MIMIC;
    public static BlockFluidBase HOT_WATER;
    public static BlockFluidBase FRESH_WATER;
    public static BlockFluidBase SALT_WATER;

    public static void onRegister(Registry registry) {

        //==== Blocks ================================================================================================//

        registry.registerBlock(DEBUG = new BlockDebug(), DEBUG.getItemBlock(), BlockDebug.NAME);
        registry.registerBlock(AGGREGATE = new BlockAggregate(), AGGREGATE.getItemBlock(), BlockAggregate.NAME);
        registry.registerBlock(THATCH = new BlockThatch(), THATCH.getItemBlock(), BlockThatch.NAME);
        registry.registerBlock(PLACED_ITEM = new BlockPlacedItem(), PLACED_ITEM.getItemBlock(), BlockPlacedItem.NAME);
        registry.registerBlock(POWDERKEG = new BlockPowderKeg(), POWDERKEG.getItemBlock(), BlockPowderKeg.NAME);


        registry.registerBlock(PLACED_ITEM_FLAT = new BlockPlacedItemFlat(), BlockPlacedItemFlat.NAME);
        registry.registerBlock(PLACED_HIDE = new BlockPlacedHide(), BlockPlacedHide.NAME);
        registry.registerBlock(THATCH_BED = new BlockThatchBed(), BlockThatchBed.NAME);

        //==== Alabaster =============================================================================================//

//        for (var variant : new RockBlockVariant[]{RAW, BRICKS, SMOOTH}) {
//            for (var color : EnumColor.values()) {
//                var alabasterBlock = new BlockAlabaster(variant, color);
//
//                if (ALABASTER_BLOCKS.put(new Pair<>(variant, color), alabasterBlock) != null)
//                    throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", color, variant));
//                BLOCKS.add(alabasterBlock);
//            }
//        }

        //==== Groundcover ===========================================================================================//

        for (var type : GroundcoverType.values()) {
            var groundcoverBlock = new BlockGroundcover(type);

            if (GROUNDCOVER_BLOCKS.put(type, groundcoverBlock) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", type, groundcoverBlock));
        }

        //==== Fluid =================================================================================================//


        registry.registerBlock(SEA_ICE = new BlockSeaIce(FluidRegistry.getFluid("salt_water")), "sea_ice");
        registry.registerBlockRegistrationStrategy((forgeRegistry) -> {
            forgeRegistry.register(ROOTY_DIRT_MIMIC = new BlockTreeRootyMimic());
            forgeRegistry.register(HOT_WATER = new BlockFluidHotWater(FluidRegistry.getFluid("hot_water")));
            forgeRegistry.register(FRESH_WATER = new BlockFluidWater(FluidRegistry.getFluid("fresh_water"), WATER, false));
            forgeRegistry.register(SALT_WATER = new BlockFluidWater(FluidRegistry.getFluid("salt_water"), WATER, true));
        });


        //==== TileEntity ============================================================================================//

        RegistryHelper.registerTileEntities(
                registry,
                TETickCounter.class,
                TEPlacedItem.class,
                TEPlacedItemFlat.class,
                TEPlacedHide.class,
                TEPowderKeg.class,
                TELargeVessel.class
        );
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerVanillaOverridesBlock(RegistryEvent.Register<Block> event) {
        // Ванильные переопределения. Используется для небольших настроек ванильных предметов, а не для их полной замены.
        if (ConfigTFC.General.OVERRIDES.enableFrozenOverrides) {
            ModuleCore.LOGGER.info("The below warnings about unintended overrides are normal. The override is intended. ;)");
            event.getRegistry().registerAll(
                    new BlockVanillaIce(FluidRegistry.WATER),
                    new BlockSnowTFC()
            );
        }

        if (ConfigTFC.General.OVERRIDES.enableTorchOverride) {
            event.getRegistry().register(new BlockTorchTFC());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {

        registry.registerClientModelRegistrationStrategy(() -> {

            ModelRegistrationHelper.registerBlockItemModels(
                    DEBUG,
                    AGGREGATE,
                    THATCH,
//                    THATCH_BED,
                    PLACED_ITEM_FLAT,
                    PLACED_ITEM,
//                    PLACED_HIDE,
                    POWDERKEG
            );


            // Empty Models
            final ModelResourceLocation empty = new ModelResourceLocation(Tags.MOD_ID + ":empty");
            // todo: switch to hide rack (involves changing mechanics, etc)
            final ModelResourceLocation hideRack = new ModelResourceLocation(Tags.MOD_ID + ":hide_rack");

            ModelLoader.setCustomStateMapper(BlocksCore.THATCH_BED, new StateMap.Builder().ignore(BlockThatchBed.OCCUPIED).build());
            ModelLoader.setCustomStateMapper(BlocksCore.PLACED_ITEM_FLAT, blockIn -> ImmutableMap.of(BlocksCore.PLACED_ITEM_FLAT.getDefaultState(), empty));
            ModelLoader.setCustomStateMapper(BlocksCore.PLACED_ITEM, blockIn -> ImmutableMap.of(BlocksCore.PLACED_ITEM.getDefaultState(), empty));
            ModelLoader.setCustomStateMapper(BlocksCore.PLACED_HIDE, blockIn -> ImmutableMap.of(BlocksCore.PLACED_HIDE.getDefaultState()
                    .withProperty(BlockPlacedHide.SIZE, ItemAnimalHide.HideSize.SMALL), empty, BlocksCore.PLACED_HIDE.getDefaultState()
                    .withProperty(BlockPlacedHide.SIZE, ItemAnimalHide.HideSize.MEDIUM), empty, BlocksCore.PLACED_HIDE.getDefaultState()
                    .withProperty(BlockPlacedHide.SIZE, ItemAnimalHide.HideSize.LARGE), empty));
        });

        //==== TESRs =================================================================================================//

        ClientRegistry.bindTileEntitySpecialRenderer(TEPlacedItemFlat.class, new TESRPlacedItemFlat());
        ClientRegistry.bindTileEntitySpecialRenderer(TEPlacedItem.class, new TESRPlacedItem());
        ClientRegistry.bindTileEntitySpecialRenderer(TEPlacedHide.class, new TESRPlacedHide());

        RenderingRegistry.registerEntityRenderingHandler(EntityFallingBlockTFC.class, RenderFallingBlock::new);
        //RenderingRegistry.registerEntityRenderingHandler(EntityThrownJavelin.class, RenderThrownJavelin::new);
    }
}
