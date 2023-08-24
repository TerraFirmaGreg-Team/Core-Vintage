package net.dries007.tfc.common.objects.blocks.rock;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.variant.RockBlockVariant;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockRockSpeleothem extends BlockRock {
    public static PropertyEnum<EnumSize> SIZE = PropertyEnum.create("size", EnumSize.class);

    public BlockRockSpeleothem(RockBlockVariant variant, RockType type) {
        super(variant, type);

        setDefaultState(blockState.getBaseState().withProperty(SIZE, EnumSize.MEDIUM));
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, @Nonnull BlockPos pos) {
        return getBearing(worldIn, pos) > 0;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        var size = EnumSize.values()[Math.max(0, getBearing(worldIn, pos) - 1)];
        worldIn.setBlockState(pos, state.withProperty(SIZE, size));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        int size = state.getValue(SIZE).strength;
        if (getBearing(worldIn, pos) < size + 1) {
            worldIn.playEvent(2001, pos, Block.getStateId(worldIn.getBlockState(pos)));
            dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return 1 + random.nextInt(3);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return TFCBlocks.getRockItem(getType());
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, @Nonnull IBlockState state, EntityPlayer player) {
        return true;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }

    private int getBearing(IBlockAccess world, BlockPos pos) {
        return Math.max(getStrength(world, pos.down()), getStrength(world, pos.up()));
    }

    private int getStrength(IBlockAccess world, BlockPos pos) {
        var state = world.getBlockState(pos);
        if (state.isFullBlock())
            return 3;

        if (state.getPropertyKeys().contains(SIZE))
            return state.getValue(SIZE).strength;

        return 0;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return state.getValue(SIZE).aabb;
    }

    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        return getBoundingBox(blockState, worldIn, pos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos blockPos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean canPlaceTorchOnTop(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        return true;
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, SIZE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(SIZE).ordinal();
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(SIZE, EnumSize.values()[Math.min(EnumSize.values().length - 1, meta)]);
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getActualState(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        var size = EnumSize.values()[Math.max(0, getBearing(worldIn, pos) - 1)];
        if (isCenter(worldIn, pos))
            size = EnumSize.MEDIUM;
        return state.withProperty(SIZE, size);
    }

    private boolean isCenter(IBlockAccess world, BlockPos pos) {
        return isThis(world, pos.down()) && isThis(world, pos.up());
    }

    private boolean isThis(IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() instanceof BlockRockSpeleothem;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(getResourceLocation(),
                        "rocktype=" + getType() + "," +
                                "size=" + state.getValue(SIZE));
            }
        });


        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getResourceLocation(),
                        "rocktype=" + getType() + "," +
                                "size=big"));
    }

    public enum EnumSize implements IStringSerializable {

        SMALL(0, 2),
        MEDIUM(1, 4),
        BIG(2, 8);

        public final int strength;
        public final AxisAlignedBB aabb;

        EnumSize(int strength, int width) {
            this.strength = strength;

            float pad = (((16 - width) / 2f) / 16F);
            aabb = new AxisAlignedBB(pad, 0F, pad, 1F - pad, 1F, 1F - pad);
        }

        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }
}
