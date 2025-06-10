package su.terrafirmagreg.framework.manager.registry.base.potion.spi;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.registry.base.potion.api.IPotionEntry;

import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;

import com.google.common.collect.ImmutableList;

import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public abstract class BasePotion extends PotionType implements IPotionEntry {

  protected final Settings settings;

  public BasePotion() {
    this(Settings.of());

  }

  public BasePotion(Settings settings) {
    super(settings.getRegistryKey(), settings.getEffect());
    this.settings = settings;

  }

  @Override
  public String getNamePrefixed(String prefix) {
    return prefix + getName();
  }

  @Override
  public List<PotionEffect> getEffects() {
    return ImmutableList.copyOf(settings.getEffect());
  }

  public String getName() {
    return ModUtils.localize(Objects.requireNonNull(this.getRegistryName()));
  }

}
