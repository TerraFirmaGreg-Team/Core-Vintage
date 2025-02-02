package net.dries007.horsepower;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;

import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fluids.Fluid;

import net.dries007.horsepower.lib.Reference;

@Config(modid = Reference.MODID, category = "all")
public class Configs {

  @Comment("Client only configs")
  @Config.LangKey("config.gui.client")
  public static Client client = new Client();

  @Comment("General configs")
  @Config.LangKey("config.gui.general")
  public static General general = new General();

  @Comment({"Contains the customizable recipes", "For the recipes to show in JEI the resources needs to be reloaded, F3+T"})
  @Config.LangKey("config.gui.recipes")
  public static Recipes recipes = new Recipes();

  @Comment("Contains misc configs, mostly for debugging and dev")
  @Config.LangKey("config.gui.misc")
  public static Misc misc = new Misc();

  public static class Client {

    @Comment("If the amount text on how many items is in a stack in a grindstone should render")
    @Name("Render Item Amount")
    public boolean renderItemAmount = true;

    @Comment("Must look at the block to show the amount in it")
    @Name("Must Look For Amount ")
    public boolean mustLookAtBlock = true;

    @Comment("If true will show the area needed when placing a HP block")
    @Name("Show Obstructed Area")
    public boolean showObstructedPlace = true;
  }

  public static class Recipes {

    @Comment("If the separate list of recipes should be used for the hand grindstone")
    @Name("Separate Grindstone Recipes")
    @Config.RequiresMcRestart
    public boolean useSeperateGrindstoneRecipes = true;

    @Comment("If the separate list of recipes should be used for the chopping block")
    @Name("Separate Chopping Recipes")
    @Config.RequiresMcRestart
    public boolean useSeperateChoppingRecipes = true;

