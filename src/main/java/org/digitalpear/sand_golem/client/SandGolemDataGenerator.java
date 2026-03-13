package org.digitalpear.sand_golem.client;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import org.digitalpear.sand_golem.common.datagen.SGDynamicRegistryProvider;
import org.digitalpear.sand_golem.common.datagen.SGLanguageProvider;
import org.digitalpear.sand_golem.common.datagen.SGModelProvider;
import org.digitalpear.sand_golem.init.SGRegistries;
import org.digitalpear.sand_golem.init.SandGolemVariants;

public class SandGolemDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(SGLanguageProvider::new);
        pack.addProvider(SGModelProvider::new);
        pack.addProvider(SGDynamicRegistryProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(SGRegistries.SAND_GOLEM_VARIANT, SandGolemVariants::bootstrap);
    }
}
