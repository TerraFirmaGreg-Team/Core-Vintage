package com.buuz135.hotornot;

import com.buuz135.hotornot.config.HotConfig;
import com.buuz135.hotornot.config.HotLists;
import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.IItemHeat;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        if (HotConfig.TOOLTIP && !stack.isEmpty() && !HotLists.isRemoved(stack))
        {
            if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null))
            {
                IFluidHandlerItem fluidHandlerItem = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
                FluidStack fluidStack = fluidHandlerItem.drain(1000, false);
                if (fluidStack != null)
                {
                    for (FluidEffect effect : FluidEffect.values())
                    {
                        if (effect.isValid.test(fluidStack))
                        {
                            event.getToolTip().add(effect.color + new TextComponentTranslation(effect.tooltip).getUnformattedText());
                        }
                    }
                }
            }
            else if (HotLists.isHot(stack))
            {
                event.getToolTip().add(FluidEffect.HOT.color + new TextComponentTranslation(FluidEffect.HOT.tooltip).getUnformattedText());
            }
            else if (HotLists.isCold(stack))
            {
                event.getToolTip().add(FluidEffect.COLD.color + new TextComponentTranslation(FluidEffect.COLD.tooltip).getUnformattedText());
            }
            else if (HotLists.isGaseous(stack))
            {
                event.getToolTip().add(FluidEffect.GAS.color + new TextComponentTranslation(FluidEffect.GAS.tooltip).getUnformattedText());
            }
            else if (Loader.isModLoaded("tfc"))
            {
                if (stack.hasCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null))
                {
                    IItemHeat heat = stack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
                    if (heat.getTemperature() >= HotConfig.HOT_ITEM)
                    {
                        event.getToolTip().add(FluidEffect.HOT.color + new TextComponentTranslation(FluidEffect.HOT.tooltip).getUnformattedText());
                    }
                }
            }
        }
    }
}
