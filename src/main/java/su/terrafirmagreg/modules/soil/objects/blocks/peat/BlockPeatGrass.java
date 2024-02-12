package su.terrafirmagreg.modules.soil.objects.blocks.peat;

import net.dries007.tfc.client.GrassColorHandler;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.spi.block.BlockBase;
import su.terrafirmagreg.api.spi.block.IColorfulBlock;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilGrass;

import java.util.Random;


public class BlockPeatGrass extends BlockBase implements IColorfulBlock {

    // Used for connected textures only.
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");

    public BlockPeatGrass() {
        super(Material.GRASS);

        setSoundType(SoundType.PLANT);
        setTickRandomly(true);
        setDefaultState(this.getDefaultState()
                .withProperty(NORTH, Boolean.FALSE)
                .withProperty(EAST, Boolean.FALSE)
                .withProperty(SOUTH, Boolean.FALSE)
                .withProperty(WEST, Boolean.FALSE));

        OreDictionaryHelper.register(this, "peat");
        OreDictionaryHelper.register(this, "peat", "grass");
        Blocks.FIRE.setFireInfo(this, 5, 5);
    }

    @Override
    public @NotNull String getName() {
        return "soil/peat_grass";
    }

    @Override
    public int getMetaFromState(@NotNull IBlockState state) {
        return 0;
    }

    @SuppressWarnings("deprecation")
    @NotNull
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, @NotNull BlockPos pos) {
        pos = pos.add(0, -1, 0);
        return state.withProperty(NORTH, BlocksSoil.isGrass(world.getBlockState(pos.offset(EnumFacing.NORTH))))
                .withProperty(EAST, BlocksSoil.isGrass(world.getBlockState(pos.offset(EnumFacing.EAST))))
                .withProperty(SOUTH, BlocksSoil.isGrass(world.getBlockState(pos.offset(EnumFacing.SOUTH))))
                .withProperty(WEST, BlocksSoil.isGrass(world.getBlockState(pos.offset(EnumFacing.WEST))));
    }

    @Override
    public void randomTick(World world, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull Random rand) {
        if (world.isRemote) return;
        BlockSoilGrass.spreadGrass(world, pos, state, rand);
    }

    @Override
    @NotNull
    public Item getItemDropped(@NotNull IBlockState state, @NotNull Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    @Override
    @NotNull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @NotNull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, NORTH, EAST, WEST, SOUTH);
    }


    @Override
    public IBlockColor getColorHandler() {
        return GrassColorHandler::computeGrassColor;
    }

    @Override
    public IItemColor getItemColorHandler() {
        return (s, i) -> this.getColorHandler().colorMultiplier(this.getDefaultState(), null, null, i);
    }
}
