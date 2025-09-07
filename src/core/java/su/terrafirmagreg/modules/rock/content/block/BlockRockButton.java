package su.terrafirmagreg.modules.rock.content.block;

import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockButton;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.feature.rocktype.types.IRockEntry;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import lombok.Getter;

@Getter
public class BlockRockButton extends BaseBlockButton implements IRockEntry {

  protected final RockType type;

  public BlockRockButton(RockType type) {
    super(false, BlockSettings.of()
      .material(Material.CIRCUITS)
      .hardness(type.getHardness(6f))
      .sound(SoundType.STONE)
      .hardness(0.5f)
      .addOreDict("button", "stone")
      .addOreDict("button", "stone", type));

    this.type = type;
  }


}
