package su.terrafirmagreg.api.util;

import su.terrafirmagreg.TerraFirmaGreg;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.google.common.collect.Lists;
import se.gory_moon.horsepower.Configs;

import org.jetbrains.annotations.Nullable;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

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

  private static final ConcurrentHashMap<String, Class<? extends EntityCreature>> ENTITY_CLASSES_CACHE = new ConcurrentHashMap<>();


  public static ArrayList<Class<? extends EntityCreature>> getCreatureClasses() {
    ArrayList<Class<? extends EntityCreature>> clazzes = Lists.newArrayList();
    if (Configs.general.useHorseInterface) {
      clazzes.add(AbstractHorse.class);
    }

    for (String string : Configs.general.grindstoneMobList) {
      Class clazz = EntityUtils.getEntityClass(string);

      if (clazz == null) {
        continue;
      }

      if (EntityCreature.class.isAssignableFrom(clazz)) {
        clazzes.add(clazz);
      } else {
        TerraFirmaGreg.LOGGER.error("Error in config, the mob ( {} ) can't be leashed", string);
      }
    }
    return clazzes;
  }

  public static @Nullable Class<? extends Entity> getEntityClass(String mobName) {
    return ENTITY_CLASSES_CACHE.computeIfAbsent(mobName, k -> {
      Class clazz = ClassUtils.getClassFromString(mobName);
      if (Entity.class.isAssignableFrom(clazz)) {
        return clazz;
      } else {
        TerraFirmaGreg.LOGGER.error("Error in config, the Entity ( {} ) can't be leashed", mobName);
        return null;
      }
    });
  }

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
  @Nullable
  public static <T extends Entity> T getEntity(World world, BlockPos pos, Class<T> aClass) {
    Entity entity = world.getEntityByID(pos.getX());
    if (aClass.isInstance(entity)) {
      return (T) entity;
    }
    return null;
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
