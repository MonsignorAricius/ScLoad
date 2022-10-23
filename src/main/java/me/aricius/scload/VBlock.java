package me.aricius.scload;

import com.sk89q.jnbt.CompoundTag;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.PasteBuilder;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.block.BlockTypes;
import org.bukkit.Material;
import org.bukkit.World;

public class VBlock {
    BukkitWorld world;
    BlockVector3 bvector;
    BlockState bblock;
    BaseBlock block;
    int hash;

    public VBlock(World w, BlockVector3 bv, BlockState bb, CompoundTag nbt) {
        this.world = new BukkitWorld(w);
        this.bvector = bv;
        this.block = bb.toBaseBlock(nbt);
        this.bblock = bb;
        this.hash = this.calcHashCode();
    }

    public void placeBlockFast() {
        try {
            world.setBlock(bvector, bblock, false);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
    }

    public void placeBlock() {
        try {
            world.setBlock(bvector, bblock, true);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
    }
    public void placeBlockFastWithoutAir() {
        try {
            if (getTypeId().equals(BlockTypes.AIR)) {
                return;
            } else {
                world.setBlock(bvector, bblock, false);
            }
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
    }

    public void placeBlockWithoutAir() {
        try {
            if (getTypeId().equals(BlockTypes.AIR)) {
                return;
            } else {
                world.setBlock(bvector, bblock, true);
            }
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
    }

    public BlockType getTypeId() {
        return this.bblock.getBlockType();
    }


    public int calcHashCode() {
        int prime = 31;
        int result = bvector.getBlockX() >> 4;
        if (result < 0) result = result * (-1) * prime;
        result = result * prime + (bvector.getBlockZ() >> 4);
        if (result < 0) result = result * (-1) * prime;
        return result;
    }

    @Override
    public int hashCode() {
        return this.hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof VBlock)) return false;
        if (world == null) return false;
        VBlock other = (VBlock) obj;
        if (other.world == null) return false;
        if (!world.getWorld().equals(other.world.getWorld())) return false;
        if (bvector == null) return false;
        if (other.bvector == null) return false;
        if (bvector.getBlockX() != other.bvector.getBlockX()) return false;
        if (bvector.getBlockY() != other.bvector.getBlockY()) return false;
        return (bvector.getBlockZ() == other.bvector.getBlockZ());
    }
}