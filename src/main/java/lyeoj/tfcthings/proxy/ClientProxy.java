package lyeoj.tfcthings.proxy;

import lyeoj.tfcthings.entity.projectile.EntityThrownHookJavelin;
import lyeoj.tfcthings.init.TFCThingsEntities;
import lyeoj.tfcthings.renderer.TESRGemDisplay;
import lyeoj.tfcthings.renderer.TESRGrindstone;
import lyeoj.tfcthings.tileentity.TileEntityGemDisplay;
import lyeoj.tfcthings.tileentity.TileEntityGrindstone;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy extends CommonProxy {

	private final Minecraft MINECRAFT = Minecraft.getMinecraft();



	public void syncJavelinGroundState(int javelinID, boolean inGround) {
		EntityThrownHookJavelin javelin = (EntityThrownHookJavelin) MINECRAFT.world.getEntityByID(javelinID);
		javelin.setInGroundSynced(inGround);
	}

}
