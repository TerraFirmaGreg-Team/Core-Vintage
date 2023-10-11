package net.dries007.tfc.module.rock.init;

import net.dries007.tfc.module.animal.objects.tiles.TEPlacedHide;
import net.dries007.tfc.module.ceramic.objects.tiles.TELargeVessel;
import net.dries007.tfc.module.core.api.util.IHasModel;
import net.dries007.tfc.module.core.objects.tiles.TEPlacedItem;
import net.dries007.tfc.module.core.objects.tiles.TEPlacedItemFlat;
import net.dries007.tfc.module.core.objects.tiles.TEPowderKeg;
import net.dries007.tfc.module.core.objects.tiles.TETickCounter;
import net.dries007.tfc.module.rock.StorageRock;
import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.util.registry.Registry;

public class BlocksRock {

    public static void onRegister(Registry registry) {

        for (var block : StorageRock.ROCK_BLOCKS.values()) {
            var itemBlock = block.getItemBlock();
            if (itemBlock != null) registry.registerBlock((Block) block, block.getItemBlock(), block.getName());
            else registry.registerBlock((Block) block, block.getName());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {

        registry.registerClientModelRegistrationStrategy(() -> {
            StorageRock.ROCK_BLOCKS.values().forEach(IHasModel::onModelRegister);
        });
    }
}
