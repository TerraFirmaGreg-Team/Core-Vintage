package net.dries007.tfc.objects.blocks.stone;

import net.minecraft.block.BlockButtonStone;
import net.minecraft.block.SoundType;

import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.util.OreDictionaryHelper;

import java.util.HashMap;
import java.util.Map;

public class BlockButtonStoneTFC extends BlockButtonStone {

  private static final Map<Rock, BlockButtonStoneTFC> MAP = new HashMap<>();
  public final Rock rock;

  public BlockButtonStoneTFC(Rock rock) {
    this.rock = rock;
    if (MAP.put(rock, this) != null) {throw new IllegalStateException("There can only be one.");}
    setHardness(0.5F);
    setSoundType(SoundType.STONE);

    OreDictionaryHelper.register(this, "button", "stone");
    OreDictionaryHelper.register(this, "button", "stone", rock.toString());
  }

  public static BlockButtonStoneTFC get(Rock rock) {
    return MAP.get(rock);
  }
}
