package it.polimi.ingsw.riccardoemelissa.elements;

import it.polimi.ingsw.riccardoemelissa.ClientHandler;

import java.util.ArrayList;

public class CustomObservable
{
    private ArrayList<CustomObserver> observers=new ArrayList<CustomObserver>();

    public void addObserver(CustomObserver new_observer)
    {
        observers.add(new_observer);
    }

    public void custom_notifyAll(){
        for (CustomObserver obs :
                observers) {
            System.out.println("notifica a :"+obs.toString());
            obs.update(this);
        }
    }
}
