package su.terrafirmagreg.api.spi.block;

import su.terrafirmagreg.api.model.CustomStateMap;
import su.terrafirmagreg.api.spi.itemblock.BaseItemSlab;
import su.terrafirmagreg.api.util.ModelUtils;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Random;

@Getter
@SuppressWarnings("deprecation")
public abstract class BaseBlockSlab extends BlockSlab implements ISettingsBlock, ICustomStateBlock {

    public static final PropertyEnum<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);

    protected final Settings settings;

    public BaseBlockSlab(Settings settings) {
        super(settings.material);

        this.settings = settings;
        this.useNeighborBrightness = true;

        var state = getBlockState().getBaseState();
        if (!isDouble()) state = state.withProperty(BlockSlab.HALF, EnumBlockHalf.BOTTOM);
        setDefaultState(state.withProperty(VARIANT, Variant.DEFAULT));
    }

    @Override
    public @Nullable BaseItemSlab getItemBlock() {
        return this.isDouble() ? null : new BaseItemSlab(this.getHalfSlab(), this.getDoubleSlab());
    }

    public abstract boolean isDouble();

    public abstract BaseBlockSlab getHalfSlab();

    public abstract BaseBlockSlab getDoubleSlab();

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(getHalfSlab());
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(getHalfSlab());
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

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, Variant.DEFAULT);

        if (!this.isDouble()) {
            iblockstate = iblockstate.withProperty(BlockSlab.HALF, (meta & 8) == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
        }

        return iblockstate;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;

        if (!this.isDouble() && state.getValue(BlockSlab.HALF) == EnumBlockHalf.TOP) {
            i |= 8;
        }

        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return this.isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, BlockSlab.HALF, VARIANT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onStateRegister() {
        ModelUtils.registerStateMapper(this, new CustomStateMap.Builder().ignore(VARIANT).build());
    }

    @Override
    public final boolean isFluidloggable(IBlockState state, World world, BlockPos pos) {
        return isWaterloggable(state, world, pos);
    }

    @Override
    public final boolean isFluidValid(IBlockState state, World world, BlockPos pos, Fluid fluid) {
        return isWaterloggable(state, world, pos) && fluid == FluidRegistry.WATER;
    }

    @Override
    public final boolean canFluidFlow(IBlockAccess world, BlockPos pos, IBlockState state, EnumFacing side) {
        return isWaterloggable(state, world, pos) && canWaterFlow(world, pos, state, side);
    }

    /** Whether this block can be water-logged or not. */
    public boolean isWaterloggable(IBlockState state, IBlockAccess world, BlockPos pos) {
        return this.isDouble();
    }

    public enum Variant implements IStringSerializable {
        DEFAULT;

        @Override
        public String getName() {
            return "default";
        }
    }

}
