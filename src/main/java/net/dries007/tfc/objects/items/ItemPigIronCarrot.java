package net.dries007.tfc.objects.items;

import su.terrafirmagreg.modules.animal.api.type.IAnimal;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalPig;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.types.DefaultMetals;
import net.dries007.tfc.objects.entity.living.EntityPigvil;
import net.dries007.tfcthings.init.TFCThingsBlocks;
import net.dries007.tfcthings.main.ConfigTFCThings;

import javax.annotation.Nonnull;

public class ItemPigIronCarrot extends ItemTFC implements TFCThingsConfigurableItem {

  private final Metal metal;

  public ItemPigIronCarrot(Metal metal) {
    this.metal = metal;
  }

  @Nonnull
  @Override
  public Size getSize(@Nonnull ItemStack itemStack) {
    return Size.SMALL;
  }

  @Nonnull
  @Override
  public Weight getWeight(@Nonnull ItemStack itemStack) {
    return Weight.MEDIUM;
  }

  public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity, EnumHand hand) {
    //Debug utility to raise an animal's familiarity
//        if(entity instanceof EntityAnimalTFC) {
//            EntityAnimalTFC animal = (EntityAnimalTFC)entity;
//            animal.setFamiliarity(animal.getFamiliarity() + 0.1f);
//        }
    if (metal == TFCRegistries.METALS.getValue(DefaultMetals.PIG_IRON) && entity instanceof EntityAnimalPig piggy) {
      float requiredFamiliarity = (float) ConfigTFCThings.Misc.PIGVIL.familiarityLevel;
      if (piggy.getGender().equals(IAnimal.Gender.MALE) && piggy.getAge() == IAnimal.Age.ADULT && piggy.getFamiliarity() >= requiredFamiliarity) {
        player.world.playSound(player, piggy.getPosition(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.NEUTRAL, 1.0f, 1.0f);
        if (!player.world.isRemote) {
          if (!player.isCreative()) {
            itemstack.shrink(1);
          }
          if (Math.random() < ConfigTFCThings.Misc.PIGVIL.convertChance) {
            EntityPigvil pigvil = new EntityPigvil(entity.world);
            pigvil.copyLocationAndAnglesFrom(piggy);
            entity.world.spawnEntity(pigvil);
            pigvil.spawnExplosionParticle();
            piggy.setDead();
          }
        }
      }
    } else if (entity instanceof EntityPigvil pigvil) {
      if (pigvil.getAnvil() == TFCThingsBlocks.PIGVIL_BLOCK && metal == TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL)) {
        player.world.playSound(player, pigvil.getPosition(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.NEUTRAL, 1.0f, 1.0f);
        if (!player.world.isRemote) {
          if (!player.isCreative()) {
            itemstack.shrink(1);
          }
          if (Math.random() < ConfigTFCThings.Misc.PIGVIL.convertChance) {
            pigvil.setAnvil(TFCThingsBlocks.PIGVIL_BLOCK_BLACK);
            pigvil.spawnExplosionParticle();
          }
        }
      } else if (pigvil.getAnvil() == TFCThingsBlocks.PIGVIL_BLOCK_BLACK && (metal == TFCRegistries.METALS.getValue(DefaultMetals.BLUE_STEEL)
                                                                             || metal == TFCRegistries.METALS.getValue(DefaultMetals.RED_STEEL))) {
        player.world.playSound(player, pigvil.getPosition(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.NEUTRAL, 1.0f, 1.0f);
        if (!player.world.isRemote) {
          if (!player.isCreative()) {
            itemstack.shrink(1);
          }
          if (Math.random() < ConfigTFCThings.Misc.PIGVIL.convertChance) {
            if (metal == TFCRegistries.METALS.getValue(DefaultMetals.BLUE_STEEL)) {
              pigvil.setAnvil(TFCThingsBlocks.PIGVIL_BLOCK_BLUE);
            } else {
              pigvil.setAnvil(TFCThingsBlocks.PIGVIL_BLOCK_RED);
            }
            pigvil.spawnExplosionParticle();
          }
        }
      } else if ((pigvil.getAnvil() == TFCThingsBlocks.PIGVIL_BLOCK_RED && metal == TFCRegistries.METALS.getValue(DefaultMetals.BLUE_STEEL)) ||
                 (pigvil.getAnvil() == TFCThingsBlocks.PIGVIL_BLOCK_BLUE && metal == TFCRegistries.METALS.getValue(DefaultMetals.RED_STEEL))) {
        player.world.playSound(player, pigvil.getPosition(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.NEUTRAL, 1.0f, 1.0f);
        if (!player.world.isRemote) {
          if (!player.isCreative()) {
            itemstack.shrink(1);
          }
          if (Math.random() < ConfigTFCThings.Misc.PIGVIL.convertChance) {
            pigvil.setAnvil(TFCThingsBlocks.PIGVIL_BLOCK_PURPLE);
            pigvil.spawnExplosionParticle();
          }
        }
      }
    }
    return super.itemInteractionForEntity(itemstack, player, entity, hand);
  }

  @Override
  public boolean isEnabled() {
    return ConfigTFCThings.Items.MASTER_ITEM_LIST.enablePigvil;
  }
}
