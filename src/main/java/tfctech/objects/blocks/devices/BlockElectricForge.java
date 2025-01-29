package tfctech.objects.blocks.devices;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mcp.MethodsReturnNonnullByDefault;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import tfctech.client.TechGuiHandler;
import tfctech.objects.tileentities.TEElectricForge;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockElectricForge extends BlockHorizontal implements ICapabilitySize
{
    public static final PropertyBool LIT = PropertyBool.create("lit");

    public BlockElectricForge()
    {
        super(Material.IRON);
        setSoundType(SoundType.METAL);
        setHardness(4);
        setHarvestLevel("pickaxe", 0);
        setDefaultState(getBlockState().getBaseState().withProperty(LIT, false).withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    @SuppressWarnings("deprecation")
    @Nonnull
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta % 4)).withProperty(LIT, meta >= 4);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex() + (state.getValue(LIT) ? 4 : 0);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!player.isSneaking())
        {
            if (!world.isRemote)
            {
                TechGuiHandler.openGui(world, pos, player, TechGuiHandler.Type.ELECTRIC_FORGE);
            }
            return true;
        }
        return false;
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, LIT);
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TEElectricForge();
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack itemStack)
    {
        return Size.LARGE;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack itemStack)
    {
        return Weight.MEDIUM;
    }

    @Override
    public boolean canStack(@Nonnull ItemStack stack)
    {
        return false;
    }
}
