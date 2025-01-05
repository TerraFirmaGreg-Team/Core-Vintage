package su.terrafirmagreg.api.base.object.potion.spi;

import su.terrafirmagreg.api.base.object.potion.api.IPotionSettings;
import su.terrafirmagreg.api.util.GameUtils;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public abstract class BasePotion extends Potion implements IPotionSettings {

  //Offsets provided in case it becomes a good idea to switch to a sprite sheet format and not individual textures

  protected final Settings settings;

  protected int xOffset = 0;
  protected int yOffset = 0;

  protected ResourceLocation texture;

  protected BasePotion(Settings settings) {
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
    if (texture != null) {
      GameUtils.getTextureManager().bindTexture(texture);
      Gui.drawModalRectWithCustomSizedTexture(x + xOffset + 6, y + yOffset + 7, 0, 0, 18, 18, 18, 18);
    }
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void renderHUDEffect(PotionEffect effect, Gui gui, int x, int y, float z, float alpha) {
    if (texture != null) {
      GameUtils.getTextureManager().bindTexture(texture);
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
