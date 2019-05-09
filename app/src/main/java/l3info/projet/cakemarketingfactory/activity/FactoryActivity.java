package l3info.projet.cakemarketingfactory.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.modele.Factory;
import l3info.projet.cakemarketingfactory.modele.Line;
import l3info.projet.cakemarketingfactory.utils.Contents;
import l3info.projet.cakemarketingfactory.utils.ImageContent;

public class FactoryActivity extends AppCompatActivity {
    Context context;
    Factory factory;

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
        int factoryID = factory.getFactorySpot()-1;


        //Provisoire en attendant les Tasks
        ArrayList<Integer> levels = new ArrayList<Integer>();
        for(int i = 0; i<3; i++){levels.add(0);}
        Line line1 = new Line(0,levels);
        Line line2 = new Line(0,levels);
        Line line3 = new Line(0,levels);
        ArrayList<Line> lines = new ArrayList<>();
        lines.add(line1);
        lines.add(line2);
        lines.add(line3);
        factory.setLineList(lines);


        FrameLayout background = findViewById(R.id.factoryBackground);
        //getRessources().getDrawable au lieu de getDrawable pour pouvoir compiler sous une API < LOLIPOP
        background.setBackground(getResources().getDrawable(ImageContent.factoryBackgroundID[factoryID]));

        ImageView wall1 = findViewById(R.id.factoryWall1);
        //todo : éditer la couleurs des murs graphiquement
        wall1.setImageDrawable(getResources().getDrawable(ImageContent.factoryWallID[factoryID]));
        ImageView wall2 = findViewById(R.id.factoryWall2);
        wall2.setImageDrawable(getResources().getDrawable(ImageContent.factoryWallID[factoryID]));


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
            allBelts.get(i).setBackground(getResources().getDrawable(ImageContent.beltImagesID[factory.getLine(i).getLvl(i)]));
            allRobots.get(i).setImageResource(ImageContent.robotImagesID[factory.getLine(i).getLvl(i)]);
            allOvens.get(i).setImageResource(ImageContent.ovenImagesID[factory.getLine(i).getLvl(i)]);
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

        /*
        ArrayList<Button> allMachineButtons = new ArrayList<>();
        for(int iLine = 0; iLine < 3; iLine++){
            for(int iMachine = 0; iMachine < 3; iMachine++){

            }
        }*/


        Button factoryBeltButtonLine1 = findViewById(R.id.factoryBeltButtonLine1);
        factoryBeltButtonLine1.setOnClickListener(view -> {
            int level = factory.getLine(0).getLvl(0);
            openPopupUpgrade(level, ImageContent.beltImagesID[level],0,0);
        });
        Button factoryRobotButtonLine1 = findViewById(R.id.factoryRobotButtonLine1);
        factoryRobotButtonLine1.setOnClickListener(view -> {
            int level = factory.getLine(0).getLvl(1);
            openPopupUpgrade(level, ImageContent.robotImagesID[level],0,1);
        });
        Button factoryOvenButtonLine1 = findViewById(R.id.factoryOvenButtonLine1);
        factoryOvenButtonLine1.setOnClickListener(view -> {
            int level = factory.getLine(0).getLvl(2);
            openPopupUpgrade(level, ImageContent.ovenImagesID[level],0,2);
        });



        Button factoryBeltButtonLine2 = findViewById(R.id.factoryBeltButtonLine2);
        factoryBeltButtonLine2.setOnClickListener(view -> {
            int level = factory.getLine(1).getLvl(0);
            openPopupUpgrade(level, ImageContent.beltImagesID[level],1,0);
        });
        Button factoryRobotButtonLine2 = findViewById(R.id.factoryRobotButtonLine2);
        factoryRobotButtonLine2.setOnClickListener(view -> {
            int level = factory.getLine(1).getLvl(1);
            openPopupUpgrade(level, ImageContent.robotImagesID[level],1,1);
        });
        Button factoryOvenButtonLine2 = findViewById(R.id.factoryOvenButtonLine2);
        factoryOvenButtonLine2.setOnClickListener(view -> {
            int level = factory.getLine(1).getLvl(2);
            openPopupUpgrade(level, ImageContent.ovenImagesID[level],1,2);
        });



        Button factoryBeltButtonLine3 = findViewById(R.id.factoryBeltButtonLine3);
        factoryBeltButtonLine3.setOnClickListener(view -> {
            int level = factory.getLine(2).getLvl(0);
            openPopupUpgrade(level, ImageContent.beltImagesID[level],2,0);
        });
        Button factoryRobotButtonLine3 = findViewById(R.id.factoryRobotButtonLine3);
        factoryRobotButtonLine3.setOnClickListener(view -> {
            int level = factory.getLine(2).getLvl(1);
            openPopupUpgrade(level, ImageContent.robotImagesID[level],2,1);
        });
        Button factoryOvenButtonLine3 = findViewById(R.id.factoryOvenButtonLine3);
        factoryOvenButtonLine3.setOnClickListener(view -> {
            int level = factory.getLine(2).getLvl(2);
            openPopupUpgrade(level, ImageContent.ovenImagesID[level],2,2);
        });




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
                factory.getLine(line).setLvl(id, level + 1);

                if (id == 0) { allBelts.get(line).setBackground(getResources().getDrawable(ImageContent.beltImagesID[level+1]));}
                else if (id == 1) { allRobots.get(line).setImageResource(ImageContent.robotImagesID[level+1]);}
                else { allOvens.get(line).setImageResource(ImageContent.ovenImagesID[level+1]);}

                dialog.dismiss();
            }else { dialog.dismiss();}
        });

        TextView popupUpgradeMessage = dialog.findViewById(R.id.popupUpgradeMessage);
        popupUpgradeMessage.setText("Êtes vous sûr de vouloir lancer l'amélioration");

        TextView popupUpgradeLevel = dialog.findViewById(R.id.popupUpgradeLevel);
        String levelText = getString(R.string.level) + level + line + id;
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
