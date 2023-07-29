/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.blocks.soil;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.types2.soil.SoilType;
import net.dries007.tfc.api.types2.soil.SoilVariant;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.api.types2.soil.util.ISoilTypeBlock;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockGrassPath;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.objects.blocks.soil.BlockSoil.BLOCK_SOIL_MAP;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockSoilPath extends BlockGrassPath implements ISoilTypeBlock {
	private static final AxisAlignedBB GRASS_PATH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

	private final SoilVariant soilVariant;
	private final SoilType soilType;
	private final ResourceLocation modelLocation;

	public BlockSoilPath(SoilVariant soilVariant, SoilType soilType) {

		if (BLOCK_SOIL_MAP.put(new Pair<>(soilVariant, soilType), this) != null)
			throw new RuntimeException("Duplicate registry entry detected for block: " + soilVariant + " " + soilType);


		this.soilVariant = soilVariant;
		this.soilType = soilType;
		this.modelLocation = new ResourceLocation(MOD_ID, "soil/" + soilVariant);

		this.setLightOpacity(255);
		this.useNeighborBrightness = true;

		String blockRegistryName = String.format("soil/%s/%s", soilVariant, soilType);

		this.setCreativeTab(CreativeTabsTFC.EARTH);
		this.setSoundType(SoundType.GROUND);
		this.setHarvestLevel("shovel", 0);
		this.setRegistryName(MOD_ID, blockRegistryName);
		this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
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
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	@SuppressWarnings("deprecation")
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return GRASS_PATH_AABB;
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
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (fromPos.getY() == pos.getY() + 1) {
			IBlockState up = world.getBlockState(fromPos);
			if (up.isSideSolid(world, fromPos, EnumFacing.DOWN) && FallingBlockManager.getSpecification(up) == null) {
				BlockSoilFarmland.turnToDirt(world, pos);
			}
		}
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		BlockPos upPos = pos.up();
		IBlockState up = world.getBlockState(upPos);
		if (up.isSideSolid(world, upPos, EnumFacing.DOWN) && FallingBlockManager.getSpecification(up) == null) {
			BlockSoilFarmland.turnToDirt(world, pos);
		}
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
