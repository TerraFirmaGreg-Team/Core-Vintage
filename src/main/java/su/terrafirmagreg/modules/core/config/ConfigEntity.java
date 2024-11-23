package su.terrafirmagreg.modules.core.config;

import net.minecraftforge.common.config.Config;

public final class ConfigEntity {

  @Config.Comment("Player settings")
  public final Player PLAYER = new Player();

  public static final class Player {

    @Config.RequiresMcRestart
    @Config.Comment("The hunger value with which a player respawns. Default = 100")
    @Config.RangeInt(min = 0, max = 100)
    public int respawnHungerLevel = 100;

    @Config.RequiresMcRestart
    @Config.Comment("The thirst value with which a player respawns. Default = 100")
    @Config.RangeInt(min = 0, max = 100)
    public int respawnThirstLevel = 100;

    @Config.Comment("Modifier for passive exhaustion (exhaustion that naturally occurs just by living). 1.0 = full hunger bar once 2.5 minecraft days.")
    @Config.RangeDouble(min = 0, max = 100)
    public double passiveExhaustionMultiplier = 1;

    @Config.Comment("Delay (in ticks) for drinking water by hand")
    @Config.RangeInt(min = 1)
    public int drinkDelay = 12;

    @Config.Comment("Chance that a salty drink apply a thirst effect")
    @Config.RangeDouble(min = 0, max = 1)
    public double chanceThirstOnSaltyDrink = 0.25;

    @Config.Comment("Should the player receive passive regeneration of health, food, and thirst, while in peaceful mode similar to vanilla?")
    public boolean peacefulDifficultyPassiveRegeneration = false;

    @Config.Comment("How quickly the players becomes thirsty when hunger is drained by actions/sprinting? 100 = full thirst bar.")
    @Config.RangeDouble(min = 0, max = 100)
    public double thirstModifier = 8.0;

    @Config.Comment({"Modifier for how quickly the player will naturally regenerate health.",
                     "When on full hunger and thirst bars, 1.0 = 3HP / 5 secs."})
    @Config.RangeDouble(min = 0, max = 100)
    public double naturalRegenerationModifier = 1.0;
  }
}
