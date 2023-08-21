package net.dries007.tfc.common.objects.blocks.rock;

import net.dries007.tfc.api.types.rock.IRockBlock;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.variant.RockBlockVariant;
import net.dries007.tfc.api.types.rock.variant.RockBlockVariants;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.common.objects.items.rock.ItemRockSlab;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public abstract class BlockRockSlab extends BlockSlab implements IRockBlock {
    public static final PropertyEnum<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);

    public Block modelBlock;
    protected Half halfSlab;

    private BlockRockSlab(RockBlockVariant variant, RockType type) {
        super(Material.ROCK);

        IBlockState state = blockState.getBaseState();

        if (!isDouble()) state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);
        
        useNeighborBrightness = true;

        setLightOpacity(255);
        setDefaultState(state.withProperty(VARIANT, Variant.DEFAULT));
        setCreativeTab(CreativeTabsTFC.ROCK);
        setSoundType(SoundType.STONE);
    }

    protected static Block getFullBlockFromSlab(RockBlockVariant rockBlockVariant, RockType rockType) {
        if (rockBlockVariant == RockBlockVariants.SLAB_RAW || rockBlockVariant == RockBlockVariants.SLAB_DOUBLE_RAW) {
            return TFCBlocks.getRockBlock(RockBlockVariants.RAW, rockType);
        } else if (rockBlockVariant == RockBlockVariants.SLAB_COBBLE || rockBlockVariant == RockBlockVariants.SLAB_DOUBLE_COBBLE) {
            return TFCBlocks.getRockBlock(RockBlockVariants.COBBLE, rockType);
        } else if (rockBlockVariant == RockBlockVariants.SLAB_SMOOTH || rockBlockVariant == RockBlockVariants.SLAB_DOUBLE_SMOOTH) {
            return TFCBlocks.getRockBlock(RockBlockVariants.SMOOTH, rockType);
        } else if (rockBlockVariant == RockBlockVariants.SLAB_BRICK || rockBlockVariant == RockBlockVariants.SLAB_DOUBLE_BRICK) {
            return TFCBlocks.getRockBlock(RockBlockVariants.BRICK, rockType);
        }

        throw new RuntimeException(String.format("Full block from slab not founded: %s, %s", rockBlockVariant, rockType));
    }

    protected static Block getDoubleSlabFromSlab(RockBlockVariant rockBlockVariant, RockType rockType) {
        if (rockBlockVariant == RockBlockVariants.SLAB_RAW) {
            return TFCBlocks.getRockBlock(RockBlockVariants.SLAB_DOUBLE_RAW, rockType);
        } else if (rockBlockVariant == RockBlockVariants.SLAB_COBBLE) {
            return TFCBlocks.getRockBlock(RockBlockVariants.SLAB_DOUBLE_COBBLE, rockType);
        } else if (rockBlockVariant == RockBlockVariants.SLAB_SMOOTH) {
            return TFCBlocks.getRockBlock(RockBlockVariants.SLAB_DOUBLE_SMOOTH, rockType);
        } else if (rockBlockVariant == RockBlockVariants.SLAB_BRICK) {
            return TFCBlocks.getRockBlock(RockBlockVariants.SLAB_DOUBLE_BRICK, rockType);
        }

        throw new RuntimeException(String.format("Double slab from slab not founded: %s, %s", rockBlockVariant, rockType));
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
        private final RockBlockVariant rockBlockVariant;
        private final RockType rockType;

        public Double(RockBlockVariant variant, RockType type) {
            super(variant, type);

            this.rockBlockVariant = variant;
            this.rockType = type;
            this.modelBlock = getFullBlockFromSlab(variant, type);

            setRegistryName(getRegistryLocation());
            setTranslationKey(getTranslationName());
        }

        @Override
        public boolean isDouble() {
            return true;
        }

        @Nonnull
        @Override
        public RockBlockVariant getBlockVariant() {
            return rockBlockVariant;
        }

        @Nonnull
        @Override
        public RockType getType() {
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
                    return new ModelResourceLocation(getResourceLocation(),
                            "rocktype=" + rockType.toString());
                }
            });
        }
    }

    public static class Half extends BlockRockSlab {
        public final Double doubleSlab;
        private final RockBlockVariant variant;
        private final RockType type;

        public Half(RockBlockVariant variant, RockType type) {
            super(variant, type);

            doubleSlab = (Double) getDoubleSlabFromSlab(variant, type);
            doubleSlab.halfSlab = this;
            halfSlab = this;

            this.variant = variant;
            this.type = type;

            setRegistryName(getRegistryLocation());
            setTranslationKey(getTranslationName());

            setHardness(getFinalHardness());
            setHarvestLevel("pickaxe", 0);

            OreDictionaryHelper.register(this, variant.toString(), type.toString());
        }

        @Override
        public boolean isDouble() {
            return false;
        }

        @Nonnull
        @Override
        public RockBlockVariant getBlockVariant() {
            return variant;
        }

        @Nonnull
        @Override
        public RockType getType() {
            return type;
        }

        @Override
        public ItemBlock getItemBlock() {
            return new ItemRockSlab(this, this, this.doubleSlab);
        }


        @SideOnly(Side.CLIENT)
        @Override
        public void onModelRegister() {
            ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
                @Nonnull
                protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                    return new ModelResourceLocation(getResourceLocation(),
                            "half=" + state.getValue(HALF) + "," +
                                    "rocktype=" + type.toString());
                }
            });

            for (IBlockState state : getBlockState().getValidStates()) {
                ModelLoader.setCustomModelResourceLocation(
                        Item.getItemFromBlock(this),
                        getMetaFromState(state),
                        new ModelResourceLocation(getResourceLocation(),
                                "half=bottom," +
                                        "rocktype=" + type.toString()));
            }
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
            super.addInformation(stack, worldIn, tooltip, flagIn);

            tooltip.add(new TextComponentTranslation("rockcategory.name").getFormattedText() + ": " + type.getCategory().getLocalizedName());
        }
    }
}
