package org.digitalpear.sand_golem.common.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.digitalpear.sand_golem.init.SGBlocks;

public class SGModelProvider extends FabricModelProvider {
    private static final PropertyDispatch<VariantMutator> ROTATION_HORIZONTAL_FACING = PropertyDispatch.modify(BlockStateProperties.HORIZONTAL_FACING)
            .select(Direction.EAST, BlockModelGenerators.Y_ROT_90)
            .select(Direction.SOUTH, BlockModelGenerators.Y_ROT_180)
            .select(Direction.WEST, BlockModelGenerators.Y_ROT_270)
            .select(Direction.NORTH, BlockModelGenerators.NOP);

    public SGModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        createSandBlocks(blockModelGenerators, SGBlocks.SAND_LAYER, Blocks.SAND);
        createSandBlocks(blockModelGenerators, SGBlocks.RED_SAND_LAYER, Blocks.RED_SAND);

        createCarvedCactus(blockModelGenerators, SGBlocks.CARVED_CACTUS);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {

    }
    private void createSandBlocks(BlockModelGenerators blockModelGenerators, Block block, Block textureBase) {
        TextureMapping textureMapping = new TextureMapping().put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(textureBase));
        MultiVariant fullBlockModelLocation = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(textureBase));
        blockModelGenerators.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(BlockStateProperties.LAYERS).generate((height) -> {
            MultiVariant var2;
            if (height < 8) {
                var2 = BlockModelGenerators.plainVariant(SGModelTemplates.HEIGHT_TO_MODEL.get(height * 2).createWithSuffix(block, "_height" + height * 2, textureMapping, blockModelGenerators.modelOutput));
            } else {
                var2 = fullBlockModelLocation;
            }

            return var2;
        })));
        blockModelGenerators.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(block, "_height2"));
    }
    public final void createCarvedCactus(BlockModelGenerators blockModelGenerators, Block block) {
        MultiVariant multiVariant = BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(block));
        blockModelGenerators.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, multiVariant).with(ROTATION_HORIZONTAL_FACING));
    }
}
