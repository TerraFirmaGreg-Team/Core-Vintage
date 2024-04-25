package su.terrafirmagreg.modules.core.objects.potion;

import su.terrafirmagreg.api.lib.MathConstants;
import su.terrafirmagreg.api.spi.effects.PotionBase;
import su.terrafirmagreg.modules.core.api.util.DamageSources;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class PotionSwarm extends PotionBase {

    public PotionSwarm() {
        super(true, 0xffff1a);
        formatTexture("swarm");
    }

    @Override
    public void performEffect(@NotNull EntityLivingBase entity, int amplifier) {
        World world = entity.getEntityWorld();
        if (world.isRemote) {
            BlockPos pos = entity.getPosition();
            Random rand = MathConstants.RNG;
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.5;
            double z = pos.getZ() + 0.5;
            for (int i = 0; i < 3 + rand.nextInt(4); i++) {
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + rand.nextFloat() - rand.nextFloat(), y + rand.nextFloat(),
                        z + rand.nextFloat() - rand.nextFloat(),
                        0.5 * (rand.nextFloat() - rand.nextFloat()), 0.5 * (rand.nextFloat() - rand.nextFloat()),
                        0.5 * (rand.nextFloat() - rand.nextFloat()));
            }
        } else {
            if (!entity.isWet())
                entity.attackEntityFrom(DamageSources.SWARM, 1.0f + amplifier);
        }

    }
}
