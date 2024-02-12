package su.terrafirmagreg.modules.wood.objects.blocks;

import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import su.terrafirmagreg.api.util.ModelRegistrationHelper;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

public class BlockWoodFenceLog extends BlockWoodFence {

    public BlockWoodFenceLog(WoodBlockVariant blockVariant, WoodType type) {
        super(blockVariant, type);
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelRegistrationHelper.registerBlockItemModel(this.getDefaultState());
        ModelRegistrationHelper.registerItemModel(Item.getItemFromBlock(this), this.getRegistryName().toString());
    }
}
