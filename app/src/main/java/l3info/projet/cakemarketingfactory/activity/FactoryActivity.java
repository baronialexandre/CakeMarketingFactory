package l3info.projet.cakemarketingfactory.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.model.Factory;
import l3info.projet.cakemarketingfactory.model.Line;
import l3info.projet.cakemarketingfactory.utils.Contents;
import l3info.projet.cakemarketingfactory.utils.ImageContent;

public class FactoryActivity extends AppCompatActivity {
    Context context;
    Factory factory;

    ArrayList<ImageButton> allProduction = new ArrayList<>();
    ArrayList<LinearLayout> allBelts = new ArrayList<>();
    ArrayList<ImageView> allRobots = new ArrayList<>();
    ArrayList<ImageView> allOvens = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory);

        context = this;

        //factory récupérée après le "getExtra"
        factory = (Factory) getIntent().getSerializableExtra("factory");
        int factoryId = factory.getFactorySpot()-1;


        FrameLayout background = findViewById(R.id.factoryBackground);
        //getRessources().getDrawable au lieu de getDrawable pour pouvoir compiler sous une API < LOLIPOP
        background.setBackground(getResources().getDrawable(ImageContent.factoryBackgroundID[factoryId]));

        ImageView wall1 = findViewById(R.id.factoryWall1);
        //todo : éditer la couleurs des murs graphiquement
        wall1.setImageDrawable(getResources().getDrawable(ImageContent.factoryWallID[factoryId]));
        ImageView wall2 = findViewById(R.id.factoryWall2);
        wall2.setImageDrawable(getResources().getDrawable(ImageContent.factoryWallID[factoryId]));

        allProduction.add(findViewById(R.id.factoryProductionButtonLine1));
        allProduction.add(findViewById(R.id.factoryProductionButtonLine2));
        allProduction.add(findViewById(R.id.factoryProductionButtonLine2));

        allBelts.add(findViewById(R.id.factoryBeltLine1));
        allBelts.add(findViewById(R.id.factoryBeltLine2));
        allBelts.add(findViewById(R.id.factoryBeltLine3));

        allRobots.add(findViewById(R.id.factoryRobotLine1));
        allRobots.add(findViewById(R.id.factoryRobotLine2));
        allRobots.add(findViewById(R.id.factoryRobotLine3));

        allOvens.add(findViewById(R.id.factoryOvenLine1));
        allOvens.add(findViewById(R.id.factoryOvenLine2));
        allOvens.add(findViewById(R.id.factoryOvenLine3));

        for(int i = 0; i < 3; i++){
            if(factory.getLine(i) != null){
                allProduction.get(i).setImageDrawable(getResources().getDrawable(ImageContent.cakeImageID[factory.getLine(i).getCakeId()]));
                allBelts.get(i).setBackground(getResources().getDrawable(ImageContent.beltImagesID[factory.getLine(i).getMachineLevel(i)]));
                allRobots.get(i).setImageResource(ImageContent.robotImagesID[factory.getLine(i).getMachineLevel(i)]);
                allOvens.get(i).setImageResource(ImageContent.ovenImagesID[factory.getLine(i).getMachineLevel(i)]);
            }
        }


        //access to the userId in shared preferences
        SharedPreferences shr = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
        long userId = shr.getLong("userId",0L);

        //EnterFactoryTask task = new EnterFactoryTask(userId, factory, context);

        ImageView factoryBack = findViewById(R.id.factoryBack);
        factoryBack.setOnClickListener(view -> {
            //Revenir en arrière sur une activity
            FactoryActivity.this.finish(); //"dépile" la stack d'activity
        });


        Button factoryBeltButtonLine1 = findViewById(R.id.factoryBeltButtonLine1);
        factoryBeltButtonLine1.setOnClickListener(view -> {
            int level = factory.getLine(0).getMachineLevel(0);
            openPopupUpgrade(level, ImageContent.beltImagesID[level],0,0);
        });
        Button factoryRobotButtonLine1 = findViewById(R.id.factoryRobotButtonLine1);
        factoryRobotButtonLine1.setOnClickListener(view -> {
            int level = factory.getLine(0).getMachineLevel(1);
            openPopupUpgrade(level, ImageContent.robotImagesID[level],0,1);
        });
        Button factoryOvenButtonLine1 = findViewById(R.id.factoryOvenButtonLine1);
        factoryOvenButtonLine1.setOnClickListener(view -> {
            int level = factory.getLine(0).getMachineLevel(2);
            openPopupUpgrade(level, ImageContent.ovenImagesID[level],0,2);
        });
        if (factory.getLine(0) == null){
            factoryBeltButtonLine1.setEnabled(false);
            factoryRobotButtonLine1.setEnabled(false);
            factoryOvenButtonLine1.setEnabled(false);
        }



        Button factoryBeltButtonLine2 = findViewById(R.id.factoryBeltButtonLine2);
        factoryBeltButtonLine2.setOnClickListener(view -> {
            int level = factory.getLine(1).getMachineLevel(0);
            openPopupUpgrade(level, ImageContent.beltImagesID[level],1,0);
        });
        Button factoryRobotButtonLine2 = findViewById(R.id.factoryRobotButtonLine2);
        factoryRobotButtonLine2.setOnClickListener(view -> {
            int level = factory.getLine(1).getMachineLevel(1);
            openPopupUpgrade(level, ImageContent.robotImagesID[level],1,1);
        });
        Button factoryOvenButtonLine2 = findViewById(R.id.factoryOvenButtonLine2);
        factoryOvenButtonLine2.setOnClickListener(view -> {
            int level = factory.getLine(1).getMachineLevel(2);
            openPopupUpgrade(level, ImageContent.ovenImagesID[level],1,2);
        });
        if (factory.getLine(1) == null){
            factoryBeltButtonLine2.setEnabled(false);
            factoryRobotButtonLine2.setEnabled(false);
            factoryOvenButtonLine2.setEnabled(false);
        }


        Button factoryBeltButtonLine3 = findViewById(R.id.factoryBeltButtonLine3);
        factoryBeltButtonLine3.setOnClickListener(view -> {
            int level = factory.getLine(2).getMachineLevel(0);
            openPopupUpgrade(level, ImageContent.beltImagesID[level],2,0);
        });
        Button factoryRobotButtonLine3 = findViewById(R.id.factoryRobotButtonLine3);
        factoryRobotButtonLine3.setOnClickListener(view -> {
            int level = factory.getLine(2).getMachineLevel(1);
            openPopupUpgrade(level, ImageContent.robotImagesID[level],2,1);
        });
        Button factoryOvenButtonLine3 = findViewById(R.id.factoryOvenButtonLine3);
        factoryOvenButtonLine3.setOnClickListener(view -> {
            int level = factory.getLine(2).getMachineLevel(2);
            openPopupUpgrade(level, ImageContent.ovenImagesID[level],2,2);
        });
        if (factory.getLine(2) == null){
            factoryBeltButtonLine3.setEnabled(false);
            factoryRobotButtonLine3.setEnabled(false);
            factoryOvenButtonLine3.setEnabled(false);
        }



        Button factoryButtonStock = findViewById(R.id.factoryButtonStock);
        factoryButtonStock.setOnClickListener(v -> {
            int level = 0;
            openPopupUpgrade(level, R.drawable.title,0,0);
        });

        Button sell = findViewById(R.id.factorySell);
        sell.setOnClickListener(v -> openPopupSell(context));

    }

    //exemple
    void openPopupUpgrade(int level, int res, int line, int id)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_upgrade);
        Button popupUpgradeCancel = dialog.findViewById(R.id.popupUpgradeCancel);
        popupUpgradeCancel.setOnClickListener(v -> dialog.dismiss());

        Button popupUpgradeOk = dialog.findViewById(R.id.popupUpgradeOk);
        popupUpgradeOk.setOnClickListener(v -> {
            if (level<=8) {
                factory.getLine(line).setMachineLevel(id, level + 1);

                if (id == 0) { allBelts.get(line).setBackground(getResources().getDrawable(ImageContent.beltImagesID[level+1]));}
                else if (id == 1) { allRobots.get(line).setImageResource(ImageContent.robotImagesID[level+1]);}
                else { allOvens.get(line).setImageResource(ImageContent.ovenImagesID[level+1]);}

                dialog.dismiss();
            }else { dialog.dismiss();}
        });

        TextView popupUpgradeMessage = dialog.findViewById(R.id.popupUpgradeMessage);
        popupUpgradeMessage.setText("Êtes vous sûr de vouloir lancer l'amélioration");

        TextView popupUpgradeLevel = dialog.findViewById(R.id.popupUpgradeLevel);
        String levelText = getString(R.string.level) + level;
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
