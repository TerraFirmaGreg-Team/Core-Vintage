package net.dries007.tfc.client;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CustomStateMap extends StateMapperBase {

    private final IProperty<?> name;
    private final String suffix;
    private final List<IProperty<?>> ignored;
    private final ResourceLocation customModelPath;

    private CustomStateMap(@Nullable IProperty<?> name, @Nullable String suffix, List<IProperty<?>> ignored, ResourceLocation customModelPath) {
        this.name = name;
        this.suffix = suffix;
        this.ignored = ignored;
        this.customModelPath = customModelPath;
    }

    protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
        Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap(state.getProperties());
        String s;

        if (this.name == null) {
            s = Block.REGISTRY.getNameForObject(state.getBlock()).toString();
        } else {
            s = String.format("%s:%s", Block.REGISTRY.getNameForObject(state.getBlock()).getNamespace(), this.removeName(this.name, map));
        }

        if (this.suffix != null) {
            s = s + this.suffix;
        }

        for (IProperty<?> iproperty : this.ignored) {
            map.remove(iproperty);
        }

        return new ModelResourceLocation(customModelPath, this.getPropertyString(map));
    }

    private <T extends Comparable<T>> String removeName(IProperty<T> property, Map<IProperty<?>, Comparable<?>> values) {
        return property.getName((T) values.remove(this.name));
    }

    @SideOnly(Side.CLIENT)
    public static class Builder {
        private final List<IProperty<?>> ignored = Lists.newArrayList();
        private IProperty<?> name;
        private String suffix;
        private ResourceLocation resourceLocation;

        public Builder withName(IProperty<?> builderPropertyIn) {
            this.name = builderPropertyIn;
            return this;
        }

        public Builder withSuffix(String builderSuffixIn) {
            this.suffix = builderSuffixIn;
            return this;
        }

        public Builder ignore(IProperty<?>... ignores) {
            Collections.addAll(this.ignored, ignores);
            return this;
        }

        public Builder customPath(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
            return this;
        }

        public CustomStateMap build() {
            return new CustomStateMap(this.name, this.suffix, this.ignored, this.resourceLocation);
        }
    }
}
