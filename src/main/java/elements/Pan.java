package elements;

public class Pan extends GodFactory {
    @Override
    protected God createAction() {
        return new PanAction();
    }
}
