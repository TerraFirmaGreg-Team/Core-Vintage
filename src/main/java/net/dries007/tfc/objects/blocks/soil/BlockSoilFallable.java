/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.blocks.soil;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.types2.soil.SoilType;
import net.dries007.tfc.api.types2.soil.SoilVariant;
import net.dries007.tfc.api.util.*;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
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
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.objects.blocks.soil.BlockSoil.BLOCK_SOIL_MAP;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockSoilFallable extends Block implements ISoilTypeBlock {

    private final SoilVariant soilVariant;
    private final SoilType soilType;
    private final ResourceLocation modelLocation;

    public BlockSoilFallable(SoilVariant soilVariant, SoilType soilType) {
        super(Material.GROUND);

        if (BLOCK_SOIL_MAP.put(new Pair<>(soilVariant, soilType), this) != null)
            throw new RuntimeException("Duplicate registry entry detected for block: " + soilVariant + " " + soilType);

        if (soilVariant.canFall()) {
            FallingBlockManager.registerFallable(this, soilVariant.getFallingSpecification());
        }

        this.soilVariant = soilVariant;
        this.soilType = soilType;
        this.modelLocation = new ResourceLocation(MOD_ID, "soil/" + soilVariant);

        String blockRegistryName = String.format("%s/%s/%s", "soil", soilVariant, soilType);

        this.setCreativeTab(CreativeTabsTFC.EARTH);
        this.setSoundType(SoundType.STONE);
        this.setRegistryName(MOD_ID, blockRegistryName);
        this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
    }

    @Override
    public SoilVariant getSoilVariant() {
        return soilVariant;
    }

    @Override
    public SoilType getSoilType() {
        return soilType;
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlock(this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
    {
        if (this.soilVariant.canFall() && rand.nextInt(16) == 0 && FallingBlockManager.shouldFall(world, pos, pos, state, false))
        {
            double d0 = (float) pos.getX() + rand.nextFloat();
            double d1 = (double) pos.getY() - 0.05D;
            double d2 = (float) pos.getZ() + rand.nextFloat();
            world.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.getStateId(state));
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return super.getItemDropped(state, rand, fortune);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(modelLocation, "soiltype=" + soilType.getName());
            }
        });


        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this),
                this.getMetaFromState(this.getBlockState().getBaseState()),
                new ModelResourceLocation(modelLocation, "soiltype=" + soilType.getName()));
    }

}
