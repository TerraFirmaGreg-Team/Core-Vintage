package net.dries007.tfc.objects.blocks.rock;

import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.api.util.Triple;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Random;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types2.rock.RockBlockType.ORDINARY;
import static net.dries007.tfc.api.types2.rock.RockVariant.GRAVEL;
import static net.dries007.tfc.api.types2.rock.RockVariant.SAND;

public class BlockRockFallable extends BlockRockVariant {

    private final RockVariant rockVariant;
    private final RockType rockType;
    private final ResourceLocation modelLocation;

    public BlockRockFallable(RockVariant rockVariant, RockType rockType) {
        super(Material.SAND);

        if (BLOCK_ROCK_MAP.put(new Triple<>(ORDINARY, rockVariant, rockType), this) != null)
            throw new RuntimeException("Duplicate registry entry detected for block: " + rockVariant + " " + rockType);

        if (rockVariant.canFall()) {
            FallingBlockManager.registerFallable(this, rockVariant.getFallingSpecification());
        }

        this.rockVariant = rockVariant;
        this.rockType = rockType;
        this.modelLocation = new ResourceLocation(MOD_ID, "rock/" + rockVariant);

        String blockRegistryName = String.format("rock/%s/%s", rockVariant, rockType);
        if (rockVariant == SAND) {
            this.setSoundType(SoundType.SAND);
        } else {
            this.setSoundType(SoundType.GROUND);
        }

        this.setHardness(getFinalHardness() * 0.2F);
        this.setHarvestLevel("shovel", 0);
        this.setRegistryName(MOD_ID, blockRegistryName);
        this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));

        //OreDictionaryModule.register(this, rockVariant.getName(), rockVariant.getName() + WordUtils.capitalize(rockType.getName()));
    }

    @Override
    public RockVariant getRockVariant() {
        return rockVariant;
    }

    @Override
    public RockType getRockType() {
        return rockType;
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlock(this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(modelLocation, "rocktype=" + rockType.getName());
            }
        });

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this),
                this.getMetaFromState(this.getBlockState().getBaseState()),
                new ModelResourceLocation(modelLocation, "rocktype=" + rockType.getName()));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        if (this.rockVariant.canFall() && rand.nextInt(16) == 0 && FallingBlockManager.shouldFall(world, pos, pos, state, false)) {
            double d0 = (float) pos.getX() + rand.nextFloat();
            double d1 = (double) pos.getY() - 0.05D;
            double d2 = (float) pos.getZ() + rand.nextFloat();
            world.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.getStateId(state));
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if (rockVariant == GRAVEL) {
            if (fortune > 3)
                fortune = 3;

            if (rand.nextInt(10 - fortune * 3) == 0)
                return Items.FLINT;
        }
        return super.getItemDropped(state, rand, fortune);
    }
}
