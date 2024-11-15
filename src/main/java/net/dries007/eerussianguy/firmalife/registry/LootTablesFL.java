package net.dries007.eerussianguy.firmalife.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.eerussianguy.firmalife.ConfigFL;

import java.util.Arrays;

import static su.terrafirmagreg.api.data.Reference.MODID_FL;

@Mod.EventBusSubscriber(modid = MODID_FL)

public class LootTablesFL {

  public static ResourceLocation RENNET_DROP;

  public LootTablesFL() {
  }

  public static void init() {
    RENNET_DROP = register("rennet_drop");
  }

  private static ResourceLocation register(String id) {
    return LootTableList.register(new ResourceLocation(MODID_FL, id));
  }

  @SubscribeEvent
  public static void onLootTableLoad(LootTableLoadEvent event) {
    if (Arrays.stream(ConfigFL.General.BALANCE.rennetLootTable).anyMatch(x -> x.equals(event.getName().getPath()))) {
      event.getTable()
           .addPool(event.getLootTableManager().getLootTableFromLocation(RENNET_DROP).getPool("rennet_drop"));
    }
  }
}
