package su.terrafirmagreg.api.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("unused")
public final class GameUtils {

  /**
   * Gets the current client difficulty.
   *
   * @return The difficulty for the client.
   */
  public static EnumDifficulty getClientDifficulty() {
    return getGameSettings().difficulty;
  }

  /**
   * Static way to get the game settings.
   *
   * @return The current game settings.
   */
  @SideOnly(Side.CLIENT)
  public static GameSettings getGameSettings() {
    return GameUtils.getMinecraft().gameSettings;
  }

  /**
   * Closes the currently open container for the given player. If the player is not in a container, this method won't do anything.
   */
  public static void closeContainer(EntityPlayer player) {
    player.closeScreen();
  }

  /**
   * Closes the player's opened screen
   */
  public static void closeScreen() {
    getPlayer().closeScreen();
  }


  public static void displayScreen(GuiScreen screen) {
    getMinecraft().displayGuiScreen(screen);
  }

  public static Minecraft getMinecraft() {
    return Minecraft.getMinecraft();
  }

  public static TextureManager getTextureManager() {
    return getMinecraft().getTextureManager();
  }

  public static FontRenderer getFontRenderer() {
    return getMinecraft().fontRenderer;
  }

  public static EntityPlayer getPlayer() {
    return getMinecraft().player;
  }

  public static World getWorld() {
    return getMinecraft().world;
  }

  public static BlockRendererDispatcher getBlockRenderer() {
    return getMinecraft().getBlockRendererDispatcher();
  }

  public static RenderItem getRenderItem() {
    return getMinecraft().getRenderItem();
  }

  public static float getPartialTicks() {
    Minecraft minecraft = getMinecraft();
    return minecraft.isGamePaused() ? minecraft.renderPartialTicksPaused : minecraft.getRenderPartialTicks();
  }

}
