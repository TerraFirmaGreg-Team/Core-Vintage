package net.dries007.tfc.objects.items.metal;

import su.terrafirmagreg.api.data.enums.Mods;

import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;

@SuppressWarnings("WeakerAccess")
public class ItemGear extends ItemTechMetal {

  public ItemGear(Metal metal, ItemType type) {
    super(metal, type);
  }

  public Metal getSleeveMetal() {
    switch (this.metal.getTier()) {
      default:
      case TIER_0:
      case TIER_I:
      case TIER_II:
        return TFCRegistries.METALS.getValue(new ResourceLocation(Mods.ModIDs.TFC, "tin"));
      case TIER_III:
      case TIER_IV:
        return TFCRegistries.METALS.getValue(new ResourceLocation(Mods.ModIDs.TFC, "brass"));
      case TIER_V:
      case TIER_VI:
        return TFCRegistries.METALS.getValue(new ResourceLocation(Mods.ModIDs.TFC, "steel"));
    }
  }
}
