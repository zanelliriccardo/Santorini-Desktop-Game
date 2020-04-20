package it.polimi.ingsw.riccardoemelissa;

import java.util.Observable;

public class Controller extends Observable
{
    private static GameState game = null;

    public void initializeGame(int numplayer)
    {
        game.setNumplayer(numplayer);
        notifyAll();
    }

}
