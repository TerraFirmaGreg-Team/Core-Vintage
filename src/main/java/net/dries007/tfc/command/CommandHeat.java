package net.dries007.tfc.command;

import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.heat.ICapabilityHeat;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class CommandHeat extends CommandBase {

  @Override
  @Nonnull
  public String getName() {
    return "heat";
  }

  @Override
  @Nonnull
  public String getUsage(ICommandSender sender) {
    return "tfc.command.heat.usage";
  }

  @Override
  public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
    if (args.length != 1) {throw new WrongUsageException("tfc.command.heat.failed");}
    double heat = parseDouble(args[0], 0);

    Entity entity = sender.getCommandSenderEntity();
    if (entity instanceof EntityPlayer player) {
      ItemStack stack = player.getHeldItemMainhand();
      ICapabilityHeat cap = stack.getCapability(CapabilityHeat.CAPABILITY, null);
      if (cap == null) {throw new WrongUsageException("tfc.command.heat.failed.missingcap");}
      cap.setTemperature((float) heat);
    } else {
      throw new WrongUsageException("tfc.command.heat.failed.usage_expected_player");
    }
  }

  @Override
  public int getRequiredPermissionLevel() {
    return 2;
  }
}
