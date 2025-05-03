package su.terrafirmagreg.modules.core.feature.ambiental.capability;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.capabilities.food.spi.Nutrient;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.feature.ambiental.spi.AmbientalRegistry;
import su.terrafirmagreg.modules.core.feature.ambiental.spi.modifier.ModifierBlock;
import su.terrafirmagreg.modules.core.feature.ambiental.spi.modifier.ModifierEnvironmental;
import su.terrafirmagreg.modules.core.feature.ambiental.spi.modifier.ModifierEquipment;
import su.terrafirmagreg.modules.core.feature.ambiental.spi.modifier.ModifierItem;
import su.terrafirmagreg.modules.core.feature.ambiental.spi.modifier.ModifierTile;
import su.terrafirmagreg.modules.core.feature.ambiental.spi.provider.IAmbientalProviderBlock;
import su.terrafirmagreg.modules.core.feature.ambiental.spi.provider.IAmbientalProviderEnvironmental;
import su.terrafirmagreg.modules.core.feature.ambiental.spi.provider.IAmbientalProviderEquipment;
import su.terrafirmagreg.modules.core.feature.ambiental.spi.provider.IAmbientalProviderItem;
import su.terrafirmagreg.modules.core.feature.ambiental.spi.provider.IAmbientalProviderTile;
import su.terrafirmagreg.modules.core.init.EffectsCore;
import su.terrafirmagreg.modules.core.init.FluidsCore;
import su.terrafirmagreg.modules.device.object.tile.TileCrucible;
import su.terrafirmagreg.modules.device.object.tile.TileElectricForge;
import su.terrafirmagreg.modules.device.object.tile.TileFridge;
import su.terrafirmagreg.modules.device.object.tile.TileInductionCrucible;
import su.terrafirmagreg.modules.food.api.IFoodStatsTFC;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import net.dries007.tfc.objects.te.TELamp;
import net.dries007.tfc.objects.te.TEOven;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.Optional;

public final class CapabilityAmbiental {

  public static final ResourceLocation KEY = ModUtils.resource("ambiental_capability");

  @CapabilityInject(ICapabilityAmbiental.class)
  public static final Capability<ICapabilityAmbiental> CAPABILITY = ModUtils.getNull();

  public static void register() {

    CapabilityManager.INSTANCE.register(ICapabilityAmbiental.class, new CapabilityStorageAmbiental(), CapabilityProviderAmbiental::new);
  }

  public static ICapabilityAmbiental get(Entity entity) {
    return entity.getCapability(CAPABILITY, null);
  }

  public static class Handler {

    public static final AmbientalRegistry<IAmbientalProviderBlock> BLOCK = new AmbientalRegistry<>();
    public static final AmbientalRegistry<IAmbientalProviderItem> ITEM = new AmbientalRegistry<>();
    public static final AmbientalRegistry<IAmbientalProviderTile> TILE = new AmbientalRegistry<>();
    public static final AmbientalRegistry<IAmbientalProviderEquipment> EQUIPMENT = new AmbientalRegistry<>();
    public static final AmbientalRegistry<IAmbientalProviderEnvironmental> ENVIRONMENT = new AmbientalRegistry<>();


