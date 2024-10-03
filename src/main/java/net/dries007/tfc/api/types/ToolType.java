package net.dries007.tfc.api.types;

import su.terrafirmagreg.modules.rock.api.types.category.RockCategory;

import net.minecraft.item.Item;

import net.dries007.tfc.objects.items.tools.ItemRockAxe;
import net.dries007.tfc.objects.items.tools.ItemRockHammer;
import net.dries007.tfc.objects.items.tools.ItemRockHoe;
import net.dries007.tfc.objects.items.tools.ItemRockJavelin;
import net.dries007.tfc.objects.items.tools.ItemRockKnife;
import net.dries007.tfc.objects.items.tools.ItemRockShovel;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.function.Function;

public enum ToolType {
  AXE(ItemRockAxe::new, " X   ", "XXXX ", "XXXXX", "XXXX ", " X   "),
  SHOVEL(ItemRockShovel::new, "XXX", "XXX", "XXX", "XXX", " X "),
  HOE(ItemRockHoe::new, "XXXXX", "   XX"),
  KNIFE(ItemRockKnife::new, "X ", "XX", "XX", "XX", "XX"),
  JAVELIN(ItemRockJavelin::new, "XXX  ", "XXXX ", "XXXXX", " XXX ", "  X  "),
  HAMMER(ItemRockHammer::new, "XXXXX", "XXXXX", "  X  ");

  private final Function<RockCategory, Item> supplier;
  @Getter
  private final String[] pattern;

  ToolType(@NotNull Function<RockCategory, Item> supplier, String... pattern) {
    this.supplier = supplier;
    this.pattern = pattern;
  }

  public Item create(RockCategory category) {
    return supplier.apply(category);
  }

}
