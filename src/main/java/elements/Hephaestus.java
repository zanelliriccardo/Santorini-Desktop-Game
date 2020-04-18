package elements;

public class Hephaestus extends GodFactory {
    @Override
    protected God createAction() {
        return new HephaestusAction();
    }
}
