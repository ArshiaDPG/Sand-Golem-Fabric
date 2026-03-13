package org.digitalpear.sand_golem.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.digitalpear.sand_golem.common.sand_golem.SandGolemVariant;

public class SGRegistries {
    public static final ResourceKey<Registry<SandGolemVariant>> SAND_GOLEM_VARIANT = createRegistryKey("sand_golem_variant");

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String string) {
        return ResourceKey.createRegistryKey(Identifier.withDefaultNamespace(string));
    }

    public static void init() {

    }
}
