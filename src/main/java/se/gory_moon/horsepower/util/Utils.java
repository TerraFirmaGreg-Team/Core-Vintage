package se.gory_moon.horsepower.util;

import su.terrafirmagreg.api.util.GameUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

import se.gory_moon.horsepower.HorsePowerMod;
import se.gory_moon.horsepower.recipes.HPRecipes;

import java.security.InvalidParameterException;
import java.util.stream.Collectors;

public class Utils {


  public static int getBaseAmount(String in) {
    try {
      return Integer.parseInt(in.split("-")[0]);
    } catch (NumberFormatException e) {
      errorMessage("Base amount for chopping axe is malformed, (" + in + ")", false);
    }
    return 0;
  }

  public static void errorMessage(String message, boolean showDirectly) {
    if (GameUtils.isClient()) {
      if (FMLClientHandler.instance().getClientPlayerEntity() != null && showDirectly) {
        FMLClientHandler.instance()
                        .getClientPlayerEntity()
                        .sendMessage(new TextComponentString(TextFormatting.RED + message).setStyle(
                          new Style().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, Loader.instance()
                                                                                                      .getConfigDir() + "/horsepower.cfg"))
                                     .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                                   new TextComponentString("Change in in-game config or click to open the config file to fix this")))));
      } else {
        HPRecipes.ERRORS.add(message);
      }
    }
    HorsePowerMod.logger.warn(message);
  }

  public static int getChance(String in) {
    try {
      return Integer.parseInt(in.split("-")[1]);
    } catch (NumberFormatException e) {
      errorMessage("Chance for chopping axe is malformed, (" + in + ")", false);
    }
    return 0;
  }

  public static void sendSavedErrors() {
    if (GameUtils.isClient() && FMLClientHandler.instance().getClientPlayerEntity() != null && !HPRecipes.ERRORS.isEmpty()) {
      FMLClientHandler.instance().getClientPlayerEntity()
                      .sendMessage(new TextComponentString(TextFormatting.RED + "" + TextFormatting.BOLD + "HorsePower config errors"));
      FMLClientHandler.instance().getClientPlayerEntity()
                      .sendMessage(new TextComponentString(TextFormatting.RED + "" + TextFormatting.BOLD + "-----------------------------------------"));
      HPRecipes.ERRORS.forEach(s -> FMLClientHandler.instance().getClientPlayerEntity()
                                                    .sendMessage(new TextComponentString(TextFormatting.RED + s).setStyle(
                                                      new Style().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE,
                                                                                               Loader.instance().getConfigDir() + "/horsepower.cfg"))
                                                                 .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                                                                               new TextComponentString("Changed in in-game config or click to open the config file to fix this"))))));
      FMLClientHandler.instance().getClientPlayerEntity()
                      .sendMessage(new TextComponentString(TextFormatting.RED + "" + TextFormatting.BOLD + "-----------------------------------------"));
      HPRecipes.ERRORS.clear();
    }
  }

  public static Object parseItemStack(String item, boolean acceptOre, boolean acceptAmount) throws Exception {
    String[] data = item.split("\\$");
    NBTTagCompound nbt = data.length == 1 ? null : JsonToNBT.getTagFromJson(data[1]);
    if (data.length == 2) {
      item = item.substring(0, item.indexOf("$"));
    }

    data = item.split("@");
    int amount = !acceptAmount || data.length == 1 ? 1 : Integer.parseInt(data[1]);
    if (data.length == 2) {
      item = item.substring(0, item.indexOf("@"));
    }

    data = item.split(":");
    int meta = data.length == 2 ? 0 : "*".equals(data[2]) ? OreDictionary.WILDCARD_VALUE : Integer.parseInt(data[2]);

    if (item.startsWith("ore:")) {
      if (!acceptOre) {
        throw new InvalidParameterException();
      }
      if (amount > 1) {
        return OreDictionary.getOres(item.substring(4)).stream().map(stack ->
                                                                     {
                                                                       ItemStack stack1 = stack.copy();
                                                                       stack1.setCount(amount);
                                                                       return stack1;
                                                                     }).collect(Collectors.toList());
      } else {
        return OreDictionary.getOres(item.substring(4));
      }
    } else if (item.startsWith("fluid:")) {
      Fluid fluid = FluidRegistry.getFluid(item.substring(6));
      return new FluidStack(fluid, amount, nbt);
    } else {
      NBTTagCompound compound = new NBTTagCompound();
      compound.setString("id", data[0] + ":" + data[1]);
      compound.setByte("Count", (byte) amount);
      compound.setShort("Damage", (short) meta);
      if (nbt != null) {
        compound.setTag("tag", nbt);
      }
      return new ItemStack(compound);
    }
  }

}
