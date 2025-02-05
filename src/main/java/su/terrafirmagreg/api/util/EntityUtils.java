package su.terrafirmagreg.api.util;

import su.terrafirmagreg.TerraFirmaGreg;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.Nullable;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.UUID;
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

  private static final Map<String, UUID> USERNAME = CollectionUtils.invertMap(UsernameCache.getMap());

  @SuppressWarnings("unchecked")
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

  public static @Nullable Class<? extends Entity> getEntity(String mobName) {
    ResourceLocation key = new ResourceLocation(mobName);
    EntityEntry entityEntry = ForgeRegistries.ENTITIES.getValue(key);
    if (entityEntry != null) {
      return entityEntry.getEntityClass();
    }
    return null;
  }

  @SideOnly(Side.CLIENT)
  public static UUID getClientPlayerUUID() {
    Minecraft minecraft = GameUtils.getMinecraft();

    return minecraft.getSession().getProfile().getId();
  }

  public static UUID getUUID(String player) {
    return USERNAME.get(player);
  }

  public static boolean hasUUID(String player) {
    return USERNAME.containsKey(player);
  }

  public static EnumHand getHandForItemAndMeta(EntityPlayer player, Item item, int meta) {
    for (EnumHand hand : EnumHand.values()) {
      ItemStack heldStack = player.getHeldItem(hand);

      if (!heldStack.isEmpty() && heldStack.getItem() == item && heldStack.getMetadata() == meta) {return hand;}
    }

    return EnumHand.MAIN_HAND;
  }

  /**
   * Calculates the distance between an entity and a BlockPos.
   *
   * @param entity The Entity to use for the first position.
   * @param pos    The BlockPos to use for the second position.
   * @return double The distance between the Entity and the BlockPos.
   */
  public static double getDistaceFromPos(Entity entity, BlockPos pos) {

    return MathUtils.getDistanceBetweenPoints(entity.getPositionVector(), new Vec3d(pos));
  }

  /**
   * Pushes an entity towards a specific direction.
   *
   * @param entityToMove The entity that you want to push.
   * @param direction    The direction to push the entity.
   * @param force        The amount of force to push the entity with.
   */
  public static void pushTowards(Entity entityToMove, EnumFacing direction, double force) {

    pushTowards(entityToMove, entityToMove.getPosition().offset(direction.getOpposite(), 1), force);
  }

  /**
   * Pushes an Entity towards a BlockPos.
   *
   * @param entityToMove The entity that you want to push.
   * @param pos          The BlockPos to push the entity towards.
   * @param force        The amount of force to push the entity with.
   */
  public static void pushTowards(Entity entityToMove, BlockPos pos, double force) {

    final BlockPos entityPos = entityToMove.getPosition();
    final double distanceX = (double) pos.getX() - entityPos.getX();
    final double distanceY = (double) pos.getY() - entityPos.getY();
    final double distanceZ = (double) pos.getZ() - entityPos.getZ();
    applyMotion(entityToMove, force, distanceX, distanceY, distanceZ);
  }

  private static void applyMotion(Entity entityToMove, double force, double distanceX, double distanceY, double distanceZ) {
    final double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);

    if (distance > 0) {

      entityToMove.motionX = distanceX / distance * force;
      entityToMove.motionY = distanceY / distance * force;
      entityToMove.motionZ = distanceZ / distance * force;
    }
  }

  /**
   * Pushes an entity towards another one.
   *
   * @param entityToMove The entity that should be pushed towards the other entity.
   * @param destination  The destination entity, that the entity to move should be pushed towards.
   * @param force        The amount of force to push the entityToMove with.
   */
  public static void pushTowards(Entity entityToMove, Entity destination, double force) {

    final double distanceX = destination.posX - entityToMove.posX;
    final double distanceY = destination.posY - entityToMove.posY;
    final double distanceZ = destination.posZ - entityToMove.posZ;
    applyMotion(entityToMove, force, distanceX, distanceY, distanceZ);
  }

  /**
   * Creates a Vec3d that represents the additional motion that would be needed to push an entity towards a destination.
   *
   * @param entityToMove The entity to push.
   * @param direction    The direction to push the entity.
   * @param force        The amount of force to use.
   * @return A Vec3d object that represents the motion of pushing the entity towards the destination.
   */
  public static Vec3d pushTowardsDirection(Entity entityToMove, EnumFacing direction, double force) {

    final BlockPos entityPos = entityToMove.getPosition();
    final BlockPos destination = entityToMove.getPosition().offset(direction.getOpposite(), 1);

    final double distanceX = (double) destination.getX() - entityPos.getX();
    final double distanceY = (double) destination.getY() - entityPos.getY();
    final double distanceZ = (double) destination.getZ() - entityPos.getZ();
    final double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);

    return distance > 0 ? new Vec3d(entityToMove.motionX = distanceX / distance * force, entityToMove.motionY =
      distanceY / distance * force, entityToMove.motionZ = distanceZ / distance * force) : new Vec3d(0d, 0d, 0d);
  }

  /**
   * Checks if two entities are close enough together.
   *
   * @param firstEntity  The first entity to check.
   * @param secondEntity The second entity to check.
   * @param maxDistance  The maximum distance that the entities can be apart.
   * @return boolean True if the distance between the entities are within range of the maxDistance.
   */
  public static boolean areEntitiesCloseEnough(Entity firstEntity, Entity secondEntity, double maxDistance) {

    return getDistanceFromEntity(firstEntity, secondEntity) < maxDistance * maxDistance;
  }

  /**
   * Calculates the distance between two entities.
   *
   * @param firstEntity  The first entity to use.
   * @param secondEntity The second entity to use.
   * @return double The distance between the two entities passed.
   */
  public static double getDistanceFromEntity(Entity firstEntity, Entity secondEntity) {

    return MathUtils.getDistanceBetweenPoints(firstEntity.getPositionVector(), secondEntity.getPositionVector());
  }

  /**
   * Gets a List of entities that are within the provided area.
   *
   * @param entityClass The type of entity you are looking for.
   * @param world       The world to search in.
   * @param pos         The position to start the search around.
   * @param range       The range of the search.
   * @return List<Entity> A List containing all entities of the specified type that are within the range.
   */
  @SuppressWarnings("unchecked")
  public static <T> List<T> getEntitiesInArea(Class<? extends Entity> entityClass, World world, BlockPos pos, int range) {

    return (List<T>) world.getEntitiesWithinAABB(entityClass, new AxisAlignedBB(pos.add(-range, -range, -range), pos.add(range + 1, range + 1, range + 1)));
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

  @Nullable
  public static Entity getEntity(World world, BlockPos pos) {
    return world.getEntityByID(pos.getX());
  }


  /**
   * Gets the type of equipment for slot index.
   *
   * @param index The index of the slot.
   * @return EntityEquipmentSlot The slot for the index.
   */
  public static @Nullable EntityEquipmentSlot getEquipmentSlot(int index) {
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

    return entity.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue();
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
