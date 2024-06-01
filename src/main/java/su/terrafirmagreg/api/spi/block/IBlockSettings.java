package su.terrafirmagreg.api.spi.block;

import su.terrafirmagreg.api.registry.provider.IAutoRegProvider;
import su.terrafirmagreg.api.registry.provider.IBlockStateProvider;
import su.terrafirmagreg.api.spi.item.BaseItemBlock;
import su.terrafirmagreg.api.util.OreDictUtils;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import com.google.common.collect.Lists;


import su.terrafirmagreg.api.capabilities.size.spi.Size;

import su.terrafirmagreg.api.capabilities.size.spi.Weight;


import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.List;
import java.util.function.Function;

@SuppressWarnings("unused")
public interface IBlockSettings extends IAutoRegProvider, IBlockStateProvider {

    Settings getSettings();

    // Override Block methods

    default SoundType getSoundType() {

        return getSettings().getSoundType();
    }

    //    default String getTranslationKey() {
    //        return "tile." + getSettings().getTranslationKey();
    //    }

    default float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {

        return getSettings().getHardness().apply(blockState, worldIn, pos);
    }

    default float getExplosionResistance(Entity exploder) {

        return getSettings().getResistance() / 5.0F;
    }

    default boolean isOpaqueCube(IBlockState state) {

        return getSettings().isOpaque();
    }

    default boolean isFullCube(IBlockState state) {

        return getSettings().isFullCube();
    }

    default boolean isCollidable() {

        return getSettings().isCollidable();
    }

