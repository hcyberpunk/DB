import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.SignalService;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class SignalServiceTest {
    private static final int TEST_PORT = 8080;
    private AlgoTest algoTest;
    URI uri;


    @BeforeEach
    public void setUp() {

        algoTest = new AlgoTest();
        SignalService signalService = new SignalService(algoTest);
        try {
            signalService.start(TEST_PORT);
        } catch (IOException e) {
            fail("Failed to start the SignalService on port " + TEST_PORT);
        }
    }

    @Test
    public void testProcessSignal_doAlgo() throws IOException {
        sendSignal("doAlgo");
        assertTrue(algoTest.doAlgoCalled);
    }

    @Test
    public void testProcessSignal_cancelTrades() throws IOException {
        sendSignal("cancelTrades");
        assertTrue(algoTest.cancelTradesCalled);
    }

    @Test
    public void testProcessSignal_reverse() throws IOException {
        sendSignal("reverse");
        assertTrue(algoTest.reverseCalled);
    }

    @Test
    public void testProcessSignal_submitToMarket() throws IOException {
        sendSignal("submitToMarket");
        assertTrue(algoTest.submitToMarketCalled);
    }

    @Test
    public void testProcessSignal_performCalc() throws IOException {
        sendSignal("performCalc");
        assertTrue(algoTest.performCalcCalled);
    }

    @Test
    public void testProcessSignal_setAlgoParam() throws IOException {
        sendSignal("setAlgoParam,1,100");
        assertEquals(1, algoTest.param);
        assertEquals(100, algoTest.value);
    }

    @Test
    public void testProcessSignal_invalidSignal() throws IOException {
        sendSignal("invalidSignal");
        assertFalse(algoTest.anyMethodCalled());
    }

    private void sendSignal(String signal) throws IOException {
        try {
            uri = new URI("http", null, "localhost", TEST_PORT, "/signal", null, null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create URL", e);
        }

        URL url = uri.toURL();


        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.getOutputStream().write(signal.getBytes(StandardCharsets.UTF_8));
        int responseCode = connection.getResponseCode();
        connection.disconnect();
        assertEquals(200, responseCode);
    }
}
