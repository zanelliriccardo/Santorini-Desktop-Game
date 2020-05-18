package it.polimi.ingsw.riccardoemelissa.elements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

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
    public boolean GetStateBox (int[] pos)
    {
        return Board[pos[0]][pos[1]].GetState();
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
    public boolean GetStateBox (int i,int j)
    {
        return Board[i][j].GetState();
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
    public int GetLevelBox (int[] pos)
    {
        return Board[pos[0]][pos[1]].GetLevel();
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
    public int GetLevelBox(int i, int j)
    {
        return Board[i][j].GetLevel();
    }

    /**Building a block
     *
     * The method implements the construction of a block
     *
     * @param pos
     */
    public void DoBuild(int[] pos)
    {
        Board[pos[0]][pos[1]].Build();
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
    public Worker GetOccupant(int[] pos)
    {
        return Board[pos[0]][pos[1]].GetOccupant();
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
    public Worker GetOccupant(int i, int j) {
        return Board[i][j].GetOccupant();
    }

    public Player GetOccupantProprietary(int[] pos)
    {
        return Board[pos[0]][pos[1]].GetOccupant().GetProprietary();
    }

    public Player GetOccupantProprietary(int i,int j)
    {
        return Board[i][j].GetOccupant().GetProprietary();
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
        Board[pos[0]][pos[1]].SetOccupant(worker);
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
    public boolean BuildDome(int[] pos)
    {
        Board[pos[0]][pos[1]].SetDome();
        return true;
    }

    /**
     * Get the board
     *
     * @return
     */
    public Box[][] GetBoard()
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
                if(Board[i][j].GetOccupant().GetProprietary().GetNickname().compareTo(active_player.GetNickname())==0)
                    workers.add(Board[i][j].GetOccupant());
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

