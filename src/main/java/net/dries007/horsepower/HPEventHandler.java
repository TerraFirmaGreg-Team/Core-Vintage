package net.dries007.horsepower;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import net.dries007.horsepower.lib.Reference;
import net.dries007.horsepower.network.PacketHandler;
import net.dries007.horsepower.network.messages.SyncServerRecipesMessage;
import net.dries007.horsepower.recipes.HPRecipes;
import net.dries007.horsepower.util.Utils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class HPEventHandler {

  public static Map<ItemStack, Pair<Integer, Integer>> choppingAxes = new HashMap<>();
  public static Map<Integer, Pair<Integer, Integer>> harvestPercentages = new HashMap<>();

  @SubscribeEvent
  public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
    if (event.getModID().equals(Reference.MODID)) {
      reloadConfig();
      Utils.sendSavedErrors();
    }
  }

  public static void reloadConfig() {
    ConfigManager.sync(Reference.MODID, Config.Type.INSTANCE);
    HPRecipes.instance().reloadRecipes();
    choppingAxes.clear();
    Arrays.stream(Configs.general.choppingBlockAxes).forEach(s ->
    {
      String[] data = s.split("=");
      int base = Utils.getBaseAmount(data[1]);
      int chance = Utils.getChance(data[1]);
      ItemStack stack = ItemStack.EMPTY;

      try {
        stack = (ItemStack) Utils.parseItemStack(data[0], false, false);
      } catch (Exception e) {
        Utils.errorMessage("Parse error with item for custom axes for the chopping block", false);
      }

      if (!stack.isEmpty()) {choppingAxes.put(stack, Pair.of(base, chance));}
    });

    harvestPercentages.clear();
    Arrays.stream(Configs.general.harvestable_percentage).forEach(s ->
    {
      String[] data = s.split("=");
      try {
        int harvestLevel = Integer.parseInt(data[0]);
        int base = Utils.getBaseAmount(data[1]);
        int chance = Utils.getChance(data[1]);

        harvestPercentages.put(harvestLevel, Pair.of(base, chance));
      } catch (NumberFormatException e) {
        Utils.errorMessage("HarvestLevel config is malformed, make sure only numbers are used as values, (" + s + ")", false);
      }
    });
  }

  @SubscribeEvent
  public static void onWorldJoin(EntityJoinWorldEvent event) {
    if (FMLCommonHandler.instance().getSide().isClient() && event.getEntity() instanceof EntityPlayerSP && event.getWorld() instanceof WorldClient
        && FMLClientHandler.instance().getClientPlayerEntity() != null) {
      Utils.sendSavedErrors();
      //HPEventHandler.reloadConfig();
    }
  }

  @SubscribeEvent
  public static void onServerJoined(PlayerEvent.PlayerLoggedInEvent event) {
    if (FMLCommonHandler.instance().getSide().isServer()) {PacketHandler.INSTANCE.sendTo(new SyncServerRecipesMessage(), (EntityPlayerMP) event.player);}
  }

  @SubscribeEvent
  public static void onServerLeave(WorldEvent.Unload event) {
    if (FMLCommonHandler.instance().getSide().isClient()) {
      NetworkManager manager = FMLClientHandler.instance().getClientToServerNetworkManager();
      if (manager != null && !manager.isLocalChannel() && HPRecipes.serverSyncedRecipes) {
        HPRecipes.serverSyncedRecipes = false;
        HPRecipes.instance().reloadRecipes();
      }
    }
  }
}
