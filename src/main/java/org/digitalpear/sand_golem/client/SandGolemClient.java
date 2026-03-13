package org.digitalpear.sand_golem.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.animal.golem.SnowGolemModel;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import org.digitalpear.sand_golem.client.renderers.SandGolemRenderer;
import org.digitalpear.sand_golem.init.SGBlocks;
import org.digitalpear.sand_golem.init.SGEntityType;

public class SandGolemClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(SGModelLayers.SAND_GOLEM, SnowGolemModel::createBodyLayer);

        EntityRendererRegistry.register(SGEntityType.SAND_GOLEM, SandGolemRenderer::new);

        BlockRenderLayerMap.putBlock(SGBlocks.CARVED_CACTUS, ChunkSectionLayer.CUTOUT);
    }
}
