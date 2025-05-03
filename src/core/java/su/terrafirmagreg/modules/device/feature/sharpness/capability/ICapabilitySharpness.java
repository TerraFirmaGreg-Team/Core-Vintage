package su.terrafirmagreg.modules.device.feature.sharpness.capability;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface ICapabilitySharpness extends ICapabilityProvider {

  int getCharges();

  void setCharges(int charges);

  void addCharge();

  void removeCharge();
}
