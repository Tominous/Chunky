package org.popcraft.chunky;

import java.util.Iterator;

public class ChunkCoordinateIterator implements Iterator<ChunkCoordinate> {
    private final int radius;
    private int diameter, diameterChunks;
    private int x1, x2, z1, z2;
    private int x, z;
    private long startCount;
    private boolean hasNext = true;
    private final static int CHUNK_SIZE = 16;

    public ChunkCoordinateIterator(int radius, int centerX, int centerZ, long startCount) {
        this(radius, centerX, centerZ);
        this.x = x1 + ((int) (startCount / diameterChunks)) * CHUNK_SIZE;
        this.z = z1 + ((int) (startCount % diameterChunks)) * CHUNK_SIZE;
        this.startCount = startCount;
    }

    public ChunkCoordinateIterator(int radius, int centerX, int centerZ) {
        this.radius = radius;
        diameter = 2 * radius;
        if (diameter % CHUNK_SIZE != 0) {
            ++diameterChunks;
        }
        diameterChunks += diameter / CHUNK_SIZE;
        this.x1 = centerX - radius;
        this.x2 = centerX + radius;
        this.z1 = centerZ - radius;
        this.z2 = centerZ + radius;
        this.x = x1;
        this.z = z1;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public ChunkCoordinate next() {
        final ChunkCoordinate chunkCoord = new ChunkCoordinate(x >> 4, z >> 4);
        if ((z += CHUNK_SIZE) >= z2) {
            z = z1;
            if ((x += CHUNK_SIZE) >= x2) {
                hasNext = false;
            }
        }
        return chunkCoord;
    }

    public ChunkCoordinate peek() {
        return new ChunkCoordinate(x >> 4, z >> 4);
    }

    public long count() {
        return diameterChunks * diameterChunks;
    }
}
