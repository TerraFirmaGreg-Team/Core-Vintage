package net.dries007.tfc.module.ceramic.init;

import net.dries007.tfc.module.ceramic.objects.blocks.BlockLargeVessel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.util.registry.Registry;

public class BlocksCeramic {

    public static BlockLargeVessel FIRED_LARGE_VESSEL;

    public static void onRegister(Registry registry) {

        registry.registerBlock(FIRED_LARGE_VESSEL = new BlockLargeVessel(), BlockLargeVessel.NAME);
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {

        registry.registerClientModelRegistrationStrategy(() -> {

        });
    }
}
