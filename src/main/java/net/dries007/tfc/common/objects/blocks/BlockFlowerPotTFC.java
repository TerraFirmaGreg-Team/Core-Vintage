package net.dries007.tfc.common.objects.blocks;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.Tags;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.plant.type.PlantType;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;


@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockFlowerPotTFC extends Block {
    protected static final AxisAlignedBB FLOWER_POT_AABB = new AxisAlignedBB(0.3125D, 0.0D, 0.3125D, 0.6875D, 0.375D, 0.6875D);

    private static final Map<PlantType, BlockFlowerPotTFC> MAP = new HashMap<>();

    public final PlantType plant;

    public BlockFlowerPotTFC(PlantType plant) {
        super(Material.CIRCUITS);
        this.plant = plant;
        if (MAP.put(plant, this) != null) throw new IllegalStateException("There can only be one.");

        var blockRegistryName = String.format("flowerpot/%s", plant);

        setCreativeTab(CreativeTabsTFC.FLORA);
        setRegistryName(TerraFirmaCraft.identifier(blockRegistryName));
        setTranslationKey(Tags.MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
    }

    public static BlockFlowerPotTFC get(PlantType plant) {
        return MAP.get(plant);
    }

    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FLOWER_POT_AABB;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return face == EnumFacing.DOWN ? super.getBlockFaceShape(worldIn, state, pos, face) : BlockFaceShape.UNDEFINED;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        drops.clear();
        //drops.add(new ItemStack(BlockPlantTFC.get(plant)));
        drops.add(new ItemStack(Items.FLOWER_POT));
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState downState = worldIn.getBlockState(pos.down());
        return super.canPlaceBlockAt(worldIn, pos) && (downState.isTopSolid() || downState.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        if (!worldIn.isSideSolid(pos.down(), EnumFacing.UP)) {
            worldIn.setBlockToAir(pos);
        }
    }
}
