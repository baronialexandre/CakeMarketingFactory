package l3info.projet.cakemarketingfactory;

import java.util.Date;

/**
 * Créé par Loïc Forestier le 02/05/2019 !
 */
public class MessageItem {
    private int imageResource;
    private String title;
    private String message;
    private String date;

    MessageItem(int imageResource, String title, String message, String date)
    {
        this.imageResource = imageResource;
        this.title = title;
        this.message = message;
        this.date = date;
    }

    int getImageResource() {
        return imageResource;
    }
    String getTitle() {
        return title;
    }
    String getMessage() {
        return message;
    }
    String getDate() {
        return date;
    }

    void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
    void setTitle(String title) {
        this.title = title;
    }
    void setMessage(String message) {
        this.message = message;
    }
    void setDate(String date) {
        this.date = date;
    }
}
