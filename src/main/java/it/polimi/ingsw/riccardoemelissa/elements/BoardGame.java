package it.polimi.ingsw.riccardoemelissa.elements;

import java.io.Serializable;
import java.util.ArrayList;

public class BoardGame extends CustomObservable implements Serializable {
    private Box[][] Board;
    private Player active_player;
    private boolean gameover=false;

    public BoardGame(){
        Box[][] boxes = new Box[5][5];
        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes.length; j++)
                boxes[i][j] = new Box(true, 0);
        }
        Board=boxes;
    }

    /**
     * Get the state of a box
     *
     * The method accepts a position as a parameter and
     * returns the state of the box associated with the required position
     *
     * @param pos
     * @return
     */
    public boolean getStateBox(int[] pos)
    {
        return Board[pos[0]][pos[1]].getState();
    }

    /**
     * Get the state of a box
     *
     * The method accepts two coordinates as parameters and
     * returns the state of the box associated
     * with the position indicated by the coordinates
     *
     * @param i
     * @param j
     * @return
     */
    public boolean getStateBox(int i, int j)
    {
        return Board[i][j].getState();
    }

    /**
     * Get the construction level of a box
     *
     * The method accepts a position as a parameter and
     * returns the construction level of the box associated with the position
     *
     * @param pos
     * @return
     */
    public int getLevelBox(int[] pos)
    {
        return Board[pos[0]][pos[1]].getLevel();
    }

    /**
     * Get the construction level of a box
     *
     * The method takes two coordinates as parameters and
     * returns the construction level of the box associated
     * with the position indicated by the coordinates
     *
     * @param i
     * @param j
     * @return
     */
    public int getLevelBox(int i, int j)
    {
        return Board[i][j].getLevel();
    }

    /**Building a block
     *
     * The method implements the construction of a block
     *
     * @param pos
     */
    public void doBuild(int[] pos)
    {
        Board[pos[0]][pos[1]].build();
    }

    /**
     * Get who occupies a box
     *
     * The method accepts a position as a parameter and
     * returns the worker occupying the box associated
     * with the given position
     *
     * @param pos
     * @return
     */
    public Worker getOccupant(int[] pos)
    {
        return Board[pos[0]][pos[1]].getOccupant();
    }

    /**
     *  Get who occupies a box
     *
     * The method accepts two coordinates as parameters and
     * returns the worker occupying the box associated
     * with the position indicated by the coordinates
     *
     * @param i
     * @param j
     * @return
     */
    public Worker getOccupant(int i, int j) {
        return Board[i][j].getOccupant();
    }

    public Player getOccupantProprietary(int[] pos)
    {
        return Board[pos[0]][pos[1]].getOccupant().getProprietary();
    }

    public Player getOccupantProprietary(int i, int j)
    {
        return Board[i][j].getOccupant().getProprietary();
    }



    /**
     * Fill a box
     *
     * The method accepts a position and a worker as parameters
     * and places the worker as the occupant
     * of the box associated with the given position
     *
     * @param pos
     * @param worker
     */
    public void setOccupant(int[] pos, Worker worker)
    {
        Board[pos[0]][pos[1]].setOccupant(worker);
    }

    /**
     * Build a dome
     *
     * The method accepts a position as a parameter and
     * builds a dome in the given position
     *
     * @param pos
     * @return
     */
    public boolean buildDome(int[] pos)
    {
        Board[pos[0]][pos[1]].setDome();
        return true;
    }

    /**
     * Get the board
     *
     * @return
     */
    public Box[][] getBoard()
    {
        return Board;
    }

    /**
     * Declare the active player
     *
     * The method accepts a player as parameter and
     * sets him as the active player
     *
     * @param player
     */
    public void setActivePlayer(Player player)
    {
        active_player=player;
    }

    /**
     * Get the workers of a player
     *
     * The method accepts a player as a parameter and
     * returns his workers
     *
     * @return
     */
    public ArrayList<Worker> getWorkers() {
        ArrayList<Worker> workers=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(Board[i][j].getOccupant().getProprietary().getNickname().compareTo(active_player.getNickname())==0)
                    workers.add(Board[i][j].getOccupant());
            }
        }
        return workers;
    }

    /**
     * Remove a worker from a box
     *
     * The method accepts two coordinates as parameters and
     * removes the worker that occupies the box associated with the position indicates by the coordinates
     *
     * @param i
     * @param j
     */
    public void removeWorker(int i, int j) {
        Board[i][j].removeOccupant();
    }

    /**
     * Remove a worker from a box
     *
     *The method accepts a position as a parameter and
     * removes the worker that occupies the box associated with the given position
     * @param pos
     */
    public void removeWorker(int[] pos) {
        Board[pos[0]][pos[1]].removeOccupant();
    }

    /**
     * Set game over
     * 
     * @param b
     */
    public void setGameOver(boolean b) {
        gameover=b;
    }

    public void setBoxes(Box[][] boxes) {
        Board=boxes;
    }

    public Player getActivePlayer() {
        return active_player;
    }

    public boolean getGameover() {
        return gameover;
    }
}

