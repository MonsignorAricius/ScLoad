package me.aricius.scload;

import com.sk89q.jnbt.CompoundTag;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.registry.BlockMaterial;
import com.sk89q.worldedit.world.registry.PassthroughBlockMaterial;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class SLQueue {
    boolean flag_loaded = false;
    boolean flag_finished = false;
    boolean noair = false;
    World world;
    BukkitWorld worldeditworld;
    BlockVector3 pos;
    BlockArrayClipboard bac;
    Map<VChunk, List<VBlock>> chunked;
    List<List<VBlock>> queue;
    List<List<VBlock>> undo;
    List<Entity> entities;
    private boolean active = false;
    private boolean finished = false;

    private ScLoad plg() {
        return ScLoad.instance;
    }

    public boolean isLoaded() {
        return this.flag_loaded;
    }

    SLQueue(World w, BlockVector3 v, String filename, boolean ignoreAir) throws FileNotFoundException {
        this.world = w;
        this.pos = v;
        this.bac = this.load(filename);
        this.noair = ignoreAir;
        if (this.bac == null) {
            throw new FileNotFoundException("Schematic file not found.");
        } else {
            this.worldeditworld = new BukkitWorld(w);
        }
    }
    SLQueue(World w, BlockVector3 v, String filename) throws FileNotFoundException {
        this.world = w;
        this.pos = v;
        this.bac = this.load(filename);
        if (this.bac == null) {
            throw new FileNotFoundException("Schematic file not found.");
        } else {
            this.worldeditworld = new BukkitWorld(w);
        }
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void start() {
        this.active = true;
        this.fillEntities();
        this.addAndStart(this.bac);
    }

    public BlockArrayClipboard load(String filename) {
        if (filename.isEmpty()) {
            return null;
        } else {
            File f = null;
            File[] var3 = (new File(this.plg().schem_dir)).listFiles();
            int var4 = var3.length;

            int var5;
            File file;
            for(var5 = 0; var5 < var4; ++var5) {
                file = var3[var5];
                if (file.getName().startsWith(filename + ".")) {
                    f = file;
                }
            }

            if (f == null) {
                var3 = (new File(this.plg().schem_dir)).listFiles();
                var4 = var3.length;

                for(var5 = 0; var5 < var4; ++var5) {
                    file = var3[var5];
                    if (file.isDirectory()) {
                        File[] var7 = file.listFiles();
                        int var8 = var7.length;

                        for(int var9 = 0; var9 < var8; ++var9) {
                            File schematic = var7[var9];
                            if (schematic.getName().startsWith(filename + ".")) {
                                f = schematic;
                            }
                        }
                    }
                }
            }

            if (f != null && f.exists()) {
                ClipboardFormat sc = ClipboardFormats.findByFile(f);
                if (sc == null) {
                    return null;
                } else {
                    BlockArrayClipboard bac = null;

                    try {
                        bac = (BlockArrayClipboard)sc.getReader(new FileInputStream(f)).read();
                    } catch (Exception var11) {
                        this.plg().u.log("Error loading file " + f.getAbsolutePath());
                        var11.printStackTrace();
                    }

                    return bac;
                }
            } else {
                return null;
            }
        }
    }

    public void addAndStart(final BlockArrayClipboard bac) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(this.plg(), new Runnable() {
            public void run() {
                SLQueue.this.addDelayed(SLQueue.this.pos, bac);
                SLQueue.this.processQueue();
            }
        }, (long)this.plg().delay);
    }

    public void fillEntities() {
        BlockVector3 dim = this.bac.getDimensions();
        Location loc1 = new Location(this.world, (double)this.pos.getBlockX(), (double)this.pos.getBlockY(), (double)this.pos.getBlockZ());
        Location loc2 = new Location(this.world, (double)(this.pos.getBlockX() + dim.getBlockX()), (double)(this.pos.getBlockY() + dim.getBlockY()), (double)(this.pos.getBlockZ() + dim.getBlockZ()));
        this.entities = this.plg().u.getEntities(loc1, loc2);
    }

    public void addDelayed(BlockVector3 pos2, BlockArrayClipboard bac) {
        this.queue = new ArrayList<List<VBlock>>();
        this.chunked = new HashMap<VChunk, List<VBlock>>();
        List<VBlock> first = new ArrayList<VBlock>();
        List<VBlock> last = new ArrayList<VBlock>();
        List<VBlock> fin = new ArrayList<VBlock>();
        BlockVector3 dimensions = bac.getDimensions();

        int counter;
        int i;
        for(counter = 0; counter < dimensions.getBlockX(); ++counter) {
            for(int y = 0; y < dimensions.getBlockY(); ++y) {
                for(i = 0; i < dimensions.getBlockZ(); ++i) {
                    if (pos2.getBlockY() + y >= this.world.getMinHeight() && pos2.getBlockY() + y < this.world.getMaxHeight()) {
                        BlockVector3 altpos = BlockVector3.at(counter, y, i).add(bac.getMinimumPoint());
                        BlockState bb = bac.getBlock(altpos);
                        CompoundTag nbt = bac.getFullBlock(altpos).getNbtData();
                        BlockVector3 bv = pos2.add(counter, y, i);
                        VChunk vch = new VChunk(bv);
                        VBlock vb = new VBlock(this.world, bv, bb, nbt);
                        if (!this.chunked.containsKey(vch)) {
                            this.chunked.put(vch, new ArrayList<VBlock>());
                        }

                        ((List<VBlock>)this.chunked.get(vch)).add(vb);
                    }
                }
            }
        }

        if (!this.chunked.isEmpty()) {
            Iterator<VChunk> var16 = this.chunked.keySet().iterator();

            VBlock vb;
            while(var16.hasNext()) {
                VChunk vch = (VChunk)var16.next();

                for(i = 0; i < ((List<VBlock>)this.chunked.get(vch)).size(); ++i) {
                    vb = (VBlock)((List<VBlock>)this.chunked.get(vch)).get(i);
                    if (BlockPlacement.shouldPlaceFinal(vb.bblock.getBlockType())) {
                        fin.add(vb);
                    } else if (BlockPlacement.shouldPlaceLast(vb.bblock.getBlockType())) {
                        last.add(vb);
                    } else {
                        first.add(vb);
                    }
                }
            }

            counter = 0;
            this.queue.clear();
            List<VBlock> bpt = new ArrayList<VBlock>();
            if (!first.isEmpty()) {
                for(i = 0; i < first.size(); ++i) {
                    vb = (VBlock)first.get(i);
                    ++counter;
                    if (counter >= this.plg().blockpertick) {
                        this.queue.add(bpt);
                        bpt = new ArrayList<VBlock>();
                        counter = 0;
                    }

                    bpt.add(vb);
                }

                this.queue.add(bpt);
            }

            bpt = new ArrayList<VBlock>();
            if (!last.isEmpty()) {
                for(i = 0; i < last.size(); ++i) {
                    vb = (VBlock)last.get(i);
                    ++counter;
                    if (counter >= this.plg().blockpertick) {
                        this.queue.add(bpt);
                        bpt = new ArrayList<VBlock>();
                        counter = 0;
                    }

                    bpt.add(vb);
                }

                this.queue.add(bpt);
            }

            bpt = new ArrayList<VBlock>();
            if (!fin.isEmpty()) {
                for(i = 0; i < fin.size(); ++i) {
                    vb = (VBlock)fin.get(i);
                    ++counter;
                    if (counter >= this.plg().blockpertick) {
                        this.queue.add(bpt);
                        bpt = new ArrayList<VBlock>();
                        counter = 0;
                    }

                    bpt.add(vb);
                }

                this.queue.add(bpt);
            }
        }

    }

    public void processQueue() {
        if (!this.queue.isEmpty()) {
            Bukkit.getScheduler().runTaskLater(this.plg(), new Runnable() {
                public void run() {
                    SLQueue.this.placeBlocks((List<VBlock>)SLQueue.this.queue.get(0));
                    SLQueue.this.queue.remove(0);
                    if (!SLQueue.this.queue.isEmpty()) {
                        SLQueue.this.processQueue();
                    } else {
                        SLQueue.this.finished = true;
                        SLQueue.this.active = false;
                        if (SLQueue.this.plg().fastplace) {
                            SLQueue.this.refreshChunks(SLQueue.this.chunked.keySet());
                        }
                    }

                }
            }, (long)this.plg().delay);
        }

    }

    public void placeBlocks(List<VBlock> blocks) {
        if (!blocks.isEmpty()) {
            int i;
            for(i = 0; i < blocks.size(); ++i) {
                Block b = this.world.getBlockAt(((VBlock)blocks.get(i)).bvector.getBlockX(), ((VBlock)blocks.get(i)).bvector.getBlockY(), ((VBlock)blocks.get(i)).bvector.getBlockZ());
                BlockType type = BukkitAdapter.adapt(b.getBlockData()).getBlockType();
                BlockMaterial material = type.getMaterial();
                if (!BlockPlacement.shouldPlaceFinal(type) && !BlockPlacement.shouldPlaceLast(type) && !(material instanceof PassthroughBlockMaterial) && material.hasContainer()) {
                    this.worldeditworld.clearContainerBlockContents(((VBlock)blocks.get(i)).bvector);
                }
            }

            if (noair) {
                if (this.plg().fastplace) {
                    for(i = 0; i < blocks.size(); ++i) {
                        ((VBlock)blocks.get(i)).placeBlockFastWithoutAir();
                    }
                } else {
                    for(i = 0; i < blocks.size(); ++i) {
                        ((VBlock)blocks.get(i)).placeBlockWithoutAir();
                    }
                }
            } else if (this.plg().fastplace) {
                for(i = 0; i < blocks.size(); ++i) {
                    ((VBlock)blocks.get(i)).placeBlockFast();
                }
            } else {
                for(i = 0; i < blocks.size(); ++i) {
                    ((VBlock)blocks.get(i)).placeBlock();
                }
            }
        }

    }

    public void refreshChunks(final Set<VChunk> vchs) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(this.plg(), new Runnable() {
            public void run() {
                Iterator<VChunk> var1 = vchs.iterator();

                while(var1.hasNext()) {
                    VChunk vch = (VChunk)var1.next();
                    SLQueue.this.world.refreshChunk(vch.x, vch.z);
                }

            }
        }, (long)this.plg().delay);
    }
}