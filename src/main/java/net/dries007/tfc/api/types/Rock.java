/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.api.types;

import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.util.FallingBlockManager.Specification;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.util.FallingBlockManager.Specification.*;


public class Rock extends IForgeRegistryEntry.Impl<Rock> {
    @GameRegistry.ObjectHolder("tfc:granite")
    public static final Rock GRANITE = Helpers.getNull();
    @GameRegistry.ObjectHolder("tfc:basalt")
    public static final Rock BASALT = Helpers.getNull();
    @GameRegistry.ObjectHolder("tfc:rhyolite")
    public static final Rock RHYOLITE = Helpers.getNull();
    @GameRegistry.ObjectHolder("tfc:limestone")
    public static final Rock LIMESTONE = Helpers.getNull();

    private final RockCategory rockCategory;
    private final ResourceLocation textureLocation;
    private final boolean isFluxStone;
    private final boolean isNaturallyGenerating;

    public Rock(@Nonnull ResourceLocation name, @Nonnull RockCategory rockCategory, boolean isFluxStone, boolean isNaturallyGenerating) {
        //noinspection ConstantConditions
        if (rockCategory == null)
            throw new IllegalArgumentException("Rock category is not allowed to be null (on rock " + name + ")");

        setRegistryName(name);
        this.rockCategory = rockCategory;
        this.textureLocation = new ResourceLocation(MOD_ID, "textures/blocks/stonetypes/raw/" + name.getPath() + ".png");
        this.isFluxStone = isFluxStone;
        this.isNaturallyGenerating = isNaturallyGenerating;
    }

    public Rock(@Nonnull ResourceLocation name, @Nonnull RockCategory rockCategory, boolean isFluxStone) {
        this(name, rockCategory, isFluxStone, true);
    }

    public Rock(@Nonnull ResourceLocation name, @Nonnull ResourceLocation categoryName, boolean isFluxStone) {
        //noinspection ConstantConditions
        this(name, TFCRegistries.ROCK_CATEGORIES.getValue(categoryName), isFluxStone, true);
    }

    /**
     * Used for knapping GUI
     *
     * @return a texture resource location
     */
    public ResourceLocation getTexture() {
        return textureLocation;
    }

    public RockCategory getRockCategory() {
        return rockCategory;
    }

    public boolean isFluxStone() {
        return isFluxStone;
    }

    public boolean isNaturallyGenerating() {
        return isNaturallyGenerating;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public String toString() {
        return getRegistryName().getPath();
    }


    public enum Type {
        RAW(Material.ROCK, false, COLLAPSABLE),
        ANVIL(Material.ROCK, false, COLLAPSABLE),
        SPIKE(Material.ROCK, false, null),
        SMOOTH(Material.ROCK, false, COLLAPSABLE),
        COBBLE(Material.ROCK, false, new Specification(true, () -> TFCSounds.ROCK_SLIDE_SHORT)),
        BRICKS(Material.ROCK, false, null),
        SAND(Material.SAND, false, VERTICAL_AND_HORIZONTAL),
        GRAVEL(Material.SAND, false, VERTICAL_AND_HORIZONTAL),
        DIRT(Material.GROUND, false, VERTICAL_AND_HORIZONTAL),
        GRASS(Material.GRASS, true, VERTICAL_AND_HORIZONTAL),
        DRY_GRASS(Material.GRASS, true, VERTICAL_AND_HORIZONTAL),
        CLAY(Material.CLAY, false, VERTICAL_ONLY),
        CLAY_GRASS(Material.GRASS, true, VERTICAL_ONLY),
        FARMLAND(Material.GROUND, false, VERTICAL_ONLY),
        PATH(Material.GROUND, false, VERTICAL_ONLY);

        public final Material material;
        public final boolean isGrass;

        @Nullable
        private final Specification fallingSpecification;

        Type(Material material, boolean isGrass, @Nullable Specification fallingSpecification) {
            this.material = material;
            this.isGrass = isGrass;
            this.fallingSpecification = fallingSpecification;
        }


        @Nullable
        public Specification getFallingSpecification() {
            return fallingSpecification;
        }
    }

}
