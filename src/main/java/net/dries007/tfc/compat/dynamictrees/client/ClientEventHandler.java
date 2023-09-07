package net.dries007.tfc.compat.dynamictrees.client;

import com.ferreusveritas.dynamictrees.blocks.BlockRooty;
import com.ferreusveritas.dynamictrees.models.bakedmodels.BakedModelBlockRooty;
import net.dries007.tfc.client.util.GrassColorHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.common.objects.blocks.TFCBlocks.ROOTY_DIRT_MIMIC;
import static net.dries007.tfc.compat.dynamictrees.trees.WoodTreeFamily.TREES;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = MOD_ID, value = {Side.CLIENT})
public class ClientEventHandler {
    public ClientEventHandler() {
    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
        Block block = ROOTY_DIRT_MIMIC;
        if (block.getRegistryName() != null) {
            BakedModelBlockRooty rootyModel = new BakedModelBlockRootyTFC();
            event.getModelRegistry().putObject(new ModelResourceLocation(block.getRegistryName(), "normal"), rootyModel);
        }
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        //Register Meshers for Branches
        for (var tree : TREES) {
            //ModelHelperTFC.regModel(tree.getDynamicBranch());//Register Branch itemBlock
            ModelHelperTFC.regModel(tree);//Register custom state mapper for branch
        }

        ModelLoader.setCustomStateMapper(ROOTY_DIRT_MIMIC, new StateMap.Builder()
                .ignore(BlockRooty.LIFE)
                .build());
    }

    @SubscribeEvent
    public static void registerColorHandlerBlocks(ColorHandlerEvent.Block event) {
        final BlockColors blockColors = event.getBlockColors();

        blockColors.registerBlockColorHandler(GrassColorHandler::computeGrassColor, ROOTY_DIRT_MIMIC);
    }
}
