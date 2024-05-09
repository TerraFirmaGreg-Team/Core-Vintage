package su.terrafirmagreg.modules.soil.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlock;
import su.terrafirmagreg.api.spi.block.IBlockColorProvider;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.soil.client.GrassColorHandler;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static su.terrafirmagreg.api.data.Blockstates.*;

@SuppressWarnings("deprecation")
public class BlockSoilPeatGrass extends BaseBlock implements IBlockColorProvider {

    public BlockSoilPeatGrass() {
        super(Settings.of()
                .material(Material.GRASS)
                .soundType(SoundType.PLANT));

        setTickRandomly(true);
        setDefaultState(getBlockState().getBaseState()
                .withProperty(NORTH, Boolean.FALSE)
                .withProperty(EAST, Boolean.FALSE)
                .withProperty(SOUTH, Boolean.FALSE)
                .withProperty(WEST, Boolean.FALSE));

        BlockUtils.setFireInfo(this, 5, 5);
    }

    @Override
    public String getName() {
        return "soil/peat_grass";
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, "peat");
        OreDictUtils.register(this, "peat", "grass");
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @NotNull
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        pos = pos.add(0, -1, 0);
        return state.withProperty(NORTH, BlockUtils.isGrass(world.getBlockState(pos.offset(EnumFacing.NORTH))))
                .withProperty(EAST, BlockUtils.isGrass(world.getBlockState(pos.offset(EnumFacing.EAST))))
                .withProperty(SOUTH, BlockUtils.isGrass(world.getBlockState(pos.offset(EnumFacing.SOUTH))))
                .withProperty(WEST, BlockUtils.isGrass(world.getBlockState(pos.offset(EnumFacing.WEST))));
    }

    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (world.isRemote) return;
        BlockSoilGrass.spreadGrass(world, pos, state, rand);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(BlocksSoil.PEAT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH);
    }

    @Override
    public IBlockColor getBlockColor() {
        return GrassColorHandler::computeGrassColor;
    }

    @Override
    public IItemColor getItemColor() {
        return (s, i) -> this.getBlockColor().colorMultiplier(this.getDefaultState(), null, null, i);
    }
}
