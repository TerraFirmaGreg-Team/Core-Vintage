package su.terrafirmagreg.modules.metal.api.types.type;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.data.lib.types.type.Type;
import su.terrafirmagreg.data.lib.types.variant.Variant;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import gregtech.api.unification.material.Material;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;

import lombok.Getter;

import java.util.Set;

@Getter
public class MetalType extends Type<MetalType> {

  @Getter
  private static final Set<MetalType> types = new ObjectLinkedOpenHashSet<>();

  private final Material material;
  private final int tier;
  private final float specificHeat;
  private final float meltTemp;
  private final int color;

  public MetalType(Builder builder) {
    super(builder.name);

    this.material = builder.material;
    this.tier = builder.tier;
    this.specificHeat = builder.specificHeat;
    this.meltTemp = builder.meltTemp;
    this.color = builder.color;

    if (!types.add(this)) {
      throw new RuntimeException(String.format("Type: [%s] already exists!", name));
    }
  }

  public ResourceLocation getTexture(Variant<?, MetalType> variant) {
    return ModUtils.resource(String.format("textures/blocks/metal/%s/%s.png", variant, this));
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(String.format("metal.type.%s.name", this)).getFormattedText();
  }

  public String getRegistryKey(Variant<?, MetalType> variant) {
    return String.format("metal/%s/%s", variant, this);
  }

  public static class Builder {

    private final String name;

    private Material material;
    private float meltTemp;
    private float specificHeat;
    private int color;
    private int tier;

    public Builder(String name) {
      this.name = name;
    }

    public Builder material(Material material) {
      this.material = material;
      this.color = material.getMaterialRGB();
      return this;
    }

    public Builder heat(float specificHeat, float meltTemp) {
      this.specificHeat = specificHeat;
      this.meltTemp = meltTemp;
      return this;
    }

    public Builder tier(int tier) {
      this.tier = tier;
      return this;
    }

    public MetalType build() {
      return new MetalType(this);
    }

  }
}
