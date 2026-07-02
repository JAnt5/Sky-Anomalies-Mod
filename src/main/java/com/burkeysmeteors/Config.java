package com.burkeysmeteors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;


public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue SHOULD_METEORS_SPAWN_NATURALLY = BUILDER
            .comment("If meteors should spawn naturally")
            .define("meteorSpawning", true);

    public static final ModConfigSpec.BooleanValue SHOULD_METEORS_OF_ALL_SIZES_EXPLODE = BUILDER
            .comment("If meteors should explode no matter their size")
            .define("meteorExplosions", false);


    public static final ModConfigSpec.IntValue METEOR_SPAWN_SIZE_MIN = BUILDER
            .comment("Defines the MINIMUM size for meteors (1-5), meteors from 1-3 are not explosive")
            .defineInRange("meteorSpawnSizeMin", 1, 1, 5);

    public static final ModConfigSpec.IntValue METEOR_SPAWN_SIZE_MAX = BUILDER
            .comment("Defines the MAXIMUM size for meteors (1-5), meteors from 1-3 are not explosive")
            .defineInRange("meteorSpawnSizeMax", 3, 1, 5);


    public static final ModConfigSpec.IntValue METEOR_SPAWN_FREQUENCY = BUILDER
            .comment("Defines the FREQUENCY at which meteors spawn default (36000) is about 0.5h ")
            .defineInRange("meteorSpawnFrequency",36000,1, 10000000 );
    static final ModConfigSpec SPEC = BUILDER.build();

}
