package com.burkeysmeteors.entity.client.meteorclient;

import com.burkeysmeteors.SkyAnomaliesMod;
import com.burkeysmeteors.entity.custom.meteor.StandardMeteorEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;

public class MeteorGlowLayer<T extends StandardMeteorEntity> extends EyesLayer<T, MeteorModel<T>> {
    private static final RenderType METEOR_GLOW = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(SkyAnomaliesMod.MODID,"textures/entity/meteor/meteor.png"));

    public MeteorGlowLayer(RenderLayerParent<T, MeteorModel<T>> modelRenderLayerParent) {
        super(modelRenderLayerParent);
    }

    public RenderType renderType() {
        return METEOR_GLOW;
    }
}