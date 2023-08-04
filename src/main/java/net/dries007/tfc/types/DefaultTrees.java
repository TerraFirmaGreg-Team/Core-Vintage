package net.dries007.tfc.types;

import net.dries007.tfc.api.util.ITreeGenerator;
import net.dries007.tfc.world.classic.worldgen.trees.*;
import net.minecraftforge.fml.common.Mod;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@SuppressWarnings("WeakerAccess")
@Mod.EventBusSubscriber(modid = MOD_ID)
public final class DefaultTrees {

	/**
	 * Simple ITreeGenerator instances.
	 */
	public static final ITreeGenerator GEN_NORMAL = new TreeGenNormal(1, 3);
	public static final ITreeGenerator GEN_MEDIUM = new TreeGenNormal(2, 2);
	public static final ITreeGenerator GEN_TALL = new TreeGenNormal(3, 3);
	public static final ITreeGenerator GEN_CONIFER = new TreeGenVariants(false, 7);
	public static final ITreeGenerator GEN_TROPICAL = new TreeGenVariants(true, 7);
	public static final ITreeGenerator GEN_WILLOW = new TreeGenWillow();
	public static final ITreeGenerator GEN_ACACIA = new TreeGenAcacia();
	public static final ITreeGenerator GEN_KAPOK = new TreeGenKapok();
	public static final ITreeGenerator GEN_SEQUOIA = new TreeGenSequoia();
	public static final ITreeGenerator GEN_KAPOK_COMPOSITE = new TreeGenComposite().add(0.4f, GEN_TALL).add(0.6f, GEN_KAPOK);
	public static final ITreeGenerator GEN_BUSHES = new TreeGenBushes();

}
