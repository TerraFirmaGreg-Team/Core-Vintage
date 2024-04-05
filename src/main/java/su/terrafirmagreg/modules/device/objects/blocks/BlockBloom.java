package su.terrafirmagreg.modules.device.objects.blocks;

import net.dries007.tfc.objects.items.ItemsTFC;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.spi.block.BlockBase;
import su.terrafirmagreg.api.spi.tile.ITEBlock;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.objects.tiles.TEBloom;


public class BlockBloom extends BlockBase implements ITEBlock {
	public BlockBloom() {
		super(Material.IRON);
		setHardness(3.0f);
		setHarvestLevel("pickaxe", 0);
		setSoundType(SoundType.STONE);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TEBloom te = TileUtils.getTile(worldIn, pos, TEBloom.class);
		if (te != null) {
			te.onBreakBlock(worldIn, pos, state);
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, @Nullable EntityPlayer player, boolean willHarvest) {
		if (player != null && player.canHarvestBlock(state) && !player.isCreative()) {
			// Try to give the contents of the TE directly to the player if possible
			TEBloom tile = TileUtils.getTile(world, pos, TEBloom.class);
			if (tile != null) {
				IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
				if (cap != null) {
					ItemStack contents = cap.extractItem(0, 64, false);
					ItemHandlerHelper.giveItemToPlayer(player, contents);
				}
			}
		}
		//noinspection ConstantConditions
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TEBloom();
	}

	@NotNull
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		TEBloom tile = TileUtils.getTile(world, pos, TEBloom.class);
		if (tile != null) {
			IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			if (cap != null) {
				ItemStack stack = cap.extractItem(0, 1, true);
				if (!stack.isEmpty()) {
					return stack;
				}
			}
		}
		return new ItemStack(ItemsTFC.UNREFINED_BLOOM);
	}

	@Override
	public @NotNull String getName() {
		return "device/bloom";
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TEBloom.class;
	}
}