    default BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {

        return isOpaqueCube(state) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    default float getSlipperiness(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity entity) {

        return getSettings().getSlipperiness().apply(state, world, pos);
    }

    default int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {

        return getSettings().getLightValue().apply(state, world, pos);
    }

    default MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {

        return getSettings().getMapColor() != null ? getSettings().getMapColor().apply(state, world, pos) : getSettings().getMaterial().getMaterialMapColor();
    }

    @SideOnly(Side.CLIENT)
    default BlockRenderLayer getRenderLayer() {

        return getSettings().getRenderLayer();
    }

    // Override IOreDictProvider methods

    @Override
    default void onRegisterOreDict() {
        if (!getSettings().getOreDict().isEmpty()) {
            for (var ore : getSettings().getOreDict()) {
                OreDictUtils.register(getBlock(), ore);
            }
            getSettings().getOreDict().clear();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    default IStateMapper getStateMapper() {
        var ignored = getSettings().getIgnoredProperties();
        if (ignored != null && ignored.length > 0) {
            return new StateMap.Builder().ignore(ignored).build();
        }
        return new StateMap.Builder().build();
    }

    // Override IAutoRegProvider methods

    @Override
    default String getRegistryKey() {

        return getSettings().getRegistryKey();
    }

    // Override IItemSize methods

    @Override
    default Size getSize(ItemStack stack) {

        return getSettings().getSize();
    }

    @Override
    default Weight getWeight(ItemStack stack) {

        return getSettings().getWeight();
    }

    @Override
    default boolean canStack(ItemStack stack) {

        return getSettings().isCanStack();
    }

    // New methods

    default boolean getHasItemSubtypes() {

        return getSettings().isHasItemSubtypes();
    }

    default @Nullable Item getItemBlock() {

        return getSettings().isHasItemBlock() ? new BaseItemBlock(getBlock()) : null;
    }

    default Block getBlock() {

        return (Block) this;
    }

    default Item asItem() {

        return Item.getItemFromBlock(getBlock());
    }

    @Getter
    @SuppressWarnings("deprecation")
    class Settings {

        protected final List<Object[]> oreDict = Lists.newArrayList();
        // Block
        protected final Material material;
        protected IProperty<?>[] ignoredProperties;
        protected ContextFunction<MapColor> mapColor;
        protected String translationKey;
        protected String registryKey;
        protected CreativeTabs tab;
        protected SoundType soundType;
        protected ContextFunction<Float> hardness;
        protected ContextFunction<Integer> lightValue;
        protected ContextFunction<Float> slipperiness;
        protected EnumRarity rarity;
        protected BlockRenderLayer renderLayer;
        protected boolean collidable;
        protected boolean opaque;
        protected boolean fullCube;
        protected boolean hasItemBlock;
        protected float resistance;

        // Size
        protected Size size;
        protected Weight weight;
        protected boolean canStack;

        protected boolean hasItemSubtypes;

        protected Settings(Material material) {

            this.material = material;
            this.mapColor = (state, world, pos) -> material.getMaterialMapColor();
            this.resistance = 1.0F;
            this.hardness = (state, world, pos) -> 1.0F;
            this.soundType = SoundType.STONE;
            this.lightValue = (state, world, pos) -> 0;
            this.slipperiness = (state, world, pos) -> 0.6F;
            this.rarity = EnumRarity.COMMON;
            this.renderLayer = BlockRenderLayer.SOLID;
            this.size = Size.SMALL;
            this.weight = Weight.LIGHT;

            this.hasItemBlock = true;
            this.canStack = true;
            this.collidable = true;
            this.opaque = true;
            this.fullCube = true;
            this.hasItemSubtypes = false;
        }

        public static Settings of() {
            return new Settings(Material.AIR);
        }

        public static Settings of(Material material) {
            return new Settings(material);
        }

        public static <B extends Block> Settings copy(B block) {
            return copy(block, 0);
        }

        public static <B extends Block> Settings copy(B block, int meta) {
            IBlockState state = block.getStateFromMeta(meta);
            Settings settings = Settings.of(block.getMaterial(state));
            settings.mapColor = ($, world, pos) -> block.getMapColor(state, world, pos);
            settings.tab = block.getCreativeTab();
            settings.collidable = block.isCollidable();
            settings.opaque = block.isOpaqueCube(state);
            settings.fullCube = block.isFullCube(state);
            settings.resistance = block.getExplosionResistance(null) * 5.0F / 3.0F;
            settings.hardness = ($, world, pos) -> block.getBlockHardness(null, null, null);
            settings.soundType = block.getSoundType();
            settings.lightValue = ($, world, pos) -> block.getLightValue(state, world, pos);
            settings.slipperiness = ($, world, pos) -> block.getSlipperiness(state, world, pos, null);
            return settings;
        }

        //        public static Settings copy(BaseBlock block) {
        //            Settings settings = new Settings();
        //            settings.material = block.settings.material;
        //            settings.mapColor = block.settings.mapColor;
        //            settings.tab = block.settings.tab;
        //            settings.collidable = block.settings.collidable;
        //            settings.opaque = block.settings.opaque;
        //            settings.fullCube = block.settings.fullCube;
        //            settings.resistance = block.settings.resistance;
        //            settings.hardness = block.settings.hardness;
        //            settings.soundType = block.settings.soundType;
        //            settings.lightValue = block.settings.lightValue;
        //            settings.slipperiness = block.settings.slipperiness;
        //            return settings;
        //        }

        public Settings mapColor(MapColor mapColor) {
            this.mapColor = (state, world, pos) -> mapColor;
            return this;
        }

        public Settings registryKey(String registryKey) {
            this.registryKey = registryKey;
            return this;
        }

        public Settings translationKey(String translationKey) {
            this.translationKey = translationKey;
            return this;
        }

        public Settings noItemBlock() {
            this.hasItemBlock = false;
            return this;
        }

        public Settings noCollision() {
            this.collidable = false;
            this.opaque = false;
            return this;
        }

        public Settings nonCube() {
            this.opaque = false;
            this.fullCube = false;
            this.renderLayer = BlockRenderLayer.CUTOUT;
            return this;
        }

        public Settings nonOpaque() {
            this.opaque = false;
            return this;
        }

        public Settings nonFullCube() {
            this.fullCube = false;
            return this;
        }

        public Settings creativeTab(CreativeTabs tab) {
            this.tab = tab;
            return this;
        }

        public Settings strength(float strength) {
            resistance = strength;
            hardness = (state, world, pos) -> strength;
            return this;
        }

        public Settings resistance(float resistance) {
            this.resistance = resistance * 3.0F;
            return this;
        }

        public Settings hardness(float hardness) {
            this.hardness = (state, world, pos) -> hardness;
            return this;
        }

        public Settings unbreakable() {
            this.hardness = (state, world, pos) -> -1.0F;
            return this;
        }

        public Settings soundType(SoundType soundType) {
            this.soundType = soundType;
            return this;
        }

        public Settings lightValue(Settings.ContextFunction<Integer> lightValue) {
            this.lightValue = lightValue;
            return this;
        }

        public Settings lightValue(Function<IBlockState, Integer> lightValue) {
            this.lightValue = (state, access, pos) -> lightValue.apply(state);
            return this;
        }

        public Settings lightValue(int lightValue) {
            this.lightValue = (state, access, pos) -> lightValue;
            return this;
        }

        public Settings slipperiness(Settings.ContextFunction<Float> slipperiness) {
            this.slipperiness = slipperiness;
            return this;
        }

        public Settings slipperiness(Function<IBlockState, Float> slipperiness) {
            this.slipperiness = (state, access, pos) -> slipperiness.apply(state);
            return this;
        }

        public Settings slipperiness(float slipperiness) {
            this.slipperiness = (state, access, pos) -> slipperiness;
            return this;
        }

        public Settings hasItemSubtypes() {
            this.hasItemSubtypes = true;
            return this;
        }

        public Settings rarity(EnumRarity rarity) {
            this.rarity = rarity;
            return this;
        }

        public Settings renderLayer(BlockRenderLayer renderLayer) {
            this.renderLayer = renderLayer;
            return this;
        }

        public Settings addOreDict(Object... oreDict) {
            this.oreDict.add(oreDict);
            return this;
        }

        public Settings ignoresProperties(IProperty<?>... properties) {
            this.ignoredProperties = properties;
            return this;
        }

        public Settings weight(Weight weight) {
            this.weight = weight;
            return this;
        }

        public Settings size(Size size) {
            this.size = size;
            return this;
        }

        public Settings nonCanStack() {
            this.canStack = false;
            return this;
        }

        public interface ContextFunction<R> {

            R apply(IBlockState state, IBlockAccess world, BlockPos pos);
        }
    }
}
