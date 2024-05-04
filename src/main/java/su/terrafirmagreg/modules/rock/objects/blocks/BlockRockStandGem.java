package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.api.spi.tile.ITileBlock;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;
import su.terrafirmagreg.modules.rock.client.render.TESRRockGemDisplay;
import su.terrafirmagreg.modules.rock.objects.tiles.TileRockGemDisplay;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.api.util.PropertyUtils.HORIZONTAL;
import static su.terrafirmagreg.api.util.PropertyUtils.UP;

public class BlockRockStandGem extends BlockRock implements ITileBlock {

    public BlockRockStandGem(RockBlockVariant blockVariant, RockType type) {
        super(blockVariant, type);

        this.setSoundType(SoundType.STONE);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.0F);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(HORIZONTAL, EnumFacing.EAST)
                .withProperty(UP, Boolean.TRUE));
    }

    @Override
    public Size getSize(ItemStack itemStack) {
        return Size.LARGE;
    }

    @Override
    public Weight getWeight(ItemStack itemStack) {
        return Weight.HEAVY;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HORIZONTAL, UP);
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta))
                .withProperty(UP, meta / 4 % 2 != 0);
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(HORIZONTAL).getHorizontalIndex() + (state.getValue(UP) ? 4 : 0);
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX,
                                            float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(HORIZONTAL, placer.getHorizontalFacing());
    }

    @NotNull
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos,
                                            EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Nullable
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileRockGemDisplay();
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileRockGemDisplay te = TileUtils.getTile(worldIn, pos, TileRockGemDisplay.class);
            if (te != null) {
                return te.onRightClick(playerIn, hand);
            }
        }

        return true;
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileRockGemDisplay te = TileUtils.getTile(worldIn, pos, TileRockGemDisplay.class);
        if (te != null) {
            te.onBreakBlock();
        }
        super.breakBlock(worldIn, pos, state);
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (fromPos.equals(pos.up())) {
            if (worldIn.getBlockState(fromPos).getBlock() instanceof BlockAir) {
                state = state.withProperty(UP, Boolean.TRUE);
            } else {
                state = state.withProperty(UP, Boolean.FALSE);
            }
            worldIn.setBlockState(pos, state, 2);
        }
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
        TileRockGemDisplay te = TileUtils.getTile(world, pos, TileRockGemDisplay.class);
        assert te != null;
        return (int) Math.floor(15 * ((double) te.getSize() / (double) te.getMaxStackSize()));
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileRockGemDisplay.class;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public TileEntitySpecialRenderer<?> getTileRenderer() {
        return new TESRRockGemDisplay();
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileRockGemDisplay();
    }
}
