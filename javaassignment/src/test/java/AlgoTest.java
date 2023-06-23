import service.Algo;

/**
 * This is a subclass of Algo used for testing purposes.
 * It overrides the methods of Algo to track method invocations.
 */
public class AlgoTest extends Algo {
    boolean doAlgoCalled = false;
    boolean cancelTradesCalled = false;
    boolean reverseCalled = false;
    boolean submitToMarketCalled = false;
    boolean performCalcCalled = false;
    int param;
    int value;

    @Override
    public void doAlgo() {
        doAlgoCalled = true;
    }

    @Override
    public void cancelTrades() {
        cancelTradesCalled = true;
    }

    @Override
    public void reverse() {
        reverseCalled = true;
    }

    @Override
    public void submitToMarket() {
        submitToMarketCalled = true;
    }

    @Override
    public void performCalc() {
        performCalcCalled = true;
    }

    @Override
    public void setAlgoParam(int param, int value) {
        this.param = param;
        this.value = value;
    }

    public boolean anyMethodCalled() {
        return doAlgoCalled || cancelTradesCalled || reverseCalled ||
                submitToMarketCalled || performCalcCalled;
    }
}
