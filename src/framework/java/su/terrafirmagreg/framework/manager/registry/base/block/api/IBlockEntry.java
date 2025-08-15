package su.terrafirmagreg.framework.manager.registry.base.block.api;

import su.terrafirmagreg.api.library.TriFunction;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;
import su.terrafirmagreg.framework.manager.registry.base.block.api.IBlockEntry.BlockSettings;
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
import net.minecraft.item.ItemBlock;
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
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


@SuppressWarnings("unused")
public interface IBlockEntry extends IRegistryEntry<BlockSettings, Block> {


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
  class BlockSettings extends RegistrySettings<BlockSettings> {

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


    TriFunction<IBlockState, IBlockAccess, BlockPos, Integer> lightValue;
    TriFunction<IBlockState, IBlockAccess, BlockPos, Float> slipperiness;
    BiFunction<IBlockAccess, BlockPos, Boolean> isReplaceable;
    Predicate<IBlockState> isSuffocating;
    IRarity rarity;
    EnumBlockRenderType renderType;
    BlockRenderLayer renderLayer;
    Function<Block, ? extends ItemBlock> itemBlock;
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
    boolean isTranslucent;
    boolean isPassable;
    boolean isAir;
    boolean nonCanStack;
    boolean enableStats;

    protected BlockSettings(Material material, MapColor color) {

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
      this.isReplaceable = (world, pos) -> world.getBlockState(pos).getMaterial().isReplaceable();
      this.canFall = false;
      this.collidable = true;
      this.opaque = true;
      this.fullCube = true;
      this.nonCanStack = false;
      this.hasItemSubtypes = false;
      this.requiresCorrectTool = false;
      this.enableStats = true;
    }

    public static BlockSettings of(Material material, EnumDyeColor color) {
      return new BlockSettings(material, MapColor.getBlockColor(color));
    }

    public static BlockSettings of(Material material) {
      return new BlockSettings(material, material.getMaterialMapColor());
    }

    public static <B extends Block> BlockSettings of(B block) {
      return of(block, 0);
    }

