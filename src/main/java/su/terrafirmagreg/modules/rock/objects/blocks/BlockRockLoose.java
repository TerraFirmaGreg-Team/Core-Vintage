package su.terrafirmagreg.modules.rock.objects.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.util.FallingBlockManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.modules.rock.StorageRock;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariants;


public class BlockRockLoose extends BlockRock {

    public static final PropertyDirection AXIS = PropertyDirection.create("axis", EnumFacing.Plane.HORIZONTAL);
    private static final AxisAlignedBB STONE_AABB = new AxisAlignedBB(
            2.0 / 16.0, 0.0 / 16.0, 2.0 / 16.0,
            14.0 / 16.0, 2.0 / 16.0, 14.0 / 16.0);

    public BlockRockLoose(RockBlockVariant variant, RockType type) {
        super(variant, type);

        setHardness(0.1f);
        setDefaultState(blockState.getBaseState().withProperty(AXIS, EnumFacing.NORTH));
        FallingBlockManager.registerFallable(this, variant.getSpecification());
    }

    @Override
    public ItemBlock getItemBlock() {
        return null;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!worldIn.isSideSolid(pos.down(), EnumFacing.UP)) {
            worldIn.destroyBlock(pos, false);
        }
    }

    @NotNull
    @Override
    @SuppressWarnings("deprecation")
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        var itemStack = new ItemStack(StorageRock.getItem(RockItemVariants.LOOSE, getType()));

        if (playerIn.addItemStackToInventory(itemStack)) {
            worldIn.setBlockToAir(pos);

            playerIn.swingArm(EnumHand.MAIN_HAND);
            playerIn.playSound(SoundEvents.ENTITY_ITEMFRAME_REMOVE_ITEM, 1.0f, 1.0f);
        }

        return true;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        drops.add(new ItemStack(StorageRock.getItem(RockItemVariants.LOOSE, getType())));
    }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        return true;
    }

    @NotNull
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(StorageRock.getItem(RockItemVariants.LOOSE, getType()));
    }

    @NotNull
    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(@NotNull IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
        return STONE_AABB;
    }

    @Override

    @SuppressWarnings("deprecation")
    public boolean isTopSolid(IBlockState state) {
        return false;
    }

    @Override

    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override

    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Nullable
    @Override

    @SuppressWarnings("deprecation")
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return STONE_AABB;
    }

    @Override

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos);
        IBlockState stateDown = worldIn.getBlockState(pos.down());

        return stateDown.isSideSolid(worldIn, pos, EnumFacing.DOWN) && state.getBlock().equals(Blocks.AIR);
    }

    @Override
    @NotNull
    public Block.EnumOffsetType getOffsetType() {
        return Block.EnumOffsetType.XZ;
    }


    @Override
    @NotNull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @NotNull
    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AXIS, EnumFacing.byHorizontalIndex(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(AXIS).getHorizontalIndex();
    }

    @NotNull
    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AXIS);
    }
}
