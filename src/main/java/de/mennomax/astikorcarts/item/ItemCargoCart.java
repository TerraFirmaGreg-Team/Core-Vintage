package de.mennomax.astikorcarts.item;

import net.minecraft.world.World;

import de.mennomax.astikorcarts.entity.AbstractDrawn;
import de.mennomax.astikorcarts.entity.EntityCargoCart;

public class ItemCargoCart extends AbstractCartItem
{
    public ItemCargoCart()
    {
        super("cargocart");
    }

    @Override
    public AbstractDrawn newCart(World worldIn)
    {
        return new EntityCargoCart(worldIn);
    }
}