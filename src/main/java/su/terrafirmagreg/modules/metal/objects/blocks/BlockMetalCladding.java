package su.terrafirmagreg.modules.metal.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlock;
import su.terrafirmagreg.api.spi.itemblock.BaseItemBlock;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.metal.api.types.type.MetalType;
import su.terrafirmagreg.modules.metal.api.types.variant.block.IMetalBlock;
import su.terrafirmagreg.modules.metal.api.types.variant.block.MetalBlockVariant;
import su.terrafirmagreg.modules.metal.objects.tiles.TEMetalSheet;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import static su.terrafirmagreg.api.data.Blockstates.*;

@SuppressWarnings("deprecation")
public class BlockMetalCladding extends BaseBlock implements IMetalBlock {

    public static final PropertyBool[] FACE_PROPERTIES = new PropertyBool[] { DOWN, UP, NORTH, SOUTH, WEST, EAST };
    private static final AxisAlignedBB[] SHEET_AABB = new AxisAlignedBB[] {
            new AxisAlignedBB(0d, 0.9375d, 0d, 1d, 1d, 1d),
            new AxisAlignedBB(0d, 0d, 0d, 1d, 0.0625d, 1d),
            new AxisAlignedBB(0d, 0d, 0.9375d, 1d, 1d, 1d),
            new AxisAlignedBB(0d, 0d, 0d, 1d, 1d, 0.0625d),
            new AxisAlignedBB(0.9375d, 0d, 0d, 1d, 1d, 1d),
            new AxisAlignedBB(0d, 0d, 0d, 0.0625d, 1d, 1d)
    };

    private final MetalBlockVariant variant;
    private final MetalType type;

    public BlockMetalCladding(MetalBlockVariant variant, MetalType type) {
        super(Settings.of()
                .material(Material.IRON)
                .nonOpaque()
                .nonFullCube()
                .hardness(40F)
                .resistance(25F));

        this.variant = variant;
        this.type = type;

        setHarvestLevel("pickaxe", 0);
        setDefaultState(getBlockState().getBaseState()
                .withProperty(FACE_PROPERTIES[0], false)
                .withProperty(FACE_PROPERTIES[1], false)
                .withProperty(FACE_PROPERTIES[2], false)
                .withProperty(FACE_PROPERTIES[3], false)
                .withProperty(FACE_PROPERTIES[4], false)
                .withProperty(FACE_PROPERTIES[5], false));
    }

    @Override
    public MetalBlockVariant getBlockVariant() {
        return variant;
    }

    @Override
    public MetalType getType() {
        return type;
    }

    @Override
    public @Nullable BaseItemBlock getItemBlock() {
        return null;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        var te = TileUtils.getTile(worldIn, pos, TEMetalSheet.class);
        if (te != null) {
            for (EnumFacing face : EnumFacing.values()) {
                state = state.withProperty(FACE_PROPERTIES[face.getIndex()], te.getFace(face));
            }
        }
        return state;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        TEMetalSheet tile = TileUtils.getTile(source, pos, TEMetalSheet.class);
        int sheets = 0;
        AxisAlignedBB boundingBox = FULL_BLOCK_AABB;
        if (tile != null) {
            for (EnumFacing face : EnumFacing.values()) {
                if (tile.getFace(face)) {
                    if (sheets == 1) {
                        return FULL_BLOCK_AABB;
                    } else {
                        boundingBox = SHEET_AABB[face.getIndex()];
                        sheets++;
                    }
                }
            }
        }
        // This should't ever return null, since it will return FULL_BLOCK_AABB before that
        return boundingBox;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn,
                                      boolean isActualState) {
        var te = TileUtils.getTile(worldIn, pos, TEMetalSheet.class);
        if (te != null) {
            for (EnumFacing face : EnumFacing.values()) {
                if (te.getFace(face)) {
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SHEET_AABB[face.getIndex()]);
                }
            }
        }
    }

    @Override
    public @Nullable AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return getBoundingBox(state, worldIn, pos);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        TEMetalSheet tile = TileUtils.getTile(worldIn, pos, TEMetalSheet.class);
        if (tile != null) {
            for (EnumFacing face : EnumFacing.values()) {
                if (tile.getFace(face) && !worldIn.isSideSolid(pos.offset(face.getOpposite()), face)) {
                    InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(OreDictUnifier
                            .get(OrePrefix.plate, Materials.Iron)
                            .getItem()));
                    tile.setFace(face, false);
                }
            }
            if (tile.getFaceCount() == 0) {
                // Remove the block
                worldIn.setBlockToAir(pos);
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        var te = TileUtils.getTile(worldIn, pos, TEMetalSheet.class);
        if (te != null) te.onBreakBlock();
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public @Nullable RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        TEMetalSheet tile = TileUtils.getTile(worldIn, pos, TEMetalSheet.class);
        if (tile != null) {
            for (EnumFacing face : EnumFacing.values()) {
                if (tile.getFace(face)) {
                    RayTraceResult result = rayTrace(pos, start, end, SHEET_AABB[face.getIndex()]);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACE_PROPERTIES);
    }

    @Override
    public @Nullable TileEntity createTileEntity(World world, IBlockState state) {
        return new TEMetalSheet();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(OreDictUnifier.get(OrePrefix.plate, Materials.Iron).getItem());
    }
}
