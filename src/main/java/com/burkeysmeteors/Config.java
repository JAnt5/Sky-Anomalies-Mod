package com.burkeysmeteors;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue SHOULD_METEORS_SPAWN_NATURALLY = BUILDER
            .comment("If meteors should spawn natrually")
            .define("meteorSpawning", true);

    public static final ModConfigSpec.IntValue METEOR_SPAWN_SIZE_MIN = BUILDER
            .comment("Defines the MINIMUM size for meteors (1-5), meteors from 1-3 are not explosive")
            .defineInRange("meteorSpawnSizeMin", 1, 1, 5);

    public static final ModConfigSpec.IntValue METEOR_SPAWN_SIZE_MAX = BUILDER
            .comment("Defines the MAXIMUM size for meteors (1-5), meteors from 1-3 are not explosive")
            .defineInRange("meteorSpawnSizeMax", 3, 1, 5);


    public static final ModConfigSpec.IntValue METEOR_SPAWN_FREQUENCY = BUILDER
            .comment("Defines the FREQUENCY at which meteors spawn default (36000) is 0.5h ")
            .defineInRange("meteorSpawnFrequency",36000,1, 10000000 );

    public static final ModConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
            .comment("What you want the introduction message to be for the magic number")
            .define("magicNumberIntroduction", "The magic number is... ");

    // a list of strings that are treated as resource locations for items
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
            .comment("A list of items to log on common setup.")
            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), () -> "", Config::validateItemName);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }
}
