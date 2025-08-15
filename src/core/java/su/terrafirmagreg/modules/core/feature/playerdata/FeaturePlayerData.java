package su.terrafirmagreg.modules.core.feature.playerdata;

import su.terrafirmagreg.api.util.CapabilityUtils;
import su.terrafirmagreg.api.util.MathUtils;
import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;
import su.terrafirmagreg.framework.module.spi.StateEvent;
import su.terrafirmagreg.modules.core.feature.calendar.spi.ICalendar;
import su.terrafirmagreg.modules.core.feature.playerdata.capability.CapabilityPlayerData;
import su.terrafirmagreg.modules.core.feature.playerdata.capability.ProviderPlayerData;
import su.terrafirmagreg.modules.core.feature.playerdata.spi.SmithingSkill;
import su.terrafirmagreg.modules.core.packet.SCPacketPlayerDataUpdate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class FeaturePlayerData extends BaseFeature {

  private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

  @SubscribeEvent
  public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
    final Entity entity = event.getObject();

    if (entity instanceof EntityPlayer player) {
      // Player skills
      if (!CapabilityPlayerData.has(player)) {
        event.addCapability(CapabilityPlayerData.KEY, new ProviderPlayerData(player));
      }
    }
  }

  /*
   * Fired on server, sync capabilities to client whenever player changes dimension.
   */
  @SubscribeEvent
  public static void onPlayerChangedDimension(PlayerChangedDimensionEvent event) {
    final EntityPlayer entityPlayer = event.player;

    if (entityPlayer instanceof EntityPlayerMP player) {
      CapabilityUtils.getOptional(player, CapabilityPlayerData.CAPABILITY).ifPresent(cap -> {
        new SCPacketPlayerDataUpdate(cap.serializeNBT()).sendTo(player);
      });
    }

  }

  /**
   * Fired on server only when a player dies and respawns, or travels through dimensions
   *
   * @param event {@link PlayerRespawnEvent event}
   */
  @SubscribeEvent
  public static void onPlayerRespawn(PlayerRespawnEvent event) {
    final EntityPlayer entityPlayer = event.player;

    if (entityPlayer instanceof EntityPlayerMP player) {
      CapabilityUtils.getOptional(player, CapabilityPlayerData.CAPABILITY).ifPresent(cap -> {
        new SCPacketPlayerDataUpdate(cap.serializeNBT()).sendTo(player);
      });
    }

  }

  /**
   * Fired on server only when a player logs in
   *
   * @param event {@link PlayerLoggedInEvent}
   */
  @SubscribeEvent
  public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
    final EntityPlayer entityPlayer = event.player;

    if (entityPlayer instanceof EntityPlayerMP player) {
      CapabilityUtils.getOptional(player, CapabilityPlayerData.CAPABILITY).ifPresent(cap -> {
        new SCPacketPlayerDataUpdate(cap.serializeNBT()).sendTo(player);
      });
    }


  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void breakEvent(BlockEvent.BreakEvent event) {
    final EntityPlayer player = event.getPlayer();
    if (player == null) {
      return;
    }

    CapabilityUtils.getOptional(player, CapabilityPlayerData.CAPABILITY).ifPresent(cap -> {
      cap.setHarvestingTool(player.getHeldItemMainhand());
    });
  }

  @SubscribeEvent
  public static void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event) {
    final EntityPlayer player = event.getHarvester();
    if (player == null) {
      return;
    }
    // Apply durability modifier on tools
    CapabilityUtils.getOptional(player, CapabilityPlayerData.CAPABILITY).ifPresent(cap -> {
      ItemStack tool = cap.getHarvestingTool();
      if (!tool.isEmpty()) {
        float skillModifier = SmithingSkill.getSkillBonus(tool, SmithingSkill.Type.TOOLS) / 2.0F;
        if (skillModifier > 0 && MathUtils.RNG.nextFloat() < skillModifier) {
          // Up to 50% negating damage, for double durability
          player.setHeldItem(EnumHand.MAIN_HAND, tool);
        }
      }
    });
  }

  /**
   * Fired on server only when a player dies and respawns. Used to copy skill level before respawning since we need the original (AKA the body) player entity
   *
   * @param event {@link PlayerEvent.Clone}
   */
  @SubscribeEvent
  public static void onPlayerClone(PlayerEvent.Clone event) {
    final EntityPlayer entityPlayer = event.getEntityPlayer();

    if (entityPlayer instanceof EntityPlayerMP player) {

      // Skills
      CapabilityUtils.getOptional(player, CapabilityPlayerData.CAPABILITY).ifPresent(newSkills -> {
        CapabilityUtils.getOptional(event.getOriginal(), CapabilityPlayerData.CAPABILITY).ifPresent(originalSkills -> {
          newSkills.deserializeNBT(originalSkills.serializeNBT());
          // To properly sync, we need to use PlayerRespawnEvent
        });
      });
    }
  }

  @SubscribeEvent
  public static void onServerChatEvent(ServerChatEvent event) {
    final var player = event.getPlayer();
    CapabilityUtils.getOptional(player, CapabilityPlayerData.CAPABILITY).ifPresent(cap -> {

      long intoxicatedTicks = cap.getIntoxicatedTime() - 6 * ICalendar.TICKS_IN_HOUR; // Only apply intoxication after 6 hr
      if (intoxicatedTicks > 0) {
        float drunkChance = MathHelper.clamp((float) intoxicatedTicks / ProviderPlayerData.MAX_INTOXICATED_TICKS, 0, 0.7f);
        String originalMessage = event.getMessage();
        String[] words = originalMessage.split(" ");
        for (int i = 0; i < words.length; i++) {
          String word = words[i];
          if (word.isEmpty()) {
            continue;
          }

          // Swap two letters
          if (MathUtils.RNG.nextFloat() < drunkChance && word.length() >= 2) {
            int pos = MathUtils.RNG.nextInt(word.length() - 1);
            word = word.substring(0, pos) + word.charAt(pos + 1) + word.charAt(pos) + word.substring(pos + 2);
          }

          // Repeat / slur letters
          if (MathUtils.RNG.nextFloat() < drunkChance) {
            int pos = MathUtils.RNG.nextInt(word.length());
            char repeat = word.charAt(pos);
            int amount = 1 + MathUtils.RNG.nextInt(3);
            word = word.substring(0, pos) + new String(new char[amount]).replace('\0', repeat) + (pos + 1 < word.length() ? word.substring(pos + 1) : "");
          }

          // Add additional letters
          if (MathUtils.RNG.nextFloat() < drunkChance) {
            int pos = MathUtils.RNG.nextInt(word.length());
            char replacement = ALPHABET.charAt(MathUtils.RNG.nextInt(ALPHABET.length()));
            if (Character.isUpperCase(word.charAt(MathUtils.RNG.nextInt(word.length())))) {
              replacement = Character.toUpperCase(replacement);
            }
            word = word.substring(0, pos) + replacement + (pos + 1 < word.length() ? word.substring(pos + 1) : "");
          }

          words[i] = word;
        }
        event.setComponent(new TextComponentTranslation("<" + event.getUsername() + "> " + String.join(" ", words)));
      }
    });
  }

  @SubscribeEvent
  public static void onPreInit(StateEvent.PreInitialization event) {
    CapabilityPlayerData.register();
  }
}
