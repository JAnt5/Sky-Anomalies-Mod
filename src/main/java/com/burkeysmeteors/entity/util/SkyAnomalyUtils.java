package com.burkeysmeteors.entity.util;

import com.burkeysmeteors.entity.ModEntities;
import com.burkeysmeteors.entity.custom.meteor.MeteorBaseEntity;
import com.burkeysmeteors.entity.custom.meteor.MeteorTypes;
import com.burkeysmeteors.entity.custom.meteor.StandardMeteorEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;


public class SkyAnomalyUtils {
    public static void spawnMeteor(ServerLevel world, Player player, MeteorTypes meteorType){

        StandardMeteorEntity meteor = new StandardMeteorEntity(ModEntities.STANDARD_METEOR.get(),world);
        int rand = world.getRandom().nextIntBetweenInclusive(50, 100);
        rand = rand * (world.getLevel().random.nextBoolean() ? 1 : -1);
        meteor.moveTo(player.getX() + rand,player.getY()+ 100,player.getZ()+ rand);


        double d0 = world.getLevel().random.nextDouble() * (double)((float)Math.PI * 2F);


        Vec3 meteorPos = meteor.position();

        double offsetRange = 15.0;
        double offsetX = (world.getRandom().nextDouble() * 2 - 1) * offsetRange;
        double offsetZ = (world.getRandom().nextDouble() * 2 - 1) * offsetRange;
        Vec3 targetPos = new Vec3(player.getX() + offsetX, player.getY(), player.getZ() + offsetZ);
        Vec3 dir = meteorPos.subtract(targetPos);

        Vec3 spdVec = dir.normalize().scale(-0.75f);

        meteor.setDeltaMovement(spdVec);
        if(meteorType.equals(MeteorTypes.STANDARD)){
            meteor.setSize(5);
        }


        world.addFreshEntity(meteor);
    }



}
