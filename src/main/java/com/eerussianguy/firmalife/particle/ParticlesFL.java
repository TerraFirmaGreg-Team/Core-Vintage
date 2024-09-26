package com.eerussianguy.firmalife.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.client.particle.TFCParticles;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static su.terrafirmagreg.data.Constants.MODID_FL;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MODID_FL)
public enum ParticlesFL {
  SPRINKLE(new ResourceLocation(MODID_FL, "particle/sprinkle"), () -> ParticleSprinkle::new);

  private final ResourceLocation location;
  private final Supplier<TFCParticles.IParticleFactoryTFC> factorySupplier;
  private TextureAtlasSprite sprite;

  ParticlesFL(ResourceLocation location, Supplier<TFCParticles.IParticleFactoryTFC> factorySupplier) {
    this.location = location;
    this.factorySupplier = factorySupplier;
  }

  @SubscribeEvent
  public static void onTextureStitchEvent(TextureStitchEvent.Pre event) {
    for (ParticlesFL particle : ParticlesFL.values()) {
      particle.registerSprite(event.getMap());
    }
  }

  @SideOnly(Side.CLIENT)
  private void registerSprite(@NotNull TextureMap map) {
    this.sprite = map.registerSprite(location);
  }

  @SideOnly(Side.CLIENT)
  public void spawn(World worldIn, double x, double y, double z, double speedX, double speedY, double speedZ, int duration) {
    Particle particle = factorySupplier.get().createParticle(worldIn, x, y, z, speedX, speedY, speedZ, duration);
    particle.setParticleTexture(sprite);
    Minecraft.getMinecraft().effectRenderer.addEffect(particle);
  }
}
