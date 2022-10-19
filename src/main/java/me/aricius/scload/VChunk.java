package me.aricius.scload;

import com.sk89q.worldedit.math.BlockVector3;

public class VChunk {
    int x;
    int z;

    public VChunk(BlockVector3 bv) {
        x = bv.getBlockX() >> 4;
        z = bv.getBlockZ() >> 4;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + z;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof VChunk))
            return false;
        VChunk other = (VChunk) obj;
        if (x != other.x)
            return false;
        if (z != other.z)
            return false;
        return true;
    }


}