package net.dries007.tfc.module.soil.common.blocks;

import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.common.objects.blocks.itemblocks.ItemBlockTFC;
import net.dries007.tfc.module.soil.StorageSoil;
import net.dries007.tfc.module.soil.api.type.SoilType;
import net.dries007.tfc.module.soil.api.variant.block.ISoilBlock;
import net.dries007.tfc.module.soil.api.variant.block.SoilBlockVariant;
import net.dries007.tfc.module.soil.api.variant.item.SoilItemVariants;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockGrassPath;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockSoilGrassPath extends BlockGrassPath implements ISoilBlock {
    private static final AxisAlignedBB GRASS_PATH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

    private final SoilBlockVariant variant;
    private final SoilType type;

    public BlockSoilGrassPath(SoilBlockVariant variant, SoilType type) {


        this.variant = variant;
        this.type = type;
        this.useNeighborBrightness = true;

        setSoundType(SoundType.PLANT);
        setHardness(2.0F);
        setHarvestLevel("shovel", 0);
        setLightOpacity(255);

        OreDictionaryHelper.register(this, variant.toString(), type.toString());
    }

    @Nonnull
    @Override
    public SoilBlockVariant getBlockVariant() {
        return variant;
    }

    @Nonnull
    @Override
    public SoilType getType() {
        return type;
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTFC(this);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        return GRASS_PATH_AABB;
    }

    @Override
    public void neighborChanged(@Nonnull IBlockState state, @Nonnull World world, BlockPos pos, @Nonnull Block blockIn, BlockPos fromPos) {
        if (fromPos.getY() == pos.getY() + 1) {
            IBlockState up = world.getBlockState(fromPos);
            if (up.isSideSolid(world, fromPos, EnumFacing.DOWN) && FallingBlockManager.getSpecification(up) == null) {
                BlockSoilFarmland.turnToDirt(world, pos);
            }
        }
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, @Nonnull IBlockState state) {
        BlockPos upPos = pos.up();
        IBlockState up = world.getBlockState(upPos);
        if (up.isSideSolid(world, upPos, EnumFacing.DOWN) && FallingBlockManager.getSpecification(up) == null) {
            BlockSoilFarmland.turnToDirt(world, pos);
        }
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
        return StorageSoil.getSoilItem(SoilItemVariants.PILE, type);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public boolean shouldSideBeRendered(@Nonnull IBlockState blockState, @Nonnull IBlockAccess blockAccess, @Nonnull BlockPos pos, EnumFacing side) {
        switch (side) {
            case UP:
                return true;
            case NORTH:
            case SOUTH:
            case WEST:
            case EAST:
                IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
                Block block = iblockstate.getBlock();
                if (iblockstate.isOpaqueCube()) return false;
                if (block instanceof BlockFarmland || block instanceof BlockGrassPath) return false;
            default:
                return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(getResourceLocation(), "soiltype=" + type.toString());
            }
        });


        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this),
                this.getMetaFromState(this.getBlockState().getBaseState()),
                new ModelResourceLocation(getResourceLocation(), "soiltype=" + type.toString()));
    }
}
