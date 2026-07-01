package com.burkeysmeteors.event;


import com.burkeysmeteors.Burkeysmeteormod;
import com.burkeysmeteors.Config;
import com.burkeysmeteors.commands.SpawnMeteorCommand;
import com.burkeysmeteors.entity.client.meteorclient.MeteorModel;
import com.burkeysmeteors.entity.custom.meteor.MeteorTypes;
import com.burkeysmeteors.entity.util.SkyAnomalyUtils;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.level.ChunkWatchEvent;

@EventBusSubscriber(modid = Burkeysmeteormod.MODID)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MeteorModel.LAYER_LOCATION, MeteorModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event){
        SpawnMeteorCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void spawnMeteor(ChunkWatchEvent.Watch event){

        if(Config.SHOULD_METEORS_SPAWN_NATURALLY.get()){
            ServerLevel world = event.getLevel();
            if(world.getRandom().nextIntBetweenInclusive(0, Config.METEOR_SPAWN_FREQUENCY.getAsInt()) == 0){
                SkyAnomalyUtils.spawnMeteor(world, event.getPlayer(), MeteorTypes.STANDARD,
                        event.getLevel().getRandom().nextIntBetweenInclusive(Config.METEOR_SPAWN_SIZE_MIN.getAsInt(), Config.METEOR_SPAWN_SIZE_MAX.getAsInt()));
            }
        }

    }





}
