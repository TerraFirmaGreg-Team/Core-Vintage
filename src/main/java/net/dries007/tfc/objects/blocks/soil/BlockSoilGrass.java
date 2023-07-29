/*
 * Licensed under the EUPL, Version 1.2.
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 */

package net.dries007.tfc.objects.blocks.soil;


import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.api.types2.soil.SoilType;
import net.dries007.tfc.api.types2.soil.SoilVariant;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.api.types2.soil.util.ISoilTypeBlock;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.plants.BlockShortGrassTFC;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types2.soil.SoilVariant.*;
import static net.dries007.tfc.objects.blocks.soil.BlockSoil.BLOCK_SOIL_MAP;
import static net.dries007.tfc.objects.blocks.soil.BlockSoil.getBlockSoilMap;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockSoilGrass extends BlockGrass implements ISoilTypeBlock {

	// Used for connected textures only.
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");

	private final SoilVariant soilVariant;
	private final SoilType soilType;
	private final ResourceLocation modelLocation;

	public BlockSoilGrass(SoilVariant soilVariant, SoilType soilType) {

		if (BLOCK_SOIL_MAP.put(new Pair<>(soilVariant, soilType), this) != null)
			throw new RuntimeException("Duplicate registry entry detected for block: " + soilVariant + " " + soilType);

		if (soilVariant.canFall()) {
			FallingBlockManager.registerFallable(this, soilVariant.getFallingSpecification());
		}

		this.soilVariant = soilVariant;
		this.soilType = soilType;
		this.modelLocation = new ResourceLocation(MOD_ID, "soil/" + soilVariant);

		String blockRegistryName = String.format("soil/%s/%s", soilVariant, soilType);

		this.setCreativeTab(CreativeTabsTFC.EARTH);
		this.setSoundType(SoundType.GROUND);
		this.setHarvestLevel("shovel", 0);
		this.setRegistryName(MOD_ID, blockRegistryName);
		this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
		this.setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState()
				.withProperty(NORTH, Boolean.FALSE)
				.withProperty(EAST, Boolean.FALSE)
				.withProperty(SOUTH, Boolean.FALSE)
				.withProperty(WEST, Boolean.FALSE));
	}

	public static void spreadGrass(World world, BlockPos pos, IBlockState us, Random rand) {
		// Получаем позицию верхнего блока
		BlockPos upPos = pos.up();
		// Получаем состояние верхнего блока
		IBlockState up = world.getBlockState(upPos);
		// Получаем значение освещенности от соседних блоков
		int neighborLight = world.getLightFromNeighbors(upPos);
		// Получаем тип блока, с которого распространяется трава
		Block usBlock = us.getBlock();

		// Проверяем условие для генерации торфа
		if (up.getMaterial().isLiquid() || (neighborLight < 4 && up.getLightOpacity(world, upPos) > 2)) {
			// Генерируем торф в зависимости от типа блока
			if (usBlock instanceof BlockPeat) {
				world.setBlockState(pos, BlocksTFC.PEAT.getDefaultState());
			} else if (usBlock instanceof ISoilTypeBlock soil) {
				world.setBlockState(pos, getBlockSoilMap(soil.getSoilVariant().getNonGrassVersion(), soil.getSoilType()).getDefaultState());
			}
		} else if (neighborLight >= 9) {
			for (int i = 0; i < 4; ++i) {
				// Генерируем случайную позицию вокруг исходной позиции
				BlockPos target = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

				// Проверяем, находится ли целевая позиция в пределах допустимой области
				if (world.isOutsideBuildHeight(target) || !world.isBlockLoaded(target)) {
					return;
				}

				// Получаем состояние текущего блока
				IBlockState current = world.getBlockState(target);

				// Пропускаем итерацию, если текущий блок не является почвой или уже имеет траву
				if (!BlocksTFC.isSoil(current) || BlocksTFC.isGrass(current)) {
					continue;
				}

				// Получаем позицию верхнего блока целевой позиции
				BlockPos targetUp = target.up();
				// Получаем состояние верхнего блока целевой позиции
				IBlockState targetUpState = world.getBlockState(targetUp);

				// Пропускаем итерацию, если верхний блок жидкость или имеет высокую прозрачность
				if (world.getLightFromNeighbors(targetUp) < 4 || targetUpState.getMaterial().isLiquid() || targetUpState.getLightOpacity(world, targetUp) > 3) {
					continue;
				}

				// Получаем тип текущего блока
				Block currentBlock = current.getBlock();

				// Генерируем траву в зависимости от типа текущего блока
				if (currentBlock instanceof BlockPeat) {
					world.setBlockState(target, BlocksTFC.PEAT_GRASS.getDefaultState());
				} else if (currentBlock instanceof ISoilTypeBlock block) {
					SoilVariant spreader = GRASS;

					// Проверяем тип блока, с которого распространяется трава
					if (usBlock instanceof ISoilTypeBlock && ((ISoilTypeBlock) usBlock).getSoilVariant() == DRY_GRASS) {
						spreader = DRY_GRASS;
					}

					world.setBlockState(pos, getBlockSoilMap(block.getSoilVariant().getGrassVersion(spreader), block.getSoilType()).getDefaultState());
				}
			}
			// Генерируем короткую траву на верхнем блоке с определенной вероятностью
			for (Plant plant : TFCRegistries.PLANTS.getValuesCollection()) {
				if (plant.getPlantType() == Plant.PlantType.SHORT_GRASS && rand.nextFloat() < 0.5f) {
					float temp = ClimateTFC.getActualTemp(world, upPos);
					BlockShortGrassTFC plantBlock = BlockShortGrassTFC.get(plant);

					// Проверяем условия для генерации короткой травы
					if (world.isAirBlock(upPos) &&
							plant.isValidLocation(temp, ChunkDataTFC.getRainfall(world, upPos), Math.subtractExact(world.getLightFor(EnumSkyBlock.SKY, upPos), world.getSkylightSubtracted())) &&
							plant.isValidGrowthTemp(temp) &&
							rand.nextDouble() < plantBlock.getGrowthRate(world, upPos)) {
						world.setBlockState(upPos, plantBlock.getDefaultState());
					}
				}
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
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			if (!worldIn.isAreaLoaded(pos, 3))
				return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
			Block block = worldIn.getBlockState(pos).getBlock();
			if (block instanceof ISoilTypeBlock) {
				SoilType soilType = ((ISoilTypeBlock) block).getSoilType();

				if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2) {
					worldIn.setBlockState(pos, getBlockSoilMap(DIRT, soilType).getDefaultState());
				} else {
					if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
						for (int i = 0; i < 4; ++i) {
							BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

							if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos)) {
								return;
							}

							IBlockState iblockstate = worldIn.getBlockState(blockpos.up());
							IBlockState iblockstate1 = worldIn.getBlockState(blockpos);

							if (iblockstate1.getBlock() == getBlockSoilMap(DIRT, soilType) && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate.getLightOpacity(worldIn, pos.up()) <= 2) {
								worldIn.setBlockState(blockpos, getBlockSoilMap(GRASS, soilType).getDefaultState());
							}
						}
					}
				}
			}
		}
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		pos = pos.add(0, -1, 0);
		Block blockUp = world.getBlockState(pos.up()).getBlock();
		return state
				.withProperty(NORTH, Boolean.valueOf(world.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() instanceof BlockSoilGrass))
				.withProperty(EAST, Boolean.valueOf(world.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() instanceof BlockSoilGrass))
				.withProperty(SOUTH, Boolean.valueOf(world.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() instanceof BlockSoilGrass))
				.withProperty(WEST, Boolean.valueOf(world.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() instanceof BlockSoilGrass));
