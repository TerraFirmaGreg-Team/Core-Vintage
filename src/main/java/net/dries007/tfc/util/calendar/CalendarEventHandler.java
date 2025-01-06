/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.util.calendar;

import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.food.api.FoodStatsTFC;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.GameRuleChangeEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;

import java.util.List;
import java.util.Objects;

import static net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import static net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import static su.terrafirmagreg.api.data.enums.Mods.Names.TFC;

@Mod.EventBusSubscriber(modid = TFC)
public class CalendarEventHandler {

  /**
   * Called from LOGICAL SERVER Responsible for primary time tracking for player time Synced to client every second
   *
   * @param event {@link ServerTickEvent}
   */
  @SubscribeEvent
  public static void onServerTick(ServerTickEvent event) {
    if (event.phase == Phase.END) {
      Calendar.INSTANCE.onServerTick();
    }
  }

  @SubscribeEvent
  public static void onOverworldTick(TickEvent.WorldTickEvent event) {
    if (event.phase == Phase.END && event.world.provider.getDimension() == 0) {
      Calendar.INSTANCE.onOverworldTick(event.world);
    }
  }

  /**
   * Disables the vanilla /time command as we replace it with one that takes into account the calendar
   *
   * @param event {@link CommandEvent}
   */
  @SubscribeEvent
  public static void onCommandFire(CommandEvent event) {
    if ("time".equals(event.getCommand().getName())) {
      event.setCanceled(true);
      event.getSender().sendMessage(new TextComponentTranslation(TFC + ".command.time.disabled"));
    }
  }

  /**
   * This allows beds to function correctly with TFCs calendar
   *
   * @param event {@link PlayerWakeUpEvent}
   */
  @SubscribeEvent
  public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
    if (!event.getEntityPlayer().world.isRemote && !event.updateWorld()) {
      long currentWorldTime = event.getEntity().getEntityWorld().getWorldTime();
      if (Calendar.CALENDAR_TIME.getWorldTime() != currentWorldTime) {
        long jump = Calendar.INSTANCE.setTimeFromWorldTime(currentWorldTime);
        // Consume food/water on all online players accordingly (EXHAUSTION_MULTIPLIER is here to de-compensate)
        event.getEntity().getEntityWorld().getEntities(EntityPlayer.class, Objects::nonNull)
          .forEach(player -> player.addExhaustion(
            FoodStatsTFC.PASSIVE_EXHAUSTION * jump / FoodStatsTFC.EXHAUSTION_MULTIPLIER * (float) ConfigTFC.General.PLAYER.passiveExhaustionMultiplier));

      }
    }
  }

  /**
   * Fired on server only when a player logs out
   *
   * @param event {@link PlayerLoggedOutEvent}
   */
  @SubscribeEvent
  public static void onPlayerLoggedOut(PlayerLoggedOutEvent event) {
    if (event.player instanceof EntityPlayerMP) {
      // Check total players and reset player / calendar time ticking
      MinecraftServer server = event.player.getServer();
      if (server != null) {
        TerraFirmaCraft.getLog().info("Player Logged Out - Checking for Calendar Updates.");
        List<EntityPlayerMP> players = server.getPlayerList().getPlayers();
        int playerCount = players.size();
        // The player logging out doesn't count
        if (players.contains(event.player)) {
          playerCount--;
        }
        Calendar.INSTANCE.setPlayersLoggedOn(playerCount > 0);
      }
    }
  }

  /**
   * Fired on server only when a player logs in
   *
   * @param event {@link PlayerLoggedInEvent}
   */
  @SubscribeEvent
  public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
    if (event.player instanceof EntityPlayerMP) {
      // Check total players and reset player / calendar time ticking
      MinecraftServer server = event.player.getServer();
      if (server != null) {
        TerraFirmaCraft.getLog().info("Player Logged In - Checking for Calendar Updates.");
        int players = server.getPlayerList().getPlayers().size();
        Calendar.INSTANCE.setPlayersLoggedOn(players > 0);
      }
    }
  }

  /**
   * Detects when a user manually changes `doDaylightCycle`, and updates the calendar accordingly
   *
   * @param event {@link GameRuleChangeEvent}
   */
  @SubscribeEvent
  public static void onGameRuleChange(GameRuleChangeEvent event) {
    if ("doDaylightCycle".equals(event.getRuleName())) {
      // This is only called on server, so it needs to sync to client
      Calendar.INSTANCE.setDoDaylightCycle();
    }
  }
}
