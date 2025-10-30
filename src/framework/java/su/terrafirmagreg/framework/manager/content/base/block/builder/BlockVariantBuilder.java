package su.terrafirmagreg.framework.manager.content.base.block.builder;

import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockSlab;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockStairs;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockWall;
import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItemBlock;

import net.minecraft.block.Block;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

@Getter
public class BlockVariantBuilder {

  private final Map<String, Function<Block, ?>> variants = new Object2ObjectLinkedOpenHashMap<>();

  // Ключ является и суффиксом, кроме "item"
  public BlockVariantBuilder() {
    variants.put("item", BaseItemBlock::new); // по умолчанию
  }

  public <T> BlockVariantBuilder add(String name, Function<Block, T> factory) {
    variants.put(name, factory);
    return this;
  }

  public BlockVariantBuilder remove(String name) {
    variants.remove(name);
    return this;
  }

  public boolean has(String name) {
    return variants.containsKey(name);
  }

  public Map<String, Function<Block, ?>> getVariants() {
    return Collections.unmodifiableMap(variants);
  }

  /**
   * Базовые строительные блоки — стена, ступень, плита
   */
  public BlockVariantBuilder templateBuildingSet() {
    return this
      .add("wall", BaseBlockWall::new)
      .add("stairs", BaseBlockStairs::new)
      .add("slab_single", BaseBlockSlab.Single::new)
      .add("slab_double", BaseBlockSlab.Double::new);
  }

  public BlockVariantBuilder noItemBlock() {
    return this
      .remove("item");
  }

  public BlockVariantBuilder onlyItemBlock() {
    this.variants.clear();
    return this.add("item", BaseItemBlock::new);
  }


  public BlockVariantBuilder itemBlock(Function<Block, ? extends BaseItemBlock> factory) {
    return this
      .add("item", factory);

  }

  public BlockVariantBuilder stairsBlock() {
    return this
      .add("stairs", BaseBlockStairs::new);

  }

  public BlockVariantBuilder noStairsBlock() {
    return this
      .remove("stairs");

  }

  public BlockVariantBuilder slabBlock() {
    return this
      .add("slab", BaseBlockSlab.Single::new)
      .add("slab_double", BaseBlockSlab.Double::new);

  }

  public BlockVariantBuilder noSlabBlock() {
    return this
      .remove("slab")
      .remove("slab_double");
  }

  public BlockVariantBuilder wallBlock() {
    return this
      .add("wall", BaseBlockWall::new);

  }

  public BlockVariantBuilder noWallBlock() {
    return this
      .remove("wall");

  }
}
