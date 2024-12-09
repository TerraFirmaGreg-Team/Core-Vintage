package su.terrafirmagreg.api.registry.spi;

import net.minecraft.block.Block;

import static su.terrafirmagreg.api.library.json.creator.PatternLoader.createBlockStateJson;

public interface IRegistryData {

  default void onRegisterData(Block settings) {
    createBlockStateJson(settings.getBlockState());
  }
}
