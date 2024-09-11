package su.terrafirmagreg.modules.core.capabilities.pull;

import su.terrafirmagreg.modules.wood.object.entity.EntityWoodCart;

public interface ICapabilityPull {

  EntityWoodCart getDrawn();

  void setDrawn(EntityWoodCart drawnIn);
}
