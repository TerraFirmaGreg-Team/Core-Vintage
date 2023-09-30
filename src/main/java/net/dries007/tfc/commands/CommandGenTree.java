package net.dries007.tfc.commands;

import net.dries007.tfc.module.wood.tree.type.TreeType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.TemplateManager;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Random;

public class CommandGenTree extends CommandBase {
    private static final Random random = new Random();

    @Nonnull
    @Override
    public String getName() {
        return "maketree";
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return "tfc.command.gentree.useage";
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) throw new WrongUsageException("tfc.command.gentree.failed");

        var tree = TreeType.getTreeTypes().stream().filter(s -> Objects.equals(s.toString(), args[0])).findFirst().orElse(null);

        if (tree == null) throw new WrongUsageException("tfc.command.gentree.failed.woodtype", args[0]);

        if (sender.getCommandSenderEntity() == null) return;

        final World world = sender.getEntityWorld();
        final BlockPos center = new BlockPos(sender.getCommandSenderEntity());
        final TemplateManager manager = ((WorldServer) world).getStructureTemplateManager();

        if (!tree.makeTree(manager, world, center, random, false)) {
            sender.sendMessage(new TextComponentTranslation("tfc.command.gentree.failed.grow"));
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
