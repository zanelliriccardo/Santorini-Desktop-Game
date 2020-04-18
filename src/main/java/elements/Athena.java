package elements;

public class Athena extends GodFactory {
    @Override
    protected God createAction() {
        return new AthenaAction();
    }
}
