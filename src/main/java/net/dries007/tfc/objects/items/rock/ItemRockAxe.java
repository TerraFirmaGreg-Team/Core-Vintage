package net.dries007.tfc.objects.items.rock;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import mcp.MethodsReturnNonnullByDefault;


import su.terrafirmagreg.modules.core.capabilities.damage.spi.DamageType;


import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.api.util.IRockObject;
import net.dries007.tfc.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MethodsReturnNonnullByDefault

public class ItemRockAxe extends ItemAxe implements ICapabilitySize, IRockObject {

    private static final Map<RockCategory, ItemRockAxe> MAP = new HashMap<>();
    public final RockCategory category;

    public ItemRockAxe(RockCategory category) {
        super(category.getToolMaterial(), category.getToolMaterial().getAttackDamage(), -3);
        this.category = category;
        if (MAP.put(category, this) != null) throw new IllegalStateException("There can only be one.");
        setHarvestLevel("axe", category.getToolMaterial().getHarvestLevel());
        OreDictionaryHelper.register(this, "axe");
        OreDictionaryHelper.register(this, "axe", "stone");
        OreDictionaryHelper.register(this, "axe", "stone", category);
        OreDictionaryHelper.registerDamageType(this, DamageType.SLASHING);
    }

    public static ItemRockAxe get(RockCategory category) {
        return MAP.get(category);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("Rock type: " + category);
    }

    @Override
    public @NotNull Size getSize(ItemStack stack) {
        return Size.LARGE; // Stored only in chests
    }

    @Override
    public @NotNull Weight getWeight(ItemStack stack) {
        return Weight.MEDIUM;
    }

    @Override
    public boolean canStack(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public Rock getRock(ItemStack stack) {
        return null;
    }

    @NotNull
    @Override
    public RockCategory getRockCategory(ItemStack stack) {
        return category;
    }
}