    @Comment({"Add recipes to the Grindstone Block here, the format of the recipes is: ",
              "modid:input:meta${nbt}-modid:output:meta@amount${nbt}-time[-modid:secondary:meta@amount${nbt}-chance]",
              "The meta can be a '*' to be a wildcard", "The amount is optional, if not set 1 is default, ${nbt} is optional and follows vanilla tag syntax",
              "The part in [] is optional, the chance can be 0-100",
              "The input item can be an item from the ore dictionary, use it as 'ore:name', the other rules don't applies",
              "The time for the horse increases for each point that it reaches, one lap is 8 points.",
              "Must be edited with in-game editor for live changes."})
    @Config.LangKey("config.gui.recipes.grindstone")
    @Name("Grindstone Recipes")
    public String[] grindstoneRecipes = {
      "minecraft:bone-minecraft:dye:15@5-8",
      "minecraft:coal:1-tfc:powder/charcoal@8-8",
      "ore:rockFlux-tfc:powder/flux@4-8",
      "tfc:food/barley:*-tfc:food/barley_grain@2-8-tfc:straw-50",
      "tfc:food/barley_grain:*-tfc:food/barley_flour@2-8",
      "tfc:food/maize:*-tfc:food/maize_grain@2-8-tfc:straw-50",
      "tfc:food/maize_grain:*-tfc:food/cornmeal_flour@2-8",
      "tfc:food/oat:*-tfc:food/oat_grain@2-8-tfc:straw-50",
      "tfc:food/oat_grain:*-tfc:food/oat_flour@2-8",
      "tfc:food/olive:*-tfc:food/olive_paste@2-8",
      "tfc:food/rice:*-tfc:food/rice_grain@2-8-tfc:straw-50",
      "tfc:food/rice_grain:*-tfc:food/rice_flour@2-8",
      "tfc:food/rye:*-tfc:food/rye_grain@2-8-tfc:straw-50",
      "tfc:food/rye_grain:*-tfc:food/rye_flour@2-8",
      "tfc:food/sugarcane:*-minecraft:sugar-4",
      "tfc:food/wheat:*-tfc:food/wheat_grain@2-8-tfc:straw-50",
      "tfc:food/wheat_grain:*-tfc:food/wheat_flour@2-8",
      "tfc:ore/cinnabar-minecraft:redstone@8-8",
      "tfc:ore/cryolite-minecraft:redstone@8-8",
      "tfc:ore/graphite-tfc:powder/graphite@6-8",
      "tfc:ore/kaolinite-tfc:powder/kaolinite@6-8",
      "tfc:ore/lapis_lazuli-tfc:powder/lapis_lazuli@8-8",
      "tfc:ore/saltpeter-tfc:powder/saltpeter@8-8",
      "tfc:ore/selenite-minecraft:glowstone_dust@8-8",
      "tfc:ore/sulfur-tfc:powder/sulfur@8-8",
      "tfc:ore/sylvite-tfc:powder/fertilizer@8-8",
      "tfc:plants/allium-minecraft:dye:5@2-8",
      "tfc:plants/athyrium_fern-minecraft:dye:13@2-8",
      "tfc:plants/barrel_cactus-minecraft:dye:2@3-8",
      "tfc:plants/black_orchid-minecraft:dye:5@2-8",
      "tfc:plants/blood_lily-minecraft:dye:13@2-8",
      "tfc:plants/blue_orchid-tfc:dye/blue@2-8",
      "tfc:plants/butterfly_milkweed-minecraft:dye:14@2-8",
      "tfc:plants/calendula-minecraft:dye:11@2-8",
      "tfc:plants/canna-minecraft:dye:14@2-8",
      "tfc:plants/dandelion-minecraft:dye:11@2-8",
      "tfc:plants/duckweed-minecraft:dye:2-8",
      "tfc:plants/field_horsetail-minecraft:dye:2-8",
      "tfc:plants/fountain_grass-minecraft:dye:2-8",
      "tfc:plants/foxglove-minecraft:dye:9@2-8",
      "tfc:plants/goldenrod-minecraft:dye:11@2-8",
      "tfc:plants/grape_hyacinth-tfc:dye/blue@2-8",
      "tfc:plants/guzmania-minecraft:dye:1@2-8",
      "tfc:plants/houstonia-tfc:dye/white@2-8",
      "tfc:plants/labrador_tea-minecraft:dye:12@2-8",
      "tfc:plants/lady_fern-minecraft:dye:2-8",
      "tfc:plants/licorice_fern-minecraft:dye:2-8",
      "tfc:plants/lotus-minecraft:dye:2-8",
      "tfc:plants/meads_milkweed-minecraft:dye:11@2-8",
      "tfc:plants/morning_glory-minecraft:dye:13@2-8",
      "tfc:plants/moss-minecraft:dye:10@2-8",
      "tfc:plants/nasturtium-minecraft:dye:14@2-8",
      "tfc:plants/orchard_grass-minecraft:dye:2-8",
      "tfc:plants/ostrich_fern-minecraft:dye:2-8",
      "tfc:plants/oxeye_daisy-tfc:dye/white@2-8",
      "tfc:plants/pampas_grass-minecraft:dye:2-8",
      "tfc:plants/perovskia-minecraft:dye:5@2-8",
      "tfc:plants/pistia-minecraft:dye:2-8",
      "tfc:plants/poppy-minecraft:dye:1@2-8",
      "tfc:plants/primrose-tfc:dye/white@2-8",
      "tfc:plants/pulsatilla-minecraft:dye:13@2-8",
      "tfc:plants/reindeer_lichen-tfc:dye/white@2-8",
      "tfc:plants/rose-minecraft:dye:1@3-8",
      "tfc:plants/rough_horsetail-tfc:dye/brown-8",
      "tfc:plants/ryegrass-minecraft:dye:2-8",
      "tfc:plants/sacred_datura-minecraft:dye:9@2-8",
      "tfc:plants/sagebrush-minecraft:dye:11@2-8",
      "tfc:plants/sapphire_tower-minecraft:dye:12@2-8",
      "tfc:plants/sargassum-tfc:dye/brown-8",
      "tfc:plants/scutch_grass-minecraft:dye:2-8",
      "tfc:plants/snapdragon_pink-minecraft:dye:9@2-8",
      "tfc:plants/snapdragon_red-minecraft:dye:1@2-8",
      "tfc:plants/snapdragon_white-tfc:dye/white@2-8",
      "tfc:plants/snapdragon_yellow-minecraft:dye:11@2-8",
      "tfc:plants/spanish_moss-tfc:dye/white-8",
      "tfc:plants/strelitzia-minecraft:dye:14@2-8",
      "tfc:plants/switchgrass-minecraft:dye:2-8",
      "tfc:plants/sword_fern-minecraft:dye:2-8",
      "tfc:plants/tall_fescue_grass-minecraft:dye:2-8",
      "tfc:plants/timothy_grass-minecraft:dye:2-8",
      "tfc:plants/toquilla_palm-minecraft:dye:2-8",
      "tfc:plants/tree_fern-minecraft:dye:2-8",
      "tfc:plants/trillium-tfc:dye/white@2-8",
      "tfc:plants/tropical_milkweed-minecraft:dye:1@2-8",
      "tfc:plants/tulip_orange-minecraft:dye:14@2-8",
      "tfc:plants/tulip_pink-minecraft:dye:9@2-8",
      "tfc:plants/tulip_red-minecraft:dye:1@2-8",
      "tfc:plants/tulip_white-tfc:dye/white@2-8",
      "tfc:plants/vriesea-minecraft:dye:1@2-8",
      "tfc:plants/water_canna-minecraft:dye:14@2-8",
      "tfc:plants/water_lily-minecraft:dye:2-8",
      "tfc:plants/yucca-minecraft:dye:7@2-8",
      "tfc:rock/rocksalt-tfc:powder/salt@8-8"
    };

