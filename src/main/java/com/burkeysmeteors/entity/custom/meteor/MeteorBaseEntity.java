package com.burkeysmeteors.entity.custom.meteor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class MeteorBaseEntity extends Projectile{



    public final AnimationState fallingAnimationState = new AnimationState();
    public MeteorBaseEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);

    }






    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if(!this.level().isClientSide()){
            this.destroy();
        }
    }

    private void destroy() {
        this.discard();
        this.level().gameEvent(GameEvent.ENTITY_DAMAGE, this.position(), GameEvent.Context.of(this));
    }
    private void setupAnimationStates() {


        if (!this.fallingAnimationState.isStarted()) {
            this.fallingAnimationState.start(this.tickCount);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (!this.noPhysics) {
                this.hitTargetOrDeflectSelf(hitresult);
                this.hasImpulse = true;
            }
            if(this.onGround()){
                this.onHitBlock((BlockHitResult) hitresult);
            }
            this.move(MoverType.SELF, this.getDeltaMovement());



            this.checkInsideBlocks();
        }
     if(this.level().isClientSide()) {
            this.setupAnimationStates();
        }


    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        //compound.putString("MeteorType", this.getMeteorType());

    }

    public void readAdditionalSaveData(CompoundTag compound) {
        //this.set(compound.getString("MeteorType") + 1, false);
        super.readAdditionalSaveData(compound);
    }



    public double getDistanceFromGround(){
        Level level = this.level();
        BlockPos current = this.getOnPos();

        for(int y = current.getY(); y >= level.getMinBuildHeight(); y--){
            BlockPos check = new BlockPos(current.getX(),y,current.getZ());
            BlockState state = level.getBlockState(check);

            if(!state.isAir()){
                double posY = y +1;
                return this.getY() - posY;
            }
        }
        return -1;
    }


}
