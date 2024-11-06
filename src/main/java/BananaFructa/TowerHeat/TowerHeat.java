package BananaFructa.TowerHeat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.Heat;
import net.dries007.tfc.api.capability.heat.IItemHeat;
import net.dries007.tfc.client.gui.GuiBlastFurnace;
import net.dries007.tfc.client.gui.GuiContainerTE;
import net.dries007.tfc.objects.te.TEBlastFurnace;

import java.util.ArrayList;
import java.util.List;

@Mod(modid = TowerHeat.modId, version = TowerHeat.version, name = TowerHeat.name)
public class TowerHeat {

  public static final String modId = "tfctowerheat";
  public static final String name = "TFC TowerHeat";
  public static final String version = "1.2";
  TEBlastFurnace cachedBlastFurnace = null;
  List<Float> tempList = new ArrayList<>();
  long lastBurningTicks = 0;

  @Mod.EventHandler
  public void init(FMLInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(this);
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onGuiOpen(GuiOpenEvent event) {

    if (event.getGui() instanceof GuiBlastFurnace) {
      cachedBlastFurnace = Utils.readDeclaredField(GuiContainerTE.class, event.getGui(), "tile");
      if (cachedBlastFurnace != null) {
        readTemperatures();
        lastBurningTicks = cachedBlastFurnace.getBurnTicksLeft();
      }
    }

  }

  @SideOnly(Side.CLIENT)
  public String getLayerTemp(float avgTemp) {

    String ttString = Heat.getTooltip(avgTemp);
    if (ttString == null) {return "Cold";} else {return ttString.replaceAll("\u2605", "");}

  }

  @SideOnly(Side.CLIENT)
  public void readTemperatures() {

    tempList.clear();
    for (int i = 0; i < cachedBlastFurnace.getOreStacks().size(); i++) {
      IItemHeat heatInf = cachedBlastFurnace.getOreStacks().get(i).getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
      if (heatInf != null) {
        tempList.add(heatInf.getTemperature());
      }
    }
  }

  @SideOnly(Side.CLIENT)
  @SubscribeEvent
  public void onGuiRender(GuiScreenEvent.BackgroundDrawnEvent event) {
    if (event.getGui() instanceof GuiBlastFurnace && tempList != null && cachedBlastFurnace != null) {

      ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

      if (lastBurningTicks != cachedBlastFurnace.getBurnTicksLeft()) {
        lastBurningTicks = cachedBlastFurnace.getBurnTicksLeft();
        readTemperatures();
      }

      if (tempList.size() != 0) {

        Gui.drawRect(
          sr.getScaledWidth() / 2 - 206,
          sr.getScaledHeight() / 2 - 82, sr.getScaledWidth() / 2 - 91, sr.getScaledHeight() / 2 - 81 + 11 * ((tempList.size()) / 4), 0x77000000);

        Gui.drawRect(sr.getScaledWidth() / 2 - 206, sr.getScaledHeight() / 2 - 81, sr.getScaledWidth() / 2 - 91, sr.getScaledHeight() / 2 - 82, 0xff000000);
        Gui.drawRect(
          sr.getScaledWidth() / 2 - 206,
          sr.getScaledHeight() / 2 - 81 + 11 * ((tempList.size()) / 4),
          sr.getScaledWidth() / 2 - 91, sr.getScaledHeight() / 2 - 82 + 11 * ((tempList.size()) / 4), 0xff000000);

        Gui.drawRect(
          sr.getScaledWidth() / 2 - 206,
          sr.getScaledHeight() / 2 - 81, sr.getScaledWidth() / 2 - 207, sr.getScaledHeight() / 2 - 82 + 11 * ((tempList.size()) / 4), 0xff000000);
        Gui.drawRect(
          sr.getScaledWidth() / 2 - 91,
          sr.getScaledHeight() / 2 - 81, sr.getScaledWidth() / 2 - 90, sr.getScaledHeight() / 2 - 82 + 11 * ((tempList.size()) / 4), 0xff000000);

        float sum = 0;

        for (int i = 0; i < tempList.size(); i++) {
          sum += tempList.get(i);
          if ((i + 1) % 4 == 0) {
            sum /= 4;
            Minecraft.getMinecraft().fontRenderer.drawString(
              "Layer " + ((i + 1) / 4) + ": " + getLayerTemp(sum),
              sr.getScaledWidth() / 2 - 205, sr.getScaledHeight() / 2 - 80 + 11 * ((i + 1) / 4 - 1), 0xffffff);
            sum = 0;
          } else if (i + 1 == tempList.size()) {
            sum /= (i + 1) % 4;
            Minecraft.getMinecraft().fontRenderer.drawString(
              "Layer " + ((i + 1) / 4 + 1) + ": " + getLayerTemp(sum),
              sr.getScaledWidth() / 2 - 205, sr.getScaledHeight() / 2 - 80 + 11 * ((i + 1) / 4), 0xffffff);
            sum = 0;
          }
        }

      }

    }
  }

}
