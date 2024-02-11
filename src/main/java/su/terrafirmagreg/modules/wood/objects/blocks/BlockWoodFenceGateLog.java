package su.terrafirmagreg.modules.wood.objects.blocks;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import su.terrafirmagreg.api.registry.RegistryModel;
import su.terrafirmagreg.api.util.CustomStateMap;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

public class BlockWoodFenceGateLog extends BlockWoodFenceGate {

    public BlockWoodFenceGateLog(WoodBlockVariant variant, WoodType type) {
        super(variant, type);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {

        RegistryModel.registerBlockModel(this, new StateMap.Builder()
                        .ignore(BlockFenceGate.IN_WALL)
                        .ignore(BlockFenceGate.POWERED)
                        .build());

        RegistryModel.registerItemModel(Item.getItemFromBlock(this), 0, "inventory");
    }
}
