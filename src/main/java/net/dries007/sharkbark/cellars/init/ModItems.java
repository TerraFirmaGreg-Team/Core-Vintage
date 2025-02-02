package net.dries007.sharkbark.cellars.init;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

import net.dries007.tfc.util.Helpers;
import net.dries007.sharkbark.cellars.items.ItemIceShard;
import net.dries007.sharkbark.cellars.util.Reference;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

  public static final List<Item> ITEMS = new ArrayList<Item>();

  @ObjectHolder(Reference.MOD_ID + ":ice_shard")
  public static final ItemIceShard ICE_SHARD = Helpers.getNull();
  @ObjectHolder(Reference.MOD_ID + ":packed_ice_shard")
  public static final ItemIceShard PACKED_ICE_SHARD = Helpers.getNull();
  @ObjectHolder(Reference.MOD_ID + ":sea_ice_shard")
  public static final ItemIceShard SEA_ICE_SHARD = Helpers.getNull();

  public static void registerItems(IForgeRegistry<Item> registry) {
    registry.registerAll(

      new ItemIceShard("ice_shard"),
      new ItemIceShard("packed_ice_shard"),
      new ItemIceShard("sea_ice_shard")
    );
  }
}
