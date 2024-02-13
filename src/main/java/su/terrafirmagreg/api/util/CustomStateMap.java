package su.terrafirmagreg.api.util;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class CustomStateMap extends StateMapperBase {

    public static File rootFolder = Launch.minecraftHome == null ? new File(".") : Launch.minecraftHome;

    private final IProperty<?> name;
    private final String suffix;
    private final List<IProperty<?>> ignored;
    private final ResourceLocation resourceLocation;

    private CustomStateMap(Builder builder) {
        this.name = builder.name;
        this.suffix = builder.suffix;
        this.ignored = builder.ignored;
        this.resourceLocation = builder.resourceLocation;
    }

    @Override
    protected @NotNull ModelResourceLocation getModelResourceLocation(IBlockState state) {
        Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap(state.getProperties());
        String s;

        if (this.name == null) {
            s = Block.REGISTRY.getNameForObject(state.getBlock()).toString();
        } else {
            s = String.format("%s:%s", Block.REGISTRY.getNameForObject(state.getBlock())
                    .getNamespace(), this.removeName(this.name, map));
        }

        if (this.suffix != null) {
            s = s + this.suffix;
        }

        for (IProperty<?> iproperty : this.ignored) {
            map.remove(iproperty);
        }


        return new ModelResourceLocation(resourceLocation, this.getPropertyString(map));


    }

//	public String getPropertyString(Map<IProperty<?>, Comparable<?>> values, String variant) {
//		StringBuilder stringbuilder = new StringBuilder();
//
//		for (Map.Entry<IProperty<?>, Comparable<?>> entry : values.entrySet()) {
//			if (stringbuilder.length() != 0) {
//				stringbuilder.append(",");
//			}
//
//			IProperty<?> iproperty = entry.getKey();
//			stringbuilder.append(iproperty.getName());
//			stringbuilder.append("=");
//			stringbuilder.append(this.getPropertyName(iproperty, entry.getValue()));
//		}
//
//		if (variant != null) {
//			if (stringbuilder.length() != 0) {
//				stringbuilder.append(",");
//			}
//			stringbuilder.append(variant);
//		}
//
//		if (stringbuilder.length() == 0) {
//			stringbuilder.append("normal");
//		}
//
//		return stringbuilder.toString();
//	}

    private <T extends Comparable<T>> String getPropertyName(IProperty<T> property, Comparable<?> value) {
        return property.getName((T) value);
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
            return new CustomStateMap(this);
        }
    }
}
