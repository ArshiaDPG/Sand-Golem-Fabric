package org.digitalpear.sand_golem.init;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.digitalpear.sand_golem.SandGolem;
import org.digitalpear.sand_golem.common.sand_golem.SandGolemVariant;

public class SandGolemVariants {

    public static final ResourceKey<SandGolemVariant> PALE = createKey("pale");
    public static final ResourceKey<SandGolemVariant> RED = createKey("red");

    private static ResourceKey<SandGolemVariant> createKey(String string) {
        return ResourceKey.create(SGRegistries.SAND_GOLEM_VARIANT, SandGolem.id(string));
    }

    private static void register(BootstrapContext<SandGolemVariant> bootstrapContext, ResourceKey<SandGolemVariant> resourceKey, BlockState state, BlockState summoningState) {
        register(bootstrapContext, resourceKey, SandGolem.id("textures/entity/sand_golem/").withSuffix(resourceKey.identifier().getPath()), state, summoningState);
    }

    private static void register(BootstrapContext<SandGolemVariant> bootstrapContext, ResourceKey<SandGolemVariant> resourceKey, Identifier textureLocation, BlockState layerState, BlockState summoningState) {
        bootstrapContext.register(resourceKey, new SandGolemVariant(textureLocation, layerState, summoningState));
    }

    public static void bootstrap(BootstrapContext<SandGolemVariant> bootstrapContext) {
        register(bootstrapContext, PALE, SGBlocks.SAND_LAYER.defaultBlockState(), Blocks.SAND.defaultBlockState());
        register(bootstrapContext, RED, SGBlocks.RED_SAND_LAYER.defaultBlockState(), Blocks.RED_SAND.defaultBlockState());
    }

    public static void init() {

    }
}
