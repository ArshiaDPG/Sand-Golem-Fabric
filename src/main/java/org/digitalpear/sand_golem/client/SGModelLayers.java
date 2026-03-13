package org.digitalpear.sand_golem.client;

import net.fabricmc.fabric.mixin.client.rendering.ModelLayersAccessor;
import net.minecraft.client.model.geom.ModelLayerLocation;
import org.digitalpear.sand_golem.SandGolem;

public class SGModelLayers {
    public static final ModelLayerLocation SAND_GOLEM = register("sand_golem");

    private static ModelLayerLocation register(String string) {
        return register(string, "main");
    }

    private static ModelLayerLocation register(String string, String string2) {
        ModelLayerLocation modelLayerLocation = createLocation(string, string2);
        if (!ModelLayersAccessor.getLayers().add(modelLayerLocation)) {
            throw new IllegalStateException("Duplicate registration for " + modelLayerLocation);
        } else {
            return modelLayerLocation;
        }
    }
    private static ModelLayerLocation createLocation(String string, String string2) {
        return new ModelLayerLocation(SandGolem.id(string), string2);
    }

}
