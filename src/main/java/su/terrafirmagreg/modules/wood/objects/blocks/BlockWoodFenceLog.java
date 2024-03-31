package su.terrafirmagreg.modules.wood.objects.blocks;

import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

public class BlockWoodFenceLog extends BlockWoodFence {

	public BlockWoodFenceLog(WoodBlockVariant blockVariant, WoodType type) {
		super(blockVariant, type);
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void onModelRegister() {
		ModelUtils.registerBlockInventoryModel(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onStateMapperRegister() {
		ModelUtils.registerStateMapper(this, new StateMap.Builder().build());
	}
}
