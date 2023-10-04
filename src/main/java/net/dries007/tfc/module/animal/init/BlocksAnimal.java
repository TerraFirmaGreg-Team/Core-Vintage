package net.dries007.tfc.module.animal.init;

import su.terrafirmagreg.util.registry.Registry;
import su.terrafirmagreg.util.util.ModelRegistrationHelper;
import net.dries007.tfc.module.core.api.util.RegistryHelper;
import net.dries007.tfc.module.animal.objects.blocks.BlockNestBox;
import net.dries007.tfc.module.animal.objects.tiles.TENestBox;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlocksAnimal {

    public static BlockNestBox NEST_BOX;

    public static void onRegister(Registry registry) {

        registry.registerBlock(NEST_BOX = new BlockNestBox(), NEST_BOX.getItemBlock(), BlockNestBox.NAME);

        RegistryHelper.registerTileEntities(
                registry,
                TENestBox.class
        );
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {

        registry.registerClientModelRegistrationStrategy(() -> {
            ModelRegistrationHelper.registerBlockItemModels(
                    NEST_BOX
            );
        });
    }
}
