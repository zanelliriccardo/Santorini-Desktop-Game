package it.polimi.ingsw.riccardoemelissa.client;

import java.io.Serializable;

public class Position implements Serializable {
    private int x;
    private int y;

    public Position(int[] new_position) {
        x=new_position[0];
        y=new_position[1];
    }
}
