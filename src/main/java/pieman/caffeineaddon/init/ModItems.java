package pieman.caffeineaddon.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;


import net.dries007.tfc.objects.items.ItemCoffee;
import net.dries007.tfc.objects.items.ItemCoffeeHeatable;
import net.dries007.tfc.objects.items.ItemMug;
import net.dries007.tfc.objects.items.ItemPotteryCA;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

  public static final List<Item> ITEMS = new ArrayList<>();
  public static final List<ItemBlock> ITEMBLOCKS = new ArrayList<>();

  public static final Item GreenCoffeeBeans = new ItemCoffeeHeatable("coffee_beans_green");
  public static final Item CoffeeBeans = new ItemCoffee("coffee_beans");
  public static final Item GroundCoffee = new ItemCoffee("coffee_ground");
  public static final Item Mug = new ItemMug("fired_mug");
  public static final Item MugUnfired = new ItemPotteryCA("unfired_mug");

}
