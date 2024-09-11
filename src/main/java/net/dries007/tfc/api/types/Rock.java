package net.dries007.tfc.api.types;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;


import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.objects.items.tools.ItemRockAxe;
import net.dries007.tfc.objects.items.tools.ItemRockHammer;
import net.dries007.tfc.objects.items.tools.ItemRockHoe;
import net.dries007.tfc.objects.items.tools.ItemRockJavelin;
import net.dries007.tfc.objects.items.tools.ItemRockKnife;
import net.dries007.tfc.objects.items.tools.ItemRockShovel;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.function.Function;

import static su.terrafirmagreg.data.Constants.MODID_TFC;

public class Rock extends IForgeRegistryEntry.Impl<Rock> {

  @Getter
  private final RockCategory rockCategory;
  private final ResourceLocation textureLocation;
  private final boolean isFluxStone;
  private final boolean isNaturallyGenerating;

  public Rock(@NotNull ResourceLocation name, @NotNull RockCategory rockCategory, boolean isFluxStone) {
    this(name, rockCategory, isFluxStone, true);
  }

  public Rock(@NotNull ResourceLocation name, @NotNull RockCategory rockCategory, boolean isFluxStone, boolean isNaturallyGenerating) {
    //noinspection ConstantConditions
    if (rockCategory == null) {
      throw new IllegalArgumentException("Rock category is not allowed to be null (on rock " + name + ")");
    }

    setRegistryName(name);
    this.rockCategory = rockCategory;
    this.textureLocation = new ResourceLocation(MODID_TFC, "textures/blocks/stonetypes/raw/" + name.getPath() + ".png");
    this.isFluxStone = isFluxStone;
    this.isNaturallyGenerating = isNaturallyGenerating;
  }

  public Rock(@NotNull ResourceLocation name, @NotNull ResourceLocation categoryName, boolean isFluxStone) {
    //noinspection ConstantConditions
    this(name, TFCRegistries.ROCK_CATEGORIES.getValue(categoryName), isFluxStone, true);
  }

  /**
   * Used for knapping GUI
   *
   * @return a texture resource location
   */
  public ResourceLocation getTexture() {
    return textureLocation;
  }

  public boolean isFluxStone() {
    return isFluxStone;
  }

  public boolean isNaturallyGenerating() {
    return isNaturallyGenerating;
  }

  @SuppressWarnings("ConstantConditions")
  @Override
  public String toString() {
    return getRegistryName().getPath();
  }

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
}
