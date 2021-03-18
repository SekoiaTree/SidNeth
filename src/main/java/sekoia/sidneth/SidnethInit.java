package sekoia.sidneth;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import sekoia.sidneth.abstractions.ArmorRegister;

public class SidnethInit implements ModInitializer {
    public static Item[] skystoneItems;
    public static Item[] aquaniteItems;

    public static final Identifier SYNC_ARMOR_BONUS_PACKET_ID = new Identifier("sidneth", "sync_armor_bonus");

    @Override
    public void onInitialize() {
        skystoneItems = ArmorRegister.register(new Skystone(), "skystone", ArmorRegister.ALL & (~ArmorRegister.CHESTPLATE)); // ALL flags and not Chestplate
        aquaniteItems = ArmorRegister.register(new Aquanite(), "aquanite"); // ALL flags
    }
}
