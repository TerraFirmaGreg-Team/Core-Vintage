package su.terrafirmagreg.modules.core.command;

import su.terrafirmagreg.api.base.command.spi.BaseCommand;
import su.terrafirmagreg.api.util.CommandUtils;
import su.terrafirmagreg.api.util.CommandUtils.ExecuteType;
import su.terrafirmagreg.api.util.CommandUtils.Level;
import su.terrafirmagreg.api.util.TranslatorUtils;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;
import su.terrafirmagreg.modules.core.capabilities.food.spi.Nutrient;
import su.terrafirmagreg.modules.core.capabilities.food.spi.NutritionStats;
import su.terrafirmagreg.modules.core.feature.playerdata.capability.CapabilityPlayerData;
import su.terrafirmagreg.modules.core.feature.playerdata.spi.Skill;
import su.terrafirmagreg.modules.core.feature.playerdata.spi.SkillType;
import su.terrafirmagreg.modules.food.api.FoodStatsTFC;
import su.terrafirmagreg.modules.food.api.IFoodStatsTFC;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.FoodStats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;

import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CommandPlayer extends BaseCommand {

  public CommandPlayer() {
    super(Settings.of());

    getSettings()
      .registryKey("player")
      .level(Level.OP_OR_SP);

  }


  @Override
  public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
    if (sender.getCommandSenderEntity() instanceof EntityPlayer player) {
      if (args.length < 1) {
        CommandUtils.throwWrongUsage(sender, this, "first_argument");
      }
      ExecuteType executeType = ExecuteType.parse(args[0]);
      if (args.length < 2) {
        CommandUtils.throwWrongUsage(sender, this, "second_argument", args[0]);
      }
      switch (args[1]) {
        case "nutrition":
          executeNutrient(sender, player, executeType);
          break;
        case "skill":
          executeSkill(sender, player, args, executeType);
          break;
        case "food":
          executeFood(sender, player, args, executeType);
          break;
        case "saturation":
          executeSaturation(sender, player, args, executeType);
          break;
        case "water":
          executeWater(sender, player, args, executeType);
          break;
        default:
          CommandUtils.throwWrongUsage(sender, this, "second_argument", args[0]);
      }
    } else {
      CommandUtils.throwWrongUsage(sender, this, "needs_player");
    }
  }

  private void executeNutrient(ICommandSender sender, EntityPlayer player, ExecuteType executeType) throws CommandException {
    NutritionStats nutritionStats = ((IFoodStatsTFC) player.getFoodStats()).getNutrition();
    switch (executeType) {
      case RESET:
        nutritionStats.reset();
        CommandUtils.sendLocalizedChatMessage(sender, this, "reset.nutrients");
        break;
      case GET:
        CommandUtils.sendLocalizedChatMessage(sender, this, "get.nutrients", String.format("%.2f", nutritionStats.getAverageNutrition()));
        for (Nutrient nutrient : Nutrient.values()) {
          CommandUtils.sendLocalizedChatMessage(sender, this, "get.nutrients_nutrient", new TextComponentTranslation(TranslatorUtils.getEnumName(nutrient)), String.format("%.2f", nutritionStats.getNutrient(nutrient)));
        }
        FoodData lastRecord = nutritionStats.getMostRecentRecord();
        if (lastRecord != null) {
          float[] nutrients = lastRecord.getNutrients();

          CommandUtils.sendLocalizedChatMessage(sender, this, "get.nutrients_last_eaten",
            lastRecord.getHunger(),
            String.format("%.2f", lastRecord.getSaturation()),
            String.format("%.2f", lastRecord.getDecayModifier()));

          for (Nutrient nutrient : Nutrient.values()) {
            CommandUtils.sendLocalizedChatMessage(sender, this, "get.nutrients_last_eaten_nutrient",
              new TextComponentTranslation(TranslatorUtils.getEnumName(nutrient)),
              String.format("%.2f", nutrients[nutrient.ordinal()]));
          }
        }
        break;
      default:
        CommandUtils.throwWrongUsage(sender, this, "cant_set_add_nutrients");
    }
  }

  private void executeSkill(ICommandSender sender, EntityPlayer player, String[] args, ExecuteType executeType) throws CommandException {
    if (args.length < 3) {
      CommandUtils.throwWrongUsage(sender, this, "id", args[0] + " " + args[1]);
    }
    SkillType<Skill> inputSkill = SkillType.get(args[2].toLowerCase(), Skill.class);
    if (inputSkill == null) {
      CommandUtils.throwWrongUsage(sender, this, "unknown_skill", args[2]);
    }
    Skill skill = CapabilityPlayerData.getSkill(player, inputSkill);
    if (skill == null) {
      return;
    }

    switch (executeType) {
      case GET:
        CommandUtils.sendLocalizedChatMessage(sender, this, "get.skill", inputSkill.getName(), skill.getTotalLevel(), new TextComponentTranslation(TranslatorUtils.getEnumName(skill.getTier())), skill.getLevel());
        break;
      case RESET:
        skill.setTotalLevel(0);
        CommandUtils.sendLocalizedChatMessage(sender, this, "set.skill", inputSkill.getName(), 0);
        break;
      default: {
        if (args.length < 4) {
          CommandUtils.throwWrongUsage(sender, this, "value", args[0] + " " + args[1] + " " + args[2]);
        }
        double level = parseDouble(args[3], 0, 4);

        switch (executeType) {
          case ADD:
            skill.addTotalLevel(level / 4.0D);
            CommandUtils.sendLocalizedChatMessage(sender, this, "add.skill", level, inputSkill.getName());
            break;
          case SET:
            skill.setTotalLevel(level / 4.0D);
            CommandUtils.sendLocalizedChatMessage(sender, this, "set.skill", inputSkill.getName(), level);
            break;
        }
      }
    }
  }

  private void executeFood(ICommandSender sender, EntityPlayer player, String[] args, ExecuteType executeType) throws CommandException {
    FoodStats foodStats = player.getFoodStats();

    switch (executeType) {
      case GET:
        CommandUtils.sendLocalizedChatMessage(sender, this, "get.food", foodStats.getFoodLevel());
        break;
      case RESET:
        foodStats.setFoodLevel(20);
        CommandUtils.sendLocalizedChatMessage(sender, this, "reset.food", 20);
        break;

      default: {
        if (args.length < 3) {
          CommandUtils.throwWrongUsage(sender, this, "value", args[0] + " " + args[1]);
        }
        int value = parseInt(args[2], 0, 20);

        switch (executeType) {
          case SET:
            foodStats.setFoodLevel(value);
            CommandUtils.sendLocalizedChatMessage(sender, this, "set.food", value);
          case ADD:
            value += foodStats.getFoodLevel();
            value = MathHelper.clamp(value, 0, 20);
            foodStats.setFoodLevel(value);
            CommandUtils.sendLocalizedChatMessage(sender, this, "add.food", value);
        }
      }
    }
  }

  private void executeSaturation(ICommandSender sender, EntityPlayer player, String[] args, ExecuteType executeType) throws CommandException {
    FoodStatsTFC foodStats = (FoodStatsTFC) player.getFoodStats();
    switch (executeType) {
      case GET:
        CommandUtils.sendLocalizedChatMessage(sender, this, "get.saturation", foodStats.getSaturationLevel());
        break;
      case RESET:
        foodStats.setSaturation(0);
        CommandUtils.sendLocalizedChatMessage(sender, this, "reset.saturation", 0);
        break;
      default: {
        if (args.length < 3) {
          CommandUtils.throwWrongUsage(sender, this, "value", args[0] + " " + args[1]);
        }
        int value = parseInt(args[2], 0, 20);
        switch (executeType) {
          case ADD:
            value += (int) foodStats.getSaturationLevel();
            value = MathHelper.clamp(value, 0, 20);
            foodStats.setSaturation(value);
            CommandUtils.sendLocalizedChatMessage(sender, this, "add.saturation", value);
            break;
          case SET:
            foodStats.setSaturation(value);
            CommandUtils.sendLocalizedChatMessage(sender, this, "set.saturation", value);
            break;
        }
      }
    }
  }

  private void executeWater(ICommandSender sender, EntityPlayer player, String[] args, ExecuteType executeType) throws CommandException {
    IFoodStatsTFC foodStats = (IFoodStatsTFC) player.getFoodStats();
    switch (executeType) {
      case GET:
        CommandUtils.sendLocalizedChatMessage(sender, this, "get.water", foodStats.getThirst());
        break;
      case RESET:
        foodStats.setThirst(100);
        CommandUtils.sendLocalizedChatMessage(sender, this, "reset.water", 100);
        break;
      default: {
        if (args.length < 3) {
          CommandUtils.throwWrongUsage(sender, this, "value", args[0] + " " + args[1]);
        }
        float value = (float) parseDouble(args[2], 0, 100);
        switch (executeType) {
          case ADD:
            value += foodStats.getThirst();
            value = MathHelper.clamp(value, 0, 100);
            foodStats.setThirst(value);
            CommandUtils.sendLocalizedChatMessage(sender, this, "add.water", value);
            break;
          case SET:
            foodStats.setThirst(value);
            CommandUtils.sendLocalizedChatMessage(sender, this, "set.water", value);
            break;
        }
      }
    }
  }

  @Override
  public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
    if (args.length == 1) {
      return getListOfStringsMatchingLastWord(args, "get", "set", "reset", "add");
    } else if (args.length == 2) {
      return getListOfStringsMatchingLastWord(args, "nutrition", "skill", "food", "saturation", "water");
    } else if (args.length == 3) {
      if ("skill".equals(args[2])) {
        return getListOfStringsMatchingLastWord(args, SkillType.getSkills()
          .stream()
          .map(s -> s.getName().toLowerCase())
          .collect(Collectors.toList()));
      }
    }
    return Collections.emptyList();
  }

}
