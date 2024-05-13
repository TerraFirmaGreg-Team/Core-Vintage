package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlock;
import su.terrafirmagreg.api.spi.tile.ITileBlock;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.client.render.TESRLatexExtractor;
import su.terrafirmagreg.modules.device.init.SoundDevice;
import su.terrafirmagreg.modules.device.objects.tiles.TileLatexExtractor;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
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


import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;

import org.jetbrains.annotations.Nullable;

import static net.minecraft.block.BlockHorizontal.FACING;
import static net.minecraft.util.EnumFacing.NORTH;

@SuppressWarnings("deprecation")
public class BlockLatexExtractor extends BaseBlock implements ITileBlock {

    public static final PropertyBool BASE = PropertyBool.create("base"); //from TE
    public static final PropertyBool POT = PropertyBool.create("pot"); //from TE
    public static final PropertyInteger CUT = PropertyInteger.create("cut", 0, 2); //from TE

    private static final AxisAlignedBB AABB_N = new AxisAlignedBB(0.1875D, 0.125D, 0.3125D, 0.8125D, 0.875D, 1.0D);
    private static final AxisAlignedBB AABB_S = new AxisAlignedBB(0.1875D, 0.125D, 0.0D, 0.8125D, 0.875D, 0.6875D);
    private static final AxisAlignedBB AABB_E = new AxisAlignedBB(0.0D, 0.125D, 0.1875D, 0.6875D, 0.875D, 0.8125D);
    private static final AxisAlignedBB AABB_W = new AxisAlignedBB(0.3125D, 0.125D, 0.1875D, 1.0D, 0.875D, 0.8125D);

    public BlockLatexExtractor() {
        super(Settings.of(Material.IRON));

        getSettings()
                .registryKey("device/latex_extractor")
                .noItemBlock()
                .renderLayer(BlockRenderLayer.CUTOUT_MIPPED)
                .hardness(2.0F)
                .nonFullCube()
                .nonOpaque();
        
        setHarvestLevel("pickaxe", 0);
        setDefaultState(getBlockState().getBaseState()
                .withProperty(FACING, NORTH)
                .withProperty(BASE, false)
                .withProperty(POT, false)
                .withProperty(CUT, 0));
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
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        var tile = TileUtils.getTile(worldIn, pos, TileLatexExtractor.class);
        if (tile != null) {
            return state.withProperty(BASE, tile.hasBase())
                    .withProperty(POT, tile.hasPot())
                    .withProperty(CUT, tile.cutState());
        }
        return super.getActualState(state, worldIn, pos);
    }

    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return switch (state.getValue(FACING)) {
            case NORTH -> AABB_N;
            case SOUTH -> AABB_S;
            case EAST -> AABB_E;
            case WEST -> AABB_W;
            default -> FULL_BLOCK_AABB;
        };
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!(worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite()))
                .getBlock() instanceof BlockLogTFC)) {
            worldIn.destroyBlock(pos, false);
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        var tile = TileUtils.getTile(worldIn, pos, TileLatexExtractor.class);
        if (tile != null) {
            if (tile.cutState() > 0 && worldIn.getBlockState(pos.offset(state.getValue(FACING).getOpposite()))
                    .getBlock() instanceof BlockLogTFC) {
                worldIn.destroyBlock(pos.offset(state.getValue(FACING).getOpposite()), true);
            }
            tile.onBreakBlock();
        }

    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        var tile = TileUtils.getTile(world, pos, TileLatexExtractor.class);
        if (tile != null && hand == EnumHand.MAIN_HAND) {
            ItemStack stack = player.getHeldItem(hand);
            if (stack.getItem().getHarvestLevel(stack, "knife", player, state) != -1) {
                if (tile.makeCut()) {
                    world.playSound(null, pos, SoundDevice.LATEX_EXTRACTOR_TRUNK_SCRATH, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    return true;
                }
            } else if (!tile.hasPot() && tile.isValidPot(stack) && tile.addPot(stack)) {
                stack.shrink(1);
                world.playSound(null, pos, SoundDevice.LATEX_EXTRACTOR_BOWL_FIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return true;
            } else if (!tile.hasBase() && tile.isValidBase(stack) && tile.addBase(stack)) {
                stack.shrink(1);
                world.playSound(null, pos, SoundDevice.LATEX_EXTRACTOR_MOUNT_FIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return true;
            } else if (stack.isEmpty() && tile.hasPot()) {
                player.setHeldItem(hand, tile.removePot());
                world.playSound(null, pos, SoundDevice.LATEX_EXTRACTOR_BOWL_GRAB, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return true;
            } else if (stack.isEmpty() && tile.hasBase()) {
                player.setHeldItem(hand, tile.removeBase());
                world.playSound(null, pos, SoundDevice.LATEX_EXTRACTOR_GROOVE_FIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                return true;
            }
        }
        return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, BASE, POT, CUT);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileLatexExtractor();
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileLatexExtractor.class;
    }

    @Override
    public @Nullable TileEntitySpecialRenderer<?> getTileRenderer() {
        return new TESRLatexExtractor();
    }
}
