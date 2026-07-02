package com.burkeysmeteors.entity.client.meteorclient;


import com.burkeysmeteors.SkyAnomaliesMod;
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

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(SkyAnomaliesMod.MODID, "meteorentitymodel"), "main");
    private final ModelPart root;

    private final ModelPart meteor;
    private final ModelPart main_rock;
    private final ModelPart OuterLayer;
    private final ModelPart trail;
    public MeteorModel(ModelPart root) {
        this.root = root;

        this.meteor = root.getChild("meteor");
        this.main_rock = this.meteor.getChild("main_rock");
        this.OuterLayer = this.main_rock.getChild("OuterLayer");
        this.trail = this.meteor.getChild("trail");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition meteor = partdefinition.addOrReplaceChild("meteor", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition main_rock = meteor.addOrReplaceChild("main_rock", CubeListBuilder.create().texOffs(0, 72).addBox(-10.5F, -18.5F, -12.0F, 32.0F, 32.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.5862F, 0.428F, -3.9696F));

        PartDefinition OuterLayer = main_rock.addOrReplaceChild("OuterLayer", CubeListBuilder.create().texOffs(0, 0).addBox(-6.9138F, -22.928F, -10.0304F, 36.0F, 36.0F, 36.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.5F, 2.5F, -4.0F));

        PartDefinition trail = meteor.addOrReplaceChild("trail", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(StandardMeteorEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animate(entity.fallingAnimationState, MeteorAnimations.falling, ageInTicks, 1f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int colour) {
        meteor.render(poseStack, vertexConsumer, packedLight, packedOverlay, colour);

    }

    @Override
    public ModelPart root() {
        return this.root;
    }


}