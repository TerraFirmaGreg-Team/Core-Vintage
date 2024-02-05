package su.terrafirmagreg.modules.soil.objects.blocks;


import net.dries007.tfc.api.util.FallingBlockManager;

import net.minecraft.block.Block;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.objects.itemblock.ItemBlockBase;
import su.terrafirmagreg.modules.soil.StorageSoil;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlockVariant;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariants;

import java.util.Random;

public class BlockSoilMycelium extends BlockMycelium implements ISoilBlockVariant {

    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");

    private final SoilBlockVariant variant;
    private final SoilType type;

    public BlockSoilMycelium(SoilBlockVariant variant, SoilType type) {

        this.variant = variant;
        this.type = type;

        if (variant.canFall()) FallingBlockManager.registerFallable(this, variant.getSpecification());


        setDefaultState(this.blockState.getBaseState()
                .withProperty(NORTH, Boolean.FALSE)
                .withProperty(EAST, Boolean.FALSE)
                .withProperty(SOUTH, Boolean.FALSE)
                .withProperty(WEST, Boolean.FALSE));


        //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
    }

    @NotNull
    @Override
    public SoilBlockVariant getBlockVariant() {
        return variant;
    }

    @NotNull
    @Override
    public SoilType getType() {
        return type;
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockBase(this);
    }

    @NotNull
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, @NotNull BlockPos pos) {
        pos = pos.add(0, -1, 0);
        Block blockUp = world.getBlockState(pos.up()).getBlock();
        return state
                .withProperty(NORTH, world.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() instanceof BlockSoilGrass)
                .withProperty(EAST, world.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() instanceof BlockSoilGrass)
                .withProperty(SOUTH, world.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() instanceof BlockSoilGrass)
                .withProperty(WEST, world.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() instanceof BlockSoilGrass)
                .withProperty(SNOWY, blockUp == Blocks.SNOW || blockUp == Blocks.SNOW_LAYER);
    }

    @NotNull
    @Override
    public Item getItemDropped(@NotNull IBlockState state, @NotNull Random rand, int fortune) {
        return StorageSoil.getItem(SoilItemVariants.PILE, type);
    }

    @NotNull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
