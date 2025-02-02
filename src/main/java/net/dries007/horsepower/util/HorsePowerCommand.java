package net.dries007.horsepower.util;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.IClientCommand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.horsepower.HPEventHandler;
import net.dries007.horsepower.recipes.HPRecipes;

@SideOnly(Side.CLIENT)
public class HorsePowerCommand extends CommandBase implements IClientCommand {

  @Override
  public String getName() {
    return "horsepower";
  }

  @Override
  public String getUsage(ICommandSender sender) {
    return "commands.horsepower.usage";
  }

  @Override
  public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
    if (args.length == 1) {
      if ("entity".equals(args[0])) {
        if (sender instanceof EntityPlayerSP) {
          RayTraceResult result = Minecraft.getMinecraft().objectMouseOver;

          if (result != null && result.typeOfHit == RayTraceResult.Type.ENTITY) {
            Entity entity = result.entityHit;
            sender.sendMessage(new TextComponentTranslation("commands.horsepower.entity.has", entity.getClass().getName()));
            try {
              StringSelection selection = new StringSelection(entity.getClass().getName());
              Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
            } catch (Exception ignored) {
            }
          } else {sender.sendMessage(new TextComponentTranslation("commands.horsepower.entity.no"));}
        } else {throw new CommandException("commands.horsepower.entity.invalid");}
        return;
      }
      if ("reload".equals(args[0])) {
        sender.sendMessage(new TextComponentTranslation("commands.horsepower.reload").setStyle(new Style().setColor(TextFormatting.YELLOW).setBold(true)));
        HPEventHandler.reloadConfig();
        boolean hasErrors = HPRecipes.ERRORS.size() > 0;
        Utils.sendSavedErrors();
        if (hasErrors) {
          sender.sendMessage(new TextComponentTranslation("commands.horsepower.reload.error").setStyle(new Style().setColor(TextFormatting.DARK_RED)
            .setBold(true)));
        } else {
          sender.sendMessage(new TextComponentTranslation("commands.horsepower.reload.noerror").setStyle(new Style().setColor(TextFormatting.GREEN)
            .setBold(true)));
        }
        return;
      }
    }
    throw new WrongUsageException(getUsage(sender));
  }

  @Override
  public int getRequiredPermissionLevel() {
    return 2;
  }

  @Override
  public List<String> getAliases() {
    return Lists.newArrayList("hp");
  }

  @Override
  public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
    return args.length == 1 ? getListOfStringsMatchingLastWord(args, "entity", "reload") : Collections.emptyList();
  }

  @Override
  public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
    return false;
  }
}