//				.withProperty(SNOWY, Boolean.valueOf(blockUp == Blocks.SNOW || blockUp == Blocks.SNOW_LAYER));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{EAST, NORTH, WEST, SOUTH, SNOWY});
	}

	@Override
	public void randomTick(World world, BlockPos pos, IBlockState state, Random rand) {
		if (world.isRemote) return;
		spreadGrass(world, pos, state, rand);
		super.randomTick(world, pos, state, rand);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (this.soilVariant.canFall() && rand.nextInt(16) == 0 && FallingBlockManager.shouldFall(world, pos, pos, state, false)) {
			double d0 = (float) pos.getX() + rand.nextFloat();
			double d1 = (double) pos.getY() - 0.05D;
			double d2 = (float) pos.getZ() + rand.nextFloat();
			world.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.getStateId(state));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("deprecation")
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return super.shouldSideBeRendered(blockState, world, pos, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onModelRegister() {
		ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
			@Nonnull
			protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
				return new ModelResourceLocation(modelLocation,
						"east=" + state.getValue(EAST) + "," +
								"north=" + state.getValue(NORTH) + "," +
								"soiltype=" + soilType.getName() + "," +
								"south=" + state.getValue(SOUTH) + "," +
								"west=" + state.getValue(WEST));
			}
		});

		ModelLoader.setCustomModelResourceLocation(
				Item.getItemFromBlock(this),
				this.getMetaFromState(this.getBlockState().getBaseState()),
				new ModelResourceLocation(modelLocation,
						"east=false,north=false," +
								"soiltype=" + soilType.getName() + "," +
								"south=false,west=false"));
	}

	@Nonnull
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
}
