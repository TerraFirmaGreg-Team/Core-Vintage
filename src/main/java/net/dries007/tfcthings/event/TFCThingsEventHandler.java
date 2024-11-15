package net.dries007.tfcthings.event;

import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.modules.animal.init.ItemsAnimal;
import su.terrafirmagreg.modules.animal.object.entity.livestock.EntityAnimalSheep;
import su.terrafirmagreg.modules.core.capabilities.sharpness.CapabilitySharpness;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfcthings.entity.projectile.EntityThrownRopeJavelin;
import net.dries007.tfcthings.items.ItemRopeJavelin;
import net.dries007.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;
import net.dries007.tfc.objects.entity.projectile.EntityThrownWeapon;
import net.dries007.tfc.util.OreDictionaryHelper;

import static su.terrafirmagreg.api.data.Reference.MODID_TFCTHINGS;
import static su.terrafirmagreg.api.data.Properties.BoolProp.PLACED;

@Mod.EventBusSubscriber(modid = MODID_TFCTHINGS)
public class TFCThingsEventHandler {

  @SubscribeEvent
  public static void onBlockBreak(BlockEvent.BreakEvent event) {
    if (CapabilitySharpness.has(event.getPlayer().getHeldItemMainhand())) {
      var capability = CapabilitySharpness.get(event.getPlayer().getHeldItemMainhand());
      if (capability != null && capability.getCharges() > 0) {
        capability.removeCharge();
        ItemStack stack = event.getPlayer().getHeldItemMainhand();
        if (capability.getCharges() > 256) {
          if (Math.random() < 0.2 && stack.getItemDamage() > 0) {
            stack.setItemDamage(stack.getItemDamage() - 1);
          }
        } else if (capability.getCharges() > 64 && stack.getItemDamage() > 0) {
          if (Math.random() < 0.1) {
            stack.setItemDamage(stack.getItemDamage() - 1);
          }
        }
      }
    }
  }

  @SubscribeEvent
  public static void onLivingAttack(LivingDamageEvent event) {
    if (event.getSource() instanceof EntityDamageSource source) {
      if (source.getTrueSource() instanceof EntityPlayer player) {
        ItemStack weapon;
        if (source instanceof EntityDamageSourceIndirect && source.getImmediateSource() instanceof EntityThrownWeapon) {
          weapon = ((EntityThrownWeapon) source.getImmediateSource()).getWeapon();
        } else if (source instanceof EntityDamageSourceIndirect && (source.getImmediateSource() instanceof EntityThrownWeapon)) {
          weapon = ((EntityThrownRopeJavelin) source.getImmediateSource()).getWeapon();
        } else {
          weapon = player.getHeldItemMainhand();
        }
        if (CapabilitySharpness.has(weapon)) {
          var capability = CapabilitySharpness.get(weapon);
          if (capability != null && event.getAmount() > 2.0f) {
            if (capability.getCharges() > 256) {
              event.setAmount(event.getAmount() + (ConfigTFCThings.Items.WHETSTONE.damageBoost * 3));
              capability.removeCharge();
            } else if (capability.getCharges() > 64) {
              event.setAmount(event.getAmount() + (ConfigTFCThings.Items.WHETSTONE.damageBoost * 2));
              capability.removeCharge();
            } else if (capability.getCharges() > 0) {
              event.setAmount(event.getAmount() + ConfigTFCThings.Items.WHETSTONE.damageBoost);
              capability.removeCharge();
            }
            if (capability.getCharges() > 256) {
              if (Math.random() < 0.2 && weapon.getItemDamage() > 0) {
                weapon.setItemDamage(weapon.getItemDamage() - 1);
              }
            } else if (capability.getCharges() > 64) {
              if (Math.random() < 0.1 && weapon.getItemDamage() > 0) {
                weapon.setItemDamage(weapon.getItemDamage() - 1);
              }
            }
          }
        }
      }
    }
  }

