package su.terrafirmagreg.modules.device.objects.blocks;

import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.metal.ItemMetalTool;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.spi.block.BlockBase;
import su.terrafirmagreg.api.spi.tile.ITEBlock;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.animal.api.type.IPredator;
import su.terrafirmagreg.modules.core.api.util.DamageSources;
import su.terrafirmagreg.modules.device.objects.tiles.TEBearTrap;

import static su.terrafirmagreg.api.util.PropertyUtils.*;

public class BlockBearTrap extends BlockBase implements ITEBlock {

	protected static final AxisAlignedBB TRAP_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D);

	public BlockBearTrap() {
		super(Material.IRON);

		this.setHardness(10.0F);
		this.setResistance(10.0F);
		this.setHarvestLevel("pickaxe", 0);
		this.setDefaultState(this.blockState.getBaseState()
				.withProperty(HORIZONTAL, EnumFacing.NORTH)
				.withProperty(BURIED, Boolean.FALSE)
				.withProperty(CLOSED, Boolean.FALSE));
	}

	@Override
	public @NotNull String getName() {
		return "device/bear_trap";
	}

	@Override
	public @Nullable ItemBlock getItemBlock() {
		return new ItemBlock(this);
	}

	@Override
	public boolean hasTileEntity(@NotNull IBlockState state) {
		return true;
	}

	@Override
	public TEBearTrap createTileEntity(@NotNull World world, @NotNull IBlockState state) {
		return new TEBearTrap();
	}


	@SuppressWarnings("deprecation")
	public @NotNull AxisAlignedBB getBoundingBox(@NotNull IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
		return TRAP_AABB;
	}

	public boolean isPassable(@NotNull IBlockAccess worldIn, @NotNull BlockPos pos) {
		return true;
	}

	@Override
	protected @NotNull BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, HORIZONTAL, BURIED, CLOSED);
	}

	@SuppressWarnings("deprecation")
	public @NotNull BlockFaceShape getBlockFaceShape(@NotNull IBlockAccess worldIn, @NotNull IBlockState state, @NotNull BlockPos pos, @NotNull EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}

	@NotNull
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState()
				.withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta % 4))
				.withProperty(BURIED, meta / 4 % 2 != 0)
				.withProperty(CLOSED, meta / 8 != 0);
	}

	public int getMetaFromState(IBlockState state) {
		return state.getValue(HORIZONTAL).getHorizontalIndex() + (state.getValue(BURIED) ? 4 : 0) + (state.getValue(CLOSED) ? 8 : 0);
	}

	@SuppressWarnings("deprecation")
	public @NotNull IBlockState getStateForPlacement(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(HORIZONTAL, placer.getHorizontalFacing());
	}

	@Nullable
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, @NotNull IBlockAccess worldIn, @NotNull BlockPos pos) {
		AxisAlignedBB axisalignedbb = blockState.getBoundingBox(worldIn, pos);
		return new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.maxX, (double) ((float) 0 * 0.125F), axisalignedbb.maxZ);
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
		TEBearTrap trap = TileUtils.getTile(worldIn, pos, TEBearTrap.class);
		if (!trap.isOpen()) {
			if (Math.random() < ConfigTFCThings.Items.BEAR_TRAP.breakChance) {
				worldIn.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0f, 0.8f);
			} else {
				super.harvestBlock(worldIn, player, pos, state, te, stack);
			}
		} else {
			super.harvestBlock(worldIn, player, pos, state, te, stack);
		}
	}

	public boolean onBlockActivated(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, EntityPlayer playerIn, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (playerIn.getHeldItem(hand).getItem() instanceof ItemSpade || (playerIn.getHeldItem(hand).getItem() instanceof ItemMetalTool &&
				((ItemMetalTool) playerIn.getHeldItem(hand).getItem()).getType()
						.equals(Metal.ItemType.SHOVEL))) {
			playerIn.getHeldItem(hand).damageItem(1, playerIn);
			state = state.cycleProperty(BURIED);
			worldIn.setBlockState(pos, state, 2);
			worldIn.playSound(playerIn, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f);
		}
		return true;
	}

	@Override
	public void onEntityCollision(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull Entity entityIn) {
		if (entityIn instanceof EntityLivingBase entityLiving) {
			TEBearTrap trap = TileUtils.getTile(worldIn, pos, TEBearTrap.class);
			if (trap.isOpen()) {
				int debuffDuration = ConfigTFCThings.Items.BEAR_TRAP.debuffDuration;
				double healthCut = ConfigTFCThings.Items.BEAR_TRAP.healthCut;
				entityLiving.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, debuffDuration));
				entityLiving.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, debuffDuration));
				entityLiving.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, debuffDuration));
				if (ConfigTFCThings.Items.BEAR_TRAP.fixedDamage > 0) {
					entityLiving.attackEntityFrom(DamageSources.BEAR_TRAP, (float) ConfigTFCThings.Items.BEAR_TRAP.fixedDamage);
				} else if (healthCut > 0) {
					entityLiving.attackEntityFrom(DamageSources.BEAR_TRAP, entityLiving.getHealth() / (float) healthCut);
				}
				trap.setCapturedEntity(entityLiving);
				entityIn.setPositionAndUpdate(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
				trap.setOpen(false);
				state = state.withProperty(CLOSED, Boolean.TRUE);
				worldIn.setBlockState(pos, state, 2);
				entityLiving.playSound(SoundEvents.ENTITY_ITEM_BREAK, 2.0F, 0.4F);
			} else if (trap.getCapturedEntity() != null && trap.getCapturedEntity().equals(entityLiving)) {
				entityLiving.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
				if (entityLiving instanceof IPredator && Math.random() < ConfigTFCThings.Items.BEAR_TRAP.breakoutChance) {
					worldIn.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0f, 0.8f);
					if (Math.random() > 2 * ConfigTFCThings.Items.BEAR_TRAP.breakChance) {
						this.dropBlockAsItem(worldIn, pos, state, 0);
					}
					worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), worldIn.isRemote ? 11 : 3);
				}
				if (entityLiving.isDead) {
					trap.setCapturedEntity(null);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void neighborChanged(@NotNull IBlockState state, World worldIn, BlockPos pos, @NotNull Block blockIn, @NotNull BlockPos fromPos) {
		if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP)) {
			TEBearTrap trap = TileUtils.getTile(worldIn, pos, TEBearTrap.class);
			if (!trap.isOpen()) {
				if (Math.random() < ConfigTFCThings.Items.BEAR_TRAP.breakChance) {
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
		return Weight.HEAVY;
	}

	@Override
	public Class<? extends TileEntity> getTileEntityClass() {
		return TEBearTrap.class;
	}
}
