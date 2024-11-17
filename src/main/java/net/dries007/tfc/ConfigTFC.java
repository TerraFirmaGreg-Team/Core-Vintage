package net.dries007.tfc;

import su.terrafirmagreg.api.data.enums.InventoryCraftingMode;
import su.terrafirmagreg.api.data.enums.QuiverSearch;
import su.terrafirmagreg.api.data.enums.TimeTooltipMode;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfc.util.Alloy;

import static su.terrafirmagreg.api.data.Reference.MODID_TFC;

/**
 * Top level items must be static, the subclasses' fields must not be static.
 */
@Mod.EventBusSubscriber(modid = MODID_TFC)
public final class ConfigTFC {

  @SubscribeEvent
  public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
    if (event.getModID().equals(MODID_TFC)) {
      TerraFirmaCraft.getLog().warn("Config changed");
      ConfigManager.sync(MODID_TFC, Config.Type.INSTANCE);
    }
  }

  @Config(modid = MODID_TFC, category = "general", name = "TerraFirmaCraft - General")
  @Config.LangKey("config." + MODID_TFC + ".general")
  public static final class General {

    @Config.Comment("Override settings")
    @Config.LangKey("config." + MODID_TFC + ".general.overrides")
    public static final OverridesCFG OVERRIDES = new OverridesCFG();

    @Config.Comment("Difficulty settings")
    @Config.LangKey("config." + MODID_TFC + ".general.difficulty")
    public static final DifficultyCFG DIFFICULTY = new DifficultyCFG();

    @Config.Comment("Tree settings")
    @Config.LangKey("config." + MODID_TFC + ".general.tree")
    public static final TreeCFG TREE = new TreeCFG();

    @Config.Comment("Spawn protection settings")
    @Config.LangKey("config." + MODID_TFC + ".general.spawn_protection")
    public static final SpawnProtectionCFG SPAWN_PROTECTION = new SpawnProtectionCFG();

    @Config.Comment("Player settings")
    @Config.LangKey("config." + MODID_TFC + ".general.player")
    public static final PlayerCFG PLAYER = new PlayerCFG();

    @Config.Comment("World regeneration settings")
    @Config.LangKey("config." + MODID_TFC + ".general.world_regen")
    public static final WorldRegenCFG WORLD_REGEN = new WorldRegenCFG();

    @Config.Comment("Food settings")
    @Config.LangKey("config." + MODID_TFC + ".general.food")
    public static final FoodCFG FOOD = new FoodCFG();

    @Config.Comment("Miscelaneous")
    @Config.LangKey("config." + MODID_TFC + ".general.misc")
    public static final MiscCFG MISC = new MiscCFG();

    public static final class OverridesCFG {

      @Config.Comment("Enable ingot pile placement in world.")
      @Config.LangKey("config." + MODID_TFC + ".general.overrides.enableIngotPiles")
      public boolean enableIngotPiles = true;

      @Config.Comment("Enable log pile placement in world.")
      @Config.LangKey("config." + MODID_TFC + ".general.overrides.enableLogPiles")
      public boolean enableLogPiles = true;

      @Config.Comment("Enable the creation of thatch beds.")
      @Config.LangKey("config." + MODID_TFC + ".general.overrides.enableThatchBed")
      public boolean enableThatchBed = true;

      @Config.Comment("Enable the creation of grass paths using TFC's shovels.")
      @Config.LangKey("config." + MODID_TFC + ".general.overrides.enableGrassPath")
      public boolean enableGrassPath = true;

      @Config.Comment("Enable the creation of farmland on TFC's soil.")
      @Config.LangKey("config." + MODID_TFC + ".general.overrides.enableHoeing")
      public boolean enableHoeing = true;

      @Config.Comment("Enable the creation of stone anvils.")
      @Config.LangKey("config." + MODID_TFC + ".general.overrides.enableStoneAnvil")
      public boolean enableStoneAnvil = true;

      @Config.Comment("Turn this off to disable TFC's registry replacement of Torches. This will disable them extihguishing over time.")
      @Config.LangKey("config." + MODID_TFC + ".general.overrides.enableTorchOverride")
      public boolean enableTorchOverride = true;

      @Config.Comment(
        "Turn this off to disable TFC's registry replacement of Ice and Snow blocks. This will remove their temperature based behavior.")
      @Config.LangKey("config." + MODID_TFC + ".general.overrides.enableFrozenOverrides")
      public boolean enableFrozenOverrides = true;

      @Config.Comment(
        "Number of ticks required for a torch to burn out (72000 = 1 in game hour = 50 seconds), default is 72 hours. Set to -1 to disable torch burnout.")
      @Config.RangeInt(min = -1)
      @Config.LangKey("config." + MODID_TFC + ".general.overrides.torchTime")
      public int torchTime = 72000;

      @Config.Comment("Overrides lava and water making vanilla stones to TFC variants.")
      @Config.LangKey("config." + MODID_TFC + ".general.overrides.enableLavaWaterPlacesTFCBlocks")
      public boolean enableLavaWaterPlacesTFCBlocks = true;

      @Config.Comment(
        "If true, TFC will try and force the `level-type` setting to `tfg:classic` during DedicatedServer startup or define it as default world type for clients.")
      @Config.LangKey("config." + MODID_TFC + ".general.overrides.forceTFCWorldType")
      public boolean forceTFCWorldType = true;

      @Config.Comment(
        "If true, TFC will override default ore gen file. Pack Makers: Disable this to keep your ore gen file from being reset to default.")
      @Config.LangKey("config." + MODID_TFC + ".general.overrides.forceDefaultOreGenFile")
      public boolean forceDefaultOreGenFile = true;

      @Config.Comment("Enable/Disable the vanilla recipe removal spam. False = Those recipes are left in place.")
      @Config.LangKey("config." + MODID_TFC + ".general.overrides.removeVanillaRecipes")
      public boolean removeVanillaRecipes = true;

      @Config.Comment(
        "Enable/Disable the vanilla loot entries that conflict with TFC (ie: potatoes). False = Those loot entries are left in place.")
      @Config.LangKey("config." + MODID_TFC + ".general.overrides.removeVanillaLoots")
      public boolean removeVanillaLoots = true;

      @Config.Comment({"If true, this will force the gamerule naturalRegeneration to be false. ",
                       "Note: this DOES NOT AFFECT TFC's natural regeneration.",
                       "If you set naturalRegeneration to true, then you will have both TFC regeneration and normal vanilla regeneration (which is much faster)"})
      @Config.LangKey("config." + MODID_TFC + ".general.overrides.forceNoVanillaNaturalRegeneration")
      public boolean forceNoVanillaNaturalRegeneration = true;

      @Config.Comment({
        "If true, this will replace vanilla animals with the TFC counterpart under any spawning circumstances (ie: mob spawner, etc)."})
      @Config.LangKey("config." + MODID_TFC + ".general.overrides.forceReplaceVanillaAnimals")
      public boolean forceReplaceVanillaAnimals = true;
    }

    public static final class DifficultyCFG {

      @Config.Comment(
        "This controls how many animals (any kind) are loaded (spawned) / player. Higher values means more animals near a player, which in turn raises difficulty and meat abundance (caution, a value too high can also negatively impact performance).")
      @Config.RangeInt(min = 0)
      @Config.LangKey("config." + MODID_TFC + ".general.difficulty.animalSpawnCount")
      public int animalSpawnCount = 75;

      @Config.Comment(
        "This controls how many mobs are loaded (spawned) / player. Higher values means more mobs near a player, which in turn raises difficulty (caution, a value too high can also negatively impact performance)")
      @Config.RangeInt(min = 0)
      @Config.LangKey("config." + MODID_TFC + ".general.difficulty.mobSpawnCount")
      public int mobSpawnCount = 140;

      @Config.Comment("Prevent mob spawning on surface?")
      @Config.LangKey("config." + MODID_TFC + ".general.difficulty.preventMobsOnSurface")
      public boolean preventMobsOnSurface = true;

      @Config.Comment("Give wrought iron weapons to vanilla mobs?")
      @Config.LangKey("config." + MODID_TFC + ".general.difficulty.giveVanillaMobsEquipment")
      public boolean giveVanillaMobsEquipment = true;
    }

    public static final class TreeCFG {

      @Config.Comment({"Enable trees being fully cut by axes.",
                       "Disable it if you want other mods to handle tree felling."})
      @Config.LangKey("config." + MODID_TFC + ".general.tree.enableFelling")
      public boolean enableFelling = true;

      @Config.Comment("Enable smacking logs with a hammer giving sticks.")
      @Config.LangKey("config." + MODID_TFC + ".general.tree.enableHammerSticks")
      public boolean enableHammerSticks = true;

      @Config.Comment(
        "Should logs require tools (axes and saws, or hammers for sticks) to be cut? If false, breaking logs with an empty hand will not drop logs.")
      @Config.LangKey("config." + MODID_TFC + ".general.tree.requiresAxe")
      public boolean requiresAxe = true;

      @Config.Comment("If false, leaves will not drop saplings.")
      @Config.LangKey("config." + MODID_TFC + ".general.tree.enableSaplings")
      public boolean enableSaplings = true;

      @Config.Comment("Chance per log for an item to drop when using a stone axe.")
      @Config.RangeDouble(min = 0, max = 1)
      @Config.LangKey("config." + MODID_TFC + ".general.tree.stoneAxeReturnRate")
      public double stoneAxeReturnRate = 0.6;

      @Config.Comment("Normal leaf drop chance for sticks")
      @Config.RangeDouble(min = 0, max = 1)
      @Config.LangKey("config." + MODID_TFC + ".general.tree.leafStickDropChance")
      public double leafStickDropChance = 0.1;

      @Config.Comment("Chance that leaves will drop more sticks when harvested with configured tool classes.")
      @Config.RangeDouble(min = 0, max = 1)
      @Config.LangKey("config." + MODID_TFC + ".general.tree.leafStickDropChanceBonus")
      public double leafStickDropChanceBonus = 0.25;

      @Config.Comment("Tool classes that have the configured bonus to drop more sticks when harvesting leaves.")
      @Config.LangKey("config." + MODID_TFC + ".general.tree.leafStickDropChanceBonusClasses")
      public String[] leafStickDropChanceBonusClasses = new String[]{"knife", "scythe"};
    }

    public static final class SpawnProtectionCFG {

      @Config.Comment("Should living in a chunk prevent hostile mob spawning over time?")
      @Config.LangKey("config." + MODID_TFC + ".general.spawn_protection.preventMobs")
      public boolean preventMobs = true;

      @Config.Comment("Should living in a chunk prevent predators spawning over time?")
      @Config.LangKey("config." + MODID_TFC + ".general.spawn_protection.preventPredators")
      public boolean preventPredators = true;

      @Config.Comment("The min Y value a spawn has to be for spawn protection to prevent mobs. Anything below it will not be prevented.")
      @Config.RangeInt(min = 1, max = 255)
      @Config.LangKey("config." + MODID_TFC + ".general.spawn_protection.minYMobs")
      public int minYMobs = 100;

      @Config.Comment("The max Y value a spawn has to be for spawn protection to prevent mobs. Anything above it will not be prevented.")
      @Config.RangeInt(min = 1, max = 255)
      @Config.LangKey("config." + MODID_TFC + ".general.spawn_protection.maxYMobs")
      public int maxYMobs = 255;

      @Config.Comment("The min Y value a spawn has to be for spawn protection to prevent predators. Anything below it will not be prevented.")
      @Config.RangeInt(min = 1, max = 255)
      @Config.LangKey("config." + MODID_TFC + ".general.spawn_protection.minYPredators")
      public int minYPredators = 100;

      @Config.Comment("The max Y value a spawn has to be for spawn protection to prevent predators. Anything above it will not be prevented.")
      @Config.RangeInt(min = 1, max = 255)
      @Config.LangKey("config." + MODID_TFC + ".general.spawn_protection.maxYPredators")
      public int maxYPredators = 255;
    }

    public static final class PlayerCFG {

      @Config.Comment("Enable a 3x3 crafting inventory via key binding.")
      @Config.LangKey("config." + MODID_TFC + ".general.player.inventoryCraftingMode")
      public InventoryCraftingMode inventoryCraftingMode = InventoryCraftingMode.ENABLED;

      @Config.Comment({"Minimum health modifier player can obtain with low nutrition.",
                       "Example 1(Vanilla): 20 * 0.2(mod) = 4 (or 2 hearts).",
                       "Example 2(TFC): 1000 * 0.2(mod) = 200."})
      @Config.RangeDouble(min = 0.1, max = 1)
      @Config.LangKey("config." + MODID_TFC + ".general.player.minHealthModifier")
      public double minHealthModifier = 0.2;

      @Config.Comment({"Maximum health modifier player can obtain with high nutrition.",
                       "Example 1(Vanilla): 20 * 3(mod) = 60 (or 30 hearts).",
                       "Example 2(TFC): 1000 * 3(mod) = 3000."})
      @Config.RangeDouble(min = 1, max = 5)
      @Config.LangKey("config." + MODID_TFC + ".general.player.maxHealthModifier")
      public double maxHealthModifier = 3;

      @Config.Comment("How quickly the players becomes thirsty when hunger is drained by actions/sprinting? 100 = full thirst bar.")
      @Config.RangeDouble(min = 0, max = 100)
      @Config.LangKey("config." + MODID_TFC + ".general.player.thirstModifier")
      public double thirstModifier = 8.0;

      @Config.Comment({"Modifier for how quickly the player will naturally regenerate health.",
                       "When on full hunger and thirst bars, 1.0 = 3HP / 5 secs."})
      @Config.LangKey("config." + MODID_TFC + ".general.player.naturalRegenerationModifier")
      @Config.RangeDouble(min = 0, max = 100)
      public double naturalRegenerationModifier = 1.0;

      @Config.Comment("Should the player receive passive regeneration of health, food, and thirst, while in peaceful mode similar to vanilla?")
      @Config.LangKey("config." + MODID_TFC + ".general.player.peacefulDifficultyPassiveRegeneration")
      public boolean peacefulDifficultyPassiveRegeneration = false;

      @Config.Comment(
        "Modifier for passive exhaustion (exhaustion that naturally occurs just by living). 1.0 = full hunger bar once 2.5 minecraft days.")
      @Config.LangKey("config." + MODID_TFC + ".general.player.passiveExhaustionMultiplier")
      @Config.RangeDouble(min = 0, max = 100)
      public double passiveExhaustionMultiplier = 1;

      @Config.Comment({
        "The amount of replenished hunger before the player's nutrition will lose the first food consumed. Most TFC foods have 4 hunger.",
        "Example: Multiply Vanilla hunger(20) by 4 to get one food bar worth of food before the first food is lost from the cycle."})
      @Config.LangKey("config." + MODID_TFC + ".general.player.nutritionRotationHungerWindow")
      @Config.RangeInt(min = 4)
      public int nutritionRotationHungerWindow = 4 * 20;

      @Config.Comment("Delay (in ticks) for drinking water by hand")
      @Config.LangKey("config." + MODID_TFC + ".general.player.drinkDelay")
      @Config.RangeInt(min = 1)
      public int drinkDelay = 12;

      @Config.Comment("Chance that a salty drink apply a thirst effect")
      @Config.LangKey("config." + MODID_TFC + ".general.player.chanceThirstOnSaltyDrink")
      @Config.RangeDouble(min = 0, max = 1)
      public double chanceThirstOnSaltyDrink = 0.25;

      @Config.Comment("Which inventory slots will ammo refill/pickup search for quivers?")
      @Config.LangKey("config." + MODID_TFC + ".general.player.quiverSearch")
      public QuiverSearch quiverSearch = QuiverSearch.HOTBAR;
    }

    public static final class WorldRegenCFG {

      @Config.Comment(
        "The minimum time for a chunk to be unoccupied for it's resources (berry bushes, debris and crops) to naturally regenerate. (In days). After this amount, regeneration will scale up based on how long since this duration, up to a maximum of 4x.")
      @Config.RangeInt(min = 12, max = 1000)
      @Config.LangKey("config." + MODID_TFC + ".general.world_regen.minimumTime")
      public int minimumTime = 24;

      @Config.Comment("The weight for loose rocks and sticks regeneration in the world.")
      @Config.RangeDouble(min = 0, max = 1)
      @Config.LangKey("config." + MODID_TFC + ".general.world_regen.sticksRocksModifier")
      public double sticksRocksModifier = 0.5;

      @Config.Comment("Minimum server tps to allow chunk regeneration in the spring")
      @Config.RangeInt(min = 0, max = 20)
      @Config.LangKey("config." + MODID_TFC + ".general.world_regen.minRegenTps")
      public int minRegenTps = 16;
    }

    public static final class FoodCFG {

      @Config.Comment("If false, crops will never die under any circumstances. THIS DOES NOT MEAN THEY WILL ALWAYS GROW!")
      @Config.LangKey("config." + MODID_TFC + ".general.food.enableCropDeath")
      public boolean enableCropDeath = true;

      @Config.Comment("Defines wild crops rarity to generate, in 1 / N chunks. 0 = Disable")
      @Config.RangeInt(min = 0)
      @Config.LangKey("config." + MODID_TFC + ".general.food.cropRarity")
      public int cropRarity = 30;

      @Config.Comment("Defines berry bush rarity to generate, in 1 / N chunks. 0 = Disable")
      @Config.RangeInt(min = 0)
      @Config.LangKey("config." + MODID_TFC + ".general.food.berryBushRarity")
      public int berryBushRarity = 80;

      @Config.Comment("Defines fruit tree rarity to generate, in 1 / N chunks. 0 = Disable")
      @Config.RangeInt(min = 0)
      @Config.LangKey("config." + MODID_TFC + ".general.food.fruitTreeRarity")
      public int fruitTreeRarity = 80;

      @Config.Comment("Modifier for how long crops take to grow.")
      @Config.RangeDouble(min = 0.01, max = 100)
      @Config.LangKey("config." + MODID_TFC + ".general.food.cropGrowthTimeModifier")
      public double cropGrowthTimeModifier = 1.0;

      @Config.Comment("Modifier for how long berry bushes take to grow fruits.")
      @Config.RangeDouble(min = 0.01, max = 100)
      @Config.LangKey("config." + MODID_TFC + ".general.food.berryBushGrowthTimeModifier")
      public double berryBushGrowthTimeModifier = 1.0;

      @Config.Comment("Modifier for how long fruit trees take to grow trunks / leaves / fruits.")
      @Config.RangeDouble(min = 0.01, max = 100)
      @Config.LangKey("config." + MODID_TFC + ".general.food.fruitTreeGrowthTimeModifier")
      public double fruitTreeGrowthTimeModifier = 1.0;
    }

    public static final class MiscCFG {

      @Config.Comment("Chance for a plant to grow each random tick, does not include crops. Lower = slower growth.")
      @Config.RangeDouble(min = 0, max = 1)
      @Config.LangKey("config." + MODID_TFC + ".general.misc.plantGrowthRate")
      public double plantGrowthRate = 0.01;

      @Config.Comment("Leaf block movement modifier. Lower = Slower, Higher = Faster. 1 = No slow down. (Speed * this = slow).")
      @Config.RangeDouble(min = 0, max = 1)
      @Config.LangKey("config." + MODID_TFC + ".general.misc.leafMovementModifier")
      public double leafMovementModifier = 0.1;

      @Config.Comment("Berry bush movement modifier. Lower = Slower, Higher = Faster. 1 = No slow down. (Speed * this = slow).")
      @Config.RangeDouble(min = 0, max = 1)
      @Config.LangKey("config." + MODID_TFC + ".general.misc.berryBushMovementModifier")
      public double berryBushMovementModifier = 0.1;

      @Config.Comment("Generic snow movement modifier. Lower = Slower, Higher = Faster. 1 = No slow down. (Speed * this = slow).")
      @Config.RangeDouble(min = 0, max = 1)
      @Config.LangKey("config." + MODID_TFC + ".general.misc.snowMovementModifier")
      public double snowMovementModifier = 0.85;

      @Config.Comment(
        "Generic movement modifier. Lower = Slower, Higher = Faster. 1 = No slow down. Note: this is a little different than other densities (leaf / berry bush), because this actually functions as a maximum slow down. Actual value is dependent on the plant and it's age.")
      @Config.RangeDouble(min = 0, max = 1)
      @Config.LangKey("config." + MODID_TFC + ".general.misc.minimumPlantMovementModifier")
      public double minimumPlantMovementModifier = 0;

      @Config.Comment({"Chance that mining a raw stone will drop a gem.",
                       "Gem grade is random from: 16/31 Chipped, 8/31 Flawed, 4/31 Normal, 2/31 Flawless and 1/31 Exquisite."})
      @Config.RangeDouble(min = 0, max = 1)
      @Config.LangKey("config." + MODID_TFC + ".general.misc.stoneGemDropChance")
      public double stoneGemDropChance = 31.0 / 8000.0; // 0.003875

      @Config.Comment("Chance for the fire starter to be successful")
      @Config.RangeDouble(min = 0d, max = 1d)
      @Config.LangKey("config." + MODID_TFC + ".general.misc.fireStarterChance")
      public double fireStarterChance = 0.5;

      @Config.Comment("Add iron ore dictionary (ie: ingotIron, oreIron) to wrought iron items?")
      @Config.LangKey("config." + MODID_TFC + ".general.misc.dictionaryIron")
      public boolean dictionaryIron = false;

      @Config.Comment("Add plate ore dictionary (plateIron, plateBronze) to sheets?")
      @Config.LangKey("config." + MODID_TFC + ".general.misc.dictionaryPlates")
      public boolean dictionaryPlates = false;

      @Config.Comment("List of fluids allowed to be picked up by blue steel bucket")
      @Config.LangKey("config." + MODID_TFC + ".general.misc.blueSteelBucketWhitelist")
      public String[] blueSteelBucketWhitelist = new String[]{"lava"};

      @Config.Comment("List of fluids allowed to be picked up by red steel bucket")
      @Config.LangKey("config." + MODID_TFC + ".general.misc.redSteelBucketWhitelist")
      public String[] redSteelBucketWhitelist = new String[]{"fresh_water", "hot_water", "salt_water", "water"};

      @Config.Comment("Entities that can be plucked for feathers.")
      @Config.LangKey("config." + MODID_TFC + ".general.misc.pluckableEntities")
      public String[] pluckableEntities = new String[]{"tfg:chicken", "tfg:pheasant", "tfg:parrot", "tfg:duck", "tfg:grouse",
                                                       "tfg:pheasant", "tfg:quail", "tfg:turkey"};

      @Config.Comment("Damage dealt to an entity when a feather is harvested.")
      @Config.RangeDouble(min = 0)
      @Config.LangKey("config." + MODID_TFC + ".general.misc.damagePerFeather")
      public double damagePerFeather = 0.6;

      @Config.Comment("This controls the time it takes to mine rock blocks. 1.0 = Like vanilla, 10.0 = Classic TFC")
      @Config.RangeDouble(min = 1, max = 1000)
      @Config.LangKey("config." + MODID_TFC + ".general.misc.rockMiningTimeModifier")
      public double rockMiningTimeModifier = 10.0;

      @Config.Comment("This controls the time it takes to mine log blocks. 1.0 = Like vanilla, 7.5 = Classic TFC")
      @Config.RangeDouble(min = 1, max = 1000)
      @Config.LangKey("config." + MODID_TFC + ".general.misc.logMiningTimeModifier")
      public double logMiningTimeModifier = 7.5;
    }
  }

  @Config(modid = MODID_TFC, category = "devices", name = "TerraFirmaCraft - Devices")
  @Config.LangKey("config." + MODID_TFC + ".devices")
  public static final class Devices {

    @Config.Comment("Barrel")
    @Config.LangKey("config." + MODID_TFC + ".devices.barrel")
    public static final BarrelCFG BARREL = new BarrelCFG();

    @Config.Comment("Chisel")
    @Config.LangKey("config." + MODID_TFC + ".devices.chisel")
    public static final ChiselCFG CHISEL = new ChiselCFG();

    @Config.Comment("Small Vessel")
    @Config.LangKey("config." + MODID_TFC + ".devices.small_vessel")
    public static final SmallVesselCFG SMALL_VESSEL = new SmallVesselCFG();

    @Config.Comment("Sluice")
    @Config.LangKey("config." + MODID_TFC + ".devices.sluice")
    public static final SluiceCFG SLUICE = new SluiceCFG();

    @Config.Comment("Jug")
    @Config.LangKey("config." + MODID_TFC + ".devices.jug")
    public static final JugCFG JUG = new JugCFG();

    @Config.Comment("GoldPan")
    @Config.LangKey("config." + MODID_TFC + ".devices.goldpan")
    public static final GoldPanCFG GOLD_PAN = new GoldPanCFG();

    public static final class BarrelCFG {

      @Config.Comment("How much fluid (mB) can a barrel hold?")
      @Config.RangeInt(min = 100)
      @Config.LangKey("config." + MODID_TFC + ".devices.barrel.tank")
      public int tank = 10_000;

      @Config.Comment("List of fluids allowed to be inserted into a barrel.")
      @Config.LangKey("config." + MODID_TFC + ".devices.barrel.fluidWhitelist")
      public String[] fluidWhitelist = new String[]{"fresh_water", "hot_water", "salt_water", "water", "limewater", "tannin", "olive_oil",
                                                    "olive_oil_water", "vinegar", "rum", "beer", "whiskey", "rye_whiskey", "corn_whiskey", "sake", "vodka",
                                                    "cider", "brine", "milk",
                                                    "milk_curdled", "milk_vinegar", "white_dye", "orange_dye", "magenta_dye", "light_blue_dye", "yellow_dye",
                                                    "lime_dye", "pink_dye",
                                                    "gray_dye", "light_gray_dye", "cyan_dye", "purple_dye", "blue_dye", "brown_dye", "green_dye", "red_dye",
                                                    "black_dye",
                                                    "distilled_water", "waste", "base_potash_liquor", "white_tea", "green_tea", "black_tea", "chamomile_tea",
                                                    "dandelion_tea",
                                                    "labrador_tea", "coffee", "agave_wine", "barley_wine", "banana_wine", "berry_wine", "cherry_wine",
                                                    "juniper_wine", "lemon_wine",
                                                    "orange_wine", "papaya_wine", "peach_wine", "pear_wine", "plum_wine", "mead", "red_wine", "wheat_wine",
                                                    "white_wine", "calvados",
                                                    "gin", "tequila", "shochu", "grappa", "banana_brandy", "cherry_brandy", "lemon_brandy", "orange_brandy",
                                                    "papaya_brandy",
                                                    "peach_brandy", "pear_brandy", "plum_brandy", "berry_brandy", "brandy", "cognac", "beer_barley",
                                                    "beer_corn", "beer_rye",
                                                    "beer_wheat", "beer_amaranth", "beer_buckwheat", "beer_fonio", "beer_millet", "beer_quinoa", "beer_spelt",
                                                    "sugar_water",
                                                    "honey_water", "rice_water", "soybean_water", "linseed_water", "rape_seed_water", "sunflower_seed_water",
                                                    "opium_poppy_seed_water", "sugar_beet_water", "soy_milk", "linseed_oil", "rape_seed_oil",
                                                    "sunflower_seed_oil",
                                                    "opium_poppy_seed_oil", "wort", "firma_cola", "juice_blackberry", "juice_blueberry", "juice_bunch_berry",
                                                    "juice_cloud_berry",
                                                    "juice_cranberry", "juice_elderberry", "juice_gooseberry", "juice_raspberry", "juice_snow_berry",
                                                    "juice_strawberry",
                                                    "juice_wintergreen_berry", "juice_agave", "juice_apple", "juice_banana", "juice_cherry", "juice_lemon",
                                                    "juice_orange",
                                                    "juice_papaya", "juice_peach", "juice_pear", "juice_plum", "juice_juniper", "juice_green_grape",
                                                    "juice_purple_grape",
                                                    "juice_barrel_cactus", "yeast_starter", "coconut_milk", "yak_milk", "zebu_milk", "goat_milk",
                                                    "curdled_goat_milk",
                                                    "curdled_yak_milk", "pina_colada"};
    }

    public static final class ChiselCFG {

      @Config.Comment("Does the chisel have a delay on use?")
      @Config.LangKey("config." + MODID_TFC + ".devices.chisel.hasDelay")
      public boolean hasDelay = false;

      @Config.Comment("If true, hammer must be in offhand for chisel use. If false, hammer can be in offhand or toolbar.")
      @Config.LangKey("config." + MODID_TFC + ".devices.chisel.requireHammerInOffHand")
      public boolean requireHammerInOffHand = true;
    }

    public static final class SmallVesselCFG {

      @Config.Comment("How much metal (units / mB) can a small vessel hold?")
      @Config.RangeInt(min = 100, max = Alloy.SAFE_MAX_ALLOY)
      @Config.LangKey("config." + MODID_TFC + ".devices.small_vessel.tank")
      public int tank = 4_000;
    }

    public static final class SluiceCFG {

      @Config.Comment({"The amount of times a chunk can be worked (300 = default, 0 = disable).",
                       "Note: While sluices increase work by 1, Goldpan increase by 6."})
      @Config.LangKey("config." + MODID_TFC + ".devices.sluice.maxWorkChunk")
      @Config.RangeInt(min = 0, max = 10_000)
      public int maxWorkChunk = 300;

      @Config.Comment("The radius sluice works, in chunks.")
      @Config.LangKey("config." + MODID_TFC + ".devices.sluice.radius")
      @Config.RangeInt(min = 0, max = 10)
      public int radius = 1;

      @Config.Comment("The amount of ticks a sluice uses to work.")
      @Config.LangKey("config." + MODID_TFC + ".devices.sluice.ticks")
      @Config.RangeInt(min = 20)
      public int ticks = 100;

      @Config.Comment("Chance that a sluice operation produce small ore.")
      @Config.RangeDouble(min = 0, max = 1)
      @Config.LangKey("config." + MODID_TFC + ".devices.sluice.oreChance")
      public double oreChance = 0.05;

      @Config.Comment("Chance that a sluice operation produce gems.")
      @Config.RangeDouble(min = 0, max = 1)
      @Config.LangKey("config." + MODID_TFC + ".devices.sluice.gemChance")
      public double gemChance = 0.05;

      @Config.Comment("Chance that a diamond is dropped when sluice produce gems.")
      @Config.RangeDouble(min = 0, max = 1)
      @Config.LangKey("config." + MODID_TFC + ".devices.sluice.diamondGemChance")
      public double diamondGemChance = 0.01;
    }

    public static final class JugCFG {

      @Config.Comment(
        "Enable Shift-Right clicking of Jars to dump water out. Enabling this will not pour an actual water block onto the ground.")
      @Config.LangKey("config." + MODID_TFC + ".devices.jug.dumpWaterOnShiftRightClick")
      public boolean dumpWaterOnShiftRightClick = true;
    }

    public static final class GoldPanCFG {

      @Config.Comment("Cooldown ticks after panning.")
      @Config.LangKey("config." + MODID_TFC + ".devices.gold_pan.cooldownTicks")
      public int cooldownTicks = 20;
    }

  }

  @Config(modid = MODID_TFC, category = "client", name = "TerraFirmaCraft - Client")
  @Config.LangKey("config." + MODID_TFC + ".client")
  public static class Client {

    @Config.Comment("Tooltip settings")
    @Config.LangKey("config." + MODID_TFC + ".client.tooltip")
    public static final TooltipCFG TOOLTIP = new TooltipCFG();

    @Config.Comment("Render settings")
    @Config.LangKey("config." + MODID_TFC + ".client.render")
    public static final RenderCFG RENDER = new RenderCFG();

    public static final class TooltipCFG {

      @Config.Comment({"Show ItemStack tool classes when advanced tooltips are enabled. (F3+H)"})
      @Config.LangKey("config." + MODID_TFC + ".client.tooltip.showToolClassTooltip")
      public boolean showToolClassTooltip = true;

      @Config.Comment({"Show ItemStack OreDictionary matches when advanced tooltips are enabled. (F3+H)"})
      @Config.LangKey("config." + MODID_TFC + ".client.tooltip.showOreDictionaryTooltip")
      public boolean showOreDictionaryTooltip = true;

      @Config.Comment({"Show ItemStack NBT on the tooltip when advanced tooltips are enabled. (F3+H)"})
      @Config.LangKey("config." + MODID_TFC + ".client.tooltip.showNBTTooltip")
      public boolean showNBTTooltip = true;

      @Config.Comment("Should the prospectors pick output to the actionbar? (the space just above the hotbar)")
      @Config.LangKey("config." + MODID_TFC + ".client.tooltip.propickOutputToActionBar")
      public boolean propickOutputToActionBar = true;

      @Config.Comment("Should welding output to the actionbar? (the space just above the hotbar)")
      @Config.LangKey("config." + MODID_TFC + ".client.tooltip.anvilWeldOutputToActionBar")
      public boolean anvilWeldOutputToActionBar = true;

      @Config.Comment("Should vessels output to the actionbar? (the space just above the hotbar)")
      @Config.LangKey("config." + MODID_TFC + ".client.tooltip.vesselOutputToActionBar")
      public boolean vesselOutputToActionBar = true;

      @Config.Comment("Should animal feedback output to the actionbar? (the space just above the hotbar)")
      @Config.LangKey("config." + MODID_TFC + ".client.tooltip.animalsOutputToActionBar")
      public boolean animalsOutputToActionBar = true;

      @Config.Comment({"Time tooltip info mode."})
      @Config.LangKey("config." + MODID_TFC + ".client.tooltip.timeTooltipMode")
      public TimeTooltipMode timeTooltipMode = TimeTooltipMode.MINECRAFT_HOURS;
    }

    public static class RenderCFG {

      @Config.Comment("Render distance for flat placed items (rocks, sticks). Default: 32 Blocks")
      @Config.LangKey("config." + MODID_TFC + ".client.render.placedItemFlatDistance")
      public int placedItemFlatDistance = 32;
    }
  }

}
