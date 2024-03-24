package su.terrafirmagreg.modules.core.api.capabilities.damage;

public interface IDamageResistanceCapability {
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
