package su.terrafirmagreg.modules.wood.objects.blocks;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import su.terrafirmagreg.api.util.ModelRegistrationHelper;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

public class BlockWoodFenceGateLog extends BlockWoodFenceGate {

    public BlockWoodFenceGateLog(WoodBlockVariant blockVariant, WoodType type) {
        super(blockVariant, type);
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelRegistrationHelper.registerBlockItemModel(this.getDefaultState());
    }
}
