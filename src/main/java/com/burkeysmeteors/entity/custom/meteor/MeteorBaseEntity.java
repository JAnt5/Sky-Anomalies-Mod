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
import net.minecraft.world.phys.Vec3;

public class MeteorBaseEntity extends Projectile{


    BlockPos anchorPoint;

    public final AnimationState fallingAnimationState = new AnimationState();
    public MeteorBaseEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
        this.anchorPoint = BlockPos.ZERO;

    }

    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if(!this.level().isClientSide()){
            this.destroy();
        }
    }

    public void destroy() {
        this.level().gameEvent(GameEvent.ENTITY_DAMAGE, this.position(), GameEvent.Context.of(this));
        this.discard();
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


            this.checkInsideBlocks();
        }
        else{
            this.setupAnimationStates();
        }
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.setPos(d0, d1, d2);


    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("AX", this.anchorPoint.getX());
        compound.putInt("AY", this.anchorPoint.getY());
        compound.putInt("AZ", this.anchorPoint.getZ());

    }

    public void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("AX")) {
            this.anchorPoint = new BlockPos(compound.getInt("AX"), compound.getInt("AY"), compound.getInt("AZ"));
        }
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

    public BlockPos getAnchorPoint() {
        return anchorPoint;
    }

    public void setAnchorPoint(BlockPos anchorPoint) {
        this.anchorPoint = anchorPoint;
    }


}
