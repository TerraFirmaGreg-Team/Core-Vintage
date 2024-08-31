package su.terrafirmagreg.api.base.block;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
@SuppressWarnings("deprecation")
public abstract class BaseBlock extends Block implements IBlockSettings {

    protected final Settings settings;

    public BaseBlock(Settings settings) {
        super(settings.getMaterial(), settings.getMapColor());

        this.settings = settings;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return this.settings == null || this.settings.isOpaque();
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return this.settings.isFullCube();
    }

    @Override
    public boolean isCollidable() {
        return this.settings.isCollidable();
    }

    @Override
    public SoundType getSoundType() {
        return this.settings.getSoundType();
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        return isOpaqueCube(state) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return this.settings.getRenderLayer();
    }

    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return this.settings.getHardness().apply(blockState, worldIn, pos);
    }

    @Override
    public float getExplosionResistance(Entity exploder) {
        return this.settings.getResistance() / 5.0F;
    }

    @Override
    public boolean isAir(IBlockState state, IBlockAccess world, BlockPos pos) {
        return this.settings.isAir();
    }

    @Override
    public boolean causesSuffocation(IBlockState state) {
        return this.settings.getIsSuffocating().test(state);
    }

    @Override
    public String getTranslationKey() {
        return this.settings.getTranslationKey() == null ? super.getTranslationKey() : "tile." + this.settings.getTranslationKey();
    }

    @Override
    public boolean getUseNeighborBrightness(IBlockState state) {
        return getSettings().isUseNeighborBrightness();
    }

    @Override
    public float getSlipperiness(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity entity) {
        return this.settings.getSlipperiness().apply(state, world, pos);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return this.settings.getLightValue().apply(state, world, pos);
    }

    @Override
    public Item asItem() {
        return Item.getItemFromBlock(this);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return this.settings.isCollidable() ? super.getCollisionBoundingBox(blockState, worldIn, pos) : NULL_AABB;
    }

    @Override
    public boolean getTickRandomly() {
        return this.settings.isTicksRandomly();
    }

    @Override
    public String getHarvestTool(IBlockState state) {
        return this.settings.getHarvestTool();
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
        return this.settings.getHarvestLevel();
    }

    @Override
    public boolean getHasItemSubtypes() {
        return this.settings.isHasItemSubtypes();
    }

    @Override
    public Size getSize(ItemStack stack) {
        return this.settings.getSize();
    }

    @Override
    public Weight getWeight(ItemStack stack) {
        return this.settings.getWeight();
    }

    @Override
    public boolean canStack(ItemStack stack) {
        return this.settings.isCanStack();
    }

}
