package net.dries007.tfc.objects.items.metal;

import su.terrafirmagreg.data.Constants;

import net.minecraft.util.ResourceLocation;


import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;

@SuppressWarnings("WeakerAccess")
public class ItemGear extends ItemTechMetal {

  public ItemGear(Metal metal, ItemType type) {
    super(metal, type);
  }

  public Metal getSleeveMetal() {
    return switch (this.metal.getTier()) {
      case TIER_III, TIER_IV -> TFCRegistries.METALS.getValue(new ResourceLocation(Constants.MODID_TFC, "brass"));
      case TIER_V, TIER_VI -> TFCRegistries.METALS.getValue(new ResourceLocation(Constants.MODID_TFC, "steel"));
      default -> TFCRegistries.METALS.getValue(new ResourceLocation(Constants.MODID_TFC, "tin"));
    };
  }
}
