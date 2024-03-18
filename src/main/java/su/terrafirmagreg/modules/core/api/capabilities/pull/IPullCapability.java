package su.terrafirmagreg.modules.core.api.capabilities.pull;

import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodCart;

public interface IPullCapability {
	EntityWoodCart getDrawn();

	void setDrawn(EntityWoodCart drawnIn);
}
