import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileNotFoundException;

public class BoardMaker {
    private Board board;
    private FileReader fileReader;
    private int currentLevelNo;
    public int numberOfMovements = 0;
    Button button;


    public BoardMaker() {
        button = new Button("NUMBER OF MOVES " + numberOfMovements);
        fileReader = new FileReader();
        initializeLevel();
    }

    public BoardMaker(Board board) {
        this.board = board;
        button = new Button("NUMBER OF MOVES " + numberOfMovements);
        this.fileReader = new FileReader();
        initializeLevel();
    }


    private void initializeLevel() {
        currentLevelNo = 0;

    }

    public void createBoard() throws FileNotFoundException {

        incrementLevel();
        Tile newTile;

        fileReader.setFileAndScanner(new File(getPathcurrentLevel()));
        while (fileReader.hasNextLine()) {
            newTile = createTiles(fileReader.getNextLine());
            addımages(newTile);
            board.placeTileAndAppendToPane(newTile);
        }
        setOnMouseSwipeEvents();
    }


    private String getPathcurrentLevel() {
        return "src/level" + currentLevelNo + ".txt";
    }

    private void incrementLevel() {
        currentLevelNo++;

    }

    private Tile createTiles(String inputLine) {
        String[] linesplit = inputLine.split(",");
        int id = Integer.parseInt(linesplit[0]);
        String type = linesplit[1];
        String property = linesplit[2];
        switch (type) {
            case "Starter":
                return new Starter(id, type, property);

            case "End":
                return new End(id, type, property);

            case "Empty":
                if (property.equals("none")) {
                    return new Pipe(id, type, property);
                } else
                    return new Empty(id, type, property);


            case "PipeStatic": {
                if (property.equals("Vertical") || property.equals("Horizontal"))
                    return new PipeStatic(id, type, property);
                else {
                    return new CurvedPipeStatic(id, type, property);
                }
            }
            case "Pipe": {
                if (property.equals("none"))
                    return new Pipe(id, type, property);
                else
                    return new CurvedPipe(id, type, property);
            }


        }
        return null;
    }


    private void addımages(Tile newTile) {
        String type = newTile.getType();
        String property = newTile.getProperty();
        String typePro = type + property;
        switch (typePro) {
            case "StarterVertical":
                newTile.getChildren().add(new ImageView(new Image("images/STARTERVERTICAL.PNG", 150, 150, true, true)));
                break;
            case "StarterHorizontal":
                newTile.getChildren().add(new ImageView(new Image("images/STARTERHORIZONTAL.PNG", 150, 150, true, true)));
                break;
            case "PipeVertical":
                newTile.getChildren().add(new ImageView(new Image("images/PIPEVERTICAL.jpeg", 150, 150, true, true)));
                break;
            case "PipeHorizontal":
                newTile.getChildren().add(new ImageView(new Image("images/PIPEHORIZONTAL.jpeg", 150, 150, true, true)));
                break;
            case "Pipe00":
                newTile.getChildren().add(new ImageView(new Image("images/CURVED00.jpeg", 150, 150, true, true)));
                break;
            case "Pipe01":
                newTile.getChildren().add(new ImageView(new Image("images/CURVED01.jpeg", 150, 150, true, true)));
                break;
            case "Pipe10":
                newTile.getChildren().add(new ImageView(new Image("images/CURVED10.jpeg", 150, 150, true, true)));
                break;
            case "Pipe11":
                newTile.getChildren().add(new ImageView(new Image("images/CURVED11.jpeg", 150, 150, true, true)));
                break;
            case "PipeStaticHorizontal":
                newTile.getChildren().add(new ImageView(new Image("images/PIPESTATICHORIZONTAL.jpeg", 150, 150, true, true)));
                break;
            case "PipeStaticVertical":
                newTile.getChildren().add(new ImageView(new Image("images/PIPESTATICVERTICAL.jpeg", 150, 150, true, true)));
                break;
            case "PipeStatic01":
                newTile.getChildren().add(new ImageView(new Image("images/PIPESTATIC01.jpeg", 150, 150, true, true)));
                break;
            case "Emptynone":
                newTile.getChildren().add(new ImageView(new Image("images/EMPTYNONE.jpeg", 150, 150, true, true)));
                break;
            case "EmptyFree":
                newTile.getChildren().add(new ImageView(new Image("images/EMPTYFREE.jpeg", 150, 150, true, true)));
                break;
            case "EndHorizontal":
                newTile.getChildren().add(new ImageView(new Image("images/ENDHORIZONTAL.PNG", 150, 150, true, true)));
                break;
            case "EndVertical":
                newTile.getChildren().add(new ImageView(new Image("images/ENDVERTICAL.PNG", 150, 150, true, true)));
                break;
        }
    }

