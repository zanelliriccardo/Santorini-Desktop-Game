package elements;

import java.util.ArrayList;
import java.util.List;

public abstract class GodFactory {
    private List<God> gods;
    private final int N = 9;

    public GodFactory()
    {
        this.gods = new ArrayList<>();

        for (int i = 0; i < this.N; i++) {
            gods.add(createAction());
        }
    }

    abstract protected God createAction();
    // deleghiamo la parte di creazione del god alle sottoclassi


   /*public static God getGod(String god)
    {
        if ( god.equalsIgnoreCase("Apollo") )
            return new Apollo();
        else if ( god.equalsIgnoreCase("Artemis") )
            return new Artemis();
        else if ( god.equalsIgnoreCase("Athena") )
            return new Athena();
        else if ( god.equalsIgnoreCase("Demeter") )
            return new Demeter();
        else if ( god.equalsIgnoreCase("Hephaestus") )
            return new Hephaestus();
        else if ( god.equalsIgnoreCase("Minotaur") )
            return new Minotaur();
        else if ( god.equalsIgnoreCase("Pan") )
            return new Pan();
        else if ( god.equalsIgnoreCase("Prometheus") )
            return new Prometheus();

        return null;
    }

    */
}
