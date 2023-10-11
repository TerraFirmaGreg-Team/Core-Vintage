package net.dries007.tfc.module.devices.init;

import com.google.common.collect.ImmutableMap;
import net.dries007.tfc.Tags;
import net.dries007.tfc.module.devices.client.render.*;
import net.dries007.tfc.module.devices.objects.blocks.*;
import net.dries007.tfc.module.devices.objects.tile.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.util.registry.Registry;

public class BlocksDevice {

    public static BlockBellows BELLOWS;
    public static BlockBlastFurnace BLAST_FURNACE;
    public static BlockBloom BLOOM;
    public static BlockBloomery BLOOMERY;
    public static BlockPitKiln PIT_KILN;
    public static BlockCharcoalForge CHARCOAL_FORGE;
    public static BlockCharcoalPile CHARCOAL_PILE;
    public static BlockCrucible CRUCIBLE;
    public static BlockFirePit FIREPIT;
    public static BlockLogPile LOG_PILE;
    public static BlockQuern QUERN;
    public static BlockMolten MOLTEN;

    public static void onRegister(Registry registry) {

        registry.registerBlock(BELLOWS = new BlockBellows(), BELLOWS.getItemBlock(), BlockBellows.NAME);
        registry.registerBlock(BLAST_FURNACE = new BlockBlastFurnace(), BLAST_FURNACE.getItemBlock(), BlockBlastFurnace.NAME);
        registry.registerBlock(BLOOMERY = new BlockBloomery(), BLOOMERY.getItemBlock(), BlockBloomery.NAME);
        registry.registerBlock(CHARCOAL_FORGE = new BlockCharcoalForge(), CHARCOAL_FORGE.getItemBlock(), BlockCharcoalForge.NAME);
        registry.registerBlock(CRUCIBLE = new BlockCrucible(), CRUCIBLE.getItemBlock(), BlockCrucible.NAME);
        registry.registerBlock(FIREPIT = new BlockFirePit(), FIREPIT.getItemBlock(), BlockFirePit.NAME);
        registry.registerBlock(QUERN = new BlockQuern(), QUERN.getItemBlock(), BlockQuern.NAME);
        registry.registerBlock(PIT_KILN = new BlockPitKiln(), PIT_KILN.getItemBlock(), BlockPitKiln.NAME);

        //TODO отключить потом им ItemBlock
        registry.registerBlock(BLOOM = new BlockBloom(), BLOOM.getItemBlock(), BlockBloom.NAME);
        registry.registerBlock(LOG_PILE = new BlockLogPile(), LOG_PILE.getItemBlock(), BlockLogPile.NAME);
        registry.registerBlock(CHARCOAL_PILE = new BlockCharcoalPile(), CHARCOAL_PILE.getItemBlock(), BlockCharcoalPile.NAME);
        registry.registerBlock(MOLTEN = new BlockMolten(), MOLTEN.getItemBlock(), BlockMolten.NAME);

        registry.registerTileEntities(
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
            MOLTEN.onModelRegister();
            PIT_KILN.onModelRegister();

            //TODO
            ModelLoader.setCustomStateMapper(BlocksDevice.PIT_KILN, blockIn -> ImmutableMap.of(BlocksDevice.PIT_KILN.getDefaultState(), new ModelResourceLocation(Tags.MOD_ID + ":empty")));

        });

        //==== TESRs =================================================================================================//
        ClientRegistry.bindTileEntitySpecialRenderer(TEPitKiln.class, new TESRPitKiln());
        ClientRegistry.bindTileEntitySpecialRenderer(TEQuern.class, new TESRQuern());
        ClientRegistry.bindTileEntitySpecialRenderer(TEBellows.class, new TESRBellows());
        ClientRegistry.bindTileEntitySpecialRenderer(TECrucible.class, new TESRCrucible());
        ClientRegistry.bindTileEntitySpecialRenderer(TEFirePit.class, new TESRFirePit());
    }
}
