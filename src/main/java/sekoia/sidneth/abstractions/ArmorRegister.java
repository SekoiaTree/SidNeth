package sekoia.sidneth.abstractions;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class ArmorRegister {

    public static final int BOOTS = 1; // 0001
    public static final int LEGGINGS = 2; // 0010
    public static final int CHESTPLATE = 4; // 0100
    public static final int HELMET = 8; // 1000
    public static final int MATERIAL = 16; // 00010000
    public static final int ALL = 31; // 00011111

    public ArmorRegister() {
    }

    public static Item[] register(ArmorMaterial material, String name) {
        Item.Settings settings = new Item.Settings().group(ItemGroup.COMBAT);
        Item[] items = new Item[5];
        items[0] = Registry.register(Registry.ITEM, "sidneth:" + name, new Item(new Item.Settings().group(ItemGroup.MATERIALS)));
        items[1] = Registry.register(Registry.ITEM, "sidneth:" + name + "_boots", new ArmorItem(material, EquipmentSlot.FEET, settings));
        items[2] = Registry.register(Registry.ITEM, "sidneth:" + name + "_leggings", new ArmorItem(material, EquipmentSlot.LEGS, settings));
        items[3] = Registry.register(Registry.ITEM, "sidneth:" + name + "_chestplate", new ArmorItem(material, EquipmentSlot.CHEST, settings));
        items[4] = Registry.register(Registry.ITEM, "sidneth:" + name + "_helmet", new ArmorItem(material, EquipmentSlot.HEAD, settings));
        return items;
    }

    public static Item[] register(ArmorMaterial material, String name, int flag) {
        Item.Settings settings = new Item.Settings().group(ItemGroup.COMBAT);
        Item[] items = new Item[Integer.bitCount(flag)];
        int counter = 0;
        if ((flag & MATERIAL) == MATERIAL) {
            items[counter] = Registry.register(Registry.ITEM, "sidneth:" + name, new Item(new Item.Settings().group(ItemGroup.MATERIALS)));
            counter++;
        }
        if ((flag & BOOTS) == BOOTS) {
            items[counter] = Registry.register(Registry.ITEM, "sidneth:" + name + "_boots", new ArmorItem(material, EquipmentSlot.FEET, settings));
            counter++;
        }
        if ((flag & LEGGINGS) == LEGGINGS) {
            items[counter] = Registry.register(Registry.ITEM, "sidneth:" + name + "_leggings", new ArmorItem(material, EquipmentSlot.LEGS, settings));
            counter++;
        }
        if ((flag & CHESTPLATE) == CHESTPLATE) {
            items[counter] = Registry.register(Registry.ITEM, "sidneth:" + name + "_chestplate", new ArmorItem(material, EquipmentSlot.CHEST, settings));
            counter++;
        }
        if ((flag & HELMET) == HELMET) {
            items[counter] = Registry.register(Registry.ITEM, "sidneth:" + name + "_helmet", new ArmorItem(material, EquipmentSlot.HEAD, settings));
        }
        return items;
    }
}
