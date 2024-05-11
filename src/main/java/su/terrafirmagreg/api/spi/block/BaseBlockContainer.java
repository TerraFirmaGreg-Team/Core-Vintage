package su.terrafirmagreg.api.spi.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
@SuppressWarnings("deprecation")
public abstract class BaseBlockContainer extends BlockContainer implements ISettingsBlock {

    protected final Settings settings;

    public BaseBlockContainer(Settings settings) {
        super(settings.material, settings.material.getMaterialMapColor());
        this.settings = settings;

        setTranslationKey(settings.translationKey);

        // Fix some potential issues with these fields being set prematurely by the super ctor
        this.fullBlock = getDefaultState().isOpaqueCube();
        this.lightOpacity = this.fullBlock ? 255 : 0;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return settings != null && settings.opaque;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return settings.fullCube;
    }

    @Override
    public boolean isCollidable() {
        return settings.collidable;
    }

    @Override
    public boolean getHasItemSubtypes() {
        return settings.hasItemSubtypes;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return settings.renderLayer;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        return isOpaqueCube(state) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    public float getSlipperiness(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity entity) {
        return settings.slipperiness.apply(state, world, pos);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return settings.lightValue.apply(state, world, pos);
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
        return settings.mapColor != null ? settings.mapColor.apply(state, world, pos) : settings.material.getMaterialMapColor();
    }

    @Override
    public Size getSize(ItemStack stack) {
        return settings.size;
    }

    @Override
    public Weight getWeight(ItemStack stack) {
        return settings.weight;
    }

    @Override
    public boolean canStack(ItemStack stack) {
        return settings.canStack;
    }
}
