package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;
import su.terrafirmagreg.modules.core.content.block.BlockAggregate;
import su.terrafirmagreg.modules.core.content.block.BlockDebug;
import su.terrafirmagreg.modules.core.content.block.BlockFireBricks;
import su.terrafirmagreg.modules.core.content.block.BlockPuddle;
import su.terrafirmagreg.modules.core.content.block.BlockThatch;

import net.minecraft.block.Block;

import net.dries007.tfc.objects.blocks.BlockFireClay;
import net.dries007.tfc.objects.blocks.metal.BlockIngotPile;

public final class BlocksCore {


  public static BlockDebug DEBUG;
  public static BlockPuddle PUDDLE;
  public static BlockFireBricks FIRE_BRICKS;
  public static BlockThatch THATCH;
  public static BlockAggregate AGGREGATE;
  public static BlockIngotPile INGOT_PILE;
  public static BlockFireClay FIRE_CLAY;
  public static Block JACK_O_LANTERN;
//  public static BlockGroundcoverDriftwood DRIFTWOOD;
//  public static BlockGroundcoverFlint FLINT;
//  public static BlockGroundcoverSeashells SEASHELLS;
//  public static BlockGroundcoverBones BONES;
//  public static BlockGroundcoverPinecone PINECONE;
//  public static BlockGroundcoverTwig TWIG;

  public static void onRegister(IContentRegistrar registry) {

    DEBUG = registry.addBlock(new BlockDebug());
    PUDDLE = registry.addBlock(new BlockPuddle());
    FIRE_BRICKS = registry.addBlock(new BlockFireBricks());
    THATCH = registry.addBlock(new BlockThatch());
    AGGREGATE = registry.addBlock(new BlockAggregate());
//    INGOT_PILE = registry.addBlock(new BlockIngotPile());
//    FIRE_CLAY = registry.addBlock(new BlockFireClay());
//    DRIFTWOOD = registry.block(new BlockGroundcoverDriftwood());
//    FLINT = registry.block(new BlockGroundcoverFlint());
//    SEASHELLS = registry.block(new BlockGroundcoverSeashells());
//    BONES = registry.block(new BlockGroundcoverBones());
//    PINECONE = registry.block(new BlockGroundcoverPinecone());
//    TWIG = registry.block(new BlockGroundcoverTwig());
  }
}
