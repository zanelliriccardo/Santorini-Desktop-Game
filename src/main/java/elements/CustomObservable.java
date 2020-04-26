package elements;

import java.util.ArrayList;

public class CustomObservable
{
    private ArrayList<CustomObserver> observers=new ArrayList<CustomObserver>();

    public void addObserver(CustomObserver new_observer)
    {
        observers.add(new_observer);
    }

    public void custom_notifyAll()
    {
        for (CustomObserver obs :
                observers) {
            obs.update(this);
        }
    }
}
