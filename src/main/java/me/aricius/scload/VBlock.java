package me.aricius.scload;

import com.sk89q.jnbt.CompoundTag;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockType;
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
            this.world.setBlock(this.bvector, this.block, false);
        } catch (WorldEditException var2) {
            var2.printStackTrace();
        }

    }

    public void placeBlock() {
        try {
            this.world.setBlock(this.bvector, this.block, true);
        } catch (WorldEditException var2) {
            var2.printStackTrace();
        }

    }

    public BlockType getTypeId() {
        return this.bblock.getBlockType();
    }

    public int calcHashCode() {
        int prime = 31;
        int result = this.bvector.getBlockX() >> 4;
        if (result < 0) {
            result = result * -1 * prime;
        }

        result = result * prime + (this.bvector.getBlockZ() >> 4);
        if (result < 0) {
            result = result * -1 * prime;
        }

        return result;
    }

    public int hashCode() {
        return this.hash;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof VBlock)) {
            return false;
        } else if (this.world == null) {
            return false;
        } else {
            VBlock other = (VBlock)obj;
            if (other.world == null) {
                return false;
            } else if (!this.world.getWorld().equals(other.world.getWorld())) {
                return false;
            } else if (this.bvector == null) {
                return false;
            } else if (other.bvector == null) {
                return false;
            } else if (this.bvector.getBlockX() != other.bvector.getBlockX()) {
                return false;
            } else if (this.bvector.getBlockY() != other.bvector.getBlockY()) {
                return false;
            } else {
                return this.bvector.getBlockZ() == other.bvector.getBlockZ();
            }
        }
    }
}

