package su.terrafirmagreg.core.mixin.tfc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import gregtech.api.items.toolitem.ToolHelper;
import gregtech.common.items.ToolItems;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.ILivestock;
import net.dries007.tfc.network.PacketSimpleMessage;
import net.dries007.tfc.objects.entity.animal.EntityAnimalMammal;
import net.dries007.tfc.objects.entity.animal.EntitySheepTFC;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.OreDictionaryHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.ParametersAreNonnullByDefault;

@Mixin(value = EntitySheepTFC.class, remap = false)
@ParametersAreNonnullByDefault
public abstract class EntitySheepTFCMixin extends EntityAnimalMammal implements IShearable, ILivestock {

  public EntitySheepTFCMixin(World worldIn) {
    super(worldIn);
  }

  /**
   * @author SpeeeDCraft
   * @reason Allow to use GT knife on TFC Sheep
   */
  @Inject(method = "processInteract", at = @At(value = "HEAD"), remap = false, cancellable = true)
  public void processInteract(EntityPlayer player, EnumHand hand, CallbackInfoReturnable<Boolean> cir) {
    ItemStack stack = player.getHeldItem(hand);
    if (stack.getItem() == ToolItems.KNIFE) {
      if (!world.isRemote) {
        if (isReadyForAnimalProduct()) {
          ToolHelper.damageItem(stack, player);
          ItemStack woolStack = new ItemStack(ItemsTFC.WOOL, 1);
          Helpers.spawnItemStack(player.world, new BlockPos(posX, posY, posZ), woolStack);
          playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
          setProductsCooldown();
        } else {
          TextComponentTranslation tooltip = getTooltip();
          if (tooltip != null) {
            TerraFirmaCraft.getNetwork()
                           .sendTo(new PacketSimpleMessage(PacketSimpleMessage.MessageCategory.ANIMAL, tooltip), (EntityPlayerMP) player);
          }
        }
      }
      cir.setReturnValue(true);
    } else if (OreDictionaryHelper.doesStackMatchOre(stack, "shears")) {
      if (!world.isRemote) {
        if (!isReadyForAnimalProduct()) {
          TextComponentTranslation tooltip = getTooltip();
          if (tooltip != null) {
            TerraFirmaCraft.getNetwork()
                           .sendTo(new PacketSimpleMessage(PacketSimpleMessage.MessageCategory.ANIMAL, tooltip), (EntityPlayerMP) player);
          }
        }
      }
      cir.setReturnValue(false);
    } else {
      cir.setReturnValue(super.processInteract(player, hand));
    }
  }
}
