package com.burkeysmeteors.entity.util;

import com.burkeysmeteors.entity.ModEntities;
import com.burkeysmeteors.entity.custom.meteor.MeteorBaseEntity;
import com.burkeysmeteors.entity.custom.meteor.MeteorTypes;
import com.burkeysmeteors.entity.custom.meteor.StandardMeteorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.util.AirRandomPos;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;


public class SkyAnomalyUtils {
    public static void spawnMeteor(ServerLevel world, Player player, MeteorTypes meteorType, int size){
        StandardMeteorEntity meteor = new StandardMeteorEntity(ModEntities.STANDARD_METEOR.get(),world);
        // Handles the spawnpos of the meteor
        Level level = world.getLevel();
        int rand = level.getRandom().nextIntBetweenInclusive(100, 150);
        rand = rand * (level.random.nextBoolean() ? 1 : -1);

        BlockPos blockPos = new BlockPos(player.getBlockX() + rand ,player.getBlockY(),player.getBlockZ()+ rand);

        meteor.setAnchorPoint(level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, blockPos).above(200 + level.getRandom().nextInt(20)));
        meteor.moveTo(Vec3.atLowerCornerOf(meteor.getAnchorPoint()));

        // Gives the meteor a movement direction
        Vec3 meteorPos = meteor.position();

        double offsetRange = 200.0;
        double offsetX = (world.getRandom().nextDouble() * 2 - 1) * offsetRange;
        double offsetZ = (world.getRandom().nextDouble() * 2 - 1) * offsetRange;
        Vec3 targetPos = new Vec3(player.getX() + offsetX, player.getY(), player.getZ() + offsetZ);
        Vec3 dir = meteorPos.subtract(targetPos);

        Vec3 spdVec = dir.normalize().scale(-1f);
        spdVec = spdVec.scale(0.75+ 0.50*(5 - size));
        meteor.setSize(size);

        meteor.setDeltaMovement(spdVec);


        world.addFreshEntity(meteor);
    }



}
