package lightsout;
import javafx.scene.Group;

class Board {
    int x;
    int y;
    static int height;
    static int width;
    private int rowSpacing;
    private int colSpacing;
    Square[][] squares;
    Group lights;
    private Object[] images;

    Board(int level) {
        setupBoard(level);
    }

    @Override
    public String toString() {
        String boardState = "";
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                boardState += squares[row][col].state + " ";
            }
            boardState += "\n";
        }
        return boardState;
    }

    int getWidthInPixels() {
        return Square.width * width + colSpacing * (width - 1);
    }

    int getHeightInPixels() {
        return Square.height * height + rowSpacing * (height - 1);
    }

    private void setSpacing() {
        colSpacing = (int)(16.0 / width);
        rowSpacing = colSpacing;
    }

    private void setupBoard(int level) {
        height = 2 + (level - 1);
        width = height;
        setSpacing();
        int widthInPixels = getWidthInPixels();
        int heightInPixels = getHeightInPixels();
        double centerx = Main.scene.getWidth() / 2;
        double centery = Main.scene.getHeight() / 2;
        this.x = (int)centerx - widthInPixels / 2;
        this.y = (int)centery - heightInPixels / 2;
        squares = new Square[height][width];
        boolean valid;
        do {
            lights = new Group();
            int x = this.x;
            int y = this.y;
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    Square square = new Square(x, y, row, col);
                    squares[row][col] = square;
                    lights.getChildren().add(square);
                    x += Square.width + colSpacing;
                }
                x = this.x;
                y += Square.height + rowSpacing;
            }
            valid = !isCleared();
        } while (!valid);
        images = lights.getChildren().toArray();
    }

    boolean isCleared() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Square square = squares[row][col];
                if (square.isOn()) {
                    return false;
                }
            }
        }
        return true;
    }

    void change(int row, int col) {
        Square square = squares[row][col];
        square.change();
        for (Tuple t : square.adjacentSquares) {
            Square adjacent = squares[t.y][t.x];
            adjacent.change();
        }
        for (int i = 0; i < images.length; i++) {
            lights.getChildren().set(i, (Square)images[i]);
        }
    }

    void resizeSquares() {
        Square.width = (int)(Square.width * 0.8);
        Square.height = Square.width;
    }
}
