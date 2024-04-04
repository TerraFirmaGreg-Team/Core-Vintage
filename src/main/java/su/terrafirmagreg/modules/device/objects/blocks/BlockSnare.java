package su.terrafirmagreg.modules.device.objects.blocks;

import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.entity.animal.AnimalFood;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.block.BlockBase;
import su.terrafirmagreg.api.spi.tile.ITEBlock;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalBase;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalHare;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalPheasant;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalRabbit;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalTurkey;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalChicken;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalDuck;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalGrouse;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalQuail;
import su.terrafirmagreg.modules.device.objects.tiles.TESnare;

import javax.annotation.Nullable;
import java.util.Random;

import static su.terrafirmagreg.api.util.PropertyUtils.*;


public class BlockSnare extends BlockBase implements ITEBlock {

	protected static final AxisAlignedBB TRAP_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D);


	public BlockSnare() {
		super(Material.WOOD);

		this.setTickRandomly(true);
		this.setSoundType(SoundType.WOOD);
		this.setHardness(1.5f);
		this.setHarvestLevel("axe", 0);
		this.setDefaultState(this.blockState.getBaseState()
		                                    .withProperty(HORIZONTAL, EnumFacing.NORTH)
		                                    .withProperty(BAITED, Boolean.FALSE)
		                                    .withProperty(CLOSED, Boolean.FALSE));
	}

	@Override
	public boolean hasTileEntity(@NotNull IBlockState state) {
		return true;
	}

	@Override
	public TESnare createTileEntity(@NotNull World world, @NotNull IBlockState state) {
		return new TESnare();
	}

	@Override
	protected @NotNull BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, HORIZONTAL, BAITED, CLOSED);
	}

	@NotNull
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState()
		           .withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta % 4))
		           .withProperty(BAITED, meta / 4 % 2 != 0)
		           .withProperty(CLOSED, meta / 8 != 0);
	}

	public int getMetaFromState(IBlockState state) {
		return state.getValue(HORIZONTAL).getHorizontalIndex() + (state.getValue(BAITED) ? 4 : 0) + (state.getValue(CLOSED) ? 8 : 0);
	}

	@SuppressWarnings("deprecation")
	public @NotNull IBlockState getStateForPlacement(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(HORIZONTAL, placer.getHorizontalFacing());
	}

	@Nullable
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, @NotNull IBlockAccess worldIn, @NotNull BlockPos pos) {
		AxisAlignedBB axisalignedbb = blockState.getBoundingBox(worldIn, pos);
		return new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.maxX, (float) 0 * 0.125F, axisalignedbb.maxZ);
	}

	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState iblockstate = worldIn.getBlockState(pos.down());
		Block block = iblockstate.getBlock();

		if (block != Blocks.BARRIER) {
			BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP);
			return blockfaceshape == BlockFaceShape.SOLID || iblockstate.getBlock()
			                                                            .isLeaves(iblockstate, worldIn, pos.down());
		} else {
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	public boolean isOpaqueCube(@NotNull IBlockState state) {
		return false;
	}

	@SuppressWarnings("deprecation")
	public boolean isFullCube(@NotNull IBlockState state) {
		return false;
	}

	public void harvestBlock(@NotNull World worldIn, @NotNull EntityPlayer player, @NotNull BlockPos pos, @NotNull IBlockState state, @Nullable TileEntity te, @NotNull ItemStack stack) {
		TESnare snare = TileUtils.getTile(worldIn, pos, TESnare.class);
		assert snare != null;
		if (!snare.isOpen()) {
			if (Math.random() < ConfigTFCThings.Items.SNARE.breakChance) {
				worldIn.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0f, 0.8f);
			} else {
				super.harvestBlock(worldIn, player, pos, state, te, stack);
			}
		} else {
			super.harvestBlock(worldIn, player, pos, state, te, stack);
		}
	}

	public boolean onBlockActivated(@NotNull World worldIn, @NotNull BlockPos pos, IBlockState state, @NotNull EntityPlayer playerIn, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!state.getValue(BAITED)) {
			ItemStack stack = playerIn.getHeldItem(hand);
			if ((stack.getItem() instanceof ItemSeedsTFC || isFood(stack)) && !worldIn.isRemote) {
				if (!playerIn.isCreative()) {
					stack.shrink(1);
					if (stack.isEmpty()) {
						playerIn.inventory.deleteStack(stack);
					}
				}
				state = state.withProperty(BAITED, Boolean.TRUE);
				worldIn.setBlockState(pos, state, 2);
			}
		}
		return true;
	}

	private boolean isFood(ItemStack stack) {
		AnimalFood food = AnimalFood.get(EntityAnimalChicken.class);
		return food != null && food.isFood(stack);
	}

	@Override
	public void onEntityCollision(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull Entity entityIn) {
		if (isCapturable(entityIn)) {
			TESnare snare = TileUtils.getTile(worldIn, pos, TESnare.class);
			EntityLivingBase entityLiving = (EntityLivingBase) entityIn;
			assert snare != null;
			if (snare.isOpen()) {
				snare.setCapturedEntity(entityLiving);
				entityIn.setPositionAndUpdate(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
				snare.setOpen(false);
				state = state.withProperty(CLOSED, Boolean.TRUE);
				state = state.withProperty(BAITED, Boolean.FALSE);
				worldIn.setBlockState(pos, state, 2);
			} else if (snare.getCapturedEntity() != null && snare.getCapturedEntity().equals(entityLiving)) {
				entityLiving.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
			}
		}
	}

	public void updateTick(@NotNull World worldIn, BlockPos pos, @NotNull IBlockState state, @NotNull Random rand) {
		AxisAlignedBB captureBox = new AxisAlignedBB(pos.getX() - 10.0D, pos.getY() - 5.0D, pos.getZ() - 10.0D, pos.getX() + 10.0D, pos.getY() + 5.0D, pos.getZ() + 10.0D);
		TESnare snare = TileUtils.getTile(worldIn, pos, TESnare.class);
		assert snare != null;
		if (snare.isOpen() && worldIn.getEntitiesWithinAABB(EntityPlayer.class, captureBox)
		                             .isEmpty() && !worldIn.isRemote) {
			for (EntityAnimalBase animal : worldIn.getEntitiesWithinAABB(EntityAnimalBase.class, captureBox)) {
				if ((isCapturable(animal)) && !(worldIn.getBlockState(animal.getPosition())
				                                       .getBlock() instanceof BlockSnare)) {
					snare.setCapturedEntity(animal);
					snare.setOpen(false);
					state = state.withProperty(CLOSED, Boolean.TRUE);
					state = state.withProperty(BAITED, Boolean.FALSE);
					worldIn.setBlockState(pos, state, 2);
					animal.setPositionAndUpdate(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
					return;
				}
			}
			if (state.getValue(BAITED)) {
				if (rand.nextDouble() < ConfigTFCThings.Items.SNARE.baitCaptureChance) {
					double entitySelection = rand.nextDouble();
					EntityAnimalBase animal;
					if (entitySelection < 0.1) {
						if (entitySelection < 0.03) {
							if (entitySelection < 0.01) {
								animal = new EntityAnimalGrouse(worldIn);
							} else {
								animal = new EntityAnimalQuail(worldIn);
							}
						} else {
							animal = new EntityAnimalDuck(worldIn);
						}
					} else if (entitySelection < 0.5) {
						if (entitySelection < 0.3) {
							animal = new EntityAnimalHare(worldIn);
						} else {
							animal = new EntityAnimalRabbit(worldIn);
						}
					} else {
						animal = new EntityAnimalPheasant(worldIn);
					}
					animal.setLocationAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
					worldIn.spawnEntity(animal);
					snare.setCapturedEntity(animal);
					snare.setOpen(false);
					state = state.withProperty(CLOSED, Boolean.TRUE);
					state = state.withProperty(BAITED, Boolean.FALSE);
					worldIn.setBlockState(pos, state, 2);
				} else if (rand.nextDouble() < ConfigTFCThings.Items.SNARE.baitExpireChance) {
					state = state.withProperty(BAITED, Boolean.FALSE);
					worldIn.setBlockState(pos, state, 2);
				}
			}
		}
	}

	private boolean isCapturable(Entity entityIn) {
		return entityIn instanceof EntityAnimalRabbit || entityIn instanceof EntityAnimalPheasant || entityIn instanceof EntityAnimalDuck || entityIn instanceof EntityAnimalChicken || entityIn instanceof EntityAnimalTurkey;
	}

	public void neighborChanged(@NotNull IBlockState state, World worldIn, BlockPos pos, @NotNull Block blockIn, @NotNull BlockPos fromPos) {
		if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP)) {
			TESnare snare = TileUtils.getTile(worldIn, pos, TESnare.class);
			if (!snare.isOpen()) {
				if (Math.random() < ConfigTFCThings.Items.SNARE.breakChance) {
					worldIn.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0f, 0.8f);
				} else {
					this.dropBlockAsItem(worldIn, pos, state, 0);
				}
			} else {
				this.dropBlockAsItem(worldIn, pos, state, 0);
			}
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public @NotNull Size getSize(@NotNull ItemStack itemStack) {
		return Size.LARGE;
	}

	@Override
	public @NotNull Weight getWeight(@NotNull ItemStack itemStack) {
		return Weight.MEDIUM;
	}

	@SuppressWarnings("deprecation")
	public @NotNull AxisAlignedBB getBoundingBox(@NotNull IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
		return TRAP_AABB;
	}

	public boolean isPassable(@NotNull IBlockAccess worldIn, @NotNull BlockPos pos) {
		return true;
	}

	@SuppressWarnings("deprecation")
	public @NotNull BlockFaceShape getBlockFaceShape(@NotNull IBlockAccess worldIn, @NotNull IBlockState state, @NotNull BlockPos pos, @NotNull EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}


	@Override
	public @NotNull String getName() {
		return "device/snare";
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TESnare.class;
	}
}
