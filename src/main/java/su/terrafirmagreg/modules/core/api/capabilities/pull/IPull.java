package su.terrafirmagreg.modules.core.api.capabilities.pull;

import de.mennomax.astikorcarts.entity.AbstractDrawn;

public interface IPull {
	AbstractDrawn getDrawn();

	void setDrawn(AbstractDrawn drawnIn);
}
