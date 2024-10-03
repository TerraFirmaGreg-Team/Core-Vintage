package su.terrafirmagreg.modules.core.capabilities.sharpness;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public interface ICapabilitySharpness {

  int getCharges();

  void setCharges(int charges);

  void addCharge();

  void removeCharge();

  @SideOnly(Side.CLIENT)
  default void addSharpnessInfo(ItemStack stack, List<String> text) {
    if (getCharges() > 0) {
      TextFormatting color = getCharges() > 64 ? getCharges() > 256 ? TextFormatting.DARK_PURPLE : TextFormatting.BLUE : TextFormatting.DARK_GREEN;
      text.add(I18n.format("tfcthings.tooltip.sharpness", color, "" + getCharges()));
    }
  }
}
