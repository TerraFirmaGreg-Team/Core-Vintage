package su.terrafirmagreg.modules.soil;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.Tags;
import su.terrafirmagreg.api.modules.ModuleBase;
import su.terrafirmagreg.api.modules.ModuleTFG;
import su.terrafirmagreg.api.objects.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.soil.api.types.type.SoilTypeHandler;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariantHandler;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariantHandler;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;

import java.util.Collections;
import java.util.List;

import static su.terrafirmagreg.api.modules.ModuleContainerTFG.MODULE_SOIL;

@ModuleTFG(
        moduleID = MODULE_SOIL,
        containerID = Tags.MOD_ID,
        name = "TFG Soil")
public class ModuleSoil extends ModuleBase {

    public static final Logger LOGGER = LogManager.getLogger("ModuleSoil");

    public static final CreativeTabs SOIL_TAB = new CreativeTabBase("soil", "wand", false);


    public ModuleSoil() {
        this.enableAutoRegistry(SOIL_TAB);

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
