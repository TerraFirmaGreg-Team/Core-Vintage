package su.terrafirmagreg.api.base.object.item.api;


import su.terrafirmagreg.api.base.object.item.api.IItemSettings.Settings;
import su.terrafirmagreg.api.library.IBaseSettings;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.IRarity;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public interface IItemSettings extends IBaseSettings<Settings> {

  @Getter
  class Settings extends BaseSettings<Settings> {

    final List<Object[]> oreDict = new ArrayList<>();
    final Set<CreativeTabs> groups = new ObjectOpenHashSet<>();

    ResourceLocation resource;

    boolean canStack = true;
    boolean isFireResistant;
    int maxDamage;
    int maxCount = 64;

    IRarity rarity = EnumRarity.COMMON;
    Size size = Size.SMALL;
    Weight weight = Weight.LIGHT;

    protected Settings() {}

    public static Settings of() {
      return new Settings();
    }

    public Settings maxDamage(int maxDamage) {
      if (maxDamage < 1) {throw new IllegalArgumentException("Maximum stack size must be greater than zero!");}
      if (maxDamage > 1 && this.maxCount != 0) {throw new RuntimeException("An item cannot have durability and be stackable!");}

      this.maxDamage = maxDamage;
      return this;
    }

    public Settings maxCount(int maxCount) {
      if (this.maxDamage != 64 && this.maxDamage > 1) {throw new RuntimeException("An item cannot have durability and be stackable!");}

      this.maxCount = maxCount;
      this.maxDamage = 1;
      return this;
    }

    public Settings group(CreativeTabs group) {
      if (group != null) {
        this.groups.add(group);
      }
      return this;
    }

    public Settings rarity(IRarity rarity) {
      this.rarity = rarity;
      return this;
    }

    public Settings customResource(String path) {
      this.resource = ModUtils.resource(path);
      return this;
    }

    public Settings oreDict(Object... oreDict) {
      this.oreDict.add(oreDict);
      return this;
    }

    public Settings weight(Weight weight) {
      this.weight = weight;
      return this;
    }

    public Settings size(Size size) {
      this.size = size;
      return this;
    }

    public Settings notCanStack() {
      this.canStack = false;
      return this;
    }

    public Settings fireResistant() {
      this.isFireResistant = true;
      return this;
    }
  }
}
