package com.burkeysmeteors.entity.client.meteorclient;

import com.burkeysmeteors.Burkeysmeteormod;
import com.burkeysmeteors.entity.custom.meteor.MeteorBaseEntity;
import com.burkeysmeteors.entity.custom.meteor.StandardMeteorEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class MeteorRenderer extends EntityRenderer<StandardMeteorEntity> {
    private final MeteorModel<StandardMeteorEntity> model;
    private final ResourceLocation TEXTURE_LOCATION = ResourceLocation.fromNamespaceAndPath(Burkeysmeteormod.MODID,"textures/entity/meteor/meteor.png");

    public MeteorRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new MeteorModel<>(context.bakeLayer(MeteorModel.LAYER_LOCATION));

    }

    @Override
    public ResourceLocation getTextureLocation(StandardMeteorEntity meteorBaseEntity) {
        return TEXTURE_LOCATION;
    }

    public void render(StandardMeteorEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        float ageInTicks = entity.tickCount + partialTicks;

        this.model.setupAnim(entity, 0.0F, 0.0F, ageInTicks, 0.0F, 0.0F);

        VertexConsumer vertexconsumer = buffer.getBuffer(this.model.renderType(TEXTURE_LOCATION));
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }
}
