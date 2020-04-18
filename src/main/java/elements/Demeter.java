package elements;

public class Demeter extends GodFactory {
    @Override
    protected God createAction() {
        return new DemeterAction();
    }
}