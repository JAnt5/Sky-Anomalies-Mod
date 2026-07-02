package com.burkeysmeteors.entity.custom.meteor;

import com.burkeysmeteors.Config;
import com.burkeysmeteors.particle.ModParticles;
import com.burkeysmeteors.sounds.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public class StandardMeteorEntity extends MeteorBaseEntity{


    private boolean flewBy;
    public StandardMeteorEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);

        flewBy = false;
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
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

        if(!this.level().isClientSide()){
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    ModSounds.METEOR_CRASH_SMALL.get(), SoundSource.NEUTRAL, 3+ getSize(), (1 + (float) 1 / this.getSize()));

            //((ServerLevel)this.level()).sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getY(), this.getZ(), 50, 0.2, 0.2, 0.2, 0.5);
            ((ServerLevel)this.level()).sendParticles(ModParticles.METEOR_CHUNK_PARTICLES.get(), this.getX(), this.getY(), this.getZ(), 20, 0.2, 0.2, 0.2, 0.2);

            if(this.getSize() >= 4 || Config.SHOULD_METEORS_OF_ALL_SIZES_EXPLODE.getAsBoolean()){
                this.explode();
            }
        }

    }

    protected void explode() {

        float f = (float) (1.5 * getSize());
        this.level().explode(this, this.getX(), this.getY(0.0625F), this.getZ(), f, Level.ExplosionInteraction.TNT);

    }
    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()) {
            double offsetX = (this.random.nextDouble() - 0.5) * this.getBbWidth();
            double offsetY = this.random.nextDouble() * this.getBbHeight();
            double offsetZ = (this.random.nextDouble() - 0.5) * this.getBbWidth();
            for(int i = 0; i < 10; i++){
                this.level().addAlwaysVisibleParticle(ModParticles.METEOR_DUST_PARTICLES.get(),true,this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ, 0.2 * (-1*this.getMotionDirection().getStepX()), 0.2* (-1*this.getMotionDirection().getStepY()), 0.2* (-1*this.getMotionDirection().getStepZ()));

            }
            if(this.getDistanceFromGround() < 1){
                    for(int i = 0; i < 15 * this.getSize(); i++){
                        Random r = new Random();
                        double xOffset = r.nextGaussian() *  ( random.nextBoolean() ? 1 : -1 );
                        double yOffset = r.nextGaussian() *  ( random.nextBoolean() ? 1 : -1 );
                        double zOffset = r.nextGaussian() *  ( random.nextBoolean() ? 1 : -1 );


                        level().addAlwaysVisibleParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,true,this.getX() + xOffset,this.getY() +yOffset,this.getZ() +zOffset, 0.5f + xOffset,0.5f +yOffset,0.5f+zOffset);

                    }

            }

        }else{
            if(!isFlewBy()){
                Player player = this.level().getNearestPlayer(this, 48);
                if(player != null){
                    double deltaY = player.getY() - this.getY();
                    double verticalDist = Math.sqrt(deltaY * deltaY);
                        if(verticalDist <= 20){
                            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                                    ModSounds.METEOR_FLYBY.get(), SoundSource.NEUTRAL, 3.0f, (1 + (float) 1 / this.getSize()));
                            setFlewBy(true);
                        }
                    }
            }
        }
    }


    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (ID_SIZE.equals(key)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(key);
    }
    public boolean isFlewBy() {
        return flewBy;
    }

    public void setFlewBy(boolean flewBy) {
        this.flewBy = flewBy;
    }



}
