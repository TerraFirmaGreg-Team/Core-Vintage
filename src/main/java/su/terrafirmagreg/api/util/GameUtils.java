package su.terrafirmagreg.api.util;

import su.terrafirmagreg.data.Constants;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;


import com.google.common.base.Joiner;

import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("unused")
public final class GameUtils {

  private static final Joiner JOINER_DOT = Joiner.on('.');

  /**
   * Checks if the game is running on the client or not.
   *
   * @return Whether or not the current thread is client sided.
   */
  public static boolean isClient() {
    return FMLCommonHandler.instance().getSide() == Side.CLIENT;
  }

  /**
   * Static way to get the game settings.
   *
   * @return The current game settings.
   */
  @SideOnly(Side.CLIENT)
  public static GameSettings getGameSettings() {
    return Minecraft.getMinecraft().gameSettings;
  }

  /**
   * Gets the current client difficulty.
   *
   * @return The difficulty for the client.
   */
  public static EnumDifficulty getClientDifficulty() {
    return getGameSettings().difficulty;
  }

  public static String getEnumName(Enum<?> anEnum) {
    return JOINER_DOT.join(Constants.MODID_TFC, "enum", anEnum.getDeclaringClass().getSimpleName(),
        anEnum).toLowerCase();
  }

  public static String getTypeName(IForgeRegistryEntry<?> type) {
    //noinspection ConstantConditions
    return JOINER_DOT.join(Constants.MODID_TFC, "types", type.getRegistryType().getSimpleName(),
        type.getRegistryName().getPath()).toLowerCase();
  }
}
