package su.terrafirmagreg.api.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("unused")
public final class EntityUtils {

  /**
   * An array of armor equipment slots.
   */
  private static final EntityEquipmentSlot[] EQUIPMENT_SLOTS = new EntityEquipmentSlot[]{
          EntityEquipmentSlot.HEAD,
          EntityEquipmentSlot.CHEST,
          EntityEquipmentSlot.LEGS,
          EntityEquipmentSlot.FEET
  };

  /**
   * Возвращает экземпляр типизированного объекта Entity по его классу.
   *
   * @param world  игровой мир
   * @param pos    позиция блока
   * @param aClass класс типизированного объекта Entity
   * @param <T>    тип типизированного объекта Entity
   * @return экземпляр типизированного объекта Entity
   */
  @SuppressWarnings("unchecked")
  public static <T extends Entity> T getEntity(World world, BlockPos pos, Class<T> aClass) {
    Entity entity = world.getEntityByID(pos.getX());
    if (!aClass.isInstance(entity)) {
      return null;
    }
    return (T) entity;
  }

  /**
   * Gets the type of equipment for slot index.
   *
   * @param index The index of the slot.
   * @return EntityEquipmentSlot The slot for the index.
   */
  public static EntityEquipmentSlot getEquipmentSlot(int index) {
    if (index >= 0 && index < EQUIPMENT_SLOTS.length) {
      return EQUIPMENT_SLOTS[index];
    }

    return null;
  }

  /**
   * Gets the max health value of an entity.
   *
   * @param entity The entity to get the value from.
   * @return The value of the attribute.
   */
  public static double getMaxHealth(EntityLivingBase entity) {

    return entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue();
  }

  /**
   * Gets the follow/tracking range value of an entity.
   *
   * @param entity The entity to get the value from.
   * @return The value of the attribute.
   */
  public static double getFollowRange(EntityLivingBase entity) {

    return entity.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
  }

  /**
   * Gets the knockback resistance value of an entity.
   *
   * @param entity The entity to get the value from.
   * @return The value of the attribute.
   */
  public static double getKnockbackResistance(EntityLivingBase entity) {

    return entity.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE)
            .getAttributeValue();
  }

  /**
   * Gets the movement speed value of an entity.
   *
   * @param entity The entity to get the value from.
   * @return The value of the attribute.
   */
  public static double getMovementSpeed(EntityLivingBase entity) {

    return entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
  }

  /**
   * Gets the attack value of an entity.
   *
   * @param entity The entity to get the value from.
   * @return The value of the attribute.
   */
  public static double getAttackDamage(EntityLivingBase entity) {

    return entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
  }

  /**
   * Gets the attack speed value of an entity.
   *
   * @param entity The entity to get the value from.
   * @return The value of the attribute.
   */
  public static double getAttackSpeed(EntityLivingBase entity) {

    return entity.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
  }

  /**
   * Gets the armor value of an entity.
   *
   * @param entity The entity to get the value from.
   * @return The value of the attribute.
   */
  public static double getArmor(EntityLivingBase entity) {

    return entity.getEntityAttribute(SharedMonsterAttributes.ARMOR).getAttributeValue();
  }

  /**
   * Gets the armor toughness value of an entity.
   *
   * @param entity The entity to get the value from.
   * @return The value of the attribute.
   */
  public static double getArmorToughness(EntityLivingBase entity) {

    return entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue();
  }

  /**
   * Gets the luck value of an entity.
   *
   * @param entity The entity to get the value from.
   * @return The value of the attribute.
   */
  public static double getLuck(EntityLivingBase entity) {

    return entity.getEntityAttribute(SharedMonsterAttributes.LUCK).getAttributeValue();
  }

  /**
   * Gets a value of an attribute for an entity.
   *
   * @param entity    The entity to get the value of.
   * @param attribute The attribute to get the value of.
   * @return The value of the attribute.
   */
  public static double getAttributeValue(EntityLivingBase entity, IAttribute attribute) {

    return entity.getEntityAttribute(attribute).getAttributeValue();
  }
}
