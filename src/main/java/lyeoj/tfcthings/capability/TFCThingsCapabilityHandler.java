package lyeoj.tfcthings.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


import lyeoj.tfcthings.items.ItemRopeJavelin;
import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.objects.items.metal.ItemMetalSword;
import net.dries007.tfc.objects.items.metal.ItemMetalTool;

import java.util.Arrays;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFCTHINGS;

public class TFCThingsCapabilityHandler {

    public static final ResourceLocation SHARPNESS_CAPABILITY = new ResourceLocation(MODID_TFCTHINGS, "sharpness");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() instanceof ItemMetalTool
                || event.getObject().getItem() instanceof ItemMetalSword
                || event.getObject().getItem() instanceof ItemRopeJavelin
                || (event.getObject().getItem().getRegistryName() != null
                && Arrays.asList(ConfigTFCThings.Items.WHETSTONE.canSharpen)
                .contains(event.getObject().getItem().getRegistryName().toString()))) {
            event.addCapability(SHARPNESS_CAPABILITY, new SharpnessHandler(event.getObject()));
        }
    }

}
