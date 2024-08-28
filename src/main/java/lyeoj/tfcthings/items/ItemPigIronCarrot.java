package lyeoj.tfcthings.items;

import su.terrafirmagreg.modules.animal.api.type.IAnimal;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalPig;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.metal.api.types.type.MetalTypes;
import su.terrafirmagreg.modules.metal.init.BlocksMetal;
import su.terrafirmagreg.modules.metal.objects.entity.EntityMetalPigvil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;


import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfc.types.DefaultMetals;

import org.jetbrains.annotations.NotNull;

public class ItemPigIronCarrot extends ItemTFC implements TFCThingsConfigurableItem {

    private final Metal metal;

    public ItemPigIronCarrot(Metal metal) {
        this.metal = metal;
    }

    @Override
    public @NotNull Size getSize(@NotNull ItemStack itemStack) {
        return Size.SMALL;
    }

    @Override
    public @NotNull Weight getWeight(@NotNull ItemStack itemStack) {
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
            if (piggy.getGender()
                    .equals(IAnimal.Gender.MALE) && piggy.getAge() == IAnimal.Age.ADULT && piggy.getFamiliarity() >= requiredFamiliarity) {
                player.world.playSound(player, piggy.getPosition(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                if (!player.world.isRemote) {
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                    }
                    if (Math.random() < ConfigTFCThings.Misc.PIGVIL.convertChance) {
                        EntityMetalPigvil pigvil = new EntityMetalPigvil(entity.world);
                        pigvil.copyLocationAndAnglesFrom(piggy);
                        entity.world.spawnEntity(pigvil);
                        pigvil.spawnExplosionParticle();
                        piggy.setDead();
                    }
                }
            }
        } else if (entity instanceof EntityMetalPigvil pigvil) {
            if (pigvil.getAnvil() == BlocksMetal.PIGVIL.get(MetalTypes.STEEL) && metal == TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL)) {
                player.world.playSound(player, pigvil.getPosition(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                if (!player.world.isRemote) {
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                    }
                    if (Math.random() < ConfigTFCThings.Misc.PIGVIL.convertChance) {
                        pigvil.setAnvil(BlocksMetal.PIGVIL.get(MetalTypes.BLACK_STEEL));
                        pigvil.spawnExplosionParticle();
                    }
                }
            } else if (pigvil.getAnvil() == BlocksMetal.PIGVIL.get(MetalTypes.BLACK_STEEL) && (metal == TFCRegistries.METALS.getValue(DefaultMetals.BLUE_STEEL) ||
                    metal == TFCRegistries.METALS.getValue(DefaultMetals.RED_STEEL))) {
                player.world.playSound(player, pigvil.getPosition(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                if (!player.world.isRemote) {
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                    }
                    if (Math.random() < ConfigTFCThings.Misc.PIGVIL.convertChance) {
                        if (metal == TFCRegistries.METALS.getValue(DefaultMetals.BLUE_STEEL)) {
                            pigvil.setAnvil(BlocksMetal.PIGVIL.get(MetalTypes.BLUE_STEEL));
                        } else {
                            pigvil.setAnvil(BlocksMetal.PIGVIL.get(MetalTypes.RED_STEEL));
                        }
                        pigvil.spawnExplosionParticle();
                    }
                }
            } else if ((pigvil.getAnvil() == BlocksMetal.PIGVIL.get(MetalTypes.RED_STEEL) && metal == TFCRegistries.METALS.getValue(DefaultMetals.BLUE_STEEL)) ||
                    (pigvil.getAnvil() == BlocksMetal.PIGVIL.get(MetalTypes.BLUE_STEEL) && metal == TFCRegistries.METALS.getValue(DefaultMetals.RED_STEEL))) {
                player.world.playSound(player, pigvil.getPosition(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                if (!player.world.isRemote) {
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                    }
                    if (Math.random() < ConfigTFCThings.Misc.PIGVIL.convertChance) {
                        pigvil.setAnvil(BlocksMetal.PIGVIL.get(MetalTypes.PIG_IRON));
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
