package org.digitalpear.sand_golem.common.sand_golem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.digitalpear.sand_golem.init.SGRegistries;

public record SandGolemVariant(Identifier texture, BlockState layerState, BlockState summoningState) {
    public static final Codec<SandGolemVariant> DIRECT_CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    Identifier.CODEC.fieldOf("texture").forGetter(SandGolemVariant::texture),
                    BlockState.CODEC.fieldOf("layer_state").forGetter(SandGolemVariant::layerState),
                    BlockState.CODEC.fieldOf("summoning_state").forGetter(SandGolemVariant::summoningState)
            ).apply(instance, SandGolemVariant::new));
    public static final Codec<SandGolemVariant> NETWORK_CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    Identifier.CODEC.fieldOf("texture").forGetter(SandGolemVariant::texture)
            ).apply(instance, SandGolemVariant::new));

    public static final Codec<Holder<SandGolemVariant>> CODEC = RegistryFixedCodec.create(SGRegistries.SAND_GOLEM_VARIANT);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SandGolemVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(SGRegistries.SAND_GOLEM_VARIANT);

    public SandGolemVariant(Identifier texture){
        this(texture, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState());
    }
}
