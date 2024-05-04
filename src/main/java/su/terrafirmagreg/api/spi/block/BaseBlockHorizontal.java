package su.terrafirmagreg.api.spi.block;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
@SuppressWarnings("deprecation")
public abstract class BaseBlockHorizontal extends BlockHorizontal implements ISettingsBlock {

    protected final Settings settings;

    public BaseBlockHorizontal(Settings settings) {
        super(settings.material, settings.material.getMaterialMapColor());
        this.settings = settings;

        setResistance(settings.resistance);
        setHardness(settings.hardness);
        setSoundType(settings.soundType);
        setTranslationKey(settings.translationKey);
        setCreativeTab(settings.tab);

        setDefaultState(getBlockState().getBaseState()
                .withProperty(FACING, EnumFacing.NORTH));

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

    public boolean getHasItemSubtypes() {
        return settings.hasItemSubtypes;
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

    public Item asItem() {
        return Item.getItemFromBlock(this);
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

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirror) {
        return state.withRotation(mirror.toRotation(state.getValue(FACING)));
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }
}
