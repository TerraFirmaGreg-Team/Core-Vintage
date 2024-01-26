package su.terrafirmagreg.modules.core;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.SidedProxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.Tags;
import su.terrafirmagreg.api.modules.ModuleBase;
import su.terrafirmagreg.api.modules.ModuleTFG;
import su.terrafirmagreg.api.objects.creativetab.CreativeTabBase;
import su.terrafirmagreg.api.modules.ModuleContainerTFG;
import su.terrafirmagreg.modules.core.init.BlocksCore;
import su.terrafirmagreg.modules.core.init.ItemsCore;
import su.terrafirmagreg.proxy.IProxy;

import static su.terrafirmagreg.Tags.*;

@ModuleTFG(
        moduleID = ModuleContainerTFG.MODULE_CORE,
        containerID = Tags.MOD_ID,
        name = "TFG Core",
        description = "Core TFG content. Disabling this disables the entire mod and all its addons.",
        coreModule = true)
public class ModuleCore extends ModuleBase {

    public static final Logger LOGGER = LogManager.getLogger("ModuleCore");

    public static final CreativeTabs MISC_TAB = new CreativeTabBase("misc", "wand", false);


    public ModuleCore() {
        this.enableAutoRegistry(MISC_TAB);

    }

    @Override
    public void onRegister() {
        BlocksCore.onRegister(registry);
        ItemsCore.onRegister(registry);
    }

    @Override
    public @NotNull Logger getLogger() {
        return LOGGER;
    }
}
