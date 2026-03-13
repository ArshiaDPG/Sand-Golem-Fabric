package org.digitalpear.sand_golem.client.renderers;

import net.minecraft.client.model.animal.golem.SnowGolemModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.digitalpear.sand_golem.SandGolem;
import org.digitalpear.sand_golem.client.SGModelLayers;
import org.digitalpear.sand_golem.client.renderers.layers.SandGolemHeadLayer;
import org.digitalpear.sand_golem.client.renderers.state.SandGolemRenderState;
import org.digitalpear.sand_golem.common.sand_golem.SandGolemEntity;

public class SandGolemRenderer extends MobRenderer<SandGolemEntity, SandGolemRenderState, SnowGolemModel> {
    private static final Identifier TEXTURE = SandGolem.id("textures/entity/pale.png");

    public SandGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new SnowGolemModel(context.bakeLayer(SGModelLayers.SAND_GOLEM)), 0.5F);
        this.addLayer(new SandGolemHeadLayer(this, context.getBlockRenderDispatcher()));
    }

    public Identifier getTextureLocation(SandGolemRenderState sandGolemRenderState) {
        return sandGolemRenderState.texture;
    }

    public SandGolemRenderState createRenderState() {
        return new SandGolemRenderState();
    }

    public void extractRenderState(SandGolemEntity sandGolem, SandGolemRenderState sandGolemRenderState, float f) {
        super.extractRenderState(sandGolem, sandGolemRenderState, f);
        sandGolemRenderState.hasCactus = sandGolem.hasCactus();
        sandGolemRenderState.texture = sandGolem.getVariant().value().texture();
    }
}
