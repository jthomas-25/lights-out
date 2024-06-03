package lightsout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Square extends Rectangle {
    private int x;
    private int y;
    static int width = 100;
    static int height = width;
    int left;
    int right;
    int top;
    int bottom;
    int row;
    int col;
    String state;
    Tuple[] adjacentSquares;

    Square(int x, int y, int row, int col) {
        super(x, y, width, height);
        this.x = x;
        this.y = y;
        this.row = row;
        this.col = col;
        String[] states = {"ON", "OFF"};
        state = states[(int)(Math.random() * 2)];
        setColor();
        adjacentSquares = getAdjacentSquares();
        setProperties();
    }

    @Override
    public String toString() { return "[" + this.row + ", " + this.col + "]: "; }

    private void setColor() {
        if (isOn()) {
            setFill(Color.GREEN);
        }
        else {
            setFill(Color.BLACK);
        }
    }

    private void setProperties() {
        left = x;
        right = x + width;
        top = y;
        bottom = y + height;
    }

    private Tuple[] getAdjacentSquares() {
        Tuple[] adjacentSquares;
        if (isEdge()) {
            adjacentSquares = (isCorner()) ? new Tuple[2] : new Tuple[3];
            if (isLeftEdge()) {
                if (isTopLeft()) {
                    adjacentSquares[0] = new Tuple(this.row, this.col + 1);
                    adjacentSquares[1] = new Tuple(this.row + 1, this.col);
                }
                else if (isBottomLeft()) {
                    adjacentSquares[0] = new Tuple(this.row, this.col + 1);
                    adjacentSquares[1] = new Tuple(this.row - 1, this.col);
                }
                else {
                    adjacentSquares[0] = new Tuple(this.row, this.col + 1);
                    adjacentSquares[1] = new Tuple(this.row - 1, this.col);
                    adjacentSquares[2] = new Tuple(this.row + 1, this.col);
                }
            }
            else if (isRightEdge()) {
                if (isTopRight()) {
                    adjacentSquares[0] = new Tuple(this.row, this.col - 1);
                    adjacentSquares[1] = new Tuple(this.row + 1, this.col);
                }
                else if (isBottomRight()) {
                    adjacentSquares[0] = new Tuple(this.row, this.col - 1);
                    adjacentSquares[1] = new Tuple(this.row - 1, this.col);
                }
                else {
                    adjacentSquares[0] = new Tuple(this.row, this.col - 1);
                    adjacentSquares[1] = new Tuple(this.row - 1, this.col);
                    adjacentSquares[2] = new Tuple(this.row + 1, this.col);
                }
            }
            else if (isTopEdge()) {
                adjacentSquares[0] = new Tuple(this.row, this.col - 1);
                adjacentSquares[1] = new Tuple(this.row, this.col + 1);
                adjacentSquares[2] = new Tuple(this.row + 1, this.col);
            }
            else if (isBottomEdge()) {
                adjacentSquares[0] = new Tuple(this.row, this.col - 1);
                adjacentSquares[1] = new Tuple(this.row , this.col + 1);
                adjacentSquares[2] = new Tuple(this.row - 1, this.col);
            }
        }
        else {
            adjacentSquares = new Tuple[4];
            adjacentSquares[0] = new Tuple(this.row, this.col - 1);
            adjacentSquares[1] = new Tuple(this.row, this.col + 1);
            adjacentSquares[2] = new Tuple(this.row - 1, this.col);
            adjacentSquares[3] = new Tuple(this.row + 1, this.col);
        }
        return adjacentSquares;
    }

    private boolean isCorner() {
        return isTopLeft() || isTopRight() || isBottomLeft() || isBottomRight();
    }

    private boolean isTopLeft() { return isTopEdge() && isLeftEdge(); }

    private boolean isTopRight() { return isTopEdge() && isRightEdge(); }

    private boolean isBottomLeft() { return isBottomEdge() && isLeftEdge(); }

    private boolean isBottomRight() { return isBottomEdge() && isRightEdge(); }

    private boolean isEdge() {
        return isLeftEdge() || isRightEdge() || isTopEdge() || isBottomEdge();
    }

    private boolean isLeftEdge() { return this.col == 0; }

    private boolean isRightEdge() { return this.col == Board.width - 1; }

    private boolean isTopEdge() { return this.row == 0; }

    private boolean isBottomEdge() { return this.row == Board.height - 1;}

    void change() {
        if (isOn()) {
            turnOff();
        }
        else {
            turnOn();
        }
        setColor();
    }

    boolean isOn() { return state.equals("ON"); }

    boolean isOff() { return state.equals("OFF"); }

    void turnOn() { state = "ON"; }

    void turnOff() { state = "OFF"; }
}
