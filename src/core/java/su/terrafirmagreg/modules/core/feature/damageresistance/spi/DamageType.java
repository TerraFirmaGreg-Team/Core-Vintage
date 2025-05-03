package su.terrafirmagreg.modules.core.feature.damageresistance.spi;

import su.terrafirmagreg.api.util.CapabilityUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.feature.damageresistance.capability.CapabilityDamageResistance;
import su.terrafirmagreg.modules.core.feature.damageresistance.capability.ICapabilityDamageResistance;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Resistances are positive, weaknesses are negative Calculations are done per <a href="https://www.desmos.com/calculator/689oqycw1t">this spreadsheet</a>
 */
@ParametersAreNonnullByDefault
public enum DamageType {
  CRUSHING,
  PIERCING,
  SLASHING,
  GENERIC;

  public static float getModifier(DamageSource source, EntityLivingBase entityUnderAttack) {
    DamageType type = DamageType.get(source);
    float resistance = 0;
    if (type != DamageType.GENERIC) {
      // Apply damage type specific resistances, from the entity under attack and from their armor
      {

        ICapabilityDamageResistance resist = CapabilityUtils.get(entityUnderAttack, CapabilityDamageResistance.CAPABILITY);

        resistance += type.getModifier(resist);
      }
      if (!source.isUnblockable()) {
        for (ItemStack stack : entityUnderAttack.getArmorInventoryList()) {
          ICapabilityDamageResistance resist;
          if (stack.getItem() instanceof ICapabilityDamageResistance capabilityDamageResistance) {
            resist = capabilityDamageResistance;
          } else {
            resist = CapabilityUtils.get(stack, CapabilityDamageResistance.CAPABILITY);
          }
          resistance += type.getModifier(resist);
        }
      }
    }
    return (float) Math.pow(Math.E, -0.01 * resistance);
  }

  @Nonnull
  private static DamageType get(DamageSource source) {
    // Unblockable damage types don't have a special damage source
    if (!source.isUnblockable()) {
      // First try and match damage types specified via config
      for (String damageType : ConfigCore.FEATURE.DAMAGE_RESISTANCE.slashingSources) {
        if (damageType.equals(source.damageType)) {
          return SLASHING;
        }
      }
      for (String damageType : ConfigCore.FEATURE.DAMAGE_RESISTANCE.crushingSources) {
        if (damageType.equals(source.damageType)) {
          return CRUSHING;
        }
      }
      for (String damageType : ConfigCore.FEATURE.DAMAGE_RESISTANCE.piercingSources) {
        if (damageType.equals(source.damageType)) {
          return PIERCING;
        }
      }

      // Next, try and check for an entity doing the damaging
      Entity sourceEntity = source.getTrueSource();
      if (sourceEntity != null) {
        // Check for the attacking weapon
        if (sourceEntity instanceof EntityLivingBase entityLivingBase) {
          ItemStack heldItem = entityLivingBase.getHeldItemMainhand();
          if (!heldItem.isEmpty()) {
            // Find a unique damage type for the weapon, if it exists
            DamageType weaponDamageType = getFromItem(heldItem);
            if (weaponDamageType != GENERIC) {
              return weaponDamageType;
            }
          }
        }

        // Check for config based entities
        ResourceLocation entityType = EntityList.getKey(sourceEntity);
        if (entityType != null) {
          String entityTypeName = entityType.toString();
          for (String damageType : ConfigCore.FEATURE.DAMAGE_RESISTANCE.slashingEntities) {
            if (damageType.equals(entityTypeName)) {
              return SLASHING;
            }
          }
          for (String damageType : ConfigCore.FEATURE.DAMAGE_RESISTANCE.crushingEntities) {
            if (damageType.equals(entityTypeName)) {
              return CRUSHING;
            }
          }
          for (String damageType : ConfigCore.FEATURE.DAMAGE_RESISTANCE.piercingEntities) {
            if (damageType.equals(entityTypeName)) {
              return PIERCING;
            }
          }
        }
      }
    }
    // Default to generic damage
    return GENERIC;
  }

  @Nonnull
  private static DamageType getFromItem(ItemStack stack) {
    if (OreDictUtils.contains(stack, "damageTypeCrushing")) {
      return CRUSHING;
    } else if (OreDictUtils.contains(stack, "damageTypeSlashing")) {
      return SLASHING;
    } else if (OreDictUtils.contains(stack, "damageTypePiercing")) {
      return PIERCING;
    }
    return GENERIC;
  }

  private float getModifier(@Nullable ICapabilityDamageResistance resistSource) {
    if (resistSource != null) {
      switch (this) {
        case CRUSHING:
          return resistSource.getCrushingModifier();
        case PIERCING:
          return resistSource.getPiercingModifier();
        case SLASHING:
          return resistSource.getSlashingModifier();
      }
    }
    return 1f;
  }
}
