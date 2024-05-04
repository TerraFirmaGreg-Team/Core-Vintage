package su.terrafirmagreg.modules.device.objects.items;

import su.terrafirmagreg.api.spi.item.BaseItem;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.device.objects.entity.EntitySlingStone;
import su.terrafirmagreg.modules.device.objects.entity.EntitySlingStoneMetal;
import su.terrafirmagreg.modules.device.objects.entity.EntitySlingStoneMetalLight;
import su.terrafirmagreg.modules.device.objects.entity.EntityUnknownProjectile;
import su.terrafirmagreg.modules.rock.objects.items.ItemRockLoose;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.metal.ItemIngot;

import org.jetbrains.annotations.Nullable;

public class ItemSling extends BaseItem {

    public ItemSling() {
        this.setMaxDamage(64);
        this.setNoRepair();
        this.setMaxStackSize(1);
        this.addPropertyOverride(new ResourceLocation("spinning"), new IItemPropertyGetter() {

            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if (entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack && entityIn.getItemInUseMaxCount() > 0) {
                    int maxPower = ConfigTFCThings.Items.SLING.maxPower;
                    int chargeSpeed = ConfigTFCThings.Items.SLING.chargeSpeed;
                    float powerRatio = Math.min((float) entityIn.getItemInUseMaxCount() / (float) chargeSpeed, maxPower) / (float) maxPower;
                    return (float) MathHelper.floor(((entityIn.getItemInUseMaxCount() * powerRatio) % 8) + 1);
                }
                return 0.0F;
            }
        });

    }

    @Override
    public String getName() {
        return "device/sling/normal";
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, "tool");
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    @Override
    public Size getSize(ItemStack itemStack) {
        return Size.NORMAL;
    }

    @Override
    public Weight getWeight(ItemStack itemStack) {
        return Weight.MEDIUM;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        ItemStack itemstack = playerIn.getHeldItem(handIn);
        boolean flag = !this.findAmmo(playerIn).isEmpty();

        if (!playerIn.isCreative() && !flag) {
            return flag ? new ActionResult(EnumActionResult.PASS, itemstack) : new ActionResult(EnumActionResult.FAIL, itemstack);
        } else {
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        if (entityLiving instanceof EntityPlayer entityplayer) {

            boolean flag = entityplayer.isCreative();
            ItemStack itemStack = this.findAmmo(entityplayer);

            int maxPower = ConfigTFCThings.Items.SLING.maxPower;
            int chargeSpeed = ConfigTFCThings.Items.SLING.chargeSpeed;

            int power = Math.min((this.getMaxItemUseDuration(stack) - timeLeft) / chargeSpeed, maxPower);
            float velocity = 1.6F * (power / (float) maxPower);
            float inaccuracy = 0.5F * (8.0F - power);

            if (!itemStack.isEmpty() && !flag) {

                if (!worldIn.isRemote) {
                    shoot(worldIn, entityLiving, power, velocity, inaccuracy, itemStack);
                }
                worldIn.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_SNOWBALL_THROW,
                        SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

                itemStack.shrink(1);
                if (itemStack.isEmpty()) {
                    entityplayer.inventory.deleteStack(itemStack);
                }
                stack.damageItem(1, entityplayer);

            } else if (flag) {
                if (!worldIn.isRemote) {
                    shoot(worldIn, entityLiving, power, velocity, inaccuracy, itemStack);
                }
                worldIn.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_SNOWBALL_THROW,
                        SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            }
        }
    }

    private void shoot(World worldIn, EntityLivingBase entityLiving, int power, float velocity, float inaccuracy, ItemStack itemStack) {

        EntitySlingStone entitySlingStone;
        float adjustedVelocity = velocity;

        if (itemStack.getItem() instanceof ItemIngot) {
            entitySlingStone = new EntityUnknownProjectile(worldIn, entityLiving, power);
        } else if (itemStack.getItem() instanceof ItemSlingAmmo ammo) {
            switch (ammo.getType()) {
                case 0:
                    entitySlingStone = new EntitySlingStoneMetal(worldIn, entityLiving, power + 5);
                    break;
                case 1:
                    entitySlingStone = new EntitySlingStoneMetal(worldIn, entityLiving, power + 2);
                    for (int i = 0; i < 4; i++) {
                        EntitySlingStoneMetal bonusStone = new EntitySlingStoneMetal(worldIn, entityLiving, power);
                        bonusStone.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0f, velocity * 0.75f,
                                inaccuracy + 2.5f);
                        worldIn.spawnEntity(bonusStone);
                    }
                    break;
                case 2:
                    entitySlingStone = new EntitySlingStoneMetalLight(worldIn, entityLiving, power + 3);
                    adjustedVelocity *= 1.2F;
                    break;
                default:
                    entitySlingStone = new EntitySlingStoneMetal(worldIn, entityLiving, power + 2);
                    entitySlingStone.setFire(10);
            }
        } else {
            entitySlingStone = new EntitySlingStone(worldIn, entityLiving, power);
        }
        entitySlingStone.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0F, adjustedVelocity, inaccuracy);
        worldIn.spawnEntity(entitySlingStone);
    }

    private ItemStack findAmmo(EntityPlayer player) {
        if (this.isStone(player.getHeldItem(EnumHand.OFF_HAND))) {
            return player.getHeldItem(EnumHand.OFF_HAND);
        } else if (this.isStone(player.getHeldItem(EnumHand.MAIN_HAND))) {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        } else {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (this.isStone(itemstack)) {
                    return itemstack;
                }
            }

            return ItemStack.EMPTY;
        }
    }

    protected boolean isStone(ItemStack stack) {
        if (stack.getItem() instanceof ItemRockLoose) {
            return true;
        } else if (stack.getItem() instanceof ItemIngot ingot) {
            return ingot.getMetal(stack) == Metal.UNKNOWN;
        }
        return false;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

}
