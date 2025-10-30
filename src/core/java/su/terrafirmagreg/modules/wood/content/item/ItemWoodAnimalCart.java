package su.terrafirmagreg.modules.wood.content.item;

import su.terrafirmagreg.framework.manager.content.base.item.spi.BaseItemEntity;
import su.terrafirmagreg.framework.manager.content.provider.IProviderItemColor;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.wood.content.entity.EntityWoodAnimalCart;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import lombok.Getter;

@Getter
public class ItemWoodAnimalCart extends BaseItemEntity implements IWoodEntry, IProviderItemColor {

  protected final WoodType type;

  public ItemWoodAnimalCart(WoodType type) {
    super(ItemSettings.of()
      .customResource(type.getResource("animal_cart"))
      .addOreDict("supply_cart")
      .capability(CapabilityProviderSize.of(Size.HUGE, Weight.VERY_HEAVY))
      .maxStackSize(1)
    );

    this.type = type;
  }

  @Override
  public IItemColor getItemColor() {
    return (s, i) -> this.getType().getColor();
  }

  @Override
  public EntityWoodAnimalCart createEntity(World worldIn, Entity location, ItemStack itemstack) {
    var cart = new EntityWoodAnimalCart(worldIn);
    cart.setWood(type);
    return cart;
  }
}
