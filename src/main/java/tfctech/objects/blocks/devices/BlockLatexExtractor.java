package tfctech.objects.blocks.devices;

import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;
import tfctech.client.TechSounds;
import tfctech.objects.tileentities.TELatexExtractor;

import org.jetbrains.annotations.Nullable;

import static net.minecraft.block.BlockHorizontal.FACING;
import static net.minecraft.util.EnumFacing.NORTH;

@MethodsReturnNonnullByDefault
public class BlockLatexExtractor extends Block {

    public static final PropertyBool BASE = PropertyBool.create("base"); //from TE
    public static final PropertyBool POT = PropertyBool.create("pot"); //from TE
    public static final PropertyInteger CUT = PropertyInteger.create("cut", 0, 2); //from TE

    private static final AxisAlignedBB AABB_N = new AxisAlignedBB(0.1875D, 0.125D, 0.3125D, 0.8125D, 0.875D, 1.0D);
    private static final AxisAlignedBB AABB_S = new AxisAlignedBB(0.1875D, 0.125D, 0.0D, 0.8125D, 0.875D, 0.6875D);
    private static final AxisAlignedBB AABB_E = new AxisAlignedBB(0.0D, 0.125D, 0.1875D, 0.6875D, 0.875D, 0.8125D);
    private static final AxisAlignedBB AABB_W = new AxisAlignedBB(0.3125D, 0.125D, 0.1875D, 1.0D, 0.875D, 0.8125D);

    public BlockLatexExtractor() {
        super(Material.IRON);
        setDefaultState(blockState.getBaseState()
                .withProperty(FACING, NORTH)
                .withProperty(BASE, false)
                .withProperty(POT, false)
                .withProperty(CUT, 0));
        setHardness(2.0F);
        setHarvestLevel("pickaxe", 0);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TELatexExtractor te = TileUtils.getTile(worldIn, pos, TELatexExtractor.class);
        if (te != null) {
            return state.withProperty(BASE, te.hasBase())
                    .withProperty(POT, te.hasPot())
                    .withProperty(CUT, te.cutState());
        }
        return super.getActualState(state, worldIn, pos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch (state.getValue(FACING)) {
            case NORTH:
                return AABB_N;
            case SOUTH:
                return AABB_S;
            case EAST:
                return AABB_E;
            case WEST:
                return AABB_W;
            default:
                return FULL_BLOCK_AABB;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!(worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite()))
                .getBlock() instanceof BlockLogTFC)) {
            worldIn.destroyBlock(pos, false);
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TELatexExtractor te = TileUtils.getTile(worldIn, pos, TELatexExtractor.class);
        if (te != null) {
            if (te.cutState() > 0 && worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite()))
                    .getBlock() instanceof BlockLogTFC) {
                worldIn.destroyBlock(pos.offset(state.getValue(FACING).getOpposite()), true);
            }
            te.onBreakBlock();
        }

    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX,
                                    float hitY, float hitZ) {
        TELatexExtractor te = TileUtils.getTile(world, pos, TELatexExtractor.class);
        if (te != null && hand == EnumHand.MAIN_HAND) {
            ItemStack stack = player.getHeldItem(hand);
            if (stack.getItem().getHarvestLevel(stack, "knife", player, state) != -1) {
                if (te.makeCut()) {
                    world.playSound(null, pos, TechSounds.RUBBER_TRUNK_SCRATH, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return true;
                }
            } else if (!te.hasPot() && te.isValidPot(stack) && te.addPot(stack)) {
                stack.shrink(1);
                world.playSound(null, pos, TechSounds.RUBBER_BOWL_FIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return true;
            } else if (!te.hasBase() && te.isValidBase(stack) && te.addBase(stack)) {
                stack.shrink(1);
                world.playSound(null, pos, TechSounds.RUBBER_MOUNT_FIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return true;
            } else if (stack.isEmpty() && te.hasPot()) {
                player.setHeldItem(hand, te.removePot());
                world.playSound(null, pos, TechSounds.RUBBER_BOWL_GRAB, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return true;
            } else if (stack.isEmpty() && te.hasBase()) {
                player.setHeldItem(hand, te.removeBase());
                world.playSound(null, pos, TechSounds.RUBBER_GROOVE_FIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return true;
            }
        }
        return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, BASE, POT, CUT);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TELatexExtractor();
    }
}
