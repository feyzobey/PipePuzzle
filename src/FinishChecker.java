import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FinishChecker {
    private Board board;

    /* Kontroller
        1- End tile kontrolü
            1.1- End tile bağlantı kontrolü ->
        2- Pipe kontrolü
            2.1- Pipe bağlantı kontrolü ->

     */
    public FinishChecker(Board board) {
        this.board = board;
    }

    public FinishChecker() {
    }

    public boolean isGameFinished() {
        Tile starter = board.getStarterTile();

        String starterProp = starter.getProperty();

        if (starterProp.equals(PipeProps.VERTICAL))
            return isVerticalSolutionAvailable();
        else
            return isHorizontalSolutionAvailable();

    }

    private boolean isHorizontalSolutionAvailable() {
        if (isLeftDirectionAvailable(board.getStarterTile())) {
            Tile nextTile = board.getTile(board.getStarterRow(), board.getStarterCol() - 1);
            while (!nextTile.getType().equals("End")) {
                nextTile = getNextTile(nextTile);
                if (nextTile == null)
                    break;
            }
            return nextTile != null;
        }

        if (isRightDirectionAvailable(board.getStarterTile())) {
            Tile nextTile = board.getTile(board.getStarterRow(), board.getStarterCol() + 1);
            while (!nextTile.getType().equals("End")) {
                nextTile = getNextTile(nextTile);
                if (nextTile == null)
                    break;
            }
            return nextTile != null;
        }

        return false;
    }

    private Tile getNextTile(Tile currentTile) {
        String direction = getDirection(currentTile);
        if (direction == null)
            return null;

        switch (direction) {
            case Directions.UP:
                return board.getTile(board.getTileRow(currentTile) - 1, board.getTileCol(currentTile));
            case Directions.DOWN:
                return board.getTile(board.getTileRow(currentTile) + 1, board.getTileCol(currentTile));
            case Directions.RIGHT:
                return board.getTile(board.getTileRow(currentTile), board.getTileCol(currentTile) + 1);
            case Directions.LEFT:
                return board.getTile(board.getTileRow(currentTile), board.getTileCol(currentTile) - 1);
        }
        return null;
    }

    private String getDirection(Tile tile) {
        if (isUpDirectionAvailable(tile))
            return Directions.UP;
        else if (isDownDirectionAvailable(tile))
            return Directions.DOWN;
        else if (isRightDirectionAvailable(tile))
            return Directions.RIGHT;
        else if (isLeftDirectionAvailable(tile))
            return Directions.LEFT;
        return null;
    }

    private boolean isRightDirectionAvailable(Tile tile) {
        int row = board.getTileRow(tile);
        int col = board.getTileCol(tile);
        if (col == board.getSurface().length)
            return false;
        else {
            Tile rightTile = board.getTile(row, col + 1);
            return isPipe(rightTile) && isConnectionEstablished(tile, rightTile);
        }
    }

    private boolean isLeftDirectionAvailable(Tile tile) {
        int row = board.getTileRow(tile);
        int col = board.getTileCol(tile);
        if (col == 0)
            return false;
        else {
            Tile leftTile = board.getTile(row, col - 1);
            return isPipe(leftTile) && isConnectionEstablished(tile, leftTile);
        }
    }

    private boolean isUpDirectionAvailable(Tile tile) {


    }

    private boolean isDownDirectionAvailable(Tile tile) {


    }


    private boolean isConnectionEstablished(Tile tile1, Tile tile2) {
        return isVerConnectionEstablished(tile1, tile2) || isHorConnectionEstablished(tile1, tile2);
    }

    private boolean isHorConnectionEstablished(Tile tile1, Tile tile2) {
        int tile1Col = board.getTileCol(tile1);
        int tile2Col = board.getTileCol(tile2);
        List<String> propsOfRightEntrance = PipeProps.getPropsOfRightEntrance();
        List<String> propsOfLeftEntrance = PipeProps.getPropsOfLeftEntrance();
        if (tile1Col < tile2Col) {
            return propsOfRightEntrance.contains(tile1.getProperty()) && propsOfLeftEntrance.contains(tile2.getProperty());
        } else {
            return propsOfRightEntrance.contains(tile2.getProperty()) && propsOfLeftEntrance.contains(tile1.getProperty());
        }

    }

    private boolean isVerConnectionEstablished(Tile tile1, Tile tile2) {
        int tile1Row = board.getTileRow(tile1);
        int tile2Row = board.getTileRow(tile2);
        List<String> propsOfBottomEntrance = PipeProps.getPropsOfBottomEntrance();
        List<String> propsOfUpEntrance = PipeProps.getPropsOfUpEntrance();
        if (tile1Row < tile2Row) {
            return propsOfUpEntrance.contains(tile1.getProperty()) && propsOfBottomEntrance.contains(tile2.getProperty());
        } else {
            return propsOfUpEntrance.contains(tile2.getProperty()) && propsOfBottomEntrance.contains(tile1.getProperty());
        }
    }

    private boolean isPipe(Tile tile) {
        return tile instanceof Pipe;
    }

    private boolean isVerticalSolutionAvailable() {

    }
}

class PipeProps {
    static final String VERTICAL = "Vertical";
    static final String HORIZONTAL = "Horizontal";
    static final String _00 = "00";
    static final String _01 = "01";
    static final String _10 = "10";
    static final String _11 = "11";

    public static List<String> getPropsOfUpEntrance() {
        return new ArrayList<>(Arrays.asList(_00, _01, VERTICAL));
    }

    public static List<String> getPropsOfBottomEntrance() {
        return new ArrayList<>(Arrays.asList(_10, _11, VERTICAL));
    }


    public static List<String> getPropsOfRightEntrance() {
        return new ArrayList<>(Arrays.asList(_01, _11, HORIZONTAL));
    }

    public static List<String> getPropsOfLeftEntrance() {
        return new ArrayList<>(Arrays.asList(_10, _00, HORIZONTAL));
    }
}

class Directions {
    static final String LEFT = "Left";
    static final String RIGHT = "Right";
    static final String UP = "Up";
    static final String DOWN = "Down";
}
