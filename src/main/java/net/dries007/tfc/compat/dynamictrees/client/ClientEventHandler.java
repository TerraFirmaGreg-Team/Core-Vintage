package net.dries007.tfc.compat.dynamictrees.client;

import com.ferreusveritas.dynamictrees.blocks.BlockRooty;
import com.ferreusveritas.dynamictrees.models.bakedmodels.BakedModelBlockRooty;
import net.dries007.tfc.Tags;
import net.dries007.tfc.api.types.tree.type.TreeType;
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

import static net.dries007.tfc.common.objects.blocks.TFCBlocks.ROOTY_DIRT_MIMIC;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = Tags.MOD_ID, value = {Side.CLIENT})
public class ClientEventHandler {
    public ClientEventHandler() {
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        // Register Meshers for Branches
        for (var tree : TreeType.getTreeTypes()) {
            ModelHelperTFC.regModel(tree.getDynamicBranch()); //Register Branch itemBlock
            ModelHelperTFC.regModel(tree); //Register custom state mapper for branch
        }
    }
}
