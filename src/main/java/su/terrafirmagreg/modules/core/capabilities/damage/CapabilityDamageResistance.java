package su.terrafirmagreg.modules.core.capabilities.damage;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import su.terrafirmagreg.api.util.ModUtils;

public final class CapabilityDamageResistance {

    public static final ResourceLocation KEY = ModUtils.resource("damage_resistance_capability");

    @CapabilityInject(ICapabilityDamageResistance.class)
    public static final Capability<ICapabilityDamageResistance> CAPABILITY = ModUtils.getNull();

    public static void register() {
        CapabilityManager.INSTANCE.register(ICapabilityDamageResistance.class, new CapabilityStorageDamageResistance(), CapabilityProviderDamageResistance::new);
    }

    public static ICapabilityDamageResistance get(ItemStack itemStack) {
        return itemStack.getCapability(CAPABILITY, null);
    }

    public static ICapabilityDamageResistance get(Entity entity) {
        return entity.getCapability(CAPABILITY, null);
    }

    public static boolean has(ItemStack itemStack) {
        return itemStack.hasCapability(CAPABILITY, null);
    }

    public static boolean has(Entity entity) {
        return entity.hasCapability(CAPABILITY, null);
    }

}
