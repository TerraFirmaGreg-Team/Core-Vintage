package net.dries007.tfc.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.chunk.Chunk;


import net.dries007.tfc.api.capability.chunkdata.ChunkDataTFC;

import org.jetbrains.annotations.NotNull;

public class CommandWorkChunk extends CommandBase {

    @Override
    @NotNull
    public String getName() {
        return "work";
    }

    @Override
    @NotNull
    public String getUsage(@NotNull ICommandSender iCommandSender) {
        return "tfc.command.work.usage";
    }

    @Override
    public void execute(MinecraftServer minecraftServer, ICommandSender iCommandSender, String[] strings) throws CommandException {
        if (strings.length != 2) throw new WrongUsageException("tfc.command.work.args");
        String action = strings[0];
        int work = parseInt(strings[1]);

        Entity entity = iCommandSender.getCommandSenderEntity();
        if (entity != null) {
            Chunk chunk = minecraftServer.getEntityWorld().getChunk(entity.getPosition());
            ChunkDataTFC data = ChunkDataTFC.get(chunk);
            if (action.equals("add")) {
                data.addWork(work);
            } else if (action.equals("set")) {
                if (work < 0)
                    work = 0;
                data.setWork(work);
            } else {
                throw new WrongUsageException("tfc.command.work.string");
            }
        } else {
            throw new WrongUsageException("tfc.command.work.nonentity");
        }

    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
