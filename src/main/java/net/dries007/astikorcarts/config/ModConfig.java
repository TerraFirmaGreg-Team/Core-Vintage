package net.dries007.astikorcarts.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RangeDouble;
import net.minecraftforge.common.config.Config.RequiresMcRestart;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.astikorcarts.AstikorCarts;

import static su.terrafirmagreg.api.data.enums.Mods.Names.TFC;

@Config(modid = AstikorCarts.MODID)
public class ModConfig {

  @LangKey("config.astikorcarts:speedmodifier")
  @RequiresMcRestart
  @RangeDouble(min = -1.0D, max = 0.0D)
  public static double speedModifier = -0.65D;

  @LangKey("entity.astikorcarts:cargocart.name")
  public static CargoCart cargoCart = new CargoCart();

  @LangKey("entity.astikorcarts:plowcart.name")
  public static PlowCart plowCart = new PlowCart();

  @LangKey("entity.astikorcarts:mobcart.name")
  public static MobCart mobCart = new MobCart();

  public static class CargoCart {

    public String[] canPull = {
      "minecraft:donkey",
      "minecraft:horse",
      "minecraft:mule",
      "minecraft:pig",
      "minecraft:player",
      "tfg:animal/camel",
      "tfg:animal/donkey",
      "tfg:animal/horse",
      "tfg:animal/mule"
    };
  }

  public static class PlowCart {

    public String[] canPull = {
      "minecraft:donkey",
      "minecraft:horse",
      "minecraft:mule",
      "minecraft:pig",
      "minecraft:player",
      "tfg:animal/camel",
      "tfg:animal/donkey",
      "tfg:animal/horse",
      "tfg:animal/mule"
    };
  }

  public static class MobCart {

    public String[] canPull = {
      "minecraft:donkey",
      "minecraft:horse",
      "minecraft:mule",
      "minecraft:pig",
      "minecraft:player",
      "tfg:animal/camel",
      "tfg:animal/donkey",
      "tfg:animal/horse",
      "tfg:animal/mule"
    };
  }

  @Mod.EventBusSubscriber(modid = TFC)
  private static class EventHandler {

    @SubscribeEvent
    public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
      if (event.getModID().equals(AstikorCarts.MODID)) {
        ConfigManager.sync(AstikorCarts.MODID, Config.Type.INSTANCE);
      }
    }
  }
}