    public void setOnMouseSwipeEvents() {
        Tile[][] boardSurface = board.getSurface();
        Tile tile;
        for (Tile[] tiles : boardSurface) {
            for (Tile value : tiles) {
                tile = value;
                if (tile.isMovable())
                    setOnSwipeEvent(tile);
            }
        }
    }

    public void setOnSwipeEvent(Tile tile) {
        int group = getGroup(tile);
        switch (group) {
            case 1:
                setEventsForGroup1(tile);
                break;
            case 2:
                setEventsForGroup2(tile);
                break;
            case 3:
                setEventsForGroup3(tile);
                break;
            case 4:
                setEventsForGroup4(tile);
                break;
            case 5:
                setEventsForGroup5(tile);
                break;
            case 6:
                setEventsForGroup6(tile);
                break;
            case 7:
                setEventsForGroup7(tile);
                break;
            case 8:
                setEventsForGroup8(tile);
                break;
            case 9:
                setEventsForGroup9(tile);
                break;

        }
    }

    private void setEventsForGroup9(Tile tile) {
        setOnMouseSwipeLeftEvent(tile);
        setOnMouseSwipeRightEvent(tile);
        setOnMouseSwipeUpEvent(tile);
        setOnMouseSwipeDownEvent(tile);
    }

    private void setEventsForGroup8(Tile tile) {
        setOnMouseSwipeLeftEvent(tile);
        setOnMouseSwipeRightEvent(tile);
        setOnMouseSwipeUpEvent(tile);
    }

    private void setEventsForGroup7(Tile tile) {
        setOnMouseSwipeLeftEvent(tile);
        setOnMouseSwipeUpEvent(tile);
        setOnMouseSwipeDownEvent(tile);
    }

    private void setEventsForGroup6(Tile tile) {
        setOnMouseSwipeRightEvent(tile);
        setOnMouseSwipeUpEvent(tile);
        setOnMouseSwipeDownEvent(tile);
    }

    private void setEventsForGroup5(Tile tile) {
        setOnMouseSwipeLeftEvent(tile);
        setOnMouseSwipeRightEvent(tile);
        setOnMouseSwipeDownEvent(tile);
    }

    private void setEventsForGroup4(Tile tile) {
        setOnMouseSwipeLeftEvent(tile);
        setOnMouseSwipeUpEvent(tile);
    }

    private void setEventsForGroup3(Tile tile) {
        setOnMouseSwipeRightEvent(tile);
        setOnMouseSwipeUpEvent(tile);
    }

    private void setEventsForGroup2(Tile tile) {
        setOnMouseSwipeDownEvent(tile);
        setOnMouseSwipeLeftEvent(tile);
    }

    private void setEventsForGroup1(Tile tile) {
        setOnMouseSwipeDownEvent(tile);
        setOnMouseSwipeRightEvent(tile);
    }

    private int getGroup(Tile tile) {
        int row = board.getTileRow(tile);
        int col = board.getTileCol(tile);
        String rowCol = "" + row + col;
        int group;
        switch (rowCol) {
            case "00":
                group = 1;
                break;
            case "03":
                group = 2;
                break;
            case "30":
                group = 3;
                break;
            case "33":
                group = 4;
                break;
            case "01":
            case "02":
                group = 5;
                break;
            case "10":
            case "20":
                group = 6;
                break;
            case "13":
            case "23":
                group = 7;
                break;
            case "31":
            case "32":
                group = 8;
                break;
            default:
                group = 9;
                break;
        }

        return group;
    }

    private void setOnMouseSwipeLeftEvent(Tile tile) {
        int row = board.getTileRow(tile);
        int col = board.getTileCol(tile);
        Tile leftTile = board.getSurface()[row][col - 1];
        if (isSwipeValid(tile, leftTile)) {
            setOnMousePressedEvent(tile);
            setOnMouseReleasedEvent(tile);
        }
    }

    private void setOnMouseSwipeRightEvent(Tile tile) {
        int row = board.getTileRow(tile);
        int col = board.getTileCol(tile);
        Tile rightTile = board.getSurface()[row][col + 1];
        if (isSwipeValid(tile, rightTile)) {
            tile.setEventName("Swipe Right");
            setOnMousePressedEvent(tile);
            setOnMouseReleasedEvent(tile);
        }
    }

    private void setOnMouseSwipeUpEvent(Tile tile) {
        int row = board.getTileRow(tile);
        int col = board.getTileCol(tile);
        Tile upTile = board.getSurface()[row - 1][col];
        if (isSwipeValid(tile, upTile)) {
            tile.setEventName("Swipe Up");
            setOnMousePressedEvent(tile);
            setOnMouseReleasedEvent(tile);
        }
    }

    private void setOnMouseSwipeDownEvent(Tile tile) {
        int row = board.getTileRow(tile);
        int col = board.getTileCol(tile);
        Tile downTile = board.getSurface()[row + 1][col];
        if (isSwipeValid(tile, downTile)) {
            tile.setEventName("Swipe Down");
            setOnMousePressedEvent(tile);
            setOnMouseReleasedEvent(tile);
        }
    }

