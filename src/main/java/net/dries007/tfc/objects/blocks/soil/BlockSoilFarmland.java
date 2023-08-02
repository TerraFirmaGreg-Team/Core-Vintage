/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.blocks.soil;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types2.soil.SoilType;
import net.dries007.tfc.api.types2.soil.SoilVariant;
import net.dries007.tfc.api.types2.soil.util.ISoilTypeBlock;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockGrassPath;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types2.soil.SoilVariant.*;
import static net.dries007.tfc.objects.blocks.BlocksTFC.isGravel;
import static net.dries007.tfc.objects.blocks.BlocksTFC.isSand;
import static net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC.WILD;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockSoilFarmland extends Block implements ISoilTypeBlock {
	public static final int MAX_MOISTURE = 15;
	public static final PropertyInteger MOISTURE = PropertyInteger.create("moisture", 0, MAX_MOISTURE);
	public static final int[] TINT = new int[]{
			0xffffffff,
			0xfff7f7f7,
			0xffefefef,
			0xffe7e7e7,
			0xffdfdfdf,
			0xffd7d7d7,
			0xffcfcfcf,
			0xffc7c7c7,
			0xffbfbfbf,
			0xffb7b7b7,
			0xffafafaf,
			0xffa7a7a7,
			0xff9f9f9f,
			0xff979797,
			0xff8f8f8f,
			0xff878787,
	};
	private static final AxisAlignedBB FARMLAND_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);
	private static final AxisAlignedBB FLIPPED_AABB = new AxisAlignedBB(0.0D, 0.9375D, 0.0D, 1.0D, 1.0D, 1.0D);

	private final SoilVariant soilVariant;
	private final SoilType soilType;
	private final ResourceLocation modelLocation;

	public BlockSoilFarmland(SoilVariant soilVariant, SoilType soilType) {
		super(Material.GROUND);

		TFCStorage.addSoilBlock(soilVariant, soilType, this);

		if (soilVariant.canFall())
			FallingBlockManager.registerFallable(this, soilVariant.getFallingSpecification());

		this.soilVariant = soilVariant;
		this.soilType = soilType;
		this.modelLocation = new ResourceLocation(MOD_ID, "soil/" + soilVariant.getName());
		this.useNeighborBrightness = true;

		var blockRegistryName = String.format("soil/%s/%s", soilVariant, soilType);

		this.setCreativeTab(CreativeTabsTFC.EARTH);
		this.setSoundType(SoundType.GROUND);
		this.setHardness(0.6F);
		this.setHarvestLevel("shovel", 0);
		this.setRegistryName(MOD_ID, blockRegistryName);
		this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
		this.setDefaultState(this.blockState.getBaseState().withProperty(MOISTURE, 1)); // 1 is default so it doesn't instantly turn back to dirt
		this.setTickRandomly(true);
		this.setLightOpacity(255);
	}

	protected static void turnToDirt(World world, BlockPos pos) {
		Block block = world.getBlockState(pos).getBlock();
		if (block instanceof ISoilTypeBlock) {
			SoilType soilType = ((ISoilTypeBlock) block).getSoilType();

			world.setBlockState(pos, TFCStorage.getSoilBlock(DIRT, soilType).getDefaultState());
			AxisAlignedBB axisalignedbb = FLIPPED_AABB.offset(pos);
			for (Entity entity : world.getEntitiesWithinAABBExcludingEntity(null, axisalignedbb)) {
				double d0 = Math.min(axisalignedbb.maxY - axisalignedbb.minY, axisalignedbb.maxY - entity.getEntityBoundingBox().minY);
				entity.setPositionAndUpdate(entity.posX, entity.posY + d0 + 0.001D, entity.posZ);
			}
		}
	}

	@Override
	public SoilVariant getSoilVariant() {
		return soilVariant;
	}

	@Override
	public SoilType getSoilType() {
		return soilType;
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlock(this);
	}

	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(MOISTURE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(MOISTURE);
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FARMLAND_AABB;
	}

	@Override
	@SuppressWarnings("deprecation")
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		int current = state.getValue(MOISTURE);
		int target = world.isRainingAt(pos.up()) ? MAX_MOISTURE : getWaterScore(world, pos);

		if (current < target) {
			if (current < MAX_MOISTURE) world.setBlockState(pos, state.withProperty(MOISTURE, current + 1), 2);
		} else if (current > target || target == 0) {
			if (current > 0) world.setBlockState(pos, state.withProperty(MOISTURE, current - 1), 2);
			else if (!hasCrops(world, pos)) turnToDirt(world, pos);
		}

		super.updateTick(world, pos, state, rand);
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return (side != EnumFacing.DOWN && side != EnumFacing.UP);
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(this);
	}

	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
		int beachDistance = 2;

		if (plantable instanceof BlockPlantTFC) {
			switch (((BlockPlantTFC) plantable).getPlantTypeTFC()) {
				case CLAY -> {
					return soilVariant == DIRT || soilVariant == GRASS || soilVariant == DRY_GRASS || soilVariant == CLAY || soilVariant == CLAY_GRASS;
				}
				case DESERT_CLAY -> {
					return soilVariant == CLAY || soilVariant == CLAY_GRASS || isSand(state);
				}
				case DRY_CLAY -> {
					return soilVariant == DIRT || soilVariant == DRY_GRASS || soilVariant == CLAY || soilVariant == CLAY_GRASS || isSand(state);
				}
				case DRY -> {
					return soilVariant == DIRT || soilVariant == DRY_GRASS || isSand(state);
				}
				case FRESH_WATER -> {
					return soilVariant == DIRT || soilVariant == GRASS || soilVariant == DRY_GRASS || isGravel(state);
				}
				case SALT_WATER -> {
					return soilVariant == DIRT || soilVariant == GRASS || soilVariant == DRY_GRASS || isSand(state) || isGravel(state);
				}
				case FRESH_BEACH -> {
					boolean flag = false;
					for (EnumFacing facing : EnumFacing.HORIZONTALS) {
						for (int i = 1; i <= beachDistance; i++) {
							if (BlocksTFC.isFreshWaterOrIce(world.getBlockState(pos.offset(facing, i)))) {
								flag = true;
								break;
							}
						}
					}
					return (soilVariant == DIRT || soilVariant == GRASS || soilVariant == DRY_GRASS || isSand(state)) && flag;
				}
				case SALT_BEACH -> {
					boolean flag = false;
					for (EnumFacing facing : EnumFacing.HORIZONTALS) {
						for (int i = 1; i <= beachDistance; i++)
							if (BlocksTFC.isSaltWater(world.getBlockState(pos.offset(facing, i)))) {
								flag = true;
							}
					}
					return (soilVariant == DIRT || soilVariant == GRASS || soilVariant == DRY_GRASS || isSand(state)) && flag;
				}
			}
		} else if (plantable instanceof BlockCropTFC) {
			IBlockState cropState = world.getBlockState(pos.up());
			if (cropState.getBlock() instanceof BlockCropTFC) {
				boolean isWild = cropState.getValue(WILD);
				if (isWild) {
					if (soilVariant == DIRT || soilVariant == GRASS || soilVariant == DRY_GRASS || soilVariant == CLAY_GRASS) {
						return true;
					}
				}
				return soilVariant == FARMLAND;
			}
		}

		switch (plantable.getPlantType(world, pos.offset(direction))) {
			case Plains -> {
				return soilVariant == DIRT || soilVariant == GRASS || soilVariant == FARMLAND || soilVariant == DRY_GRASS || soilVariant == CLAY || soilVariant == CLAY_GRASS;
			}
			case Crop -> {
				return soilVariant == FARMLAND;
			}
			case Desert -> {
				return isSand(state);
			}
			case Cave -> {
				return true;
			}
			case Water, Nether -> {
				return false;
			}
			case Beach -> {
				boolean flag = false;
				for (EnumFacing facing : EnumFacing.HORIZONTALS) {
					for (int i = 1; i <= beachDistance; i++)
						if (BlocksTFC.isWater(world.getBlockState(pos.offset(facing, i)))) {
							flag = true;
						}
				}
				return (soilVariant == DIRT || soilVariant == GRASS || soilVariant == DRY_GRASS || isSand(state)) && flag;
			}
		}

		return false;
	}

	public int getWaterScore(IBlockAccess world, BlockPos pos) {
		final int hRange = 7;
		float score = 0;
		for (BlockPos.MutableBlockPos i : BlockPos.getAllInBoxMutable(pos.add(-hRange, -1, -hRange), pos.add(hRange, 2, hRange))) {
			BlockPos diff = i.subtract(pos);
			float hDist = MathHelper.sqrt(diff.getX() * diff.getX() + diff.getZ() * diff.getZ());
			if (hDist > hRange) continue;
			if (world.getBlockState(i).getMaterial() != Material.WATER) continue;
			score += ((hRange - hDist) / (float) hRange);
		}
		return score > 1 ? MAX_MOISTURE : Math.round(score * MAX_MOISTURE);
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
		if (fromPos.getY() == pos.getY() + 1) {
			IBlockState up = world.getBlockState(fromPos);
			if (up.isSideSolid(world, fromPos, EnumFacing.DOWN) && FallingBlockManager.getSpecification(up) == null) {
				turnToDirt(world, pos);
			}
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(TFCStorage.getSoilBlock(DIRT, soilType));
	}

	private boolean hasCrops(World worldIn, BlockPos pos) {
		Block block = worldIn.getBlockState(pos.up()).getBlock();
		return block instanceof IPlantable && canSustainPlant(worldIn.getBlockState(pos), worldIn, pos, EnumFacing.UP, (IPlantable) block);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("deprecation")
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		switch (side) {
			case UP:
				return true;
			case NORTH:
			case SOUTH:
			case WEST:
			case EAST:
				IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
				Block block = iblockstate.getBlock();
				if (iblockstate.isOpaqueCube()) return false;
				if (block instanceof BlockFarmland || block instanceof BlockGrassPath) return false;
			default:
				return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, MOISTURE);
	}

//	@SideOnly(Side.CLIENT)
//	@Override
//	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
//	{
//		if (this.soilType.canFall() && rand.nextInt(16) == 0 && FallingBlockManager.shouldFall(world, pos, pos, state, false))
//		{
//			double d0 = (float) pos.getX() + rand.nextFloat();
//			double d1 = (double) pos.getY() - 0.05D;
//			double d2 = (float) pos.getZ() + rand.nextFloat();
//			world.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.getStateId(state));
//		}
//	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onModelRegister() {
		ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
			@Nonnull
			protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
				return new ModelResourceLocation(modelLocation, "soiltype=" + soilType.getName());
			}

		});


		ModelLoader.setCustomModelResourceLocation(
				Item.getItemFromBlock(this),
				this.getMetaFromState(this.getBlockState().getBaseState()),
				new ModelResourceLocation(modelLocation, "soiltype=" + soilType.getName()));
	}

}
