package coder;

import com.google.gson.Gson;
import model.Notification;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class NotificationDecoder implements Decoder.Text<Notification> {

    private static Gson gson = new Gson();

    @Override
    public Notification decode(String s) throws DecodeException {
        return gson.fromJson(s, Notification.class);
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
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