    public static void init() {
      BLOCK.register((player, pos, state) ->
        ModifierBlock.defined("torch", 3f, 0f)
          .filter(mod -> state.getBlock() == Blocks.TORCH)
      );

      BLOCK.register((player, pos, state) ->
        ModifierBlock.defined("fire", 3f, 0f)
          .filter(mod -> state.getBlock() == Blocks.FIRE)
      );

      BLOCK.register((player, pos, state) ->
        ModifierBlock.defined("lava", 3f, 0f)
          .filter(mod -> state.getBlock() == Blocks.LAVA)
      );

      BLOCK.register((player, pos, state) ->
        ModifierBlock.defined("flowing_lava", 3f, 0f)
          .filter(mod -> state.getBlock() == Blocks.FLOWING_LAVA)
      );

      BLOCK.register((player, pos, state) ->
        ModifierBlock.defined("snow_layer", -1.5f, 0.2f)
          .filter(mod -> state.getBlock() == Blocks.SNOW_LAYER)
      );

      BLOCK.register((player, pos, state) ->
        ModifierBlock.defined("snow", -0.5f, 0.2f)
          .filter(mod -> state.getBlock() == Blocks.SNOW &&
                         player.world.getLightFor(EnumSkyBlock.SKY, pos) == 15
          )
      );

      // GTCEu
      BLOCK.register((player, pos, state) ->
        ModifierBlock.defined("wire_coil", 3f, 3f)
          .filter(mod -> state.getBlock() == MetaBlocks.WIRE_COIL)
      );

      ITEM.register(Handler::handleHeatItem);

      // TFC-Tech
      TILE.register(Handler::handleElectricForge); // Тигель
      TILE.register(Handler::handleInductionCrucible); // Кузня
      TILE.register(Handler::handleFridge); // Холодос

      // Firmalife
      TILE.register(Handler::handleClayOven); // Oven

      // TFC
      TILE.register(Handler::handleLamps); // Лампа

      EQUIPMENT.register((player, stack) ->
        ModifierEquipment.defined("nanoHelmet", 0f, 0f)
          .filter(mod -> stack.getItem() == MetaItems.NANO_HELMET.getStackForm().getItem())
      );

      EQUIPMENT.register((player, stack) ->
        ModifierEquipment.defined("nanoChestplate", 0f, 0f)
          .filter(mod -> stack.getItem() == MetaItems.NANO_CHESTPLATE.getStackForm().getItem()
                         || stack.getItem() == MetaItems.NANO_CHESTPLATE_ADVANCED.getStackForm().getItem())
      );

      EQUIPMENT.register((player, stack) ->
        ModifierEquipment.defined("nanoLeggings", 0f, 0f)
          .filter(mod -> stack.getItem() == MetaItems.NANO_LEGGINGS.getStackForm().getItem())
      );

      EQUIPMENT.register((player, stack) ->
        ModifierEquipment.defined("nanoBoots", 0f, 0f)
          .filter(mod -> stack.getItem() == MetaItems.NANO_BOOTS.getStackForm().getItem())
      );

      EQUIPMENT.register((player, stack) ->
        ModifierEquipment.defined("quantumHelmet", 0f, 0f)
          .filter(mod -> stack.getItem() == MetaItems.QUANTUM_HELMET.getStackForm().getItem())
      );

      EQUIPMENT.register((player, stack) ->
        ModifierEquipment.defined("quantumChestplate", 0f, 0f)
          .filter(mod -> stack.getItem() == MetaItems.QUANTUM_CHESTPLATE.getStackForm().getItem()
                         || stack.getItem() == MetaItems.QUANTUM_CHESTPLATE_ADVANCED.getStackForm().getItem())
      );

      EQUIPMENT.register((player, stack) ->
        ModifierEquipment.defined("quantumLeggings", 0f, 0f)
          .filter(mod -> stack.getItem() == MetaItems.QUANTUM_LEGGINGS.getStackForm().getItem())
      );

      EQUIPMENT.register((player, stack) ->
        ModifierEquipment.defined("quantumBoots", 0f, 0f)
          .filter(mod -> stack.getItem() == MetaItems.QUANTUM_BOOTS.getStackForm().getItem())
      );

      EQUIPMENT.register(Handler::handleArmor);

      ENVIRONMENT.register(Handler::handleGeneralTemperature);
      ENVIRONMENT.register(Handler::handleTimeOfDay);
      ENVIRONMENT.register(Handler::handleShade);
      ENVIRONMENT.register(Handler::handleCozy);
      ENVIRONMENT.register(Handler::handleThirst);
      ENVIRONMENT.register(Handler::handleFood);
      ENVIRONMENT.register(Handler::handleDiet);
      ENVIRONMENT.register(Handler::handleFire);
      ENVIRONMENT.register(Handler::handleWater);
      ENVIRONMENT.register(Handler::handleRain);
      ENVIRONMENT.register(Handler::handleSprinting);
      ENVIRONMENT.register(Handler::handleUnderground);
      ENVIRONMENT.register(Handler::handlePotionEffects);
    }


    public static Optional<ModifierTile> handleClayOven(EntityPlayer player, TileEntity tile) {
      if (tile instanceof TEOven oven) {

        boolean isBurning = false;

        try {
          isBurning = (boolean) FieldUtils.readField(oven, "isBurning", true);
        } catch (Exception e) {
          e.printStackTrace();
        }

        float change = 0.0f;
        float potency = 1.0f;

        if (isBurning) {
          change = 8f;
          potency = 4f;

          if (ModifierTile.hasProtection(player)) {
            change = 1.0F;
          }
        }

        return ModifierTile.defined("firmalife_oven", change, potency);
      } else {
        return ModifierTile.none();
      }
    }

    public static Optional<ModifierTile> handleLamps(EntityPlayer player, TileEntity tile) {
      if (tile instanceof TELamp lamp) {

        if (ModifierEnvironmental.getEnvironmentTemperature(player) < CapabilityProviderAmbiental.AVERAGE) {
          float change = (lamp.isPowered() && lamp.getFuel() > 0) ? 1f : 0f;
          float potency = 0f;
          return ModifierTile.defined("lamp", change, potency);
        }
      }
      return ModifierTile.none();
    }

