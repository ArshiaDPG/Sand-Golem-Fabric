package org.digitalpear.sand_golem.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import org.digitalpear.sand_golem.SandGolem;
import org.digitalpear.sand_golem.common.sand_golem.SandGolemVariant;

import java.util.function.UnaryOperator;

public class SGDataComponents {
    public static final DataComponentType<Holder<SandGolemVariant>> SAND_GOLEM_VARIANT = register("sand_golem/variant", (builder) -> builder.persistent(SandGolemVariant.CODEC).networkSynchronized(SandGolemVariant.STREAM_CODEC));

    private static <T> DataComponentType<T> register(String string, UnaryOperator<DataComponentType.Builder<T>> unaryOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, SandGolem.id(string), unaryOperator.apply(DataComponentType.builder()).build());
    }

    public static void init() {

    }
}
