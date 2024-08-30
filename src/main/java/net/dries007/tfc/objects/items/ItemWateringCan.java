package net.dries007.tfc.objects.items;

import su.terrafirmagreg.api.util.MathsUtils;
import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;


import com.eerussianguy.firmalife.util.IWaterable;
import mcp.MethodsReturnNonnullByDefault;


import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;


import net.dries007.tfc.objects.blocks.stone.BlockFarmlandTFC;

import static su.terrafirmagreg.data.Properties.WET;
import static su.terrafirmagreg.data.lib.MathConstants.RNG;

@MethodsReturnNonnullByDefault

public class ItemWateringCan extends ItemMisc {

    public ItemWateringCan() {
        super(Size.SMALL, Weight.VERY_HEAVY);
        setMaxDamage(48);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        ItemStack itemstack = player.getHeldItem(handIn);
        if (!worldIn.isRemote && player.isWet()) {
            int dmg = itemstack.getItemDamage();
            int maxDmg = itemstack.getMaxDamage();
            if (dmg < maxDmg) {
                itemstack.damageItem(-1 * (maxDmg - dmg), player);
            }
            return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
        }

        player.setActiveHand(handIn);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entity, int countLeft) {
        World world = entity.getEntityWorld();
        BlockPos pos = entity.getPosition();
        if (world.isRemote) return;
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -2; z <= 2; z++) {
                    BlockPos addPos = pos.add(x, y, z);
                    var tile = TileUtils.getTile(world, addPos, TileEntity.class);
                    if (tile instanceof IWaterable) {
                        ((IWaterable) tile).setWater(2);
                        IBlockState state = world.getBlockState(addPos);
                        world.setBlockState(addPos, state.withProperty(WET, true));
                    } else {
                        IBlockState farmland = world.getBlockState(addPos);
                        if (farmland.getBlock() instanceof BlockFarmlandTFC) {
                            int current = farmland.getValue(BlockFarmlandTFC.MOISTURE);
                            if (current < BlockFarmlandTFC.MAX_MOISTURE)
                                world.setBlockState(addPos, farmland.withProperty(BlockFarmlandTFC.MOISTURE, current + 1));
                        }
                    }
                }
            }
        }
        stack.damageItem(1, entity);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase entity, int countLeft) {
        World world = entity.getEntityWorld();
        if (world.isRemote && entity instanceof EntityPlayer && countLeft % 2 == 0) {
            RayTraceResult result = MathsUtils.rayTrace(world, (EntityPlayer) entity, false);
            if (result == null || result.typeOfHit != RayTraceResult.Type.BLOCK) return;
            BlockPos pos = result.getBlockPos();
            double x = pos.getX() + RNG.nextFloat();
            double y = pos.getY() + 1.5f;
            double z = pos.getZ() + RNG.nextFloat();
            world.spawnParticle(EnumParticleTypes.WATER_SPLASH, x, y, z, RNG.nextFloat(), -0.2f, RNG.nextFloat());
        }
    }
}
