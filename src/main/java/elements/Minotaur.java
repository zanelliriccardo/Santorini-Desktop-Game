package elements;

public class Minotaur extends GodFactory {
    @Override
    protected God createAction() {
        return new MinotaurAction();
    }
}