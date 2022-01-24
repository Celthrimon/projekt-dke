package coder;

import com.google.gson.Gson;
import model.Notification;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class NotificationEncoder implements Encoder.Text<Notification> {

    private static Gson gson = new Gson();

    @Override
    public String encode(Notification notification) throws EncodeException {
        return gson.toJson(notification);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }

    @Override
    public void destroy() {
        // Close resources
    }
}
