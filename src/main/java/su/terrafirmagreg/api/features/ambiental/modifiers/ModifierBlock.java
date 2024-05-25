package su.terrafirmagreg.api.features.ambiental.modifiers;

import su.terrafirmagreg.api.features.ambiental.AmbientalRegistry;
import su.terrafirmagreg.api.features.ambiental.provider.ITemperatureBlockProvider;
import su.terrafirmagreg.api.features.ambiental.provider.ITemperatureTileProvider;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.rock.objects.blocks.BlockRock;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoil;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;


import java.util.Optional;

import static su.terrafirmagreg.api.features.ambiental.AmbientalRegistry.BLOCKS;

public class ModifierBlock extends ModifierBase {

    static {
        BLOCKS.register((player, pos, state) ->
                Optional.of(new ModifierBase("torch", 3f, 0f)).filter((mod) -> state.getBlock() == Blocks.TORCH));

        BLOCKS.register((player, pos, state) ->
                Optional.of(new ModifierBase("fire", 3f, 0f)).filter((mod) -> state.getBlock() == Blocks.FIRE));

        BLOCKS.register((player, pos, state) ->
                Optional.of(new ModifierBase("lava", 3f, 0f)).filter((mod) -> state.getBlock() == Blocks.LAVA));

        BLOCKS.register((player, pos, state) ->
                Optional.of(new ModifierBase("flowing_lava", 3f, 0f)).filter((mod) -> state.getBlock() == Blocks.FLOWING_LAVA));

        BLOCKS.register((player, pos, state) ->
                Optional.of(new ModifierBase("snow", -1.5f, 0.2f)).filter((mod) -> state.getBlock() == Blocks.SNOW_LAYER));

        BLOCKS.register((player, pos, state) ->
                Optional.of(new ModifierBase("snow", -0.5f, 0.2f)).filter((mod) -> state.getBlock() == Blocks.SNOW && player.world.getLightFor(EnumSkyBlock.SKY, pos) == 15));
    }

    public boolean affectedByDistance = false;

    public ModifierBlock(String name) {
        super(name);

    }

    public ModifierBlock(String name, float change, float potency) {
        super(name, change, potency);

    }

    public ModifierBlock(String name, float change, float potency, boolean affectedByDistance) {
        super(name, change, potency);
        this.affectedByDistance = affectedByDistance;

    }

    public static void computeModifiers(EntityPlayer player, ModifierStorage storage) {
        BlockPos p = player.getPosition();
        BlockPos pos1 = new BlockPos(p.getX() - 9, p.getY() - 3, p.getZ() - 9);
        BlockPos pos2 = new BlockPos(p.getX() + 9, p.getY() + 5, p.getZ() + 9);
        World world = player.world;
        Iterable<BlockPos.MutableBlockPos> allPositions = BlockPos.getAllInBoxMutable(pos1, pos2);
        IBlockState skipState = Blocks.AIR.getDefaultState();

        for (BlockPos.MutableBlockPos pos : allPositions) {
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();

            if (state == skipState) {
                continue;
            }
            if (block instanceof BlockRock || block instanceof BlockSoil) {
                continue;
            }

            double distance = Math.sqrt(player.getPosition().distanceSq(pos));
            float distanceMultiplier = (float) distance / 9f;

            distanceMultiplier = Math.min(1f, Math.max(0f, distanceMultiplier));
            distanceMultiplier = 1f - distanceMultiplier;

            boolean isInside = ModifierEnvironmental.getSkylight(player) < 14 && ModifierEnvironmental.getBlockLight(player) > 3;
            if (isInside) {
                distanceMultiplier *= 1.3f;
            }

            final float finalDistanceMultiplier = distanceMultiplier;
            if (block instanceof ITemperatureBlockProvider provider) {
                storage.add(provider.getModifier(player, pos, state));
            } else {
                for (ITemperatureBlockProvider provider : BLOCKS) {
                    storage.add(provider.getModifier(player, pos, state));
                }
            }

            if (block.hasTileEntity(state)) {
                var tile = TileUtils.getTile(world, pos);
                if (tile instanceof ITemperatureTileProvider provider) {
                    storage.add(provider.getModifier(player, tile));
                } else
                    for (ITemperatureTileProvider provider : AmbientalRegistry.TILE_ENTITIES) {
                        provider.getModifier(player, tile).ifPresent(mod -> {
                            mod.setChange(mod.getChange() * finalDistanceMultiplier);
                            mod.setPotency(mod.getPotency() * finalDistanceMultiplier);
                            storage.add(mod);
                        });
                        storage.add(provider.getModifier(player, tile));
                    }
            }
        }
    }

}
