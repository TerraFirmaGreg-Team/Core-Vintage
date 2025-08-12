package su.terrafirmagreg.api.library.model;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class CustomStateContainer {

  public static class Builder {

    private final Block block;
    private final List<IProperty<?>> listed = Lists.newArrayList();
    private final List<IUnlistedProperty<?>> unlisted = Lists.newArrayList();

    public Builder(Block block) {
      this.block = block;
    }

    public Builder add(IProperty<?>... props) {
      this.listed.addAll(Arrays.asList(props));
      return this;
    }

    public Builder add(IUnlistedProperty<?>... props) {
      this.unlisted.addAll(Arrays.asList(props));
      return this;
    }

    public Builder remove(IProperty<?>... props) {
      this.listed.removeAll(Arrays.asList(props));
      return this;
    }

    public Builder remove(IUnlistedProperty<?>... props) {
      this.unlisted.removeAll(Arrays.asList(props));
      return this;
    }

    public BlockStateContainer build() {
      IProperty<?>[] listed = new IProperty[this.listed.size()];
      listed = this.listed.toArray(listed);
      if (this.unlisted.isEmpty()) {return new BlockStateContainer(this.block, listed);}

      IUnlistedProperty<?>[] unlisted = new IUnlistedProperty[this.unlisted.size()];
      unlisted = this.unlisted.toArray(unlisted);

      return new ExtendedBlockState(this.block, listed, unlisted);
    }
  }
}
