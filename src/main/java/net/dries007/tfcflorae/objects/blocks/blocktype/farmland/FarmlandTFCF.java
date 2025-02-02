package net.dries007.tfcflorae.objects.blocks.blocktype.farmland;

import net.dries007.tfc.api.types.Rock;
import net.dries007.tfcflorae.objects.blocks.blocktype.BlockRockVariantFallableTFCF;
import net.dries007.tfcflorae.types.BlockTypesTFCF.RockTFCF;

public abstract class FarmlandTFCF extends BlockRockVariantFallableTFCF {

  public FarmlandTFCF(RockTFCF rockTFCF, Rock rock) {
    super(rockTFCF, rock);
  }
}
