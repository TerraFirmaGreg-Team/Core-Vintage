package su.terrafirmagreg.modules.wood.api.types.type;

import su.terrafirmagreg.api.spi.types.type.Type;


import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.Set;

@Getter
public class WoodType extends Type<WoodType> {

    @Getter
    private static final Set<WoodType> types = new ObjectOpenHashSet<>();

    private final String name;
    private final int color;
    private final float burnTemp;
    private final int burnTicks;
    private final boolean canMakeTannin;

    private WoodType(Builder builder) {
        super(builder.name);
        this.name = builder.name;
        this.color = builder.color;

        this.burnTemp = builder.burnTemp;
        this.burnTicks = builder.burnTicks;
        this.canMakeTannin = builder.canMakeTannin;

        if (!types.add(this)) throw new RuntimeException(String.format("WoodType: [%s] already exists!", name));
    }

    public static Builder builder(String name) {

        return new Builder(name);
    }

    public static class Builder {

        private final String name;
        private int color;
        private float burnTemp;
        private int burnTicks;
        private boolean canMakeTannin;

        public Builder(@NotNull String name) {
            this.name = name;
            this.color = 0xff000000;
            this.burnTemp = 0;
            this.burnTicks = 0;
            this.canMakeTannin = false;
        }

        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        // Установить температуру и количество тиков горения
        public Builder setBurnInfo(float burnTemp, int burnTicks) {
            this.burnTemp = burnTemp;
            this.burnTicks = burnTicks;
            return this;
        }

        // Установить возможность производить танин
        public Builder setTannin() {
            canMakeTannin = true;
            return this;
        }

        // Метод для построения объекта WoodType
        public WoodType build() {
            return new WoodType(this);
        }
    }
}