  @SubscribeEvent
  public static void modifyBreakSpeed(PlayerEvent.BreakSpeed event) {
    if (CapabilitySharpness.has(event.getEntityPlayer().getHeldItemMainhand())) {
      var capability = CapabilitySharpness.get(event.getEntityPlayer().getHeldItemMainhand());
      if (capability != null) {
        if (shouldBoostSpeed(event.getEntityPlayer().getHeldItemMainhand(), event.getState())) {
          if (event.getState().getBlock() instanceof BlockLogTFC && !event.getState()
                                                                          .getValue(PLACED)) {
            return;
          }
          if (capability.getCharges() > 256) {
            event.setNewSpeed(event.getNewSpeed() + ConfigTFCThings.Items.WHETSTONE.bonusSpeed + 4);
          } else if (capability.getCharges() > 64) {
            event.setNewSpeed(event.getNewSpeed() + ConfigTFCThings.Items.WHETSTONE.bonusSpeed + 2);
          } else if (capability.getCharges() > 0) {
            event.setNewSpeed(event.getNewSpeed() + ConfigTFCThings.Items.WHETSTONE.bonusSpeed);
          }
        }
      }
    }
  }

  private static boolean shouldBoostSpeed(ItemStack stack, IBlockState state) {
    if (stack.getItem().canHarvestBlock(state)) {
      return true;
    }
    for (String type : stack.getItem().getToolClasses(stack)) {
      if (state.getBlock().isToolEffective(type, state)) {
        return true;
      }
    }
    return false;
  }

  @SubscribeEvent
  public static void onItemToss(ItemTossEvent event) {
    if (event.getEntityItem().getItem().getItem() instanceof ItemRopeJavelin javelin) {
      javelin.retractJavelin(event.getEntityItem().getItem(), event.getEntity().getEntityWorld());
    }
  }

  //    @SubscribeEvent
  //    public static void onPlayerInteractBlock(PlayerInteractEvent.RightClickBlock event) {
  //        if (event.getItemStack().getItem() instanceof ItemRopeJavelin) {
  //            if (event.getWorld().getBlockState(event.getPos()).getBlock() instanceof BlockToolRack) {
  //                ((ItemRopeJavelin) event.getItemStack().getItem()).retractJavelin(event.getItemStack(), event.getWorld());
  //            }
  //        }
  //    }

  @SubscribeEvent
  public static void onPlayerInteractEntity(PlayerInteractEvent.EntityInteract event) {
    if (event.getTarget() instanceof EntityAnimalSheep sheep) {
      if ((OreDictionaryHelper.doesStackMatchOre(event.getItemStack(), "shears") ||
           OreDictionaryHelper.doesStackMatchOre(event.getItemStack(), "knife"))
          && sheep.hasWool() && sheep.getFamiliarity() == 1.0F) {
        if (!sheep.world.isRemote) {
          ItemStack woolStack = new ItemStack(ItemsAnimal.WOOL, 1);
          StackUtils.spawnItemStack(sheep.world, new BlockPos(sheep.posX, sheep.posY, sheep.posZ), woolStack);
        }
      }
    }
    //        if(event.getTarget() instanceof EntityCowTFC) {
    //            EntityCowTFC cow = (EntityCowTFC) event.getTarget();
    //            if(cow.getFamiliarity() == 1.0F && cow.isReadyForAnimalProduct()) {
    //                event.setCanceled(true);
    //                ItemStack itemstack = event.getEntityPlayer().getHeldItem(event.getHand());
    //                FluidActionResult fillResult = FluidUtil.tryFillContainer(itemstack, FluidUtil.getFluidHandler(new ItemStack(Items.MILK_BUCKET)), 1000, event.getEntityPlayer(), false);
    //                if (!fillResult.isSuccess()) {
    //                    return;
    //                }
    //                event.getEntityPlayer().playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
    //                event.getEntityPlayer().setHeldItem(event.getHand(), FluidUtil.tryFillContainerAndStow(itemstack, FluidUtil.getFluidHandler(new ItemStack(Items.MILK_BUCKET)), new PlayerInvWrapper(event.getEntityPlayer().inventory), 1000, (EntityPlayer)null, true).getResult());
    //            }
    //        }
  }

}
