package su.terrafirmagreg.api.spi.creativetab;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import gregtech.api.util.BaseCreativeTab;

import java.util.function.Supplier;

import static su.terrafirmagreg.Tags.MOD_ID;

public class CreativeTabBase extends BaseCreativeTab {

    public CreativeTabBase(String TabName, String iconSupplier) {
        this(TabName, iconSupplier, false);
    }

    public CreativeTabBase(String TabName, String iconSupplier, boolean hasSearchBar) {
        super(MOD_ID + "." + TabName, () -> new ItemStack(ForgeRegistries.ITEMS.getValue(ModUtils.getID(iconSupplier))), hasSearchBar);
    }

    public CreativeTabBase(String TabName, Supplier<ItemStack> iconSupplier, boolean hasSearchBar) {
        super(MOD_ID + "." + TabName, iconSupplier, hasSearchBar);
    }
}
