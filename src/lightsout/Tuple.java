package lightsout;

class Tuple {
    int x;
    int y;

    Tuple(int y, int x) {
        this.y = y;
        this.x = x;
    }

    @Override
    public String toString() { return "(" + this.y + ", " + this.x + ")"; }
}
