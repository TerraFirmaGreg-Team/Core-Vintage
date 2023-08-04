package net.dries007.tfc.objects.blocks.wood;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.wood.Wood;
import net.dries007.tfc.api.types.wood.WoodVariant;
import net.dries007.tfc.api.types.wood.util.IWoodBlock;
import net.dries007.tfc.client.CustomStateMap;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.objects.te.TEToolRack;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.minecraft.block.BlockHorizontal.FACING;
import static net.minecraft.block.material.Material.WOOD;

@ParametersAreNonnullByDefault
public class BlockWoodToolRack extends Block implements IItemSize, IWoodBlock {
    protected static final AxisAlignedBB RACK_EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);
    protected static final AxisAlignedBB RACK_WEST_AABB = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB RACK_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
    protected static final AxisAlignedBB RACK_NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);

    private final WoodVariant woodVariant;
    private final Wood wood;
    private final ResourceLocation modelLocation;

    public BlockWoodToolRack(WoodVariant woodVariant, Wood wood) {
        super(WOOD, MapColor.AIR);

        this.woodVariant = woodVariant;
        this.wood = wood;
        this.modelLocation = new ResourceLocation(MOD_ID, "wood/" + woodVariant);

        var blockRegistryName = String.format("wood/%s/%s", woodVariant, wood);
        setRegistryName(MOD_ID, blockRegistryName);
        setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
        setCreativeTab(CreativeTabsTFC.WOOD);
        setSoundType(SoundType.WOOD);
        setHarvestLevel("axe", 0);
        setHardness(0.5f);
        setResistance(3f);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public WoodVariant getWoodVariant() {
        return woodVariant;
    }

    @Override
    public Wood getWood() {
        return wood;
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTFC(this);
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack) {
        return Size.LARGE; // Stored only in chests
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack) {
        return Weight.VERY_HEAVY; // Stacksize = 1
    }

    @Override
    @SuppressWarnings("deprecation")
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    @Nonnull
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    @SuppressWarnings("deprecation")
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return switch (state.getValue(FACING)) {
            default -> RACK_NORTH_AABB;
            case SOUTH -> RACK_SOUTH_AABB;
            case WEST -> RACK_WEST_AABB;
            case EAST -> RACK_EAST_AABB;
        };
    }

    @Override
    @SuppressWarnings("deprecation")
    @Nonnull
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        if (!Helpers.canHangAt(worldIn, pos, state.getValue(FACING))) {
            dropBlockAsItem(worldIn, pos, state, 0);
            TEToolRack te = Helpers.getTE(worldIn, pos, TEToolRack.class);
            if (te != null) {
                te.onBreakBlock();
            }
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public void breakBlock(World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        TEToolRack te = Helpers.getTE(worldIn, pos, TEToolRack.class);
        if (te != null) {
            te.onBreakBlock();
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, @Nonnull BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && Helpers.getASolidFacing(worldIn, pos, null, EnumFacing.HORIZONTALS) != null;
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TEToolRack te = Helpers.getTE(worldIn, pos, TEToolRack.class);
            if (te != null) {
                return te.onRightClick(playerIn, hand, getSlotFromPos(state, hitX, hitY, hitZ));
            }
        }
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    @Nonnull
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        if (facing.getAxis() == EnumFacing.Axis.Y) {
            facing = placer.getHorizontalFacing().getOpposite();
        }
        return this.getDefaultState().withProperty(FACING, Helpers.getASolidFacing(worldIn, pos, facing, EnumFacing.HORIZONTALS));
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TEToolRack();
    }

    @Override
    @Nonnull
    @SuppressWarnings("ConstantConditions")
    public ItemStack getPickBlock(IBlockState state, @Nullable RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        if (target != null) {
            Vec3d vec = target.hitVec.subtract(pos.getX(), pos.getY(), pos.getZ());
            TEToolRack te = Helpers.getTE(world, pos, TEToolRack.class);
            if (te != null) {
                ItemStack item = te.getItems().get(getSlotFromPos(state, (float) vec.x, (float) vec.y, (float) vec.z));
                if (!item.isEmpty()) {
                    return item;
                }
            }
        }
        return super.getPickBlock(state, target, world, pos, player);
    }

    public int getSlotFromPos(IBlockState state, float x, float y, float z) {
        int slot = 0;
        if ((state.getValue(FACING).getAxis().equals(EnumFacing.Axis.Z) ? x : z) > .5f) {
            slot += 1;
        }
        if (y < 0.5f) {
            slot += 2;
        }
        return slot;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new CustomStateMap.Builder().customPath(modelLocation).build());

        for (IBlockState state : this.getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),
                    this.getMetaFromState(state),
                    new ModelResourceLocation(modelLocation, "normal"));
        }
    }
}