    @Comment({"Uses the same syntax as the regular grindstone recipes", "These recipes are only used when the config to separate them is enabled"})
    @Config.LangKey("config.gui.recipes.hand_grindstone")
    @Name("Hand Grindstone Recipes")
    public String[] handGrindstoneRecipes = {};

    @Comment({"Add recipes to the Chopping Block here, the format of the recipes are: modid:input:meta${nbt}-modid:output:meta@amount${nbt}-time",
              "The meta can be a '*' to be a wildcard", "The amount is optional, if not set 1 is default", "${nbt} is optional and follows vanilla tag syntax",
              "The input item can be an item from the ore dictionary, use it as 'ore:name', the other rules don't applies",
              "The time is the amount of chops for it to process, the time for one chop is determined by the \"Windup time for chop\" config",
              "Must be edited with in-game editor for live changes."})
    @Config.LangKey("config.gui.recipes.chopping")
    @Name("Chopping Recipes")
    public String[] choppingRecipes = {
      "tfc:wood/log/acacia:0-tfc:wood/lumber/acacia@10-4",
      "tfc:wood/log/ash:0-tfc:wood/lumber/ash@10-4",
      "tfc:wood/log/aspen:0-tfc:wood/lumber/aspen@10-4",
      "tfc:wood/log/birch:0-tfc:wood/lumber/birch@10-4",
      "tfc:wood/log/blackwood:0-tfc:wood/lumber/blackwood@10-4",
      "tfc:wood/log/chestnut:0-tfc:wood/lumber/chestnut@10-4",
      "tfc:wood/log/douglas_fir:0-tfc:wood/lumber/douglas_fir@10-4",
      "tfc:wood/log/hickory:0-tfc:wood/lumber/hickory@10-4",
      "tfc:wood/log/kapok:0-tfc:wood/lumber/kapok@10-4",
      "tfc:wood/log/maple:0-tfc:wood/lumber/maple@10-4",
      "tfc:wood/log/oak:0-tfc:wood/lumber/oak@10-4",
      "tfc:wood/log/palm:0-tfc:wood/lumber/palm@10-4",
      "tfc:wood/log/pine:0-tfc:wood/lumber/pine@10-4",
      "tfc:wood/log/rosewood:0-tfc:wood/lumber/rosewood@10-4",
      "tfc:wood/log/sequoia:0-tfc:wood/lumber/sequoia@10-4",
      "tfc:wood/log/spruce:0-tfc:wood/lumber/spruce@10-4",
      "tfc:wood/log/sycamore:0-tfc:wood/lumber/sycamore@10-4",
      "tfc:wood/log/white_cedar:0-tfc:wood/lumber/white_cedar@10-4",
      "tfc:wood/log/willow:0-tfc:wood/lumber/willow@10-4"
    };

