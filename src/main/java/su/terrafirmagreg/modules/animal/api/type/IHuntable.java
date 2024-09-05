package su.terrafirmagreg.modules.animal.api.type;

/**
 * Use this to tell TFC this is an huntable animal (for respawning mechanics) Used only in TFC worlds.
 */
public interface IHuntable extends ICreature {

  @Override
  default CreatureType getCreatureType() {
    return CreatureType.HUNTABLE;
  }
}
