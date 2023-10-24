package mod.acgaming.easybreedingtfc.ai;

import java.util.List;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import net.dries007.tfc.objects.entity.animal.EntityAnimalTFC;

import static mod.acgaming.easybreedingtfc.config.EBConfig.searchDistance;

public class EBEntityAI extends EntityAIBase
{
    private final EntityAnimalTFC animal;
    private final World world;

    public EBEntityAI(EntityAnimalTFC animal)
    {
        this.animal = animal;
        this.world = animal.world;
    }

    public EntityItem checkFood()
    {
        List<EntityItem> items = getItems();
        for (EntityItem item : items)
        {
            return item;
        }
        return null;
    }

    public boolean shouldExecute()
    {
        EntityItem closeFood = checkFood();
        if ((closeFood != null) && (this.animal.isFood(closeFood.getItem())))
        {
            if (this.animal.isReadyToMate())
            {
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

    public void execute(EntityAnimalTFC animal, EntityItem item)
    {
        if (animal.getNavigator().tryMoveToXYZ(item.posX, item.posY, item.posZ, 1.25F))
        {
            if (animal.getDistance(item) < 1.0F)
            {
                consumeFood(item);
                animal.setInLove(null);
            }
        }
    }

    public void consumeFood(EntityItem item)
    {
        ItemStack stack = item.getItem();
        stack.setCount(stack.getCount() - 1);
        if (stack.getCount() == 0) item.setDead();
        this.world.playSound(null, item.getPosition(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.AMBIENT, 1.0F, 1.0F);
    }

    List<EntityItem> getItems()
    {
        return world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(animal.posX - searchDistance, animal.posY - searchDistance, animal.posZ - searchDistance, animal.posX + searchDistance, animal.posY + searchDistance, animal.posZ + searchDistance));
    }
}