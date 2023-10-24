package com.lumintorious.tfcstacks;

import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.Rock.Type;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariantFallable;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.BlockGrass;

public class TestGrass extends BlockRockVariantFallable{

	public TestGrass(Type type, Rock rock) {
		super(type, rock);
		
	}
}
