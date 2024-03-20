package lyeoj.tfcthings.proxy;

import lyeoj.tfcthings.entity.projectile.EntityThrownHookJavelin;
import net.minecraft.client.Minecraft;

public class ClientProxy extends CommonProxy {

	private final Minecraft MINECRAFT = Minecraft.getMinecraft();


	public void syncJavelinGroundState(int javelinID, boolean inGround) {
		EntityThrownHookJavelin javelin = (EntityThrownHookJavelin) MINECRAFT.world.getEntityByID(javelinID);
		javelin.setInGroundSynced(inGround);
	}

}
