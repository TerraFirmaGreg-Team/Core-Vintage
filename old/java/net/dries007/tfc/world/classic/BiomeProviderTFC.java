package net.dries007.tfc.world.classic;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.dries007.tfc.world.classic.genlayers.GenLayerTFC;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

/**
 * Класс BiomeProviderTFC расширяет класс BiomeProvider и предоставляет пользовательский провайдер биомов для TFC (Terrafirmacraft).
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BiomeProviderTFC extends BiomeProvider {
    /**
     * Конструктор класса BiomeProviderTFC.
     *
     * @param world Мир, для которого создается провайдер биомов.
     * @throws RuntimeException Если тип мира не является WorldTypeTFC.
     */
    public BiomeProviderTFC(World world) {
        super(world.getWorldInfo());

        if (!(world.getWorldType() instanceof WorldTypeTFC)) {
            throw new RuntimeException("Terrible things have gone wrong here.");
        }
    }

    /**
     * Возвращает список биомов, в которых могут появляться игроки.
     *
     * @return Список биомов для спавна.
     */
    @Override
    public List<Biome> getBiomesToSpawnIn() {
        return BiomesTFC.getSpawnBiomes();
    }

    /**
     * Здесь происходит фактическое переопределение генерации биомов. Мы отбрасываем оригинальный генератор и вставляем свой собственный.
     *
     * @param worldType Тип мира.
     * @param seed      Семя генерации.
     * @param original  Оригинальные генераторы биомов.
     * @return Модифицированные генераторы биомов.
     */
    @Override
    public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original) {
        return GenLayerTFC.initializeBiomes(seed);
    }
}
