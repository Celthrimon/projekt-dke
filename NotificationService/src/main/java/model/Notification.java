package model;

public class Notification {
    String toUser;
    String content;

    public Notification ( String to, String content){

        this.toUser = to;
        this.content = content;
    }


    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return
                content + ' ' +
                        toUser + '.';
    }
}
