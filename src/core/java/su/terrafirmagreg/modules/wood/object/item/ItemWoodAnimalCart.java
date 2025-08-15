package su.terrafirmagreg.modules.wood.object.item;

import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;
import su.terrafirmagreg.modules.wood.object.entity.EntityWoodAnimalCart;
import su.terrafirmagreg.modules.wood.object.entity.spi.EntityWoodCart;
import su.terrafirmagreg.modules.wood.object.item.spi.ItemWoodCart;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWoodAnimalCart extends ItemWoodCart {

  public ItemWoodAnimalCart(WoodType type) {
    super(type, "animal_cart");
  }

  @Override
  public EntityWoodCart createEntity(World worldIn, Entity location, ItemStack itemstack) {
    return new EntityWoodAnimalCart(worldIn);
  }
}
