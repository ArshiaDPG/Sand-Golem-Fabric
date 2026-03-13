package org.digitalpear.sand_golem.client.renderers.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;

@Environment(EnvType.CLIENT)
public class SandGolemRenderState extends LivingEntityRenderState {
    public boolean hasCactus;
    public Identifier texture;

}
