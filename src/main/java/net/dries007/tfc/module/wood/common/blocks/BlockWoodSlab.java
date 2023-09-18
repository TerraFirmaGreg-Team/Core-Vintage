package net.dries007.tfc.module.wood.common.blocks;

import net.dries007.tfc.module.wood.api.type.WoodType;
import net.dries007.tfc.module.wood.api.variant.block.IWoodBlock;
import net.dries007.tfc.module.wood.api.variant.block.WoodBlockVariant;
import net.dries007.tfc.client.util.CustomStateMap;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.module.wood.common.WoodStorage;
import net.dries007.tfc.module.wood.common.blocks.itemblocks.ItemBlockWoodSlab;
import net.dries007.tfc.module.wood.api.variant.block.WoodBlockVariants;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

public abstract class BlockWoodSlab extends BlockSlab implements IWoodBlock {
    public static final PropertyEnum<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);
    public final Block block;
    protected Half halfSlab;

    private BlockWoodSlab(WoodType type) {
        super(Material.WOOD);

        IBlockState state = blockState.getBaseState();

        if (!isDouble()) state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);

        this.block = WoodStorage.getWoodBlock(WoodBlockVariants.PLANKS, type);
        useNeighborBrightness = true;

        setLightOpacity(255);
        setDefaultState(state.withProperty(VARIANT, Variant.DEFAULT));
        setCreativeTab(CreativeTabsTFC.WOOD);
        setSoundType(SoundType.STONE);

        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

    @Nonnull
    @Override
    public String getTranslationKey(int meta) {
        return super.getTranslationKey();
    }

    @Nonnull
    @Override
    public IProperty<?> getVariantProperty() {
        return VARIANT; // why is this not null-tolerable ...
    }

    @Nonnull
    @Override
    public Comparable<?> getTypeForItem(@Nonnull ItemStack stack) {
        return Variant.DEFAULT;
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, Variant.DEFAULT);

        if (!this.isDouble()) {
            iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
        }

        return iblockstate;
    }

    @Override
    public int getMetaFromState(@Nonnull IBlockState state) {
        int i = 0;

        if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
            i |= 8;
        }

        return i;
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getBlockHardness(@Nonnull IBlockState blockState, @Nonnull World worldIn, @Nonnull BlockPos pos) {
        return block.getBlockHardness(blockState, worldIn, pos);
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
        return Item.getItemFromBlock(halfSlab);
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getExplosionResistance(@Nonnull Entity exploder) {
        return block.getExplosionResistance(exploder);
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    @Override
    public ItemStack getItem(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        return new ItemStack(halfSlab);
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return this.isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, HALF, VARIANT);
    }

    @Nonnull
    @SuppressWarnings("deprecation")
    @Override
    public SoundType getSoundType() {
        return block.getSoundType();
    }

    public enum Variant implements IStringSerializable {
        DEFAULT;

        @Nonnull
        @Override
        public String getName() {
            return "default";
        }
    }

    public static class Double extends BlockWoodSlab {
        private final WoodBlockVariant variant;
        private final WoodType type;

        public Double(WoodBlockVariant variant, WoodType type) {
            super(type);

            this.variant = variant;
            this.type = type;
        }

        @Override
        public boolean isDouble() {
            return true;
        }

        @Override
        public WoodBlockVariant getBlockVariant() {
            return variant;
        }

        @Override
        public WoodType getType() {
            return type;
        }

        @Override
        public ItemBlock getItemBlock() {
            return null;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void onModelRegister() {
            ModelLoader.setCustomStateMapper(this, new CustomStateMap.Builder().customPath(getResourceLocation()).ignore(BlockWoodSlab.VARIANT).build());
        }
    }

    public static class Half extends BlockWoodSlab {
        public final Double doubleSlab;

        private final WoodBlockVariant variant;
        private final WoodType type;

        public Half(WoodBlockVariant variant, WoodType type) {
            super(type);

            doubleSlab = (Double) WoodStorage.getWoodBlock(WoodBlockVariants.SLAB_DOUBLE, type);
            doubleSlab.halfSlab = this;
            halfSlab = this;

            this.variant = variant;
            this.type = type;

            OreDictionaryHelper.register(this, variant.toString(), type.toString());
        }

        @Override
        public boolean isDouble() {
            return false;
        }

        @Override
        public WoodBlockVariant getBlockVariant() {
            return variant;
        }

        @Override
        public WoodType getType() {
            return type;
        }

        @Override
        public ItemBlock getItemBlock() {
            return new ItemBlockWoodSlab(this, this, this.doubleSlab);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void onModelRegister() {
            ModelLoader.setCustomStateMapper(this,
                    new CustomStateMap.Builder()
                            .customPath(getResourceLocation())
                            .ignore(BlockWoodSlab.VARIANT)
                            .build());

            ModelLoader.setCustomModelResourceLocation(
                    Item.getItemFromBlock(this), 0,
                    new ModelResourceLocation(getResourceLocation(), "normal"));
        }
    }
}
