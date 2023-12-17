package de.mennomax.astikorcarts.capabilities;

import de.mennomax.astikorcarts.entity.AbstractDrawn;

public interface IPull {
    AbstractDrawn getDrawn();

    void setDrawn(AbstractDrawn drawnIn);
}
