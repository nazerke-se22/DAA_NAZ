package metrics;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class PerformanceTracker {
    private long comparisons;
    private long moves;
    private long reads;
    private long writes;
    private long allocations;

    public void cmp() { comparisons++; }
    public void cmp(long n) { comparisons += n; }

    public void move() { moves++; }
    public void move(long n) { moves += n; }

    public void read() { reads++; }
    public void read(long n) { reads += n; }

    public void write() { writes++; }
    public void write(long n) { writes += n; }

    public void alloc(int n) { allocations += n; }

    public long getComparisons() { return comparisons; }
    public long getMoves() { return moves; }
    public long getReads() { return reads; }
    public long getWrites() { return writes; }
    public long getAllocations() { return allocations; }

    public void reset() {
        comparisons = moves = reads = writes = allocations = 0;
    }

    public static void writeHeader(String path) throws IOException {
        Path file = Paths.get(path);
        boolean exists = Files.exists(file) && Files.size(file) > 0;

        if (!exists) {
            try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                writer.write("time_ns,n,algo,dist,comparisons,moves,reads,writes,allocs,notes\n");
            }
        }
    }

    public void writeRow(String path, long timeNs, int n, String algo, String dist, String notes) throws IOException {
        writeHeader(path);
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(timeNs + "," + n + "," + algo + "," + dist + "," +
                    comparisons + "," + moves + "," + reads + "," + writes + "," + allocations + "," +
                    (notes == null ? "" : notes) + "\n");
        }
    }

    @Override
    public String toString() {
        return "comparisons=" + comparisons +
                ", moves=" + moves +
                ", reads=" + reads +
                ", writes=" + writes +
                ", allocations=" + allocations;
    }
}
