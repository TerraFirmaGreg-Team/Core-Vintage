package su.terrafirmagreg.modules.wood.objects.blocks;

import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.registry.RegistryModel;
import su.terrafirmagreg.api.util.CustomStateMap;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

public class BlockWoodFenceLog extends BlockWoodFence {

    public BlockWoodFenceLog(WoodBlockVariant variant, WoodType type) {
        super(variant, type);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {

        RegistryModel.registerBlockModel(this, new StateMap.Builder().build());
        RegistryModel.registerItemModel(Item.getItemFromBlock(this), 0, "inventory");
    }
}
