package org.digitalpear.sand_golem.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import org.digitalpear.sand_golem.init.SGRegistries;

import java.util.concurrent.CompletableFuture;

public class SGDynamicRegistryProvider extends FabricDynamicRegistryProvider {
    public SGDynamicRegistryProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        entries.addAll(registries.lookupOrThrow(SGRegistries.SAND_GOLEM_VARIANT));
    }

    @Override
    public String getName() {
        return "Dynamic Registry Provider";
    }
}
