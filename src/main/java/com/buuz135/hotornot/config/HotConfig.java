package com.buuz135.hotornot.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


import com.buuz135.hotornot.HotOrNot;
import com.buuz135.hotornot.network.PacketClientSettings;

import static com.buuz135.hotornot.HotOrNot.MOD_NAME;
import static su.terrafirmagreg.api.lib.Constants.MODID_HOTORNOT;

@EventBusSubscriber(modid = MODID_HOTORNOT)
@Config(modid = MODID_HOTORNOT, type = Type.INSTANCE, name = MOD_NAME)
public class HotConfig {

    @LangKey("config." + MODID_HOTORNOT + ".manual_entries")
    @Comment("Configuration for manually added items." +
            "Items are in the format <mod_id>:<registry_name> same as what you see in F3 + H")
    public static final ManualEntries MANUAL_ENTRIES = new ManualEntries();

    @LangKey("config." + MODID_HOTORNOT + ".temperature_values")
    @Comment("Configuration for the temperature thresholds the effects happen at. Values are in Celsius")
    public static final TemperatureValues TEMPERATURE_VALUES = new TemperatureValues();

    @LangKey("config." + MODID_HOTORNOT + ".effect_handling")
    @Comment("Configuration relating to effect handling")
    public static final EffectHandling EFFECT_HANDLING = new EffectHandling();

    @LangKey("config." + MODID_HOTORNOT + ".render_effect_tooltip")
    @Comment("If true, items causing effects will get a tooltip")
    public static boolean renderEffectTooltip = true;

    @SubscribeEvent
    public static void onConfigChanged(final OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID_HOTORNOT)) {
            ConfigManager.sync(MODID_HOTORNOT, Type.INSTANCE);
            // Update server in case this config changes
            HotOrNot.getNetwork().sendToServer(new PacketClientSettings(EFFECT_HANDLING.replaceBrokenHotHolder));
        }
    }
}
