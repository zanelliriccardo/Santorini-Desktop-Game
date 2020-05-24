package it.polimi.ingsw.riccardoemelissa.elements;

import it.polimi.ingsw.riccardoemelissa.ClientHandler;

import java.util.ArrayList;

/**
 * Redefinition of java observer because it's deprecated
 *
 */
public class CustomObservable
{
    private ArrayList<CustomObserver> observers=new ArrayList<CustomObserver>();

    public void addObserver(CustomObserver new_observer)
    {
        observers.add(new_observer);
    }

    public synchronized void custom_notifyAll(){
        for (CustomObserver obs :
                observers) {
            obs.update(this);
        }
    }

    public void removeObserver(ClientHandler clientHandler) {
        observers.remove(clientHandler);
    }
}
