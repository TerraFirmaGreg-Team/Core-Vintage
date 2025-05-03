package su.terrafirmagreg.modules.core.feature.heat;

import su.terrafirmagreg.api.util.CapabilityUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.api.util.TranslatorUtils;
import su.terrafirmagreg.framework.manager.feature.spi.FeatureBase;
import su.terrafirmagreg.modules.core.feature.heat.capability.CapabilityHeat;
import su.terrafirmagreg.modules.core.feature.heat.spi.Heat;

import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FeatureHeat extends FeatureBase {

  @Override
  public void onPreInit(FMLPreInitializationEvent event) {

    CapabilityHeat.register();
  }

  @Override
  public void onInit(FMLInitializationEvent event) {

    CapabilityHeat.Handler.init();
  }


  @SideOnly(Side.CLIENT)
  public static void onItemTooltipEvent(ItemTooltipEvent event) {
    var stack = event.getItemStack();
    var tooltip = event.getToolTip();

    if (!StackUtils.isValid(stack)) {return;}
    CapabilityUtils.getOptional(stack, CapabilityHeat.CAPABILITY).ifPresent(cap -> {
      float temperature = cap.getTemperature();
      if (stack.getItem() == Items.STICK) {
        if (temperature > cap.getMeltTemp() * 0.9f) {
          tooltip.add(I18n.format(TranslatorUtils.getEnumName("heat", "torch.lit")));
        } else if (temperature > 1f) {
          tooltip.add(I18n.format(TranslatorUtils.getEnumName("heat", "torch.catching_fire")));
        }
      } else {
        String heatTooltip = Heat.getTooltip(temperature);
        if (tooltip != null) {
          tooltip.add(heatTooltip);
        }
      }
    });
  }
}
