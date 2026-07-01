package com.burkeysmeteors.entity;

import com.burkeysmeteors.Burkeysmeteormod;
import com.burkeysmeteors.entity.custom.meteor.MeteorBaseEntity;
import com.burkeysmeteors.entity.custom.meteor.StandardMeteorEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Burkeysmeteormod.MODID);


    public static final Supplier<EntityType<StandardMeteorEntity>> STANDARD_METEOR =
            ENTITY_TYPES.register("standardmeteor", () -> EntityType.Builder.of(StandardMeteorEntity::new, MobCategory.MISC)
                    .sized(3f, 3f).setTrackingRange(256).updateInterval(1).build("standardmeteor"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }

}
