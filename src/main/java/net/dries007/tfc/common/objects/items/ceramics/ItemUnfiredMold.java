package net.dries007.tfc.common.objects.items.ceramics;

import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class ItemUnfiredMold extends ItemPottery {

    public final OrePrefix orePrefix;

    public ItemUnfiredMold(OrePrefix orePrefix) {
        this.orePrefix = orePrefix;

        setCreativeTab(CreativeTabsTFC.POTTERY);
        setRegistryName(TerraFirmaCraft.MOD_ID, "ceramics/unfired/mold/" + orePrefix.name.toLowerCase());
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
