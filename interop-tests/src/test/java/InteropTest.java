import com.google.common.truth.Truth;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.jms.Connection;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

class InteropTestJava {
//    @Test
//    void testComparingMapWithNullValues_j10() {
//        Map<String, String> n = new HashMap<>();
//        n.put("key", null);
//        Truth.assertThat(Map.of("key", "value")).containsExactlyEntriesIn(n);
//    }

    @Test
    void testComparingMapWithNullValues() {
        Map<String, String> m = new HashMap<>();
        Map<String, String> n = new HashMap<>();
        m.put("key", "value");
        n.put("key", null);
        Truth.assertThat(m).containsExactlyEntriesIn(n);
    }

    /**
     * Queue name is myq, that is hardcoded in the protocol payload
     *
     * @throws Exception
     */
    @Tag("external")
    @Test
    void testSendRhealikeMessage() throws Exception {
        sendRhealikeMessageToQueue();
        ActiveMQConnectionFactory owcf = new ActiveMQConnectionFactory();
        Connection owc = owcf.createConnection();
        Session ows = owc.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        MessageConsumer owr = ows.createConsumer(ows.createQueue("myq"));
        if (owr.receive(1000) == null) {
            System.out.println("failed to receive a message using openwire, trying amqp now");

            JmsConnectionFactory acf = new JmsConnectionFactory();
            Connection ac = acf.createConnection();
            Session as = ac.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            MessageConsumer ar = as.createConsumer(as.createQueue("myq"));
            System.out.println("qpid-jms says " + ar.receive(1000));

            fail("OpenWire did not receive the message");
        }

    }

    /**
     * Queue name is myq, that is hardcoded in the protocol payload
     */
    private void sendRhealikeMessageToQueue() throws IOException, InterruptedException {
        int peer0_2[] = {
            0x41, 0x4d, 0x51, 0x50, 0x00, 0x01, 0x00, 0x00};
        int peer0_3[] = {
            0x00, 0x00, 0x00, 0x3a, 0x02, 0x00, 0x00, 0x00,
            0x00, 0x53, 0x10, 0xd0, 0x00, 0x00, 0x00, 0x2a,
            0x00, 0x00, 0x00, 0x01, 0xa1, 0x24, 0x38, 0x62,
            0x32, 0x32, 0x35, 0x37, 0x63, 0x31, 0x2d, 0x62,
            0x62, 0x36, 0x36, 0x2d, 0x39, 0x39, 0x34, 0x35,
            0x2d, 0x39, 0x62, 0x61, 0x33, 0x2d, 0x32, 0x63,
            0x34, 0x39, 0x39, 0x61, 0x31, 0x35, 0x38, 0x66,
            0x65, 0x35};
        int peer0_4[] = {
            0x00, 0x00, 0x00, 0x20, 0x02, 0x00, 0x00, 0x00,
            0x00, 0x53, 0x11, 0xd0, 0x00, 0x00, 0x00, 0x10,
            0x00, 0x00, 0x00, 0x04, 0x40, 0x43, 0x70, 0x00,
            0x00, 0x08, 0x00, 0x70, 0xff, 0xff, 0xff, 0xff};
        int peer0_5[] = {
            0x00, 0x00, 0x00, 0x56, 0x02, 0x00, 0x00, 0x00,
            0x00, 0x53, 0x12, 0xd0, 0x00, 0x00, 0x00, 0x46,
            0x00, 0x00, 0x00, 0x0a, 0xa1, 0x24, 0x30, 0x39,
            0x62, 0x35, 0x62, 0x66, 0x33, 0x63, 0x2d, 0x37,
            0x65, 0x66, 0x63, 0x2d, 0x34, 0x34, 0x34, 0x39,
            0x2d, 0x61, 0x36, 0x63, 0x35, 0x2d, 0x38, 0x37,
            0x32, 0x32, 0x36, 0x32, 0x63, 0x39, 0x62, 0x36,
            0x33, 0x35, 0x43, 0x42, 0x40, 0x40, 0x00, 0x53,
            0x28, 0x45, 0x00, 0x53, 0x29, 0xd0, 0x00, 0x00,
            0x00, 0x09, 0x00, 0x00, 0x00, 0x01, 0xa1, 0x03,
            0x6d, 0x79, 0x71, 0x40, 0x40, 0x43};
        int peer0_6[] = {
            0x00, 0x00, 0x00, 0x90, 0x02, 0x00, 0x00, 0x00,
            0x00, 0x53, 0x14, 0xd0, 0x00, 0x00, 0x00, 0x0c,
            0x00, 0x00, 0x00, 0x06, 0x43, 0x43, 0xa0, 0x01,
            0x30, 0x43, 0x42, 0x42, 0x00, 0x53, 0x70, 0x45,
            0x00, 0x53, 0x73, 0xd0, 0x00, 0x00, 0x00, 0x1b,
            0x00, 0x00, 0x00, 0x06, 0x53, 0x01, 0x40, 0xa1,
            0x08, 0x6b, 0x6f, 0x72, 0x6e, 0x79, 0x73, 0x65,
            0x6b, 0x40, 0x40, 0xa1, 0x06, 0x6b, 0x6f, 0x72,
            0x6e, 0x79, 0x73, 0x00, 0x53, 0x74, 0xd1, 0x00,
            0x00, 0x00, 0x2d, 0x00, 0x00, 0x00, 0x02, 0xa1,
            0x06, 0x6b, 0x6f, 0x72, 0x6e, 0x79, 0x73, 0xd0,
            0x00, 0x00, 0x00, 0x1c, 0x00, 0x00, 0x00, 0x03,
            0xa1, 0x04, 0x70, 0x65, 0x70, 0x61, 0xa1, 0x07,
            0x74, 0x6f, 0x6d, 0x61, 0x73, 0x65, 0x6b, 0xa1,
            0x07, 0x6a, 0x61, 0x6e, 0x69, 0x63, 0x6b, 0x61,
            0x00, 0x53, 0x77, 0xd1, 0x00, 0x00, 0x00, 0x10,
            0x00, 0x00, 0x00, 0x02, 0xa1, 0x08, 0x73, 0x65,
            0x71, 0x75, 0x65, 0x6e, 0x63, 0x65, 0x52, 0x01};
        int peer0_7[] = {
            0x00, 0x00, 0x00, 0x0c, 0x02, 0x00, 0x00, 0x00,
            0x00, 0x53, 0x18, 0x45};
        int messages[][] = {peer0_2, peer0_3, peer0_4, peer0_5, peer0_6, peer0_7};

        Socket s = new Socket("127.0.0.1", 5672);
        OutputStream stream = s.getOutputStream();
        for (int buffer[] : messages) {
            convertToByteArray(buffer);
            stream.write(convertToByteArray(buffer));
            stream.flush();
            Thread.sleep(500);
        }
        s.close();
    }

    @NotNull
    private byte[] convertToByteArray(int[] buffer) {
        ByteBuffer b = ByteBuffer.allocate(buffer.length);
        for (int value : buffer) {
            b.put((byte) value);
        }
        return b.array();
    }
}
