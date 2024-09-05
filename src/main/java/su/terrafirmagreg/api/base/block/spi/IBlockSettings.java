package su.terrafirmagreg.api.base.block.spi;

import su.terrafirmagreg.api.base.item.BaseItemBlock;
import su.terrafirmagreg.api.base.item.spi.ItemRarity;
import su.terrafirmagreg.api.registry.provider.IProviderAutoReg;
import su.terrafirmagreg.api.registry.provider.IProviderBlockState;
import su.terrafirmagreg.api.registry.provider.IProviderModel;
import su.terrafirmagreg.api.registry.provider.IProviderOreDict;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.core.features.falling.FallingBlockManager;

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
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import com.google.common.collect.Lists;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import static su.terrafirmagreg.modules.core.features.falling.FallingBlockManager.Specification;

@SuppressWarnings("unused")
public interface IBlockSettings extends IProviderAutoReg, IProviderBlockState, IProviderModel,
    IProviderOreDict, ICapabilitySize {

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

    return getSettings().isOpaque();
  }

  default boolean isFullCube(IBlockState state) {

    return getSettings().isFullCube();
  }

  default boolean getUseNeighborBrightness(IBlockState state) {
    return getSettings().isUseNeighborBrightness();
  }

  default String getHarvestTool(IBlockState state) {
    return getSettings().getHarvestTool();
  }

  default int getHarvestLevel(IBlockState state) {
    return getSettings().getHarvestLevel();
  }

  default boolean isCollidable() {

    return getSettings().isCollidable();
  }

  default BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos,
      EnumFacing face) {

    return isOpaqueCube(state) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
  }

  default float getSlipperiness(IBlockState state, IBlockAccess world, BlockPos pos,
      @Nullable Entity entity) {

    return getSettings().getSlipperiness().apply(state, world, pos);
  }

  default int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {

    return getSettings().getLightValue().apply(state, world, pos);
  }

  default MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {

    return getSettings().getMapColor();
  }

  @SideOnly(Side.CLIENT)
  default BlockRenderLayer getRenderLayer() {

    return getSettings().getRenderLayer();
  }

  default boolean getTickRandomly() {
    return getSettings().isTicksRandomly();
  }

  // Override IOreDictProvider methods

  @Override
  default void onRegisterOreDict() {
    if (!getSettings().getOreDict().isEmpty()) {
      getSettings().getOreDict().forEach(ore -> OreDictUtils.register(getBlock(), ore));
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

  @Override
  default ResourceLocation getResourceLocation() {
    if (getItemBlock() != null) {
      return ModUtils.resource(getRegistryKey());
    }
    return null;
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

    final List<Object[]> oreDict = Lists.newArrayList();
    final Set<CreativeTabs> tabs = new HashSet<>();

    // Block
    final Material material;
    final MapColor mapColor;

    IProperty<?>[] ignoredProperties;

    String translationKey;
    String registryKey;

    SoundType soundType = SoundType.STONE;
    ContextFunction<Float> hardness = (state, world, pos) -> 1.0F;
    ContextFunction<Integer> lightValue = (state, world, pos) -> 0;
    ContextFunction<Float> slipperiness = (state, world, pos) -> 0.6F;
    Predicate<IBlockState> isSuffocating = (state) -> state.getMaterial().blocksMovement()
        && state.isFullCube();
    IRarity rarity = ItemRarity.COMMON.getRarity();
    BlockRenderLayer renderLayer = BlockRenderLayer.SOLID;
    Size size = Size.SMALL;
    Weight weight = Weight.LIGHT;

    String harvestTool = null;
    int harvestLevel = -1;

    float resistance = 1.0F;

    boolean canStack = true;
    boolean collidable = true;
    boolean opaque = true;
    boolean fullCube = true;
    boolean hasItemBlock = true;
    boolean hasItemSubtypes = false;
    boolean ticksRandomly = false;
    boolean requiresCorrectTool = false;
    boolean useNeighborBrightness;

    boolean isAir;

    Settings(Material material, MapColor color) {

      this.material = material;
      this.mapColor = color;
      this.isAir = material == Material.AIR;
    }

    public static Settings of(Material material, MapColor color) {
      return new Settings(material, color);
    }

    public static Settings of(Material material, EnumDyeColor color) {
      return new Settings(material, MapColor.getBlockColor(color));
    }

    public static Settings of(Material material) {
      return new Settings(material, material.getMaterialMapColor());
    }

    public static <B extends Block> Settings copy(B block) {
      return copy(block, 0);
    }

    public static <B extends Block> Settings copy(B block, int meta) {
      IBlockState state = block.getStateFromMeta(meta);
      Settings settings = Settings.of(block.material, block.blockMapColor);

      settings.collidable = block.isCollidable();
      settings.opaque = block.isOpaqueCube(state);
      settings.fullCube = block.isFullCube(state);
      settings.soundType = block.getSoundType();
      settings.lightValue = ($, world, pos) -> block.getLightValue(state, world, pos);
      settings.resistance = block.blockResistance;
      settings.hardness = ($, world, pos) -> block.blockHardness;
      settings.requiresCorrectTool = !block.material.isToolNotRequired();
      settings.ticksRandomly = block.getTickRandomly();
      settings.slipperiness = ($, world, pos) -> block.slipperiness;
      settings.isAir = block.material == Material.AIR;
      settings.isSuffocating = block::causesSuffocation;
      settings.harvestTool = block.getHarvestTool(state);
      settings.harvestLevel = block.getHarvestLevel(state);

      return settings;
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

    public Settings tab(CreativeTabs tab) {
      this.tabs.add(tab);
      return this;
    }

    public Settings oreDict(@NotNull Object... oreDict) {
      if (oreDict != null) {
        this.oreDict.add(oreDict);
      }
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
      this.hardness = (state, world, pos) -> strength;
      return this;
    }

    public Settings resistance(float resistance) {
      this.resistance = Math.max(0, resistance * 5 / 3);
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

    public Settings fallable(Block block, Specification specification, IBlockState resultingState) {
      var spec = new Specification(specification);
      spec.setResultingState(resultingState);
      return fallable(block, spec);
    }

    public Settings fallable(Block block, Specification specification) {
      if (specification != null) {
        FallingBlockManager.registerFallable(block, specification);
      }
      return this;
    }

    public Settings fallable(IBlockState state, Specification specification,
        IBlockState resultingState) {
      if (specification != null) {
        var spec = new Specification(specification);
        spec.setResultingState(resultingState);
        FallingBlockManager.registerFallable(state, spec);
      }
      return this;
    }

    public Settings fallable(IBlockState state, Specification specification) {
      if (specification != null) {
        FallingBlockManager.registerFallable(state, specification);
      }
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

    public Settings isSuffocating(boolean isSuffocating) {
      this.isSuffocating = state -> isSuffocating;
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
