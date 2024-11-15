package su.terrafirmagreg.api.base.creativetab;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public class BaseItemGroup extends CreativeTabs {

  private final boolean hasSearchBar;
  private final Supplier<ItemStack> iconSupplier;

  public BaseItemGroup(String TabName, ResourceLocation iconSupplier, boolean hasSearchBar) {
    super(ModUtils.localize(TabName));

    this.iconSupplier = () -> new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(iconSupplier)));
    this.hasSearchBar = hasSearchBar;

    if (hasSearchBar) {
      setBackgroundImageName("item_search.png");
    }
  }

  public static BaseItemGroup of(String TabName, String iconSupplier) {
    return BaseItemGroup.of(TabName, iconSupplier, false);
  }

  public static BaseItemGroup of(String TabName, String iconSupplier, boolean hasSearchBar) {
    return new BaseItemGroup(TabName, ModUtils.resource(iconSupplier), hasSearchBar);
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

    if (stack.isEmpty()) {
      TerraFirmaGreg.LOGGER.error("Icon built from iconSupplied is EMPTY for CreativeTab {}",
                                  getTabLabel());
      return new ItemStack(Items.STICK);
    }

    // Food stacks shouldn't rot in creative tabs, and these are created on demand instead of beforehand and cached
    //CapabilityFood.setStackNonDecaying(stack);
    return stack;
  }

  @Override
  public boolean hasSearchBar() {
    return hasSearchBar;
  }
}
