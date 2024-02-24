package su.terrafirmagreg.modules.wood.objects.blocks;

import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.models.ModelManager;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

public class BlockWoodFenceLog extends BlockWoodFence {

	public BlockWoodFenceLog(WoodBlockVariant blockVariant, WoodType type) {
		super(blockVariant, type);
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void onModelRegister() {
		ModelManager.registerBlockItemModel(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onStateMapperRegister() {
		ModelManager.registerStateMapper(this, new StateMap.Builder().build());
	}
}
