/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.blocks.soil;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types2.rock.RockBlockType;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.types2.soil.SoilBlockType;
import net.dries007.tfc.api.types2.soil.SoilType;
import net.dries007.tfc.api.types2.soil.SoilVariant;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.api.util.IRockTypeBlock;
import net.dries007.tfc.api.util.ISoilTypeBlock;
import net.dries007.tfc.api.util.Triple;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.objects.blocks.soil.BlockSoil.BLOCK_SOIL_MAP;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockSoilFallable extends Block implements ISoilTypeBlock {

    private final SoilBlockType soilBlockType;
    private final SoilVariant soilVariant;
    private final SoilType soilType;
    private final ResourceLocation modelLocation;

    public BlockSoilFallable(SoilBlockType soilBlockType, SoilVariant soilVariant, SoilType soilType) {
        super(Material.GROUND);

        if (BLOCK_SOIL_MAP.put(new Triple<>(soilBlockType, soilVariant, soilType), this) != null)
            throw new RuntimeException("Duplicate registry entry detected for block: " + soilVariant + " " + soilType);

        if (soilVariant.canFall())
        {
            FallingBlockManager.registerFallable(this, soilVariant.getFallingSpecification());
        }

        this.soilBlockType = soilBlockType;
        this.soilVariant = soilVariant;
        this.soilType = soilType;
        this.modelLocation = new ResourceLocation(MOD_ID, soilBlockType + "/" + soilType);

        String blockRegistryName = String.format("%s/%s/%s", soilBlockType, soilVariant, soilType);

        this.setRegistryName(MOD_ID, blockRegistryName);
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
    public void onModelRegister() {

    }

    @Override
    public SoilVariant getSoilVariant() {
        return null;
    }

    @Override
    public SoilType getSoilType() {
        return null;
    }

    @Override
    public ItemBlock getItemBlock() {
        return null;
    }
}
