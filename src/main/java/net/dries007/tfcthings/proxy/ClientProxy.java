package net.dries007.tfcthings.proxy;

import net.minecraft.client.Minecraft;

import net.dries007.tfcthings.entity.projectile.EntityThrownHookJavelin;

public class ClientProxy extends CommonProxy {

  private final Minecraft MINECRAFT = Minecraft.getMinecraft();

  public void syncJavelinGroundState(int javelinID, boolean inGround) {
    EntityThrownHookJavelin javelin = (EntityThrownHookJavelin) MINECRAFT.world.getEntityByID(javelinID);
    javelin.setInGroundSynced(inGround);
  }

}
