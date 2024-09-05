package su.terrafirmagreg.modules.animal.api.type;

/**
 * Use this to tell TFC this is a livestock animal (for respawning mechanics) Used only in TFC worlds.
 */
public interface ILivestock extends ICreature {

  @Override
  default CreatureType getCreatureType() {
    return CreatureType.LIVESTOCK;
  }
}
