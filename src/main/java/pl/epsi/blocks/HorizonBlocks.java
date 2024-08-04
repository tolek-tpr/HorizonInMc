package pl.epsi.blocks;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import pl.epsi.HorizonInMc;
import pl.epsi.items.HorizonItems;

public class HorizonBlocks {

    public static final Block CONDENSED_DIRT = register(
            new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE)),
            "condensed_dirt",
            true
    );
    public static final Block CAULDRON_WALL_BLOCK = register(
            new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE).mapColor(MapColor.BLUE)),
        "cauldron_wall_block",
true
    );
    public static final Block LIGHT_LINE = register(
            new Block(AbstractBlock.Settings.create().luminance(state -> 15).mapColor(MapColor.LIGHT_BLUE).noCollision()),
            "light_line",
            true
    );

    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        Identifier id = Identifier.of(HorizonInMc.MOD_ID, name);

        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, blockItem);
        }

        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(HorizonItems.CUSTOM_ITEM_GROUP_KEY).register((itemGroup) -> {
                itemGroup.add(HorizonBlocks.CONDENSED_DIRT.asItem());
                itemGroup.add(HorizonBlocks.CAULDRON_WALL_BLOCK.asItem());
                itemGroup.add(HorizonBlocks.LIGHT_LINE.asItem());
            }
        );
    }

}
