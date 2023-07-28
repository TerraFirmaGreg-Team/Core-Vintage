/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.blocks.soil;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types2.soil.SoilType;
import net.dries007.tfc.api.types2.soil.SoilVariant;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.api.util.ISoilTypeBlock;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.plants.BlockShortGrassTFC;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockSoil extends Block implements ISoilTypeBlock {

	// Used for connected textures only.
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final Map<Pair<SoilVariant, SoilType>, ISoilTypeBlock> BLOCK_SOIL_MAP = new LinkedHashMap<>();
	private final SoilVariant soilVariant;
	private final SoilType soilType;
	private final ResourceLocation modelLocation;

	public BlockSoil(SoilVariant soilVariant, SoilType soilType) {
		super(Material.GROUND);

		if (BLOCK_SOIL_MAP.put(new Pair<>(soilVariant, soilType), this) != null)
			throw new RuntimeException("Duplicate registry entry detected for block: " + soilVariant + " " + soilType);

		if (soilVariant.canFall())
			FallingBlockManager.registerFallable(this, soilVariant.getFallingSpecification());

		this.soilVariant = soilVariant;
		this.soilType = soilType;
		this.modelLocation = new ResourceLocation(MOD_ID, "soil/" + soilVariant.getName());

		String blockRegistryName = String.format("soil/%s/%s", soilVariant, soilType);

		this.setCreativeTab(CreativeTabsTFC.EARTH);
		this.setSoundType(SoundType.STONE);
		this.setRegistryName(MOD_ID, blockRegistryName);
		this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));

	}

	public static Block getBlockSoilMap(SoilVariant soilVariant, SoilType soilType) {
		return (Block) BLOCK_SOIL_MAP.get(new Pair<>(soilVariant, soilType));
	}

	public static void spreadGrass(World world, BlockPos pos, IBlockState us, Random rand) {
		BlockPos upPos = pos.up();
		IBlockState up = world.getBlockState(upPos);
		int neighborLight;
		Block usBlock = us.getBlock();
		if (up.getMaterial().isLiquid() || ((neighborLight = world.getLightFromNeighbors(upPos)) < 4 && up.getLightOpacity(world, upPos) > 2)) {
			if (usBlock instanceof BlockPeat) {
				world.setBlockState(pos, BlocksTFC.PEAT.getDefaultState());
			}
//            else if (usBlock instanceof ISoilTypeBlock) {
//                ISoilTypeBlock soil = ((ISoilTypeBlock) usBlock);
//                world.setBlockState(pos, getBlockSoilMap(soil.getSoilVariant().getNonGrassVersion())).getDefaultState());
//            }
		} else if (neighborLight >= 9) {
			for (int i = 0; i < 4; ++i) {
				BlockPos target = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
				if (world.isOutsideBuildHeight(target) || !world.isBlockLoaded(target)) {
					return;
				}
				IBlockState current = world.getBlockState(target);
				if (!BlocksTFC.isSoil(current) || BlocksTFC.isGrass(current)) {
					continue;
				}
				BlockPos targetUp = target.up();
				IBlockState targetUpState;
				if (world.getLightFromNeighbors(targetUp) < 4 || (targetUpState = world.getBlockState(targetUp)).getMaterial().isLiquid() || targetUpState.getLightOpacity(world, targetUp) > 3) {
					continue;
				}
				Block currentBlock = current.getBlock();
				if (currentBlock instanceof BlockPeat) {
					world.setBlockState(target, BlocksTFC.PEAT_GRASS.getDefaultState());
				} else if (currentBlock instanceof BlockRockVariant) {
					Rock.Type spreader = Rock.Type.GRASS;
					usBlock = us.getBlock();
					if ((usBlock instanceof BlockRockVariant) && ((BlockRockVariant) usBlock).getType() == Rock.Type.DRY_GRASS) {
						spreader = Rock.Type.DRY_GRASS;
					}
					BlockRockVariant block = ((BlockRockVariant) current.getBlock());
					world.setBlockState(target, block.getVariant(block.getType().getGrassVersion(spreader)).getDefaultState());
				}
			}
			for (Plant plant : TFCRegistries.PLANTS.getValuesCollection()) {
				if (plant.getPlantType() == Plant.PlantType.SHORT_GRASS && rand.nextFloat() < 0.5f) {
					float temp = ClimateTFC.getActualTemp(world, upPos);
					BlockShortGrassTFC plantBlock = BlockShortGrassTFC.get(plant);

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

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		pos = pos.add(0, -1, 0);
		return state.withProperty(NORTH, BlocksTFC.isGrass(world.getBlockState(pos.offset(EnumFacing.NORTH))))
				.withProperty(EAST, BlocksTFC.isGrass(world.getBlockState(pos.offset(EnumFacing.EAST))))
				.withProperty(SOUTH, BlocksTFC.isGrass(world.getBlockState(pos.offset(EnumFacing.SOUTH))))
				.withProperty(WEST, BlocksTFC.isGrass(world.getBlockState(pos.offset(EnumFacing.WEST))));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH);
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
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return super.getItemDropped(state, rand, fortune);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onModelRegister() {
		ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
			@Nonnull
			protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
				return new ModelResourceLocation(modelLocation,
						"soiltype=" + soilType.getName());
			}
		});


		ModelLoader.setCustomModelResourceLocation(
				Item.getItemFromBlock(this),
				this.getMetaFromState(this.getBlockState().getBaseState()),
				new ModelResourceLocation(modelLocation,
						"soiltype=" + soilType.getName()));
	}

	@Nonnull
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}
}
