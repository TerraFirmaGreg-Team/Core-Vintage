package su.terrafirmagreg.modules.wood.object.command;

import su.terrafirmagreg.api.base.command.BaseCommand;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.TemplateManager;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import static su.terrafirmagreg.api.util.MathUtils.RNG;

public class CommandGenTree extends BaseCommand {

  @Override
  public String getName() {
    return "maketree";
  }

  @Override
  public String getUsage(ICommandSender sender) {
    return ModUtils.localize("command", "maketree.useage");
  }

  @Override
  public void execute(MinecraftServer server, ICommandSender sender, String[] args)
    throws CommandException {
    if (args.length != 1) {
      throw new WrongUsageException(ModUtils.localize("command", "maketree.failed"));
    }

    WoodType type = WoodType.getTypes().stream()
                            .filter(t -> t.getName().equalsIgnoreCase(args[0]))
                            .findFirst()
                            .orElse(null);

    if (type == null) {
      throw new WrongUsageException(ModUtils.localize("command", "maketree.failed.woodtype"), args[0]);
    }

    if (sender.getCommandSenderEntity() == null) {
      return;
    }

    final World world = sender.getEntityWorld();
    final BlockPos center = new BlockPos(sender.getCommandSenderEntity());
    final TemplateManager manager = ((WorldServer) world).getStructureTemplateManager();

    if (!type.makeTree(manager, world, center, RNG, false)) {
      sender.sendMessage(new TextComponentTranslation(ModUtils.localize("command", "maketree.failed.grow")));
    }
  }

  @Override
  public int getRequiredPermissionLevel() {
    return 2;
  }

  @Override
  public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
    return getListOfStringsMatchingLastWord(args, WoodType.getTypes().stream().map(WoodType::getName).toArray(String[]::new));
  }
}
