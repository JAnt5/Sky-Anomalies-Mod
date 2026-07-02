package com.burkeysmeteors;

import com.burkeysmeteors.entity.ModEntities;
import com.burkeysmeteors.entity.client.meteorclient.MeteorRenderer;
import com.burkeysmeteors.particle.MeteorChunkParticle;
import com.burkeysmeteors.particle.MeteorDustParticle;
import com.burkeysmeteors.particle.ModParticles;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = SkyAnomaliesMod.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = SkyAnomaliesMod.MODID, value = Dist.CLIENT)
public class SkyAnomaliesModClient {
    public SkyAnomaliesModClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.STANDARD_METEOR.get(), MeteorRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticleProvider(RegisterParticleProvidersEvent event){
        event.registerSpriteSet(ModParticles.METEOR_DUST_PARTICLES.get(), MeteorDustParticle.Provider::new);
        event.registerSpriteSet(ModParticles.METEOR_CHUNK_PARTICLES.get(), MeteorChunkParticle.Provider::new);

    }
}