    private void setOnMouseReleasedEvent(Tile tile) {
        tile.setOnMouseReleased(e -> {
            Tile releasedTile = getReleasedTile(e.getSceneX(), e.getSceneY());
            board.setReleasedTile(releasedTile);

            // x, y değerler için null check yapmayı unutmayın
            if (areTilesConsecutive(board.getPressedTile(), board.getReleasedTile())) {
                System.out.println("released x: " + e.getSceneX() + ", y: " + e.getSceneY());
                swapTiles(board.getPressedTile(), board.getReleasedTile());


                numberOfMovements++;
                button.setText("NUMBER OF MOVES " + numberOfMovements);

                board.refresh();
                // board.setTilesToPane()
                clearOnMouseSwipeEvents();
                setOnMouseSwipeEvents();
                System.out.println("Number of movements : " + numberOfMovements);
            }
        });
    }

    private void clearOnMouseSwipeEvents() {
        Tile[][] boardSurface = board.getSurface();
        Tile tile;
        for (Tile[] tiles : boardSurface) {
            for (Tile value : tiles) {
                tile = value;
                if (tile.getOnMousePressed() != null)
                    tile.removeEventHandler(MouseEvent.MOUSE_PRESSED, tile.getOnMousePressed());
                if (tile.getOnMousePressed() != null)
                    tile.removeEventHandler(MouseEvent.MOUSE_RELEASED, tile.getOnMouseReleased());
            }
        }
    }

    private Tile getReleasedTile(double x, double y) {
        int col = (int) (x / 150);
        int row = (int) (y / 150);


        if (row <= 3 && col <= 3)
            return board.getSurface()[row][col];
        return null;
    }

    private void setOnMousePressedEvent(Tile tile) {
        tile.setOnMousePressed(e -> {
            System.out.println("pressed x: " + e.getSceneX() + ", y: " + e.getSceneY());
            board.setPressedTile(tile);
        });
    }

    private void swapTiles(Tile pressedTile, Tile releasedTile) {
        if (isSwipeValid(pressedTile, releasedTile)) {
            if (areTilesOnTopOfEachOther(pressedTile, releasedTile))
                swapTilesVertically(pressedTile, releasedTile);
            else if (areTilesSideBySide(pressedTile, releasedTile))
                swapTilesHorizontally(pressedTile, releasedTile);
        }
    }

    private void swapTilesHorizontally(Tile firstTile, Tile secondTile) {
        int row = board.getTileRow(firstTile);
        int firstTileCol = board.getTileCol(firstTile);
        int secondTileCol = board.getTileCol(secondTile);
        Tile[][] surface = board.getSurface();
        surface[row][firstTileCol] = secondTile;
        surface[row][secondTileCol] = firstTile;
    }

    private void swapTilesVertically(Tile firstTile, Tile secondTile) {
        int firstTileRow = board.getTileRow(firstTile);
        int secondTileRow = board.getTileRow(secondTile);
        int col = board.getTileCol(firstTile);
        Tile[][] surface = board.getSurface();
        surface[firstTileRow][col] = secondTile;
        surface[secondTileRow][col] = firstTile;
    }

    private boolean areTilesConsecutive(Tile pressedTile, Tile releasedTile) {
        return areTilesOnTopOfEachOther(pressedTile, releasedTile)
                || areTilesSideBySide(pressedTile, releasedTile);

    }


    private boolean areTilesSideBySide(Tile pressedTile, Tile releasedTile) {
        return Math.abs(board.getTileCol(pressedTile) - board.getTileCol(releasedTile)) == 1
                && Math.abs(board.getTileRow(pressedTile) - board.getTileRow(releasedTile)) == 0;
    }

    private boolean areTilesOnTopOfEachOther(Tile pressedTile, Tile releasedTile) {
        return Math.abs(board.getTileRow(pressedTile) - board.getTileRow(releasedTile)) == 1
                && Math.abs(board.getTileCol(pressedTile) - board.getTileCol(releasedTile)) == 0;
    }

    private boolean isSwipeValid(Tile tile1, Tile tile2) {
        return tile1.isEmptyFreeTile() && tile2.isMovable() || tile1.isMovable() && tile2.isEmptyFreeTile();
    }


    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public FileReader getFileReader() {
        return fileReader;
    }

    public void setFileReader(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public int getCurrentLevelNo() {
        return currentLevelNo;
    }

    public void setCurrentLevelNo(int currentLevelNo) {
        this.currentLevelNo = currentLevelNo;
    }

    public int getNumberOfMovements() {
        return numberOfMovements;
    }

    public void setNumberOfMovements(int numberOfMovements) {
        this.numberOfMovements = numberOfMovements;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}