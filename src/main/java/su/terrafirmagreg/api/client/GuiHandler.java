package su.terrafirmagreg.api.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.spi.tile.IContainerProvider;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

	// use this instead of player.openGui() -> avoids magic numbers
	public static void openGui(World world, BlockPos pos, EntityPlayer player, Type type) {
		player.openGui(TerraFirmaGreg.getInstance(), type.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
	}

	// Only use this for things that don't need a BlockPos to identify TE's!!!
	public static void openGui(World world, EntityPlayer player, Type type) {
		player.openGui(TerraFirmaGreg.getInstance(), type.ordinal(), world, 0, 0, 0);
	}

	@Nullable
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		BlockPos blockPos = new BlockPos(x, y, z);
		IBlockState blockState = world.getBlockState(blockPos);
		TileEntity tileEntity = world.getTileEntity(blockPos);

		if (tileEntity instanceof IContainerProvider<?, ?> containerProvider) {
			return containerProvider.getContainer(player.inventory, world, blockState, blockPos);
		}

		return null;
	}

	@Nullable
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		BlockPos blockPos = new BlockPos(x, y, z);
		IBlockState blockState = world.getBlockState(blockPos);
		TileEntity tileEntity = world.getTileEntity(blockPos);

		if (tileEntity instanceof IContainerProvider<?, ?> containerProvider) {
			return containerProvider.getGuiContainer(player.inventory, world, blockState, blockPos);
		}

		return null;
	}


	public enum Type {
		WOOD_BARREL
	}
}
