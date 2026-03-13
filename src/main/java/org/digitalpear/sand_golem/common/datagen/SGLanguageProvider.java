package org.digitalpear.sand_golem.common.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import org.digitalpear.sand_golem.init.SGBlocks;
import org.digitalpear.sand_golem.init.SGEntityType;

import java.util.concurrent.CompletableFuture;

public class SGLanguageProvider extends FabricLanguageProvider {
    public SGLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
        translationBuilder.add(SGEntityType.SAND_GOLEM, "Sand Golem");
        translationBuilder.add(SGBlocks.CARVED_CACTUS, "Carved Cactus");
        translationBuilder.add(SGBlocks.SAND_LAYER, "Sand Layer");
        translationBuilder.add(SGBlocks.RED_SAND_LAYER, "Red Sand Layer");
    }
}
