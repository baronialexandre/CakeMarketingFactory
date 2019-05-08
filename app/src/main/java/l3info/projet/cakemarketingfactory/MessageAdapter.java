package l3info.projet.cakemarketingfactory;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Créé par Loïc Forestier le 02/05/2019 !
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private ArrayList<MessageItem> messageItems;
    private OnItemClickListener listener;

    MessageAdapter(ArrayList<MessageItem> messageItems) {
        this.messageItems = messageItems;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    //affecte au listener @listener
    void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }



    static class MessageViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title;
        TextView message;

        //Paramétrage de la view @itemView
        //@listener > MessageViewHoldert doit rester statique
        MessageViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.messageItemIcon);
            title = itemView.findViewById(R.id.messageItemTitle);
            message = itemView.findViewById(R.id.messageItemMessage);

            itemView.setOnClickListener(view -> {
                if(listener != null) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION)
                        listener.onItemClick(position); //renvoie l'id du message cliqué
                }
            });
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_item, viewGroup, false);
        return new MessageViewHolder(view, listener);
    }

    //lier la view de la position donnée à son messageItem
    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int position) {
        MessageItem messageItem = messageItems.get(position);
        messageViewHolder.imageView.setImageResource(messageItem.getImageResource());
        messageViewHolder.title.setText(messageItem.getTitle());

        if(messageItem.getMessage().length()>60) {
            String messageTmp = messageItem.getMessage().substring(0,59) + "...";
            messageViewHolder.message.setText(messageTmp);
        }
        else
            messageViewHolder.message.setText(messageItem.getMessage());
    }

    @Override
    public int getItemCount() {
        return messageItems.size();
    }
}
