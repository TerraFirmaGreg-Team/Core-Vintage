package su.terrafirmagreg.api.base.item.spi;

import net.minecraft.item.EnumRarity;
import net.minecraftforge.common.IRarity;


import lombok.Getter;

@Getter
public enum ItemRarity {
  COMMON(EnumRarity.COMMON),
  UNCOMMON(EnumRarity.UNCOMMON),
  RARE(EnumRarity.RARE),
  EPIC(EnumRarity.EPIC);

  private final IRarity rarity;

  ItemRarity(IRarity rarity) {
    this.rarity = rarity;
  }

}