    @Comment({"Uses the same syntax as the regular chopping recipes, the only difference is that the time is the amount of chopps",
              "These recipes are only used when the config to separate them is enabled"})
    @Config.LangKey("config.gui.recipes.manual_chopping")
    @Name("Manual Chopping Block Recipes")
    public String[] manualChoppingRecipes = {};

    @Comment({"Add recipes to the Press Block here, the format of the recipe is: modid:input:meta@amount${nbt}-modid:output:meta@amount${nbt}",
              "The meta can be a '*' to be a wildcard", "The amount is optional, if not set 1 is default", "${nbt} is optional and follows vanilla tag syntax",
              "The input item can be an item from the ore dictionary, use it as 'ore:name', the other rules don't applies",
              "The 'modid' for the output can be 'fluid' for fluid outputs",
              "The time is same for all recipes, it uses the \"Points For Press\"",
              "Must be edited with in-game editor for live changes."})
    @Name("Press Recipes")
    public String[] pressRecipes = {
      "minecraft:string@22-tfc:animal/product/silk_cloth",
      "ore:treeLeaves@8-fluid:fresh_water@1000",
      "tfc:animal/product/wool_yarn@14-tfc:animal/product/wool_cloth",
      "tfc:crop/product/jute_fiber@10-tfc:crop/product/burlap_cloth",
      "tfc:food/olive:*-fluid:olive_oil_water@250"
    };
  }

  public static class General {

    @Comment({"Enables the flour item", "If disabled all related recipes will be disabled", "Requires minecraft restart"})
    @Config.RequiresMcRestart
    @Name("Enable Flour")
    public boolean enableFlour = true;

    @Comment({"Enables the dough item", "If disabled all related recipes will be disabled", "Requires minecraft restart"})
    @Config.RequiresMcRestart
    @Name("Enable Dough")
    public boolean enableDough = true;

    @Comment({"Enables the manual chopping block", "Requires minecraft restart"})
    @Config.RequiresMcRestart
    @Name("Enable Manual Chopping Block")
    public boolean enableHandChoppingBlock = false;

    @Comment({"Removes the vanilla crafting recipes that grinds or uses grinded resources",
              "Removes Reeds -> Sugar, Bone -> Bonemeal, Wheat -> Bread, Flowers -> Dye"})
    @Config.RequiresMcRestart
    @Name("Remove Vanilla Recipes")
    public boolean removeVanillaRecipes = false;

    @Comment({"Use the base definition of a horse, in vanilla it includes Horse, Donkey & Mule", "If false only entries in the list are valid",
              "Must be edited with in-game editor for live changes."})
    @Name("Use AbstractHorse")
    public boolean useHorseInterface = false;

    @Comment("If the item used as an axe for the manual chopping block should be damaged")
    @Name("Should Damage Axe")
    public boolean shouldDamageAxe = true;

    @Comment({"The items to use with the manual chopping block, syntax is: ", "modid:input:meta${nbt}=baseAmount-chance",
              "meta is optional and ${nbt} is also optional and follows vanilla tag syntax",
              "The baseAmount is the percentage of returned items, the chance is for getting one more output"})
    @Config.LangKey("config.gui.chopping_axes")
    @Name("Chopping Block Axes")
    public String[] choppingBlockAxes = {};

    @Comment({"The percentage amount for the different materials", "The syntax is harvestLevel=baseAmount-chance",
              "The baseAmount is the percentage of returned items, the chance is for getting one more output"})
    @Name("Harvestable Percentages")
    @Config.LangKey("config.gui.harvest")
    public String[] harvestable_percentage = {
      "0=25-25",
      "1=50-25",
      "2=75-25",
      "3=100-25",
      "4=125-50"
    };

