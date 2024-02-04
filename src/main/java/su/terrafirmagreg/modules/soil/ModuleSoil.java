package su.terrafirmagreg.modules.soil;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.module.Module;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.objects.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.soil.api.types.type.SoilTypeHandler;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariantHandler;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariantHandler;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;

import java.util.Collections;
import java.util.List;

@Module(moduleID = "Soil", name = "TFG Soil")
public class ModuleSoil extends ModuleBase {

    public static final Logger LOGGER = LogManager.getLogger("Module Soil");

    public static final CreativeTabs SOIL_TAB = new CreativeTabBase("soil", "soil/grass/silt", false);


    public ModuleSoil() {
        super(2);
        this.enableAutoRegistry(SOIL_TAB);

        MinecraftForge.EVENT_BUS.register(this);

    }

    @Override
    public void onRegister() {
        SoilTypeHandler.init();
        SoilBlockVariantHandler.init();
        SoilItemVariantHandler.init();

        BlocksSoil.onRegister(registry);
        ItemsSoil.onRegister(registry);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onClientRegister() {
        BlocksSoil.onClientRegister(registry);
        ItemsSoil.onClientRegister(registry);
    }

    @Override
    public @NotNull Logger getLogger() {
        return LOGGER;
    }

    @NotNull
    @Override
    public List<Class<?>> getEventBusSubscribers() {
        return Collections.singletonList(ModuleSoil.class);
    }
}
