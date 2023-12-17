package tfctech.objects.blocks.devices;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.util.IBellowsConsumerBlock;
import net.dries007.tfc.objects.blocks.property.ILightableBlock;
import net.dries007.tfc.objects.items.ItemFireStarter;
import net.dries007.tfc.objects.te.TEBellows;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tfctech.client.TechGuiHandler;
import tfctech.objects.tileentities.TESmelteryFirebox;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockSmelteryFirebox extends BlockHorizontal implements IBellowsConsumerBlock, ILightableBlock, IItemSize {

    public BlockSmelteryFirebox() {
        super(Material.IRON);
        setHardness(3.0F);
        setSoundType(SoundType.STONE);
        setHarvestLevel("pickaxe", 0);
        setDefaultState(getBlockState().getBaseState().withProperty(LIT, false).withProperty(FACING, EnumFacing.NORTH));
        setTickRandomly(true);
        setLightLevel(1f);
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack itemStack) {
        return Size.LARGE;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack itemStack) {
        return Weight.MEDIUM;
    }

    @Override
    @SuppressWarnings("deprecation")
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(FACING, EnumFacing.byHorizontalIndex(meta % 4))
                .withProperty(LIT, meta / 4 % 2 != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex()
                + (state.getValue(LIT) ? 4 : 0);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rng) {
        if (!state.getValue(LIT)) return;

        if (rng.nextInt(24) == 0) {
            world.playSound((float) pos.getX() + 0.5F, (float) pos.getY() + 0.5F, (float) pos.getZ() + 0.5F, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + rng.nextFloat(), rng.nextFloat() * 0.7F + 0.3F, false);
        }
        if (rng.nextFloat() < 0.4f) {
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.35;
            double z = pos.getZ() + 0.5;
            switch (state.getValue(FACING)) {
                case NORTH:
                    z -= 0.6f;
                    break;
                case SOUTH:
                    z += 0.6f;
                    break;
                case WEST:
                    x += 0.6f;
                    break;
                case EAST:
                    x += 0.6f;
                    break;
            }

            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0D, 0.2D, 0.0D);
            if (rng.nextFloat() > 0.75)
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, x, y, z, 0.0D, 0.1D, 0.0D);
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TESmelteryFirebox te = Helpers.getTE(worldIn, pos, TESmelteryFirebox.class);
        if (te != null) {
            te.onBreakBlock(worldIn, pos, state);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!player.isSneaking()) {
            if (!world.isRemote) {
                ItemStack held = player.getHeldItem(hand);
                if (world.getBlockState(pos.up()).getBlock() instanceof BlockSmelteryCauldron) {
                    TESmelteryFirebox firebox = Helpers.getTE(world, pos, TESmelteryFirebox.class);
                    if (ItemFireStarter.canIgnite(held) && firebox.onIgnite()) {
                        ItemFireStarter.onIgnition(held);
                    } else {
                        TechGuiHandler.openGui(world, pos, player, TechGuiHandler.Type.SMELTERY_FIREBOX);
                    }
                } else {
                    if (held.getItem() instanceof ItemBlock && ((ItemBlock) held.getItem()).getBlock() instanceof BlockSmelteryCauldron && world.getBlockState(pos.up()).getMaterial().isReplaceable()) {
                        held.getItem().onItemUse(player, world, pos.up(), hand, side, hitX, hitY, hitZ);
                    } else {
                        player.sendStatusMessage(new TextComponentTranslation("tooltip.tfctech.smeltery.invalid"), true);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LIT, FACING);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.getValue(LIT) ? 15 : 0;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TESmelteryFirebox();
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public boolean canIntakeFrom(@Nonnull Vec3i offset, @Nonnull EnumFacing direction) {
        return offset.equals(TEBellows.OFFSET_LEVEL);
    }

    @Override
    public void onAirIntake(@Nonnull World world, @Nonnull BlockPos pos, int airAmount) {
        TESmelteryFirebox firebox = Helpers.getTE(world, pos, TESmelteryFirebox.class);
        if (firebox != null) {
            firebox.onAirIntake(airAmount);
        }
    }

}