    @Comment({"If true the manual chopping block will drop the result items",
              "If false the manual chopping block will put the result items in it's internal inventory"})
    @Name("Manual Chopping Block output")
    public boolean choppingBlockDrop = true;

    @Comment({"That amount of \"points\" for the chopper to do windup and do a chop", "One lap around the chopping block is 8 \"points\""})
    @Name("Windup time for chop")
    @Config.RangeInt(min = 1)
    public int pointsForWindup = 4;

    @Comment({"The amount of points per rotation with a hand grindstone", "The points correspond to the recipes requirement of time"})
    @Name("Points Per Rotation")
    @Config.RangeInt(min = 1)
    public int pointsPerRotation = 2;

    @Comment({"The exhaustion amount that will be added to the player when using the grindstone", "If set to 0 this is disabled"})
    @Name("Grindstone Exhaustion")
    public double grindstoneExhaustion = 0.1D;

    @Comment({"The exhaustion amount that will be added to the player when using the chopping block", "If set to 0 this is disabled"})
    @Name("Chopping Block Exhaustion")
    public double choppingblockExhaustion = 0.1D;

    @Comment({"The amount of chopps the time value in the horse chopping recipes should be multiplied with if recipes isn't separated",
              "If the recipes are separate this isn't used, instead the recipe value is used"})
    @Name("Chopping Multiplier")
    public int choppMultiplier = 4;

    @Comment({"Add mobs that can use the horse powered blocks", "Only mobs that can be leashed are valid",
              "Add the full path to the mob class, can be found with /horsepower entity or /hp entity",
              "Must be edited with in-game editor for live changes."})
    @Config.LangKey("config.gui.mobs")
    @Name("Mob List")
    public String[] grindstoneMobList = {
      "net.dries007.tfc.objects.entity.animal.EntityHorseTFC",
      "net.dries007.tfc.objects.entity.animal.EntityDonkeyTFC",
      "net.dries007.tfc.objects.entity.animal.EntityMuleTFC",
      "net.dries007.tfc.objects.entity.animal.EntityCamelTFC",
      "net.dries007.tfc.objects.entity.animal.EntityLlamaTFC"
    };

    @Comment("The amount of points that is needed for a full press")
    @Name("Points For Press")
    public int pointsForPress = 16;

    @Comment("The tank size of the press in mb, 1000mb = 1 bucket")
    @Config.RequiresMcRestart
    @Name("Press Tank Size")
    public int pressFluidTankSize = Fluid.BUCKET_VOLUME * 3;

    @Comment({"If true the chopping blocks will use all logs types in the game when crafted", "If false the chopping blocks will only use TFC logs",
              "If only vanilla logs are used other logs in recipe will yield oak texture"})
    @Name("Use Dynamic Chopping Crafting")
    public boolean useDynamicCrafting = true;

    @Comment({"If true it will show all chopping block types in the creative tab and JEI", "If false only TFC wood variants will show",
              "JEI needs a resource reload for this to update"})
    @Name("Use Dynamic Chopping Display")
    public boolean useDynamicDisplay = true;
  }

  public static class ConfigFactory implements IConditionFactory {

    @Override
    public BooleanSupplier parse(JsonContext context, JsonObject json) {
      String item = JsonUtils.getString(json, "enabled");
      return () -> "flour".equals(item) ? general.enableFlour : "dough".equals(item) && general.enableDough;
    }
  }

  public static class Misc {

    @Comment("Will show a items all ore dictionaries in the tooltip")
    @Name("Show Ore Dictionaries")
    public boolean showOreDictionaries = false;

    @Comment("Will show the harvest level of items in the tooltip")
    @Name("Show Harvest Level")
    public boolean showHarvestLevel = false;

    @Comment("What harvest types to show the harvest level for")
    @Name("Harvest Types")
    public String[] harvestTypes = {
      "axe"
    };
  }
}
