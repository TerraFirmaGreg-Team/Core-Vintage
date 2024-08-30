package su.terrafirmagreg.modules.rock.objects.containers;

import net.dries007.tfc.api.recipes.knapping.KnappingTypes;


import su.terrafirmagreg.modules.core.objects.container.ContainerBaseKnapping;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class ContainerKnappingRock extends ContainerBaseKnapping {

    public ContainerKnappingRock(InventoryPlayer playerInv, ItemStack stack) {
        super(KnappingTypes.STONE, playerInv, stack);
    }
}
