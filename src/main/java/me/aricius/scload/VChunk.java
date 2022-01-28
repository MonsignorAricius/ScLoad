package me.aricius.scload;

import com.sk89q.worldedit.math.BlockVector3;

public class VChunk {
    int x;
    int z;

    public VChunk(BlockVector3 bv) {
        this.x = bv.getBlockX() >> 4;
        this.z = bv.getBlockZ() >> 4;
    }

    public int hashCode() {
        boolean prime = true;
        int result = 1;
        result = 31 * result + this.x;
        result = 31 * result + this.z;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof VChunk)) {
            return false;
        } else {
            VChunk other = (VChunk)obj;
            if (this.x != other.x) {
                return false;
            } else {
                return this.z == other.z;
            }
        }
    }
}
