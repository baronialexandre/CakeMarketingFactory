package l3info.projet.cakemarketingfactory.modele;

import java.util.Date;

/**
 * Créé par Loïc Forestier le 02/05/2019 !
 */
public class MessageItem {
    private int imageResource;
    private String title;
    private String message;
    private String date;

    public MessageItem(int imageResource, String title, String message, String date)
    {
        this.imageResource = imageResource;
        this.title = title;
        this.message = message;
        this.date = date;
    }

    public int getImageResource() {
        return imageResource;
    }
    public String getTitle() {
        return title;
    }
    public String getMessage() {
        return message;
    }
    public String getDate() {
        return date;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
