package net.dries007.tfc.module.core.common.objects.blocks.devices;

import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import net.dries007.tfc.Tags;
import net.dries007.tfc.api.util.IBellowsConsumerBlock;
import net.dries007.tfc.api.util.property.ILightableBlock;
import net.dries007.tfc.module.core.client.util.TFCGuiHandler;
import net.dries007.tfc.module.core.common.objects.CreativeTabsTFC;
import net.dries007.tfc.module.core.common.objects.blocks.TFCBlock;
import net.dries007.tfc.module.core.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.module.core.common.objects.items.ItemFireStarter;
import net.dries007.tfc.module.core.common.objects.tileentities.TEBellows;
import net.dries007.tfc.module.core.common.objects.tileentities.TEBlastFurnace;
import net.dries007.tfc.module.core.common.objects.tileentities.TEMetalSheet;
import net.dries007.tfc.module.metal.common.blocks.BlockMetalCladding;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.block.Multiblock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Predicate;


@ParametersAreNonnullByDefault
public class BlockBlastFurnace extends TFCBlock implements IBellowsConsumerBlock, ILightableBlock {
    private static final Multiblock BLAST_FURNACE_CHIMNEY;

    static {
        Predicate<IBlockState> stoneMatcher = state -> state.getBlock() == MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.COKE_BRICKS).getBlock();
        Predicate<IBlockState> claddingMatcher = state -> state.getBlock() instanceof BlockMetalCladding;
        BLAST_FURNACE_CHIMNEY = new Multiblock()
                .match(new BlockPos(0, 0, 0), state -> state.getBlock() == TFCBlocks.MOLTEN || state.getMaterial().isReplaceable())
                .match(new BlockPos(0, 0, 1), stoneMatcher)
                .match(new BlockPos(0, 0, -1), stoneMatcher)
                .match(new BlockPos(1, 0, 0), stoneMatcher)
                .match(new BlockPos(-1, 0, 0), stoneMatcher)
                .match(new BlockPos(0, 0, -2), claddingMatcher)
                .match(new BlockPos(0, 0, -2), tile -> tile.getFace(EnumFacing.NORTH), TEMetalSheet.class)
                .match(new BlockPos(0, 0, 2), claddingMatcher)
                .match(new BlockPos(0, 0, 2), tile -> tile.getFace(EnumFacing.SOUTH), TEMetalSheet.class)
                .match(new BlockPos(2, 0, 0), claddingMatcher)
                .match(new BlockPos(2, 0, 0), tile -> tile.getFace(EnumFacing.EAST), TEMetalSheet.class)
                .match(new BlockPos(-2, 0, 0), claddingMatcher)
                .match(new BlockPos(-2, 0, 0), tile -> tile.getFace(EnumFacing.WEST), TEMetalSheet.class)
                .match(new BlockPos(-1, 0, -1), claddingMatcher)
                .match(new BlockPos(-1, 0, -1), tile -> tile.getFace(EnumFacing.NORTH) && tile.getFace(EnumFacing.WEST), TEMetalSheet.class)
                .match(new BlockPos(1, 0, -1), claddingMatcher)
                .match(new BlockPos(1, 0, -1), tile -> tile.getFace(EnumFacing.NORTH) && tile.getFace(EnumFacing.EAST), TEMetalSheet.class)
                .match(new BlockPos(-1, 0, 1), claddingMatcher)
                .match(new BlockPos(-1, 0, 1), tile -> tile.getFace(EnumFacing.SOUTH) && tile.getFace(EnumFacing.WEST), TEMetalSheet.class)
                .match(new BlockPos(1, 0, 1), claddingMatcher)
                .match(new BlockPos(1, 0, 1), tile -> tile.getFace(EnumFacing.SOUTH) && tile.getFace(EnumFacing.EAST), TEMetalSheet.class);
    }

    public BlockBlastFurnace() {
        super(Material.IRON);
        setHardness(2.0F);
        setResistance(2.0F);
        setHarvestLevel("pickaxe", 0);

        setCreativeTab(CreativeTabsTFC.MISC_TAB);
        setRegistryName(Tags.MOD_ID, "blast_furnace");
        setTranslationKey(Tags.MOD_ID + ".blast_furnace");
    }

    /**
     * Structural check of the blast furnace. Any value > 0 means this blast furnace can work
     *
     * @param world the world obj to check on the structrue
     * @param pos   this block pos
     * @return [0, 5] where 0 means this blast furnace can't operate.
     */
    public static int getChimneyLevels(World world, BlockPos pos) {
        for (int i = 1; i < 6; i++) {
            BlockPos center = pos.up(i);
            if (!BLAST_FURNACE_CHIMNEY.test(world, center)) {
                return i - 1;
            }
        }
        // Maximum levels
        return 5;
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(LIT, meta == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(LIT) ? 1 : 0;
    }


    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TEBlastFurnace te = Helpers.getTE(worldIn, pos, TEBlastFurnace.class);
        if (te != null) {
            te.onBreakBlock(worldIn, pos, state);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            if (!state.getValue(LIT)) {
                TEBlastFurnace te = Helpers.getTE(worldIn, pos, TEBlastFurnace.class);
                if (te == null)
                    return true;
                ItemStack held = playerIn.getHeldItem(hand);
                if (te.canIgnite() && ItemFireStarter.onIgnition(held)) {
                    worldIn.setBlockState(pos, state.withProperty(LIT, true));
                    //te.onIgnite();
                    return true;
                }
            }
            if (!playerIn.isSneaking()) {
                TFCGuiHandler.openGui(worldIn, pos, playerIn, TFCGuiHandler.Type.BLAST_FURNACE);
            }
        }
        return true;
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LIT);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TEBlastFurnace();
    }

    @Override
    public boolean canIntakeFrom(@Nonnull Vec3i offset, @Nonnull EnumFacing facing) {
        return offset.equals(TEBellows.OFFSET_LEVEL);
    }

    @Override
    public void onAirIntake(@Nonnull World world, @Nonnull BlockPos pos, int airAmount) {
        TEBlastFurnace teBlastFurnace = Helpers.getTE(world, pos, TEBlastFurnace.class);
        if (teBlastFurnace != null) {
            teBlastFurnace.onAirIntake(airAmount);
        }
    }
}