    public static <B extends Block> BlockSettings of(B block, int meta) {
      IBlockState state = block.getStateFromMeta(meta);
      BlockSettings settings = BlockSettings.of(block.material, block.blockMapColor);

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

    public static BlockSettings of(Material material, MapColor color) {

      return new BlockSettings(material, color);
    }

    public BlockSettings noItemBlock() {
      this.itemBlock = null;
      return this.self();
    }

    public BlockSettings itemBlock(Function<Block, ? extends ItemBlock> itemBlock) {
      this.itemBlock = itemBlock;
      return this.self();
    }

    public BlockSettings tile(Class<? extends TileEntity> tileClass) {
      this.tileClass = tileClass;
      return this.self();
    }

    public <T extends TileEntity> BlockSettings tile(Class<T> tileClass, TileEntitySpecialRenderer<T> tileRenderer) {
      this.tileClass = tileClass;
      this.tileRenderer = tileRenderer;
      return this.self();
    }

    public BlockSettings noCollision() {
      this.collidable = false;
      this.opaque = false;
      return this.self();
    }

    public BlockSettings nonOpaque() {
      this.opaque = false;
      return this.self();
    }

    public BlockSettings nonFullCube() {
      this.fullCube = false;
      return this.self();
    }

    public BlockSettings nonCube() {
      this.opaque = false;
      this.fullCube = false;
      this.renderLayer = BlockRenderLayer.CUTOUT;
      return this.self();
    }

    public BlockSettings group(CreativeTabs group) {
      this.group = group;
      return this.self();
    }

    public BlockSettings removeOreDictAll() {
      this.oreDict.clear();
      return this.self();
    }

    public BlockSettings removeOreDict(Object... oreDict) {
      this.oreDict.remove(oreDict);
      return this.self();
    }

    public BlockSettings addOreDict(Supplier<Boolean> supplier, Object... oreDict) {
      if (!supplier.get()) {
        this.oreDict.add(oreDict);
      }
      return this.self();
    }

    public BlockSettings addOreDict(List<Object[]> oreDict) {
      this.oreDict.addAll(oreDict);
      return this.self();
    }

    public BlockSettings addOreDict(Object... oreDict) {
      this.oreDict.add(oreDict);
      return this.self();
    }

    public BlockSettings capability(List<IProviderItemCapability> providers) {
      providers.forEach(this::capability);
      return this.self();
    }

    public BlockSettings capability(IProviderItemCapability... providers) {
      this.capability.addAll(Arrays.asList(providers));
      return this.self();
    }

    public BlockSettings rarity(EnumRarity rarity) {
      this.rarity = rarity;
      return this.self();
    }

    public BlockSettings renderLayer(BlockRenderLayer renderLayer) {
      this.renderLayer = renderLayer;
      return this.self();
    }

    public BlockSettings renderType(EnumBlockRenderType renderType) {
      this.renderType = renderType;
      return this.self();
    }

    public BlockSettings sound(SoundType soundType) {
      this.soundType = soundType;
      return this.self();
    }

    public BlockSettings strength(float strength) {
      this.resistance = strength;
      this.hardness = strength;
      return this.self();
    }

    public BlockSettings resistance(float resistance) {
      this.resistance = Math.max(0, resistance * 5 / 3);
      return this.self();
    }

    public BlockSettings hardness(float hardness) {
      this.hardness = hardness;
      return this.self();
    }

    public BlockSettings unbreakable() {
      this.hardness = -1.0F;
      return this.self();
    }

    public BlockSettings harvestLevel(String harvestTool, int harvestLevel) {
      this.harvestTool = harvestTool;
      this.harvestLevel = harvestLevel;
      return this.self();
    }

    public BlockSettings fireInfo(int encouragement, int flammability) {
      this.encouragement = encouragement;
      this.flammability = flammability;
      return this.self();
    }

    public BlockSettings requiresCorrectTool() {
      this.requiresCorrectTool = true;
      return this.self();
    }

    public BlockSettings useNeighborBrightness() {
      this.useNeighborBrightness = true;
      return this.self();
    }

    public BlockSettings randomTicks() {
      this.ticksRandomly = true;
      return this.self();
    }

    public BlockSettings randomTicks(boolean tickRandomly) {
      this.ticksRandomly = tickRandomly;
      return this.self();
    }

    public BlockSettings replaceable() {
      this.isReplaceable = (access, pos) -> true;
      return this.self();
    }

    public BlockSettings noReplaceable() {
      this.isReplaceable = (access, pos) -> false;
      return this.self();
    }

    public BlockSettings translucent() {
      this.isTranslucent = true;
      return this.self();
    }

    public BlockSettings passable() {
      this.isPassable = true;
      return this.self();
    }

    public BlockSettings disableStats() {
      this.enableStats = false;
      return this.self();
    }

    public BlockSettings lightValue(TriFunction<IBlockState, IBlockAccess, BlockPos, Integer> lightValue) {
      this.lightValue = lightValue;
      return this.self();
    }

    public BlockSettings lightValue(Function<IBlockState, Integer> lightValue) {
      this.lightValue = (state, access, pos) -> lightValue.apply(state);
      return this.self();
    }

    public BlockSettings lightValue(int lightValue) {
      this.lightValue = (state, access, pos) -> lightValue;
      return this.self();
    }

    public BlockSettings lightValue(float lightValue) {
      this.lightValue = (state, access, pos) -> (int) (15.0F * lightValue);
      return this.self();
    }

    public BlockSettings slipperiness(TriFunction<IBlockState, IBlockAccess, BlockPos, Float> slipperiness) {
      this.slipperiness = slipperiness;
      return this.self();
    }

    public BlockSettings slipperiness(Function<IBlockState, Float> slipperiness) {
      this.slipperiness = (state, access, pos) -> slipperiness.apply(state);
      return this.self();
    }

    public BlockSettings slipperiness(float slipperiness) {
      this.slipperiness = (state, access, pos) -> slipperiness;
      return this.self();
    }

    public BlockSettings hasItemSubtypes() {
      this.hasItemSubtypes = true;
      return this.self();
    }

    public BlockSettings air() {
      this.isAir = true;
      this.useNeighborBrightness = false;
      return this.self();
    }

    public BlockSettings isSuffocating(Predicate<IBlockState> isSuffocating) {
      this.isSuffocating = isSuffocating;
      return this.self();
    }

    public BlockSettings isSuffocating() {
      this.isSuffocating = state -> true;
      return this.self();
    }

    public BlockSettings noSuffocating() {
      this.isSuffocating = state -> false;
      return this.self();
    }

    public BlockSettings ignoresProperties(IProperty<?>... properties) {
      this.ignoredProperties = properties;
      return this.self();
    }

    public BlockSettings customResource(String path) {
      this.resource = ModUtils.resource(path);
      return this.self();
    }

    public BlockSettings customResource(ResourceLocation resource) {
      this.resource = resource;
      return this.self();
    }

    public BlockSettings stateMapper(IStateMapper stateMapper) {
      this.stateMapper = stateMapper;
      return this.self();
    }
  }
}
