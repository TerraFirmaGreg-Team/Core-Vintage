package su.terrafirmagreg.api.registry.spi;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;

import net.minecraft.block.Block;
import net.minecraft.item.Item;


import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;

public interface IRegistryBlock
        extends IRegistryBase {

  default <T extends Block, A> Map<A, T> blocks(Map<A, T> map) {
    for (var block : map.values()) {
      if (block instanceof IBlockSettings provider) {
        this.block(provider.getBlock(), provider.getItemBlock(), provider.getRegistryKey());
      }
    }
    return map;
  }

  default <T extends Block> Collection<T> blocks(Collection<T> collection) {
    for (var block : collection) {
      if (block instanceof IBlockSettings provider) {
        this.block(provider.getBlock(), provider.getItemBlock(), provider.getRegistryKey());
      }
    }
    return collection;
  }

  default <B extends Block & IBlockSettings> B block(B provider) {

    return this.block(provider, provider.getItemBlock(), provider.getRegistryKey());
  }

  default <B extends Block> B block(B block, String name) {

    return this.block(block, null, name);
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
    block.setTranslationKey(
            this.getModID() + "." + name.toLowerCase().replace("_", ".").replaceAll("/", "."));
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

}
