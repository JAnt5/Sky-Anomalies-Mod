package com.burkeysmeteors.entity.client.meteorclient;


import com.burkeysmeteors.Burkeysmeteormod;
import com.burkeysmeteors.entity.custom.meteor.MeteorBaseEntity;
import com.burkeysmeteors.entity.custom.meteor.StandardMeteorEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class MeteorModel<T extends StandardMeteorEntity> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Burkeysmeteormod.MODID, "meteorentitymodel"), "main");
    private final ModelPart root;
    private final ModelPart main_rock;
    public MeteorModel(ModelPart root) {
        this.root = root;
        this.main_rock = root.getChild("main_rock");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main_rock = partdefinition.addOrReplaceChild("main_rock", CubeListBuilder.create().texOffs(-60, -30).addBox(-17.0F, -16.0F, -15.0F, 32.0F, 32.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(-15.0F, 8.0F, 15.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(StandardMeteorEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animate(entity.fallingAnimationState, MeteorAnimations.falling, ageInTicks, 1f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int colour) {
        main_rock.render(poseStack, vertexConsumer, packedLight, packedOverlay, colour);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}