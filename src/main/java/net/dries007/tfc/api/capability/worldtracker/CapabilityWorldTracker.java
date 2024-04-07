package net.dries007.tfc.api.capability.worldtracker;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import net.dries007.tfc.api.capability.DumbStorage;
import net.dries007.tfc.util.Helpers;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;

public class CapabilityWorldTracker {

    public static final ResourceLocation KEY = new ResourceLocation(MODID_TFC, "world_tracker");
    @CapabilityInject(WorldTracker.class)
    public static Capability<WorldTracker> CAPABILITY = Helpers.getNull();

    public static void preInit() {
        CapabilityManager.INSTANCE.register(WorldTracker.class, new DumbStorage<>(), WorldTracker::new);
    }
}
