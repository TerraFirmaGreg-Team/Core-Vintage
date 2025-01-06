package su.terrafirmagreg.api.base.object.effect.spi;

import su.terrafirmagreg.api.base.object.effect.api.IEffectSettings;
import su.terrafirmagreg.api.util.GameUtils;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import lombok.Getter;


@Getter
public abstract class BaseEffect extends Potion implements IEffectSettings {

  //Offsets provided in case it becomes a good idea to switch to a sprite sheet format and not individual textures

  protected final Settings settings;

  protected int xOffset = 0;
  protected int yOffset = 0;

  protected BaseEffect() {
    this(Settings.of());
  }

  protected BaseEffect(Settings settings) {
    super(settings.isBadEffect(), settings.getLiquidColor());

    this.settings = settings;
  }

  public void removePotionEffect(EntityLivingBase entity, final Potion potion) {
    //Potion Core Compatibility
    if (entity.isPotionActive(potion)) {
      if (entity.getActivePotionEffect(potion).getDuration() > 1) {
        entity.removePotionEffect(potion);
        entity.addPotionEffect(new PotionEffect(potion, 1));
      }
    }
  }

  @Override
  public boolean isReady(int duration, int amplifier) {
    //Regeneration base
    return isReadyVar(duration, amplifier, 50);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public boolean hasStatusIcon() {
    return settings.isStatusIcon();
  }

  @SideOnly(Side.CLIENT)
  public boolean isBeneficial() {
    return settings.isBeneficial();
  }

  @Override
  public boolean shouldRender(PotionEffect effect) {
    return settings.isDrawInventory();
  }

  @Override
  public boolean shouldRenderInvText(PotionEffect effect) {
    return settings.isDrawInventoryText();
  }

  @Override
  public boolean shouldRenderHUD(PotionEffect effect) {
    return settings.isStatusIcon();
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void renderInventoryEffect(PotionEffect effect, Gui gui, int x, int y, float z) {
    if (settings.getTexture() != null) {
      GameUtils.getTextureManager().bindTexture(settings.getTexture());
      Gui.drawModalRectWithCustomSizedTexture(x + xOffset + 6, y + yOffset + 7, 0, 0, 18, 18, 18, 18);
    }
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void renderHUDEffect(PotionEffect effect, Gui gui, int x, int y, float z, float alpha) {
    if (settings.getTexture() != null) {
      GameUtils.getTextureManager().bindTexture(settings.getTexture());
      Gui.drawModalRectWithCustomSizedTexture(x + xOffset + 3, y + yOffset + 3, 0, 0, 18, 18, 18, 18);
    }
  }

  @SideOnly(Side.CLIENT)
  @Override
  public int getStatusIconIndex() {
    return settings.getStatusIconIndex();
  }

  public boolean isReadyVar(int duration, int amplifier, int var) {
    int k = var >> amplifier;

    if (k > 0) {
      return duration % k == 0;
    } else {
      return true;
    }
  }

  @Override
  public String getName() {
    return ModUtils.localize("potion", this.getRegistryName());
  }

}
