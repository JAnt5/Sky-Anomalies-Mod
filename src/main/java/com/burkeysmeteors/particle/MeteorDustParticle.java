package com.burkeysmeteors.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class MeteorDustParticle extends TextureSheetParticle {
    SpriteSet spriteSet;
    protected MeteorDustParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.friction = 0.96F;
        this.gravity = -0.4f;
        this.speedUpWhenYMotionIsBlocked = true;
        this.spriteSet = spriteSet;
        this.xd *= (double)zSpeed;
        this.yd *= (double)ySpeed;
        this.zd *= (double)zSpeed;
        this.xd += xSpeed;
        this.yd += ySpeed;
        this.zd += zSpeed;
        this.quadSize *= 2.75F;
        this.lifetime = (int)((double)lifetime / ((double)level.random.nextFloat() * 0.8 + 0.2));
        this.lifetime = Math.max(this.lifetime, 1);
        this.setSpriteFromAge(spriteSet);
        this.hasPhysics = true;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ < this.lifetime && !(this.alpha <= 0.0F)) {
            this.setSpriteFromAge(spriteSet);
            if (this.age >= this.lifetime - (this.lifetime / 2) && this.alpha > 0.01F) {
                this.alpha -= 0.015F;
            }
        } else {
            this.remove();

        }

    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet){
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double pX, double pY, double pZ, double vX, double vY, double vZ) {
            MeteorDustParticle meteorDustParticle = new MeteorDustParticle(clientLevel,pX,pY,pZ,this.spriteSet,vX,vY,vZ);
            meteorDustParticle.setAlpha(0.95f);
            return meteorDustParticle;
        }
    }

    public int getLightColor(float partialTick) {
        int i = super.getLightColor(partialTick);
        int j = 240;
        int k = i >> 16 & 255;
        return 240 | k << 16;
    }

}
