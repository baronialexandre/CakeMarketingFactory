package l3info.projet.cakemarketingfactory.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Messages implements Serializable {
    private ArrayList<MessageItem> messageItems;
    public Messages(ArrayList<MessageItem> messageItems){
        this.messageItems = messageItems;
    }

    public ArrayList<MessageItem> getMessageItems() {
        return messageItems;
    }

    public void setMessageItems(ArrayList<MessageItem> messageItems) {
        this.messageItems = messageItems;
    }
}
