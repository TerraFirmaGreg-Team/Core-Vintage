//package net.dries007.tfc.api.types2.rock;
//
//import net.minecraft.util.IStringSerializable;
//import su.terrafirmagreg.modules.stonelayers.objects.blocks.loose.BlockLooseFlintTFG;
//import su.terrafirmagreg.modules.stonelayers.objects.blocks.loose.BlockLooseMixedStoneTFG;
//import su.terrafirmagreg.modules.stonelayers.objects.blocks.loose.BlockLooseStickTFG;
//import su.terrafirmagreg.modules.stonelayers.objects.blocks.loose.BlockLooseTFG;
//
//import javax.annotation.Nonnull;
//import java.util.function.Function;
//
//public enum FossilType implements IStringSerializable {
//    //STICK(BlockLooseStick::new),
//    MIXED_STONE(BlockLooseMixedStone::new),
//    FLINT(BlockLooseFlint::new);
//
//    private final Function<FossilType, BlockLooseTFG> blockFactory;
//
//    FossilType(Function<FossilType, BlockLooseTFG> blockFactory) {
//        this.blockFactory = blockFactory;
//    }
//
//    public BlockLooseTFG createBlock() {
//        return this.blockFactory.apply(this);
//    }
//
//    @Nonnull
//    @Override
//    public String getName() {
//        return name().toLowerCase();
//    }
//}
