package su.terrafirmagreg.modules.core.client.gui.overlay;

import su.terrafirmagreg.api.data.Unicode;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.capabilities.ambiental.CapabilityAmbiental;
import su.terrafirmagreg.modules.core.capabilities.ambiental.CapabilityProviderAmbiental;
import su.terrafirmagreg.modules.core.init.FluidsCore;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class OverlayAmbiental {

  public static final ResourceLocation COLD_VIGNETTE = ModUtils.resource("textures/gui/cold_vignette.png");
  public static final ResourceLocation HOT_VIGNETTE = ModUtils.resource("textures/gui/hot_vignette.png");
  public static final ResourceLocation MINUS = ModUtils.resource("textures/gui/icons/lower.png");
  public static final ResourceLocation PLUS = ModUtils.resource("textures/gui/icons/higher.png");
  public static final ResourceLocation MINUSER = ModUtils.resource("textures/gui/icons/lowerer.png");
  public static final ResourceLocation PLUSER = ModUtils.resource("textures/gui/icons/higherer.png");

  private static void drawTexturedModalRect(float x, float y, float width, float height, ResourceLocation loc) {
    Minecraft minecraft = Minecraft.getMinecraft();
    minecraft.getTextureManager().bindTexture(loc);

    GlStateManager.disableDepth();
    GlStateManager.depthMask(false);
    GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0f);
    GlStateManager.disableAlpha();
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder buffer = tessellator.getBuffer();
    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
    buffer.pos(x, y + height, -90.0D).tex(0.0D, 1.0D).endVertex();
    buffer.pos(x + width, y + height, -90.0D).tex(1.0D, 1.0D).endVertex();
    buffer.pos(x + width, y, -90.0D).tex(1.0D, 0.0D).endVertex();
    buffer.pos(x, y, -90.0D).tex(0.0D, 0.0D).endVertex();
    tessellator.draw();
    GlStateManager.depthMask(true);
    GlStateManager.enableDepth();
    GlStateManager.enableAlpha();
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
  }

  @SubscribeEvent
  public void onPostRenderOverlay(RenderGameOverlayEvent.Post event) {
  }

  @SubscribeEvent
  public void render(RenderGameOverlayEvent.Pre event) {

    EntityPlayer player = Minecraft.getMinecraft().player;
    if (player.isCreative() || player.isDead || player.isSpectator()) {
      return;
    }
    var tempSystem = CapabilityAmbiental.get(player);
    ScaledResolution resolution = event.getResolution();
    int width = resolution.getScaledWidth();
    int height = resolution.getScaledHeight();
    float redCol = 0f;
    float greenCol = 0f;
    float blueCol = 0f;

    float offsetY = 0f;
    float offsetX = 0f;
    float offsetYArrow = 0f;
    BlockPos pos = new BlockPos(player.getPosition());
    BlockPos pos2 = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
    if (player.isRiding()) {
      offsetY = -10f;
      offsetYArrow = -10f;
      offsetX = 0;
    }
    IBlockState state = player.world.getBlockState(pos2);
    Block block = state.getBlock();
    if (block == FluidsCore.HOT_WATER.get().getBlock() ||
        block == FluidsCore.SALT_WATER.get().getBlock() ||
        block == FluidsCore.FRESH_WATER.get().getBlock()) {
      offsetY = -10f;
      offsetX = 0;
    }

    drawTemperatureVignettes(width, height, player, event);
    if (event.getType() != ElementType.HOTBAR) {
      return;
    }
    float temperature = 1f;
    ScaledResolution sr = event.getResolution();
    int healthRowHeight = sr.getScaledHeight() - 40;
    int armorRowHeight = healthRowHeight - 10;
    int mid = sr.getScaledWidth() / 2;
    temperature = tempSystem.getTemperature();
    GL11.glEnable(GL11.GL_BLEND);
    if (temperature > CapabilityProviderAmbiental.AVERAGE) {
      float hotRange = CapabilityProviderAmbiental.HOT_THRESHOLD - CapabilityProviderAmbiental.AVERAGE + 2;
      float red = Math.max(0, Math.min(1, (temperature - CapabilityProviderAmbiental.AVERAGE) / hotRange));
      redCol = 1F;
      greenCol = 1.0F - red / 2.4F;
      blueCol = 1.0F - red / 1.6F;
    } else {
      float coolRange = CapabilityProviderAmbiental.AVERAGE - CapabilityProviderAmbiental.COOL_THRESHOLD - 2;
      float blue = Math.max(0,
        Math.min(1, (CapabilityProviderAmbiental.AVERAGE - temperature) / coolRange));
      redCol = 1.0F - blue / 1.6F;
      greenCol = 1.0F - blue / 2.4F;
      blueCol = 1.0F;
    }
    GL11.glColor4f(redCol, greenCol, blueCol, 0.9F);
    java.awt.Color c = new java.awt.Color(redCol, greenCol, blueCol, 1.0F);
    GL11.glColor4f(redCol, greenCol, blueCol, 0.9F);
    GL11.glEnable(GL11.GL_BLEND);
    float speed = tempSystem.getChangeSpeed();
    float change = tempSystem.getChange();
    if (change > 0) {
      if (change > CapabilityProviderAmbiental.HIGH_CHANGE) {
        drawTexturedModalRect(mid - 8, armorRowHeight - 4 + offsetYArrow, 16, 16, PLUSER);
      } else {
        drawTexturedModalRect(mid - 8, armorRowHeight - 4 + offsetYArrow, 16, 16, PLUS);
      }
    } else {
      if (change < -CapabilityProviderAmbiental.HIGH_CHANGE) {
        drawTexturedModalRect(mid - 8, armorRowHeight - 4 + offsetYArrow, 16, 16, MINUSER);
      } else {
        drawTexturedModalRect(mid - 8, armorRowHeight - 4 + offsetYArrow, 16, 16, MINUS);
      }
    }
    if (player.isSneaking() || !ConfigCore.MISC.AMBIENTAL.sneakyDetails) {
      float targetFormatted = tempSystem.getTarget();
      float tempFormatted = tempSystem.getTemperature();
      float changeFormatted = tempSystem.getChange();
      if (!ConfigCore.MISC.AMBIENTAL.celsius) {
        targetFormatted = targetFormatted * ((float) 9 / 5) + 32;
        tempFormatted = tempFormatted * ((float) 9 / 5) + 32;
        changeFormatted = changeFormatted * ((float) 9 / 5);
      }
      FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
      String tempStr = String.format("%.1f" + Unicode.DEGREE + " -> %.1f" + Unicode.DEGREE,
        temperature, targetFormatted);
      String changeStr = String.format("%.3f" + Unicode.DEGREE + "/s", change);
      fr.drawStringWithShadow(tempStr, mid + 50 - (float) fr.getStringWidth(tempStr) / 2 + offsetX,
        armorRowHeight + 1 + offsetY, c.getRGB());
      fr.drawStringWithShadow(changeStr, mid - 50 - (float) fr.getStringWidth(changeStr) / 2,
        armorRowHeight + 1, c.getRGB());

    }
    GL11.glColor4f(1f, 1f, 1f, 0.9F);
    GL11.glDisable(GL11.GL_BLEND);
  }

  private void drawTemperatureVignettes(int width, int height, EntityPlayer player, RenderGameOverlayEvent.Pre event) {
    ResourceLocation vignetteLocation = null;
    float temperature = 1f;
    var tempSystem = CapabilityAmbiental.get(player);
    if (tempSystem != null) {
      temperature = tempSystem.getTemperature();
    }

    float opacity = 1f;
    if (temperature > CapabilityProviderAmbiental.HOT_THRESHOLD) {
      vignetteLocation = HOT_VIGNETTE;
      opacity = Math.min(0.95f, (temperature - CapabilityProviderAmbiental.HOT_THRESHOLD) / 14);
    } else if (temperature < CapabilityProviderAmbiental.COOL_THRESHOLD) {
      vignetteLocation = COLD_VIGNETTE;
      opacity = Math.min(0.95f, (CapabilityProviderAmbiental.COOL_THRESHOLD - temperature) / 14);
    }

    if (event.getType() == ElementType.PORTAL) {

      if (vignetteLocation != null) {
        Minecraft minecraft = Minecraft.getMinecraft();
        minecraft.getTextureManager().bindTexture(vignetteLocation);

        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.color(1.0F, 1.0F, 1.0F, opacity);
        GlStateManager.disableAlpha();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(0.0D, height, -90.0D).tex(0.0D, 1.0D).endVertex();
        buffer.pos(width, height, -90.0D).tex(1.0D, 1.0D).endVertex();
        buffer.pos(width, 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
        buffer.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      }
    }
  }
}
