package com.burkeysmeteors.entity.custom.meteor;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class StandardMeteorEntity extends MeteorBaseEntity{
    public StandardMeteorEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }
    private static final EntityDataAccessor<Integer> ID_SIZE;
    static {
        ID_SIZE = SynchedEntityData.defineId(StandardMeteorEntity.class, EntityDataSerializers.INT);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ID_SIZE, 1);
    }
    public int getSize() {
        return this.entityData.get(ID_SIZE);
    }

    public void setSize(int size) {
        this.entityData.set(ID_SIZE,size);
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Size", this.getSize() - 1);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        this.setSize(compound.getInt("Size") + 1);
        super.readAdditionalSaveData(compound);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);



    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if(!this.level().isClientSide()){
            ((ServerLevel)this.level()).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getY(), this.getZ(), 50, 0.2, 0.2, 0.2, 0.8);
            ((ServerLevel)this.level()).sendParticles(ParticleTypes.DUST_PLUME, this.getX(), this.getY(), this.getZ(), 20, 0.2, 0.2, 0.2, 0.2);


            if(this.getSize() >= 5){
                this.explode();
            }
        }
    }

    protected void explode() {
        float f = 4.0F;
        this.level().explode(this, this.getX(), this.getY((double)0.0625F), this.getZ(), 4.0F, Level.ExplosionInteraction.TNT);
    }
}
