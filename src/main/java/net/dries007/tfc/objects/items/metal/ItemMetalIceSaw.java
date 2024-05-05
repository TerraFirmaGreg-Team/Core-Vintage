package net.dries007.tfc.objects.items.metal;

import su.terrafirmagreg.modules.core.init.ItemsCore;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;


import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.dries007.tfc.api.capability.metal.IMetalItem;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.sharkbark.cellars.ModConfig;
import net.sharkbark.cellars.items.ItemBase;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.dries007.tfc.objects.CreativeTabsTFC.CT_METAL;
import static su.terrafirmagreg.api.data.Constants.MODID_CELLARS;

@Mod.EventBusSubscriber(modid = MODID_CELLARS)
public class ItemMetalIceSaw extends ItemBase implements IMetalItem {

    private final Metal metal;
    private final double attackDamage;
    private final float attackSpeed;
    private final float efficiency;

    public ItemMetalIceSaw(Metal metal, String name) {
        super(name);
        this.metal = metal;
        ToolMaterial material = metal.getToolMetal();
        setMaxStackSize(1);
        setCreativeTab(CT_METAL);
        setMaxDamage(material.getMaxUses());
        efficiency = material.getEfficiency();
        attackDamage = 0.5 * material.getAttackDamage();
        attackSpeed = -2.8F;
    }

    @Override
    public @NotNull Size getSize(@NotNull ItemStack itemStack) {
        return Size.LARGE;
    }

    @Override
    public @NotNull Weight getWeight(@NotNull ItemStack itemStack) {
        return Weight.MEDIUM;
    }

    @Override
    public Metal getMetal(ItemStack itemStack) {
        return metal;
    }

    @Override
    public boolean canStack(@NotNull ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
        if (getDroppedItem(state) != null) {
            return true;
        }
        return super.canHarvestBlock(state, stack);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return canHarvestBlock(state, stack) ? efficiency : 1.0f;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        if (slot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
                    new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
                    new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", attackSpeed, 0));
        }
        return multimap;
    }

    @Override
    public int getSmeltAmount(ItemStack itemStack) {
        if (isDamageable() && itemStack.isItemDamaged()) {
            double d = (itemStack.getMaxDamage() - itemStack.getItemDamage()) / (double) itemStack.getMaxDamage() - .10;
            return d < 0 ? 0 : MathHelper.floor((double) 100 * d);

        } else {
            return 100;
        }
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemStack, World world, IBlockState state, BlockPos pos, EntityLivingBase player) {
        super.onBlockDestroyed(itemStack, world, state, pos, player);

        if (world.isRemote) {
            return false;
        }

        if (state.getBlockHardness(world, pos) > 0) {
            itemStack.damageItem(1, player);
        }

        Item droppedItem = getDroppedItem(state);
        if (droppedItem != null) {
            EntityItem entityItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(droppedItem, 1));
            world.spawnEntity(entityItem);
            world.setBlockToAir(pos);
        }

        return true;
    }

    @Nullable
    private Item getDroppedItem(IBlockState state) {
        Block block = state.getBlock();

        if (block == BlocksTFC.SEA_ICE) {
            if (ModConfig.disableShards) {
                return Item.getItemFromBlock(block);
            } else {
                return ItemsCore.SEA_ICE_SHARD;
            }
        } else if (block == Blocks.PACKED_ICE) {
            if (ModConfig.disableShards) {
                return Item.getItemFromBlock(block);
            } else {
                return ItemsCore.PACKED_ICE_SHARD;
            }
        } else if (block == Blocks.ICE) {
            if (ModConfig.disableShards) {
                return Item.getItemFromBlock(block);
            } else {
                return ItemsCore.ICE_SHARD;
            }
        }

        return null;
    }
}
