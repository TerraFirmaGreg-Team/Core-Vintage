package su.terrafirmagreg.framework.manager.content.base.block.api;

import su.terrafirmagreg.api.library.ResourceExtender;
import su.terrafirmagreg.api.library.function.TriFunction;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.manager.content.api.IContentEntry;
import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry.BlockSettings;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockSlab;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockStairs;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockWall;
import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItemBlock;
import su.terrafirmagreg.framework.manager.content.provider.IProviderItemCapability;

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
import net.minecraft.client.renderer.color.IItemColor;
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
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;


@SuppressWarnings("unused")
public interface IBlockEntry extends IContentEntry<BlockSettings, Block> {


  default Item asItem() {
    return Item.getItemFromBlock(asEntry());
  }

  default BlockWall asWall() {
    return BlockUtils.getWallFromBlock(asEntry());
  }

  default BlockSlab asSlab() {
    return BlockUtils.getSlabFromBlock(asEntry());
  }

  default BlockSlab asSlabDouble() {
    return BlockUtils.getSlabDoubleFromBlock(asEntry());
  }

  default BlockStairs asStairs() {
    return BlockUtils.getStairsFromBlock(asEntry());
  }

  @Override
  default void apply() {
    final var settings = getSettings();
    settings.addOreDict(settings.getRegistryKey());
    asEntry()
      .setTranslationKey(settings.getTranslateKey() != null ? settings.getTranslateKey() : ModUtils.localize(settings.getIdentifier()))
      .setResistance(settings.getResistance())
      .setHardness(settings.getHardness())
      .setSoundType(settings.getSoundType())
      .setTickRandomly(settings.isTicksRandomly())
      .setLightOpacity(settings.isOpaque() ? 255 : 0);

    if (!settings.enableStats) {
      asEntry().disableStats();
    }
    if (settings.group != null) {
      asEntry().setCreativeTab(settings.group);
    }
//    if (settings.lightValue != null) {
//      asEntry().setLightLevel(settings.lightValue.apply(null, null, null));
//    }
//
//    if (settings.slipperiness != null) {
//      asEntry().setDefaultSlipperiness(settings.slipperiness.apply(null, null, null));
//    }
    if (settings.harvestTool != null && settings.harvestLevel >= 0) {
      asEntry().setHarvestLevel(settings.harvestTool, settings.harvestLevel);
    }
  }

  @Override
  default void postRegister() {
    final var settings = getSettings();

    TileUtils.addTile(asEntry());
    BlockUtils.addFireInfo(asEntry(), settings.getEncouragement(), settings.getFlammability());
    ModelUtils.addModel(asEntry());
  }

  @Getter
  @SuppressWarnings("deprecation")
  class BlockSettings extends ContentSettings<BlockSettings> {

    protected final List<Object[]> oreDict = new ObjectArrayList<>();
    protected final List<IProviderItemCapability> capability = new ObjectArrayList<>();

    // Block
    protected Block block;

    protected Material material = Material.AIR;
    protected MapColor mapColor = MapColor.AIR;

    protected IProperty<?>[] ignoredProperties = null;
    protected ResourceLocation resource = null;
    protected IStateMapper stateMapper = null;
    protected IBlockColor blockColor = null;
    protected IItemColor itemColor = null;

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
    protected String translateKey;
    protected String translateArgument;

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
    protected boolean enableStats = true;

    protected Function<Block, ? extends BaseItemBlock> itemBlock = BaseItemBlock::new;
    protected Function<Block, ? extends BaseBlockWall> wallBlock;
    protected Function<Block, ? extends BaseBlockSlab> slabSingleBlock;
    protected Function<Block, ? extends BaseBlockSlab> slabDoubleBlock;
    protected Function<Block, ? extends BaseBlockStairs> stairsBlock;

    public static BlockSettings of() {
      final var settings = new BlockSettings();
      settings.capability.clear();
      settings.oreDict.clear();
      return settings;
    }

    public static <B extends Block> BlockSettings of(final B block) {
      return of(block, 0);
    }


    public static <B extends Block> BlockSettings of(final B block, final int meta) {
      final IBlockState state = block.getStateFromMeta(meta);
      final BlockSettings settings = BlockSettings.of();

      if (block instanceof final IBlockEntry entry) {
        var entrySettings = entry.getSettings();
        settings.registryKey = entrySettings.getRegistryKey();
        settings.translateKey = entrySettings.getTranslateKey();
        settings.translateArgument = entrySettings.getTranslateArgument();
        settings.encouragement = entrySettings.getEncouragement();
        settings.flammability = entrySettings.getFlammability();
        settings.resource = entrySettings.getResource();
      }

      settings.block = block;
      settings.material = block.material;
      settings.mapColor = block.blockMapColor;

      settings.collidable = block.isCollidable();
      settings.opaque = block.isOpaqueCube(state);
      settings.fullCube = ($) -> block.isFullCube(state);
      settings.soundType = block.getSoundType();
      settings.lightValue = ($, world, pos) -> state.getLightValue();
      settings.resistance = block.blockResistance;
      settings.hardness = block.blockHardness;
      settings.requiresCorrectTool = !block.material.isToolNotRequired();
      settings.ticksRandomly = block.getTickRandomly();
      settings.slipperiness = ($, world, pos) -> block.slipperiness;
      settings.isAir = block.material == Material.AIR;
      settings.isSuffocating = block::causesSuffocation;
      settings.harvestTool = block.getHarvestTool(state);
      settings.harvestLevel = block.getHarvestLevel(state);
      settings.useNeighborBrightness = block.getUseNeighborBrightness(state);
      settings.enableStats = block.getEnableStats();

      return settings;
    }

