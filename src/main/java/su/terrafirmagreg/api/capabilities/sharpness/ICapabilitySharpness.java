package su.terrafirmagreg.api.capabilities.sharpness;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface ICapabilitySharpness extends ICapabilityProvider {

    int getCharges();

    void setCharges(int charges);

    void addCharge();

    void removeCharge();
}
