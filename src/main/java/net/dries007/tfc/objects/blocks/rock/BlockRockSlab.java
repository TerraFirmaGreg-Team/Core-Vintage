package net.dries007.tfc.objects.blocks.rock;

import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types2.rock.RockBlockType;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.types2.rock.util.IRockTypeBlock;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.rock.ItemRockSlab;
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
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types2.rock.RockBlockType.*;

public abstract class BlockRockSlab extends BlockSlab implements IRockTypeBlock {
	public static final PropertyEnum<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);

	public final Block modelBlock;
	protected Half halfSlab;

	private BlockRockSlab(RockBlockType rockBlockType, RockVariant rockVariant, RockType rockType) {
		super(Material.ROCK);

		IBlockState state = blockState.getBaseState();

		if (!isDouble())
			state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);

		this.modelBlock = TFCStorage.getRockBlock(ORDINARY, rockVariant, rockType);
		useNeighborBrightness = true;

		setLightOpacity(255);
		setDefaultState(state.withProperty(VARIANT, Variant.DEFAULT));
		setCreativeTab(CreativeTabsTFC.ROCK_STUFFS);
		setSoundType(SoundType.STONE);
	}

	@Override
	@Nonnull
	public String getTranslationKey(int meta) {
		return super.getTranslationKey();
	}

	@Override
	@Nonnull
	public IProperty<?> getVariantProperty() {
		return VARIANT; // why is this not null-tolerable ...
	}

	@Override
	@Nonnull
	public Comparable<?> getTypeForItem(@Nonnull ItemStack stack) {
		return Variant.DEFAULT;
	}

	@SuppressWarnings("deprecation")
	@Override
	@Nonnull
	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, Variant.DEFAULT);

		if (!this.isDouble()) {
			iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
		}

		return iblockstate;
	}

	@Override
	public int getMetaFromState(@Nonnull IBlockState state) {
		int i = 0;

		if (!this.isDouble() && state.getValue(HALF) == EnumBlockHalf.TOP) {
			i |= 8;
		}

		return i;
	}

	@SuppressWarnings("deprecation")
	@Override
	public float getBlockHardness(@Nonnull IBlockState blockState, @Nonnull World worldIn, @Nonnull BlockPos pos) {
		return modelBlock.getBlockHardness(blockState, worldIn, pos);
	}

	@Override
	@Nonnull
	public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
		return Item.getItemFromBlock(halfSlab);
	}

	@SuppressWarnings("deprecation")
	@Override
	public float getExplosionResistance(@Nonnull Entity exploder) {
		return modelBlock.getExplosionResistance(exploder);
	}

	@SuppressWarnings("deprecation")
	@Override
	@Nonnull
	public ItemStack getItem(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
		return new ItemStack(halfSlab);
	}


	@Override
	@Nonnull
	protected BlockStateContainer createBlockState() {
		return this.isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, HALF, VARIANT);
	}

	@SuppressWarnings("deprecation")
	@Nonnull
	@Override
	public SoundType getSoundType() {
		return modelBlock.getSoundType();
	}


	public enum Variant implements IStringSerializable {
		DEFAULT;

		@Override
		@Nonnull
		public String getName() {
			return "default";
		}
	}

	public static class Double extends BlockRockSlab {
		private final RockVariant rockVariant;
		private final RockType rockType;
		private final ResourceLocation modelLocation;

		public Double(RockBlockType rockBlockType, RockVariant rockVariant, RockType rockType) {
			super(rockBlockType, rockVariant, rockType);

			TFCStorage.addRockBlock(SLAB_DOUBLE, rockVariant, rockType, this);

			this.rockVariant = rockVariant;
			this.rockType = rockType;
			this.modelLocation = new ResourceLocation(MOD_ID, "rock/" + rockBlockType + "/" + rockVariant);

			var blockRegistryName = String.format("rock/%s/%s/%s", rockBlockType, rockVariant, rockType);
			this.setRegistryName(MOD_ID, blockRegistryName);
			this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
		}

		@Override
		public boolean isDouble() {
			return true;
		}

		@Override
		public RockVariant getRockVariant() {
			return rockVariant;
		}

		@Override
		public RockType getRockType() {
			return rockType;
		}

		@Override
		public ItemBlock getItemBlock() {
			return null;
		}


		@Override
		@SideOnly(Side.CLIENT)
		public void onModelRegister() {
			ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
				@Nonnull
				protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
					return new ModelResourceLocation(modelLocation,
							"rocktype=" + rockType.getName());
				}
			});
		}
	}

	public static class Half extends BlockRockSlab {
		public final Double doubleSlab;
		private final RockType rockType;
		private final ResourceLocation modelLocation;
		private final RockVariant rockVariant;

		public Half(RockBlockType rockBlockType, RockVariant rockVariant, RockType rockType) {
			super(rockBlockType, rockVariant, rockType);

			TFCStorage.addRockBlock(SLAB, rockVariant, rockType, this);

			doubleSlab = (Double) TFCStorage.getRockBlock(SLAB_DOUBLE, rockVariant, rockType);
			doubleSlab.halfSlab = this;
			halfSlab = this;

			this.rockVariant = rockVariant;
			this.rockType = rockType;
			this.modelLocation = new ResourceLocation(MOD_ID, "rock/" + rockBlockType + "/" + rockVariant);

			var blockRegistryName = String.format("rock/%s/%s/%s", rockBlockType, rockVariant, rockType);
			this.setRegistryName(MOD_ID, blockRegistryName);
			this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));

			this.setHardness(getFinalHardness());
			this.setHarvestLevel("pickaxe", 0);

			OreDictionaryHelper.register(this, "slab", "slab_" + rockType);

			//OreDictionaryModule.register(this, rockBlockType.getName(), rockVariant.getName(), rockVariant.getName() + WordUtils.capitalize(rockType.getName()));
		}

		@Override
		public boolean isDouble() {
			return false;
		}

		@Override
		public RockVariant getRockVariant() {
			return rockVariant;
		}

		@Override
		public RockType getRockType() {
			return rockType;
		}

		@Override
		public ItemBlock getItemBlock() {
			return new ItemRockSlab(this, this, this.doubleSlab);
		}

