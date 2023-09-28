package net.dries007.tfc.module.core.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import com.codetaylor.mc.athenaeum.util.ModelRegistrationHelper;
import com.google.common.collect.ImmutableMap;
import net.dries007.tfc.Tags;
import net.dries007.tfc.api.util.RegistryHelper;
import net.dries007.tfc.module.animal.client.render.TESRPlacedHide;
import net.dries007.tfc.module.animal.common.blocks.BlockPlacedHide;
import net.dries007.tfc.module.animal.common.items.ItemAnimalHide;
import net.dries007.tfc.module.animal.common.tiles.TEPlacedHide;
import net.dries007.tfc.module.ceramic.common.tiles.TELargeVessel;
import net.dries007.tfc.module.core.client.render.TESRPlacedItem;
import net.dries007.tfc.module.core.client.render.TESRPlacedItemFlat;
import net.dries007.tfc.module.core.common.blocks.*;
import net.dries007.tfc.module.core.common.tiles.TEPlacedItem;
import net.dries007.tfc.module.core.common.tiles.TEPlacedItemFlat;
import net.dries007.tfc.module.core.common.tiles.TEPowderKeg;
import net.dries007.tfc.module.core.common.tiles.TETickCounter;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlocksCore {


    public static BlockDebug DEBUG;
    public static BlockAggregate AGGREGATE;
    public static BlockThatch THATCH;

    public static BlockThatchBed THATCH_BED;

    public static BlockPlacedItemFlat PLACED_ITEM_FLAT;
    public static BlockPlacedItem PLACED_ITEM;
    public static BlockPlacedHide PLACED_HIDE;
    public static BlockPowderKeg POWDERKEG;

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
    }
}
