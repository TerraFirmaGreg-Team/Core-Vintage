package su.terrafirmagreg.modules.soil.api.types.variant.item;

import su.terrafirmagreg.api.library.types.variant.Variant;
import su.terrafirmagreg.api.library.types.variant.item.VariantItem;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.soil.ModuleSoil;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Set;
import java.util.function.BiFunction;

@Getter
public class SoilItemVariant extends VariantItem<SoilItemVariant, SoilType> {

  @Getter
  private static final Set<SoilItemVariant> variants = new ObjectOpenHashSet<>();

  private SoilItemVariant(Builder builder) {
    super(builder.name);

    if (!variants.add(this)) {
      throw new RuntimeException(String.format("Variant: [%s] already exists!", name));
    }

    SoilType.getTypes().forEach(type -> {
      var item = builder.factory.apply(this, type);
      if (map.put(type, item.getItem()) != null) {
        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
      }
      ModuleSoil.REGISTRY.item(item);
    });
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

  public ResourceLocation getTexture(Variant<?, RockType> variant) {
    return ModUtils.resource(String.format("textures/blocks/soil/%s/%s.png", variant, this));
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(String.format("soil.variant.%s.name", this)).getFormattedText();
  }

  public String getRegistryKey(SoilType type) {
    return String.format("soil/%s/%s", this.getName(), type);
  }

  public static class Builder {

    private final String name;
    private BiFunction<SoilItemVariant, SoilType, ISoilItem> factory;

    public Builder(String name) {
      this.name = name;
    }

    public Builder factory(BiFunction<SoilItemVariant, SoilType, ISoilItem> factory) {
      this.factory = factory;
      return this;
    }


    public SoilItemVariant build() {
      return new SoilItemVariant(this);
    }
  }
}
