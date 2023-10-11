package net.dries007.tfc.module.core.init;

import net.dries007.tfc.module.core.objects.entities.EntityFallingBlockTFC;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import su.terrafirmagreg.util.registry.Registry;

public class EntitiesCore {

    public static void onRegister(Registry registry) {

        registry.createEntityEntry("falling_block", EntityEntryBuilder.create()
                .entity(EntityFallingBlockTFC.class)
                .tracker(160, 20, true)
        );

//        registry.createEntityEntry("thrown_javelin", EntityEntryBuilder.create()
//                .entity(EntityThrownJavelin.class)
//                .tracker(160, 20, true)
//        );
    }
}
