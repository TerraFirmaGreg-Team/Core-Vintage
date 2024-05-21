package su.terrafirmagreg.api.capabilities.pull;

import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodCart;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface ICapabilityPull extends ICapabilityProvider {

    EntityWoodCart getDrawn();

    void setDrawn(EntityWoodCart drawnIn);
}
