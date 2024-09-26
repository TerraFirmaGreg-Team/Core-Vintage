package su.terrafirmagreg.mixin;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import zone.rong.mixinbooter.IEarlyMixinLoader;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.Name("EarlyMixinLoader")
public class EarlyMixinLoader implements IEarlyMixinLoader, IFMLLoadingPlugin {

  @Override
  public String[] getASMTransformerClass() {
    return null;
  }

  @Override
  public String getModContainerClass() {
    return null;
  }

  @Nullable
  @Override
  public String getSetupClass() {
    return null;
  }

  @Override
  public void injectData(Map<String, Object> data) {
  }

  @Override
  public String getAccessTransformerClass() {
    return null;
  }

  @Override
  public List<String> getMixinConfigs() {
    List<String> configs = new ArrayList<>();

    configs.add("mixins.tfg.json");

    return configs;
  }
}
