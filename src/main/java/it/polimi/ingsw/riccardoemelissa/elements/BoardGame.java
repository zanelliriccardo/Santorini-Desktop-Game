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

    /**
     * Check if a move is possible
     *
     * The method accepts as parameters the starting position of the active worker
     * and the new position where he would like to move and
     * returns if it is a possible move or not
     *
     * @param newpos
     * @param oldpos
     * @return
     */
    public boolean IsAPossibleMove(int[] newpos,int[] oldpos)
    {
        if(!Board[newpos[0]][newpos[1]].GetState())
            return false;
        if(Board[newpos[0]][newpos[1]].GetLevel()==4)
            return false;
        if((GetLevelBox(newpos)-GetLevelBox(oldpos))>1)
            return false;

        return true;
    }

    /**
     * Check if a construction is possible
     *
     * The method accepts as parameters the position of the active worker
     * and the position where he would like to build and
     * returns if it is a possible construction or not
     *
     * @param pos
     * @param workerpos
     * @return
     */
    public boolean IsAPossibleBuild(int[] pos, int[] workerpos)
    {
        if(Math.abs(pos[0]-workerpos[0])>1)
            return false;
        if(Math.abs(pos[1]-workerpos[1])>1)
            return false;
        if(Board[pos[0]][pos[1]].GetLevel()==4)
            return false;
        return true;
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

    public boolean IsABlockedWorker(int[] pos)
    {
        for(int i=pos[0]-1;i<=pos[0]+1;i++)
            for(int j=pos[1]-1;j<pos[1]+1;j++)
            {
                if(IsAPossibleMove(new int[]{i, j},pos))
                    return false;
            }
        return true;
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
     * The method accepts the position of the active worker and
     * the position where he would like to move as parameters and
     * returns if they are two adjacent positions or not
     *
     * @param workerpos
     * @param newpos
     * @return
     */
    public boolean IsAdjacentBox(int[] workerpos, int[] newpos)
    {
        ArrayList<int[]> adj_boxes = AdjacentBoxforBuild(workerpos);

        for ( int[] i : adj_boxes)
        {
            if(Arrays.equals(i, newpos))
                return true;
        }
        return false;
    }

    /**
     * Get the list of the adjacent boxes
     *
     * The method accepts the position of the active worker as a parameter and
     * returns the list of the adjacent positions
     *
     * @param worker_pos
     * @return
     */
    public ArrayList<int[]> AdjacentBoxforBuild(int[] worker_pos)
    {
        ArrayList<int[]> adj_boxes = new ArrayList<>();

        for (int x = worker_pos[0] - 1; x <= worker_pos[0] + 1; x++) {
            for (int y = worker_pos[1] - 1; y <= worker_pos[1] + 1; y++) {
                if (x == worker_pos[0] && y == worker_pos[1])
                    continue;

                if (x > 4 || x < 0)
                    continue;

                if (y > 4 || y < 0)
                    continue;

                if(!GetStateBox(x,y))
                    continue;

                if(GetLevelBox(x,y)==4)
                    continue;

                adj_boxes.add(new int[]{x, y});
            }
        }
        return adj_boxes;
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
     * @param activePlayer
     * @return
     */
    public ArrayList<Worker> getWorkers(Player activePlayer) {
        ArrayList<Worker> workers=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(Board[i][j].GetOccupant().GetProprietary().GetNickname().compareTo(activePlayer.GetNickname())==0)
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
}

