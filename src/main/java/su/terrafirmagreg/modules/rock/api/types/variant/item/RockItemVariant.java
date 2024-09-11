package su.terrafirmagreg.modules.rock.api.types.variant.item;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.data.lib.types.variant.Variant;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;

import net.minecraft.item.Item;
import net.minecraft.util.text.TextComponentTranslation;


import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

@Getter
public class RockItemVariant extends Variant<RockItemVariant> {

  @Getter
  private static final Set<RockItemVariant> itemVariants = new ObjectOpenHashSet<>();

  private final Map<RockType, Item> map = new Object2ObjectOpenHashMap<>();
  private BiFunction<RockItemVariant, RockType, ? extends Item> factory;

  private RockItemVariant(String name) {
    super(name);

    if (!itemVariants.add(this)) {
      throw new RuntimeException(String.format("RockItemVariant: [%s] already exists!", name));
    }
  }

  public static RockItemVariant builder(String name) {
    return new RockItemVariant(name);
  }

  public Item get(RockType type) {
    var item = this.map.get(type);
    if (item != null) {
      return item;
    }
    throw new RuntimeException(String.format("Item rock is null: %s, %s", this, type));
  }

  public RockItemVariant setFactory(BiFunction<RockItemVariant, RockType, ? extends Item> factory) {
    this.factory = factory;
    return this;
  }

  public RockItemVariant build(RegistryManager registry) {
    RockType.getTypes().forEach(type -> {
      if (map.put(type, factory.apply(this, type)) != null) {
        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
      }
    });
    registry.items(map.values());
    return this;
  }

  public String getRegistryKey(RockType type) {
    return String.format("rock/%s/%s", this, type);
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(
            String.format("rock.variant.%s.name", this)).getFormattedText();
  }
}
