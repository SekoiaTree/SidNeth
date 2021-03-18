package sekoia.sidneth.mixins;

import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sekoia.sidneth.SidnethInit;

@Mixin(EndermanEntity.class)
public class ViewEndermanMixin {
    @Inject(method = "isPlayerStaring", at = @At("HEAD"), cancellable = true)
    private void cancelIfWearingSkystoneHelm(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = player.inventory.armor.get(3);
        if (itemStack.getItem() == SidnethInit.skystoneItems[3]) {
            cir.setReturnValue(false);
        }
    }
}
