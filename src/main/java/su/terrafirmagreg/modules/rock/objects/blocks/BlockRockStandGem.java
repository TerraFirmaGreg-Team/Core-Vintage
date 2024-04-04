package su.terrafirmagreg.modules.rock.objects.blocks;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.spi.tile.ITEBlock;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;
import su.terrafirmagreg.modules.rock.client.render.TESRRockGemDisplay;
import su.terrafirmagreg.modules.rock.objects.tiles.TERockGemDisplay;

import static su.terrafirmagreg.api.util.PropertyUtils.HORIZONTAL;
import static su.terrafirmagreg.api.util.PropertyUtils.UP;

public class BlockRockStandGem extends BlockRock implements ITEBlock {

	public BlockRockStandGem(RockBlockVariant blockVariant, RockType type) {
		super(blockVariant, type);

		this.setSoundType(SoundType.STONE);
		this.setHarvestLevel("pickaxe", 0);
		this.setHardness(1.0F);
		this.setDefaultState(this.blockState.getBaseState()
		                                    .withProperty(HORIZONTAL, EnumFacing.EAST)
		                                    .withProperty(UP, Boolean.TRUE));
	}

	@Override
	public @NotNull Size getSize(@NotNull ItemStack itemStack) {
		return Size.LARGE;
	}

	@Override
	public @NotNull Weight getWeight(@NotNull ItemStack itemStack) {
		return Weight.HEAVY;
	}

	public boolean isFullCube(@NotNull IBlockState state) {
		return false;
	}


	protected @NotNull BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, HORIZONTAL, UP);
	}

	public @NotNull IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState()
		           .withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta))
		           .withProperty(UP, meta / 4 % 2 != 0);
	}

	public int getMetaFromState(IBlockState state) {
		return state.getValue(HORIZONTAL).getHorizontalIndex() + (state.getValue(UP) ? 4 : 0);
	}

	public @NotNull IBlockState getStateForPlacement(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(HORIZONTAL, placer.getHorizontalFacing());
	}

	@NotNull
	public BlockFaceShape getBlockFaceShape(@NotNull IBlockAccess worldIn, @NotNull IBlockState state, @NotNull BlockPos pos, @NotNull EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	public boolean isOpaqueCube(@NotNull IBlockState state) {
		return false;
	}

	public boolean hasTileEntity(@NotNull IBlockState state) {
		return true;
	}

	@Nullable
	public TileEntity createTileEntity(@NotNull World world, @NotNull IBlockState state) {
		return new TERockGemDisplay();
	}

	public boolean onBlockActivated(World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull EntityPlayer playerIn, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			TERockGemDisplay te = TileUtils.getTile(worldIn, pos, TERockGemDisplay.class);
			if (te != null) {
				return te.onRightClick(playerIn, hand);
			}
		}

		return true;
	}

	public void breakBlock(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
		TERockGemDisplay te = TileUtils.getTile(worldIn, pos, TERockGemDisplay.class);
		if (te != null) {
			te.onBreakBlock();
		}
		super.breakBlock(worldIn, pos, state);
	}

	public void neighborChanged(@NotNull IBlockState state, @NotNull World worldIn, BlockPos pos, @NotNull Block blockIn, BlockPos fromPos) {
		if (fromPos.equals(pos.up())) {
			if (worldIn.getBlockState(fromPos).getBlock() instanceof BlockAir) {
				state = state.withProperty(UP, Boolean.TRUE);
			} else {
				state = state.withProperty(UP, Boolean.FALSE);
			}
			worldIn.setBlockState(pos, state, 2);
		}
	}

	@Override
	public boolean hasComparatorInputOverride(@NotNull IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(@NotNull IBlockState state, World world, @NotNull BlockPos pos) {
		TERockGemDisplay te = TileUtils.getTile(world, pos, TERockGemDisplay.class);
		assert te != null;
		return (int) Math.floor(15 * ((double) te.getSize() / (double) te.getMaxStackSize()));
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TERockGemDisplay.class;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public TileEntitySpecialRenderer<?> getTileRenderer() {
		return new TESRRockGemDisplay();
	}
}
