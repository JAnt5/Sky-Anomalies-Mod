package com.burkeysmeteors.particle;

import com.burkeysmeteors.SkyAnomaliesMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, SkyAnomaliesMod.MODID);

    public static final Supplier<SimpleParticleType> METEOR_DUST_PARTICLES = PARTICLE_TYPES.register("meteor_dust_particles",() -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> METEOR_CHUNK_PARTICLES = PARTICLE_TYPES.register("meteor_chunk_particles",() -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}
