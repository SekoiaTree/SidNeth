package sekoia.sidneth.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(LivingEntity.class)
public abstract class LavaSwimmingSpeedMixin extends Entity {

    public LavaSwimmingSpeedMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    @ModifyConstant(method = "travel", constant = @Constant(floatValue = 0.02F),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isInLava()Z"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getFluidHeight(Lnet/minecraft/tag/Tag;)D")
            ))
    private float makeLavaSwimmingFaster(float s) {
        if (isSwimming()) {
            return 0.1F;
        }
        return s;
    }
}
