package su.terrafirmagreg.modules.wood.objects.blocks;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.registry.ModelManager;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

public class BlockWoodFenceGateLog extends BlockWoodFenceGate {

	public BlockWoodFenceGateLog(WoodBlockVariant blockVariant, WoodType type) {
		super(blockVariant, type);
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void onStateMapperRegister() {
		ModelManager.registerStateMapper(this, new StateMap.Builder()
				.ignore(BlockFenceGate.IN_WALL, BlockFenceGate.POWERED).build());
		ModelManager.registerItemModel(Item.getItemFromBlock(this), this.getRegistryName().toString());
	}
}
