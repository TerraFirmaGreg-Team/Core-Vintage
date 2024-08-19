package tfcflorae.objects.items.tools;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;


import mcp.MethodsReturnNonnullByDefault;


import su.terrafirmagreg.modules.core.capabilities.damage.spi.DamageType;


import tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

@MethodsReturnNonnullByDefault

public class ItemAxeTFCF extends ItemAxe implements ICapabilitySize {

    public final ToolMaterial material;

    public ItemAxeTFCF(ToolMaterial material, float AttackDamage, float AttackSpeed, int Durability, Object... oreNameParts) {
        super(material, material.getAttackDamage(), AttackSpeed);
        this.material = material;
        this.attackDamage = (AttackDamage);
        this.attackSpeed = (AttackSpeed);
        this.setMaxDamage(Durability);
        this.setHarvestLevel("axe", material.getHarvestLevel());

        for (Object obj : oreNameParts) {
            if (obj instanceof Object[])
                OreDictionaryHelper.register(this, (Object[]) obj);
            else
                OreDictionaryHelper.register(this, obj);
        }
        OreDictionaryHelper.registerDamageType(this, DamageType.SLASHING);
    }

    @Override
    public boolean canHarvestBlock(IBlockState state) {
        Material material = state.getMaterial();
        return material == Material.WOOD || material == Material.GOURD || material == Material.PLANTS || material == Material.VINE;
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
}
