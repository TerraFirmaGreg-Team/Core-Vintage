package su.terrafirmagreg.modules.wood.objects.entities.ai;

import su.terrafirmagreg.api.capabilities.pull.CapabilityPull;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityWoodAIPullCart extends EntityAIBase {

    private final EntityLiving living;

    public EntityWoodAIPullCart(EntityLiving livingIn) {
        this.living = livingIn;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        if (CapabilityPull.has(this.living)) {
            return CapabilityPull.get(this.living).getDrawn() != null;
        }
        return false;
    }

}
