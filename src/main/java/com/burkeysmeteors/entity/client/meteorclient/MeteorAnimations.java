package com.burkeysmeteors.entity.client.meteorclient;// Save this class in your mod and generate all required imports

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Made with Blockbench 5.1.4
 * Exported for Minecraft version 1.19 or later with Mojang mappings
 * @author Author
 */
public class MeteorAnimations {
	public static final AnimationDefinition falling = AnimationDefinition.Builder.withLength(2.0F).looping()
			.addAnimation("meteor", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, -360.0F, 360.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();
}