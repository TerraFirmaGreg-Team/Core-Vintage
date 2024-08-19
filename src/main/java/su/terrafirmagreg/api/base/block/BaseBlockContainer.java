package su.terrafirmagreg.api.base.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;


import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
@SuppressWarnings("deprecation")
public abstract class BaseBlockContainer extends BlockContainer implements IBlockSettings {

    protected final Settings settings;

    public BaseBlockContainer(Settings settings) {
        super(settings.material, settings.material.getMaterialMapColor());
        this.settings = settings;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return getSettings() != null && getSettings().isOpaque();
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return getSettings().isFullCube();
    }

    @Override
    public boolean isCollidable() {
        return getSettings().isCollidable();
    }

    @Override
    public SoundType getSoundType() {
        return getSettings().getSoundType();
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        return isOpaqueCube(state) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return getSettings().getRenderLayer();
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
        return getSettings().getMapColor() != null ? getSettings().getMapColor().apply(state, world, pos) : getSettings().getMaterial().getMaterialMapColor();
    }

    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return getSettings().getHardness().apply(blockState, worldIn, pos);
    }

    @Override
    public float getExplosionResistance(Entity exploder) {
        return getSettings().getResistance() / 5.0F;
    }

    @Override
    public String getTranslationKey() {
        return getSettings().getTranslationKey() == null ? super.getTranslationKey() : "tile." + getSettings().getTranslationKey();
    }

    @Override
    public float getSlipperiness(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity entity) {
        return getSettings().getSlipperiness().apply(state, world, pos);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return getSettings().getLightValue().apply(state, world, pos);
    }

    @Override
    public Item asItem() {
        return Item.getItemFromBlock(this);
    }

    @Override
    public boolean getHasItemSubtypes() {
        return getSettings().isHasItemSubtypes();
    }

    @Override
    public Size getSize(ItemStack stack) {
        return getSettings().getSize();
    }

    @Override
    public Weight getWeight(ItemStack stack) {
        return getSettings().getWeight();
    }

    @Override
    public boolean canStack(ItemStack stack) {
        return getSettings().isCanStack();
    }
}
