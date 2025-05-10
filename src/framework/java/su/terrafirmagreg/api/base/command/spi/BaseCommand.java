package su.terrafirmagreg.api.base.command.spi;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import su.terrafirmagreg.api.base.command.api.ICommandSettings;
import su.terrafirmagreg.api.util.CommandUtils;
import su.terrafirmagreg.api.util.ModUtils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * This class is a new implementation of CommandBase, which adds working permission levels.
 */
@Getter
public abstract class BaseCommand extends CommandBase implements ICommandSettings {

    protected final Settings settings;

    @Setter
    private ResourceLocation registryName;

    public BaseCommand() {
        this(Settings.of());
    }

    public BaseCommand(Settings settings) {

        this.settings = settings;
    }

    @Override
    public String getName() {

        return settings.getRegistryKey();
    }

    @Override
    public String getUsage(ICommandSender sender) {

        return ModUtils.format(getTranslationKey(), "usage");
    }


    @Override
    public int getRequiredPermissionLevel() {

        return settings.getLevel().getRequiredPermissionLevel();
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {

        return settings.getLevel().getPermissionChecker().checkPermission(server, sender, this);
    }

    public String getTranslationKey() {
        return ModUtils.localize("command", this.getRegistryName());
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        if (args.length == 0) {
            return Collections.emptyList();

        } else if (isUsernameIndex(args, args.length - 1)) {
            return CommandUtils.getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        }

        return super.getTabCompletions(server, sender, args, pos);
    }


}
