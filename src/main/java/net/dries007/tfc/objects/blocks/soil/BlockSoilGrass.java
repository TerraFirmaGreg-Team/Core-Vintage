/*
 * Licensed under the EUPL, Version 1.2.
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 */

package net.dries007.tfc.objects.blocks.soil;


import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.types2.soil.SoilType;
import net.dries007.tfc.api.types2.soil.SoilVariant;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.api.util.ISoilTypeBlock;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.objects.CreativeTabsTFC;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types2.soil.SoilVariant.DIRT;
import static net.dries007.tfc.api.types2.soil.SoilVariant.GRASS;
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
		this.setSoundType(SoundType.STONE);
		this.setRegistryName(MOD_ID, blockRegistryName);
		this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));

		this.setTickRandomly(true);
		this.setDefaultState(this.blockState.getBaseState()
				.withProperty(NORTH, Boolean.valueOf(false))
				.withProperty(EAST, Boolean.valueOf(false))
				.withProperty(SOUTH, Boolean.valueOf(false))
				.withProperty(WEST, Boolean.valueOf(false)));
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
		return new BlockStateContainer(this, new IProperty[] {EAST, NORTH, WEST, SOUTH, SNOWY});
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
