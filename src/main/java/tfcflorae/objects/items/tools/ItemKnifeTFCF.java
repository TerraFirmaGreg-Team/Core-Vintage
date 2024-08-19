package tfcflorae.objects.items.tools;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


import com.google.common.collect.ImmutableSet;
import mcp.MethodsReturnNonnullByDefault;


import su.terrafirmagreg.modules.core.capabilities.damage.spi.DamageType;


import tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

@MethodsReturnNonnullByDefault

public class ItemKnifeTFCF extends ItemTool implements ICapabilitySize {

    public final ToolMaterial material;

    public ItemKnifeTFCF(ToolMaterial material, float AttackDamage, float AttackSpeed, int Durability, Object... oreNameParts) {
        super(material.getAttackDamage(), AttackSpeed, material, ImmutableSet.of());
        this.material = material;
        this.attackDamage = AttackDamage;
        this.attackSpeed = AttackSpeed;
        this.setMaxDamage(Durability);
        this.setHarvestLevel("knife", material.getHarvestLevel());

        for (Object obj : oreNameParts) {
            if (obj instanceof Object[])
                OreDictionaryHelper.register(this, (Object[]) obj);
            else
                OreDictionaryHelper.register(this, obj);
        }
        OreDictionaryHelper.registerDamageType(this, DamageType.PIERCING);
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return true;
    }

    @Override
    public @NotNull Size getSize(ItemStack stack) {
        return Size.NORMAL; // Stored in large vessels
    }

    @Override
    public @NotNull Weight getWeight(ItemStack stack) {
        return Weight.MEDIUM;
    }

    @Override
    public boolean canStack(ItemStack stack) {
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        // Knives always take damage
        if (!worldIn.isRemote) {
            stack.damageItem(1, entityLiving);
        }
        return true;
    }
}
