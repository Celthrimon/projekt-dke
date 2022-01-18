package at.jku.userservice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;

    private String password;

    private String attributes;

    public User() {}

    public User(String username, String password, String attributes) {
        setPassword(password);
        setAttributes(attributes);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getAttributes(){
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public void setPassword(String password) {
        //hash Password without salt (MD5 is not very secure)
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            StringBuilder sb = new StringBuilder();
            byte[] bytes = md.digest();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            this.password = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public boolean verifyPassword(String password){
        //hash Password without salt (MD5 is not very secure)
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            StringBuilder sb = new StringBuilder();
            byte[] bytes = md.digest();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString().equals(this.password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setPasswordRaw(String password){
        this.password = password;
    }

    protected String getPassword(){
        return password;
    }

    @Override

    public String toString() {
        return "{" +
                "\"username\":\"" + username + '\"' +
                ",\"attributes\":\"" + attributes + '\"' +
                '}';
    }
}
