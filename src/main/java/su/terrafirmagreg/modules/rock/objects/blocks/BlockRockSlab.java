package su.terrafirmagreg.modules.rock.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariants;
import su.terrafirmagreg.modules.rock.init.BlocksRock;
import su.terrafirmagreg.modules.rock.objects.itemblocks.ItemRockSlab;

import java.util.List;
import java.util.Random;

@Getter
public abstract class BlockRockSlab extends BlockSlab implements IRockBlock {

    public static final PropertyEnum<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);
    private final RockBlockVariant blockVariant;
    private final RockType type;
    protected Block block;
    protected Half halfSlab;
    protected Double doubleSlab;

    private BlockRockSlab(RockBlockVariant blockVariant, RockType type) {
        super(Material.ROCK);

        this.blockVariant = blockVariant;
        this.type = type;
        this.block = getFullBlockFromSlab(blockVariant, type);
        this.useNeighborBrightness = true;

        var state = blockState.getBaseState();
        if (!isDouble()) state = state.withProperty(BlockSlab.HALF, EnumBlockHalf.BOTTOM);
        setDefaultState(state.withProperty(VARIANT, Variant.DEFAULT));
        setSoundType(SoundType.STONE);
        setHardness(getFinalHardness());
        setHarvestLevel("pickaxe", 0);
    }

    protected static Block getFullBlockFromSlab(RockBlockVariant variant, RockType type) {
        return switch (variant.toString()) {
            case "slab/raw", "slab_double/raw" -> BlocksRock.getBlock(RockBlockVariants.RAW, type);
            case "slab/cobble", "slab_double/cobble" -> BlocksRock.getBlock(RockBlockVariants.COBBLE, type);
            case "slab/smooth", "slab_double/smooth" -> BlocksRock.getBlock(RockBlockVariants.SMOOTH, type);
            case "slab/bricks", "slab_double/bricks" -> BlocksRock.getBlock(RockBlockVariants.BRICKS, type);
            default -> throw new RuntimeException(String.format("Double slab from slab not founded: %s, %s", variant, type));
        };
    }

    protected static Block getDoubleSlabFromSlab(RockBlockVariant variant, RockType type) {
        return switch (variant.toString()) {
            case "slab/raw" -> BlocksRock.getBlock(RockBlockVariants.SLAB_DOUBLE_RAW, type);
            case "slab/cobble" -> BlocksRock.getBlock(RockBlockVariants.SLAB_DOUBLE_COBBLE, type);
            case "slab/smooth" -> BlocksRock.getBlock(RockBlockVariants.SLAB_DOUBLE_SMOOTH, type);
            case "slab/bricks" -> BlocksRock.getBlock(RockBlockVariants.SLAB_DOUBLE_BRICK, type);
            default -> throw new RuntimeException(String.format("Double slab from slab not founded: %s, %s", variant, type));
        };
    }

    @Override
    public ItemBlock getItemBlock() {
        return this.isDouble() ? null : new ItemRockSlab(this.halfSlab, this.halfSlab, this.halfSlab.doubleSlab);
    }

    @Override
    @NotNull
    public String getTranslationKey(int meta) {
        return super.getTranslationKey();
    }

    @Override
    @NotNull
    public IProperty<?> getVariantProperty() {
        return VARIANT; // why is this not null-tolerable ...
    }

    @Override
    @NotNull
    public Comparable<?> getTypeForItem(@NotNull ItemStack stack) {
        return Variant.DEFAULT;
    }

    @SuppressWarnings("deprecation")
    @Override
    @NotNull
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, Variant.DEFAULT);

        if (!this.isDouble()) {
            iblockstate = iblockstate.withProperty(BlockSlab.HALF,
                    (meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
        }

        return iblockstate;
    }

    @Override
    public int getMetaFromState(@NotNull IBlockState state) {
        int i = 0;

        if (!this.isDouble() && state.getValue(BlockSlab.HALF) == EnumBlockHalf.TOP) {
            i |= 8;
        }

        return i;
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getBlockHardness(@NotNull IBlockState blockState, @NotNull World worldIn, @NotNull BlockPos pos) {
        return block.getBlockHardness(blockState, worldIn, pos);
    }

    @Override
    @NotNull
    public Item getItemDropped(@NotNull IBlockState state, @NotNull Random rand, int fortune) {
        return Item.getItemFromBlock(halfSlab);
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getExplosionResistance(@NotNull Entity exploder) {
        return block.getExplosionResistance(exploder);
    }

    @SuppressWarnings("deprecation")
    @Override
    @NotNull
    public ItemStack getItem(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
        return new ItemStack(halfSlab);
    }

    @Override
    @NotNull
    protected BlockStateContainer createBlockState() {
        return this.isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, BlockSlab.HALF,
                VARIANT);
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public SoundType getSoundType() {
        return block.getSoundType();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(
                new TextComponentTranslation("rockcategory.name")
                        .getFormattedText() + ": " + getType().getRockCategory().getLocalizedName());
    }

    public enum Variant implements IStringSerializable {
        DEFAULT;

        @Override
        @NotNull
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
    }

    public static class Half extends BlockRockSlab {

        public Half(RockBlockVariant variant, RockType type) {
            super(variant, type);

            this.doubleSlab = (Double) getDoubleSlabFromSlab(variant, type);
            this.doubleSlab.halfSlab = this;
            this.halfSlab = this;

            //OreDictionaryHelper.register(this, variant.toString());
            //OreDictionaryHelper.register(this, variant.toString(), type.toString());
        }

        @Override
        public boolean isDouble() {
            return false;
        }
    }
}
