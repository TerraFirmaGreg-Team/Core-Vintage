package net.dries007.tfc.objects.blocks.wood;

import su.terrafirmagreg.api.data.ToolClasses;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.util.OreDictionaryHelper;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class BlockBookshelfTFC extends Block {

  private static final Map<Tree, BlockBookshelfTFC> MAP = new HashMap<>();
  public final Tree wood;

  public BlockBookshelfTFC(Tree wood) {
    super(Material.WOOD);
    if (MAP.put(wood, this) != null) {throw new IllegalStateException("There can only be one.");}
    this.wood = wood;
    setSoundType(SoundType.WOOD);
    setHardness(2.0F).setResistance(5.0F);
    setHarvestLevel(ToolClasses.AXE, 0);
    OreDictionaryHelper.register(this, "bookshelf");
    //noinspection ConstantConditions
    OreDictionaryHelper.register(this, "bookshelf", wood.getRegistryName().getPath());
    Blocks.FIRE.setFireInfo(this, 30, 20);
  }

  public static BlockBookshelfTFC get(Tree wood) {
    return MAP.get(wood);
  }

  @SideOnly(Side.CLIENT)
  @Override
  @Nonnull
  public BlockRenderLayer getRenderLayer() {
    return BlockRenderLayer.CUTOUT_MIPPED;
  }

  @Override
  public float getEnchantPowerBonus(World world, BlockPos pos) {
    return 1.0F; // Same as vanilla
  }
}
