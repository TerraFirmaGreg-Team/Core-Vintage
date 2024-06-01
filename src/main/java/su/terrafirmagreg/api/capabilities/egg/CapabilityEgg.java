package su.terrafirmagreg.api.capabilities.egg;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityEgg {

    public static final ResourceLocation KEY = ModUtils.resource("egg_capability");
    @CapabilityInject(ICapabilityEgg.class)
    public static final Capability<ICapabilityEgg> CAPABILITY = ModUtils.getNull();

    public static void register() {
        CapabilityManager.INSTANCE.register(ICapabilityEgg.class, new StorageEgg(), ProviderEgg::new);

    }

    public static ICapabilityEgg get(ItemStack itemStack) {
        return itemStack.getCapability(CAPABILITY, null);
    }

    public static boolean has(ItemStack itemStack) {
        return itemStack.hasCapability(CAPABILITY, null);
    }
}
