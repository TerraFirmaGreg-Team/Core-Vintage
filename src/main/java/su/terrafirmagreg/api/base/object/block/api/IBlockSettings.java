package su.terrafirmagreg.api.base.object.block.api;

import su.terrafirmagreg.api.base.object.block.api.IBlockSettings.Settings;
import su.terrafirmagreg.api.base.object.item.spi.BaseItemBlock;
import su.terrafirmagreg.api.library.IBaseSettings;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.registry.api.provider.IProviderItemCapability;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IRarity;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


@SuppressWarnings("unused")
public interface IBlockSettings extends IBaseSettings<Settings> {


  default Block asBlock() {
    return (Block) this;
  }

  default Item asItem() {
    return Item.getItemFromBlock(asBlock());
  }

  @Getter
  @SuppressWarnings("deprecation")
  class Settings extends BaseSettings<Settings> {

    final List<Object[]> oreDict;
    final Set<IProviderItemCapability> capability;
    final Set<CreativeTabs> groups;

    // Block
    final Material material;
    final MapColor mapColor;

    IProperty<?>[] ignoredProperties = null;
    ResourceLocation resource = null;

    SoundType soundType;

    ContextFunction<Integer> lightValue;
    ContextFunction<Float> slipperiness;
    Predicate<IBlockState> isSuffocating;
    IRarity rarity;
    BlockRenderLayer renderLayer;
    Function<Block, Item> itemBlock;


    String harvestTool;
    int harvestLevel;

    float resistance;
    float hardness;

    boolean canFall;
    boolean collidable;
    boolean opaque;
    boolean fullCube;
    boolean hasItemSubtypes;
    boolean ticksRandomly;
    boolean requiresCorrectTool;
    boolean useNeighborBrightness;
    boolean isReplaceable;

    boolean isAir;

    protected Settings(Material material, MapColor color) {

      this.oreDict = new ArrayList<>();
      this.capability = new HashSet<>();
      this.groups = new HashSet<>();

      this.material = material;
      this.mapColor = color;
      this.isAir = material == Material.AIR;

      this.soundType = SoundType.STONE;
      this.lightValue = (state, world, pos) -> 0;
      this.slipperiness = (state, world, pos) -> 0.6F;
      this.isSuffocating = (state) -> state.getMaterial().blocksMovement() && state.isFullCube();
      this.rarity = EnumRarity.COMMON;
      this.renderLayer = BlockRenderLayer.SOLID;
      this.itemBlock = BaseItemBlock::new;
      this.harvestLevel = -1;
      this.resistance = 1.0F;
      this.canFall = false;
      this.collidable = true;
      this.opaque = true;
      this.fullCube = true;
      this.hasItemSubtypes = false;
      this.requiresCorrectTool = false;
    }

    public static Settings of(Material material, EnumDyeColor color) {
      return new Settings(material, MapColor.getBlockColor(color));
    }

    public static Settings of(Material material) {
      return new Settings(material, material.getMaterialMapColor());
    }

    public static <B extends Block> Settings of(B block) {
      return of(block, 0);
    }

    public static <B extends Block> Settings of(B block, int meta) {
      IBlockState state = block.getStateFromMeta(meta);
      Settings settings = Settings.of(block.material, block.blockMapColor);

      settings.collidable = block.isCollidable();
      settings.opaque = block.isOpaqueCube(state);
      settings.fullCube = block.isFullCube(state);
      settings.soundType = block.getSoundType();
      settings.lightValue = ($, world, pos) -> block.getLightValue(state, world, pos);
      settings.resistance = block.blockResistance;
      settings.hardness = block.getBlockHardness(null, null, null);
      settings.requiresCorrectTool = !block.material.isToolNotRequired();
      settings.ticksRandomly = block.getTickRandomly();
      settings.slipperiness = ($, world, pos) -> block.slipperiness;
      settings.isAir = block.material == Material.AIR;
      settings.isSuffocating = block::causesSuffocation;
      settings.harvestTool = block.getHarvestTool(state);
      settings.harvestLevel = block.getHarvestLevel(state);

      return settings;
    }

