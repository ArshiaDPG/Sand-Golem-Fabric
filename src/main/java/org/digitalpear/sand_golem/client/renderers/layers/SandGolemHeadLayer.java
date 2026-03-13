package org.digitalpear.sand_golem.client.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.animal.golem.SnowGolemModel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.world.level.block.state.BlockState;
import org.digitalpear.sand_golem.client.renderers.state.SandGolemRenderState;
import org.digitalpear.sand_golem.init.SGBlocks;

public class SandGolemHeadLayer extends RenderLayer<SandGolemRenderState, SnowGolemModel> {
    private final BlockRenderDispatcher blockRenderer;

    public SandGolemHeadLayer(RenderLayerParent<SandGolemRenderState, SnowGolemModel> renderLayerParent, BlockRenderDispatcher blockRenderDispatcher) {
        super(renderLayerParent);
        this.blockRenderer = blockRenderDispatcher;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int i, SandGolemRenderState sandGolemRenderState, float f, float g) {
        if (sandGolemRenderState.hasCactus) {
            if (!sandGolemRenderState.isInvisible || sandGolemRenderState.appearsGlowing()) {
                poseStack.pushPose();
                this.getParentModel().getHead().translateAndRotate(poseStack);
                float blockScale = 0.625F;
                poseStack.translate(0.0F, -0.34375F, 0.0F);
                poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
                poseStack.scale(blockScale, -blockScale, -blockScale);
                BlockState blockState = SGBlocks.CARVED_CACTUS.defaultBlockState();
                BlockStateModel blockStateModel = this.blockRenderer.getBlockModel(blockState);
                int j = LivingEntityRenderer.getOverlayCoords(sandGolemRenderState, 0.0F);
                poseStack.translate(-0.5F, -0.5F, -0.5F);
                RenderType renderType = sandGolemRenderState.appearsGlowing() && sandGolemRenderState.isInvisible ? RenderTypes.outline(TextureAtlas.LOCATION_BLOCKS) : ItemBlockRenderTypes.getRenderType(blockState);
                submitNodeCollector.submitBlockModel(poseStack, renderType, blockStateModel, 0.0F, 0.0F, 0.0F, i, j, sandGolemRenderState.outlineColor);
                poseStack.popPose();
            }
        }
    }
}
