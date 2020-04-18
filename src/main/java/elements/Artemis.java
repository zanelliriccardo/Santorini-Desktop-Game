package elements;

public class Artemis extends GodFactory {
    @Override
    protected God createAction() {
        return new ArtemisAction();
    }
}
