package elements;

public class GodFactory {
    public static God getGod(String god)
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
}
