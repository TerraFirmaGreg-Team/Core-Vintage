package net.dries007.tfc.module.devices.objects.blocks;

import net.dries007.tfc.client.util.CustomStateMap;
import net.dries007.tfc.client.util.TFCGuiHandler;
import net.dries007.tfc.module.core.api.capability.size.IItemSizeAndWeight;
import net.dries007.tfc.module.core.api.capability.size.Size;
import net.dries007.tfc.module.core.api.capability.size.Weight;
import net.dries007.tfc.module.core.api.objects.block.BlockBase;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.core.api.util.IHasModel;
import net.dries007.tfc.module.devices.api.util.IHeatConsumerBlock;
import net.dries007.tfc.module.devices.objects.blocks.itemblocks.ItemBlockCrucible;
import net.dries007.tfc.module.devices.objects.tile.TECrucible;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;


@ParametersAreNonnullByDefault
public class BlockCrucible extends BlockBase implements IHeatConsumerBlock, IItemSizeAndWeight, IHasModel {
    public static final String NAME = "device.crucible";
    private static final AxisAlignedBB CRUCIBLE_AABB = new AxisAlignedBB(0.0625, 0.0625, 0.0625, 0.9375, 0.9375, 0.9375);
    private static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.9375D, 0.125D, 0.9375D);
    private static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.9375D, 0.1875D);
    private static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0625D, 0.0D, 0.8125D, 0.9375D, 0.9375D, 0.9375D);
    private static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.8125D, 0.0D, 0.0625D, 0.9375D, 0.9375D, 0.9375D);
    private static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.1875D, 0.9375D, 0.9375D);

    public BlockCrucible() {
        super(Material.IRON);

        setHardness(3.0f);
        setHarvestLevel("pickaxe", 0);
        setSoundType(SoundType.METAL);
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockCrucible(this);
    }

    @Override
    public void acceptHeat(World world, BlockPos pos, float temperature) {
        TECrucible tile = Helpers.getTE(world, pos, TECrucible.class);
        if (tile != null) {
            tile.acceptHeat(temperature);
        }
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack) {
        return stack.getTagCompound() == null ? Size.LARGE : Size.HUGE; // Can only store in chests if not full, overburden if full and more than one is carried
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack) {
        return Weight.VERY_HEAVY;  // stacksize = 1
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isTopSolid(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isNormalCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return CRUCIBLE_AABB;
    }

    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        if (face == EnumFacing.UP) {
            return BlockFaceShape.BOWL;
        }
        return BlockFaceShape.UNDEFINED;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
    }

    @SuppressWarnings("deprecation")
    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return CRUCIBLE_AABB;
    }

    @SuppressWarnings("deprecation")
    @SideOnly(Side.CLIENT)
    @Override
    @Nonnull
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return CRUCIBLE_AABB;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TECrucible te = Helpers.getTE(worldIn, pos, TECrucible.class);
        if (te != null) {
            te.onBreakBlock(worldIn, pos, state);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote && !playerIn.isSneaking()) {
            TFCGuiHandler.openGui(worldIn, pos, playerIn, TFCGuiHandler.Type.CRUCIBLE);
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (!worldIn.isRemote) {
            NBTTagCompound nbt = stack.getTagCompound();
            if (nbt != null) {
                TECrucible te = Helpers.getTE(worldIn, pos, TECrucible.class);
                if (te != null) {
                    te.readFromItemTag(nbt);
                }
            }
        }
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isSideSolid(IBlockState baseState, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TECrucible();
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        //breakBlock already handle this
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {

        var resourceLocation = Helpers.getID(NAME.replaceAll("\\.", "/"));

        ModelLoader.setCustomStateMapper(this,
                new CustomStateMap.Builder().customPath(resourceLocation).build());

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(resourceLocation, "normal"));
    }
}
