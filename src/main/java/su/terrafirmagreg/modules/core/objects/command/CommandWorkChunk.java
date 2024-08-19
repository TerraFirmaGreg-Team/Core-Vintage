package su.terrafirmagreg.modules.core.objects.command;

import su.terrafirmagreg.api.base.command.BaseCommand;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.chunk.Chunk;


import net.dries007.tfc.api.capability.chunkdata.ChunkData;

import org.jetbrains.annotations.NotNull;

public class CommandWorkChunk extends BaseCommand {

    @Override
    public String getName() {
        return "workchunk";
    }

    @Override
    public String getUsage(@NotNull ICommandSender iCommandSender) {
        return ModUtils.localize("command", "workchunk.usage");
    }

    @Override
    public void execute(MinecraftServer minecraftServer, ICommandSender iCommandSender, String[] strings) throws CommandException {
        if (strings.length != 2) throw new WrongUsageException(ModUtils.localize("command", "workchunk.args"));
        String action = strings[0];
        int work = parseInt(strings[1]);

        Entity entity = iCommandSender.getCommandSenderEntity();
        if (entity != null) {
            Chunk chunk = minecraftServer.getEntityWorld().getChunk(entity.getPosition());
            ChunkData data = ChunkData.get(chunk);
            if (action.equals("add")) {
                data.addWork(work);
            } else if (action.equals("set")) {
                if (work < 0)
                    work = 0;
                data.setWork(work);
            } else {
                throw new WrongUsageException(ModUtils.localize("command", "workchunk.string"));
            }
        } else {
            throw new WrongUsageException(ModUtils.localize("command", "workchunk.nonentity"));
        }

    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
