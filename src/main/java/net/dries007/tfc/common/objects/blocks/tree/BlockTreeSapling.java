package net.dries007.tfc.common.objects.blocks.tree;

import net.dries007.tfc.api.types.tree.ITreeBlock;
import net.dries007.tfc.api.types.tree.type.TreeType;
import net.dries007.tfc.api.types.tree.variant.block.TreeBlockVariant;
import net.dries007.tfc.api.util.IGrowingPlant;
import net.dries007.tfc.client.util.CustomStateMap;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.items.tree.itemblocks.ItemBlockTreeSapling;
import net.dries007.tfc.common.objects.tileentities.TETickCounter;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;


public class BlockTreeSapling extends BlockBush implements ITreeBlock, IGrowable, IGrowingPlant {

    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.1, 0, 0.1, 0.9, 0.9, 0.9);
    private final TreeBlockVariant variant;
    private final TreeType type;

    public BlockTreeSapling(TreeBlockVariant variant, TreeType type) {
        this.variant = variant;
        this.type = type;

        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());
        setCreativeTab(CreativeTabsTFC.WOOD);
        setHardness(0.0F);

        Blocks.FIRE.setFireInfo(this, 5, 20);
        OreDictionaryHelper.register(this, variant.toString());
        OreDictionaryHelper.register(this, variant.toString(), type.toString());
    }


    @Override
    public TreeBlockVariant getBlockVariant() {
        return variant;
    }

    @Override
    public TreeType getType() {
        return type;
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTreeSapling(this);
    }


    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public void onBlockPlacedBy(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityLivingBase placer, @Nonnull ItemStack stack) {
        TETickCounter te = Helpers.getTE(worldIn, pos, TETickCounter.class);
        if (te != null) {
            te.resetCounter();
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this);
    }

    @Override
    @Nonnull
    public Block.EnumOffsetType getOffsetType() {
        return Block.EnumOffsetType.XZ;
    }

    @Override
    public boolean hasTileEntity(@Nonnull IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TETickCounter();
    }

    @Override
    public void updateTick(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random random) {
        super.updateTick(world, pos, state, random);

//        if (!world.isRemote) {
//            TETickCounter te = Helpers.getTE(world, pos, TETickCounter.class);
//            if (te != null) {
//                long days = te.getTicksSinceUpdate() / ICalendar.TICKS_IN_DAY;
//                if (days > type.getMinGrowthTime()) {
//                    grow(world, random, pos, state);
//                }
//            }
//        }
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        return SAPLING_AABB;
    }

    @Nonnull
    public EnumPlantType getPlantType(@Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        return EnumPlantType.Plains;
    }

    @Override
    public boolean canGrow(@Nonnull World world, @Nonnull BlockPos blockPos, @Nonnull IBlockState iBlockState, boolean b) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(@Nonnull World world, @Nonnull Random random, @Nonnull BlockPos blockPos, @Nonnull IBlockState iBlockState) {
        return false;
    }

    @Override
    public void grow(@Nonnull World world, @Nonnull Random random, @Nonnull BlockPos blockPos, @Nonnull IBlockState blockState) {
        type.makeTree(world, blockPos, random, false);
    }

    @Override
    public GrowthStatus getGrowingStatus(IBlockState state, World world, BlockPos pos) {
        return GrowthStatus.GROWING;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this,
                new CustomStateMap.Builder()
                        .customPath(getResourceLocation())
                        .customVariant("type=" + getType())
                        .build());

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getResourceLocation(),
                        "type=" + getType()));
    }
}
