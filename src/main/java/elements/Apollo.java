package elements;

public class Apollo extends GodFactory {
    @Override
    protected God createAction() {
        return new ApolloAction();
    }
}