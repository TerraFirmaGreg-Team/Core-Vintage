package net.dries007.waterflasks.item;


import net.dries007.waterflasks.ConfigFlasks;

public class ItemLeatherFlask extends ItemFlask {

  protected static int CAPACITY = ConfigFlasks.GENERAL.leatherCap;
  protected static int DRINK = 100; //matches amount of water in TFC Jug

  public ItemLeatherFlask() {
    super("leather_flask", CAPACITY, DRINK);
  }

}
