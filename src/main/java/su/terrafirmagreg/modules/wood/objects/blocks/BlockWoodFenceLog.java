package su.terrafirmagreg.modules.wood.objects.blocks;

import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.registry.ModelManager;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

public class BlockWoodFenceLog extends BlockWoodFence {

	public BlockWoodFenceLog(WoodBlockVariant blockVariant, WoodType type) {
		super(blockVariant, type);
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void onStateMapperRegister() {
		ModelManager.registerBlockItemModel(this.getDefaultState());
		ModelManager.registerItemModel(Item.getItemFromBlock(this), this.getRegistryName().toString());
	}
}
