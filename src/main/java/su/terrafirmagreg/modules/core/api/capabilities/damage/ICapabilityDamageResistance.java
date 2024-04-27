package su.terrafirmagreg.modules.core.api.capabilities.damage;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface ICapabilityDamageResistance extends ICapabilityProvider {

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
