package su.terrafirmagreg.framework.manager.content.base.group.spi;

import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.content.ContentManager;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;


public class BaseItemGroup extends CreativeTabs {

  private static final Map<String, List<ItemStack>> TAB_ITEMS = new Object2ObjectOpenHashMap<>();

  private final String identifier;
  private final Supplier<ItemStack> icon;


  private boolean hasSearchBar = false;
  private Consumer<Consumer<ItemStack>> filler;
  private Comparator<ItemStack> sorter;

  private BaseItemGroup(String identifier, Supplier<ItemStack> icon) {
    super(identifier);

    this.identifier = identifier;
    this.icon = icon;

    if (hasSearchBar) {
      setBackgroundImageName("item_search.png");
    }
  }

  public static BaseItemGroup of(String identifier, String icon) {

    return of(identifier, ModUtils.resource(icon));
  }

  public static BaseItemGroup of(ResourceLocation identifier, String icon) {

    return of(ModUtils.localize(identifier), ModUtils.regKey(identifier.getPath(), icon));
  }

  public static BaseItemGroup of(ResourceLocation identifier, ResourceLocation icon) {

    return of(ModUtils.localize(identifier), icon);
  }

  public static BaseItemGroup of(ResourceLocation identifier, Item icon) {

    return of(ModUtils.localize(identifier), () -> new ItemStack(icon));
  }

  public static BaseItemGroup of(String identifier, ResourceLocation icon) {

    return of(identifier, () -> new ItemStack(ForgeRegistries.ITEMS.getValue(icon)));
  }


  public static BaseItemGroup of(String identifier, Supplier<ItemStack> icon) {

    return new BaseItemGroup(identifier, icon);
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

  public BaseItemGroup sortAlphabeticallyTranslated() {
    return this.sorter(Comparator.comparing(ItemStack::getTranslationKey));
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

  public static void addToTab(String identifier, ItemStack stack) {
    TAB_ITEMS.computeIfAbsent(identifier, k -> new ArrayList<>()).add(stack);
  }

  public void addToTab(Item item) {
    addToTab(identifier, new ItemStack(item));
  }

  public void addToTab(Block block) {
    addToTab(identifier, new ItemStack(block));
  }

  @Override
  @SideOnly(Side.CLIENT)
  public String getTabLabel() {
    return this.identifier;
  }


  @Override
  public String getTranslationKey() {
    return ModUtils.localize(LocalizeKeys.ITEM_GROUP, identifier, "name");
  }

  @Override
  public ItemStack createIcon() {
    if (this.icon == null) {
      ContentManager.LOGGER.error("Icon supplier was null for CreativeTab {}", getTabLabel());
      return new ItemStack(Items.STICK);
    }

    ItemStack stack = this.icon.get();
    if (stack == null) {
      ContentManager.LOGGER.error("Icon supplier return null for CreativeTab {}", getTabLabel());
      return new ItemStack(Items.STICK);
    }

    if (stack.isEmpty()) {
      ContentManager.LOGGER.error("Icon built from iconSupplied is EMPTY for CreativeTab {}", getTabLabel());
      return new ItemStack(Items.STICK);
    }
    return stack;
  }

  @Override
  public void displayAllRelevantItems(NonNullList<ItemStack> items) {
    List<ItemStack> tabItems = TAB_ITEMS.get(identifier);
    if (tabItems != null) {
      items.addAll(tabItems);
    }

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
