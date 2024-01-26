package su.terrafirmagreg.api.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class GameUtils {

    private GameUtils() {
        throw new IllegalAccessError("Utility class");
    }

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
}