package l3info.projet.cakemarketingfactory.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import l3info.projet.cakemarketingfactory.task.GetScoreTask;
import l3info.projet.cakemarketingfactory.task.SellStockTask;
import l3info.projet.cakemarketingfactory.utils.Contents;
import l3info.projet.cakemarketingfactory.utils.ImageContent;
import l3info.projet.cakemarketingfactory.utils.ViewContent;

public class FactoryActivity extends AppCompatActivity {
    Context context;
    Factory factory;
    long userId;
    int score;

    TextView userScore;
    TextView stockText;

    ArrayList<TextView> allProductSpeed;
    ArrayList<ImageButton> allProduction;
    ArrayList<LinearLayout> allBelts;
    ArrayList<ImageView> allRobots;
    ArrayList<ImageView> allOvens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory);

        context = this;

        //factory récupérée après le "getExtra"
        factory = (Factory) getIntent().getSerializableExtra("factory");
        int factoryId = factory.getFactorySpot()-1;

        //access to the userId in shared preferences
        SharedPreferences shr = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
        userId = shr.getLong("userId",0L);
        score = shr.getInt("score",0);

        userScore = findViewById(R.id.factoryCapital);
        GetScoreTask getScore = new GetScoreTask(userId, userScore, context);
        getScore.execute();

        FrameLayout background = findViewById(R.id.factoryBackground);
        //getRessources().getDrawable au lieu de getDrawable pour pouvoir compiler sous une API < LOLIPOP
        background.setBackground(getResources().getDrawable(ImageContent.factoryBackgroundId[factoryId]));

        allProductSpeed = new ArrayList<>();
        allProduction = new ArrayList<>();
        allBelts = new ArrayList<>();
        allRobots = new ArrayList<>();
        allOvens = new ArrayList<>();

        /* --------- Pictures creation --------- */
        ImageView wall1 = findViewById(R.id.factoryWall1);
        wall1.setImageDrawable(getResources().getDrawable(ImageContent.factoryWallId[factoryId]));
        ImageView wall2 = findViewById(R.id.factoryWall2);
        wall2.setImageDrawable(getResources().getDrawable(ImageContent.factoryWallId[factoryId]));

        allProductSpeed.add(findViewById(R.id.factoryUnitPerSecondLine1));
        allProductSpeed.add(findViewById(R.id.factoryUnitPerSecondLine2));
        allProductSpeed.add(findViewById(R.id.factoryUnitPerSecondLine3));

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

        //pour le warning : on ne doit pas avoir de concaténation dans le xxx.setText(xxx);
        String text;

        for(int i = 0; i < 3; i++){
            if(factory.getLine(i) != null){
                text = factory.getLine(i).getProduction()+"/s"; //todo : par secondes ? => minutes ! //Loïc
                allProductSpeed.get(i).setText(text);
                allProduction.get(i).setImageDrawable(getResources().getDrawable(ImageContent.cakeImageId[factory.getLine(i).getCakeId()]));
                allBelts.get(i).setBackground(getResources().getDrawable(ImageContent.beltImagesId[factory.getLine(i).getMachineLevel(i)]));
                allRobots.get(i).setImageResource(ImageContent.robotImagesId[factory.getLine(i).getMachineLevel(i)]);
                allOvens.get(i).setImageResource(ImageContent.ovenImagesId[factory.getLine(i).getMachineLevel(i)]);
            }
        }

        text = (factory.getCurrentStocks().get(0)
                + factory.getCurrentStocks().get(1)
                + factory.getCurrentStocks().get(2)) +
                "/" +
                (factory.getCapacityLevel() + 1) * 100;
        stockText = findViewById(R.id.factoryStockText);
        stockText.setText(text);
        /* --------- End pictures creation --------- */

        //EnterFactoryTask task = new EnterFactoryTask(userId, factory, context);

        ImageView factoryBack = findViewById(R.id.factoryBack);
        factoryBack.setOnClickListener(view -> {
            //Revenir en arrière sur une activity
            FactoryActivity.this.finish(); //"dépile" la stack d'activity
        });

        /* ---------- Change production ---------- */
        ImageButton productionLine1 = findViewById(R.id.factoryProductionButtonLine1);
        productionLine1.setOnClickListener(view -> {
            if (factory.getLine(0) != null) { openPopupSelection(0); }
        });
        ImageButton productionLine2 = findViewById(R.id.factoryProductionButtonLine2);
        productionLine2.setOnClickListener(view -> {
            if (factory.getLine(1) != null) {
                if (factory.getLine(0) != null) { openPopupSelection(1); }
            }
        });
        ImageButton productionLine3 = findViewById(R.id.factoryProductionButtonLine3);
        productionLine3.setOnClickListener(view -> {
            if (factory.getLine(2) != null) {
                if (factory.getLine(0) != null) { openPopupSelection(2); }
            }
        });
        /* ---------- End change production ---------- */

        /* ---------- Upgrade buttons ---------- */
        //Line 1
        Button factoryBeltButtonLine1 = findViewById(R.id.factoryBeltButtonLine1);
        factoryBeltButtonLine1.setOnClickListener(view -> {
            int level = factory.getLine(0).getMachineLevel(0);
            openPopupUpgrade(level, ImageContent.beltImagesId[level],0,0, factoryBeltButtonLine1);
        });
        Button factoryRobotButtonLine1 = findViewById(R.id.factoryRobotButtonLine1);
        factoryRobotButtonLine1.setOnClickListener(view -> {
            int level = factory.getLine(0).getMachineLevel(1);
            openPopupUpgrade(level, ImageContent.robotImagesId[level],0,1, factoryRobotButtonLine1);
        });
        Button factoryOvenButtonLine1 = findViewById(R.id.factoryOvenButtonLine1);
        factoryOvenButtonLine1.setOnClickListener(view -> {
            int level = factory.getLine(0).getMachineLevel(2);
            openPopupUpgrade(level, ImageContent.ovenImagesId[level],0,2, factoryOvenButtonLine1);
        });


        if (factory.getLine(0) == null){
            factoryBeltButtonLine1.setEnabled(false);
            factoryRobotButtonLine1.setEnabled(false);
            factoryOvenButtonLine1.setEnabled(false);
            factoryBeltButtonLine1.setBackground(getResources().getDrawable(R.drawable.red_button_selector));
            factoryRobotButtonLine1.setBackground(getResources().getDrawable(R.drawable.red_button_selector));
            factoryOvenButtonLine1.setBackground(getResources().getDrawable(R.drawable.red_button_selector));
        }
        //Line 2
        Button factoryBeltButtonLine2 = findViewById(R.id.factoryBeltButtonLine2);
        factoryBeltButtonLine2.setOnClickListener(view -> {
            int level = factory.getLine(1).getMachineLevel(0);
            openPopupUpgrade(level, ImageContent.beltImagesId[level],1,0, factoryBeltButtonLine2);
        });
        Button factoryRobotButtonLine2 = findViewById(R.id.factoryRobotButtonLine2);
        factoryRobotButtonLine2.setOnClickListener(view -> {
            int level = factory.getLine(1).getMachineLevel(1);
            openPopupUpgrade(level, ImageContent.robotImagesId[level],1,1, factoryRobotButtonLine2);
        });
        Button factoryOvenButtonLine2 = findViewById(R.id.factoryOvenButtonLine2);
        factoryOvenButtonLine2.setOnClickListener(view -> {
            int level = factory.getLine(1).getMachineLevel(2);
            openPopupUpgrade(level, ImageContent.ovenImagesId[level],1,2, factoryOvenButtonLine2);
        });
        if (factory.getLine(1) == null){
            factoryBeltButtonLine2.setEnabled(false);
            factoryRobotButtonLine2.setEnabled(false);
            factoryOvenButtonLine2.setEnabled(false);
            factoryBeltButtonLine2.setBackground(getResources().getDrawable(R.drawable.red_button_selector));
            factoryRobotButtonLine2.setBackground(getResources().getDrawable(R.drawable.red_button_selector));
            factoryOvenButtonLine2.setBackground(getResources().getDrawable(R.drawable.red_button_selector));
        }
        //Line 3
        Button factoryBeltButtonLine3 = findViewById(R.id.factoryBeltButtonLine3);
        factoryBeltButtonLine3.setOnClickListener(view -> {
            int level = factory.getLine(2).getMachineLevel(0);
            openPopupUpgrade(level, ImageContent.beltImagesId[level],2,0, factoryBeltButtonLine3);
        });
        Button factoryRobotButtonLine3 = findViewById(R.id.factoryRobotButtonLine3);
        factoryRobotButtonLine3.setOnClickListener(view -> {
            int level = factory.getLine(2).getMachineLevel(1);
            openPopupUpgrade(level, ImageContent.robotImagesId[level],2,1, factoryRobotButtonLine3);
        });
        Button factoryOvenButtonLine3 = findViewById(R.id.factoryOvenButtonLine3);
        factoryOvenButtonLine3.setOnClickListener(view -> {
            int level = factory.getLine(2).getMachineLevel(2);
            openPopupUpgrade(level, ImageContent.ovenImagesId[level],2,2, factoryOvenButtonLine3);
        });
        if (factory.getLine(2) == null){
            factoryBeltButtonLine3.setEnabled(false);
            factoryRobotButtonLine3.setEnabled(false);
            factoryOvenButtonLine3.setEnabled(false);
            factoryBeltButtonLine3.setBackground(getResources().getDrawable(R.drawable.red_button_selector));
            factoryRobotButtonLine3.setBackground(getResources().getDrawable(R.drawable.red_button_selector));
            factoryOvenButtonLine3.setBackground(getResources().getDrawable(R.drawable.red_button_selector));
        }
        Button factoryButtonStock = findViewById(R.id.factoryButtonStock);
        factoryButtonStock.setOnClickListener(v -> {
            int level = factory.getCapacityLevel();
            openPopupUpgrade(level, R.drawable.title,0,-1, factoryButtonStock);
        });

        /* End upgrade button */

        Button sell = findViewById(R.id.factorySell);
        sell.setOnClickListener(v -> openPopupSell(context));

    }

    //exemple
    void openPopupUpgrade(int level, int res, int line, int id, Button button)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_upgrade);
        Button popupUpgradeCancel = dialog.findViewById(R.id.popupUpgradeCancel);
        popupUpgradeCancel.setOnClickListener(v -> dialog.dismiss());

        String text; //pour le warning : on ne doit pas avoir de concaténation dans le xxx.setText(xxx);

        TextView popupUpgradeMessage = dialog.findViewById(R.id.popupUpgradeMessage);
        text = getString(R.string.upgrade_valid)+" "+(level*1000)+" $";
        popupUpgradeMessage.setText(text);

        if(level == 9){
            popupUpgradeMessage.setText(R.string.max_level);
            popupUpgradeCancel.setVisibility(View.INVISIBLE);
            popupUpgradeCancel.setClickable(false);
        }

        Button popupUpgradeOk = dialog.findViewById(R.id.popupUpgradeOk);
        popupUpgradeOk.setOnClickListener(v -> {
            if (level<=8) {
                if (id >= 0){ factory.getLine(line).setMachineLevel(id, level + 1); }
                else {
                    factory.setCapacityLevel( level+1 );
                    TextView stock = findViewById(R.id.factoryStockText);
                    stock.setText(factory.getCurrentStocks().get(0)+factory.getCurrentStocks().get(1)+factory.getCurrentStocks().get(2)+"/"+(factory.getCapacityLevel()+1)*100);
                }

                if (id == 0) {
                    allBelts.get(line).setBackground(getResources().getDrawable(ImageContent.beltImagesId[level+1]));
                }
                else if (id == 1) {
                    allRobots.get(line).setImageResource(ImageContent.robotImagesId[level+1]);
                }
                else if (id == 2){
                    allOvens.get(line).setImageResource(ImageContent.ovenImagesId[level+1]);
                }
                allProductSpeed.get(line).setText(factory.getLine(line).getProduction()+"/s");

                if (level == 8) {
                    button.setBackground(getResources().getDrawable(R.drawable.gold_button_selector));
                    button.setText("M");
                }
                dialog.dismiss();
            }else {
                dialog.dismiss();}
        });

        TextView popupUpgradeLevel = dialog.findViewById(R.id.popupUpgradeLevel);
        String levelText = getString(R.string.level) + level;
        popupUpgradeLevel.setText(levelText);


        ImageView popupUpgradeImage = dialog.findViewById(R.id.popupUpgradeImage);
        popupUpgradeImage.setImageResource(res);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }

    void openPopupSelection(int line)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_cake_sel);
        ImageView popupMessageCancel = dialog.findViewById(R.id.popupCakeSelBack);
        popupMessageCancel.setOnClickListener(v -> dialog.dismiss());

        ImageButton cookieSelect = dialog.findViewById(R.id.popupCakeSelCookie);
        cookieSelect.setOnClickListener(v -> {
            allProduction.get(line).setImageDrawable(getResources().getDrawable(ImageContent.cakeImageId[0]));
            factory.getLine(line).setCakeId(0);
            dialog.dismiss();
        });

        ImageButton cupcakeSelect = dialog.findViewById(R.id.popupCakeSelCupcake);
        cupcakeSelect.setOnClickListener(v -> {
            allProduction.get(line).setImageDrawable(getResources().getDrawable(ImageContent.cakeImageId[1]));
            factory.getLine(line).setCakeId(1);
            dialog.dismiss();
        });

        ImageButton donutSelect = dialog.findViewById(R.id.popupCakeSelDonut);
        donutSelect.setOnClickListener(v -> {
            allProduction.get(line).setImageDrawable(getResources().getDrawable(ImageContent.cakeImageId[2]));
            factory.getLine(line).setCakeId(2);
            dialog.dismiss();
        });

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }

    void openPopupSell(Context context)
    {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_market_sell);
        ImageView popupMessageCancel = dialog.findViewById(R.id.popupMarketSellBack);
        popupMessageCancel.setOnClickListener(v -> dialog.dismiss());

        final String[] text = new String[1]; //pour le warning : on ne doit pas avoir de concaténation dans le xxx.setText(xxx);
        final String[] newText = new String[1];
        for(int i=0; i<3; i++)
        {
            TextView stock = dialog.findViewById(ViewContent.sellMarketCakeText[i]);
            text[0] = factory.getCurrentStocks().get(i)+"";
            stock.setText(text[0]);

            ImageButton cakeSell = dialog.findViewById(ViewContent.sellCakeButtons[i]);
            int cake = i;
            cakeSell.setOnClickListener(v -> {
                SellStockTask sellStockTask = new SellStockTask(userId, factory, cake, score, context, stock, userScore, stockText);
                sellStockTask.execute();
            });
        }

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }
}
