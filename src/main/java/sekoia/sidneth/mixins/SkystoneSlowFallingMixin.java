package sekoia.sidneth.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sekoia.sidneth.ArmorSet;
import sekoia.sidneth.SidnethInit;
import sekoia.sidneth.setBonusInterface;


@Mixin(LivingEntity.class)
public abstract class SkystoneSlowFallingMixin extends Entity {
    public SkystoneSlowFallingMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @ModifyVariable(method = "travel", at = @At("STORE"), ordinal = 0)
    private double isWearingSkystone(double d, Vec3d _v) {
        if (((setBonusInterface)this).getCurrentArmorSet() == ArmorSet.SKYSTONE && this.isSneaking()) {
            this.fallDistance = 0.0F;
            return 0.01D;
        }
        return d;
    }

    @Mixin(PlayerEntity.class)
    private static abstract class SkystoneNoFallDamageMixin {
        @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

        @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
        private void cancelFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Boolean> cir) {
            if (this.getEquippedStack(EquipmentSlot.FEET).getItem() == SidnethInit.skystoneItems[1]) {
                cir.setReturnValue(false);
            }
        }
    }
}
