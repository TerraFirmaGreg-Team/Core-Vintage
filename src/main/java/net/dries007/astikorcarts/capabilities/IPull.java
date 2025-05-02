package net.dries007.astikorcarts.capabilities;

import net.dries007.astikorcarts.entity.AbstractDrawn;

public interface IPull {

  AbstractDrawn getDrawn();

  void setDrawn(AbstractDrawn drawnIn);
}
