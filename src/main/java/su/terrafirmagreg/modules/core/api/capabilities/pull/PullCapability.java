package su.terrafirmagreg.modules.core.api.capabilities.pull;

import lombok.Getter;
import lombok.Setter;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodCart;

@Setter
@Getter
public class PullCapability implements IPullCapability {

	private EntityWoodCart drawn = null;

}
