import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private int openCount;
    private final boolean[] openTracker;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF noBottomUF;
    private final int topNode;
    private final int bottomNode;
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be > 0");
        }
        this.n = N;
        this.openCount = 0;
        this.openTracker = new boolean[N * N];
        this.uf = new WeightedQuickUnionUF((N * N) + 2);
        this.noBottomUF = new WeightedQuickUnionUF((N * N) + 1);
        this.topNode = N * N;
        this.bottomNode = N * N + 1;
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        } else {
            openTracker[valueFromParam(row, col)] = true;
            openCount += 1;
        }
        int a = valueFromParam(row, col);
        if (row == 0) {
            uf.union(a, topNode);
            noBottomUF.union(a, topNode);
        }
        if (row == n - 1) {
            uf.union(a, bottomNode);
        }
        if (row > 0 && isOpen(row - 1, col)) {
            uf.union(a, valueFromParam(row - 1, col));
            noBottomUF.union(a, valueFromParam(row - 1, col));
        }
        if (row < n - 1 && isOpen(row + 1, col)) {
            uf.union(a, valueFromParam(row + 1, col));
            noBottomUF.union(a, valueFromParam(row + 1, col));
        }
        if (col > 0 && isOpen(row, col - 1)) {
            uf.union(a, valueFromParam(row, col - 1));
            noBottomUF.union(a, valueFromParam(row, col - 1));
        }
        if ((col < n - 1) && isOpen(row, col + 1)) {
            uf.union(a, valueFromParam(row, col + 1));
            noBottomUF.union(a, valueFromParam(row, col + 1));
        }
    }

    public int valueFromParam(int row, int col) {
        int value = row * n + (col);
        return value;
    }
    public void isParam(int row, int col) {
        if (row < 0 || col < 0 || row >= n || col >= n) {
            throw new IndexOutOfBoundsException("out of bounds");
        }
    }

    public boolean isOpen(int row, int col) {
        isParam(row, col);
        return openTracker[valueFromParam(row, col)];
    }

    public boolean isFull(int row, int col) {
        isParam(row, col);
        int a = valueFromParam(row, col);
        return (openTracker[a] && noBottomUF.connected(a, topNode));
    }

    public int numberOfOpenSites() {
        return openCount;
    }

    public boolean percolates() {
        return uf.connected(topNode, bottomNode);
    }
}
