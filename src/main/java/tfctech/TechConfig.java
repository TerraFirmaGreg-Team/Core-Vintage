package tfctech;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static su.terrafirmagreg.api.data.Reference.MODID_TFCTECH;

@SuppressWarnings("unused")
@Config(modid = MODID_TFCTECH, category = "")
@Config.LangKey("config." + MODID_TFCTECH)
@Mod.EventBusSubscriber(modid = MODID_TFCTECH)
public final class TechConfig {

  @Config.Comment("Devices configuration")
  @Config.LangKey("config." + MODID_TFCTECH + ".devices")
  public static Devices DEVICES = new Devices();

  @Config.Comment("Tweaks")
  @Config.LangKey("config." + MODID_TFCTECH + ".tweaks")
  public static Tweaks TWEAKS = new Tweaks();

  @SubscribeEvent
  public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
    if (event.getModID().equals(MODID_TFCTECH)) {
      ConfigManager.sync(MODID_TFCTECH, Config.Type.INSTANCE);
    }
  }

  public static class Tweaks {

    @Config.Comment({"Should TFCTech remove TFC and vanilla glass recipes?"})
    @Config.LangKey("config." + MODID_TFCTECH + ".tweaks.removeGlassRecipes")
    public boolean removeGlassRecipes = true;

    @Config.Comment({"Which blocks are considered valid for rubber tapping?"})
    @Config.LangKey("config." + MODID_TFCTECH + ".tweaks.validRubberTrees")
    public String[] rubberTrees = new String[]{"tfc:wood/log/hevea{placed=false,axis=y}", "dynamictreestfc:branch/hevea{radius=8}"};
  }

  public static class Devices {

    @Config.Comment({"Should TFCTech machines accepts IC2 EU?"})
    @Config.LangKey("config." + MODID_TFCTECH + ".devices.acceptIc2EU")
    public boolean acceptIc2EU = true;

    @Config.Comment({"How much energy 1 IC2 EU unit gives to machines?"})
    @Config.RangeInt(min = 1)
    @Config.LangKey("config." + MODID_TFCTECH + ".devices.ratioIc2")
    public int ratioIc2 = 4;

    @Config.Comment({"Which voltage tier machines should register to?"})
    @Config.RangeInt(min = 1)
    @Config.LangKey("config." + MODID_TFCTECH + ".devices.ic2Voltage")
    public int ic2Voltage = Integer.MAX_VALUE;

    @Config.Comment({"Should TFCTech machines accepts GTCE EU?"})
    @Config.LangKey("config." + MODID_TFCTECH + ".devices.acceptGTCEEU")
    public boolean acceptGTCEEU = true;

    @Config.Comment({"How much energy 1 GTCE EU unit gives to machines?"})
    @Config.RangeInt(min = 1)
    @Config.LangKey("config." + MODID_TFCTECH + ".devices.ratioGTCE")
    public int ratioGTCE = 4;

    @Config.Comment({"Which voltage tier machines should register to?"})
    @Config.RangeInt(min = 1)
    @Config.LangKey("config." + MODID_TFCTECH + ".devices.gtceVoltage")
    public int gtceVoltage = 32;

    @Config.Comment({"Should TFCTech machines accepts FE?"})
    @Config.LangKey("config." + MODID_TFCTECH + ".devices.acceptFE")
    public boolean acceptFE = true;

    @Config.Comment({
      "Modifier for how quickly electric forge heats items. Smaller number = slower temperature changes. Note: This is affected by TFC global modifier."})
    @Config.RangeDouble(min = 0.01D, max = 1000.0D)
    @Config.LangKey("config." + MODID_TFCTECH + ".devices.electricForgeSpeed")
    public double electricForgeSpeed = 1.0D;

    @Config.Comment({"The maximum heat obtainable by electric forge."})
    @Config.RangeDouble(min = 500.0D, max = 5000.0D)
    @Config.LangKey("config." + MODID_TFCTECH + ".devices.electricForgeMaxTemperature")
    public double electricForgeMaxTemperature = 1601.0D;

    @Config.Comment({"Electric forge energy consumption modifier."})
    @Config.RangeDouble(min = 0.01D, max = 1000.0D)
    @Config.LangKey("config." + MODID_TFCTECH + ".devices.electricForgeEnergyConsumption")
    public double electricForgeEnergyConsumption = 1;

    @Config.RequiresWorldRestart
    @Config.Comment({"Electric forge energy capacity."})
    @Config.RangeInt(min = 1000, max = 1_000_000_000)
    @Config.LangKey("config." + MODID_TFCTECH + ".devices.electricForgeEnergyCapacity")
    public int electricForgeEnergyCapacity = 10000;

    @Config.Comment({"Induction crucible energy consumption, in RF/t."})
    @Config.RangeInt(min = 1, max = 1_000_000_000)
    @Config.LangKey("config." + MODID_TFCTECH + ".devices.inductionCrucibleEnergyConsumption")
    public int inductionCrucibleEnergyConsumption = 20;

    @Config.RequiresWorldRestart
    @Config.Comment({"Induction crucible energy capacity."})
    @Config.RangeInt(min = 1000, max = 1_000_000_000)
    @Config.LangKey("config." + MODID_TFCTECH + ".devices.inductionCrucibleEnergyCapacity")
    public int inductionCrucibleEnergyCapacity = 10000;

    @Config.Comment({"Induction crucible target temperature."})
    @Config.RangeInt(min = 1, max = 1_000_000)
    @Config.LangKey("config." + MODID_TFCTECH + ".devices.inductionCrucibleTargetTemperature")
    public float inductionCrucibleTargetTemperature = 1601;

    @Config.Comment({"Fridge energy consumption modifier."})
    @Config.RangeDouble(min = 0.01D, max = 1000.0D)
    @Config.LangKey("config." + MODID_TFCTECH + ".devices.fridgeEnergyConsumption")
    public double fridgeEnergyConsumption = 1;

    @Config.RequiresWorldRestart
    @Config.Comment({"Fridge energy capacity."})
    @Config.RangeInt(min = 1000, max = 1_000_000_000)
    @Config.LangKey("config." + MODID_TFCTECH + ".devices.fridgeEnergyCapacity")
    public int fridgeEnergyCapacity = 10000;

    @Config.Comment({"How fast fridges reach frozen state."})
    @Config.RangeDouble(min = 0.01D, max = 1000.0D)
    @Config.LangKey("config." + MODID_TFCTECH + ".devices.fridgeEfficiency")
    public double fridgeEfficiency = 1;

    @Config.Comment({"How fast fridges lose efficiency when the door is open."})
    @Config.RangeDouble(min = 0.01D, max = 1000.0D)
    @Config.LangKey("config." + MODID_TFCTECH + ".devices.fridgeLoseEfficiency")
    public double fridgeLoseEfficiency = 1;
  }
}
