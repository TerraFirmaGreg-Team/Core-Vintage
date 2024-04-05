package su.terrafirmagreg.modules.device.objects.blocks;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.util.block.Multiblock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.block.BlockBase;
import su.terrafirmagreg.api.spi.tile.ITEBlock;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.data.BlocksDevice;
import su.terrafirmagreg.modules.device.objects.items.ItemFireStarter;
import su.terrafirmagreg.modules.device.objects.tiles.TEBloomery;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Predicate;

import static su.terrafirmagreg.api.util.PropertyUtils.*;

@ParametersAreNonnullByDefault
public class BlockBloomery extends BlockBase implements ITEBlock {
	//[horizontal index][basic shape / door1 / door2]
	private static final AxisAlignedBB[][] AABB =
			{
					{
							new AxisAlignedBB(0.0F, 0.0F, 0.0f, 1.0f, 1.0F, 0.5F),
							new AxisAlignedBB(0.0F, 0.0F, 0.0f, 0.125f, 1.0F, 0.5F),
							new AxisAlignedBB(0.875F, 0.0F, 0.0f, 1.0f, 1.0F, 0.5F)
					},
					{
							new AxisAlignedBB(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0f),
							new AxisAlignedBB(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 0.125f),
							new AxisAlignedBB(0.5F, 0.0F, 0.875F, 1.0F, 1.0F, 1.0f)
					},
					{
							new AxisAlignedBB(0.0f, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F),
							new AxisAlignedBB(0.0f, 0.0F, 0.5F, 0.125F, 1.0F, 1.0F),
							new AxisAlignedBB(0.875f, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F)
					},
					{
							new AxisAlignedBB(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F),
							new AxisAlignedBB(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 0.125F),
							new AxisAlignedBB(0.0F, 0.0F, 0.875F, 0.5F, 1.0F, 1.0F)
					}
			};

	private static final Multiblock BLOOMERY_CHIMNEY; // Helper for determining how high the chimney is
	private static final Multiblock[] BLOOMERY_BASE; // If one of those is true, bloomery is formed and can operate (has at least one chimney)
	private static final Multiblock GATE_Z, GATE_X; // Determines if the gate can stay in place

