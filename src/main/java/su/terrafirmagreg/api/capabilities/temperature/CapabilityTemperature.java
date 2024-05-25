package su.terrafirmagreg.api.capabilities.temperature;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityTemperature {

    public static final ResourceLocation KEY = ModUtils.resource("temperature_capability");

    @CapabilityInject(ICapabilityTemperature.class)
    public static Capability<ICapabilityTemperature> CAPABILITY;

    public static void preInit() {
        CapabilityManager.INSTANCE.register(ICapabilityTemperature.class, new StorageTemperature(), ProviderTemperature::new);

    }

    public static ICapabilityTemperature get(Entity entity) {
        return entity.getCapability(CAPABILITY, null);
    }

    public static boolean has(Entity entity) {
        return entity.hasCapability(CAPABILITY, null);
    }
}
