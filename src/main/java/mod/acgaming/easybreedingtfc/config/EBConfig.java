package mod.acgaming.easybreedingtfc.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.easybreedingtfc.EasyBreedingTFC;

@Config(modid = EasyBreedingTFC.MOD_ID, name = "EasyBreedingTFC")
public class EBConfig
{
    @Config.Name("Search distance")
    @Config.Comment("The distance for animals to search for food")
    public static double searchDistance = 10;

    @Mod.EventBusSubscriber(modid = EasyBreedingTFC.MOD_ID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(EasyBreedingTFC.MOD_ID))
            {
                ConfigManager.sync(EasyBreedingTFC.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}