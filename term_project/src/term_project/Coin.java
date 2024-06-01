package term_project;

public class Coin {
    private int denomination;
    private int count;

    public Coin(int denomination, int count) {
        this.denomination = denomination;
        this.count = count;
    }

    public int getDenomination() {
        return denomination;
    }

    public int getCount() {
        return count;
    }

    public void addCount(int count) {
        this.count += count;
    }

    public void reduceCount(int count) {
        if (this.count >= count) {
            this.count -= count;
        }
    }
}
