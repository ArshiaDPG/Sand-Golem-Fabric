package org.digitalpear.sand_golem.init;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.digitalpear.sand_golem.SandGolem;
import org.digitalpear.sand_golem.common.blocks.CarvedCactusBlock;

import java.util.function.Function;

public class SGBlocks {

    private static Block register(String string, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties properties){
        Block block = registerWithoutItem(string, function, properties);
        Items.registerBlock(block);
        return block;
    }

    private static Block registerWithoutItem(String string, Function<BlockBehaviour.Properties, Block> function, BlockBehaviour.Properties properties){
        return Blocks.register(ResourceKey.create(Registries.BLOCK, SandGolem.id(string)), function, properties);
    }

    public static final Block CARVED_CACTUS = register("carved_cactus", CarvedCactusBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CACTUS));
    public static final Block SAND_LAYER = register("sand_layer", SnowLayerBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.SAND).replaceable().forceSolidOff().randomTicks().strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.SAND).isViewBlocking((blockStatex, blockGetter, blockPos) -> blockStatex.getValue(SnowLayerBlock.LAYERS) >= 8).pushReaction(PushReaction.DESTROY));
    public static final Block RED_SAND_LAYER = register("red_sand_layer", SnowLayerBlock::new, BlockBehaviour.Properties.of().mapColor(Blocks.RED_SAND.defaultMapColor()).replaceable().forceSolidOff().randomTicks().strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.SAND).isViewBlocking((blockStatex, blockGetter, blockPos) -> blockStatex.getValue(SnowLayerBlock.LAYERS) >= 8).pushReaction(PushReaction.DESTROY));


    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
            entries.addAfter(Items.SAND, SAND_LAYER);
            entries.addAfter(Items.RED_SAND, RED_SAND_LAYER);
            entries.addBefore(Items.CARVED_PUMPKIN, CARVED_CACTUS);
        });
    }
}
