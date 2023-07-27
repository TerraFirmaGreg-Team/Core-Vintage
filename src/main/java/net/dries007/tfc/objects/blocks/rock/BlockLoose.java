package net.dries007.tfc.objects.blocks.rock;

import net.dries007.tfc.api.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types2.rock.RockVariant.LOOSE;

public abstract class BlockLoose extends BlockFalling implements IHasModel {
	private static final AxisAlignedBB STONE_AABB = new AxisAlignedBB(2.0 / 16.0, 0.0 / 16.0, 2.0 / 16.0, 14.0 / 16.0, 2.0 / 16.0, 14.0 / 16.0);
	protected final ResourceLocation modelLocation;

	protected BlockLoose(Material material) {

		super(material);
		this.blockHardness = 0.1f;
		this.blockResistance = 0.1f;
		this.modelLocation = new ResourceLocation(MOD_ID, LOOSE.getName());
	}

	protected BlockLoose() {
		this(Material.ROCK);
	}

	public Block getBlock() {
		return this;
	}

	@Nonnull
	@Override
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
		return STONE_AABB;
	}

	@Override
	@ParametersAreNonnullByDefault
	@SuppressWarnings("deprecation")
	public boolean isTopSolid(IBlockState state) {
		return false;
	}

	@Override
	@ParametersAreNonnullByDefault
	@SuppressWarnings("deprecation")
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	@ParametersAreNonnullByDefault
	@SuppressWarnings("deprecation")
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Nullable
	@Override
	@ParametersAreNonnullByDefault
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return null;
	}

	@Override
	@ParametersAreNonnullByDefault
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		IBlockState stateDown = worldIn.getBlockState(pos.down());

		return stateDown.isSideSolid(worldIn, pos, EnumFacing.DOWN) && state.getBlock().equals(Blocks.AIR);
	}

	@Override
	@Nonnull
	public EnumOffsetType getOffsetType() {
		return EnumOffsetType.XZ;
	}

	@Override
	@Nonnull
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
}
