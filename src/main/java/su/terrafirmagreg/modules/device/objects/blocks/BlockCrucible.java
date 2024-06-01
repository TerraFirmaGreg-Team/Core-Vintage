package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.data.Constants;
import su.terrafirmagreg.api.spi.block.BaseBlockContainer;
import su.terrafirmagreg.api.spi.tile.provider.ITileProvider;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.device.client.render.TESRCrucible;
import su.terrafirmagreg.modules.device.objects.tiles.TileCrucible;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import gregtech.api.items.toolitem.ToolClasses;
import net.dries007.tfc.ConfigTFC;


import su.terrafirmagreg.api.capabilities.size.spi.Size;

import su.terrafirmagreg.api.capabilities.size.spi.Weight;


import net.dries007.tfc.api.util.IHeatConsumerBlock;
import net.dries007.tfc.util.Alloy;

import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("deprecation")
public class BlockCrucible extends BaseBlockContainer implements IHeatConsumerBlock, ITileProvider {

    private static final AxisAlignedBB CRUCIBLE_AABB = new AxisAlignedBB(0.0625, 0.0625, 0.0625, 0.9375, 0.9375, 0.9375);
    private static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.9375D, 0.125D, 0.9375D);
    private static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.9375D, 0.1875D);
    private static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0625D, 0.0D, 0.8125D, 0.9375D, 0.9375D, 0.9375D);
    private static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.8125D, 0.0D, 0.0625D, 0.9375D, 0.9375D, 0.9375D);
    private static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.1875D, 0.9375D, 0.9375D);

    public BlockCrucible() {
        super(Settings.of(Material.IRON));

        getSettings()
                .registryKey("device/crucible")
                .soundType(SoundType.METAL)
                .nonFullCube()
                .nonOpaque()
                .hardness(3.0f)
                .weight(Weight.VERY_HEAVY);
        setHarvestLevel(ToolClasses.PICKAXE, 0);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public void acceptHeat(World world, BlockPos pos, float temperature) {
        var tile = TileUtils.getTile(world, pos, TileCrucible.class);
        if (tile != null) {
            tile.acceptHeat(temperature);
        }
    }

    @Override
    public Size getSize(ItemStack stack) {
        return stack.getTagCompound() == null ? Size.LARGE : Size.HUGE; // Can only store in chests if not full, overburden if full and more than one is carried
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt != null) {
            Alloy alloy = new Alloy(ConfigTFC.Devices.CRUCIBLE.tank);
            alloy.deserializeNBT(nbt.getCompoundTag("alloy"));
            String metalName = new TextComponentTranslation(alloy.getResult().getTranslationKey()).getFormattedText();
            tooltip.add(I18n.format(Constants.MODID_TFC + ".tooltip.crucible_alloy", alloy.getAmount(), metalName));
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return CRUCIBLE_AABB;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        if (face == EnumFacing.UP) {
            return BlockFaceShape.BOWL;
        }
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn,
                                      boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
    }

    @Override
    public @Nullable AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return CRUCIBLE_AABB;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return CRUCIBLE_AABB;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        var tile = TileUtils.getTile(world, pos, TileCrucible.class);
        if (tile != null) {
            tile.onBreakBlock(world, pos, state);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && !playerIn.isSneaking()) {
            GuiHandler.openGui(world, pos, playerIn, GuiHandler.Type.CRUCIBLE);
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (!world.isRemote) {
            NBTTagCompound nbt = stack.getTagCompound();
            if (nbt != null) {
                var tile = TileUtils.getTile(world, pos, TileCrucible.class);
                if (tile != null) {
                    tile.readFromItemTag(nbt);
                }
            }
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        //breakBlock already handle this
    }

    @Override
    public @Nullable TileCrucible createNewTileEntity(World world, int meta) {
        return new TileCrucible();
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileCrucible.class;
    }

    @Override
    public TileEntitySpecialRenderer<?> getTileRenderer() {
        return new TESRCrucible();
    }
}
