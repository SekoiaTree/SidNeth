package sekoia.sidneth.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import sekoia.sidneth.SidnethInit;

@Mixin(LivingEntity.class)
public abstract class AquaniteWaterMovementSpeedMixin extends Entity {

    public AquaniteWaterMovementSpeedMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @ModifyVariable(
            method = "travel",
            at = @At(
                    value = "JUMP",
                    opcode = Opcodes.IFLE,
                    ordinal = 0
            ),
            index = 10
    )
    private float hasEffectOrAquanite(float h) {
        float sum = h;
        if (getEquippedStack(EquipmentSlot.HEAD).getItem() == SidnethInit.aquaniteItems[4]) {
            sum += 5;
        }
        if (getEquippedStack(EquipmentSlot.CHEST).getItem() == SidnethInit.aquaniteItems[3]) {
            sum += 5;
        }
        if (getEquippedStack(EquipmentSlot.LEGS).getItem() == SidnethInit.aquaniteItems[2]) {
            sum += 5;
        }
        if (getEquippedStack(EquipmentSlot.FEET).getItem() == SidnethInit.aquaniteItems[1]) {
            sum += 5;
        }
        return sum;
    }
}
