package su.terrafirmagreg.modules.animal.api.type;

/**
 * Use this to tell TFC this is a predator (for respawning mechanics)
 * Used only in TFC worlds.
 */
public interface IPredator extends ICreature {
	@Override
	default CreatureType getCreatureType() {
		return CreatureType.PREDATOR;
	}
}
