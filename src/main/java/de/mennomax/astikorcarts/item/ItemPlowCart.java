package de.mennomax.astikorcarts.item;

import net.minecraft.world.World;

import de.mennomax.astikorcarts.entity.AbstractDrawn;
import de.mennomax.astikorcarts.entity.EntityPlowCart;

public class ItemPlowCart extends AbstractCartItem
{
    public ItemPlowCart()
    {
        super("plowcart");
    }

    @Override
    public AbstractDrawn newCart(World worldIn)
    {
        return new EntityPlowCart(worldIn);
    }
}