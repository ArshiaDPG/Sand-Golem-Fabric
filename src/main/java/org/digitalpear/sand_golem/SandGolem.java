package org.digitalpear.sand_golem;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.BlockEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricTrackedDataRegistry;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import org.digitalpear.sand_golem.common.sand_golem.SandGolemVariant;
import org.digitalpear.sand_golem.init.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SandGolem implements ModInitializer {

    public static final String MOD_ID = "sand_golem";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier id(String name){
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }
    public static final EntityDataSerializer<Holder<SandGolemVariant>> SAND_GOLEM_VARIANT = EntityDataSerializer.forValueType(SandGolemVariant.STREAM_CODEC);


    @Override
    public void onInitialize() {
        FabricTrackedDataRegistry.register(id("sheep_variant"), SAND_GOLEM_VARIANT);

        DynamicRegistries.register(SGRegistries.SAND_GOLEM_VARIANT, SandGolemVariant.DIRECT_CODEC);

        SGBlocks.init();
        SGEntityType.init();
        SGRegistries.init();
        SandGolemVariants.init();
        SGDataComponents.init();

        BlockEvents.USE_ITEM_ON.register((itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult) -> {
            if (itemStack.is(ConventionalItemTags.SHEAR_TOOLS) && blockState.is(Blocks.CACTUS)){
                if (level instanceof ServerLevel serverLevel){
                    Direction direction = blockHitResult.getDirection();
                    Direction direction2 = direction.getAxis() == Direction.Axis.Y ? player.getDirection().getOpposite() : direction;
//                    dropFromBlockInteractLootTable(serverLevel, BuiltInLootTables.CARVE_PUMPKIN, blockState, level.getBlockEntity(blockPos), itemStack, player, (serverLevelx, itemStackx) -> {
//                        ItemEntity itemEntity = new ItemEntity(level, (double)blockPos.getX() + (double)0.5F + (double)direction2.getStepX() * 0.65, (double)blockPos.getY() + 0.1, (double)blockPos.getZ() + (double)0.5F + (double)direction2.getStepZ() * 0.65, itemStackx);
//                        itemEntity.setDeltaMovement(0.05 * (double)direction2.getStepX() + level.random.nextDouble() * 0.02, 0.05, 0.05 * (double)direction2.getStepZ() + level.random.nextDouble() * 0.02);
//                        level.addFreshEntity(itemEntity);
//                    });
                    level.playSound(null, blockPos, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.setBlock(blockPos, SGBlocks.CARVED_CACTUS.defaultBlockState().setValue(CarvedPumpkinBlock.FACING, direction2), 11);
                    itemStack.hurtAndBreak(1, player, interactionHand.asEquipmentSlot());
                    level.gameEvent(player, GameEvent.SHEAR, blockPos);
                    player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        });
    }
}
