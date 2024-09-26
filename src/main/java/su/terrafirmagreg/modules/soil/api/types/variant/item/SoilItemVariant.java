package su.terrafirmagreg.modules.soil.api.types.variant.item;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.data.lib.types.variant.Variant;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;

import net.minecraft.item.Item;
import net.minecraft.util.text.TextComponentTranslation;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

@Getter
public class SoilItemVariant extends Variant<SoilItemVariant> {

  @Getter
  private static final Set<SoilItemVariant> variants = new ObjectOpenHashSet<>();

  private final Map<SoilType, Item> map = new Object2ObjectOpenHashMap<>();
  private BiFunction<SoilItemVariant, SoilType, ? extends Item> factory;

  private SoilItemVariant(String name) {
    super(name);

    if (!variants.add(this)) {
      throw new RuntimeException(String.format("SoilItemVariant: [%s] already exists!", name));
    }
  }

  public static SoilItemVariant builder(String name) {
    return new SoilItemVariant(name);
  }

  public Item get(SoilType type) {
    var item = map.get(type);
    if (item != null) {
      return item;
    }
    throw new RuntimeException(String.format("Item soil is null: %s, %s", this, type));
  }

  public SoilItemVariant factory(BiFunction<SoilItemVariant, SoilType, ? extends Item> factory) {
    this.factory = factory;
    return this;
  }

  public SoilItemVariant build(RegistryManager registry) {
    SoilType.getTypes().forEach(type -> {
      if (map.put(type, factory.apply(this, type)) != null) {
        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
      }
    });
    registry.items(map.values());
    return this;
  }

  public String getRegistryKey(SoilType type) {
    return String.format("soil/%s/%s", this.getName(), type);
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(
      String.format("soil.variant.%s.name", this)).getFormattedText();
  }
}
