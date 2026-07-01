package com.burkeysmeteors.commands;

import com.burkeysmeteors.entity.custom.meteor.MeteorTypes;
import com.burkeysmeteors.entity.util.SkyAnomalyUtils;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.server.command.EnumArgument;

import java.util.Objects;

public class SpawnMeteorCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("spawnMeteor").requires(
                commandSourceStack -> commandSourceStack.hasPermission(4))
                .then(Commands.argument("meteorType", EnumArgument.enumArgument(MeteorTypes.class))
                .then(Commands.argument("player", EntityArgument.player())
                .then(Commands.argument("size", IntegerArgumentType.integer(1,5))
                        .executes(SpawnMeteorCommand::spawnMeteor))))
        );
    }

    private static int spawnMeteor(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = EntityArgument.getPlayer(context,"player");
        MeteorTypes meteorType = context.getArgument("meteorType", MeteorTypes.class);
        ServerLevel world = Objects.requireNonNull(player.getServer()).getLevel(ServerLevel.OVERWORLD);
        int size = IntegerArgumentType.getInteger(context,"size");
        SkyAnomalyUtils.spawnMeteor(world,player,meteorType,size);

        Minecraft.getInstance().gui.getChat().addMessage(Component.literal("Spawned new " + meteorType + " meteor"));
        return 1;
    }
}
