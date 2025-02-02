package net.dries007.waterflasks.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

import net.dries007.waterflasks.Waterflasks;

public class ClientProxy extends CommonProxy {

  @Override
  public void registerItemRenderer(Item item, int meta, String id) {
    ModelLoader.setCustomModelResourceLocation(item, meta,
      new ModelResourceLocation(Waterflasks.MOD_ID + ":" + id, "inventory"));
  }
}