    public BlockSettings material(final Material material) {
      this.material = material;
      this.mapColor = material.getMaterialMapColor();
      return this.self();
    }

    public BlockSettings material(final Material material, final MapColor mapColor) {
      this.material = material;
      this.mapColor = mapColor;
      return this.self();
    }

    public BlockSettings mapColor(final EnumDyeColor color) {
      this.mapColor = MapColor.getBlockColor(color);
      return this.self();
    }

    public BlockSettings mapColor(final MapColor mapColor) {
      this.mapColor = mapColor;
      return this.self();
    }

    public BlockSettings translateKey(final String translateKey) {
      if (translateKey != null) {
        this.translateKey = ModUtils.replace(translateKey);
      }
      return this.self();
    }

    public BlockSettings translateKey(final String translateKey, String argument) {
      this.translateKey(translateKey);
      this.translateArgument = argument;
      return this.self();
    }

    public BlockSettings blockColor(final IBlockColor blockColor) {
      this.blockColor = blockColor;
      return this.self();
    }

    public BlockSettings itemColor(final IItemColor itemColor) {
      this.itemColor = itemColor;
      return this.self();
    }

    public BlockSettings noItemBlock() {
      this.itemBlock = null;
      return this.self();
    }

    public BlockSettings itemBlock(final Function<Block, ? extends BaseItemBlock> itemBlock) {
      this.itemBlock = itemBlock;
      return this.self();
    }

    public BlockSettings stairsBlock(final Function<Block, ? extends BaseBlockStairs> stairsBlock) {
      this.stairsBlock = stairsBlock;
      return this.self();
    }

    public BlockSettings stairsBlock() {
      this.stairsBlock = BaseBlockStairs::new;
      return this.self();
    }

    public BlockSettings noStairsBlock() {
      this.stairsBlock = null;
      return this.self();
    }

    public BlockSettings slabBlock(final Function<Block, ? extends BaseBlockSlab> slabBlock, final Function<Block, ? extends BaseBlockSlab> slabDoubleBlock) {
      this.slabSingleBlock = slabBlock;
      this.slabDoubleBlock = slabDoubleBlock;
      return this.self();
    }

    public BlockSettings slabBlock() {
      this.slabDoubleBlock = BaseBlockSlab.Double::new;
      this.slabSingleBlock = BaseBlockSlab.Single::new;
      return this.self();
    }

    public BlockSettings noSlabBlock() {
      this.slabSingleBlock = null;
      this.slabDoubleBlock = null;
      return this.self();
    }

    public BlockSettings wallBlock(final Function<Block, ? extends BaseBlockWall> wallBlock) {
      this.wallBlock = wallBlock;
      return this.self();
    }

    public BlockSettings wallBlock() {
      this.wallBlock = BaseBlockWall::new;
      return this.self();
    }

    public BlockSettings noWallBlock() {
      this.wallBlock = null;
      return this.self();
    }


    /**
     * Включает генерацию всех вариантов (плита, ступеньки, стена)
     */
    public BlockSettings withAllVariants() {
      this.stairsBlock();
      this.wallBlock();
      this.slabBlock();
      return this.self();
    }

    public BlockSettings tile(final Class<? extends TileEntity> tileClass) {
      this.tileClass = tileClass;
      return this.self();
    }


    public BlockSettings color(final Supplier<IBlockColor> colorHandler) {
      this.colorHandler = colorHandler;
      return this.self();
    }

