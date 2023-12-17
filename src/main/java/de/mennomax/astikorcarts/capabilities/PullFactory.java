package de.mennomax.astikorcarts.capabilities;

import de.mennomax.astikorcarts.entity.AbstractDrawn;

public class PullFactory implements IPull {
    private AbstractDrawn drawn = null;

    @Override
    public AbstractDrawn getDrawn() {
        return this.drawn;
    }

    @Override
    public void setDrawn(AbstractDrawn drawnIn) {
        this.drawn = drawnIn;
    }
}
