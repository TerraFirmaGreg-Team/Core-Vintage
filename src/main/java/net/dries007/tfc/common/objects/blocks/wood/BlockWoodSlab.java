package net.dries007.tfc.common.objects.blocks.wood;

import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.wood.IWoodBlock;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.WoodBlockVariant;
import net.dries007.tfc.api.types.wood.variant.WoodBlockVariants;
import net.dries007.tfc.client.util.CustomStateMap;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.items.wood.itemblocks.ItemBlockWoodSlab;
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
    public final Block modelBlock;
    protected Half halfSlab;

    private BlockWoodSlab(WoodType woodType) {
        super(Material.WOOD);

        IBlockState state = blockState.getBaseState();

        if (!isDouble()) state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);

        this.modelBlock = TFCStorage.getWoodBlock(WoodBlockVariants.PLANKS, woodType);
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
        return modelBlock.getBlockHardness(blockState, worldIn, pos);
    }

    @Nonnull
    @Override
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
        return Item.getItemFromBlock(halfSlab);
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getExplosionResistance(@Nonnull Entity exploder) {
        return modelBlock.getExplosionResistance(exploder);
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
        return modelBlock.getSoundType();
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
        private final WoodBlockVariant woodBlockVariant;
        private final WoodType woodType;

        public Double(WoodBlockVariant woodBlockVariant, WoodType woodType) {
            super(woodType);

            this.woodBlockVariant = woodBlockVariant;
            this.woodType = woodType;

            setRegistryName(getRegistryLocation());
            setTranslationKey(getTranslationName());
        }

        @Override
        public boolean isDouble() {
            return true;
        }

        @Override
        public WoodBlockVariant getBlockVariant() {
            return woodBlockVariant;
        }

        @Override
        public WoodType getType() {
            return woodType;
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

        private final WoodBlockVariant woodBlockVariant;
        private final WoodType woodType;

        public Half(WoodBlockVariant woodBlockVariant, WoodType woodType) {
            super(woodType);

            doubleSlab = (Double) TFCStorage.getWoodBlock(WoodBlockVariants.SLAB_DOUBLE, woodType);
            doubleSlab.halfSlab = this;
            halfSlab = this;

            this.woodBlockVariant = woodBlockVariant;
            this.woodType = woodType;

            setRegistryName(getRegistryLocation());
            setTranslationKey(getTranslationName());

            OreDictionaryHelper.register(this, woodBlockVariant.toString(), woodType.toString());
        }

        @Override
        public boolean isDouble() {
            return false;
        }

        @Override
        public WoodBlockVariant getBlockVariant() {
            return woodBlockVariant;
        }

        @Override
        public WoodType getType() {
            return woodType;
        }

        @Override
        public ItemBlock getItemBlock() {
            return new ItemBlockWoodSlab(this, this, this.doubleSlab);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void onModelRegister() {
            ModelLoader.setCustomStateMapper(this, new CustomStateMap.Builder().customPath(getResourceLocation()).ignore(BlockWoodSlab.VARIANT).build());

            for (IBlockState state : this.getBlockState().getValidStates()) {
                ModelLoader.setCustomModelResourceLocation(
                        Item.getItemFromBlock(this),
                        this.getMetaFromState(state), new ModelResourceLocation(getResourceLocation(), "normal"));
            }
        }
    }
}
