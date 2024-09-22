package net.dries007.tfc.objects.items.metal;

import su.terrafirmagreg.modules.core.capabilities.metal.ICapabilityMetal;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.capability.forge.ForgeableHeatableHandler;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.blocks.metal.BlockTrapDoorMetalTFC;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class ItemMetal extends ItemTFC implements ICapabilityMetal {

  private static final Map<Metal, EnumMap<Metal.ItemType, ItemMetal>> TABLE = new HashMap<>();
  protected final Metal metal;
  protected final Metal.ItemType type;

  @SuppressWarnings("ConstantConditions")
  public ItemMetal(Metal metal, Metal.ItemType type) {
    this.metal = metal;
    this.type = type;

    if (!TABLE.containsKey(metal)) {
      TABLE.put(metal, new EnumMap<>(Metal.ItemType.class));
    }
    TABLE.get(metal).put(type, this);

    setNoRepair();
    if (type == Metal.ItemType.DOUBLE_INGOT) {
      OreDictionaryHelper.register(this, "ingot", "double", metal.getRegistryName().getPath());
      if (metal == Metal.BRONZE || metal == Metal.BISMUTH_BRONZE || metal == Metal.BLACK_BRONZE) {
        OreDictionaryHelper.register(this, "ingot", "double", "Any", "Bronze");
      }
      if (metal == Metal.WROUGHT_IRON && ConfigTFC.General.MISC.dictionaryIron) {
        OreDictionaryHelper.register(this, "ingot", "double", "Iron");
      }
    } else if (type == Metal.ItemType.DOUBLE_SHEET) {
      OreDictionaryHelper.register(this, "sheet", "double", metal.getRegistryName().getPath());
      if (metal == Metal.BRONZE || metal == Metal.BISMUTH_BRONZE || metal == Metal.BLACK_BRONZE) {
        OreDictionaryHelper.register(this, "sheet", "double", "Any", "Bronze");
      }
      if (metal == Metal.WROUGHT_IRON && ConfigTFC.General.MISC.dictionaryIron) {
        OreDictionaryHelper.register(this, "sheet", "double", "Iron");
      }
    } else if (type.isToolItem()) {
      OreDictionaryHelper.register(this, type);
    } else {
      OreDictionaryHelper.register(this, type, metal.getRegistryName().getPath());
      if (metal == Metal.BRONZE || metal == Metal.BISMUTH_BRONZE || metal == Metal.BLACK_BRONZE) {
        OreDictionaryHelper.register(this, type, "Any", "Bronze");
      }
      if (type == Metal.ItemType.SHEET && ConfigTFC.General.MISC.dictionaryPlates) {
        OreDictionaryHelper.register(this, "plate", metal);
      }
      if (metal == Metal.WROUGHT_IRON && ConfigTFC.General.MISC.dictionaryIron) {
        OreDictionaryHelper.register(this, type, "Iron");
        if (type == Metal.ItemType.SHEET && ConfigTFC.General.MISC.dictionaryPlates) //Register plate for iron too
        {
          OreDictionaryHelper.register(this, "plate", "Iron");
        }
      }

    }

    if (type == Metal.ItemType.TUYERE) {
      setMaxDamage(metal.getToolMetal() != null ? (int) (metal.getToolMetal().getMaxUses() * 0.2) : 100);
    }
  }

  public static Item get(Metal metal, Metal.ItemType type) {
    if (type == Metal.ItemType.SWORD) {
      // Make sure to not crash (in 1.15+, don't forget to rewrite all metal items to extend the proper vanilla classes)
      return ItemMetalSword.get(metal);
    }
    if (type == Metal.ItemType.TRAPDOOR) {
      return ItemBlock.getItemFromBlock(BlockTrapDoorMetalTFC.get(metal));
    }
    return TABLE.get(metal).get(type);
  }

  @Override
  public float getMeltTemp(ItemStack stack) {
    return metal.getMeltTemp();
  }

  @Override
  public Metal getMetal(ItemStack stack) {
    return metal;
  }

  @Override
  public int getSmeltAmount(ItemStack stack) {
    if (!isDamageable() || !stack.isItemDamaged()) {
      return type.getSmeltAmount();
    }
    double d = (stack.getMaxDamage() - stack.getItemDamage()) / (double) stack.getMaxDamage() - .10;
    return d < 0 ? 0 : MathHelper.floor(type.getSmeltAmount() * d);
  }

  @Override
  public @NotNull Weight getWeight(@NotNull ItemStack stack) {
    return switch (type) {
      case DUST, NUGGET, SCRAP -> Weight.VERY_LIGHT; // Stacksize = 64
      case INGOT, DOUBLE_INGOT, SHEET, DOUBLE_SHEET -> Weight.LIGHT; // Stacksize = 32
      case HELMET, GREAVES, CHESTPLATE, BOOTS, UNFINISHED_CHESTPLATE, UNFINISHED_GREAVES, UNFINISHED_HELMET, UNFINISHED_BOOTS -> Weight.VERY_HEAVY; // Stacksize = 1
      default -> Weight.MEDIUM; // Stacksize = 16 for everything else, but tools will still stack only to 1
    };
  }

  @Override
  public @NotNull Size getSize(@NotNull ItemStack stack) {
    return switch (type) {
      case NUGGET, DUST, SCRAP -> Size.SMALL; // Fits in Small Vessels
      case PICK_HEAD, HAMMER_HEAD, HOE_HEAD, AXE_HEAD, CHISEL_HEAD, JAVELIN_HEAD, MACE_HEAD, PROPICK_HEAD, SHOVEL_HEAD, KNIFE_BLADE, SAW_BLADE, SCYTHE_BLADE,
           SWORD_BLADE, ICE_SAW_HEAD -> Size.NORMAL; // Tool heads fits in large vessels
      default -> Size.LARGE; // Everything else fits only in chests
    };
  }

  @Override
  public boolean canStack(@NotNull ItemStack stack) {
    return switch (type) {
      case ROD, DUST, TRAPDOOR, SCRAP, INGOT, SHEET, NUGGET, AXE_HEAD, HOE_HEAD, MACE_HEAD, PICK_HEAD, SAW_BLADE, CHISEL_HEAD, HAMMER_HEAD, KNIFE_BLADE,
           SHOVEL_HEAD, SWORD_BLADE, DOUBLE_INGOT, DOUBLE_SHEET, JAVELIN_HEAD, PROPICK_HEAD, SCYTHE_BLADE -> true;
      default -> false;
    };
  }

  @Override
  public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
    return this.type == Metal.ItemType.KNIFE || super.doesSneakBypassUse(stack, world, pos, player);
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    return new ForgeableHeatableHandler(nbt, metal.getSpecificHeat(), metal.getMeltTemp());
  }

  @Override
  @NotNull
  public IRarity getForgeRarity(@NotNull ItemStack stack) {
    switch (metal.getTier()) {
      case TIER_I:
      case TIER_II:
        return EnumRarity.COMMON;
      case TIER_III:
        return EnumRarity.UNCOMMON;
      case TIER_IV:
        return EnumRarity.RARE;
      case TIER_V:
        return EnumRarity.EPIC;
    }
    return super.getForgeRarity(stack);
  }

  public Metal.ItemType getType() {
    return type;
  }
}
