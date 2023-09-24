package net.dries007.tfc.module.rock.common.blocks;

import net.dries007.tfc.module.rock.api.type.RockType;
import net.dries007.tfc.module.rock.api.variant.block.IRockBlock;
import net.dries007.tfc.module.rock.api.variant.block.RockBlockVariant;
import net.dries007.tfc.module.rock.common.RockStorage;
import net.dries007.tfc.module.rock.common.blocks.itemblock.ItemRockSlab;
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

import static net.dries007.tfc.module.rock.api.variant.block.RockBlockVariants.*;

public abstract class BlockRockSlab extends BlockSlab implements IRockBlock {
    public static final PropertyEnum<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);
    private final RockBlockVariant variant;
    private final RockType type;
    protected Block block;
    protected Half halfSlab;
    protected Double doubleSlab;

    private BlockRockSlab(RockBlockVariant variant, RockType type) {
        super(Material.ROCK);

        this.variant = variant;
        this.type = type;
        this.block = getFullBlockFromSlab(variant, type);
        this.useNeighborBrightness = true;

        var state = blockState.getBaseState();
        if (!isDouble()) state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);
        setDefaultState(state.withProperty(VARIANT, Variant.DEFAULT));
        setSoundType(SoundType.STONE);
        setHardness(getFinalHardness());
        setHarvestLevel("pickaxe", 0);
    }

    protected static Block getFullBlockFromSlab(RockBlockVariant variant, RockType type) {
        return switch (variant.toString()) {
            case "slab/raw", "slab_double/raw" -> RockStorage.getRockBlock(RAW, type);
            case "slab/cobble", "slab_double/cobble" -> RockStorage.getRockBlock(COBBLE, type);
            case "slab/smooth", "slab_double/smooth" -> RockStorage.getRockBlock(SMOOTH, type);
            case "slab/bricks", "slab_double/bricks" -> RockStorage.getRockBlock(BRICKS, type);
            default ->
                    throw new RuntimeException(String.format("Double slab from slab not founded: %s, %s", variant, type));
        };
    }

    protected static Block getDoubleSlabFromSlab(RockBlockVariant variant, RockType type) {
        return switch (variant.toString()) {
            case "slab/raw" -> RockStorage.getRockBlock(SLAB_DOUBLE_RAW, type);
            case "slab/cobble" -> RockStorage.getRockBlock(SLAB_DOUBLE_COBBLE, type);
            case "slab/smooth" -> RockStorage.getRockBlock(SLAB_DOUBLE_SMOOTH, type);
            case "slab/bricks" -> RockStorage.getRockBlock(SLAB_DOUBLE_BRICK, type);
            default ->
                    throw new RuntimeException(String.format("Double slab from slab not founded: %s, %s", variant, type));
        };
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
        return this.isDouble() ? null : new ItemRockSlab(this.halfSlab, this.halfSlab, this.halfSlab.doubleSlab);
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
        return block.getBlockHardness(blockState, worldIn, pos);
    }

    @Override
    @Nonnull
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
        return Item.getItemFromBlock(halfSlab);
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getExplosionResistance(@Nonnull Entity exploder) {
        return block.getExplosionResistance(exploder);
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
        return block.getSoundType();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TextComponentTranslation("rockcategory.name").getFormattedText() + ": " + getCategory().getLocalizedName());
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
        public Double(RockBlockVariant variant, RockType type) {
            super(variant, type);
        }

        @Override
        public boolean isDouble() {
            return true;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void onModelRegister() {
            ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
                @Nonnull
                protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                    return new ModelResourceLocation(getResourceLocation(),
                            "rocktype=" + getType());
                }
            });
        }
    }

    public static class Half extends BlockRockSlab {
        public Half(RockBlockVariant variant, RockType type) {
            super(variant, type);

            this.doubleSlab = (Double) getDoubleSlabFromSlab(variant, type);
            this.doubleSlab.halfSlab = this;
            this.halfSlab = this;

            OreDictionaryHelper.register(this, variant.toString());
            OreDictionaryHelper.register(this, variant.toString(), type.toString());
        }

        @Override
        public boolean isDouble() {
            return false;
        }


        @SideOnly(Side.CLIENT)
        @Override
        public void onModelRegister() {
            ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
                @Nonnull
                protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                    return new ModelResourceLocation(getResourceLocation(),
                            "half=" + state.getValue(HALF) + "," +
                                    "rocktype=" + getType());
                }
            });

            ModelLoader.setCustomModelResourceLocation(
                    Item.getItemFromBlock(this), 0,
                    new ModelResourceLocation(getResourceLocation(),
                            "half=bottom," +
                                    "rocktype=" + getType()));
        }
    }
}
