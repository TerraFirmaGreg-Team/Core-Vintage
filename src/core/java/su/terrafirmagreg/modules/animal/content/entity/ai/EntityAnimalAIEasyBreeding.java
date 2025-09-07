package su.terrafirmagreg.modules.animal.content.entity.ai;

import su.terrafirmagreg.modules.animal.ConfigAnimal;
import su.terrafirmagreg.modules.animal.api.type.IAnimal;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EntityAnimalAIEasyBreeding<T extends EntityAnimal & IAnimal> extends EntityAIBase {

  private final T animal;
  private final World world;

  public EntityAnimalAIEasyBreeding(T animal) {
    this.animal = animal;
    this.world = animal.world;
  }

//  @SubscribeEvent
//  public void addAI(LivingEvent.LivingUpdateEvent event) {
//    var entityLiving = event.getEntityLiving();
//    if (entityLiving instanceof EntityAnimalBase animal && entityLiving.ticksExisted < 5 && !entityLiving.isChild()) {
//      animal.tasks.addTask(2, new EntityAnimalAIEasyBreeding(animal));
//    }
//  }

  public boolean shouldExecute() {
    EntityItem closeFood = checkFood();
    if ((closeFood != null) && (this.animal.isFood(closeFood.getItem()))) {
      if (this.animal.isReadyToMate()) {
        execute(this.animal, closeFood);
      }
            /*
            else if (this.animal.isHungry())
            {
                // TO DO
            }
            */
    }
    return false;
  }

  @Nullable
  public EntityItem checkFood() {
    List<EntityItem> items = getItems();
    for (EntityItem item : items) {
      return item;
    }
    return null;
  }

  public void execute(T animal, EntityItem item) {
    if (animal.getNavigator().tryMoveToXYZ(item.posX, item.posY, item.posZ, 1.25F)) {
      if (animal.getDistance(item) < 1.0F) {
        consumeFood(item);
        animal.setInLove(null);
      }
    }
  }

  List<EntityItem> getItems() {
    return world.getEntitiesWithinAABB(
      EntityItem.class,
      new AxisAlignedBB(
        animal.posX - ConfigAnimal.MISC.searchDistance,
        animal.posY - ConfigAnimal.MISC.searchDistance,
        animal.posZ - ConfigAnimal.MISC.searchDistance,
        animal.posX + ConfigAnimal.MISC.searchDistance,
        animal.posY + ConfigAnimal.MISC.searchDistance,
        animal.posZ + ConfigAnimal.MISC.searchDistance
      ));
  }

  public void consumeFood(EntityItem item) {
    ItemStack stack = item.getItem();
    stack.setCount(stack.getCount() - 1);
    if (stack.getCount() == 0) {
      item.setDead();
    }
    this.world.playSound(null, item.getPosition(), SoundEvents.ENTITY_PLAYER_BURP,
      SoundCategory.AMBIENT, 1.0F, 1.0F);
  }
}
