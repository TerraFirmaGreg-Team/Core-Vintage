package com.eerussianguy.firmalife.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import com.eerussianguy.firmalife.FirmaLife;
import com.eerussianguy.firmalife.network.PacketDrawBoundingBox;
import com.eerussianguy.firmalife.network.PacketSpawnVanillaParticle;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;
import net.dries007.tfc.util.calendar.ICalendar;

import java.util.Random;

public class HelpersFL {

  public HelpersFL() {

  }

  public static void sendVanillaParticleToClient(EnumParticleTypes particle, World worldIn, double x, double y, double z, double speedX,
                                                 double speedY, double speedZ) {
    final int range = 80;
    PacketSpawnVanillaParticle packet = new PacketSpawnVanillaParticle(particle, x, y, z, speedX, speedY, speedZ);
    NetworkRegistry.TargetPoint point = new NetworkRegistry.TargetPoint(worldIn.provider.getDimension(), x, y, z, range);
    FirmaLife.getNetwork().sendToAllAround(packet, point);
  }

  public static void sendBoundingBoxPacket(World worldIn, BlockPos min, BlockPos max, float red, float green, float blue, boolean isBlockShape) {
    final int range = 80;
    PacketDrawBoundingBox packet = new PacketDrawBoundingBox(min, max, red, green, blue, isBlockShape);
    NetworkRegistry.TargetPoint point = new NetworkRegistry.TargetPoint(worldIn.provider.getDimension(), min.getX(), min.getY(), min.getZ(),
                                                                        range);
    FirmaLife.getNetwork().sendToAllAround(packet, point);
  }

  public static ItemStack updateFoodFuzzed(ItemStack oldStack, ItemStack newStack) {
    ItemStack output = CapabilityFood.updateFoodFromPrevious(oldStack, newStack);
    IFood cap = output.getCapability(CapabilityFood.CAPABILITY, null);
    if (cap != null && !cap.isRotten()) {
      cap.setCreationDate(cap.getCreationDate() - (cap.getCreationDate() % ICalendar.HOURS_IN_DAY));
    }
    return output;
  }

  public static int nextSign(Random rand) {
    return rand.nextBoolean() ? 1 : -1;
  }

}
