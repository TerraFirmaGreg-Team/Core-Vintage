package net.dries007.tfc.module.core.client.button;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.dries007.tfc.module.core.client.TFCSounds;
import net.dries007.tfc.network.PacketGuiButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class GuiButtonKnapping extends GuiButton {
    private final ResourceLocation texture;

    public GuiButtonKnapping(int id, int x, int y, int width, int height, ResourceLocation texture) {
        super(id, x, y, width, height, "");
        this.texture = texture;
    }

    public void onClick() {
        if (this.enabled) {
            this.visible = false;
            TerraFirmaCraft.NETWORK.sendToServer(new PacketGuiButton(this.id));
        }
    }

    @Override
    public void drawButton(@Nonnull Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            GlStateManager.color(1, 1, 1, 1);
            mc.getTextureManager().bindTexture(texture);

            hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

            drawModalRectWithCustomSizedTexture(x, y, 0, 0, 16, 16, 16, 16);
            mouseDragged(mc, mouseX, mouseY);
        }
    }

    public void playPressSoundBasedOnKnappingType(SoundHandler soundHandler, KnappingType knappingType) {
        var soundEvent = SoundEvents.UI_BUTTON_CLICK;

        switch (knappingType) {
            case STONE -> soundEvent = TFCSounds.KNAPPING_ROCK;
            case CLAY, FIRE_CLAY -> soundEvent = TFCSounds.KNAPPING_CLAY;
            case LEATHER -> soundEvent = TFCSounds.KNAPPING_LEATHER;
        }

        soundHandler.playSound(PositionedSoundRecord.getMasterRecord(soundEvent, 1.0F));
    }

    @Override
    public void playPressSound(@Nonnull SoundHandler soundHandlerIn) {
    }
}
