package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.base.block.BaseBlock;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;


import static su.terrafirmagreg.api.data.Blockstates.CURED;
import static su.terrafirmagreg.api.lib.MathConstants.RNG;

@SuppressWarnings("deprecation")
public class BlockOvenChimney extends BaseBlock {

    public static final AxisAlignedBB CHIMNEY_BB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.25D)
            .union(new AxisAlignedBB(0.0D, 0.0D, 0.75D, 1.0D, 1.0D, 1.0D)
                    .union(new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.25D, 1.0D, 0.75D))
                    .union(new AxisAlignedBB(0.75D, 0.0D, 0.25D, 1.0D, 1.0D, 0.75D)));

    public BlockOvenChimney() {
        super(Settings.of(Material.ROCK, MapColor.RED_STAINED_HARDENED_CLAY));

        getSettings()
                .registryKey("device/oven_chimney")
                .hardness(2.0f)
                .resistance(3.0f)
                .nonOpaque()
                .size(Size.NORMAL)
                .weight(Weight.HEAVY)
                .lightValue(0);

        setDefaultState(getBlockState().getBaseState()
                .withProperty(CURED, false));
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if (state.getValue(CURED)) {
            drops.add(new ItemStack(Items.BRICK, 3 + RNG.nextInt(3)));
        } else {
            super.getDrops(drops, world, pos, state, fortune);
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(CURED, meta == 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(CURED) ? 1 : 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, CURED);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return CHIMNEY_BB;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
}
