package tfcflorae.types;

import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Rock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import static net.dries007.tfc.types.DefaultRocks.*;
import static su.terrafirmagreg.Constants.MODID_TFCF;

@SuppressWarnings({"unused", "WeakerAccess"})
@Mod.EventBusSubscriber(modid = MODID_TFCF)
public final class RockTypesTFCF {
	public static final ResourceLocation BRECCIA = new ResourceLocation(MODID_TFCF, "breccia");
	//public static final ResourceLocation FOIDOLITE = new ResourceLocation(MODID, "foidolite");
	public static final ResourceLocation PORPHYRY = new ResourceLocation(MODID_TFCF, "porphyry");
	public static final ResourceLocation PERIDOTITE = new ResourceLocation(MODID_TFCF, "peridotite");
	public static final ResourceLocation MUDSTONE = new ResourceLocation(MODID_TFCF, "mudstone");
	public static final ResourceLocation SANDSTONE = new ResourceLocation(MODID_TFCF, "sandstone");
	public static final ResourceLocation SILTSTONE = new ResourceLocation(MODID_TFCF, "siltstone");
	//public static final ResourceLocation BLUESCHIST = new ResourceLocation(MODID, "blueschist");
	public static final ResourceLocation CATLINITE = new ResourceLocation(MODID_TFCF, "catlinite");
	//public static final ResourceLocation GREENSCHIST = new ResourceLocation(MODID, "greenschist");
	public static final ResourceLocation NOVACULITE = new ResourceLocation(MODID_TFCF, "novaculite");
	public static final ResourceLocation SOAPSTONE = new ResourceLocation(MODID_TFCF, "soapstone");
	public static final ResourceLocation KOMATIITE = new ResourceLocation(MODID_TFCF, "komatiite");

	@SubscribeEvent
	public static void onPreRegisterRock(TFCRegistryEvent.RegisterPreBlock<Rock> event) {
		IForgeRegistry<Rock> r = event.getRegistry();

		r.register(new Rock(BRECCIA, IGNEOUS_INTRUSIVE, false));
		//r.register(new Rock(FOIDOLITE, IGNEOUS_INTRUSIVE, false));
		r.register(new Rock(PORPHYRY, IGNEOUS_INTRUSIVE, false));
		r.register(new Rock(PERIDOTITE, IGNEOUS_EXTRUSIVE, false));
		r.register(new Rock(MUDSTONE, SEDIMENTARY, false));
		r.register(new Rock(SANDSTONE, SEDIMENTARY, false));
		r.register(new Rock(SILTSTONE, SEDIMENTARY, false));
		//r.register(new Rock(BLUESCHIST, METAMORPHIC, false));
		r.register(new Rock(CATLINITE, METAMORPHIC, false));
		//r.register(new Rock(GREENSCHIST, METAMORPHIC, false));
		r.register(new Rock(NOVACULITE, METAMORPHIC, false));
		r.register(new Rock(SOAPSTONE, METAMORPHIC, false));
		r.register(new Rock(KOMATIITE, METAMORPHIC, false));
	}
}





