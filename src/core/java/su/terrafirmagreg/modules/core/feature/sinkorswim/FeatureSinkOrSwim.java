package su.terrafirmagreg.modules.core.feature.sinkorswim;

import su.terrafirmagreg.framework.manager.feature.spi.FeatureBase;
import su.terrafirmagreg.modules.core.ConfigCore;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import baubles.api.BaublesApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FeatureSinkOrSwim extends FeatureBase {

  @SubscribeEvent
  public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event) {

    var entityLiving = event.getEntityLiving();

    if (!(entityLiving instanceof EntityPlayer player)) {
      return;
    }

    World world = player.getEntityWorld();
    BlockPos pos = player.getPosition().down(1);
    BlockPos pos1 = player.getPosition().down(2);
    IBlockState state = world.getBlockState(pos);
    Block block = state.getBlock();

    if (!world.isRemote) {
      return;
    }
    if (player.isCreative()) {
      return;
    }

    if (!(player.isInWater() || player.isInLava())) {
      return;
    }

    if (!checkPotions(player)) {
      return;
    }

    if (!checkEnchants(player)) {
      return;
    }

    if (!checkBaubles(player)) {
      return;
    }

    if (checkArmorSlot(player)) {
      if (block.isReplaceable(world, pos)) {
        player.motionY -= 0.03D;
      }
    }

    if (checkBiome(world, pos)) {
      if (block.isReplaceable(world, pos1) || block.isReplaceable(world, pos)) {
        player.motionY -= 0.03D;
      }
    }
  }

  private static boolean checkBiome(World world, BlockPos pos) {
    return Arrays.asList(ConfigCore.FEATURE.SINK_OR_SWIM.biomeBlackList).contains(world.getBiome(pos).getBiomeName()) ||
           Arrays.asList(ConfigCore.FEATURE.SINK_OR_SWIM.biomeBlackList).contains("All");
  }

  private static boolean checkArmorSlot(EntityPlayer player) {
    var armorInventory = player.inventory.armorInventory;
    for (ItemStack stack : armorInventory) {
      if (stack.isEmpty()) {
        return true;
      }

      String string = Objects.requireNonNull(stack.getItem().getRegistryName()).toString();

      return Arrays.asList(ConfigCore.FEATURE.SINK_OR_SWIM.armorWhiteList).contains(string);
    }

    return true;
  }

  private static boolean checkEnchants(EntityPlayer player) {
    List<ItemStack> armorStacks = new ArrayList<>();
    List<String> enchantsString = new ArrayList<>();

    var armorInventory = player.inventory.armorInventory;
    for (int slot = 0; slot <= 3; slot++) {
      armorStacks.add(armorInventory.get(slot));
    }

    for (ItemStack armorStack : armorStacks) {
      List<Enchantment> enchants = new ArrayList<>(EnchantmentHelper.getEnchantments(armorStack).keySet());

      for (Enchantment enchantment : enchants) {
        enchantsString.add(Objects.requireNonNull(enchantment.getRegistryName()).toString());
      }
    }

    return Collections.disjoint(enchantsString, Arrays.asList(ConfigCore.FEATURE.SINK_OR_SWIM.enchantWhiteList));
  }

  private static boolean checkPotions(EntityPlayer player) {
    List<String> potionEffects = new ArrayList<>();

    for (PotionEffect potionEffect : player.getActivePotionEffects()) {
      String effectName = potionEffect.getEffectName().substring(7);
      potionEffects.add(effectName);
    }

    return Collections.disjoint(potionEffects, Arrays.asList(ConfigCore.FEATURE.SINK_OR_SWIM.potionWhiteList));
  }

  private static boolean checkBaubles(EntityPlayer player) {
    if (!Loader.isModLoaded("baubles")) {
      return true;
    }
    return Collections.disjoint(baublesInv(player), Arrays.asList(ConfigCore.FEATURE.SINK_OR_SWIM.baublesWhiteList));
  }

  @Optional.Method(modid = "baubles")
  private static List<String> baublesInv(EntityPlayer player) {
    List<String> listBaubles = new ArrayList<>();

    for (int slot = 0; slot < 6; slot++) {
      ItemStack itemBauble = BaublesApi.getBaublesHandler(player).getStackInSlot(slot);
      if (!itemBauble.isEmpty()) {
        String nameBauble = Objects.requireNonNull(itemBauble.getItem().getRegistryName()).toString();
        listBaubles.add(nameBauble);
      }
    }
    return listBaubles;
  }

  @Override
  public boolean isEnabled() {
    return ConfigCore.FEATURE.SINK_OR_SWIM.enable;
  }

}