    public static Optional<ModifierTile> handleElectricForge(EntityPlayer player, TileEntity tile) {
      if (tile instanceof TileElectricForge electricForge) {
        float temp = electricForge.getField(TileCrucible.FIELD_TEMPERATURE);
        float change = temp / 100f;
        float potency = temp / 350f;
        if (ModifierTile.hasProtection(player)) {
          change = 1.0F;
        }
        return ModifierTile.defined("electric_forge", change, potency);
      } else {
        return ModifierTile.none();
      }
    }

    public static Optional<ModifierTile> handleInductionCrucible(EntityPlayer player, TileEntity tile) {
      if (tile instanceof TileInductionCrucible inductionCrucible) {
        float temp = inductionCrucible.getField(TileCrucible.FIELD_TEMPERATURE);
        float change = temp / 100f;
        float potency = temp / 350f;
        if (ModifierTile.hasProtection(player)) {
          change = 1.0F;
        }
        return ModifierTile.defined("induction_crucible", change, potency);
      } else {
        return ModifierTile.none();
      }
    }

    public static Optional<ModifierTile> handleFridge(EntityPlayer player, TileEntity tile) {
      if (tile instanceof TileFridge fridge) {

        float change = 0f;
        float potency = 0f;

        if (fridge.isOpen()) {
          change = -10f;
          potency = -0.7f;
        }

        return ModifierTile.defined("fridge", change, potency);
      } else {
        return ModifierTile.none();
      }
    }

    private static Optional<ModifierEquipment> handleArmor(EntityPlayer player, ItemStack itemStack) {
      if (itemStack.getItem() instanceof ItemArmor thing) {
        if (thing.armorType == EntityEquipmentSlot.HEAD) {
          if (player.world.getLight(player.getPosition()) > 14) {
            float envTemp = ModifierEnvironmental.getEnvironmentTemperature(player);
            if (envTemp > CapabilityProviderAmbiental.AVERAGE + 3) {
              float diff = envTemp - CapabilityProviderAmbiental.AVERAGE;
              return ModifierEquipment.defined("helmet", -diff / 3f, -0.5f);
            } else {
              return ModifierEquipment.defined("armor", 3f, -0.25f);
            }
          }
        } else {
          float envTemp = ModifierEnvironmental.getEnvironmentTemperature(player);
          if (envTemp > CapabilityProviderAmbiental.AVERAGE + 3) {
            return ModifierEquipment.defined("armor", 3f, -0.25f);
          }
        }
      }
      return ModifierEquipment.none();
    }


    public static Optional<ModifierEnvironmental> handleGeneralTemperature(EntityPlayer player) {
      return ModifierEnvironmental.defined("environment",
        ModifierEnvironmental.getEnvironmentTemperature(player),
        ModifierEnvironmental.getEnvironmentHumidity(player));
    }

    public static Optional<ModifierEnvironmental> handleFire(EntityPlayer player) {
      return ModifierEnvironmental.defined("on_fire", 4f, 4f)
        .filter(mod -> player.isBurning());
    }

    public static Optional<ModifierEnvironmental> handleRain(EntityPlayer player) {
      if (player.world.isRaining()) {
        if (ModifierEnvironmental.getSkylight(player) < 15) {
          return ModifierEnvironmental.defined("weather", -2f, 0.1f);
        } else {
          return ModifierEnvironmental.defined("weather", -4f, 0.3f);
        }
      } else {
        return ModifierEnvironmental.none();
      }
    }

    public static Optional<ModifierEnvironmental> handleUnderground(EntityPlayer player) {
      if (ModifierEnvironmental.getSkylight(player) < 2) {
        return ModifierEnvironmental.defined("underground", -6f, 0.2f);
      } else {
        return ModifierEnvironmental.none();
      }
    }

    public static Optional<ModifierEnvironmental> handleSprinting(EntityPlayer player) {
      if (player.isSprinting()) {
        return ModifierEnvironmental.defined("sprint", 2f, 0.3f);
      } else {
        return ModifierEnvironmental.none();
      }
    }

    public static Optional<ModifierEnvironmental> handleTimeOfDay(EntityPlayer player) {
      int dayTicks = (int) (player.world.getWorldTime() % 24000);
      if (dayTicks < 6000) {
        return ModifierEnvironmental.defined("morning", 2f, 0);
      } else if (dayTicks < 12000) {
        return ModifierEnvironmental.defined("afternoon", 4f, 0);
      } else if (dayTicks < 18000) {
        return ModifierEnvironmental.defined("evening", 2f, 0);
      } else {
        return ModifierEnvironmental.defined("night", 1f, 0);
      }
    }

    public static Optional<ModifierEnvironmental> handlePotionEffects(EntityPlayer player) {
      if (player.isPotionActive(EffectsCore.HYPOTHERMIA)) {
        return ModifierEnvironmental.defined("hypothermia_effect", -10F, 0);
      }
      if (player.isPotionActive(EffectsCore.HYPERTHERMIA)) {
        return ModifierEnvironmental.defined("hyperthermia_effect", 10F, 0);
      }
      return ModifierEnvironmental.none();
    }

