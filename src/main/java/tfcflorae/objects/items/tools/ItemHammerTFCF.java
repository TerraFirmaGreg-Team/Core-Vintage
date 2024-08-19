package tfcflorae.objects.items.tools;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;


import com.google.common.collect.ImmutableSet;
import mcp.MethodsReturnNonnullByDefault;


import su.terrafirmagreg.modules.core.capabilities.damage.spi.DamageType;


import tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

@MethodsReturnNonnullByDefault
public class ItemHammerTFCF extends ItemTool implements ICapabilitySize {

    public final ToolMaterial material;

    public ItemHammerTFCF(ToolMaterial material, float AttackDamage, float AttackSpeed, int Durability, Object... oreNameParts) {
        super(AttackDamage, AttackSpeed, material, ImmutableSet.of());
        this.material = material;
        this.attackDamage = (AttackDamage);
        this.attackSpeed = (AttackSpeed);
        this.setMaxDamage(Durability);
        this.setHarvestLevel("hammer", material.getHarvestLevel());

        for (Object obj : oreNameParts) {
            if (obj instanceof Object[])
                OreDictionaryHelper.register(this, (Object[]) obj);
            else
                OreDictionaryHelper.register(this, obj);
        }
        OreDictionaryHelper.registerDamageType(this, DamageType.CRUSHING);
    }

    @Override
    public @NotNull Size getSize(ItemStack stack) {
        return Size.LARGE;  // Stored only in chests
    }

    @Override
    public @NotNull Weight getWeight(ItemStack stack) {
        return Weight.MEDIUM;
    }

    @Override
    public boolean canStack(ItemStack stack) {
        return false;
    }
}
