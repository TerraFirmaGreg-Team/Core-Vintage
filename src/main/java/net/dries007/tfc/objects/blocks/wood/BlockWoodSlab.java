package net.dries007.tfc.objects.blocks.wood;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types2.wood.Wood;
import net.dries007.tfc.api.types2.wood.WoodVariant;
import net.dries007.tfc.api.types2.wood.util.IWoodBlock;
import net.dries007.tfc.client.CustomStateMap;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.wood.ItemWoodSlab;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types2.wood.WoodVariant.PLANKS;
import static net.dries007.tfc.api.types2.wood.WoodVariant.SLAB_DOUBLE;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class BlockWoodSlab extends BlockSlab implements IWoodBlock {
		public static final PropertyEnum<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);
		public final Block modelBlock;
		protected Half halfSlab;

		private BlockWoodSlab(WoodVariant woodVariant, Wood wood) {
				super(Material.WOOD);

				IBlockState state = blockState.getBaseState();

				if (!isDouble()) state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);

				this.modelBlock = TFCStorage.getWoodBlock(PLANKS, wood);
				useNeighborBrightness = true;

				setLightOpacity(255);
				setDefaultState(state.withProperty(VARIANT, Variant.DEFAULT));
				setCreativeTab(CreativeTabsTFC.WOOD);
				setSoundType(SoundType.STONE);

				Blocks.FIRE.setFireInfo(this, 5, 20);
		}

		@Override
		public String getTranslationKey(int meta) {
				return super.getTranslationKey();
		}

		@Override
		public IProperty<?> getVariantProperty() {
				return VARIANT; // why is this not null-tolerable ...
		}

		@Override
		public Comparable<?> getTypeForItem(ItemStack stack) {
				return Variant.DEFAULT;
		}

		@SuppressWarnings("deprecation")
		@Override
		public IBlockState getStateFromMeta(int meta) {
				IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, Variant.DEFAULT);

				if (!this.isDouble()) {
						iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
				}

				return iblockstate;
		}

		@Override
		public int getMetaFromState(IBlockState state) {
				int i = 0;

				if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
						i |= 8;
				}

				return i;
		}

		@SuppressWarnings("deprecation")
		@Override
		public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
				return modelBlock.getBlockHardness(blockState, worldIn, pos);
		}

		@Override
		public Item getItemDropped(IBlockState state, Random rand, int fortune) {
				return Item.getItemFromBlock(halfSlab);
		}

		@SuppressWarnings("deprecation")
		@Override
		public float getExplosionResistance(Entity exploder) {
				return modelBlock.getExplosionResistance(exploder);
		}

		@SuppressWarnings("deprecation")
		@Override
		public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
				return new ItemStack(halfSlab);
		}

		@Override
		protected BlockStateContainer createBlockState() {
				return this.isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, HALF, VARIANT);
		}

		@SuppressWarnings("deprecation")
		@Override
		public SoundType getSoundType() {
				return modelBlock.getSoundType();
		}

		public enum Variant implements IStringSerializable {
				DEFAULT;

				@Override
				public String getName() {
						return "default";
				}
		}

		public static class Double extends BlockWoodSlab {
				private final WoodVariant woodVariant;
				private final Wood wood;
				private final ResourceLocation modelLocation;

				public Double(WoodVariant woodVariant, Wood wood) {
						super(woodVariant, wood);

						this.woodVariant = woodVariant;
						this.wood = wood;
						this.modelLocation = new ResourceLocation(MOD_ID, "wood/" + woodVariant);

						var blockRegistryName = String.format("wood/%s/%s", woodVariant, wood);
						setRegistryName(MOD_ID, blockRegistryName);
						setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
				}

				@Override
				public boolean isDouble() {
						return true;
				}

				@Override
				public WoodVariant getWoodVariant() {
						return woodVariant;
				}

				@Override
				public Wood getWood() {
						return wood;
				}

				@Override
				public ItemBlock getItemBlock() {
						return null;
				}

				@Override
				@SideOnly(Side.CLIENT)
				public void onModelRegister() {
						ModelLoader.setCustomStateMapper(this, new CustomStateMap.Builder().customPath(modelLocation).ignore(BlockWoodSlab.VARIANT).build());

				}
		}

		public static class Half extends BlockWoodSlab {
				public final Double doubleSlab;

				private final WoodVariant woodVariant;
				private final Wood wood;
				private final ResourceLocation modelLocation;

				public Half(WoodVariant woodVariant, Wood wood) {
						super(woodVariant, wood);

						doubleSlab = (Double) TFCStorage.getWoodBlock(SLAB_DOUBLE, wood);
						doubleSlab.halfSlab = this;
						halfSlab = this;

						this.woodVariant = woodVariant;
						this.wood = wood;
						this.modelLocation = new ResourceLocation(MOD_ID, "wood/" + woodVariant);

						var blockRegistryName = String.format("wood/%s/%s", woodVariant, wood);
						setRegistryName(MOD_ID, blockRegistryName);
						setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));

						OreDictionaryHelper.register(this, "slab");
						OreDictionaryHelper.register(this, "slab", "wood");
						OreDictionaryHelper.register(this, "slab", "wood", wood);
				}

				@Override
				public boolean isDouble() {
						return false;
				}

				@Override
				public WoodVariant getWoodVariant() {
						return woodVariant;
				}

				@Override
				public Wood getWood() {
						return wood;
				}

				@Override
				public ItemBlock getItemBlock() {
						return new ItemWoodSlab(this, this, this.doubleSlab);
				}

				@Override
				@SideOnly(Side.CLIENT)
				public void onModelRegister() {
						ModelLoader.setCustomStateMapper(this, new CustomStateMap.Builder().customPath(modelLocation).ignore(BlockWoodSlab.VARIANT).build());

						for (IBlockState state : this.getBlockState().getValidStates()) {
								ModelLoader.setCustomModelResourceLocation(
												Item.getItemFromBlock(this),
												this.getMetaFromState(state), new ModelResourceLocation(modelLocation, "normal"));
						}
				}
		}
}
