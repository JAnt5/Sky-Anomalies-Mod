package com.burkeysmeteors.sounds;

import com.burkeysmeteors.Burkeysmeteormod;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds{
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Burkeysmeteormod.MODID);

    public static final Supplier<SoundEvent> METEOR_CRASH_SMALL = registerSoundEvent("meteor_crash_small");
    public static final Supplier<SoundEvent> METEOR_FLYBY = registerSoundEvent("meteor_flyby");

    private static Supplier<SoundEvent> registerSoundEvent(String name){
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Burkeysmeteormod.MODID,name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }


    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}
