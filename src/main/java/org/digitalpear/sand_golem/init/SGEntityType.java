package org.digitalpear.sand_golem.init;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.digitalpear.sand_golem.SandGolem;
import org.digitalpear.sand_golem.common.sand_golem.SandGolemEntity;

public class SGEntityType {

    private static ResourceKey<EntityType<?>> entityId(String string) {
        return ResourceKey.create(Registries.ENTITY_TYPE, SandGolem.id(string));
    }

    private static <T extends Entity> EntityType<T> register(String string, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> resourceKey = entityId(string);
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, resourceKey, builder.build(resourceKey));
    }

    public static final EntityType<SandGolemEntity> SAND_GOLEM = register("sand_golem", EntityType.Builder.of(SandGolemEntity::new, MobCategory.MISC).sized(0.7F, 1.9F).eyeHeight(1.7F).clientTrackingRange(8));

    public static void init() {
        FabricDefaultAttributeRegistry.register(SAND_GOLEM, SandGolemEntity.createAttributes());
    }
}
