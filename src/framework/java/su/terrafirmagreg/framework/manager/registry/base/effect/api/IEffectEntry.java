package su.terrafirmagreg.framework.manager.registry.base.effect.api;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;
import su.terrafirmagreg.framework.manager.registry.base.effect.api.IEffectEntry.EffectSettings;

import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public interface IEffectEntry extends IRegistryEntry<EffectSettings, Potion> {


  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  class EffectSettings extends RegistrySettings<EffectSettings> {

    protected ResourceLocation texture;

    protected boolean isBadEffect = false;
    protected boolean statusIcon = true;
    protected boolean drawInventory = true;
    protected boolean drawInventoryText = true;
    protected boolean beneficial = false;

    protected int liquidColor = 0xFFFFFF;
    protected int statusIconIndex = -1;


    public static EffectSettings of() {
      return new EffectSettings();
    }

    public EffectSettings texture(ResourceLocation texture) {
      this.texture = texture;
      return this.self();
    }

    public EffectSettings texture(String icon) {
      this.texture = ModUtils.resource("textures/gui/icons/potion/" + icon + ".png");
      return this.self();
    }

    public EffectSettings texture(String modid, String icon) {
      this.texture = ModUtils.resource(modid, "textures/gui/icons/potion/" + icon + ".png");
      return this.self();
    }

    public EffectSettings statusIconIndex(int columnIndex, int rowIndex) {
      this.statusIconIndex = columnIndex + rowIndex * 8;
      return this.self();
    }

    public EffectSettings badEffect() {
      this.isBadEffect = true;
      return this.self();
    }

    public EffectSettings noStatusIcon() {
      this.statusIcon = false;
      return this.self();
    }

    public EffectSettings noDrawInventory() {
      this.drawInventory = false;
      return this.self();
    }

    public EffectSettings noDrawInventoryText() {
      this.drawInventoryText = false;
      return this.self();
    }

    public EffectSettings beneficial() {
      this.beneficial = true;
      return this.self();
    }

    public EffectSettings liquidColor(int liquidColor) {
      this.liquidColor = liquidColor;
      return this.self();
    }


  }
}
