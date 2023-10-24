package BananaFructa.tfcpassingdays;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

public class TETriggerList extends ArrayList<TileEntity> {

    World worldParent;

    public TETriggerList(World parent) {
        worldParent = parent;
    }

    @Override
    public Iterator<TileEntity> iterator() {
        return new ItrWrapper(super.iterator());
    }


    class ItrWrapper implements Iterator<TileEntity> {

        Iterator<TileEntity> iteratorInner;

        public ItrWrapper(Iterator<TileEntity> iterator) {
            iteratorInner = iterator;
        }

        @Override
        public boolean hasNext() {
            boolean hasNext = iteratorInner.hasNext();
            if (!hasNext) ((PassingDayWorldProviderServer)worldParent.provider).shouldInterceptCalls(false);
            worldParent.calculateInitialSkylight();
            return hasNext;
        }

        @Override
        public TileEntity next() {
            TileEntity te = iteratorInner.next();
            ((PassingDayWorldProviderServer)worldParent.provider).shouldInterceptCalls(true);
            ((PassingDayWorldProviderServer)worldParent.provider).setZIntercept(te.getPos().getZ());
            worldParent.calculateInitialSkylight();
            return te;
        }

        @Override
        public void remove() {
            iteratorInner.remove();
        }

        @Override
        public void forEachRemaining(Consumer<? super TileEntity> action) {
            iteratorInner.forEachRemaining(action);
        }
    }

}
