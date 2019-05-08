package l3info.projet.cakemarketingfactory.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.modele.Factory;
import l3info.projet.cakemarketingfactory.task.EnterFactoryTask;
import l3info.projet.cakemarketingfactory.utils.Contents;
import l3info.projet.cakemarketingfactory.utils.ImageContent;

public class FactoryActivity extends AppCompatActivity {
    Context context;
    Factory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory);

        context = this;

        //access to the userId in shared preferences
        SharedPreferences shr = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
        long userId = shr.getLong("userId",0L);

        //EnterFactoryTask task = new EnterFactoryTask(userId, factory, context);

        ImageView factoryBack = findViewById(R.id.factoryBack);
        factoryBack.setOnClickListener(view -> {
            //Changer d'activity
            Intent intentApp = new Intent(FactoryActivity.this, WorldActivity.class);
            FactoryActivity.this.startActivity(intentApp);
        });



        Button factoryBeltButtonLine1 = findViewById(R.id.factoryBeltButtonLine1);
        factoryBeltButtonLine1.setOnClickListener(view -> {
            int level = 0;
            openPopupUpgrade(level, ImageContent.beltImagesID[level]);
        });
        Button factoryRobotButtonLine1 = findViewById(R.id.factoryRobotButtonLine1);
        factoryRobotButtonLine1.setOnClickListener(view -> {
            int level = 0;
            openPopupUpgrade(level, ImageContent.robotImagesID[level]);
        });
        Button factoryOvenButtonLine1 = findViewById(R.id.factoryOvenButtonLine1);
        factoryOvenButtonLine1.setOnClickListener(view -> {
            int level = 0;
            openPopupUpgrade(level, ImageContent.ovenImagesID[level]);
        });




        Button factoryBeltButtonLine2 = findViewById(R.id.factoryBeltButtonLine2);
        factoryBeltButtonLine2.setOnClickListener(view -> {
            int level = 0;
            openPopupUpgrade(level, ImageContent.beltImagesID[level]);
        });
        Button factoryRobotButtonLine2 = findViewById(R.id.factoryRobotButtonLine2);
        factoryRobotButtonLine2.setOnClickListener(view -> {
            int level = 0;
            openPopupUpgrade(level, ImageContent.robotImagesID[level]);
        });
        Button factoryOvenButtonLine2 = findViewById(R.id.factoryOvenButtonLine2);
        factoryOvenButtonLine2.setOnClickListener(view -> {
            int level = 0;
            openPopupUpgrade(level, ImageContent.ovenImagesID[level]);
        });



        Button factoryBeltButtonLine3 = findViewById(R.id.factoryBeltButtonLine3);
        factoryBeltButtonLine3.setOnClickListener(view -> {
            int level = 0;
            openPopupUpgrade(level, ImageContent.beltImagesID[level]);
        });
        Button factoryRobotButtonLine3 = findViewById(R.id.factoryRobotButtonLine3);
        factoryRobotButtonLine3.setOnClickListener(view -> {
            int level = 0;
            openPopupUpgrade(level, ImageContent.robotImagesID[level]);
        });
        Button factoryOvenButtonLine3 = findViewById(R.id.factoryOvenButtonLine3);
        factoryOvenButtonLine3.setOnClickListener(view -> {
            int level = 0;
            openPopupUpgrade(level, ImageContent.ovenImagesID[level]);
        });




        Button factoryButtonStock = findViewById(R.id.factoryButtonStock);
        factoryButtonStock.setOnClickListener(v -> {
            int level = 0;
            openPopupUpgrade(level, R.drawable.title);
        });

        Button sell = findViewById(R.id.factorySell);
        sell.setOnClickListener(v -> openPopupSell(context));

    }

    //exemple
    void openPopupUpgrade(int level, int res)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_upgrade);
        Button popupUpgradeCancel = dialog.findViewById(R.id.popupUpgradeCancel);
        popupUpgradeCancel.setOnClickListener(v -> dialog.dismiss());

        Button popupUpgradeOk = dialog.findViewById(R.id.popupUpgradeOk);
        popupUpgradeOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        TextView popupUpgradeMessage = dialog.findViewById(R.id.popupUpgradeMessage);
        popupUpgradeMessage.setText("Êtes vous sûr de vouloir lancer l'amélioration");

        TextView popupUpgradeLevel = dialog.findViewById(R.id.popupUpgradeLevel);
        String levelText = getString(R.string.level) + (level+1);
        popupUpgradeLevel.setText(levelText);


        ImageView popupUpgradeImage = dialog.findViewById(R.id.popupUpgradeImage);
        popupUpgradeImage.setImageResource(res);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }

    //exemple
    void openPopupSell(Context context)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_market_sell);
        ImageView popupMessageCancel = dialog.findViewById(R.id.popupMarketSellBack);
        popupMessageCancel.setOnClickListener(v -> dialog.dismiss());

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }
}
