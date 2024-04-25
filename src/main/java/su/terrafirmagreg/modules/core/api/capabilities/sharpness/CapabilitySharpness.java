package su.terrafirmagreg.modules.core.api.capabilities.sharpness;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilitySharpness {

    public static final ResourceLocation KEY = ModUtils.id("sharpness_capability");

    @CapabilityInject(ICapabilitySharpness.class)
    public static Capability<ICapabilitySharpness> SHARPNESS_CAPABILITY;

    public static void preInit() {
        CapabilityManager.INSTANCE.register(ICapabilitySharpness.class, new StorageSharpness(), ProviderSharpness::new);

    }

    public static ICapabilitySharpness get(ItemStack itemStack) {
        return itemStack.getCapability(SHARPNESS_CAPABILITY, null);
    }

    public static boolean has(ItemStack itemStack) {
        return itemStack.hasCapability(SHARPNESS_CAPABILITY, null);
    }
}
