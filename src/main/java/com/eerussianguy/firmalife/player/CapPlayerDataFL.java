package com.eerussianguy.firmalife.player;

import net.dries007.tfc.api.capability.DumbStorage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.ParametersAreNonnullByDefault;

import static su.terrafirmagreg.Constants.MODID_FL;

@ParametersAreNonnullByDefault
public final class CapPlayerDataFL {
	public static ResourceLocation NAMESPACE = new ResourceLocation(MODID_FL, "firmalife_data");
	@CapabilityInject(IPlayerDataFL.class)
	public static Capability<IPlayerDataFL> CAPABILITY;

	public static void preInit() {
		CapabilityManager.INSTANCE.register(IPlayerDataFL.class, new DumbStorage<>(), () -> null);
	}

}
