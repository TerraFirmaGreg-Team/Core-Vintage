package su.terrafirmagreg.framework.manager.registry.base.block.api;

import su.terrafirmagreg.api.library.function.TriFunction;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;
import su.terrafirmagreg.framework.manager.registry.base.block.api.IBlockEntry.BlockSettings;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockSlab;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockStairs;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockWall;
import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItemBlock;
import su.terrafirmagreg.framework.manager.registry.provider.IProviderItemCapability;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.color.IBlockColor;
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

  default BlockStairs asStairs() {
    return BaseBlockStairs.getStairsFromBlock(asEntry());
  }

  default BlockWall asWall() {
    return BaseBlockWall.getWallFromBlock(asEntry());
  }

  default BlockSlab asSlab() {
    return BaseBlockSlab.getSlabFromBlock(asEntry());
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

    protected final List<Object[]> oreDict = new ObjectArrayList<>();
    protected final List<IProviderItemCapability> capability = new ObjectArrayList<>();

    // Block
    protected Material material = Material.AIR;
    protected MapColor mapColor = MapColor.AIR;

    protected IProperty<?>[] ignoredProperties = null;
    protected ResourceLocation resource = null;
    protected IStateMapper stateMapper = null;

    protected CreativeTabs group;
    protected SoundType soundType = SoundType.STONE;

    protected TriFunction<IBlockState, IBlockAccess, BlockPos, Integer> lightValue = (state, world, pos) -> 0;
    protected TriFunction<IBlockState, IBlockAccess, BlockPos, Float> slipperiness = (state, world, pos) -> 0.6F;
    protected BiFunction<IBlockAccess, BlockPos, Boolean> isReplaceable = (world, pos) -> world.getBlockState(pos).getMaterial().isReplaceable();
    protected Function<IBlockState, Boolean> fullCube = (state) -> true;
    protected Predicate<IBlockState> isSuffocating = (state) -> state.getMaterial().blocksMovement() && state.isFullCube();
    protected IRarity rarity = EnumRarity.COMMON;
    protected EnumBlockRenderType renderType = EnumBlockRenderType.MODEL;
    protected BlockRenderLayer renderLayer = BlockRenderLayer.SOLID;
    protected Class<? extends TileEntity> tileClass;
    protected Supplier<IBlockColor> colorHandler;
    protected TileEntitySpecialRenderer<? extends TileEntity> tileRenderer;
    protected String harvestTool;
    protected int harvestLevel = -1;
    protected int encouragement = -1;
    protected int flammability = -1;
    protected float resistance = 1.0F;
    protected float hardness;
    protected boolean canFall = false;
    protected boolean collidable = true;
    protected boolean opaque = true;

    protected boolean hasItemSubtypes = false;
    protected boolean ticksRandomly;
    protected boolean requiresCorrectTool = false;
    protected boolean isAir = material == Material.AIR;
    protected boolean isTranslucent = !material.blocksLight();
    protected boolean useNeighborBrightness = !isAir || isTranslucent;
    protected boolean isPassable = !material.blocksMovement();

    protected boolean nonCanStack = false;
    protected Function<Block, ? extends ItemBlock> itemBlock = BaseItemBlock::new;
    protected Function<Block, ? extends BaseBlockStairs> stairsBlock;
    protected Function<Block, ? extends BlockWall> wallBlock;
    protected Function<Block, ? extends BlockSlab> slabBlock;
    protected Function<Block, ? extends BlockSlab> slabDoubleBlock;

    protected boolean enableStats = true;

    public static BlockSettings of() {
      var settings = new BlockSettings();
      settings.capability.clear();
      settings.oreDict.clear();
      return settings;
    }

    public static <B extends Block> BlockSettings of(B block) {
      return of(block, 0);
    }

    public static <B extends Block> BlockSettings of(B block, int meta) {
      IBlockState state = block.getStateFromMeta(meta);
      BlockSettings settings = BlockSettings.of();

      settings.material = block.material;
      settings.mapColor = block.blockMapColor;
      settings.collidable = block.isCollidable();
      settings.opaque = block.isOpaqueCube(state);
      settings.fullCube = ($) -> block.isFullCube(state);
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

    public BlockSettings material(Material material) {
      this.material = material;
      this.mapColor = material.getMaterialMapColor();
      return this.self();
    }

    public BlockSettings material(Material material, MapColor mapColor) {
      this.material = material;
      this.mapColor = mapColor;
      return this.self();
    }

    public BlockSettings mapColor(EnumDyeColor color) {
      this.mapColor = MapColor.getBlockColor(color);
      return this.self();
    }

    public BlockSettings mapColor(MapColor mapColor) {
      this.mapColor = mapColor;
      return this.self();
    }

    public BlockSettings noItemBlock() {
      this.itemBlock = null;
      return this.self();
    }

    public BlockSettings itemBlock(Function<Block, ? extends ItemBlock> itemBlock) {
      this.itemBlock = itemBlock;
      return this.self();
    }

    public BlockSettings stairsBlock(Function<Block, ? extends BaseBlockStairs> stairsBlock) {
      this.stairsBlock = stairsBlock;
      return this.self();
    }

    public BlockSettings wallBlock(Function<Block, ? extends BlockWall> wallBlock) {
      this.wallBlock = wallBlock;
      return this.self();
    }

    public BlockSettings slabDoubleBlock(Function<Block, ? extends BlockSlab> slabDoubleBlock) {
      this.slabDoubleBlock = slabDoubleBlock;
      return this.self();
    }

    public BlockSettings slabBlock(Function<Block, ? extends BlockSlab> slabBlock) {
      this.slabBlock = slabBlock;
      return this.self();
    }


    public BlockSettings tile(Class<? extends TileEntity> tileClass) {
      this.tileClass = tileClass;
      return this.self();
    }


    public BlockSettings color(Supplier<IBlockColor> colorHandler) {
      this.colorHandler = colorHandler;
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
      this.fullCube = (state) -> false;
      return this.self();
    }

    public BlockSettings hasFullCube(Function<IBlockState, Boolean> fullCube) {
      this.fullCube = fullCube;
      return this.self();
    }

    public BlockSettings nonCube() {
      this.opaque = false;
      this.fullCube = (state) -> false;
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

    public BlockSettings noPassable() {
      this.isPassable = false;
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
