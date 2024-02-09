package su.terrafirmagreg.api.module;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLLog;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;

import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class ModuleConstructor {

    @Nullable
    public ModuleBase constructModule(String modId, Class<? extends ModuleBase> moduleClass) {
        try {
            ModuleBase module = moduleClass.newInstance();
            module.setModId(modId);
            ModuleManager.LOGGER.info("[ {} ] Loaded module: {}", modId, moduleClass.getName());
            return module;

        } catch (Exception e) {
            ModuleManager.LOGGER.error("[ {} ] Error loading module: {} {}", modId, moduleClass.getName(), e);
        }

        return null;
    }
}
