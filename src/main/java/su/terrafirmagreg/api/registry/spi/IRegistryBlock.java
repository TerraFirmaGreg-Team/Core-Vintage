package su.terrafirmagreg.api.registry.spi;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;
import su.terrafirmagreg.api.library.types.type.Type;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public interface IRegistryBlock
  extends IRegistryBase, IRegistryData {

  default <B extends Block & IBlockSettings> B block(B block) {
    return this.block(block, block.getItemBlock(), block.getRegistryKey());
  }

  /**
   * Registers a block to the game. This will also set the unlocalized name, and creative tab if tab has been set.
   *
   * @param block     The block to register.
   * @param itemBlock The ItemBlock for the block.
   * @param name      The name to register the block with.
   */
  default <B extends Block, I extends Item> B block(B block, @Nullable I itemBlock, String name) {

    block.setRegistryName(this.getModID(), name);
    block.setTranslationKey(this.getModID() + "." + name.toLowerCase().replace("_", ".").replaceAll("/", "."));
    if (this.getTab() != null) {
      block.setCreativeTab(this.getTab());
    }

    this.getRegistry().getBlocks().add(block);

    if (itemBlock != null) {
      itemBlock.setRegistryName(this.getModID(), name);

      this.getRegistry().getItems().add(itemBlock);
    }

    return block;
  }

  default <B extends Block> B block(B block, String name) {

    return this.block(block, null, name);
  }

  default void block(IBlockSettings block) {

    this.block(block.getBlock(), block.getItemBlock(), block.getRegistryKey());
  }

  default <T extends Type<T>, B extends Block> Map<T, B> block(Set<T> types, Function<T, B> blockCreator) {
    Map<T, B> map = new HashMap<>();
    for (var type : types) {
      var block = blockCreator.apply(type);
      map.put(type, block);
    }

    return this.block(map);
  }

  default <T extends Type<T>, B extends Block> Map<T, B> block(Map<T, B> map) {

    for (var block : map.values()) {
      if (block instanceof IBlockSettings provider) {
        this.block(provider.getBlock(), provider.getItemBlock(), provider.getRegistryKey());
      }
    }

    return map;
  }

//  default <B extends Block, T> Map<T, B> block(Map<T, B> map) {
//    for (var block : map.values()) {
//      if (block instanceof IBlockSettings provider) {
//        this.block(provider.getBlock(), provider.getItemBlock(), provider.getRegistryKey());
//
//
//      }
//    }
//    return map;
//  }

  default <T extends Block> Collection<T> block(Collection<T> collection) {
    for (var block : collection) {
      if (block instanceof IBlockSettings provider) {
        this.block(provider.getBlock(), provider.getItemBlock(), provider.getRegistryKey());
      }
    }
    return collection;
  }


}
