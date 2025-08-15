package su.terrafirmagreg.modules.wood.object.item;

import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItemEntity;
import su.terrafirmagreg.framework.manager.registry.provider.IProviderItemColor;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;
import su.terrafirmagreg.modules.wood.object.entity.EntityWoodPlowCart;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import lombok.Getter;

@Getter
public class ItemWoodPlowCart extends BaseItemEntity implements IWoodEntry, IProviderItemColor {

  protected final WoodType type;

  public ItemWoodPlowCart(WoodType type) {
    super(ItemSettings.of()
      .registryKey(type.getRegistryKey("plow_cart"))
      .customResource(type.getResource("plow_cart"))
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
  public EntityWoodPlowCart createEntity(World worldIn, Entity location, ItemStack itemstack) {
    var cart = new EntityWoodPlowCart(worldIn);
    cart.setWood(type);
    return cart;
  }
}
