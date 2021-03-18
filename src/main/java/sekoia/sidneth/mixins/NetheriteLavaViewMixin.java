package sekoia.sidneth.mixins;

import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Items;
import net.minecraft.tag.FluidTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BackgroundRenderer.class)
public class NetheriteLavaViewMixin {
    @ModifyVariable(method = "applyFog", at = @At("STORE"), index = 7)
    private static float reduceFogEnd(float v, Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog) {
        if (camera.getFocusedEntity() instanceof LivingEntity && isWearingNetherite((LivingEntity)camera.getFocusedEntity()) && camera.getSubmergedFluidState().isIn(FluidTags.LAVA)) {
            return 20.0F;
        }
        return v;
    }

    @ModifyVariable(method = "applyFog", at = @At("STORE"), index = 6)
    private static float reduceFogStart(float s, Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog) {
        if (camera.getFocusedEntity() instanceof LivingEntity && isWearingNetherite((LivingEntity)camera.getFocusedEntity()) && camera.getSubmergedFluidState().isIn(FluidTags.LAVA)) {
            return 2.0F;
        }
        return s;
    }

    private static boolean isWearingNetherite(LivingEntity e) {
        return e.getEquippedStack(EquipmentSlot.HEAD).getItem() == Items.NETHERITE_HELMET;
    }
}