package tfctech.objects.blocks.devices;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import mcp.MethodsReturnNonnullByDefault;


import su.terrafirmagreg.api.util.TileUtils;


import tfctech.objects.tileentities.TEWireDrawBench;

import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static net.minecraft.util.EnumFacing.NORTH;

@MethodsReturnNonnullByDefault
public class BlockWireDrawBench extends BlockHorizontal {

    public static final PropertyBool UPPER = PropertyBool.create("upper"); //true if this is the upper half
    private static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0.0625D, 0, 0.0D, 0.9375D, 1D, 0.9375D);
    private static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0.0625D, 0, 0.0625D, 0.9375D, 1D, 1D);
    private static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0.0625D, 0, 0.0625D, 1D, 1D, 0.9375D);
    private static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(0.0D, 0, 0.0625D, 0.9375D, 1D, 0.9375D);

    public BlockWireDrawBench() {
        super(Material.IRON);
        setDefaultState(blockState.getBaseState().withProperty(FACING, NORTH).withProperty(UPPER, false));
        setHardness(2.0f);
        setHarvestLevel("pickaxe", 0);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(FACING, EnumFacing.byHorizontalIndex(meta))
                .withProperty(UPPER, meta > 3);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex() + (state.getValue(UPPER) ? 4 : 0);
    }

    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasCustomBreakingProgress(IBlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        if (!state.getValue(UPPER)) {
            switch (state.getValue(FACING)) {
                case NORTH:
                    return AABB_NORTH;
                case SOUTH:
                    return AABB_SOUTH;
                case EAST:
                    return AABB_EAST;
                case WEST:
                    return AABB_WEST;
            }
        } else {
            switch (state.getValue(FACING)) {
                case NORTH:
                    return AABB_SOUTH;
                case SOUTH:
                    return AABB_NORTH;
                case EAST:
                    return AABB_WEST;
                case WEST:
                    return AABB_EAST;
            }
        }
        return FULL_BLOCK_AABB;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        EnumFacing enumfacing = state.getValue(FACING);

        if (!state.getValue(UPPER)) {
            if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() != this) {
                worldIn.setBlockToAir(pos);
            }
        } else if (worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getBlock() != this) {
            if (!worldIn.isRemote) {
                spawnAsEntity(worldIn, pos, new ItemStack(this));
            }
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(UPPER) ? Item.getItemFromBlock(this) : Items.AIR;
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        if (state.getValue(UPPER)) {
            super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX,
                                    float hitY, float hitZ) {
        BlockPos TEPos = pos;
        if (!state.getValue(UPPER)) {
            TEPos = pos.offset(state.getValue(FACING));
        }
        TEWireDrawBench te = TileUtils.getTile(world, TEPos, TEWireDrawBench.class);
        if (te != null && hand == EnumHand.MAIN_HAND) {
            ItemStack stack = player.getHeldItem(hand);
            if (!stack.isEmpty()) {
                if (te.isItemValid(0, stack) && !te.insertDrawPlate(stack, true).equals(stack)) {
                    if (!world.isRemote) {
                        player.setHeldItem(hand, te.insertDrawPlate(stack, false));
                    }
                    return true;
                } else if (te.isItemValid(1, stack) && !te.insertWire(stack, true).equals(stack)) {
                    if (!world.isRemote) {
                        player.setHeldItem(hand, te.insertWire(stack, false));
                    }
                    return true;
                }
                return false;
            } else {
                if (player.isSneaking()) {
                    for (int i = 1; i >= 0; i--) {
                        if (te.extractItem(i, true) != ItemStack.EMPTY) {
                            if (!world.isRemote) {
                                player.setHeldItem(hand, te.extractItem(i, false));
                            }
                            return true;
                        }
                    }
                } else {
                    return te.startWork(player);
                }
            }
        }
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, UPPER);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return state.getValue(UPPER);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return state.getValue(UPPER) ? new TEWireDrawBench() : null;
    }

    @Override
    public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate,
                                     EntityLivingBase entity, int numberOfParticles) {
        return true;
    }

    @Override
    public boolean addRunningEffects(IBlockState state, World world, BlockPos pos, Entity entity) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
        return true;
    }
}
