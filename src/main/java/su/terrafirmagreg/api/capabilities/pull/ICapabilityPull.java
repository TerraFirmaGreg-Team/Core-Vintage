package su.terrafirmagreg.api.capabilities.pull;

import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodCart;

public interface ICapabilityPull {

    EntityWoodCart getDrawn();

    void setDrawn(EntityWoodCart drawnIn);
}
