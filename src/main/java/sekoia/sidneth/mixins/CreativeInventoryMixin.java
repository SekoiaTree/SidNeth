package sekoia.sidneth.mixins;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.ItemGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.class)
public class CreativeInventoryMixin {
    @Inject(method = "setSelectedTab", at = @At("HEAD"))
    private void itemGroup(ItemGroup group, CallbackInfo ci) {
        System.out.println(group.getName());
    }
}
