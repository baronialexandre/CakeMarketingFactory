package l3info.projet.cakemarketingfactory.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.modele.World;
import l3info.projet.cakemarketingfactory.task.EnterWorldTask;
import l3info.projet.cakemarketingfactory.utils.Contents;

public class WorldActivity  extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);
        context = this;
        //access to the userId in shared preferences
        //SharedPreferences shr = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
        //long id = shr.getLong("userId",0L);
        //Log.i("BANDOL","world sharedprefid:"+id );
        //EnterWorldTask ici ou dans auth task
        //EnterWorldTask task = new EnterWorldTask(id, context);
        //task.execute();

        Log.i("BANDOL",getIntent().getSerializableExtra("world").toString());
        World world = (World) getIntent().getSerializableExtra("world");
        Log.i("BANDOL",world.factories.toString());


        ImageView worldFactory1 = findViewById(R.id.worldFactory1);
        worldFactory1.setOnClickListener(view -> {
            //Changer d'activity
            Intent intentApp = new Intent(WorldActivity.this, FactoryActivity.class);
            WorldActivity.this.startActivity(intentApp);
        });

        ImageView worldDollardSign2 = findViewById(R.id.worldSign2);
        worldDollardSign2.setOnClickListener(view -> {
            //Changer d'activity
            openPopupSign();
        });




        ImageView market = findViewById(R.id.worldMarket);
        market.setOnClickListener(view -> {
            //Changer d'activity
            Intent intentApp = new Intent(WorldActivity.this, MarketActivity.class);
            WorldActivity.this.startActivity(intentApp);
        });

        ImageView messages = findViewById(R.id.worldLetter);
        messages.setOnClickListener(view -> {
            //Changer d'activity
            Intent intentApp = new Intent(WorldActivity.this, MessagesActivity.class);
            WorldActivity.this.startActivity(intentApp);
        });

        ImageView settings = findViewById(R.id.worldSettings);
        settings.setOnClickListener(view -> openSettingsPopup());

        ImageView profile = findViewById(R.id.worldProfile);
        profile.setOnClickListener(view -> openProfilePopup());
    }

    void openSettingsPopup()
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_settings);
        Button close = dialog.findViewById(R.id.popupSettingsOk);
        close.setOnClickListener(v -> dialog.dismiss());
        Button disconnect = dialog.findViewById(R.id.popupSettingsDisconnect);
        disconnect.setOnClickListener(v -> Toast.makeText(context, "Disconnect me :)", Toast.LENGTH_SHORT).show());
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }

    void openProfilePopup()
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_profile);
        Button popupMessageClose = dialog.findViewById(R.id.popupProfileOk);
        popupMessageClose.setOnClickListener(v -> dialog.dismiss());
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }

    void openPopupSign()
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_question);
        Button popupMessageCancel = dialog.findViewById(R.id.popupQuestionCancel);
        popupMessageCancel.setOnClickListener(v -> dialog.dismiss());

        TextView popupQuestionMessage = dialog.findViewById(R.id.popupQuestionMessage);
        popupQuestionMessage.setText("Le prix c'est le prix !\nSi tu veux acheter... vend\nDU GATEAU !");

        ImageView popupQuestionImage = dialog.findViewById(R.id.popupQuestionImage);
        popupQuestionImage.setImageResource(R.drawable.world_dollard_sign);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }
}