	static {
		Predicate<IBlockState> stoneMatcher = BlockBloomery::isValidSideBlock;
		Predicate<IBlockState> insideChimney = state -> state.getBlock() == BlocksDevice.MOLTEN || state.getMaterial()
		                                                                                                .isReplaceable();
		Predicate<IBlockState> center = state -> state.getBlock() == BlocksDevice.CHARCOAL_PILE || state.getBlock() == BlocksDevice.BLOOM || state.getMaterial()
		                                                                                                                                          .isReplaceable();

		// Bloomery center is the charcoal pile pos
		BLOOMERY_BASE = new Multiblock[4];
		BLOOMERY_BASE[EnumFacing.NORTH.getHorizontalIndex()] = new Multiblock()
				.match(new BlockPos(0, 0, 0), center)
				.match(new BlockPos(0, -1, 0), stoneMatcher)
				.match(new BlockPos(0, 0, 1), state -> state.getBlock() == BlocksDevice.BLOOMERY)
				.match(new BlockPos(1, 0, 1), stoneMatcher)
				.match(new BlockPos(-1, 0, 1), stoneMatcher)
				.match(new BlockPos(0, 1, 1), stoneMatcher)
				.match(new BlockPos(0, 1, -1), stoneMatcher)
				.match(new BlockPos(1, 1, 0), stoneMatcher)
				.match(new BlockPos(-1, 1, 0), stoneMatcher)
				.match(new BlockPos(0, -1, 1), stoneMatcher)
				.match(new BlockPos(0, 0, -1), stoneMatcher)
				.match(new BlockPos(1, 0, 0), stoneMatcher)
				.match(new BlockPos(-1, 0, 0), stoneMatcher);

		BLOOMERY_BASE[EnumFacing.SOUTH.getHorizontalIndex()] = new Multiblock()
				.match(new BlockPos(0, 0, 0), center)
				.match(new BlockPos(0, -1, 0), stoneMatcher)
				.match(new BlockPos(0, 0, -1), state -> state.getBlock() == BlocksDevice.BLOOMERY)
				.match(new BlockPos(1, 0, -1), stoneMatcher)
				.match(new BlockPos(-1, 0, -1), stoneMatcher)
				.match(new BlockPos(0, 1, 1), stoneMatcher)
				.match(new BlockPos(0, 1, -1), stoneMatcher)
				.match(new BlockPos(1, 1, 0), stoneMatcher)
				.match(new BlockPos(-1, 1, 0), stoneMatcher)
				.match(new BlockPos(0, -1, -1), stoneMatcher)
				.match(new BlockPos(0, 0, 1), stoneMatcher)
				.match(new BlockPos(1, 0, 0), stoneMatcher)
				.match(new BlockPos(-1, 0, 0), stoneMatcher);

		BLOOMERY_BASE[EnumFacing.WEST.getHorizontalIndex()] = new Multiblock()
				.match(new BlockPos(0, 0, 0), center)
				.match(new BlockPos(0, -1, 0), stoneMatcher)
				.match(new BlockPos(1, 0, 0), state -> state.getBlock() == BlocksDevice.BLOOMERY)
				.match(new BlockPos(1, 0, -1), stoneMatcher)
				.match(new BlockPos(1, 0, 1), stoneMatcher)
				.match(new BlockPos(0, 1, 1), stoneMatcher)
				.match(new BlockPos(0, 1, -1), stoneMatcher)
				.match(new BlockPos(1, 1, 0), stoneMatcher)
				.match(new BlockPos(-1, 1, 0), stoneMatcher)
				.match(new BlockPos(1, -1, 0), stoneMatcher)
				.match(new BlockPos(0, 0, 1), stoneMatcher)
				.match(new BlockPos(0, 0, -1), stoneMatcher)
				.match(new BlockPos(-1, 0, 0), stoneMatcher);

		BLOOMERY_BASE[EnumFacing.EAST.getHorizontalIndex()] = new Multiblock()
				.match(new BlockPos(0, 0, 0), center)
				.match(new BlockPos(0, -1, 0), stoneMatcher)
				.match(new BlockPos(-1, 0, 0), state -> state.getBlock() == BlocksDevice.BLOOMERY)
				.match(new BlockPos(-1, 0, -1), stoneMatcher)
				.match(new BlockPos(-1, 0, 1), stoneMatcher)
				.match(new BlockPos(0, 1, 1), stoneMatcher)
				.match(new BlockPos(0, 1, -1), stoneMatcher)
				.match(new BlockPos(1, 1, 0), stoneMatcher)
				.match(new BlockPos(-1, 1, 0), stoneMatcher)
				.match(new BlockPos(-1, -1, 0), stoneMatcher)
				.match(new BlockPos(0, 0, 1), stoneMatcher)
				.match(new BlockPos(0, 0, -1), stoneMatcher)
				.match(new BlockPos(1, 0, 0), stoneMatcher);


		BLOOMERY_CHIMNEY = new Multiblock()
				.match(new BlockPos(0, 0, 0), insideChimney)
				.match(new BlockPos(1, 0, 0), stoneMatcher)
				.match(new BlockPos(-1, 0, 0), stoneMatcher)
				.match(new BlockPos(0, 0, 1), stoneMatcher)
				.match(new BlockPos(0, 0, -1), stoneMatcher);

		// Gate center is the bloomery gate block
		GATE_Z = new Multiblock()
				.match(new BlockPos(0, 0, 0), state -> state.getBlock() == BlocksDevice.BLOOMERY || state.getBlock() == Blocks.AIR)
				.match(new BlockPos(1, 0, 0), stoneMatcher)
				.match(new BlockPos(-1, 0, 0), stoneMatcher)
				.match(new BlockPos(0, 1, 0), stoneMatcher)
				.match(new BlockPos(0, -1, 0), stoneMatcher);

		GATE_X = new Multiblock()
				.match(new BlockPos(0, 0, 0), state -> state.getBlock() == BlocksDevice.BLOOMERY || state.getBlock() == Blocks.AIR)
				.match(new BlockPos(0, 0, 1), stoneMatcher)
				.match(new BlockPos(0, 0, -1), stoneMatcher)
				.match(new BlockPos(0, 1, 0), stoneMatcher)
				.match(new BlockPos(0, -1, 0), stoneMatcher);
	}

	public BlockBloomery() {
		super(Material.IRON);
		setSoundType(SoundType.METAL);
		setHarvestLevel("pickaxe", 0);
		setHardness(20.0F);
		setDefaultState(this.blockState.getBaseState()
		                               .withProperty(HORIZONTAL, EnumFacing.NORTH)
		                               .withProperty(LIT, false)
		                               .withProperty(OPEN, false));
	}

	public static boolean isValidSideBlock(IBlockState state) {
		return state.getMaterial() == Material.ROCK && state.isNormalCube();
	}

	public static int getChimneyLevels(World world, BlockPos centerPos) {
		for (int i = 1; i < 4; i++) {
			BlockPos center = centerPos.up(i);
			if (!BLOOMERY_CHIMNEY.test(world, center)) {
				return i - 1;
			}
		}
		// Maximum levels
		return 3;
	}

	public boolean canGateStayInPlace(World world, BlockPos pos, EnumFacing.Axis axis) {
		if (axis == EnumFacing.Axis.X) {
			return GATE_X.test(world, pos);
		} else {
			return GATE_Z.test(world, pos);
		}
	}

