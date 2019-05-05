package l3info.projet.cakemarketingfactory;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class WorldActivity  extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);
        context = this;

        ImageView worldFactory1 = findViewById(R.id.worldFactory1);
        worldFactory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Changer d'activity
                Intent intentApp = new Intent(WorldActivity.this, FactoryActivity.class);
                WorldActivity.this.startActivity(intentApp);
            }
        });

        ImageView worldDollardSign2 = findViewById(R.id.worldSign2);
        worldDollardSign2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Changer d'activity
                openPopupSign();
            }
        });




        ImageView market = findViewById(R.id.worldMarket);
        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Changer d'activity
                Intent intentApp = new Intent(WorldActivity.this, MarketActivity.class);
                WorldActivity.this.startActivity(intentApp);
            }
        });

        ImageView messages = findViewById(R.id.worldLetter);
        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Changer d'activity
                Intent intentApp = new Intent(WorldActivity.this, MessagesActivity.class);
                WorldActivity.this.startActivity(intentApp);
            }
        });

        ImageView settings = findViewById(R.id.worldSettings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettingsPopup();
            }
        });

        ImageView profile = findViewById(R.id.worldProfile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfilePopup();
            }
        });
    }

    void openSettingsPopup()
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_settings);
        Button popupMessageClose = dialog.findViewById(R.id.popupSettingsOk);
        popupMessageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }

    void openProfilePopup()
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_profile);
        Button popupMessageClose = dialog.findViewById(R.id.popupProfileOk);
        popupMessageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }

    void openPopupSign()
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_question);
        Button popupMessageCancel = dialog.findViewById(R.id.popupQuestionCancel);
        popupMessageCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextView popupQuestionMessage = dialog.findViewById(R.id.popupQuestionMessage);
        popupQuestionMessage.setText("Le prix c'est le prix !\nSi tu veux acheter... vend\nDU GATEAU !");

        ImageView popupQuestionImage = dialog.findViewById(R.id.popupQuestionImage);
        popupQuestionImage.setImageResource(R.drawable.world_dollard_sign);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }
}
