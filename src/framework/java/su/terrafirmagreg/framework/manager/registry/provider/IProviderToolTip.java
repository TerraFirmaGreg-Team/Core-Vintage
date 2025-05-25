package su.terrafirmagreg.framework.manager.registry.provider;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public interface IProviderToolTip {

  @SideOnly(Side.CLIENT)
  default void addTooltipInfo(ItemStack stack, List<String> tooltip, ITooltipFlag flag) {}
}
