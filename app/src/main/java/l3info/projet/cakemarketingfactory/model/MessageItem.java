package l3info.projet.cakemarketingfactory.model;

import java.io.Serializable;

import l3info.projet.cakemarketingfactory.R;

/**
 * Créé par Loïc Forestier le 02/05/2019 !
 */
public class MessageItem implements Serializable {
    private int imageResource;
    private int mailId;
    private String sendDate;
    private String title;
    private String message;
    private String mailType;
    private String adminName;

    public MessageItem(int mailId, String sendDate, String title, String message, String mailType, String adminName)
    {
        this.imageResource = R.drawable.message_letter;
        this.mailId = mailId;
        this.sendDate = sendDate;
        this.title = title;
        this.message = message;
        this.mailType = mailType;
        this.adminName = adminName;
    }

    public int getImageResource() {
        return imageResource;
    }
    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public int getMailId() {
        return mailId;
    }

    public void setMailId(int mailId) {
        this.mailId = mailId;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
