package su.terrafirmagreg.api.spi.block;

import su.terrafirmagreg.api.registry.IAutoRegProvider;
import su.terrafirmagreg.api.spi.itemblock.BaseItemBlock;
import su.terrafirmagreg.api.util.OreDictUtils;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
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
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import git.jbredwards.fluidlogged_api.api.block.IFluidloggable;
import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings("unused")
@MethodsReturnNonnullByDefault
public interface ISettingsBlock extends IAutoRegProvider, IFluidloggable {

    Settings getSettings();

    // Override Block methods

    default SoundType getSoundType() {
        return getSettings().getSoundType();
    }

    default String getTranslationKey() {
        return "tile." + getSettings().getTranslationKey();
    }

    default float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return getSettings().getHardness().apply(blockState, worldIn, pos);
    }

    default float getExplosionResistance(Entity exploder) {
        return getSettings().getResistance() / 5.0F;
    }

    default boolean isOpaqueCube(IBlockState state) {
        return getSettings() != null && getSettings().isOpaque();
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

    default void onRegisterOreDict() {
        if (!getSettings().getOreDict().isEmpty()) {
            for (var ore : getSettings().getOreDict()) {
                if (ore != null) OreDictUtils.register(getBlock(), ore);
            }
            getSettings().getOreDict().clear();
        }
    }

    // Override IAutoRegProvider methods

    @Override
    default String getName() {
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

    // Override IFluidloggable methods

    @Override
    default boolean isFluidloggable(IBlockState state, World world, BlockPos pos) {
        return isWaterloggable(state, world, pos);
    }

    @Override
    default boolean isFluidValid(IBlockState state, World world, BlockPos pos, Fluid fluid) {
        return isWaterloggable(state, world, pos) && fluid == FluidRegistry.WATER;
    }

    @Override
    default boolean canFluidFlow(IBlockAccess world, BlockPos pos, IBlockState state, EnumFacing side) {
        return isWaterloggable(state, world, pos) && canWaterFlow(world, pos, state, side);
    }

    /** Whether this block can be water-logged or not. */
    default boolean isWaterloggable(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    /** Whether water can flow into/out of this block. */
    default boolean canWaterFlow(IBlockAccess world, BlockPos pos, IBlockState state, EnumFacing side) {
        return state.getBlockFaceShape(world, pos, side) != BlockFaceShape.SOLID;
    }

    // New methods

    default boolean getHasItemSubtypes() {
        return getSettings().isHasItemSubtypes();
    }

    default @Nullable Item getItemBlock() {
        return getSettings().isItemBlock() ? new BaseItemBlock(getBlock()) : null;
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

        protected List<Object[]> oreDict = new ArrayList<>();

        protected final Material material;
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
        protected Size size;
        protected Weight weight;

        protected float resistance;

        protected boolean itemBlock;
        protected boolean canStack;
        protected boolean collidable;
        protected boolean opaque;
        protected boolean fullCube;
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

            this.itemBlock = true;
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

        public Settings noItemBlock() {
            this.itemBlock = false;
            return this;
        }

        public Settings noCollision() {
            this.collidable = false;
            this.opaque = false;
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

        public Settings translationKey(String translationKey) {
            this.translationKey = translationKey;
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
