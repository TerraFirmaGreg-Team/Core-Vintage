package su.terrafirmagreg.api.spi.effects;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;

@SuppressWarnings("WeakerAccess")
public abstract class PotionBase extends Potion {

    //Offsets provided in case it becomes a good idea to switch to a sprite sheet format and not individual textures

    protected int xOffset = 0;
    protected int yOffset = 0;
    protected boolean drawHUD = true;
    protected boolean drawInventory = true;
    protected boolean drawInventoryText = true;
    protected ResourceLocation texture;

    protected PotionBase() {
        super(false, 0xFFFFFF);
    }

    protected PotionBase(boolean isBadEffectIn, int liquidColorIn) {
        super(isBadEffectIn, liquidColorIn);
    }

    protected void formatTexture(String icon) {
        this.texture = ModUtils.id("textures/potions/" + icon + ".png");
    }

    @Override
    public boolean hasStatusIcon() {
        return drawHUD;
    }

    public void removePotionCoreEffect(EntityLivingBase entity, final Potion potion) {
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

    public boolean isReadyVar(int duration, int amplifier, int var) {
        int k = var >> amplifier;

        if (k > 0) {
            return duration % k == 0;
        } else {
            return true;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderInventoryEffect(@NotNull PotionEffect effect, @NotNull Gui gui, int x, int y, float z) {
        renderInventoryEffect(x, y, effect, Minecraft.getMinecraft());
    }

    @SideOnly(Side.CLIENT)
    @Override
    @SuppressWarnings("deprecation")
    public void renderInventoryEffect(int x, int y, @NotNull PotionEffect effect, @NotNull Minecraft mc) {
        if (texture != null) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
            Gui.drawModalRectWithCustomSizedTexture(x + xOffset + 6, y + yOffset + 7, 0, 0, 18, 18, 18, 18);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderHUDEffect(@NotNull PotionEffect effect, @NotNull Gui gui, int x, int y, float z, float alpha) {
        renderHUDEffect(x, y, effect, Minecraft.getMinecraft(), alpha);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void renderHUDEffect(int x, int y, @NotNull PotionEffect effect, @NotNull Minecraft mc, float alpha) {
        if (texture != null) {
            mc.getTextureManager().bindTexture(texture);
            Gui.drawModalRectWithCustomSizedTexture(x + xOffset + 3, y + yOffset + 3, 0, 0, 18, 18, 18, 18);
        }
    }

    @Override
    public boolean shouldRenderHUD(@NotNull PotionEffect effect) {
        return drawHUD;
    }

    @Override
    public boolean shouldRender(@NotNull PotionEffect effect) {
        return drawInventory;
    }

    @Override
    public boolean shouldRenderInvText(@NotNull PotionEffect effect) {
        return drawInventoryText;
    }

}
