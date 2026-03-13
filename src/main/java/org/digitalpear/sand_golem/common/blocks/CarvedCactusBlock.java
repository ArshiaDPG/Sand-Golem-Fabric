package org.digitalpear.sand_golem.common.blocks;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.digitalpear.sand_golem.common.sand_golem.SandGolemEntity;
import org.digitalpear.sand_golem.common.sand_golem.SandGolemVariant;
import org.digitalpear.sand_golem.init.SGEntityType;
import org.digitalpear.sand_golem.init.SGRegistries;

public class CarvedCactusBlock extends CactusBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public CarvedCactusBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    protected void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        super.onPlace(blockState, level, blockPos, blockState2, bl);
        if (level instanceof ServerLevel serverLevel){
            Registry<SandGolemVariant> lookup = serverLevel.registryAccess().lookupOrThrow(SGRegistries.SAND_GOLEM_VARIANT);
            for (SandGolemVariant sandGolemVariant : lookup.stream().toList()) {
                if (sameState(level.getBlockState(blockPos.below()), sandGolemVariant.summoningState()) && sameState(level.getBlockState(blockPos.below(2)), sandGolemVariant.summoningState())) {
                    SandGolemEntity sandGolem = SGEntityType.SAND_GOLEM.create(level, EntitySpawnReason.TRIGGERED);
                    if (sandGolem == null){
                        return;
                    }
                    spawnGolemInWorld(level, sandGolem, blockPos.below(2));

                    lookup.getResourceKey(sandGolemVariant).ifPresent(sandGolemVariantResourceKey -> {
                        sandGolem.setVariant(lookup.getOrThrow(sandGolemVariantResourceKey));
                    });
                    return;
                }
            }
        }
    }

    public boolean sameState(BlockState state1, BlockState state2){
        if (!state1.getBlock().equals(state2.getBlock())){
            return false;
        }
        return state1.getValues().equals(state2.getValues());
    }

    private static void spawnGolemInWorld(Level level, Entity entity, BlockPos blockPos) {
        level.destroyBlock(blockPos, false);
        level.destroyBlock(blockPos.below(), false);
        level.destroyBlock(blockPos.below(2), false);
        entity.snapTo((double)blockPos.getX() + (double)0.5F, (double)blockPos.getY() + 0.05, (double)blockPos.getZ() + (double)0.5F, 0.0F, 0.0F);
        level.addFreshEntity(entity);

        for(ServerPlayer serverPlayer : level.getEntitiesOfClass(ServerPlayer.class, entity.getBoundingBox().inflate(5.0F))) {
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, entity);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.getHorizontalDirection().getOpposite());
    }

    protected BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    protected BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }
}
