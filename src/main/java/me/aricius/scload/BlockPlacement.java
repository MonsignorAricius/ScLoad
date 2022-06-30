package me.aricius.scload;

import com.sk89q.worldedit.extent.reorder.MultiStageReorder.PlacementPriority;
import com.sk89q.worldedit.world.block.BlockCategories;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import java.util.HashMap;
import java.util.Map;

public class BlockPlacement {
    private static Map<BlockType, PlacementPriority> priorityMap = new HashMap();

    public BlockPlacement() {
    }

    public static boolean shouldPlaceFinal(BlockType type) {
        return priorityMap.containsKey(type) && priorityMap.get(type) == PlacementPriority.FINAL;
    }

    public static boolean shouldPlaceLast(BlockType type) {
        return priorityMap.containsKey(type) && priorityMap.get(type) == PlacementPriority.LAST;
    }

    static {
        BlockCategories.SAPLINGS.getAll().forEach((type) -> {
            PlacementPriority var10000 = (PlacementPriority)priorityMap.put(type, PlacementPriority.LAST);
        });
        BlockCategories.FLOWER_POTS.getAll().forEach((type) -> {
            PlacementPriority var10000 = (PlacementPriority)priorityMap.put(type, PlacementPriority.LAST);
        });
        BlockCategories.BUTTONS.getAll().forEach((type) -> {
            PlacementPriority var10000 = (PlacementPriority)priorityMap.put(type, PlacementPriority.LAST);
        });
        BlockCategories.ANVIL.getAll().forEach((type) -> {
            PlacementPriority var10000 = (PlacementPriority)priorityMap.put(type, PlacementPriority.LAST);
        });
        BlockCategories.WOODEN_PRESSURE_PLATES.getAll().forEach((type) -> {
            PlacementPriority var10000 = (PlacementPriority)priorityMap.put(type, PlacementPriority.LAST);
        });
        BlockCategories.RAILS.getAll().forEach((type) -> {
            PlacementPriority var10000 = (PlacementPriority)priorityMap.put(type, PlacementPriority.LAST);
        });
        BlockCategories.CARPETS.getAll().forEach((type) -> {
            PlacementPriority var10000 = (PlacementPriority)priorityMap.put(type, PlacementPriority.LAST);
        });
        priorityMap.put(BlockTypes.BLACK_BED, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.BLUE_BED, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.BROWN_BED, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.CYAN_BED, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.GRAY_BED, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.GREEN_BED, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.LIGHT_BLUE_BED, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.LIGHT_GRAY_BED, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.LIME_BED, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.MAGENTA_BED, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.ORANGE_BED, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.PINK_BED, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.PURPLE_BED, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.RED_BED, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.WHITE_BED, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.YELLOW_BED, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.GRASS, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.TALL_GRASS, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.ROSE_BUSH, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.DANDELION, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.BROWN_MUSHROOM, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.RED_MUSHROOM, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.FERN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.LARGE_FERN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.OXEYE_DAISY, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.AZURE_BLUET, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.TORCH, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.SOUL_TORCH, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.SOUL_WALL_TORCH, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.TUBE_CORAL, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.TUBE_CORAL_FAN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.TUBE_CORAL_WALL_FAN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.BRAIN_CORAL, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.BRAIN_CORAL_FAN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.BRAIN_CORAL_WALL_FAN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.BUBBLE_CORAL, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.BUBBLE_CORAL_FAN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.BUBBLE_CORAL_WALL_FAN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.HORN_CORAL, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.HORN_CORAL_FAN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.HORN_CORAL_WALL_FAN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.FIRE_CORAL, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.FIRE_CORAL_FAN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.FIRE_CORAL_WALL_FAN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.DEAD_BRAIN_CORAL, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.DEAD_BRAIN_CORAL_FAN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.DEAD_BRAIN_CORAL_WALL_FAN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.DEAD_FIRE_CORAL, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.DEAD_BUBBLE_CORAL_FAN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.DEAD_BUBBLE_CORAL_WALL_FAN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.DEAD_HORN_CORAL, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.DEAD_HORN_CORAL_WALL_FAN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.DEAD_HORN_CORAL_FAN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.DEAD_TUBE_CORAL, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.DEAD_TUBE_CORAL_FAN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.DEAD_TUBE_CORAL_WALL_FAN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.KELP_PLANT, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.BAMBOO_SAPLING, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.BAMBOO, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.WEEPING_VINES_PLANT, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.WEEPING_VINES, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.LANTERN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.SOUL_LANTERN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.TWISTING_VINES_PLANT, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.TWISTING_VINES, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.SCAFFOLDING, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.NETHER_SPROUTS, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.WARPED_ROOTS, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.CRIMSON_ROOTS, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.WARPED_FUNGUS, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.SPRUCE_SIGN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.SPRUCE_WALL_SIGN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.ACACIA_SIGN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.ACACIA_WALL_SIGN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.BIRCH_SIGN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.BIRCH_WALL_SIGN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.CRIMSON_SIGN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.CRIMSON_WALL_SIGN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.DARK_OAK_SIGN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.DARK_OAK_WALL_SIGN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.JUNGLE_SIGN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.JUNGLE_WALL_SIGN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.WARPED_SIGN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.WARPED_WALL_SIGN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.WALL_TORCH, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.FIRE, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.REDSTONE_WIRE, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.CARROTS, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.POTATOES, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.WHEAT, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.BEETROOTS, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.COCOA, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.LADDER, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.LEVER, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.REDSTONE_TORCH, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.REDSTONE_WALL_TORCH, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.SNOW, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.NETHER_PORTAL, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.END_PORTAL, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.REPEATER, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.VINE, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.LILY_PAD, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.NETHER_WART, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.PISTON, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.STICKY_PISTON, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.TRIPWIRE_HOOK, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.TRIPWIRE, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.STONE_PRESSURE_PLATE, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.HEAVY_WEIGHTED_PRESSURE_PLATE, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.LIGHT_WEIGHTED_PRESSURE_PLATE, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.COMPARATOR, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.IRON_TRAPDOOR, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.ACACIA_TRAPDOOR, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.BIRCH_TRAPDOOR, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.DARK_OAK_TRAPDOOR, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.JUNGLE_TRAPDOOR, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.OAK_TRAPDOOR, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.SPRUCE_TRAPDOOR, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.DAYLIGHT_DETECTOR, PlacementPriority.LAST);
        //1.17.1
        priorityMap.put(BlockTypes.FLOWERING_AZALEA_LEAVES, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.FLOWERING_AZALEA, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.AZALEA, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.SPORE_BLOSSOM, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.MOSS_CARPET, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.BIG_DRIPLEAF, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.BIG_DRIPLEAF_STEM, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.SMALL_DRIPLEAF, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.GLOW_LICHEN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.SMALL_AMETHYST_BUD, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.MEDIUM_AMETHYST_BUD, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.LARGE_AMETHYST_BUD, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.AMETHYST_CLUSTER, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.POINTED_DRIPSTONE, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.HANGING_ROOTS, PlacementPriority.LAST);
        //
        //1.18.1
        //
        //1.19
        priorityMap.put(BlockTypes.FROGSPAWN, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.MANGROVE_LEAVES, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.MANGROVE_PROPAGULE, PlacementPriority.LAST);
        priorityMap.put(BlockTypes.SCULK_VEIN, PlacementPriority.LAST);
        //
        BlockCategories.DOORS.getAll().forEach((type) -> {
            PlacementPriority var10000 = (PlacementPriority)priorityMap.put(type, PlacementPriority.FINAL);
        });
        BlockCategories.BANNERS.getAll().forEach((type) -> {
            PlacementPriority var10000 = (PlacementPriority)priorityMap.put(type, PlacementPriority.FINAL);
        });
        priorityMap.put(BlockTypes.CACTUS, PlacementPriority.FINAL);
        priorityMap.put(BlockTypes.SUGAR_CANE, PlacementPriority.FINAL);
        priorityMap.put(BlockTypes.CAKE, PlacementPriority.FINAL);
        priorityMap.put(BlockTypes.PISTON_HEAD, PlacementPriority.FINAL);
        priorityMap.put(BlockTypes.MOVING_PISTON, PlacementPriority.FINAL);
    }
}
