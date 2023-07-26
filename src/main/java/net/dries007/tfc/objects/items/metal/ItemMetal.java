/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.items.metal;

import gregtech.api.unification.material.Material;
import net.dries007.tfc.api.capability.metal.IMaterialItem;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.blocks.metal.BlockTrapDoorMetalTFC;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockMetalLamp;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

@ParametersAreNonnullByDefault
public class ItemMetal extends ItemTFC implements IMaterialItem
{
    private static final Map<Metal, EnumMap<Metal.ItemType, ItemMetal>> TABLE = new HashMap<>();

    public ItemMetal() {

    }

    public static Item get(Metal metal, Metal.ItemType type)
    {
        if (type == Metal.ItemType.SWORD)
        {
            // Make sure to not crash (in 1.15+, don't forget to rewrite all metal items to extend the proper vanilla classes)
            return ItemMetalSword.get(metal);
        }
        if (type == Metal.ItemType.LAMP)
        {
            return ItemBlockMetalLamp.get(metal);
        }
        if (type == Metal.ItemType.TRAPDOOR)
        {
            return ItemBlock.getItemFromBlock(BlockTrapDoorMetalTFC.get(metal));
        }
        return TABLE.get(metal).get(type);
    }

    protected Material metal;
    protected Metal.ItemType type;



    @Override
    public Material getMaterial(ItemStack stack)
    {
        return metal;
    }

    @Override
    public int getSmeltAmount(ItemStack stack) {
        if (!isDamageable() || !stack.isItemDamaged()) return type.getSmeltAmount();
        double d = (stack.getMaxDamage() - stack.getItemDamage()) / (double) stack.getMaxDamage() - .10;
        return d < 0 ? 0 : MathHelper.floor(type.getSmeltAmount() * d);
    }


    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack)
    {
        return Size.LARGE;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack)
    {
        return Weight.VERY_HEAVY;
    }


}
