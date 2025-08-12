package su.terrafirmagreg.framework.manager.registry.base.block.api;

import su.terrafirmagreg.api.library.TriFunction;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;
import su.terrafirmagreg.framework.manager.registry.base.block.api.IBlockEntry.Settings;
import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItemBlock;
import su.terrafirmagreg.framework.manager.registry.provider.IProviderItemCapability;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IRarity;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


@SuppressWarnings("unused")
public interface IBlockEntry extends IRegistryEntry<Settings, Block> {


  default Item asItem() {
    return Item.getItemFromBlock(asEntry());
  }

  @Override
  default void preRegister() {
    var settings = getSettings();
    settings.addOreDict(settings.getRegistryKey());
    asEntry()
      .setResistance(settings.getResistance())
      .setHardness(settings.getHardness())
      .setSoundType(settings.getSoundType())
      .setTickRandomly(settings.isTicksRandomly())
      .setHarvestLevel(settings.getHarvestTool(), settings.getHarvestLevel());
  }

  @Override
  default void postRegister() {
    var settings = getSettings();

    TileUtils.addTile(asEntry());
    BlockUtils.addFireInfo(asEntry(), settings.getEncouragement(), settings.getFlammability());
    ModelUtils.addModel(asEntry());
  }

  @Getter
  @SuppressWarnings("deprecation")
  class Settings extends RegistrySettings<Settings> {

    final List<Object[]> oreDict;
    final List<IProviderItemCapability> capability;


    // Block
    final Material material;
    final MapColor mapColor;

    IProperty<?>[] ignoredProperties = null;
    ResourceLocation resource = null;
    IStateMapper stateMapper = null;

    CreativeTabs group;
    SoundType soundType;

    EnumBlockRenderType renderType;
    TriFunction<IBlockState, IBlockAccess, BlockPos, Integer> lightValue;
    TriFunction<IBlockState, IBlockAccess, BlockPos, Float> slipperiness;
    Predicate<IBlockState> isSuffocating;
    IRarity rarity;
    BlockRenderLayer renderLayer;
    Function<Block, ? extends Item> itemBlock;
    Class<? extends TileEntity> tileClass;
    TileEntitySpecialRenderer<? extends TileEntity> tileRenderer;
    String harvestTool;

    int harvestLevel;
    int encouragement;
    int flammability;

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
    boolean isTranslucent;
    boolean isPassable;
    boolean isAir;
    boolean nonCanStack;
    boolean enableStats;

    protected Settings(Material material, MapColor color) {

      this.oreDict = new ObjectArrayList<>();
      this.capability = new ObjectArrayList<>();

      this.material = material;
      this.mapColor = color;
      this.isAir = material == Material.AIR;

      this.soundType = SoundType.STONE;
      this.lightValue = (state, world, pos) -> 0;
      this.slipperiness = (state, world, pos) -> 0.6F;
      this.isSuffocating = (state) -> state.getMaterial().blocksMovement() && state.isFullCube();
      this.rarity = EnumRarity.COMMON;
      this.renderLayer = BlockRenderLayer.SOLID;
      this.renderType = EnumBlockRenderType.MODEL;
      this.itemBlock = BaseItemBlock::new;
      this.harvestLevel = -1;
      this.encouragement = -1;
      this.flammability = -1;
      this.resistance = 1.0F;
      this.isTranslucent = !material.blocksLight();
      this.useNeighborBrightness = !isAir || isTranslucent;
      this.isPassable = !material.blocksMovement();
      this.canFall = false;
      this.collidable = true;
      this.opaque = true;
      this.fullCube = true;
      this.nonCanStack = false;
      this.hasItemSubtypes = false;
      this.requiresCorrectTool = false;
      this.enableStats = true;
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
      return this.self();
    }

    @SuppressWarnings("unchecked")
    public <B extends Block, I extends Item> Settings itemBlock(B block, Function<B, I> itemBlock) {
      this.itemBlock = (Function<Block, ? extends Item>) itemBlock;
      return this.self();
    }

    @SuppressWarnings("unchecked")
    public <B extends Block, I extends Item> Settings itemBlock(Function<B, I> itemBlock) {
      this.itemBlock = (Function<Block, Item>) itemBlock;
      return this.self();
    }

    public Settings tile(Class<? extends TileEntity> tileClass) {
      this.tileClass = tileClass;
      return this.self();
    }

    public <T extends TileEntity> Settings tile(Class<T> tileClass, TileEntitySpecialRenderer<T> tileRenderer) {
      this.tileClass = tileClass;
      this.tileRenderer = tileRenderer;
      return this.self();
    }

    public Settings noCollision() {
      this.collidable = false;
      this.opaque = false;
      return this.self();
    }

    public Settings nonOpaque() {
      this.opaque = false;
      return this.self();
    }

    public Settings nonFullCube() {
      this.fullCube = false;
      return this.self();
    }

    public Settings nonCube() {
      this.opaque = false;
      this.fullCube = false;
      this.renderLayer = BlockRenderLayer.CUTOUT;
      return this.self();
    }

    public Settings group(CreativeTabs group) {
      this.group = group;
      return this.self();
    }

