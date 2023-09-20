package net.dries007.tfc.module.metal.common.blocks;

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.Tags;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.module.core.common.objects.CreativeTabsTFC;
import net.dries007.tfc.module.core.common.objects.blocks.TFCBlock;
import net.dries007.tfc.module.core.common.objects.tileentities.TEMetalSheet;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;


@ParametersAreNonnullByDefault
public class BlockMetalCladding extends TFCBlock implements IHasModel {
    public static final PropertyBool[] FACE_PROPERTIES = new PropertyBool[]{
            PropertyBool.create("down"),
            PropertyBool.create("up"),
            PropertyBool.create("north"),
            PropertyBool.create("south"),
            PropertyBool.create("west"),
            PropertyBool.create("east")
    };
    private static final AxisAlignedBB[] SHEET_AABB = new AxisAlignedBB[]{
            new AxisAlignedBB(0d, 0.9375d, 0d, 1d, 1d, 1d),
            new AxisAlignedBB(0d, 0d, 0d, 1d, 0.0625d, 1d),
            new AxisAlignedBB(0d, 0d, 0.9375d, 1d, 1d, 1d),
            new AxisAlignedBB(0d, 0d, 0d, 1d, 1d, 0.0625d),
            new AxisAlignedBB(0.9375d, 0d, 0d, 1d, 1d, 1d),
            new AxisAlignedBB(0d, 0d, 0d, 0.0625d, 1d, 1d)
    };
    private final ResourceLocation modelLocation;

    public BlockMetalCladding() {
        super(net.minecraft.block.material.Material.IRON);

        this.modelLocation = TerraFirmaCraft.identifier("metal/cladding");

        var blockRegistryName = "metal/cladding";
        setRegistryName(Tags.MOD_ID, blockRegistryName);
        setTranslationKey(Tags.MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
        setCreativeTab(CreativeTabsTFC.METAL);
        setHardness(40F);
        setResistance(25F);
        setHarvestLevel("pickaxe", 0);

        setDefaultState(this.blockState.getBaseState()
                .withProperty(FACE_PROPERTIES[0], false)
                .withProperty(FACE_PROPERTIES[1], false)
                .withProperty(FACE_PROPERTIES[2], false)
                .withProperty(FACE_PROPERTIES[3], false)
                .withProperty(FACE_PROPERTIES[4], false)
                .withProperty(FACE_PROPERTIES[5], false));
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return null;
    }


    @Override
    @SuppressWarnings("deprecation")
    public boolean isTopSolid(IBlockState state) {
        return false;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TEMetalSheet tile = Helpers.getTE(worldIn, pos, TEMetalSheet.class);
        if (tile != null) {
            for (EnumFacing face : EnumFacing.values()) {
                state = state.withProperty(FACE_PROPERTIES[face.getIndex()], tile.getFace(face));
            }
        }
        return state;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isNormalCube(IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        TEMetalSheet tile = Helpers.getTE(source, pos, TEMetalSheet.class);
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
    @Nonnull
    @SuppressWarnings("deprecation")
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }


    @SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        TEMetalSheet tile = Helpers.getTE(worldIn, pos, TEMetalSheet.class);
        if (tile != null) {
            for (EnumFacing face : EnumFacing.values()) {
                if (tile.getFace(face)) {
                    addCollisionBoxToList(pos, entityBox, collidingBoxes, SHEET_AABB[face.getIndex()]);
                }
            }
        }
    }

    @Nullable
    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @SideOnly(Side.CLIENT)
    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return getBoundingBox(state, worldIn, pos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }


    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        TEMetalSheet tile = Helpers.getTE(worldIn, pos, TEMetalSheet.class);
        if (tile != null) {
            for (EnumFacing face : EnumFacing.values()) {
                if (tile.getFace(face) && !worldIn.isSideSolid(pos.offset(face.getOpposite()), face)) {
                    InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(OreDictUnifier.get(OrePrefix.plate, Materials.Iron).getItem()));
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
        TEMetalSheet te = Helpers.getTE(worldIn, pos, TEMetalSheet.class);
        if (te != null) te.onBreakBlock();
        super.breakBlock(worldIn, pos, state);
    }

    @Nullable
    @Override
    @SuppressWarnings("deprecation")
    public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
        TEMetalSheet tile = Helpers.getTE(worldIn, pos, TEMetalSheet.class);
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
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACE_PROPERTIES);
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isSideSolid(IBlockState baseState, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TEMetalSheet();
    }

    @Nonnull
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(OreDictUnifier.get(OrePrefix.plate, Materials.Iron).getItem());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(modelLocation, this.getPropertyString(state.getProperties()));
            }
        });

        for (IBlockState state : this.getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),
                    this.getMetaFromState(state),
                    new ModelResourceLocation(modelLocation, "normal"));
        }
    }
}
