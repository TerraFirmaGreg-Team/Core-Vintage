package net.dries007.tfc.module.devices.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.api.util.RegistryHelper;
import net.dries007.tfc.module.devices.client.render.TESRBellows;
import net.dries007.tfc.module.devices.client.render.TESRCrucible;
import net.dries007.tfc.module.devices.client.render.TESRFirePit;
import net.dries007.tfc.module.devices.client.render.TESRQuern;
import net.dries007.tfc.module.devices.common.blocks.*;
import net.dries007.tfc.module.devices.common.tile.*;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlocksDevice {

    public static BlockBellows BELLOWS;
    public static BlockBlastFurnace BLAST_FURNACE;
    public static BlockBloom BLOOM;
    public static BlockBloomery BLOOMERY;
    public static BlockCharcoalForge CHARCOAL_FORGE;
    public static BlockCharcoalPile CHARCOAL_PILE;
    public static BlockCrucible CRUCIBLE;
    public static BlockFirePit FIREPIT;
    public static BlockLogPile LOG_PILE;
    public static BlockQuern QUERN;

    public static void onRegister(Registry registry) {

        registry.registerBlock(BELLOWS = new BlockBellows(), BELLOWS.getItemBlock(), BlockBellows.NAME);
        registry.registerBlock(BLAST_FURNACE = new BlockBlastFurnace(), BLAST_FURNACE.getItemBlock(), BlockBlastFurnace.NAME);
        registry.registerBlock(BLOOMERY = new BlockBloomery(), BLOOMERY.getItemBlock(), BlockBloomery.NAME);
        registry.registerBlock(CHARCOAL_FORGE = new BlockCharcoalForge(), CHARCOAL_FORGE.getItemBlock(), BlockCharcoalForge.NAME);
        registry.registerBlock(CRUCIBLE = new BlockCrucible(), CRUCIBLE.getItemBlock(), BlockCrucible.NAME);
        registry.registerBlock(FIREPIT = new BlockFirePit(), FIREPIT.getItemBlock(), BlockFirePit.NAME);
        registry.registerBlock(QUERN = new BlockQuern(), QUERN.getItemBlock(), BlockQuern.NAME);

        //TODO отключить потом им ItemBlock
        registry.registerBlock(BLOOM = new BlockBloom(), BLOOM.getItemBlock(), BlockBloom.NAME);
        registry.registerBlock(LOG_PILE = new BlockLogPile(), LOG_PILE.getItemBlock(), BlockLogPile.NAME);
        registry.registerBlock(CHARCOAL_PILE = new BlockCharcoalPile(), CHARCOAL_PILE.getItemBlock(), BlockCharcoalPile.NAME);

        RegistryHelper.registerTileEntities(registry,
                TEBellows.class,
                TEBlastFurnace.class,
                TEBloom.class,
                TEBloomery.class,
                TECharcoalForge.class,
                TECrucible.class,
                TEFirePit.class,
                TEPitKiln.class,
                TEQuern.class,
                TELogPile.class
        );
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {

        registry.registerClientModelRegistrationStrategy(() -> {

            BELLOWS.onModelRegister();
            BLAST_FURNACE.onModelRegister();
            BLOOM.onModelRegister();
            BLOOMERY.onModelRegister();
            CHARCOAL_FORGE.onModelRegister();
            CHARCOAL_PILE.onModelRegister();
            CRUCIBLE.onModelRegister();
            FIREPIT.onModelRegister();
            LOG_PILE.onModelRegister();
            QUERN.onModelRegister();

        });

        // TESRs
        ClientRegistry.bindTileEntitySpecialRenderer(TEQuern.class, new TESRQuern());
        ClientRegistry.bindTileEntitySpecialRenderer(TEBellows.class, new TESRBellows());
        ClientRegistry.bindTileEntitySpecialRenderer(TECrucible.class, new TESRCrucible());
        ClientRegistry.bindTileEntitySpecialRenderer(TEFirePit.class, new TESRFirePit());
    }
}
