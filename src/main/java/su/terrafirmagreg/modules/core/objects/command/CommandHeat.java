package su.terrafirmagreg.modules.core.objects.command;

import su.terrafirmagreg.api.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.api.spi.command.BaseCommand;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class CommandHeat extends BaseCommand {

    @Override
    public String getName() {
        return "heat";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return ModUtils.localize("command", "heat.usage");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) throw new WrongUsageException(ModUtils.localize("command", "heat.failed"));
        double heat = parseDouble(args[0], 0);

        Entity entity = sender.getCommandSenderEntity();
        if (entity instanceof EntityPlayer player) {
            ItemStack stack = player.getHeldItemMainhand();
            var cap = CapabilityHeat.get(stack);
            if (cap == null)
                throw new WrongUsageException(ModUtils.localize("command", "heat.failed.missing.cap"));
            cap.setTemperature((float) heat);
        } else {
            throw new WrongUsageException(ModUtils.localize("command", "heat.failed.usage.expected.player"));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
