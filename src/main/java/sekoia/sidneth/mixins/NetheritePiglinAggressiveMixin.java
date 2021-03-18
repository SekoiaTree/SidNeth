package sekoia.sidneth.mixins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sekoia.sidneth.ArmorSet;
import sekoia.sidneth.setBonusInterface;

@Mixin(PiglinBrain.class)
public class NetheritePiglinAggressiveMixin {
    @Inject(method = "wearsGoldArmor", at = @At("RETURN"), cancellable = true)
    private static void fullNetheriteSetBonus(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            return;
        }
        if (entity instanceof PlayerEntity && ((setBonusInterface)entity).getCurrentArmorSet() == ArmorSet.NETHERITE) {
            cir.setReturnValue(true);
        }
    }
}
