package su.terrafirmagreg.modules.wood.api.types.type;

import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.api.library.types.variant.Variant;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Set;

@Getter
public class WoodType extends Type<WoodType> {

  @Getter
  private static final Set<WoodType> types = new ObjectOpenHashSet<>();

  private final int color;
  private final float burnTemp;
  private final int burnTicks;
  private final boolean canMakeTannin;


  private WoodType(Builder builder) {
    super(builder.name);

    this.color = builder.color;

    this.burnTemp = builder.burnTemp;
    this.burnTicks = builder.burnTicks;
    this.canMakeTannin = builder.canMakeTannin;

    if (!types.add(this)) {
      throw new RuntimeException(String.format("Type: [%s] already exists!", this.name));
    }
  }


  public static Builder builder(String name) {
    return new Builder(name);
  }


  public ResourceLocation getTexture(Variant<?, WoodType> variant) {
    return ModUtils.resource(String.format("textures/blocks/wood/%s/%s.png", variant, this));
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(String.format("wood.type.%s.name", this)).getFormattedText();
  }

  public static class Builder {

    private final String name;

    private float burnTemp;
    private int burnTicks;
    private int color;
    private boolean canMakeTannin;

    public Builder(String name) {
      this.name = name;

      this.burnTemp = 0;
      this.burnTicks = 0;
      this.color = 0xff000000;
      this.canMakeTannin = false;

    }

    public Builder color(int color) {
      this.color = color;
      return this;
    }

    // Установить температуру и количество тиков горения
    public Builder burnInfo(float burnTemp, int burnTicks) {
      this.burnTemp = burnTemp;
      this.burnTicks = burnTicks;
      return this;
    }

    // Установить возможность производить танин
    public Builder isCanMakeTannin() {
      canMakeTannin = true;
      return this;
    }


    public WoodType build() {
      return new WoodType(this);
    }
  }
}
