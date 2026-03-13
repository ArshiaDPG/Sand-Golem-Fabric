package org.digitalpear.sand_golem.common.sand_golem;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.golem.AbstractGolem;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
import net.minecraft.world.entity.variant.VariantUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.phys.Vec3;
import org.digitalpear.sand_golem.SandGolem;
import org.digitalpear.sand_golem.init.SGDataComponents;
import org.digitalpear.sand_golem.init.SGRegistries;
import org.digitalpear.sand_golem.init.SandGolemVariants;
import org.jspecify.annotations.Nullable;

public class SandGolemEntity extends AbstractGolem implements Shearable, RangedAttackMob {
    private static final EntityDataAccessor<Byte> DATA_CACTUS_ID = SynchedEntityData.defineId(SandGolemEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Holder<SandGolemVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(SandGolemEntity.class, SandGolem.SAND_GOLEM_VARIANT);

    public SandGolemEntity(EntityType<? extends AbstractGolem> entityType, Level level) {
        super(entityType, level);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.25F, 20, 10.0F));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0F, 1.0000001E-5F));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Mob.class, 10, true, false, (livingEntity, serverLevel) -> livingEntity instanceof Enemy));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0F).add(Attributes.MOVEMENT_SPEED, 0.2F);
    }

    @Override
    public <T> @Nullable T get(DataComponentType<? extends T> dataComponentType) {
        return (dataComponentType == SGDataComponents.SAND_GOLEM_VARIANT ? castComponentValue(dataComponentType, this.getVariant()) : super.get(dataComponentType));
    }

    @Override
    protected <T> boolean applyImplicitComponent(DataComponentType<T> dataComponentType, T object) {
        if (dataComponentType == SGDataComponents.SAND_GOLEM_VARIANT){
            this.setVariant(castComponentValue(SGDataComponents.SAND_GOLEM_VARIANT, object));
            return true;
        }
        return super.applyImplicitComponent(dataComponentType, object);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_CACTUS_ID, (byte)16);
        builder.define(DATA_VARIANT_ID, VariantUtils.getDefaultOrAny(this.registryAccess(), SandGolemVariants.PALE));
    }

    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        valueOutput.putBoolean("Cactus", this.hasCactus());
        VariantUtils.writeVariant(valueOutput, this.getVariant());
    }

    protected void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        this.setCactus(valueInput.getBooleanOr("Cactus", true));
        VariantUtils.readVariant(valueInput, SGRegistries.SAND_GOLEM_VARIANT).ifPresent(this::setVariant);
    }

    public boolean isSensitiveToWater() {
        return true;
    }

    public void aiStep() {
        super.aiStep();
        Level var2 = this.level();
        if (var2 instanceof ServerLevel serverLevel) {
            if (!(Boolean)serverLevel.getGameRules().get(GameRules.MOB_GRIEFING)) {
                return;
            }

            BlockState blockState = getVariant().value().layerState();

            for(int i = 0; i < 4; ++i) {
                int j = Mth.floor(this.getX() + (double)((float)(i % 2 * 2 - 1) * 0.25F));
                int k = Mth.floor(this.getY());
                int l = Mth.floor(this.getZ() + (double)((float)(i / 2 % 2 * 2 - 1) * 0.25F));
                BlockPos blockPos = new BlockPos(j, k, l);
                if (this.level().getBlockState(blockPos).isAir() && blockState.canSurvive(this.level(), blockPos)) {
                    this.level().setBlockAndUpdate(blockPos, blockState);
                    this.level().gameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Context.of(this, blockState));
                }
            }
        }
    }

    public Holder<SandGolemVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    public void setVariant(Holder<SandGolemVariant> holder) {
        this.entityData.set(DATA_VARIANT_ID, holder);
    }

    public void performRangedAttack(LivingEntity livingEntity, float f) {
        double d = livingEntity.getX() - this.getX();
        double e = livingEntity.getEyeY() - (double)1.1F;
        double g = livingEntity.getZ() - this.getZ();
        double h = Math.sqrt(d * d + g * g) * (double)0.2F;
        Level var12 = this.level();
        if (var12 instanceof ServerLevel serverLevel) {
            ItemStack itemStack = new ItemStack(Items.CACTUS_FLOWER);
            Projectile.spawnProjectile(new Snowball(serverLevel, this, itemStack), serverLevel, itemStack, (snowball) -> snowball.shoot(d, e + h - snowball.getY(), g, 1.6F, 12.0F));
        }

        this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.is(Items.SHEARS) && this.readyForShearing()) {
            Level var5 = this.level();
            if (var5 instanceof ServerLevel serverLevel) {
                this.shear(serverLevel, SoundSource.PLAYERS, itemStack);
                this.gameEvent(GameEvent.SHEAR, player);
                itemStack.hurtAndBreak(1, player, interactionHand.asEquipmentSlot());
            }
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    public void shear(ServerLevel serverLevel, SoundSource soundSource, ItemStack itemStack) {
        serverLevel.playSound(null, this, SoundEvents.SNOW_GOLEM_SHEAR, soundSource, 1.0F, 1.0F);
        this.setCactus(false);
        this.dropFromShearingLootTable(serverLevel, BuiltInLootTables.SHEAR_SNOW_GOLEM, itemStack, (serverLevelx, itemStackx) -> this.spawnAtLocation(serverLevelx, itemStackx, this.getEyeHeight()));
    }

    public boolean readyForShearing() {
        return this.isAlive() && this.hasCactus();
    }

    public boolean hasCactus() {
        return (this.entityData.get(DATA_CACTUS_ID) & 16) != 0;
    }

    public void setCactus(boolean bl) {
        byte b = this.entityData.get(DATA_CACTUS_ID);
        if (bl) {
            this.entityData.set(DATA_CACTUS_ID, (byte)(b | 16));
        } else {
            this.entityData.set(DATA_CACTUS_ID, (byte)(b & -17));
        }

    }

    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.SNOW_GOLEM_AMBIENT;
    }

    protected @Nullable SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SNOW_GOLEM_HURT;
    }

    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.SNOW_GOLEM_DEATH;
    }

    public Vec3 getLeashOffset() {
        return new Vec3(0.0F, 0.75F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }
}
