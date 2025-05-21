package su.terrafirmagreg.mixin;

import su.terrafirmagreg.Tags;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import com.google.common.collect.Lists;
import zone.rong.mixinbooter.IEarlyMixinLoader;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

@IFMLLoadingPlugin.Name(Tags.MOD_NAME + "|EarlyMixin")
@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
public class EarlyMixin implements IEarlyMixinLoader, IFMLLoadingPlugin {

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

    return Lists.newArrayList("mixins.tfg.minecraft.json");
  }
}
