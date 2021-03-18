package sekoia.sidneth.mixins;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import sekoia.sidneth.SidnethInit;

@Mixin(LivingEntity.class)
public abstract class SkystoneKineticEnergyMixin {
    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @ModifyVariable(method = "travel", at = @At("STORE"), name = "s")
    private float halfKineticEnergyDamage(float f) {
        if (this.getEquippedStack(EquipmentSlot.LEGS).getItem() == SidnethInit.skystoneItems[2]) {
            return f/4;
        }
        return f;
    }
}
