package su.terrafirmagreg.core.modules.hotornot;

import gregtech.api.items.toolitem.ToolHelper;
import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.IItemHeat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import su.terrafirmagreg.core.TFGConfig;
import su.terrafirmagreg.core.modules.ambiental.TFCAmbientalGuiRenderer;
import su.terrafirmagreg.core.modules.ambiental.capability.TemperatureCapability;
import su.terrafirmagreg.core.modules.gregtech.items.tools.TFGToolItems;
import su.terrafirmagreg.core.modules.hotornot.config.HotLists;

import static su.terrafirmagreg.Tags.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class CommonEventHandler {

	@SubscribeEvent
	public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(MOD_ID)) {
			ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
			TemperatureCapability.AVERAGE = TFGConfig.GENERAL.averageTemperature;
			TemperatureCapability.HOT_THRESHOLD = TFGConfig.GENERAL.hotThreshold;
			TemperatureCapability.COOL_THRESHOLD = TFGConfig.GENERAL.coolThreshold;
			TemperatureCapability.BURN_THRESHOLD = TFGConfig.GENERAL.burnThreshold;
			TemperatureCapability.FREEZE_THRESHOLD = TFGConfig.GENERAL.freezeThreshold;
			TemperatureCapability.NANO_QUARK_ARMOR_TEMP = TFGConfig.GENERAL.nanoOrQuarkTemp;
			TFCAmbientalGuiRenderer.enableOverlay = TFGConfig.CLIENT.ENABLE_OVERLAY;
		}
	}

	@SubscribeEvent
	public static void onTick(TickEvent.PlayerTickEvent event) {
		World world = event.player.world;
		EntityPlayer entityPlayer = event.player;

		if (event.phase == TickEvent.Phase.START && !world.isRemote) {
			if (!entityPlayer.isBurning() && !entityPlayer.isCreative() && entityPlayer.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
				IItemHandler handler = entityPlayer.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
				for (int i = 0; i < handler.getSlots(); i++) {
					ItemStack stack = handler.getStackInSlot(i);

					// Fluids
					if (!stack.isEmpty() && stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) && !HotLists.isRemoved(stack)) {
						IFluidHandlerItem fluidHandlerItem = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
						FluidStack fluidStack = fluidHandlerItem.drain(1000, false);
						if (fluidStack != null) {
							for (FluidEffect effect : FluidEffect.values()) {
								if (effect.isValid.test(fluidStack)) {
									ItemStack offHand = entityPlayer.getHeldItemOffhand();
									if (offHand.getItem().equals(TFGToolItems.TONGS.get())) {
										if ((world.getTotalWorldTime() % TFGConfig.General.DURABILITY_DECREASING == 0)) {
											ToolHelper.damageItem(offHand, entityPlayer);
											return;
										}
									} else if (world.getTotalWorldTime() % 20 == 0) {
										effect.interactPlayer.accept(entityPlayer);

										if (TFGConfig.General.YEET) {
											entityPlayer.inventory.deleteStack(stack);
											entityPlayer.dropItem(stack, false, true);
											return;
										}
									}
								}

							}
						}
					}
					// Items
					else if (TFGConfig.General.HOT_ITEMS && !stack.isEmpty() && !HotLists.isRemoved(stack)) {
						// TFC Heat Capability
						if (stack.hasCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null)) {
							IItemHeat heatHandlerItem = stack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
							if (heatHandlerItem != null && heatHandlerItem.getTemperature() >= TFGConfig.General.HOT_ITEM) {
								ItemStack offHand = entityPlayer.getHeldItemOffhand();
								if (offHand.getItem().equals(TFGToolItems.TONGS.get())) {
									if ((world.getTotalWorldTime() % TFGConfig.General.DURABILITY_DECREASING == 0)) {
										ToolHelper.damageItem(offHand, entityPlayer);
										return;
									}
								} else if (world.getTotalWorldTime() % 10 == 0) {
									entityPlayer.setFire(1);

									if (TFGConfig.General.YEET) {
										// Только для фикса TFC
										entityPlayer.closeScreen();

										entityPlayer.inventory.deleteStack(stack);
										entityPlayer.dropItem(stack, false, true);
										return;
									}
								}
							}
						}
						// Items from config
						// Hot
						else if (HotLists.isHot(stack)) {
							ItemStack offHand = entityPlayer.getHeldItemOffhand();
							if (offHand.getItem().equals(TFGToolItems.TONGS.get())) {
								if ((world.getTotalWorldTime() % TFGConfig.General.DURABILITY_DECREASING == 0)) {
									ToolHelper.damageItem(offHand, entityPlayer);
									return;
								}
							} else if (world.getTotalWorldTime() % 10 == 0) {
								entityPlayer.setFire(1);
								if (TFGConfig.General.YEET) {
									entityPlayer.inventory.deleteStack(stack);
									entityPlayer.dropItem(stack, false, true);
									return;
								}
							}
						}
						// Cold
						else if (HotLists.isCold(stack)) {
							ItemStack offHand = entityPlayer.getHeldItemOffhand();
							if (offHand.getItem().equals(TFGToolItems.TONGS.get())) {
								if ((world.getTotalWorldTime() % TFGConfig.General.DURABILITY_DECREASING == 0)) {
									ToolHelper.damageItem(offHand, entityPlayer);
									return;
								}
							} else if (world.getTotalWorldTime() % 10 == 0) {
								entityPlayer.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 21, 1));
								entityPlayer.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 21, 1));
							}
						}
						// Gaseous
						else if (HotLists.isGaseous(stack)) {
							if (world.getTotalWorldTime() % 10 == 0) {
								entityPlayer.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 21, 1));
							}
						}

					}
				}
			}
		}
	}
}
