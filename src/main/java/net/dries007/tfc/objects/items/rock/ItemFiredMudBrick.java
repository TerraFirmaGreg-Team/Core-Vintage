package net.dries007.tfc.objects.items.rock;

import su.terrafirmagreg.modules.core.capabilities.heat.ProviderHeat;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.api.util.IRockObject;
import net.dries007.tfc.objects.items.ItemTFCF;
import tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ItemFiredMudBrick extends ItemTFCF implements IRockObject {

    private static final Map<ItemUnfiredMudBrick, ItemFiredMudBrick> MAP = new HashMap<>();
    private final ItemUnfiredMudBrick rock;

    public ItemFiredMudBrick(ItemUnfiredMudBrick rock) {
        this.rock = rock;
        if (MAP.put(rock, this) != null) throw new IllegalStateException("There can only be one.");
        setMaxDamage(0);
        OreDictionaryHelper.register(this, "mud", "brick");
        OreDictionaryHelper.register(this, "mud", "brick", rock.getRock());
        OreDictionaryHelper.register(this, "mud", "brick", rock.getRock().getRockCategory());
    }

    public static ItemFiredMudBrick get(Rock rock) {
        return MAP.get(ItemUnfiredMudBrick.get(rock));
    }

    public static ItemFiredMudBrick get(ItemMud mud) {
        return MAP.get(ItemUnfiredMudBrick.get(mud));
    }

    public static ItemFiredMudBrick get(ItemUnfiredMudBrick rock) {
        return MAP.get(rock);
    }

    public static ItemStack get(ItemUnfiredMudBrick rock, int amount) {
        return new ItemStack(MAP.get(rock), amount);
    }

    @Override
    public @NotNull Size getSize(ItemStack stack) {
        return Size.SMALL; // Stored everywhere
    }

    @Override
    public @NotNull Weight getWeight(ItemStack stack) {
        return Weight.LIGHT; // Stacksize = 32
    }

    @NotNull
    @Override
    public Rock getRock(ItemStack stack) {
        return rock.getRock();
    }

    @NotNull
    @Override
    public RockCategory getRockCategory(ItemStack stack) {
        return rock.getRock().getRockCategory();
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        // Heat capability, as pottery needs to be able to be fired, or survive despite not having a heat capability
        return new ProviderHeat(nbt, 1.0f, 1599f);
    }
}
