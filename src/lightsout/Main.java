package lightsout;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Main extends Application {
    static final String TITLE = "Lights Out";
    static Stage win;
    static Scene scene;
    static final int WIDTH = 600;
    static final int HEIGHT = 600;
    static int level = 1;
    static int numMoves = 0;
    static ArrayList<Integer> scores;
    static Group allGraphics;
    static Group text;
    static Group buttons;
    static Board board;
    static boolean mouseInputEnabled;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage win) throws Exception {
        Main.win = win;
        scores = new ArrayList<>();
        setupGraphics();
        setupWindow(win);
        setupTitleScreen();
        draw(win);
    }

    static void setupGraphics() {
        allGraphics = new Group();
        text = new Group();
        buttons = new Group();
        addAllToGroup(allGraphics, new Node[]{text, buttons});
    }

    static void setupWindow(Stage win) {
        win.setResizable(false);
        win.setTitle(TITLE);
        win.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                handleMouseClick(event);
            }
        });
        scene = new Scene(allGraphics, WIDTH, HEIGHT);
        scene.setFill(Color.rgb(43, 43, 43));
        win.setScene(scene);
        win.centerOnScreen();
    }

    static void setupTitleScreen() {
        double width;
        double height;
        Text t = new Text(TITLE.toUpperCase());
        t.setFont(new Font(72));
        width = t.getLayoutBounds().getWidth();
        t.setX(WIDTH / 2.0 - width / 2);
        t.setY(250);
        t.setFill(Color.YELLOW);
        t.setStroke(Color.BLACK);
        addToGroup(text, t);

        Button newGameButton = new Button("New Game");
        newGameButton.setPrefSize(150, 50);
        newGameButton.setFont(new Font(18));
        width = newGameButton.getPrefWidth();
        height = newGameButton.getPrefHeight();
        newGameButton.setLayoutX(WIDTH / 2.0 - width / 2.0);
        newGameButton.setLayoutY(HEIGHT / 2.0 + height / 2.0);
        newGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getButton() == MouseButton.PRIMARY) {
                    startNewGame();
                }
            }
        });
        addToGroup(buttons, newGameButton);
    }

    static void draw(Stage win) {
        win.show();
    }

    static void startNewGame() {
        double width;
        numMoves = 0;
        if (level > scores.size()) {
            scores.add(numMoves);
        }

        clearGroup(text);
        Text t = new Text("LEVEL " + level);
        t.setFont(new Font(72));
        width = t.getLayoutBounds().getWidth();
        t.setX(WIDTH / 2.0 - width / 2.0);
        t.setY(HEIGHT / 6.0);
        t.setFill(Color.YELLOW);
        t.setStroke(Color.BLACK);
        addToGroup(text, t);

        createBoard(level);
        addToGroup(allGraphics, board.lights);

        Text t1 = new Text("Best score: " + getBestScore());
        t1.setFont(new Font(36));
        width = t1.getLayoutBounds().getWidth();
        t1.setX(WIDTH / 2.0 - width / 2.0);
        t1.setY(t.getY() + 50);
        t1.setFill(Color.YELLOW);
        t1.setStroke(Color.BLACK);
        addToGroup(text, t1);

        Text t2 = new Text("Moves: " + numMoves);
        t2.setFont(new Font(36));
        width = t2.getLayoutBounds().getWidth();
        t2.setX(WIDTH / 2.0 - width / 2.0);
        t2.setY(board.y + board.getHeightInPixels() + 50);
        t2.setFill(Color.YELLOW);
        t2.setStroke(Color.BLACK);
        addToGroup(text, t2);

        clearGroup(buttons);
        Button resetBoardButton = new Button("Reset");
        resetBoardButton.setPrefSize(150, 50);
        resetBoardButton.setFont(new Font(18));
        width = resetBoardButton.getPrefWidth();
        resetBoardButton.setLayoutX(WIDTH / 2.0 - width / 2.0);
        resetBoardButton.setLayoutY(HEIGHT - HEIGHT / 6.0);
        resetBoardButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getButton() == MouseButton.PRIMARY) {
                    numMoves = 0;
                    Text t = (Text)text.getChildren().get(2);
                    t.setText("Moves: " + numMoves);
                    double width = t.getLayoutBounds().getWidth();
                    t.setX(WIDTH / 2.0 - width / 2.0);
                    resetBoard();
                }
            }
        });
        addToGroup(buttons, resetBoardButton);

        enableMouseInput();
    }

    static void createBoard(int level) {
        board = new Board(level);
    }

    static void resetBoard() {
        clearGroup(board.lights);
        createBoard(level);
        addToGroup(allGraphics, board.lights);
    }

    static void handleMouseClick(MouseEvent click) {
        if (click.getButton() == MouseButton.PRIMARY) {
            if (hasClickedOnSquare(click)) {
                for (int i = 0; i < Board.height; i++) {
                    for (int j = 0; j < Board.width; j++) {
                        Square light = board.squares[i][j];
                        if (mouseInputEnabled && inBounds(click, light)) {
                            numMoves++;
                            Text t = (Text)text.getChildren().get(2);
                            t.setText("Moves: " + numMoves);
                            double width = t.getLayoutBounds().getWidth();
                            t.setX(WIDTH / 2.0 - width / 2.0);
                            board.change(light.row, light.col);
                            if (board.isCleared()) {
                                disableMouseInput();
                                displayLevelClearedMessage();
                                updateScores();
                            }
                        }
                    }
                }
            }
        }
    }

    static boolean hasClickedOnSquare(MouseEvent click) {
        return click.getTarget() instanceof Square;
    }

    static boolean inBounds(MouseEvent click, Square square) {
        return click.getSceneX() > square.left &&
               click.getSceneX() < square.right &&
               click.getSceneY() > square.top &&
               click.getSceneY() < square.bottom;
    }

    static void displayLevelClearedMessage() {
        Text t = (Text)text.getChildren().get(0);
        t.setText("LEVEL CLEARED!");
        double width = t.getLayoutBounds().getWidth();
        t.setX(WIDTH / 2.0 - width / 2);
        t.setY(HEIGHT / 6.0);

        clearGroup(buttons);
        Button button1 = new Button("Quit");
        button1.setPrefSize(100, 25);
        button1.setFont(new Font(16));
        button1.setLayoutX(WIDTH / 6.0);
        button1.setLayoutY(HEIGHT - HEIGHT / 6.0);

        Button button2 = new Button("Next Level");
        button2.setPrefSize(100, 25);
        button2.setFont(new Font(16));
        button2.setLayoutX(WIDTH * 2.0 / 3.0);
        button2.setLayoutY(HEIGHT - HEIGHT / 6.0);

        setButtonEvents(button1, button2);

        addAllToGroup(buttons, new Button[]{button1, button2});
    }

    static void setButtonEvents (Button b1, Button b2) {
        b1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getButton() == MouseButton.PRIMARY) {
                    returnToTitle();
                }
            }
        });
        b2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {
                if (click.getButton() == MouseButton.PRIMARY) {
                    nextLevel();
                }
            }
        });
    }

    static int getBestScore() {
        return scores.get(level - 1);
    }

    static void updateScores() {
        if (scores.get(level - 1) == 0) {
            scores.set(level - 1, numMoves);
        }
        else if (numMoves < scores.get(level - 1)) {
            scores.set(level - 1, numMoves);
        }
        Text t = (Text)text.getChildren().get(1);
        t.setText("Best score: " + scores.get(level - 1));
        double width = t.getLayoutBounds().getWidth();
        t.setX(WIDTH / 2.0 - width / 2.0);
    }

    static void returnToTitle() {
        reset();
        setupTitleScreen();
    }

    static void reset() {
        level = 1;
        Square.width = 100;
        Square.height = Square.width;
        clearGroups(new Group[]{text, buttons, board.lights});
    }

    static void nextLevel() {
        level++;
        clearGroup(board.lights);
        board.resizeSquares();
        startNewGame();
    }

    static void enableMouseInput() {
        mouseInputEnabled = true;
    }
    static void disableMouseInput() {
        mouseInputEnabled = false;
    }

    static void addToGroup(Group g, Node n) {
        g.getChildren().add(n);
    }
    static void removeFromGroup(Group g, Node n) {
        g.getChildren().remove(n);
    }
    static void clearGroup(Group g) {
        g.getChildren().clear();
    }
    static void clearGroups(Group[] args) {
        for (Group g : args) {
            clearGroup(g);
        }
    }
    static void addAllToGroup(Group g, Node[] args) {
        g.getChildren().addAll(args);
    }
    static void removeAllFromGroup(Group g, Node[] args) {
        g.getChildren().removeAll(args);
    }

    //Debug method
    static void printObjectType(MouseEvent click) {
        String source = click.getSource().getClass().getSimpleName();
        String target = click.getTarget().getClass().getSimpleName();
        System.out.println("Clicked " + target + " at: (" + click.getSceneX() + ", " + click.getSceneY() + ")");
    }
}
