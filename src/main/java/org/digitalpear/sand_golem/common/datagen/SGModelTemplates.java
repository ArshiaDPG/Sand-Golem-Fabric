package org.digitalpear.sand_golem.common.datagen;

import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureSlot;
import org.digitalpear.sand_golem.SandGolem;

import java.util.Map;
import java.util.Optional;

public class SGModelTemplates {
    public static final ModelTemplate LAYER_HEIGHT2 = create("layer_height2", TextureSlot.TEXTURE);;
    public static final ModelTemplate LAYER_HEIGHT4 = create("layer_height4", TextureSlot.TEXTURE);;
    public static final ModelTemplate LAYER_HEIGHT6 = create("layer_height6", TextureSlot.TEXTURE);;
    public static final ModelTemplate LAYER_HEIGHT8 = create("layer_height8", TextureSlot.TEXTURE);;
    public static final ModelTemplate LAYER_HEIGHT10 = create("layer_height10", TextureSlot.TEXTURE);;
    public static final ModelTemplate LAYER_HEIGHT12 = create("layer_height12", TextureSlot.TEXTURE);;
    public static final ModelTemplate LAYER_HEIGHT14 = create("layer_height14", TextureSlot.TEXTURE);;

    public static final Map<Integer, ModelTemplate> HEIGHT_TO_MODEL = Map.of(
            2, LAYER_HEIGHT2,
            4, LAYER_HEIGHT4,
            6, LAYER_HEIGHT6,
            8, LAYER_HEIGHT8,
            10, LAYER_HEIGHT10,
            12, LAYER_HEIGHT12,
            14, LAYER_HEIGHT14
    );


    private static ModelTemplate create(String string, TextureSlot... textureSlots) {
        return new ModelTemplate(Optional.of(SandGolem.id("block/" + string)), Optional.empty(), textureSlots);
    }
}