    public <T extends TileEntity> BlockSettings tile(final Class<T> tileClass, final TileEntitySpecialRenderer<T> tileRenderer) {
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

    public BlockSettings hasFullCube(final Function<IBlockState, Boolean> fullCube) {
      this.fullCube = fullCube;
      return this.self();
    }

    public BlockSettings nonCube() {
      this.opaque = false;
      this.fullCube = (state) -> false;
      this.renderLayer = BlockRenderLayer.CUTOUT;
      return this.self();
    }

    public BlockSettings group(final CreativeTabs group) {
      this.group = group;
      return this.self();
    }

    public BlockSettings removeOreDictAll() {
      this.oreDict.clear();
      return this.self();
    }

    public BlockSettings removeOreDict(final Object... oreDict) {
      this.oreDict.remove(oreDict);
      return this.self();
    }

    public BlockSettings addOreDict(final Supplier<Boolean> supplier, final Object... oreDict) {
      if (!supplier.get()) {
        this.oreDict.add(oreDict);
      }
      return this.self();
    }

    public BlockSettings addOreDict(final List<Object[]> oreDict) {
      this.oreDict.addAll(oreDict);
      return this.self();
    }

    public BlockSettings addOreDict(final Object... oreDict) {
      this.oreDict.add(oreDict);
      return this.self();
    }

    public BlockSettings capability(final List<IProviderItemCapability> providers) {
      providers.forEach(this::capability);
      return this.self();
    }

    public BlockSettings capability(final IProviderItemCapability... providers) {
      this.capability.addAll(Arrays.asList(providers));
      return this.self();
    }

    public BlockSettings rarity(final EnumRarity rarity) {
      this.rarity = rarity;
      return this.self();
    }

    public BlockSettings renderLayer(final BlockRenderLayer renderLayer) {
      this.renderLayer = renderLayer;
      return this.self();
    }

    public BlockSettings renderType(final EnumBlockRenderType renderType) {
      this.renderType = renderType;
      return this.self();
    }

    public BlockSettings sound(final SoundType soundType) {
      this.soundType = soundType;
      return this.self();
    }

    public BlockSettings strength(final float strength) {
      this.resistance = strength;
      this.hardness = strength;
      return this.self();
    }

    public BlockSettings resistance(final float resistance) {
      this.resistance = Math.max(0, resistance * 5 / 3);
      return this.self();
    }

    public BlockSettings hardness(final float hardness) {
      this.hardness = hardness;
      return this.self();
    }

    public BlockSettings unbreakable() {
      this.hardness = -1.0F;
      return this.self();
    }

    public BlockSettings harvestLevel(final String harvestTool, final int harvestLevel) {
      this.harvestTool = harvestTool;
      this.harvestLevel = harvestLevel;
      return this.self();
    }

    public BlockSettings fireInfo(final int encouragement, final int flammability) {
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

    public BlockSettings randomTicks(final boolean tickRandomly) {
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

    public BlockSettings lightValue(final TriFunction<IBlockState, IBlockAccess, BlockPos, Integer> lightValue) {
      this.lightValue = lightValue;
      return this.self();
    }

    public BlockSettings lightValue(final Function<IBlockState, Integer> lightValue) {
      this.lightValue = (state, access, pos) -> lightValue.apply(state);
      return this.self();
    }

    public BlockSettings lightValue(final int lightValue) {
      this.lightValue = (state, access, pos) -> lightValue;
      return this.self();
    }

    public BlockSettings lightValue(final float lightValue) {
      this.lightValue = (state, access, pos) -> (int) (15.0F * lightValue);
      return this.self();
    }

    public BlockSettings slipperiness(final TriFunction<IBlockState, IBlockAccess, BlockPos, Float> slipperiness) {
      this.slipperiness = slipperiness;
      return this.self();
    }

    public BlockSettings slipperiness(final Function<IBlockState, Float> slipperiness) {
      this.slipperiness = (state, access, pos) -> slipperiness.apply(state);
      return this.self();
    }

    public BlockSettings slipperiness(final float slipperiness) {
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

    public BlockSettings isSuffocating(final Predicate<IBlockState> isSuffocating) {
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

    public BlockSettings ignoresProperties(final IProperty<?>... properties) {
      this.ignoredProperties = properties;
      return this.self();
    }

    public BlockSettings customResource(final String path) {
      if (path != null) {
        this.resource = ModUtils.resource(path);
      }
      return this.self();
    }

    public BlockSettings customResource(final ResourceLocation resource, final String postfix) {
      if (resource != null && postfix != null) {
        this.resource = ResourceExtender.suffix(resource, postfix);
      }
      return this.self();
    }

    public BlockSettings customResource(final ResourceLocation resource) {
      if (resource != null) {
        this.resource = resource;
      }
      return this.self();
    }

//    public void apply(Block block) {
//      block.setHardness(this.hardness);
//      block.setResistance(this.resistance);
//      block.setSoundType(this.soundType);
//      block.setTickRandomly(this.ticksRandomly);
//      if (!this.enableStats) {
//        block.disableStats();
//      }
//      if (this.group != null) {
//        block.setCreativeTab(this.group);
//      }
//      if (this.lightValue != null) {
//        block.setLightLevel(this.lightValue);
//      }
//      block.setLightOpacity(this.opaque ? 255 : 0);
//      if (this.slipperiness != null) {
//        block.slipperiness = this.slipperiness;
//      }
//      if (this.harvestTool != null && this.harvestLevel >= 0) {
//        block.setHarvestLevel(this.harvestTool, this.harvestLevel);
//      }
//    }

    public BlockSettings stateMapper(final IStateMapper stateMapper) {
      this.stateMapper = stateMapper;
      return this.self();
    }
  }
}
