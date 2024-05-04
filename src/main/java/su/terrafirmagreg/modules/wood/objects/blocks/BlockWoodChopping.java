package su.terrafirmagreg.modules.wood.objects.blocks;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.util.FakePlayer;


import se.gory_moon.horsepower.Configs;
import se.gory_moon.horsepower.HPEventHandler;
import se.gory_moon.horsepower.lib.Constants;
import se.gory_moon.horsepower.tileentity.TileEntityManualChopper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockWoodChopping extends BlockWoodChoppingBase {

    private static final AxisAlignedBB COLLISION_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 6D / 16D, 1.0D);

    public BlockWoodChopping() {
        super();
        setHardness(2.0F);
        setResistance(5.0F);
        setRegistryName(Constants.HAND_CHOPPING_BLOCK);
        setTranslationKey(Constants.HAND_CHOPPING_BLOCK);
    }

    public static boolean isValidChoppingTool(ItemStack stack, EntityPlayer player) {
        return !stack.isEmpty() && ((stack.getItem()
                .getHarvestLevel(stack, "axe", player, null) > -1) || isChoppingToolWhitelisted(stack));
    }

    private static boolean isChoppingToolWhitelisted(ItemStack stack) {
        for (ItemStack itemStack : HPEventHandler.choppingAxes.keySet()) {
            if (ItemStack.areItemsEqualIgnoreDurability(itemStack, stack))
                return true;
        }
        return false;
    }

    @Override
    public void emptiedOutput(World world, BlockPos pos) {
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX,
                                    float hitY, float hitZ) {
        return player instanceof FakePlayer || player == null || super.onBlockActivated(worldIn, pos, state, player, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    @NotNull
    public Class<?> getTileClass() {
        return TileEntityManualChopper.class;
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (Configs.general.enableHandChoppingBlock)
            super.getSubBlocks(tab, list);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return COLLISION_AABB;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return COLLISION_AABB;
    }

    @Override
    public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World world, BlockPos pos) {
        TileEntityManualChopper te = getTileEntity(world, pos);
        if (te != null) {
            ItemStack heldItem = player.getHeldItem(EnumHand.MAIN_HAND);
            if (isValidChoppingTool(heldItem, player)) {
                if (te.canWork()) {
                    return -1;
                }
            }
        }

        return super.getPlayerRelativeBlockHardness(state, player, world, pos);
    }

    @Override
    public void onBlockClicked(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull EntityPlayer player) {
        if (player instanceof FakePlayer || player == null)
            return;

        TileEntityManualChopper te = getTileEntity(worldIn, pos);
        if (te != null) {
            ItemStack held = player.getHeldItem(EnumHand.MAIN_HAND);
            if (isValidChoppingTool(held, player)) {
                if (te.chop(player, held)) {
                    player.addExhaustion((float) Configs.general.choppingblockExhaustion);
                    if (Configs.general.shouldDamageAxe)
                        held.damageItem(1, player);
                }
            }
        }
    }

    @Override
    @NotNull
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[] {}, new IUnlistedProperty[] { SIDE_TEXTURE, TOP_TEXTURE });
    }
}