//		@Override
//		public boolean removedByPlayer(@Nonnull IBlockState state, World world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player, boolean willHarvest) {
//			if (!world.isRemote) {
//				ItemStack heldItemStack = player.getHeldItem(EnumHand.MAIN_HAND);
//				Item heldItem = heldItemStack.getItem();
//
//				// Проверяем, можно ли игроку собрать блок с использованием текущего инструмента
//				if (player.canHarvestBlock(state)) {
//					// Проверяем, является ли удерживаемый предмет инструментом с классом инструмента pickaxe и кроме инструмента HARD_HAMMER
//					if ((heldItem.getToolClasses(heldItemStack).contains("pickaxe")) && !(heldItem == HARD_HAMMER.get())) {
//						switch (rockVariant) {
//							case RAW:
//							case SMOOTH:
//							case COBBLE:
//								//Block.spawnAsEntity(world, pos, new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get(LOOSE.getName() + "/" + rockType.getName()), new Random().nextInt(2) + 2));
//								break;
//							case BRICK:
//								//Block.spawnAsEntity(world, pos, new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get(LOOSE.getName() + "/" + rockType.getName()), new Random().nextInt(2) + 2));
//								Block.spawnAsEntity(world, pos, new ItemStack(Items.CLAY_BALL, new Random().nextInt(2))); //TODO кусочек цемента?
//								break;
//						}
//					} else if (heldItem == HARD_HAMMER.get()) {
//						switch (rockVariant) {
//							case RAW:
//							case SMOOTH:
//								Block.spawnAsEntity(world, pos, new ItemStack(getBlockRockMap(ORD, COBBLE, rockType), 1));
//								break;
//							case COBBLE:
//								//Block.spawnAsEntity(world, pos, new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get(LOOSE.getName() + "/" + rockType.getName()), new Random().nextInt(2) + 3));
//								break;
//							case BRICK:
//								//Block.spawnAsEntity(world, pos, new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get("brick/" + rockType.getName()), new Random().nextInt(2) + 3));
//								Block.spawnAsEntity(world, pos, new ItemStack(Items.CLAY_BALL, new Random().nextInt(2))); //TODO кусочек цемента?
//								break;
//						}
//					}
//				}
//			}
//			return super.removedByPlayer(state, world, pos, player, willHarvest);
//		}

		@Override
		public void getDrops(@Nonnull NonNullList<ItemStack> drops, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull IBlockState state, int fortune) {
		}

		@SideOnly(Side.CLIENT)
		@Override
		public void onModelRegister() {
			ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
				@Nonnull
				protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
					return new ModelResourceLocation(modelLocation,
							"half=" + state.getValue(HALF) + "," +
									"rocktype=" + rockType.getName());
				}
			});

			for (IBlockState state : this.getBlockState().getValidStates()) {
				ModelLoader.setCustomModelResourceLocation(
						Item.getItemFromBlock(this),
						this.getMetaFromState(state),
						new ModelResourceLocation(modelLocation,
								"half=bottom," +
										"rocktype=" + rockType.getName()));
			}
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
			super.addInformation(stack, worldIn, tooltip, flagIn);

			tooltip.add(new TextComponentTranslation("stonecategory.name").getFormattedText() + ": " + getRockType().getRockCategory().getLocalizedName());
		}
	}
}
