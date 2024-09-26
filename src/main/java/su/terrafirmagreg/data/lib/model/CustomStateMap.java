package su.terrafirmagreg.data.lib.model;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CustomStateMap extends StateMapperBase {

  private final IProperty<?> name;
  private final String suffix;
  private final List<IProperty<?>> ignored;
  private final String subfolder;
  private final String path;
  private ResourceLocation resourceLocation;

  private CustomStateMap(Builder builder) {
    this.name = builder.name;
    this.suffix = builder.suffix;
    this.ignored = builder.ignored;
    this.resourceLocation = builder.resourceLocation;
    this.subfolder = builder.subfolder;
    this.path = builder.path;
  }

  @Override
  protected @NotNull ModelResourceLocation getModelResourceLocation(IBlockState state) {
    Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap(state.getProperties());
    String path;

    for (IProperty<?> iproperty : this.ignored) {
      map.remove(iproperty);
    }

    if (resourceLocation == null) {
      ResourceLocation registryName = Block.REGISTRY.getNameForObject(state.getBlock());

      if (this.name != null) {
        path = this.removeName(this.name, map);
      } else if (this.path != null) {
        path = this.path;
      } else {
        path = registryName.getPath();
      }

      if (this.subfolder != null) {
        path = subfolder + "/" + path;
      }

      if (this.suffix != null) {
        path = path + this.suffix;
      }
      resourceLocation = new ResourceLocation(registryName.getNamespace(), path);
    }

    return new ModelResourceLocation(resourceLocation, this.getPropertyString(map));

  }

  private <T extends Comparable<T>> String removeName(IProperty<T> property,
                                                      Map<IProperty<?>, Comparable<?>> values) {
    return property.getName((T) values.remove(this.name));
  }

  @SideOnly(Side.CLIENT)
  public static class Builder {

    private final List<IProperty<?>> ignored = Lists.newArrayList();
    private IProperty<?> name;
    private String suffix;
    private ResourceLocation resourceLocation;
    private String subfolder;
    private String path;

    public Builder withName(IProperty<?> name) {
      this.name = name;
      return this;
    }

    public Builder withSuffix(String suffix) {
      this.suffix = suffix;
      return this;
    }

    public Builder ignore(IProperty<?>... ignores) {
      Collections.addAll(this.ignored, ignores);
      return this;
    }

    public Builder subfolder(String subfolder) {
      this.subfolder = subfolder;
      return this;
    }

    public Builder customPath(String path) {
      this.path = path;
      return this;
    }

    public Builder customResource(ResourceLocation resourceLocation) {
      this.resourceLocation = resourceLocation;
      return this;
    }

    public CustomStateMap build() {
      return new CustomStateMap(this);
    }
  }
}
