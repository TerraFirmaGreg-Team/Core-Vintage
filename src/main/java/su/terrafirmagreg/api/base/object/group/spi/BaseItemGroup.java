package su.terrafirmagreg.api.base.object.group.spi;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.module.api.IModule;
import su.terrafirmagreg.framework.registry.RegistryManager;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Supplier;


public class BaseItemGroup extends CreativeTabs {

  private final String identifier;
  private final Supplier<ItemStack> icon;


  private boolean hasSearchBar = false;
  private Consumer<Consumer<ItemStack>> filler;
  private Comparator<ItemStack> sorter;

  private BaseItemGroup(String identifier, Supplier<ItemStack> icon) {
    super(identifier);

    this.identifier = identifier;
    this.icon = icon;

    sortAlphabetically();

    if (hasSearchBar) {
      setBackgroundImageName("item_search.png");
    }
  }

  public static Supplier<BaseItemGroup> of(String identifier, String icon) {
    return of(identifier, ModUtils.resource(icon));
  }
  

  public static Supplier<BaseItemGroup> of(IModule module, String icon) {
    var identifier = module.getIdentifier();
    return of(ModUtils.localize(identifier), ModUtils.regKey(identifier.getPath(), icon));
  }

  public static Supplier<BaseItemGroup> of(String identifier, ResourceLocation icon) {
    return of(identifier, () -> new ItemStack(ForgeRegistries.ITEMS.getValue(icon)));
  }


  public static Supplier<BaseItemGroup> of(String identifier, Supplier<ItemStack> icon) {
    return () -> new BaseItemGroup(identifier, icon);
  }

  public BaseItemGroup enableSearchBar() {
    this.hasSearchBar = true;
    return this;
  }

  /**
   * Sets a custom filler for this creative tab. By default, the creative will be filled by items with this tab set in their properties.
   *
   * @param filler a functions which pushes items to the given consumer
   */
  public BaseItemGroup filler(Consumer<Consumer<ItemStack>> filler) {
    this.filler = filler;
    return this;
  }

  /**
   * Set the sorter to sort items alphabetically based on their display name.
   */
  public BaseItemGroup sortAlphabetically() {
    return this.sorter(Comparator.comparing(ItemStack::getDisplayName));
  }

  /**
   * Sets a sorter for the items in this creative tab.
   *
   * @param sorter compares two item stacks
   */
  public BaseItemGroup sorter(Comparator<ItemStack> sorter) {
    this.sorter = sorter;
    return this;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public String getTabLabel() {
    return this.identifier;
  }


  @Override
  public String getTranslationKey() {
    return ModUtils.localize("item_group", identifier, "name");
  }

  @Override
  public ItemStack createIcon() {
    if (this.icon == null) {
      RegistryManager.LOGGER.error("Icon supplier was null for CreativeTab {}", getTabLabel());
      return new ItemStack(Items.STICK);
    }

    ItemStack stack = this.icon.get();
    if (stack == null) {
      RegistryManager.LOGGER.error("Icon supplier return null for CreativeTab {}", getTabLabel());
      return new ItemStack(Items.STICK);
    }

    if (stack.isEmpty()) {
      RegistryManager.LOGGER.error("Icon built from iconSupplied is EMPTY for CreativeTab {}", getTabLabel());
      return new ItemStack(Items.STICK);
    }
    return stack;
  }

  @Override
  public void displayAllRelevantItems(NonNullList<ItemStack> items) {
    // Fill the list with items
    if (this.filler == null) {super.displayAllRelevantItems(items);} else {this.filler.accept(items::add);}
    // Sort the items
    if (this.sorter != null) {items.sort(this.sorter);}
  }

  @Override
  public boolean hasSearchBar() {
    return this.hasSearchBar;
  }

}
