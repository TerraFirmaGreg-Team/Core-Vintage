package su.terrafirmagreg.modules.core.feature.damageresistance.capability;

public interface ICapabilityDamageResistance {

  default float getCrushingModifier() {
    return 0;
  }

  default float getPiercingModifier() {
    return 0;
  }

  default float getSlashingModifier() {
    return 0;
  }
}
