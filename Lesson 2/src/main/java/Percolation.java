import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int top = 0;
    private boolean[][] openGrid;
    private int numberOfOpenSites = 0;
    private WeightedQuickUnionUF union;
    private int bottom;
    private int size;



    public Percolation(int n){
        this.openGrid = new boolean[n][n];
        this.union = new WeightedQuickUnionUF(n*n + 2);
        this.bottom = n*n + 1;
        this.size = n;
    }

    public void open(int row, int col){
        checkIfException(row, col);
        openGrid[row - 1][col - 1] = true;
        numberOfOpenSites++;
        if (row == 1) {
            union.union(getIndex(row, col), top);
        }

        if (row == size) {
            union.union(getIndex(row, col), bottom);
        }

        if (row > 1 && isOpen(row - 1, col)) {
            union.union(getIndex(row, col), getIndex(row - 1, col));
        }

        if (row < size && isOpen(row + 1, col)) {
            union.union(getIndex(row, col), getIndex(row + 1, col));
        }

        if (col > 1 && isOpen(row, col - 1)) {
            union.union(getIndex(row, col), getIndex(row, col - 1));
        }

        if (col < size && isOpen(row, col + 1)) {
            union.union(getIndex(row, col), getIndex(row, col + 1));
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        checkIfException(row, col);
        return openGrid[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if ((row > 0 && row <= size) && (col > 0 && col <= size)) {
            return union.find(top) == union.find(getIndex(row, col));
        } else throw new IllegalArgumentException();
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates(){
        return union.find(top) == union.find(bottom);
    }

    private int getIndex(int row, int col) {
        return size * (row - 1) + col;
    }

    private void checkIfException(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException();
        }
    }

    // test client (optional)
    public static void main(String[] args){}
}