    public Settings removeOreDictAll() {
      this.oreDict.clear();
      return this.self();
    }

    public Settings removeOreDict(Object... oreDict) {
      this.oreDict.remove(oreDict);
      return this.self();
    }

    public Settings addOreDict(Supplier<Boolean> supplier, Object... oreDict) {
      if (!supplier.get()) {
        this.oreDict.add(oreDict);
      }
      return this.self();
    }

    public Settings addOreDict(List<Object[]> oreDict) {
      this.oreDict.addAll(oreDict);
      return this.self();
    }

    public Settings addOreDict(Object... oreDict) {
      this.oreDict.add(oreDict);
      return this.self();
    }

    public Settings capability(List<IProviderItemCapability> providers) {
      providers.forEach(this::capability);
      return this.self();
    }

    public Settings capability(IProviderItemCapability... providers) {
      this.capability.addAll(Arrays.asList(providers));
      return this.self();
    }

    public Settings rarity(EnumRarity rarity) {
      this.rarity = rarity;
      return this.self();
    }

    public Settings renderLayer(BlockRenderLayer renderLayer) {
      this.renderLayer = renderLayer;
      return this.self();
    }

    public Settings renderType(EnumBlockRenderType renderType) {
      this.renderType = renderType;
      return this.self();
    }

    public Settings sound(SoundType soundType) {
      this.soundType = soundType;
      return this.self();
    }

    public Settings strength(float strength) {
      this.resistance = strength;
      this.hardness = strength;
      return this.self();
    }

    public Settings resistance(float resistance) {
      this.resistance = Math.max(0, resistance * 5 / 3);
      return this.self();
    }

    public Settings hardness(float hardness) {
      this.hardness = hardness;
      return this.self();
    }

    public Settings unbreakable() {
      this.hardness = -1.0F;
      return this.self();
    }

    public Settings harvestLevel(String harvestTool, int harvestLevel) {
      this.harvestTool = harvestTool;
      this.harvestLevel = harvestLevel;
      return this.self();
    }

    public Settings fireInfo(int encouragement, int flammability) {
      this.encouragement = encouragement;
      this.flammability = flammability;
      return this.self();
    }

    public Settings requiresCorrectTool() {
      this.requiresCorrectTool = true;
      return this.self();
    }

    public Settings useNeighborBrightness() {
      this.useNeighborBrightness = true;
      return this.self();
    }

    public Settings randomTicks() {
      this.ticksRandomly = true;
      return this.self();
    }

    public Settings randomTicks(boolean tickRandomly) {
      this.ticksRandomly = tickRandomly;
      return this.self();
    }

    public Settings replaceable() {
      this.isReplaceable = true;
      return this.self();
    }

    public Settings noReplaceable() {
      this.isReplaceable = false;
      return this.self();
    }

    public Settings translucent() {
      this.isTranslucent = true;
      return this.self();
    }

    public Settings passable() {
      this.isPassable = true;
      return this.self();
    }

    public Settings disableStats() {
      this.enableStats = false;
      return this.self();
    }

    public Settings lightValue(TriFunction<IBlockState, IBlockAccess, BlockPos, Integer> lightValue) {
      this.lightValue = lightValue;
      return this.self();
    }

    public Settings lightValue(Function<IBlockState, Integer> lightValue) {
      this.lightValue = (state, access, pos) -> lightValue.apply(state);
      return this.self();
    }

    public Settings lightValue(int lightValue) {
      this.lightValue = (state, access, pos) -> lightValue;
      return this.self();
    }

    public Settings lightValue(float lightValue) {
      this.lightValue = (state, access, pos) -> (int) (15.0F * lightValue);
      return this.self();
    }

    public Settings slipperiness(TriFunction<IBlockState, IBlockAccess, BlockPos, Float> slipperiness) {
      this.slipperiness = slipperiness;
      return this.self();
    }

    public Settings slipperiness(Function<IBlockState, Float> slipperiness) {
      this.slipperiness = (state, access, pos) -> slipperiness.apply(state);
      return this.self();
    }

    public Settings slipperiness(float slipperiness) {
      this.slipperiness = (state, access, pos) -> slipperiness;
      return this.self();
    }

    public Settings hasItemSubtypes() {
      this.hasItemSubtypes = true;
      return this.self();
    }

    public Settings air() {
      this.isAir = true;
      this.useNeighborBrightness = false;
      return this.self();
    }

    public Settings isSuffocating(Predicate<IBlockState> isSuffocating) {
      this.isSuffocating = isSuffocating;
      return this.self();
    }

    public Settings isSuffocating() {
      this.isSuffocating = state -> true;
      return this.self();
    }

    public Settings noSuffocating() {
      this.isSuffocating = state -> false;
      return this.self();
    }

    public Settings ignoresProperties(IProperty<?>... properties) {
      this.ignoredProperties = properties;
      return this.self();
    }

    public Settings customResource(String path) {
      this.resource = ModUtils.resource(path);
      return this.self();
    }

    public Settings customResource(ResourceLocation resource) {
      this.resource = resource;
      return this.self();
    }

    public Settings stateMapper(IStateMapper stateMapper) {
      this.stateMapper = stateMapper;
      return this.self();
    }
  }
}
