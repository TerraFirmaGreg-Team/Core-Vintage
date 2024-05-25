package su.terrafirmagreg.modules.wood.objects.items;

import su.terrafirmagreg.api.spi.item.BaseItem;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.item.IWoodItem;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariant;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodCart;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodSupplyCart;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

@Getter
public class ItemWoodSupplyCart extends BaseItem implements IWoodItem {

    private final WoodItemVariant variant;
    private final WoodType type;

    public ItemWoodSupplyCart(WoodItemVariant variant, WoodType type) {
        this.type = type;
        this.variant = variant;

        getSettings()
                .size(Size.HUGE)
                .weight(Weight.VERY_HEAVY)
                .maxCount(1)
                .addOreDict(variant)
                .addOreDict(variant, type);
    }

    @NotNull
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        Vec3d vec3d = new Vec3d(playerIn.posX, playerIn.posY + playerIn.getEyeHeight(), playerIn.posZ);
        Vec3d lookVec = playerIn.getLookVec();
        Vec3d vec3d1 = new Vec3d(lookVec.x * 5.0 + vec3d.x, lookVec.y * 5.0 + vec3d.y, lookVec.z * 5.0 + vec3d.z);

        RayTraceResult result = worldIn.rayTraceBlocks(vec3d, vec3d1, false);
        if (result != null) {
            if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                if (!worldIn.isRemote) {
                    EntityWoodCart cart = this.newCart(worldIn);
                    cart.setPosition(result.hitVec.x, result.hitVec.y, result.hitVec.z);
                    cart.setWood(type);
                    cart.rotationYaw = (playerIn.rotationYaw + 180) % 360;
                    worldIn.spawnEntity(cart);

                    if (!playerIn.capabilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }
                    playerIn.addStat(StatList.getObjectUseStats(this));
                }
                return new ActionResult<>(EnumActionResult.PASS, itemstack);
            }
        }
        return new ActionResult<>(EnumActionResult.FAIL, itemstack);
    }

    public EntityWoodCart newCart(World worldIn) {
        return new EntityWoodSupplyCart(worldIn);
    }

    @Override
    public IItemColor getItemColor() {
        return (s, i) -> this.getType().getColor();
    }
}
