package l3info.projet.cakemarketingfactory.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.model.World;
import l3info.projet.cakemarketingfactory.task.BuyFactoryTask;
import l3info.projet.cakemarketingfactory.task.EnterFactoryTask;
import l3info.projet.cakemarketingfactory.task.EnterMarketTask;
import l3info.projet.cakemarketingfactory.task.EnterMessagesTask;
import l3info.projet.cakemarketingfactory.task.GetMoneyTask;
import l3info.projet.cakemarketingfactory.utils.Contents;
import l3info.projet.cakemarketingfactory.utils.ImageContent;
import l3info.projet.cakemarketingfactory.utils.ViewContent;

public class WorldActivity  extends AppCompatActivity {
    Context context;
    World world;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);
        context = this;

        //Log.i("BANDOL",getIntent().getSerializableExtra("world").toString());
        world = (World) getIntent().getSerializableExtra("world");
        //Log.i("BANDOL",world.factories.toString());
        //access to the userId in shared preferences
        SharedPreferences shr = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
        long userId = shr.getLong("userId",0L);
        Log.i("BANDOL","world sharedprefid:"+userId );


        TextView userMoney = findViewById(R.id.worldCapital);

        GetMoneyTask getMoney = new GetMoneyTask(userId, userMoney, context);
        getMoney.execute();


        for(int i=0; i<6; i++)
        {
            int factorySpot = i;
            if(i < world.factories.size())
            {
                ImageView factory = findViewById(ViewContent.factoryId[i]);
                factory.setImageResource(ImageContent.factoryId[i]);
                factory.setVisibility(View.VISIBLE);

                factory.setOnClickListener(v -> {
                    //Entrer dans une usine
                    //todo : put in task
                    Toast.makeText(context, "FACTORY 1 + "+ factorySpot, Toast.LENGTH_SHORT).show();


                    EnterFactoryTask task = new EnterFactoryTask(userId,world.factories.get(factorySpot), context);
                    task.execute();
                });
            }
            else
            {
                ImageView sign = findViewById(ViewContent.signId[i]);
                sign.setVisibility(View.VISIBLE);
                sign.setOnClickListener(v -> {
                    //cliquer sur un panneau $
                    Toast.makeText(context, "BUY sign 1 + " + factorySpot, Toast.LENGTH_SHORT).show();
                    //montre le prix et demande si tu veux acheter


                    openPopupSign(factorySpot+1,shr.getInt("money",0));
                });
            }
        }

        ImageView market = findViewById(R.id.worldMarket);
        market.setOnClickListener(view -> {
            //Changer d'activity
            EnterMarketTask enterMarketTask = new EnterMarketTask(userId, context);
            enterMarketTask.execute();
        });

        ImageView messages = findViewById(R.id.worldLetter);
        messages.setOnClickListener(view -> {
            //Changer d'activity
            EnterMessagesTask enterMessagesTask = new EnterMessagesTask(context);
            enterMessagesTask.execute();
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
        disconnect.setOnClickListener(v -> {
            SharedPreferences shr = context.getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = shr.edit();
            ed.remove("username");
            ed.remove("password");
            ed.remove("userId");
            ed.apply();

            Intent intentApp = new Intent(WorldActivity.this, LoginActivity.class);
            WorldActivity.this.startActivity(intentApp);
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
        popupMessageClose.setOnClickListener(v -> dialog.dismiss());
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }

    void openPopupSign(int factorySpot, int money)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_question);
        Button popupMessageCancel = dialog.findViewById(R.id.popupQuestionCancel);
        popupMessageCancel.setOnClickListener(v -> dialog.dismiss());

        TextView popupQuestionMessage = dialog.findViewById(R.id.popupQuestionMessage);
        popupQuestionMessage.setText("Le prix c'est le prix !\nSi tu veux acheter... vend\nDU GATEAU !");

        ImageView popupQuestionImage = dialog.findViewById(R.id.popupQuestionImage);
        popupQuestionImage.setImageResource(R.drawable.world_dollard_sign);

        Button popupMessageOk = dialog.findViewById(R.id.popupQuestionOk);
        popupMessageOk.setOnClickListener(v -> {
            //Propose de selectionner le gateau de la 1ere ligne lors de l'achat
            openPopupSelection(factorySpot,money);
            dialog.dismiss();
        });

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }

    void openPopupSelection(int factorySpot, int money)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_cake_sel);
        ImageView popupMessageCancel = dialog.findViewById(R.id.popupCakeSelBack);
        popupMessageCancel.setOnClickListener(v -> dialog.dismiss());
        SharedPreferences shr = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
        long userId = shr.getLong("userId",0L);
        //clic sur cookie
        ImageButton cookieSelect = dialog.findViewById(R.id.popupCakeSelCookie);
        cookieSelect.setOnClickListener(v -> {
            /*Do something*/
            BuyFactoryTask task = new BuyFactoryTask(userId,factorySpot,0,money,context);
            task.execute();
            //bien garder le dismiss qui suit //remove this comment
            dialog.dismiss();
            switchSpot(factorySpot);
        });

        //clic sur cupcake
        ImageButton cupcakeSelect = dialog.findViewById(R.id.popupCakeSelCupcake);
        cupcakeSelect.setOnClickListener(v -> {
            /*Do something*/
            BuyFactoryTask task = new BuyFactoryTask(userId,factorySpot,1,money,context);
            task.execute();
            //bien garder le dismiss qui suit //remove this comment
            dialog.dismiss();
            switchSpot(factorySpot);
        });

        //clic sur donut
        ImageButton donutSelect = dialog.findViewById(R.id.popupCakeSelDonut);
        donutSelect.setOnClickListener(v -> {
            /*Do something*/
            BuyFactoryTask task = new BuyFactoryTask(userId,factorySpot,2,money,context);
            task.execute();
            //bien garder le dismiss qui suit //remove this comment
            dialog.dismiss();
            switchSpot(factorySpot);
        });

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }

    //switch de panneau d'achat à usine achetée
    public void switchSpot(int spot)
    {
        if(spot > 5) return; //il y a 6 usines max
        ImageView factory = findViewById(ViewContent.factoryId[spot]);
        factory.setImageResource(ImageContent.factoryId[spot]);
        factory.setVisibility(View.VISIBLE);
        factory.setOnClickListener(v -> {
            //Entrer dans une usine
            Toast.makeText(context, "FACTORY 1 + "+ spot, Toast.LENGTH_SHORT).show();

            //access to the userId in shared preferences
            SharedPreferences shr = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
            long id = shr.getLong("userId",0L);
            Log.i("BANDOL","world sharedprefid:"+id );
            EnterFactoryTask task = new EnterFactoryTask(id,world.factories.get(spot), context);
            task.execute();
        });

        ImageView sign = findViewById(ViewContent.signId[spot]);
        sign.setVisibility(View.VISIBLE);
        sign.setClickable(false);
    }
}
