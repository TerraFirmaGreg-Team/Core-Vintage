package se.gory_moon.horsepower;

import su.terrafirmagreg.api.util.GameUtils;
import su.terrafirmagreg.data.Constants;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import org.apache.commons.lang3.tuple.Pair;
import se.gory_moon.horsepower.network.PacketHandler;
import se.gory_moon.horsepower.network.messages.SyncServerRecipesMessage;
import se.gory_moon.horsepower.recipes.HPRecipes;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Constants.MODID_HORSEPOWER)
public class HPEventHandler {

  public static Map<ItemStack, Pair<Integer, Integer>> choppingAxes = new HashMap<>();
  public static Map<Integer, Pair<Integer, Integer>> harvestPercentages = new HashMap<>();

  @SubscribeEvent
  public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
    if (event.getModID().equals(Constants.MODID_HORSEPOWER)) {
      ConfigManager.sync(Constants.MODID_HORSEPOWER, Config.Type.INSTANCE);
    }
  }


  @SubscribeEvent
  public static void onServerJoined(PlayerEvent.PlayerLoggedInEvent event) {
    if (GameUtils.isServer()) {
      PacketHandler.INSTANCE.sendTo(new SyncServerRecipesMessage(), (EntityPlayerMP) event.player);
    }
  }

  @SubscribeEvent
  public static void onServerLeave(WorldEvent.Unload event) {
    if (GameUtils.isClient()) {
      NetworkManager manager = FMLClientHandler.instance().getClientToServerNetworkManager();
      if (manager != null && !manager.isLocalChannel() && HPRecipes.serverSyncedRecipes) {
        HPRecipes.serverSyncedRecipes = false;
        HPRecipes.instance().reloadRecipes();
      }
    }
  }

}
