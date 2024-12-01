package su.terrafirmagreg.modules.core.object.command;

import su.terrafirmagreg.api.base.command.BaseCommand;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.flora.api.types.variant.block.IFloraBlock;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;

public class CommandStripWorld extends BaseCommand {

  @Override
  public String getName() {
    return "stripworld";
  }

  @Override
  public String getUsage(ICommandSender sender) {
    return ModUtils.localize("command", "stripworld.usage");
  }

  @Override
  public void execute(MinecraftServer server, ICommandSender sender, String[] args)
    throws CommandException {
    if (args.length != 1) {
      throw new WrongUsageException(ModUtils.localize("command", "stripworld.failed"));
    }
    int radius = parseInt(args[0], 1, 250);

    if (sender.getCommandSenderEntity() == null) {
      return;
    }

    final World world = sender.getEntityWorld();
    final BlockPos center = new BlockPos(sender.getCommandSenderEntity());

    final IBlockState fluidReplacement = Blocks.GLASS.getDefaultState();
    final IBlockState terrainReplacement = Blocks.AIR.getDefaultState();

    for (int x = -radius; x < radius; x++) {
      for (int z = -radius; z < radius; z++) {
        for (int y = 255 - center.getY(); y > -center.getY(); y--) {
          final BlockPos pos = center.add(x, y, z);
          final Block current = world.getBlockState(pos).getBlock();
          if (current instanceof BlockFluidBase || current instanceof BlockDynamicLiquid || current instanceof BlockStaticLiquid) {
            world.setBlockState(pos, fluidReplacement, 2);
          } else if (current instanceof IRockBlock || current instanceof ISoilBlock || current instanceof IFloraBlock) {
            world.setBlockState(pos, terrainReplacement, 2);
          }
        }
      }
    }

    sender.sendMessage(
      new TextComponentTranslation(ModUtils.localize("command", "stripworld.done")));
  }

  @Override
  public int getRequiredPermissionLevel() {
    return 2;
  }
}
