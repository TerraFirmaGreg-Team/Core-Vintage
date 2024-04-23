package su.terrafirmagreg.modules.core.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber
public class DebugInfoEventHandler {

    @SubscribeEvent()
    @SideOnly(Side.CLIENT)
    public static void on(RenderGameOverlayEvent.Text event) {
        if (Minecraft.getMinecraft().gameSettings.showDebugInfo) {
            event.getRight().add("Number of Biome IDs Registered: " + ForgeRegistries.BIOMES.getKeys().size());
            event.getRight().add("Number of Block IDs Registered: " + ForgeRegistries.BLOCKS.getKeys().size());
            event.getRight().add("Number of Item IDs Registered: " + ForgeRegistries.ITEMS.getKeys().size());
            event.getRight().add("Number of Potion IDs Registered: " + ForgeRegistries.POTIONS.getKeys().size());
            event.getRight().add("Number of Enchantment IDs Registered: " + ForgeRegistries.ENCHANTMENTS.getKeys().size());
            event.getRight().add("Number of Entity IDs Registered: " + ForgeRegistries.ENTITIES.getKeys().size());
        }
    }
}
