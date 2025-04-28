import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class NewClass {

    public static void main(String[] args) throws Exception {

        final Properties props = new Properties();
        props.setProperty(ProducerConfig.CLIENT_ID_CONFIG, "java-producer");
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Khởi tạo ObjectMapper để chuyển đổi thành JSON
        ObjectMapper objectMapper = new ObjectMapper();

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            for (int i = 0; i < 100; i++) {
                // Tạo đối tượng message
                Message message = new Message("Message " + i, "key-" + i);

                // Chuyển đối tượng thành chuỗi JSON
                String jsonString = objectMapper.writeValueAsString(message);

                // Gửi message dưới dạng chuỗi JSON
                ProducerRecord<String, String> record = new ProducerRecord<>(
                        "payroll-updated",    // Tên topic
                        "key-" + i,           // Key
                        jsonString            // Giá trị là chuỗi JSON
                );

                producer.send(record);
            }
        }
    }

    // Định nghĩa một lớp Message để sử dụng Jackson
    public static class Message {
        private String message;
        private String key;

        public Message(String message, String key) {
            this.message = message;
            this.key = key;
        }

        public String getMessage() {
            return message;
        }

        public String getKey() {
            return key;
        }
    }
}
