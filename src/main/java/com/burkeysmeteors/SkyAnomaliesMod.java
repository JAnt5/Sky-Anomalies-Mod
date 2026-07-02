package com.burkeysmeteors;

import com.burkeysmeteors.entity.ModEntities;
import com.burkeysmeteors.particle.ModParticles;
import com.burkeysmeteors.sounds.ModSounds;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(SkyAnomaliesMod.MODID)
public class SkyAnomaliesMod {
    public static final String MODID = "burkeysmeteormod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SkyAnomaliesMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        ModEntities.register(modEventBus);
        ModParticles.register(modEventBus);
        ModSounds.register(modEventBus);


        NeoForge.EVENT_BUS.register(this);


        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {}

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("SkyAnomalies is loaded");
    }

}
