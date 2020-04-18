package elements;

public class Prometheus extends GodFactory {
    @Override
    protected God createAction() {
        return new PrometheusAction();
    }
}