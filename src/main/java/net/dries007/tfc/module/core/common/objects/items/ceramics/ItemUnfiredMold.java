package net.dries007.tfc.module.core.common.objects.items.ceramics;

import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.TerraFirmaCraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;

import static net.dries007.tfc.module.wood.ModuleWood.POTTERY_TAB;

public class ItemUnfiredMold extends ItemPottery {

    public final OrePrefix orePrefix;

    public ItemUnfiredMold(OrePrefix orePrefix) {
        this.orePrefix = orePrefix;

        setCreativeTab(POTTERY_TAB);
        setRegistryName(TerraFirmaCraft.getID("ceramics/unfired/mold/" + orePrefix.name.toLowerCase()));
    }

    @Nonnull
    @Override
    public String getItemStackDisplayName(@Nonnull ItemStack stack) {
        return
                new TextComponentTranslation(
                        "item.tfc.ceramics.unfired.mold.name",
                        new TextComponentTranslation("item.material.oreprefix." + orePrefix.name).getFormattedText().replaceFirst(" ", "")
                ).getFormattedText();
    }
}
