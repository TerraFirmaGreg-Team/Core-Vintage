package net.dries007.tfc.objects.blocks.soil;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types2.soil.Soil;
import net.dries007.tfc.api.types2.soil.SoilVariant;
import net.dries007.tfc.api.types2.soil.util.ISoilBlock;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.init.Items;
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
import static net.dries007.tfc.api.types2.soil.SoilVariant.CLAY;
import static net.dries007.tfc.api.types2.soil.SoilVariant.DIRT;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockSoil extends Block implements ISoilBlock {

		// Used for connected textures only.
		public static final PropertyBool NORTH = PropertyBool.create("north");
		public static final PropertyBool EAST = PropertyBool.create("east");
		public static final PropertyBool SOUTH = PropertyBool.create("south");
		public static final PropertyBool WEST = PropertyBool.create("west");
		private final SoilVariant soilVariant;
		private final Soil soil;
		private final ResourceLocation modelLocation;

		public BlockSoil(SoilVariant soilVariant, Soil soil) {
				super(Material.GROUND);

				if (soilVariant.canFall())
						FallingBlockManager.registerFallable(this, soilVariant.getFallingSpecification());

				this.soilVariant = soilVariant;
				this.soil = soil;
				this.modelLocation = new ResourceLocation(MOD_ID, "soil/" + soilVariant.getName());

				var blockRegistryName = String.format("soil/%s/%s", soilVariant, soil);
				this.setCreativeTab(CreativeTabsTFC.EARTH);
				this.setSoundType(SoundType.GROUND);
				this.setHardness(0.5F);
				this.setHarvestLevel("shovel", 0);
				this.setRegistryName(MOD_ID, blockRegistryName);
				this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));

		}

		@Override
		public SoilVariant getSoilVariant() {
				return soilVariant;
		}

		@Override
		public Soil getSoil() {
				return soil;
		}

		@Override
		public ItemBlock getItemBlock() {
				return new ItemBlockTFC(this);
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
		public int quantityDropped(IBlockState state, int fortune, Random random) {
				if (soilVariant == CLAY)
						return 4;
				return super.quantityDropped(state, fortune, random);
		}

		@Override
		public Item getItemDropped(IBlockState state, Random rand, int fortune) {
				return switch (soilVariant) {
						case CLAY -> Items.CLAY_BALL;
						case DIRT -> Item.getItemFromBlock(TFCStorage.getSoilBlock(DIRT, soil));
						default -> super.getItemDropped(state, rand, fortune);
				};

		}

		@Override
		@SideOnly(Side.CLIENT)
		public void onModelRegister() {
				ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
						@Nonnull
						protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
								return new ModelResourceLocation(modelLocation,
												"soiltype=" + soil.getName());
						}
				});


				ModelLoader.setCustomModelResourceLocation(
								Item.getItemFromBlock(this),
								this.getMetaFromState(this.getBlockState().getBaseState()),
								new ModelResourceLocation(modelLocation,
												"soiltype=" + soil.getName()));
		}

		@Nonnull
		@Override
		@SideOnly(Side.CLIENT)
		public BlockRenderLayer getRenderLayer() {
				return BlockRenderLayer.CUTOUT;
		}
}
