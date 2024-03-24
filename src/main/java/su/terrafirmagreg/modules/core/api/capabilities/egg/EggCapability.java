package su.terrafirmagreg.modules.core.api.capabilities.egg;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import su.terrafirmagreg.api.util.ModUtils;

public class EggCapability {
	public static final ResourceLocation KEY = ModUtils.getID("egg_capability");
	@CapabilityInject(IEggCapability.class)
	public static Capability<IEggCapability> EGG_CAPABILITY;

	public static void preInit() {
		CapabilityManager.INSTANCE.register(IEggCapability.class, new EggStorage(), EggProvider::new);
	}
}
