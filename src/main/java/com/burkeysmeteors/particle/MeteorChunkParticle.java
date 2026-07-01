package com.burkeysmeteors.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class MeteorChunkParticle extends TextureSheetParticle {
    protected MeteorChunkParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.gravity = 0.75F;
        this.friction = 0.999F;
        this.xd *= (double)0.8F;
        this.yd *= (double)0.8F;
        this.zd *= (double)0.8F;
        this.yd = (double)(this.random.nextFloat() * 0.4F + 0.05F);
        this.quadSize *= this.random.nextFloat() * 2.0F + 0.2F;
        this.lifetime = (int)((double)16.0F / (Math.random() * 0.8 + 0.2));
        this.setSize(1F, 1F);
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet){
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double pX, double pY, double pZ, double vX, double vY, double vZ) {
            return new MeteorChunkParticle(clientLevel,pX,pY,pZ,this.spriteSet,vX,vY,vZ);
        }
    }
}
