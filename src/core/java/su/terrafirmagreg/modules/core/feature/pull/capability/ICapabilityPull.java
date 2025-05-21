package su.terrafirmagreg.modules.core.feature.pull.capability;

import net.dries007.astikorcarts.entity.AbstractDrawn;

public interface ICapabilityPull {

  // TODO entityId?
  AbstractDrawn getDrawn();

  void setDrawn(AbstractDrawn drawnIn);
}
