package net.dries007.tfc;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


import net.dries007.tfc.client.GrassColorHandler;
import net.dries007.tfc.util.Alloy;
import net.dries007.tfc.util.config.HealthDisplayFormat;
import net.dries007.tfc.util.config.InventoryCraftingMode;
import net.dries007.tfc.util.config.QuiverSearch;
import net.dries007.tfc.util.config.TimeTooltipMode;

import static su.terrafirmagreg.data.Constants.MODID_TFC;

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
            GrassColorHandler.resetColors();
        }
    }

    @Config(modid = MODID_TFC, category = "general", name = "TerraFirmaCraft - General")
    @Config.LangKey("config." + MODID_TFC + ".general")
    public static final class General {

        @Config.Comment("Override settings")
        @Config.LangKey("config." + MODID_TFC + ".general.overrides")
        public static final OverridesCFG OVERRIDES = new OverridesCFG();

        @Config.Comment("Fallable settings")
        @Config.LangKey("config." + MODID_TFC + ".general.fallable")
        public static final FallableCFG FALLABLE = new FallableCFG();

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
                    "If true, TFC will try and force the `level-type` setting to `tfc_classic` during DedicatedServer startup or define it as default world type for clients.")
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

            @Config.Comment({ "If true, this will force the gamerule naturalRegeneration to be false. ",
                    "Note: this DOES NOT AFFECT TFC's natural regeneration.",
                    "If you set naturalRegeneration to true, then you will have both TFC regeneration and normal vanilla regeneration (which is much faster)" })
            @Config.LangKey("config." + MODID_TFC + ".general.overrides.forceNoVanillaNaturalRegeneration")
            public boolean forceNoVanillaNaturalRegeneration = true;

            @Config.Comment({
                    "If true, this will replace vanilla animals with the TFC counterpart under any spawning circumstances (ie: mob spawner, etc)." })
            @Config.LangKey("config." + MODID_TFC + ".general.overrides.forceReplaceVanillaAnimals")
            public boolean forceReplaceVanillaAnimals = true;
        }

        public static final class FallableCFG {

            @Config.Comment("If false, fallable blocks (ie: dirt, stone) will never fall.")
            @Config.LangKey("config." + MODID_TFC + ".general.fallable.enable")
            public boolean enable = true;

            @Config.Comment("If false, fallable blocks (ie: dirt, stone) will never destroy ore blocks.")
            @Config.LangKey("config." + MODID_TFC + ".general.fallable.destroyOres")
            public boolean destroyOres = true;

            @Config.Comment("If false, fallable blocks (ie: dirt, stone) will never destroy loose items.")
            @Config.LangKey("config." + MODID_TFC + ".general.fallable.destroyItems")
            public boolean destroyItems = true;

            @Config.Comment("If false, fallable blocks (ie: dirt, stone) will never hurt entities.")
            @Config.LangKey("config." + MODID_TFC + ".general.fallable.hurtEntities")
            public boolean hurtEntities = true;

            @Config.Comment("Chance that mining raw rocks triggers a collapse.")
            @Config.RangeDouble(min = 0, max = 1)
            @Config.LangKey("config." + MODID_TFC + ".general.fallable.collapseChance")
            public double collapseChance = 0.1;

            @Config.Comment("Chance that collapsing blocks propagate the collapse. Influenced by distance from epicenter of collapse.")
            @Config.RangeDouble(min = 0, max = 1)
            @Config.LangKey("config." + MODID_TFC + ".general.fallable.propagateCollapseChance")
            public double propagateCollapseChance = 0.55;

            @Config.Comment("Horizontal radius of the support range of support beams.")
            @Config.RangeInt(min = 0, max = 8)
            @Config.LangKey("config." + MODID_TFC + ".general.fallable.supportBeamRangeHor")
            public int supportBeamRangeHor = 4;

            @Config.Comment("Upwards support range of support beams.")
            @Config.RangeInt(min = 0, max = 3)
            @Config.LangKey("config." + MODID_TFC + ".general.fallable.supportBeamRangeUp")
            public int supportBeamRangeUp = 1;

            @Config.Comment("Downwards support range of support beams.")
            @Config.RangeInt(min = 0, max = 3)
            @Config.LangKey("config." + MODID_TFC + ".general.fallable.supportBeamRangeDown")
            public int supportBeamRangeDown = 1;

            @Config.Comment("Should chiseling raw stone blocks cause collapses?")
            @Config.LangKey("config." + MODID_TFC + ".general.fallable.chiselCausesCollapse")
            public boolean chiselCausesCollapse = true;

            @Config.Comment("Should exploding raw stone blocks cause collapses?")
            @Config.LangKey("config." + MODID_TFC + ".general.fallable.explosionCausesCollapse")
            public boolean explosionCausesCollapse = true;
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

            @Config.Comment("Range of pixels on either side of the working target that can be accepted to complete a smithing recipe")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".general.difficulty.acceptableAnvilRange")
            public int acceptableAnvilRange = 0;
        }

        public static final class TreeCFG {

            @Config.Comment({ "Enable trees being fully cut by axes.",
                    "Disable it if you want other mods to handle tree felling." })
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
            public String[] leafStickDropChanceBonusClasses = new String[] { "knife", "scythe" };
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

            @Config.Comment({ "Minimum health modifier player can obtain with low nutrition.",
                    "Example 1(Vanilla): 20 * 0.2(mod) = 4 (or 2 hearts).",
                    "Example 2(TFC): 1000 * 0.2(mod) = 200." })
            @Config.RangeDouble(min = 0.1, max = 1)
            @Config.LangKey("config." + MODID_TFC + ".general.player.minHealthModifier")
            public double minHealthModifier = 0.2;

            @Config.Comment({ "Maximum health modifier player can obtain with high nutrition.",
                    "Example 1(Vanilla): 20 * 3(mod) = 60 (or 30 hearts).",
                    "Example 2(TFC): 1000 * 3(mod) = 3000." })
            @Config.RangeDouble(min = 1, max = 5)
            @Config.LangKey("config." + MODID_TFC + ".general.player.maxHealthModifier")
            public double maxHealthModifier = 3;

            @Config.Comment("How quickly the players becomes thirsty when hunger is drained by actions/sprinting? 100 = full thirst bar.")
            @Config.RangeDouble(min = 0, max = 100)
            @Config.LangKey("config." + MODID_TFC + ".general.player.thirstModifier")
            public double thirstModifier = 8.0;

            @Config.Comment({ "Modifier for how quickly the player will naturally regenerate health.",
                    "When on full hunger and thirst bars, 1.0 = 3HP / 5 secs." })
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
                    "Example: Multiply Vanilla hunger(20) by 4 to get one food bar worth of food before the first food is lost from the cycle." })
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

            @Config.Comment(
                    "The default length of a month (in days) when a new world is started. This can be changed in existing worlds via the /timetfc command.")
            @Config.LangKey("config." + MODID_TFC + ".general.misc.defaultMonthLength")
            @Config.RangeInt(min = 1)
            public int defaultMonthLength = 8;

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

            @Config.Comment({ "Chance that mining a raw stone will drop a gem.",
                    "Gem grade is random from: 16/31 Chipped, 8/31 Flawed, 4/31 Normal, 2/31 Flawless and 1/31 Exquisite." })
            @Config.RangeDouble(min = 0, max = 1)
            @Config.LangKey("config." + MODID_TFC + ".general.misc.stoneGemDropChance")
            public double stoneGemDropChance = 31.0 / 8000.0; // 0.003875

            @Config.Comment("Chance for the fire starter to be successful")
            @Config.RangeDouble(min = 0d, max = 1d)
            @Config.LangKey("config." + MODID_TFC + ".general.misc.fireStarterChance")
            public double fireStarterChance = 0.5;

            @Config.Comment("The amount of metal contained in a small ore / nugget.")
            @Config.LangKey("config." + MODID_TFC + ".general.misc.smallOreMetalAmount")
            @Config.RangeInt(min = 1, max = 10_000)
            public int smallOreMetalAmount = 10;

            @Config.Comment("The amount of metal contained in a poor ore.")
            @Config.LangKey("config." + MODID_TFC + ".general.misc.poorOreMetalAmount")
            @Config.RangeInt(min = 1, max = 10_000)
            public int poorOreMetalAmount = 15;

            @Config.Comment("The amount of metal contained in a normal ore.")
            @Config.LangKey("config." + MODID_TFC + ".general.misc.normalOreMetalAmount")
            @Config.RangeInt(min = 1, max = 10_000)
            public int normalOreMetalAmount = 25;

            @Config.Comment("The amount of metal contained in a rich ore.")
            @Config.LangKey("config." + MODID_TFC + ".general.misc.richOreMetalAmount")
            @Config.RangeInt(min = 1, max = 10_000)
            public int richOreMetalAmount = 35;

            @Config.Comment("Add iron ore dictionary (ie: ingotIron, oreIron) to wrought iron items?")
            @Config.LangKey("config." + MODID_TFC + ".general.misc.dictionaryIron")
            public boolean dictionaryIron = false;

            @Config.Comment("Add plate ore dictionary (plateIron, plateBronze) to sheets?")
            @Config.LangKey("config." + MODID_TFC + ".general.misc.dictionaryPlates")
            public boolean dictionaryPlates = false;

            @Config.Comment("List of fluids allowed to be picked up by wooden bucket")
            @Config.LangKey("config." + MODID_TFC + ".general.misc.woodenBucketWhitelist")
            public String[] woodenBucketWhitelist = new String[] { "fresh_water", "hot_water", "salt_water", "water", "limewater", "tannin",
                    "olive_oil", "olive_oil_water", "vinegar", "rum", "beer", "whiskey", "rye_whiskey", "corn_whiskey", "sake", "vodka", "cider",
                    "brine", "milk", "milk_curdled", "milk_vinegar", "white_dye", "orange_dye", "magenta_dye", "light_blue_dye", "yellow_dye",
                    "lime_dye", "pink_dye", "gray_dye", "light_gray_dye", "cyan_dye", "purple_dye", "blue_dye", "brown_dye", "green_dye", "red_dye",
                    "black_dye", "distilled_water", "waste", "base_potash_liquor", "white_tea", "green_tea", "black_tea", "chamomile_tea",
                    "dandelion_tea", "labrador_tea", "coffee", "agave_wine", "barley_wine", "banana_wine", "berry_wine", "cherry_wine",
                    "juniper_wine", "lemon_wine", "orange_wine", "papaya_wine", "peach_wine", "pear_wine", "plum_wine", "mead", "red_wine",
                    "wheat_wine", "white_wine", "calvados", "gin", "tequila", "shochu", "grappa", "banana_brandy", "cherry_brandy", "lemon_brandy",
                    "orange_brandy", "papaya_brandy", "peach_brandy", "pear_brandy", "plum_brandy", "berry_brandy", "brandy", "cognac", "beer_barley",
                    "beer_corn", "beer_rye", "beer_wheat", "beer_amaranth", "beer_buckwheat", "beer_fonio", "beer_millet", "beer_quinoa",
                    "beer_spelt", "sugar_water", "honey_water", "rice_water", "soybean_water", "linseed_water", "rape_seed_water",
                    "sunflower_seed_water", "opium_poppy_seed_water", "sugar_beet_water", "soy_milk", "linseed_oil", "rape_seed_oil",
                    "sunflower_seed_oil", "opium_poppy_seed_oil", "wort", "firma_cola", "juice_blackberry", "juice_blueberry", "juice_bunch_berry",
                    "juice_cloud_berry", "juice_cranberry", "juice_elderberry", "juice_gooseberry", "juice_raspberry", "juice_snow_berry",
                    "juice_strawberry", "juice_wintergreen_berry", "juice_agave", "juice_apple", "juice_banana", "juice_cherry", "juice_lemon",
                    "juice_orange", "juice_papaya", "juice_peach", "juice_pear", "juice_plum", "juice_juniper", "juice_green_grape",
                    "juice_purple_grape", "juice_barrel_cactus", "yeast_starter", "coconut_milk", "yak_milk", "zebu_milk", "goat_milk",
                    "curdled_goat_milk", "curdled_yak_milk", "pina_colada" };

            @Config.Comment("List of fluids allowed to be picked up by blue steel bucket")
            @Config.LangKey("config." + MODID_TFC + ".general.misc.blueSteelBucketWhitelist")
            public String[] blueSteelBucketWhitelist = new String[] { "lava" };

            @Config.Comment("List of fluids allowed to be picked up by red steel bucket")
            @Config.LangKey("config." + MODID_TFC + ".general.misc.redSteelBucketWhitelist")
            public String[] redSteelBucketWhitelist = new String[] { "fresh_water", "hot_water", "salt_water", "water" };

            @Config.Comment("Entities that can be plucked for feathers.")
            @Config.LangKey("config." + MODID_TFC + ".general.misc.pluckableEntities")
            public String[] pluckableEntities = new String[] { "tfc:chickentfc", "tfc:pheasanttfc", "tfc:parrottfc", "tfc:ducktfc", "tfc:grousetfc",
                    "tfc:pheasanttfc", "tfc:quailtfc", "tfc:turkeytfc" };

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
            public String[] fluidWhitelist = new String[] { "fresh_water", "hot_water", "salt_water", "water", "limewater", "tannin", "olive_oil",
                    "olive_oil_water", "vinegar", "rum", "beer", "whiskey", "rye_whiskey", "corn_whiskey", "sake", "vodka", "cider", "brine", "milk",
                    "milk_curdled", "milk_vinegar", "white_dye", "orange_dye", "magenta_dye", "light_blue_dye", "yellow_dye", "lime_dye", "pink_dye",
                    "gray_dye", "light_gray_dye", "cyan_dye", "purple_dye", "blue_dye", "brown_dye", "green_dye", "red_dye", "black_dye",
                    "distilled_water", "waste", "base_potash_liquor", "white_tea", "green_tea", "black_tea", "chamomile_tea", "dandelion_tea",
                    "labrador_tea", "coffee", "agave_wine", "barley_wine", "banana_wine", "berry_wine", "cherry_wine", "juniper_wine", "lemon_wine",
                    "orange_wine", "papaya_wine", "peach_wine", "pear_wine", "plum_wine", "mead", "red_wine", "wheat_wine", "white_wine", "calvados",
                    "gin", "tequila", "shochu", "grappa", "banana_brandy", "cherry_brandy", "lemon_brandy", "orange_brandy", "papaya_brandy",
                    "peach_brandy", "pear_brandy", "plum_brandy", "berry_brandy", "brandy", "cognac", "beer_barley", "beer_corn", "beer_rye",
                    "beer_wheat", "beer_amaranth", "beer_buckwheat", "beer_fonio", "beer_millet", "beer_quinoa", "beer_spelt", "sugar_water",
                    "honey_water", "rice_water", "soybean_water", "linseed_water", "rape_seed_water", "sunflower_seed_water",
                    "opium_poppy_seed_water", "sugar_beet_water", "soy_milk", "linseed_oil", "rape_seed_oil", "sunflower_seed_oil",
                    "opium_poppy_seed_oil", "wort", "firma_cola", "juice_blackberry", "juice_blueberry", "juice_bunch_berry", "juice_cloud_berry",
                    "juice_cranberry", "juice_elderberry", "juice_gooseberry", "juice_raspberry", "juice_snow_berry", "juice_strawberry",
                    "juice_wintergreen_berry", "juice_agave", "juice_apple", "juice_banana", "juice_cherry", "juice_lemon", "juice_orange",
                    "juice_papaya", "juice_peach", "juice_pear", "juice_plum", "juice_juniper", "juice_green_grape", "juice_purple_grape",
                    "juice_barrel_cactus", "yeast_starter", "coconut_milk", "yak_milk", "zebu_milk", "goat_milk", "curdled_goat_milk",
                    "curdled_yak_milk", "pina_colada" };
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

            @Config.Comment({ "The amount of times a chunk can be worked (300 = default, 0 = disable).",
                    "Note: While sluices increase work by 1, Goldpan increase by 6." })
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

        @Config.Comment("Display settings")
        @Config.LangKey("config." + MODID_TFC + ".client.display")
        public static final DisplayCFG DISPLAY = new DisplayCFG();

        @Config.Comment("Grass coloring settings")
        @Config.LangKey("config." + MODID_TFC + ".client.grassColor")
        public static final GrassColorCFG GRASS_COLOR = new GrassColorCFG();

        @Config.Comment("Render settings")
        @Config.LangKey("config." + MODID_TFC + ".client.render")
        public static final RenderCFG RENDER = new RenderCFG();

        public static final class TooltipCFG {

            @Config.Comment({ "Show ItemStack tool classes when advanced tooltips are enabled. (F3+H)" })
            @Config.LangKey("config." + MODID_TFC + ".client.tooltip.showToolClassTooltip")
            public boolean showToolClassTooltip = true;

            @Config.Comment({ "Show ItemStack OreDictionary matches when advanced tooltips are enabled. (F3+H)" })
            @Config.LangKey("config." + MODID_TFC + ".client.tooltip.showOreDictionaryTooltip")
            public boolean showOreDictionaryTooltip = true;

            @Config.Comment({ "Show ItemStack NBT on the tooltip when advanced tooltips are enabled. (F3+H)" })
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

            @Config.Comment({ "Time tooltip info mode." })
            @Config.LangKey("config." + MODID_TFC + ".client.tooltip.timeTooltipMode")
            public TimeTooltipMode timeTooltipMode = TimeTooltipMode.MINECRAFT_HOURS;
        }

        public static final class DisplayCFG {

            @Config.Comment("If TFC health bar is enabled, this changes display health format. (Default: TFC = 1000 / 1000).")
            @Config.LangKey("config." + MODID_TFC + ".client.display.healthDisplayFormat")
            public HealthDisplayFormat healthDisplayFormat = HealthDisplayFormat.TFC;

            @Config.Comment({ "Disable TFC health bar and use vanilla instead?" })
            @Config.LangKey("config." + MODID_TFC + ".client.display.useVanillaHealth")
            public boolean useVanillaHealth = false;

            @Config.Comment({ "Disable TFC hunger bar and use vanilla instead?" })
            @Config.LangKey("config." + MODID_TFC + ".client.display.useVanillaHunger")
            public boolean useVanillaHunger = false;

            @Config.Comment({ "Hide the thirst bar?" })
            @Config.LangKey("config." + MODID_TFC + ".client.display.hideThirstBar")
            public boolean hideThirstBar = false;

            @Config.Comment("The color to render on top of rotten food. Express as a 256 bit color value: 0xFFFFFF = white, 0x000000 = black")
            @Config.LangKey("config." + MODID_TFC + ".client.display.rottenFoodOverlayColor")
            public int rottenFoodOverlayColor = 0x88CC33;
        }

        public static final class GrassColorCFG {

            @Config.Comment("If true, grass and foliage will be slightly varied in color.")
            @Config.LangKey("config." + MODID_TFC + ".client.grassColor.noiseEnable")
            public boolean noiseEnable = true;

            @Config.Comment("If true, grass and foliage will be colored seasonally.")
            @Config.LangKey("config." + MODID_TFC + ".client.grassColor.seasonColorEnable")
            public boolean seasonColorEnable = true;

            @Config.Comment("The noise scale. Default = 10")
            @Config.LangKey("config." + MODID_TFC + ".client.grassColor.noiseScale")
            public float noiseScale = 10f;

            @Config.Comment("How many darkness levels should the noise have? Default = 5")
            @Config.LangKey("config." + MODID_TFC + ".client.grassColor.noiseLevels")
            public int noiseLevels = 5;

            @Config.Comment("How potent should the darkness be? Default = 0.15")
            @Config.LangKey("config." + MODID_TFC + ".client.grassColor.noiseDarkness")
            public float noiseDarkness = 0.15f;

            @Config.Comment("ARGB code for summer coloring in hexadecimal. Default: 1155FF44")
            @Config.LangKey("config." + MODID_TFC + ".client.grassColor.seasonColorSummer")
            public String seasonColorSummer = "1155FF44";

            @Config.Comment("ARGB code for summer coloring in hexadecimal. Default: 55FFDD44")
            @Config.LangKey("config." + MODID_TFC + ".client.grassColor.seasonColorAutumn")
            public String seasonColorAutumn = "55FFDD44";

            @Config.Comment("ARGB code for winter coloring in hexadecimal. Default: 335566FF")
            @Config.LangKey("config." + MODID_TFC + ".client.grassColor.seasonColorWinter")
            public String seasonColorWinter = "335566FF";

            @Config.Comment("ARGB code for spring coloring in hexadecimal. Default: 3355FFBB")
            @Config.LangKey("config." + MODID_TFC + ".client.grassColor.seasonColorSpring")
            public String seasonColorSpring = "3355FFBB";
        }

        public static class RenderCFG {

            @Config.Comment("Render distance for flat placed items (rocks, sticks). Default: 32 Blocks")
            @Config.LangKey("config." + MODID_TFC + ".client.render.placedItemFlatDistance")
            public int placedItemFlatDistance = 32;
        }
    }

    @Config(modid = MODID_TFC, category = "animals", name = "TerraFirmaCraft - Animals")
    @Config.LangKey("config." + MODID_TFC + ".animals")
    public static final class Animals {

        @Config.Comment("Alpaca")
        @Config.LangKey("config." + MODID_TFC + ".animals.alpaca")
        public static final AlpacaCFG ALPACA = new AlpacaCFG();

        @Config.Comment("Sheep")
        @Config.LangKey("config." + MODID_TFC + ".animals.sheep")
        public static final SheepCFG SHEEP = new SheepCFG();

        @Config.Comment("Cow")
        @Config.LangKey("config." + MODID_TFC + ".animals.cow")
        public static final CowCFG COW = new CowCFG();

        @Config.Comment("Goat")
        @Config.LangKey("config." + MODID_TFC + ".animals.goat")
        public static final GoatCFG GOAT = new GoatCFG();

        @Config.Comment("Chicken")
        @Config.LangKey("config." + MODID_TFC + ".animals.chicken")
        public static final ChickenCFG CHICKEN = new ChickenCFG();

        @Config.Comment("Duck")
        @Config.LangKey("config." + MODID_TFC + ".animals.duck")
        public static final DuckCFG DUCK = new DuckCFG();

        @Config.Comment("Pig")
        @Config.LangKey("config." + MODID_TFC + ".animals.pig")
        public static final PigCFG PIG = new PigCFG();

        @Config.Comment("Camel")
        @Config.LangKey("config." + MODID_TFC + ".animals.camel")
        public static final CamelCFG CAMEL = new CamelCFG();

        @Config.Comment("Llama")
        @Config.LangKey("config." + MODID_TFC + ".animals.llama")
        public static final LlamaCFG LLAMA = new LlamaCFG();

        @Config.Comment("Horse")
        @Config.LangKey("config." + MODID_TFC + ".animals.horse")
        public static final HorseCFG HORSE = new HorseCFG();

        @Config.Comment("Donkey")
        @Config.LangKey("config." + MODID_TFC + ".animals.donkey")
        public static final DonkeyCFG DONKEY = new DonkeyCFG();

        @Config.Comment("Mule")
        @Config.LangKey("config." + MODID_TFC + ".animals.mule")
        public static final MuleCFG MULE = new MuleCFG();

        @Config.Comment("Ocelot")
        @Config.LangKey("config." + MODID_TFC + ".animals.ocelot")
        public static final OcelotCFG OCELOT = new OcelotCFG();

        @Config.Comment("Wolf")
        @Config.LangKey("config." + MODID_TFC + ".animals.wolf")
        public static final WolfCFG WOLF = new WolfCFG();

        @Config.Comment("GrizzlyBear")
        @Config.LangKey("config." + MODID_TFC + ".animals.grizzly_bear")
        public static final GrizzlyBearCFG GRIZZLY_BEAR = new GrizzlyBearCFG();

        @Config.Comment("Polar Bear")
        @Config.LangKey("config." + MODID_TFC + ".animals.polar_bear")
        public static final PolarBearCFG POLAR_BEAR = new PolarBearCFG();

        @Config.Comment("Lion")
        @Config.LangKey("config." + MODID_TFC + ".animals.lion")
        public static final LionCFG LION = new LionCFG();

        @Config.Comment("Panther")
        @Config.LangKey("config." + MODID_TFC + ".animals.panther")
        public static final PantherCFG PANTHER = new PantherCFG();

        @Config.Comment("Saber Tooth")
        @Config.LangKey("config." + MODID_TFC + ".animals.saber_tooth")
        public static final SaberToothCFG SABER_TOOTH = new SaberToothCFG();

        @Config.Comment("Hyena")
        @Config.LangKey("config." + MODID_TFC + ".animals.hyena")
        public static final HyenaCFG HYENA = new HyenaCFG();

        @Config.Comment("Deer")
        @Config.LangKey("config." + MODID_TFC + ".animals.deer")
        public static final DeerCFG DEER = new DeerCFG();

        @Config.Comment("Parrot")
        @Config.LangKey("config." + MODID_TFC + ".animals.parrot")
        public static final ParrotCFG PARROT = new ParrotCFG();

        @Config.Comment("Pheasant")
        @Config.LangKey("config." + MODID_TFC + ".animals.pheasant")
        public static final PheasantCFG PHEASANT = new PheasantCFG();

        @Config.Comment("Rabbit")
        @Config.LangKey("config." + MODID_TFC + ".animals.rabbit")
        public static final RabbitCFG RABBIT = new RabbitCFG();

        @Config.Comment("DireWolf")
        @Config.LangKey("config." + MODID_TFC + ".animals.direwolf")
        public static final DireWolfCFG DIREWOLF = new DireWolfCFG();

        @Config.Comment("Hare")
        @Config.LangKey("config." + MODID_TFC + ".animals.hare")
        public static final HareCFG HARE = new HareCFG();

        @Config.Comment("Boar")
        @Config.LangKey("config." + MODID_TFC + ".animals.boar")
        public static final BoarCFG BOAR = new BoarCFG();

        @Config.Comment("Zebu")
        @Config.LangKey("config." + MODID_TFC + ".animals.zebu")
        public static final ZebuCFG ZEBU = new ZebuCFG();

        @Config.Comment("Gazelle")
        @Config.LangKey("config." + MODID_TFC + ".animals.gazelle")
        public static final GazelleCFG GAZELLE = new GazelleCFG();

        @Config.Comment("Wildebeest")
        @Config.LangKey("config." + MODID_TFC + ".animals.wildebeest")
        public static final WildebeestCFG WILDEBEEST = new WildebeestCFG();

        @Config.Comment("Quail")
        @Config.LangKey("config." + MODID_TFC + ".animals.quail")
        public static final QuailCFG QUAIL = new QuailCFG();

        @Config.Comment("Grouse")
        @Config.LangKey("config." + MODID_TFC + ".animals.grouse")
        public static final GrouseCFG GROUSE = new GrouseCFG();

        @Config.Comment("Mongoose")
        @Config.LangKey("config." + MODID_TFC + ".animals.mongoose")
        public static final MongooseCFG MONGOOSE = new MongooseCFG();

        @Config.Comment("Turkey")
        @Config.LangKey("config." + MODID_TFC + ".animals.turkey")
        public static final TurkeyCFG TURKEY = new TurkeyCFG();

        @Config.Comment("Jackal")
        @Config.LangKey("config." + MODID_TFC + ".animals.jackal")
        public static final JackalCFG JACKAL = new JackalCFG();

        @Config.Comment("MuskOx")
        @Config.LangKey("config." + MODID_TFC + ".animals.muskox")
        public static final MuskOxCFG MUSKOX = new MuskOxCFG();

        @Config.Comment("Yak")
        @Config.LangKey("config." + MODID_TFC + ".animals.yak")
        public static final YakCFG YAK = new YakCFG();

        @Config.Comment("Black Bear")
        @Config.LangKey("config." + MODID_TFC + ".animals.black_bear")
        public static final BlackBearCFG BLACK_BEAR = new BlackBearCFG();

        @Config.Comment("Cougar")
        @Config.LangKey("config." + MODID_TFC + ".animals.cougar")
        public static final CougarCFG COUGAR = new CougarCFG();

        @Config.Comment("Coyote")
        @Config.LangKey("config." + MODID_TFC + ".animals.coyote")
        public static final CoyoteCFG COYOTE = new CoyoteCFG();

        public static final class AlpacaCFG {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.adulthood")
            public int adulthood = 98;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.elder")
            public int elder = 392;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.gestation")
            public int gestation = 36;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.babies")
            public int babies = 1;

            @Config.Comment("How many ticks are needed for this animal grow back wool?")
            @Config.RangeInt(min = 1_000)
            @Config.LangKey("config." + MODID_TFC + ".animals.woolTicks")
            public int woolTicks = 120_000;

            @Config.Comment("Chance that old animals will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.oldDeathChance")
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 250;
        }

        public static final class SheepCFG {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.adulthood")
            public int adulthood = 64;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.elder")
            public int elder = 256;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.gestation")
            public int gestation = 28;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.babies")
            public int babies = 2;

            @Config.Comment("How many ticks are needed for this animal grow back wool?")
            @Config.RangeInt(min = 1_000)
            @Config.LangKey("config." + MODID_TFC + ".animals.woolTicks")
            public int woolTicks = 168_000;

            @Config.Comment("Chance that old animals will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.oldDeathChance")
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 240;
        }

        public static final class MuskOxCFG {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.adulthood")
            public int adulthood = 192;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.elder")
            public int elder = 768;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.gestation")
            public int gestation = 59;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.babies")
            public int babies = 1;

            @Config.Comment("How many ticks are needed for this animal grow back wool?")
            @Config.RangeInt(min = 1_000)
            @Config.LangKey("config." + MODID_TFC + ".animals.woolTicks")
            public int woolTicks = 96_000;

            @Config.Comment("Chance that old animals will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.oldDeathChance")
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 250;
        }

        public static final class CowCFG {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.adulthood")
            public int adulthood = 192;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.elder")
            public int elder = 768;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.gestation")
            public int gestation = 58;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.babies")
            public int babies = 1;

            @Config.Comment("How many ticks it is needed for this animal give milk?")
            @Config.RangeInt(min = 1_000)
            @Config.LangKey("config." + MODID_TFC + ".animals.milkTicks")
            public int milkTicks = 24_000;

            @Config.Comment("Chance that old animals will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.oldDeathChance")
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 240;
        }

        public static final class YakCFG {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.adulthood")
            public int adulthood = 180;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.elder")
            public int elder = 720;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.gestation")
            public int gestation = 62;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.babies")
            public int babies = 1;

            @Config.Comment("How many ticks it is needed for this animal give milk?")
            @Config.RangeInt(min = 1_000)
            @Config.LangKey("config." + MODID_TFC + ".animals.milkTicks")
            public int milkTicks = 24_000;

            @Config.Comment("Chance that old animals will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.oldDeathChance")
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 250;
        }

        public static final class ZebuCFG {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.adulthood")
            public int adulthood = 108;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.elder")
            public int elder = 686;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.gestation")
            public int gestation = 32;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.babies")
            public int babies = 1;

            @Config.Comment("How many ticks it is needed for this animal give milk?.")
            @Config.RangeInt(min = 1_000)
            @Config.LangKey("config." + MODID_TFC + ".animals.milkTicks")
            public int milkTicks = 48_000;

            @Config.Comment("Chance that old animals will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.oldDeathChance")
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 250;
        }

        public static final class GoatCFG {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.adulthood")
            public int adulthood = 96;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.elder")
            public int elder = 420;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.gestation")
            public int gestation = 28;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.babies")
            public int babies = 2;

            @Config.Comment("How many ticks it is needed for this animal give milk?.")
            @Config.RangeInt(min = 1_000)
            @Config.LangKey("config." + MODID_TFC + ".animals.milkTicks")
            public int milkTicks = 72_000;

            @Config.Comment("Chance that old animals will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.oldDeathChance")
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 240;
        }

        public static final class ChickenCFG {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.adulthood")
            public int adulthood = 24;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.elder")
            public int elder = 92;

            @Config.Comment("How many days it is needed for this animal finish hatching?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.hatch")
            public int hatch = 8;

            @Config.Comment("How many ticks it is needed for this animal to lay eggs?")
            @Config.RangeInt(min = 1_000)
            @Config.LangKey("config." + MODID_TFC + ".animals.eggTicks")
            public int eggTicks = 30_000;

            @Config.Comment("Chance that old animals will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.oldDeathChance")
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 240;
        }

        public static final class GrouseCFG {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.adulthood")
            public int adulthood = 26;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.elder")
            public int elder = 98;

            @Config.Comment("How many days it is needed for this animal finish hatching?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.hatch")
            public int hatch = 10;

            @Config.Comment("How many ticks it is needed for this animal to lay eggs?")
            @Config.RangeInt(min = 1_000)
            @Config.LangKey("config." + MODID_TFC + ".animals.eggTicks")
            public int eggTicks = 30_000;

            @Config.Comment("Chance that old animals will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.oldDeathChance")
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 250;
        }

        public static final class QuailCFG {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.adulthood")
            public int adulthood = 22;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.elder")
            public int elder = 88;

            @Config.Comment("How many days it is needed for this animal finish hatching?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.hatch")
            public int hatch = 8;

            @Config.Comment("How many ticks it is needed for this animal to lay eggs?")
            @Config.RangeInt(min = 1_000)
            @Config.LangKey("config." + MODID_TFC + ".animals.eggTicks")
            public int eggTicks = 28_000;

            @Config.Comment("Chance that old animals will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.oldDeathChance")
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 240;
        }

        public static final class DuckCFG {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.adulthood")
            public int adulthood = 32;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.elder")
            public int elder = 140;

            @Config.Comment("How many days it is needed for this animal finish hatching?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.hatch")
            public int hatch = 12;

            @Config.Comment("How many ticks it is needed for this animal to lay eggs?")
            @Config.RangeInt(min = 1_000)
            @Config.LangKey("config." + MODID_TFC + ".animals.eggTicks")
            public int eggTicks = 32_000;

            @Config.Comment("Chance that old animals will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.oldDeathChance")
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 240;
        }

        public static final class PigCFG {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.adulthood")
            public int adulthood = 80;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.elder")
            public int elder = 320;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.gestation")
            public int gestation = 19;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.babies")
            public int babies = 10;

            @Config.Comment("Chance that old animals will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.oldDeathChance")
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 240;
        }

        public static final class CamelCFG {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.adulthood")
            public int adulthood = 192;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.elder")
            public int elder = 768;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.gestation")
            public int gestation = 59;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.babies")
            public int babies = 1;

            @Config.Comment("Chance that old animals will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.oldDeathChance")
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 250;
        }

        public static final class LlamaCFG {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.adulthood")
            public int adulthood = 160;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.elder")
            public int elder = 640;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.gestation")
            public int gestation = 55;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.babies")
            public int babies = 1;

            @Config.Comment("Chance that old animals will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.oldDeathChance")
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 250;
        }

        public static final class HorseCFG {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.adulthood")
            public int adulthood = 200;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.elder")
            public int elder = 800;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.gestation")
            public int gestation = 43;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.babies")
            public int babies = 1;

            @Config.Comment("Chance that old animals will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.oldDeathChance")
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 250;
        }

        public static final class DonkeyCFG {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.adulthood")
            public int adulthood = 200;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.elder")
            public int elder = 800;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.gestation")
            public int gestation = 43;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.babies")
            public int babies = 1;

            @Config.Comment("Chance that old animals will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.oldDeathChance")
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 250;
        }

        public static final class MuleCFG {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.adulthood")
            public int adulthood = 200;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.elder")
            public int elder = 800;

            @Config.Comment("Chance that old animals will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.oldDeathChance")
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 0;
        }

        public static final class ParrotCFG {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 250;
        }

        public static final class OcelotCFG {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.adulthood")
            public int adulthood = 59;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.elder")
            public int elder = 236;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.gestation")
            public int gestation = 8;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.babies")
            public int babies = 2;

            @Config.Comment("Chance that old animals will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.oldDeathChance")
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 250;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            @Config.LangKey("config." + MODID_TFC + ".animals.huntCreatures")
            public String[] huntCreatures = { "tfc:pheasanttfc", "tfc:chickentfc", "tfc:ducktfc", "tfc:rabbittfc" };
        }

        public static final class WolfCFG {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.adulthood")
            public int adulthood = 70;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.elder")
            public int elder = 280;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.gestation")
            public int gestation = 10;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            @Config.LangKey("config." + MODID_TFC + ".animals.babies")
            public int babies = 2;

            @Config.Comment("Chance that old animals will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.oldDeathChance")
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 250;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            @Config.LangKey("config." + MODID_TFC + ".animals.huntCreatures")
            public String[] huntCreatures = { "tfc:sheeptfc", "tfc:rabbittfc", "tfc:haretfc" };
        }

        public static final class GrizzlyBearCFG {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 150;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            @Config.LangKey("config." + MODID_TFC + ".animals.huntCreatures")
            public String[] huntCreatures = { "tfc:deertfc", "tfc:haretfc", "tfc:rabbittfc" };
        }

        public static final class BlackBearCFG {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 150;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            @Config.LangKey("config." + MODID_TFC + ".animals.huntCreatures")
            public String[] huntCreatures = { "tfc:deertfc", "tfc:haretfc", "tfc:rabbittfc" };
        }

        public static final class PolarBearCFG {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 120;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            @Config.LangKey("config." + MODID_TFC + ".animals.huntCreatures")
            public String[] huntCreatures = { "tfc:deertfc", "tfc:haretfc", "tfc:rabbittfc" };
        }

        public static final class LionCFG {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 150;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            @Config.LangKey("config." + MODID_TFC + ".animals.huntCreatures")
            public String[] huntCreatures = { "tfc:gazelletfc", "tfc:wildebeesttfc" };
        }

        public static final class SaberToothCFG {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 150;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            @Config.LangKey("config." + MODID_TFC + ".animals.huntCreatures")
            public String[] huntCreatures = { "tfc:deertfc", "tfc:boartfc" };
        }

        public static final class DireWolfCFG {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 150;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            @Config.LangKey("config." + MODID_TFC + ".animals.huntCreatures")
            public String[] huntCreatures = { "tfc:horsetfc", "tfc:donkeytfc", "tfc:muletfc", "tfc:turkeytfc" };
        }

        public static final class CougarCFG {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 100;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            @Config.LangKey("config." + MODID_TFC + ".animals.huntCreatures")
            public String[] huntCreatures = { "tfc:boartfc", "tfc:haretfc" };
        }

        public static final class CoyoteCFG {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 100;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            @Config.LangKey("config." + MODID_TFC + ".animals.huntCreatures")
            public String[] huntCreatures = { "tfc:mongoosetfc", "tfc:haretfc" };
        }

        public static final class PantherCFG {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 100;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            @Config.LangKey("config." + MODID_TFC + ".animals.huntCreatures")
            public String[] huntCreatures = { "tfc:boartfc", "tfc:haretfc" };
        }

        public static final class JackalCFG {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 120;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            @Config.LangKey("config." + MODID_TFC + ".animals.huntCreatures")
            public String[] huntCreatures = { "tfc:pheasanttfc", "tfc:rabbittfc", "tfc:haretfc" };
        }

        public static final class HyenaCFG {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 100;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            @Config.LangKey("config." + MODID_TFC + ".animals.huntCreatures")
            public String[] huntCreatures = { "tfc:gazalletfc", "tfc:rabbittfc", "tfc:haretfc" };
        }

        public static final class MongooseCFG {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 120;
        }

        public static final class DeerCFG {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 150;
        }

        public static final class TurkeyCFG {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 140;
        }

        public static final class PheasantCFG {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 120;
        }

        public static final class RabbitCFG {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 100;
        }

        public static final class HareCFG {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 100;
        }

        public static final class BoarCFG {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 160;
        }

        public static final class GazelleCFG {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 150;
        }

        public static final class WildebeestCFG {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            @Config.LangKey("config." + MODID_TFC + ".animals.rarity")
            public int rarity = 150;
        }
    }
}
