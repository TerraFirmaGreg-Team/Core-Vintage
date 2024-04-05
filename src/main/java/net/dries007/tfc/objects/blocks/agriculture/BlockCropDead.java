package net.dries007.tfc.objects.blocks.agriculture;

import net.dries007.tfc.api.capability.player.CapabilityPlayerData;
import net.dries007.tfc.api.types.ICrop;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.plants.BlockEmergentTallWaterPlantTFC;
import net.dries007.tfc.objects.blocks.plants.BlockWaterPlantTFC;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.util.agriculture.Crop;
import net.dries007.tfc.util.skills.SimpleSkill;
import net.dries007.tfc.util.skills.SkillType;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import org.jetbrains.annotations.NotNull;
import tfcflorae.objects.blocks.plants.BlockWaterPlantTFCF;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class BlockCropDead extends BlockBush { //implements IGrowingPlant
	/* true if the crop spawned in the wild, means it ignores growth conditions i.e. farmland */
	public static final PropertyBool MATURE = PropertyBool.create("mature");

	// binary flags for state and metadata conversion
	private static final int META_MATURE = 1;

	// static field and methods for conversion from crop to Block
	private static final Map<ICrop, BlockCropDead> MAP = new HashMap<>();
	protected final ICrop crop;

	public BlockCropDead(ICrop crop) {
		super(Material.PLANTS);

		this.crop = crop;
		if (MAP.put(crop, this) != null) {
			throw new IllegalStateException("There can only be one.");
		}

		setSoundType(SoundType.PLANT);
		setHardness(0.6f);
	}

	public static BlockCropDead get(ICrop crop) {
		return MAP.get(crop);
	}

	public static Set<ICrop> getCrops() {
		return MAP.keySet();
	}

	@NotNull
	public ICrop getCrop() {
		return crop;
	}

	@Override
	@NotNull
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(MATURE, (meta & META_MATURE) > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(MATURE) ? META_MATURE : 0;
	}

	@NotNull
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ItemSeedsTFC.get(crop);
	}

	@Override
	@NotNull
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, MATURE);
	}

	@Override
	@NotNull
	public Block.EnumOffsetType getOffsetType() {
		return Block.EnumOffsetType.XZ;
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		// dead crops always drop at least 1 seed
		int count = 1;
		if (state.getValue(MATURE)) {
			// (mature and dead) crops always drop 1 extra seed
			count++;
			// mature crops have a chance to drop a bonus, dead or alive
			EntityPlayer player = harvesters.get();
			if (player != null) {
				SimpleSkill skill = CapabilityPlayerData.getSkill(player, SkillType.AGRICULTURE);
				if (skill != null) {
					count += Crop.getSkillSeedBonus(skill, RANDOM);
					skill.add(0.04f);
				}
			}
		}

		return count;
	}

	@Override
	@NotNull
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(ItemSeedsTFC.get(crop));
	}

	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		IBlockState soil;
		if (this.crop != Crop.RICE) {
			soil = worldIn.getBlockState(pos.down());
			return soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), EnumFacing.UP, this);
		} else {
			soil = worldIn.getBlockState(pos.down());
			if (!(soil.getBlock() instanceof BlockWaterPlantTFCF) && !(soil.getBlock() instanceof BlockWaterPlantTFC)) {
				if (state.getBlock() != this) {
					return this.canSustainBush(soil);
				} else {
					IBlockState stateDown = worldIn.getBlockState(pos.down());
					Material material = stateDown.getMaterial();
					return soil.getBlock()
					           .canSustainPlant(soil, worldIn, pos.down(), EnumFacing.UP, this) || material == Material.WATER && (Integer) stateDown.getValue(BlockLiquid.LEVEL) == 0 && stateDown == ChunkGenTFC.FRESH_WATER || material == Material.ICE || material == Material.CORAL && !(state.getBlock() instanceof BlockEmergentTallWaterPlantTFC);
				}
			} else {
				return false;
			}
		}
	}

	@NotNull
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 1.0, 0.875);
	}

	@NotNull
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
		return EnumPlantType.Crop;
	}

	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		if (this.crop != Crop.RICE) {
			return super.canPlaceBlockAt(worldIn, pos);
		} else {
			return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos, worldIn.getBlockState(pos));
		}
	}

	protected boolean canSustainBush(IBlockState state) {
		if (this.crop != Crop.RICE) {
			return super.canSustainBush(state);
		} else {
			return BlocksTFC.isWater(state) || state.getMaterial() == Material.ICE && state == ChunkGenTFC.FRESH_WATER || state.getMaterial() == Material.CORAL && !(state.getBlock() instanceof BlockEmergentTallWaterPlantTFC);
		}
	}
}