    public static Optional<ModifierEnvironmental> handleWater(EntityPlayer player) {
      if (player.isInWater()) {
        BlockPos pos = player.getPosition();
        IBlockState state = player.world.getBlockState(pos);
        var block = state.getBlock();
        if (block == FluidsCore.HOT_WATER.get().getBlock()) {
          return ModifierEnvironmental.defined("in_hot_water", 5f, 6f);
        } else if (block == Blocks.LAVA) {
          return ModifierEnvironmental.defined("in_lava", 10f, 5f);
        } else if (block == FluidsCore.SALT_WATER.get().getBlock() && player.world.getBiome(pos).getTempCategory() == Biome.TempCategory.OCEAN) {
          return ModifierEnvironmental.defined("in_ocean_water", -8f, 6f);
        } else {
          return ModifierEnvironmental.defined("in_water", -5f, 6f);
        }
      } else {
        return ModifierEnvironmental.none();
      }
    }

    private static Optional<ModifierEnvironmental> handleShade(EntityPlayer player) {
      int light = Math.max(12, ModifierEnvironmental.getSkylight(player));
      float temp = ModifierEnvironmental.getEnvironmentTemperatureWithTimeOfDay(player);
      float avg = CapabilityProviderAmbiental.AVERAGE;
      return ModifierEnvironmental.defined("shade", -Math.abs(avg - temp) * 0.6f, 0f)
        .filter(mod -> light < 15 && temp > avg);
    }


    private static Optional<ModifierEnvironmental> handleCozy(EntityPlayer player) {
      int skyLight = Math.max(11, ModifierEnvironmental.getSkylight(player));
      int blockLight = ModifierEnvironmental.getBlockLight(player);
      float temp = ModifierEnvironmental.getEnvironmentTemperature(player);
      float avg = CapabilityProviderAmbiental.AVERAGE;
      float coverage = (1f - (float) skyLight / 15f) + 0.4f;
      return ModifierEnvironmental.defined("cozy", Math.abs(avg - 2 - temp) * coverage, 0f)
        .filter(mod -> skyLight < 14 && blockLight > 4 && temp < avg - 2 && player.getPosition().getY() > 130);
    }

    private static Optional<ModifierEnvironmental> handleThirst(EntityPlayer player) {
      if (player.getFoodStats() instanceof IFoodStatsTFC stats) {
        if (ModifierEnvironmental.getEnvironmentTemperatureWithTimeOfDay(player) > CapabilityProviderAmbiental.AVERAGE + 3 && stats.getThirst() > 80f) {
          return ModifierEnvironmental.defined("well_hidrated", -2.5f, 0f);
        }
      }
      return ModifierEnvironmental.none();
    }

    private static Optional<ModifierEnvironmental> handleFood(EntityPlayer player) {
      if (ModifierEnvironmental.getEnvironmentTemperatureWithTimeOfDay(player) < CapabilityProviderAmbiental.AVERAGE - 3
          && player.getFoodStats().getFoodLevel() > 14) {
        return ModifierEnvironmental.defined("well_fed", 2.5f, 0f);
      }
      return ModifierEnvironmental.none();
    }

    private static Optional<ModifierEnvironmental> handleDiet(EntityPlayer player) {
      if (player.getFoodStats() instanceof IFoodStatsTFC stats) {
        if (ModifierEnvironmental.getEnvironmentTemperatureWithTimeOfDay(player) < CapabilityProviderAmbiental.COOL_THRESHOLD) {
          float grainLevel = stats.getNutrition().getNutrient(Nutrient.GRAIN);
          float meatLevel = stats.getNutrition().getNutrient(Nutrient.PROTEIN);
          return ModifierEnvironmental.defined("nutrients", 4f * grainLevel * meatLevel, 0f);
        }
        if (ModifierEnvironmental.getEnvironmentTemperatureWithTimeOfDay(player) > CapabilityProviderAmbiental.HOT_THRESHOLD) {
          float fruitLevel = stats.getNutrition().getNutrient(Nutrient.FRUIT);
          float veggieLevel = stats.getNutrition().getNutrient(Nutrient.VEGETABLES);
          return ModifierEnvironmental.defined("nutrients", -4f * fruitLevel * veggieLevel, 0f);
        }
      }
      return ModifierEnvironmental.none();
    }


    private static Optional<ModifierItem> handleHeatItem(EntityPlayer player, ItemStack stack) {
      if (CapabilityHeat.has(stack)) {
        var cap = CapabilityHeat.get(stack);
        float change = cap.getTemperature() / 500;
        float potency = 0f;
        return ModifierItem.defined("heat_item", change, potency * stack.getCount());
      }
      return ModifierItem.none();
    }
  }
}
