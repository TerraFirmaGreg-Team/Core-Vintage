package com.example.examplemod;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.Mod;

import static su.terrafirmagreg.api.lib.Constants.MODID_AGEDDRINKS;

@Config(modid = MODID_AGEDDRINKS, category = "")
@Mod.EventBusSubscriber(modid = MODID_AGEDDRINKS)
@Config.LangKey("config." + MODID_AGEDDRINKS)
public class AgedDrinksConfig {

    @Config.Comment("General settings")
    @Config.LangKey("config." + MODID_AGEDDRINKS + ".general")
    public static final GeneralCFG General = new GeneralCFG();

    public static class GeneralCFG {

        @Config.RequiresMcRestart
        @Config.Comment("If enabled, you can get drunk from drinking aged alcohol. Try TFC Medicinal for a nice way to combat this.")
        @Config.LangKey("config." + MODID_AGEDDRINKS + ".general.enableDrunkness")
        public boolean enableDrunkness = true;

        @Config.RequiresMcRestart
        @Config.Comment("How many ticks does the haste effect last?")
        @Config.LangKey("config." + MODID_AGEDDRINKS + ".general.hasteTicks")
        public int hasteTicks = 9600;

        @Config.RequiresMcRestart
        @Config.Comment("What level should the haste effect be? 0 = I, 1 = II, 2 = III ...")
        @Config.LangKey("config." + MODID_AGEDDRINKS + ".general.hasteLevel")
        public int hasteLevel = 1;

        @Config.RequiresMcRestart
        @Config.Comment("How many ticks does the speed effect last?")
        @Config.LangKey("config." + MODID_AGEDDRINKS + ".general.speedTicks")
        public int speedTicks = 9600;

        @Config.RequiresMcRestart
        @Config.Comment("What level should the speed effect be? 0 = I, 1 = II, 2 = III ...")
        @Config.LangKey("config." + MODID_AGEDDRINKS + ".general.speedLevel")
        public int speedLevel = 1;

        @Config.RequiresMcRestart
        @Config.Comment("How many ticks does the jump boost effect last?")
        @Config.LangKey("config." + MODID_AGEDDRINKS + ".general.jumpBoostTicks")
        public int jumpBoostTicks = 9600;

        @Config.RequiresMcRestart
        @Config.Comment("What level should the jump boost effect be? 0 = I, 1 = II, 2 = III ...")
        @Config.LangKey("config." + MODID_AGEDDRINKS + ".general.jumpBoostLevel")
        public int jumpBoostLevel = 1;

        @Config.RequiresMcRestart
        @Config.Comment("How many ticks does the night vision effect last?")
        @Config.LangKey("config." + MODID_AGEDDRINKS + ".general.strengthTicks")
        public int strengthTicks = 9600;

        @Config.RequiresMcRestart
        @Config.Comment("What level should the strength effect be? 0 = I, 1 = II, 2 = III ...")
        @Config.LangKey("config." + MODID_AGEDDRINKS + ".general.strengthLevel")
        public int strengthLevel = 0;

        @Config.RequiresMcRestart
        @Config.Comment("How many ticks does the resistance effect last?")
        @Config.LangKey("config." + MODID_AGEDDRINKS + ".general.resistanceTicks")
        public int resistanceTicks = 9600;

        @Config.RequiresMcRestart
        @Config.Comment("What level should the resistance effect be? 0 = I, 1 = II, 2 = III ...")
        @Config.LangKey("config." + MODID_AGEDDRINKS + ".general.resistanceLevel")
        public int resistanceLevel = 0;
    }
}
