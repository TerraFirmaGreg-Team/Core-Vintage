package su.terrafirmagreg.api.base.object.potion.api;

import su.terrafirmagreg.api.base.object.potion.api.IPotionSettings.Settings;
import su.terrafirmagreg.api.library.IBaseSettings;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.util.ResourceLocation;

import lombok.Getter;

public interface IPotionSettings extends IBaseSettings<Settings> {


  @Getter
  class Settings extends BaseSettings<Settings> {

    ResourceLocation texture;

    boolean isBadEffect = false;
    boolean statusIcon = true;
    boolean drawInventory = true;
    boolean drawInventoryText = true;
    boolean beneficial = false;

    int liquidColor = 0xFFFFFF;
    int statusIconIndex = -1;

    protected Settings() {

    }

    public static Settings of() {
      return new Settings();
    }

    public Settings texture(ResourceLocation texture) {
      this.texture = texture;
      return this;
    }

    public Settings texture(String icon) {
      this.texture = ModUtils.resource("textures/gui/icons/potion/" + icon + ".png");
      return this;
    }

    public Settings texture(String modid, String icon) {
      this.texture = ModUtils.resource(modid, "textures/gui/icons/potion/" + icon + ".png");
      return this;
    }

    public Settings statusIconIndex(int columnIndex, int rowIndex) {
      this.statusIconIndex = columnIndex + rowIndex * 8;
      return this;
    }

    public Settings badEffect() {
      this.isBadEffect = true;
      return this;
    }

    public Settings noStatusIcon() {
      this.statusIcon = false;
      return this;
    }

    public Settings noDrawInventory() {
      this.drawInventory = false;
      return this;
    }

    public Settings noDrawInventoryText() {
      this.drawInventoryText = false;
      return this;
    }

    public Settings beneficial() {
      this.beneficial = true;
      return this;
    }

    public Settings liquidColor(int liquidColor) {
      this.liquidColor = liquidColor;
      return this;
    }


  }
}
