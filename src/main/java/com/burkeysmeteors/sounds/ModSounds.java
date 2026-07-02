package com.burkeysmeteors.sounds;

import com.burkeysmeteors.SkyAnomaliesMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds{
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, SkyAnomaliesMod.MODID);

    public static final Supplier<SoundEvent> METEOR_CRASH_SMALL = registerSoundEvent("meteor_crash_small");
    public static final Supplier<SoundEvent> METEOR_FLYBY = registerSoundEvent("meteor_flyby");

    private static Supplier<SoundEvent> registerSoundEvent(String name){
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(SkyAnomaliesMod.MODID,name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }


    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}
