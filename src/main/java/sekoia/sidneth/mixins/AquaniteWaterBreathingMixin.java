package sekoia.sidneth.mixins;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sekoia.sidneth.SidnethInit;

@Mixin(LivingEntity.class)
public abstract class AquaniteWaterBreathingMixin {
    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Inject(method = "canBreatheInWater", at = @At("HEAD"), cancellable = true)
    private void canBreathWithAquanite(CallbackInfoReturnable<Boolean> cir) {
        if (getEquippedStack(EquipmentSlot.HEAD).getItem() == SidnethInit.aquaniteItems[4]) {
            cir.setReturnValue(true);
        }
    }
}
