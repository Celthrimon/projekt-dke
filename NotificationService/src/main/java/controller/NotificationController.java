package controller;
import coder.NotificationDecoder;
import coder.NotificationEncoder;
import model.Notification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(
        value="mymood/notification/{username}",
        decoders = NotificationDecoder.class,
        encoders = NotificationEncoder.class )
@RestController
@RequestMapping("/mymood/notification")
public class NotificationController {
    private Session session;
    private static Set<NotificationController> notificationEndpoints
            = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();


    @RequestMapping(value = "notify/{userId}", method = RequestMethod.POST)
    public ResponseEntity<String> notifyUser
            (@PathVariable("userId") String userName, @RequestParam String message) throws IOException, EncodeException {
        Notification notification = new Notification(userName, message);
        broadcast(notification);
        return ResponseEntity.ok("User notified");
    }

    @OnOpen
    public void onOpen(
            Session session,
            @PathParam("username") String username) throws IOException {


        this.session = session;
        notificationEndpoints.add(this);
        users.put(session.getId(), username);

    }

    @OnMessage
    public void onMessage(Session session, Notification notification)
            throws IOException, EncodeException {

        broadcast(notification);
    }

    @OnClose
    public void onClose(Session session) throws IOException {

        notificationEndpoints.remove(this);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    private static void broadcast(Notification notification)
            throws IOException, EncodeException {

        notificationEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {

                    if(users.get(endpoint.session.getId()) == notification.getToUser()) {


                        endpoint.session.getBasicRemote().
                                sendObject(notification);
                    }
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
