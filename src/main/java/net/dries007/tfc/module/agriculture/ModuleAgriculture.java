package net.dries007.tfc.module.agriculture;

import net.dries007.tfc.module.agriculture.api.types.bush.type.BushTypeHandler;
import net.dries007.tfc.module.agriculture.api.types.crop.category.CropCategoryHandler;
import net.dries007.tfc.module.agriculture.api.types.crop.type.CropTypeHandler;
import net.dries007.tfc.module.agriculture.api.types.crop.variant.block.CropBlockVariantHandler;
import net.dries007.tfc.module.agriculture.api.types.crop.variant.item.CropItemVariantHandler;
import net.dries007.tfc.module.agriculture.init.BlocksCrop;
import net.dries007.tfc.module.agriculture.init.ItemsCrop;
import net.dries007.tfc.module.core.api.util.CreativeTabBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import su.terrafirmagreg.util.module.ModuleBase;
import su.terrafirmagreg.util.registry.Registry;

import static net.dries007.tfc.Tags.MOD_ID;
import static net.dries007.tfc.Tags.MOD_NAME;

public class ModuleAgriculture extends ModuleBase {

    public static final String MODULE_ID = "module.agriculture";
    public static final CreativeTabs AGRICULTURE_TAB = new CreativeTabBase("agriculture", "tfc:crop.seed.rice");
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME + "." + ModuleAgriculture.class.getSimpleName());

    public ModuleAgriculture() {
        super(0, MOD_ID);

        this.setRegistry(new Registry(MOD_ID, AGRICULTURE_TAB));
        this.enableAutoRegistry();

        //PACKET_SERVICE = this.enableNetwork();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onRegister(Registry registry) {
        CropCategoryHandler.init();
        CropTypeHandler.init();
        CropBlockVariantHandler.init();
        CropItemVariantHandler.init();

        BushTypeHandler.init();

        BlocksCrop.onRegister(registry);
        ItemsCrop.onRegister(registry);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onClientRegister(Registry registry) {
        BlocksCrop.onClientRegister(registry);
        ItemsCrop.onClientRegister(registry);
    }
}
