package net.dries007.tfc.objects.items.tools;

import su.terrafirmagreg.modules.core.capabilities.damage.spi.DamageType;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import mcp.MethodsReturnNonnullByDefault;
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

public class ItemRockHoe extends ItemHoe implements ICapabilitySize, IRockObject {

  private static final Map<RockCategory, ItemRockHoe> MAP = new HashMap<>();
  public final RockCategory category;
  private final float attackDamage;

  public ItemRockHoe(RockCategory category) {
    super(category.getToolMaterial());
    this.category = category;
    if (MAP.put(category, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }
    setHarvestLevel("hoe", category.getToolMaterial().getHarvestLevel());
    OreDictionaryHelper.register(this, "hoe");
    OreDictionaryHelper.register(this, "hoe", "stone");
    OreDictionaryHelper.register(this, "hoe", "stone", category);
    attackDamage = category.getToolMaterial().getAttackDamage() * 0.875f;
    OreDictionaryHelper.registerDamageType(this, DamageType.PIERCING);
  }

  public static ItemRockHoe get(RockCategory category) {
    return MAP.get(category);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Rock type: " + OreDictionaryHelper.toString(category));
  }

  @Override
  public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
    Multimap<String, AttributeModifier> multimap = HashMultimap.create();
    if (slot == EntityEquipmentSlot.MAINHAND) {
      multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
              new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", attackDamage, 0));
      multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", -3, 0));
    }
    return multimap;
  }

  @Override
  public @NotNull Size getSize(ItemStack stack) {
    return Size.LARGE; // Stored only in chests
  }

  @Override
  public @NotNull Weight getWeight(ItemStack stack) {
    return Weight.LIGHT;
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
