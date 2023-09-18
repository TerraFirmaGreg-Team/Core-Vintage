package net.dries007.tfc.module.wood.common.blocks;

import net.dries007.tfc.module.wood.api.type.WoodType;
import net.dries007.tfc.module.wood.api.variant.block.IWoodBlock;
import net.dries007.tfc.module.wood.api.variant.block.WoodBlockVariant;
import net.dries007.tfc.client.util.CustomStateMap;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.module.wood.common.blocks.itemblocks.ItemBlockWoodSapling;
import net.dries007.tfc.common.objects.tileentities.TETickCounter;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class BlockWoodSapling extends BlockBush implements IWoodBlock {

    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.1, 0, 0.1, 0.9, 0.9, 0.9);
    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockWoodSapling(WoodBlockVariant variant, WoodType type) {
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
    public WoodBlockVariant getBlockVariant() {
        return variant;
    }

    @Override
    public WoodType getType() {
        return type;
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockWoodSapling(this);
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


    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        return SAPLING_AABB;
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
