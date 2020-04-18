package elements;

public class Atlas extends GodFactory {
    @Override
    protected God createAction() {
        return new AtlasAction();
    }
}