    public static Settings of(Material material, MapColor color) {
      return new Settings(material, color);
    }

    public Settings noItemBlock() {
      this.itemBlock = null;
      return this;
    }

    @SuppressWarnings("unchecked")
    public <B extends Block> Settings itemBlock(Function<B, Item> itemBlock) {
      this.itemBlock = (Function<Block, Item>) itemBlock;
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

    public Settings nonCube() {
      this.opaque = false;
      this.fullCube = false;
      this.renderLayer = BlockRenderLayer.CUTOUT;
      return this;
    }

    public Settings group(List<CreativeTabs> group) {
      group.forEach(this::group);
      return this;
    }

    public Settings group(CreativeTabs... group) {
      Collections.addAll(this.groups, group);
      return this;
    }

    public Settings group(CreativeTabs group) {
      if (group != null) {
        this.groups.add(group);
      }
      return this;
    }

    public Settings oreDict(Supplier<Boolean> supplier, Object... oreDict) {
      if (!supplier.get()) {
        this.oreDict.add(oreDict);
      }
      return this;
    }

    public Settings oreDict(List<Object[]> oreDict) {
      this.oreDict.addAll(oreDict);
      return this;
    }

    public Settings oreDict(Object... oreDict) {
      this.oreDict.add(oreDict);
      return this;
    }

    public Settings capability(List<IProviderItemCapability> providers) {
      providers.forEach(this::capability);
      return this;
    }

    public Settings capability(IProviderItemCapability... providers) {
      this.capability.addAll(Arrays.asList(providers));
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

    public Settings sound(SoundType soundType) {
      this.soundType = soundType;
      return this;
    }

    public Settings strength(float strength) {
      this.resistance = strength;
      this.hardness = strength;
      return this;
    }

    public Settings resistance(float resistance) {
      this.resistance = Math.max(0, resistance * 5 / 3);
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

    public Settings harvestLevel(String harvestTool, int harvestLevel) {
      this.harvestTool = harvestTool;
      this.harvestLevel = harvestLevel;
      return this;
    }

    public Settings requiresCorrectTool() {
      this.requiresCorrectTool = true;
      return this;
    }

    public Settings useNeighborBrightness() {
      this.useNeighborBrightness = true;
      return this;
    }

    public Settings randomTicks() {
      this.ticksRandomly = true;
      return this;
    }

    public Settings randomTicks(boolean tickRandomly) {
      this.ticksRandomly = tickRandomly;
      return this;
    }

    public Settings replaceable() {
      this.isReplaceable = true;
      return this;
    }

    public Settings noReplaceable() {
      this.isReplaceable = false;
      return this;
    }

    public Settings lightValue(ContextFunction<Integer> lightValue) {
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

    public Settings lightValue(float lightValue) {
      this.lightValue = (state, access, pos) -> (int) (15.0F * lightValue);
      return this;
    }

    public Settings slipperiness(ContextFunction<Float> slipperiness) {
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

    public Settings air() {
      this.isAir = true;
      this.useNeighborBrightness = false;
      return this;
    }

    public Settings isSuffocating(Predicate<IBlockState> isSuffocating) {
      this.isSuffocating = isSuffocating;
      return this;
    }

    public Settings isSuffocating() {
      this.isSuffocating = state -> true;
      return this;
    }

    public Settings noSuffocating() {
      this.isSuffocating = state -> false;
      return this;
    }

    public Settings ignoresProperties(IProperty<?>... properties) {
      this.ignoredProperties = properties;
      return this;
    }

    public Settings customResource(String path) {
      this.resource = ModUtils.resource(path);
      return this;
    }

    public Settings customResource(ResourceLocation resourceLocation) {
      this.resource = resourceLocation;
      return this;
    }

    public Settings weight(Weight weight) {
      return this;
    }

    public Settings size(Size size) {
      return this;
    }


    public Settings nonCanStack() {
      return this;
    }

    public interface ContextFunction<R> {

      R apply(IBlockState state, IBlockAccess world, BlockPos pos);
    }
  }
}
