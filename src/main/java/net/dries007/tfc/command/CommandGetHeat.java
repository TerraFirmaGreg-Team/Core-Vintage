package net.dries007.tfc.command;

import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.IItemHeat;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class CommandGetHeat extends CommandBase {
    @Override
    @Nonnull
    public String getName() {
        return "get_heat";
    }

    @Override
    @Nonnull
    public String getUsage(ICommandSender sender) {
        return "tfc.command.get_heat.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        var entity = sender.getCommandSenderEntity();
        if (entity instanceof EntityPlayer player) {
            ItemStack stack = player.getHeldItemMainhand();
            IItemHeat cap = stack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
            if (cap == null)
                throw new WrongUsageException("tfc.command.heat.failed.missingcap");

            server.sendMessage(new TextComponentString(cap.getTemperature() + ""));
        } else {
            throw new WrongUsageException("tfc.command.heat.failed.usage_expected_player");
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
