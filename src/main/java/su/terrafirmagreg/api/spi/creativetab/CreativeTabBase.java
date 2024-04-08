package su.terrafirmagreg.api.spi.creativetab;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static su.terrafirmagreg.Tags.MOD_ID;

public class CreativeTabBase extends CreativeTabs {

    private final boolean hasSearchBar;
    private final Supplier<ItemStack> iconSupplier;

    public CreativeTabBase(String TabName, String iconSupplier) {
        this(TabName, iconSupplier, false);
    }

    public CreativeTabBase(String TabName, String iconSupplier, boolean hasSearchBar) {
        this(MOD_ID + "." + TabName, () -> new ItemStack(ForgeRegistries.ITEMS.getValue(ModUtils.getID(iconSupplier))), hasSearchBar);
    }

    public CreativeTabBase(String TabName, Supplier<ItemStack> iconSupplier, boolean hasSearchBar) {
        super(MOD_ID + "." + TabName);

        this.iconSupplier = iconSupplier;
        this.hasSearchBar = hasSearchBar;

        if (hasSearchBar) setBackgroundImageName("item_search.png");
    }

    @NotNull
    @Override
    public ItemStack createIcon() {
        if (iconSupplier == null) {
            TerraFirmaGreg.LOGGER.error("Icon supplier was null for CreativeTab {}", getTabLabel());
            return new ItemStack(Items.STICK);
        }

        ItemStack stack = iconSupplier.get();
        if (stack == null) {
            TerraFirmaGreg.LOGGER.error("Icon supplier return null for CreativeTab {}", getTabLabel());
            return new ItemStack(Items.STICK);
        }

        if (stack == ItemStack.EMPTY) {
            TerraFirmaGreg.LOGGER.error("Icon built from iconSupplied is EMPTY for CreativeTab {}", getTabLabel());
            return new ItemStack(Items.STICK);
        }

        return stack;
    }

    @Override
    public boolean hasSearchBar() {
        return hasSearchBar;
    }
}