	public boolean isFormed(World world, BlockPos centerPos, EnumFacing facing) {
		return BLOOMERY_BASE[facing.getHorizontalIndex()].test(world, centerPos);
	}

	@Override
	@Nonnull
	public @NotNull Size getSize(ItemStack stack) {
		return Size.LARGE; // Only in chests
	}

	@Override
	@Nonnull
	public @NotNull Weight getWeight(ItemStack stack) {
		return Weight.VERY_HEAVY;  // stacksize = 1
	}

	@Override
	@SuppressWarnings("deprecation")
	@Nonnull
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState()
		           .withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta % 4))
		           .withProperty(LIT, meta / 4 % 2 != 0)
		           .withProperty(OPEN, meta / 8 != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(HORIZONTAL).getHorizontalIndex()
				+ (state.getValue(LIT) ? 4 : 0)
				+ (state.getValue(OPEN) ? 8 : 0);
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
	@Nonnull
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB[state.getValue(HORIZONTAL).getHorizontalIndex()][0];
	}

	@Override
	@SuppressWarnings("deprecation")
	@Nonnull
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	@SuppressWarnings("deprecation")
	@ParametersAreNonnullByDefault
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		if (blockState.getValue(OPEN)) {
			return NULL_AABB;
		}
		return AABB[blockState.getValue(HORIZONTAL).getHorizontalIndex()][0];
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TEBloomery te = TileUtils.getTile(worldIn, pos, TEBloomery.class);
		if (te != null) {
			te.onBreakBlock(worldIn, pos, state);
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	@SuppressWarnings("deprecation")
	@Nullable
	@ParametersAreNonnullByDefault
	public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
		if (blockState.getValue(OPEN)) {
			int index = blockState.getValue(HORIZONTAL).getHorizontalIndex();
			RayTraceResult rayTraceDoor1 = rayTrace(pos, start, end, AABB[index][1]), rayTraceDoor2 = rayTrace(pos, start, end, AABB[index][2]);

			if (rayTraceDoor1 == null) {
				return rayTraceDoor2;
			} else if (rayTraceDoor2 == null) {
				return rayTraceDoor1;
			}
			if (rayTraceDoor1.hitVec.squareDistanceTo(end) > rayTraceDoor2.hitVec.squareDistanceTo(end)) {
				return rayTraceDoor1;
			}
			return rayTraceDoor2;
		}
		return super.collisionRayTrace(blockState, worldIn, pos, start, end);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, @Nonnull BlockPos pos) {
		return super.canPlaceBlockAt(worldIn, pos) && (canGateStayInPlace(worldIn, pos, EnumFacing.Axis.Z) || canGateStayInPlace(worldIn, pos, EnumFacing.Axis.X));
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			if (!state.getValue(LIT)) {
				worldIn.setBlockState(pos, state.cycleProperty(OPEN));
				worldIn.playSound(null, pos, SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundCategory.BLOCKS, 1.0f, 1.0f);
			}
			TEBloomery te = TileUtils.getTile(worldIn, pos, TEBloomery.class);
			if (te != null) {
				if (!state.getValue(LIT) && te.canIgnite()) {
					ItemStack held = player.getHeldItem(hand);
					if (ItemFireStarter.onIgnition(held)) {
						worldIn.setBlockState(pos, state.withProperty(LIT, true).withProperty(OPEN, false));
						te.onIgnite();
						return true;
					}
				}
			}
		}
		return true;
	}

	@Override
	@SuppressWarnings("deprecation")
	@Nonnull
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		EnumFacing placeDirection;
		float wrappedRotation = MathHelper.wrapDegrees(placer.rotationYaw);
		if (canGateStayInPlace(worldIn, pos, EnumFacing.Axis.X)) {
			// Grab the player facing (in X axis, so, EAST or WEST)
			if (wrappedRotation < 0.0F) {
				placeDirection = EnumFacing.EAST;
			} else {
				placeDirection = EnumFacing.WEST;
			}
		} else if (canGateStayInPlace(worldIn, pos, EnumFacing.Axis.Z)) {
			// Grab the player facing (in Z axis, so, NORTH or SOUTH)
			if (wrappedRotation > 90.0F || wrappedRotation < -90.0F) {
				placeDirection = EnumFacing.NORTH;
			} else {
				placeDirection = EnumFacing.SOUTH;
			}
		} else {
			// Cannot place, skip
			return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
		}
		return this.getDefaultState().withProperty(HORIZONTAL, placeDirection);
	}

	@Override
	@Nonnull
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, HORIZONTAL, LIT, OPEN);
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(LIT) ? 15 : 0;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TEBloomery();
	}

	@Override
	public @NotNull String getName() {
		return "device/bloomery";
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TEBloomery.class;
	}
}
