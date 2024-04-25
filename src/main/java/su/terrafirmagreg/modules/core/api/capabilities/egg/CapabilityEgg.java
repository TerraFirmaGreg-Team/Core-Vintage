package su.terrafirmagreg.modules.core.api.capabilities.egg;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityEgg {

    public static final ResourceLocation KEY = ModUtils.id("egg_capability");
    @CapabilityInject(ICapabilityEgg.class)
    public static Capability<ICapabilityEgg> EGG_CAPABILITY;

    public static void preInit() {
        CapabilityManager.INSTANCE.register(ICapabilityEgg.class, new StorageEgg(), ProviderEgg::new);

    }

    public static ICapabilityEgg get(ItemStack itemStack) {
        return itemStack.getCapability(EGG_CAPABILITY, null);
    }

    public static boolean has(ItemStack itemStack) {
        return itemStack.hasCapability(EGG_CAPABILITY, null);
    }
}
