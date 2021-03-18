package sekoia.sidneth.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sekoia.sidneth.ArmorSet;
import sekoia.sidneth.setBonusInterface;

@Mixin(Entity.class)
public abstract class NetheriteFireImmuneMixin {

    @SuppressWarnings("ConstantConditions")
    @Inject(method = "isFireImmune", at = @At("HEAD"), cancellable = true)
    private void netheriteMakesImmune(CallbackInfoReturnable<Boolean> cir) {
        if (((Entity)(Object)this) instanceof LivingEntity && (isWearingNetherite((LivingEntity)(Object)this))) {
            cir.setReturnValue(true);
        }
    }

    private static boolean isWearingNetherite(LivingEntity e) {
        return ((setBonusInterface)e).getCurrentArmorSet() == ArmorSet.NETHERITE;
    }
}
