package su.terrafirmagreg.api.spi.block;

import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.api.spi.itemblock.BaseItemBlock;
import su.terrafirmagreg.api.util.MathsUtils;

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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;


import git.jbredwards.fluidlogged_api.api.block.IFluidloggable;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.function.Function;

@SuppressWarnings("unused")
public interface ISettingsBlock extends IAutoReg, IFluidloggable {

    default boolean isOpaqueCube(IBlockState state) {
        return getSettings().opaque;
    }

    default boolean isFullBlock(IBlockState state) {
        return isFullCube(state);
    }

    default boolean isFullCube(IBlockState state) {
        return getSettings().fullCube;
    }

    default boolean isNormalCube(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos) {
        return isFullBlock(state);
    }

    default boolean isSideSolid(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull EnumFacing side) {
        return isFullBlock(state);
    }

    default boolean isCollidable() {
        return getSettings().collidable;
    }

    default boolean getHasItemSubtypes() {
        return getSettings().hasItemSubtypes;
    }

    default BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        return isOpaqueCube(state) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    default float getSlipperiness(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable Entity entity) {
        return getSettings().slipperiness.apply(state, world, pos);
    }

    default int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return getSettings().lightValue.apply(state, world, pos);
    }

    default MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
        return getSettings().mapColor != null ? getSettings().mapColor.apply(state, world, pos) : getSettings().material.getMaterialMapColor();
    }

    @Override
    default @Nullable Item getItemBlock() {
        return new BaseItemBlock((Block) this);
    }

    default Item asItem() {
        return Item.getItemFromBlock((Block) this);
    }

    @Override
    default Size getSize(ItemStack stack) {
        return getSettings().size;
    }

    @Override
    default Weight getWeight(ItemStack stack) {
        return getSettings().weight;
    }

    @Override
    default boolean canStack(ItemStack stack) {
        return getSettings().canStack;
    }

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

    default Settings getSettings() {
        return Settings.of();
    }

    static AxisAlignedBB createShape(int x1, int y1, int z1, int x2, int y2, int z2) {
        return MathsUtils.getBoundsForPixels(x1, y1, z1, x2, y2, z2);
    }

    @Getter
    class Settings {

        Material material;
        ContextFunction<MapColor> mapColor;
        String translationKey;
        CreativeTabs tab;
        boolean collidable;
        boolean opaque;
        boolean fullCube;
        boolean hasItemSubtypes;
        float resistance;
        float hardness;
        SoundType soundType;
        ContextFunction<Integer> lightValue;
        ContextFunction<Float> slipperiness;
        EnumRarity rarity;

        Size size;
        Weight weight;
        boolean canStack;

        private Settings() {

            this.material = Material.AIR;
            this.mapColor = (state, world, pos) -> material.getMaterialMapColor();
            this.collidable = true;
            this.opaque = true;
            this.fullCube = true;
            this.hasItemSubtypes = false;
            this.resistance = 1.0F;
            this.hardness = 1.0F;
            this.soundType = SoundType.STONE;
            this.lightValue = (state, world, pos) -> 0;
            this.slipperiness = (state, world, pos) -> 0.6F;
            this.rarity = EnumRarity.COMMON;

            this.size = Size.SMALL;
            this.weight = Weight.LIGHT;
            this.canStack = true;
        }

        public static Settings of() {
            return new Settings();
        }

        public static Settings copy(Block block) {
            return copy(block, 0);
        }

        public static Settings copy(Block block, int meta) {
            IBlockState state = block.getStateFromMeta(meta);
            Settings settings = Settings.of();
            settings.material = block.getMaterial(state);
            settings.mapColor = ($, world, pos) -> block.getMapColor(state, world, pos);
            settings.tab = block.getCreativeTab();
            settings.collidable = block.isCollidable();
            settings.opaque = block.isOpaqueCube(state);
            settings.fullCube = block.isFullCube(state);
            settings.resistance = block.getExplosionResistance(null) * 5.0F / 3.0F;
            settings.hardness = block.getBlockHardness(null, null, null);
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

        public Settings material(Material material) {
            this.material = material;
            this.mapColor = (state, world, pos) -> material.getMaterialMapColor();
            return this;
        }

        public Settings mapColor(MapColor mapColor) {
            this.mapColor = (state, world, pos) -> mapColor;
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
            hardness = strength;
            return this;
        }

        public Settings resistance(float resistance) {
            this.resistance = resistance;
            return this;
        }

        public Settings hardness(float hardness) {
            this.hardness = hardness;
            return this;
        }

        public Settings unbreakable() {
            this.hardness = -1.0F;
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
