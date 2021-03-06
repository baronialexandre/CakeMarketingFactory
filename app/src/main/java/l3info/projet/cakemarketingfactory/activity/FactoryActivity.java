package l3info.projet.cakemarketingfactory.activity;

import android.annotation.SuppressLint;
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
import l3info.projet.cakemarketingfactory.activity.manager.SoundManager;
import l3info.projet.cakemarketingfactory.model.Factory;
import l3info.projet.cakemarketingfactory.task.BuyLineTask;
import l3info.projet.cakemarketingfactory.task.GetLinePriceTask;
import l3info.projet.cakemarketingfactory.task.GetScoreTask;
import l3info.projet.cakemarketingfactory.task.GetStockTask;
import l3info.projet.cakemarketingfactory.task.SellStockTask;
import l3info.projet.cakemarketingfactory.task.UpgradeCapacityTask;
import l3info.projet.cakemarketingfactory.task.UpgradeMachineTask;
import l3info.projet.cakemarketingfactory.utils.Contents;
import l3info.projet.cakemarketingfactory.utils.ImageContent;
import l3info.projet.cakemarketingfactory.utils.ViewContent;

public class FactoryActivity extends AppCompatActivity {
    Context context;
    Factory factory;
    long userId;
    int score;

    SharedPreferences shr;

    TextView userScore;
    TextView stockText;

    ArrayList<TextView> allProductSpeed;
    ArrayList<ImageButton> allProduction;
    ArrayList<LinearLayout> allBelts;
    ArrayList<ImageView> allRobots;
    ArrayList<ImageView> allOvens;
    SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory);

        context = this;
        soundManager = new SoundManager(this);

        //access to the userId in shared preferences
        shr = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
        userId = shr.getLong("userId",0L);
        score = shr.getInt("score",0);

        //factory récupérée après le "getExtra"
        factory = (Factory) getIntent().getSerializableExtra("factory");
        int factoryId = factory.getFactorySpot()-1;

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

        for (int i=0; i<3; i++){
            allProductSpeed.add(findViewById(ViewContent.factoryUnitPerSecond[i]));
            allProduction.add(findViewById(ViewContent.factoryProductionButton[i]));
            allBelts.add(findViewById(ViewContent.factoryBeltLine[i]));
            allRobots.add(findViewById(ViewContent.factoryRobotLine[i]));
            allOvens.add(findViewById(ViewContent.factoryOvenLine[i]));
        }

        String text;

        for(int i = 0; i < 3; i++){
            if(factory.getLine(i) != null){
                text = factory.getLine(i).getProduction()+"/m";
                allProductSpeed.get(i).setText(text);
                allProduction.get(i).setImageDrawable(getResources().getDrawable(ImageContent.cakeImageId[factory.getLine(i).getCakeId()]));
                allBelts.get(i).setBackground(getResources().getDrawable(ImageContent.beltImagesId[factory.getLine(i).getMachineLevel(i)]));
                allRobots.get(i).setImageResource(ImageContent.robotImagesId[factory.getLine(i).getMachineLevel(i)]);
                allOvens.get(i).setImageResource(ImageContent.ovenImagesId[factory.getLine(i).getMachineLevel(i)]);
            }
        }
        if(factory.getLine(1)==null && factory.getLine(2)==null){
            allProduction.get(2).setBackgroundColor(0);
            allProduction.get(2).setEnabled(false);
        }

        text = (factory.getCurrentStocks().get(0)
                + factory.getCurrentStocks().get(1)
                + factory.getCurrentStocks().get(2)) +
                "/" +
                (factory.getCapacityLevel() + 1) * 1000;
        stockText = findViewById(R.id.factoryStockText);
        stockText.setText(text);
        /* --------- End pictures creation --------- */

        //EnterFactoryTask task = new EnterFactoryTask(userId, factory, context);

        ImageView factoryBack = findViewById(R.id.factoryBack);
        factoryBack.setOnClickListener(view -> {
            //Revenir en arrière sur une activity
            soundManager.playSoundOut();
            FactoryActivity.this.finish(); //"dépile" la stack d'activity
        });

        /* ---------- Change production ---------- */
        for (int i=0; i<3; i++){
            int line = i;
            ImageButton productionLine = findViewById(ViewContent.factoryProduction[line]);
            productionLine.setOnClickListener(view -> {
                soundManager.playSoundIn();
                if (factory.getLine(line) != null) {
                    openPopupSelection(line);
                } else{
                    openBuyLine(line);
                }
            });
        }
        /* ---------- End change production ---------- */

        /* ---------- Upgrade buttons ---------- */
        //Line 1
        for (int i=0; i<3; i++){
            int line = i;
            Button factoryBeltButtonLine = findViewById(ViewContent.factoryBeltButtons[line]);
            factoryBeltButtonLine.setOnClickListener(view -> {
                soundManager.playSoundIn();
                int level = factory.getLine(line).getMachineLevel(0);
                openPopupUpgrade(level, ImageContent.beltImagesId[level],line,0, factoryBeltButtonLine);
            });
            Button factoryRobotButtonLine = findViewById(ViewContent.factoryRobotsButtons[line]);
            factoryRobotButtonLine.setOnClickListener(view -> {
                soundManager.playSoundIn();
                int level = factory.getLine(line).getMachineLevel(1);
                openPopupUpgrade(level, ImageContent.robotImagesId[level],line,1, factoryRobotButtonLine);
            });
            Button factoryOvenButtonLine = findViewById(ViewContent.factoryOvenButtons[line]);
            factoryOvenButtonLine.setOnClickListener(view -> {
                soundManager.playSoundIn();
                int level = factory.getLine(line).getMachineLevel(2);
                openPopupUpgrade(level, ImageContent.ovenImagesId[level],line,2, factoryOvenButtonLine);
            });
            if (factory.getLine(line) == null){
                factoryBeltButtonLine.setEnabled(false);
                factoryRobotButtonLine.setEnabled(false);
                factoryOvenButtonLine.setEnabled(false);
                factoryBeltButtonLine.setBackground(getResources().getDrawable(R.drawable.red_button_selector));
                factoryRobotButtonLine.setBackground(getResources().getDrawable(R.drawable.red_button_selector));
                factoryOvenButtonLine.setBackground(getResources().getDrawable(R.drawable.red_button_selector));
            }else{
                if(factory.getLine(line).getMachineLevel(0)==9){ factoryBeltButtonLine.setBackground(getResources().getDrawable(R.drawable.gold_button_selector)); }
                if(factory.getLine(line).getMachineLevel(1)==9){ factoryRobotButtonLine.setBackground(getResources().getDrawable(R.drawable.gold_button_selector)); }
                if(factory.getLine(line).getMachineLevel(2)==9){ factoryOvenButtonLine.setBackground(getResources().getDrawable(R.drawable.gold_button_selector)); }
            }
        }

        Button factoryButtonStock = findViewById(R.id.factoryButtonStock);
        factoryButtonStock.setOnClickListener(v -> {
            soundManager.playSoundIn();
            int level = factory.getCapacityLevel();
            openPopupUpgrade(level, R.drawable.title,0,-1, factoryButtonStock);
        });

        /* ---------- End upgrade button ---------- */

        Button sell = findViewById(R.id.factorySell);
        sell.setOnClickListener(v -> {
            soundManager.playSoundIn();
            openPopupSell(context);
        });

    }

    //exemple
    @SuppressLint("SetTextI18n")
    void openPopupUpgrade(int initLevel, int res, int line, int id, Button button)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_upgrade);
        Button popupUpgradeCancel = dialog.findViewById(R.id.popupUpgradeCancel);
        popupUpgradeCancel.setOnClickListener(v -> {
            soundManager.playSoundOut();
            dialog.dismiss();
        });

        String text; //pour le warning : on ne doit pas avoir de concaténation dans le xxx.setText(xxx);

        TextView popupUpgradeMessage = dialog.findViewById(R.id.popupUpgradeMessage);
        if(id == -1)
        {
            text = getString(R.string.upgrade_valid)+" "+Factory.getCapacityPrice(initLevel+1)+" $";
        }
        else
        {
            text = getString(R.string.upgrade_valid)+" "+(initLevel *1000)+" $";
        }

        popupUpgradeMessage.setText(text);


        if(initLevel == 9){
            popupUpgradeMessage.setText(R.string.max_level);
            popupUpgradeCancel.setVisibility(View.INVISIBLE);
            popupUpgradeCancel.setClickable(false);
        }

        Button popupUpgradeOk = dialog.findViewById(R.id.popupUpgradeOk);
        popupUpgradeOk.setOnClickListener(v -> {
            soundManager.playSoundIn();
            if (initLevel <=8) {
                if (id >= 0){
                    UpgradeMachineTask task = new UpgradeMachineTask(userId, line, id, score, context, factory);
                    task.execute();
                    factory.getLine(line).setMachineLevel(id, initLevel + 1);
                }
                else // CAPACITY UPGRADE
                    {
                    TextView capacityView = findViewById(R.id.factoryStockText);
                    UpgradeCapacityTask upgradeCapacityTask = new UpgradeCapacityTask(factory, context, capacityView, userId, initLevel);
                    upgradeCapacityTask.execute();
                    GetScoreTask getScoreTask = new GetScoreTask(userId, userScore, context);
                    getScoreTask.execute();
                }

                if (id == 0) {
                    allBelts.get(line).setBackground(getResources().getDrawable(ImageContent.beltImagesId[initLevel +1]));
                }
                else if (id == 1) {
                    allRobots.get(line).setImageResource(ImageContent.robotImagesId[initLevel +1]);
                }
                else if (id == 2){
                    allOvens.get(line).setImageResource(ImageContent.ovenImagesId[initLevel +1]);
                }
                allProductSpeed.get(line).setText(factory.getLine(line).getProduction()+"/m");

                if (initLevel == 8) {
                    button.setBackground(getResources().getDrawable(R.drawable.gold_button_selector));
                    button.setText("M");
                }
                dialog.dismiss();
            }else {
                dialog.dismiss();}
        });

        TextView popupUpgradeLevel = dialog.findViewById(R.id.popupUpgradeLevel);
        String levelText = getString(R.string.level) + initLevel;
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
        popupMessageCancel.setOnClickListener(v -> {
            soundManager.playSoundOut();
            dialog.dismiss();
        });

        for (int i=0; i<3; i++){
            int id = i;
            ImageButton cakeSelect = dialog.findViewById(ViewContent.popUpCakeSell[i]);
            cakeSelect.setOnClickListener(v -> {
                soundManager.playSoundIn();
                allProduction.get(line).setImageDrawable(getResources().getDrawable(ImageContent.cakeImageId[id]));
                factory.getLine(line).setCakeId(id);
                dialog.dismiss();
            });
        }

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }

    void openBuyLine (int line){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_question);
        Button popupMessageCancel = dialog.findViewById(R.id.popupQuestionCancel);
        popupMessageCancel.setOnClickListener(v -> {
            soundManager.playSoundOut();
            dialog.dismiss();
        });

        TextView popupQuestionMessage = dialog.findViewById(R.id.popupQuestionMessage);
        popupQuestionMessage.setText(R.string.line_price);
        GetLinePriceTask getLinePrice = new GetLinePriceTask(factory.getFactorySpot(), line, popupQuestionMessage, context);
        getLinePrice.execute();

        ImageView popupQuestionImage = dialog.findViewById(R.id.popupQuestionImage);
        popupQuestionImage.setImageResource(R.drawable.ic_cadena);

        Button popupMessageOk = dialog.findViewById(R.id.popupQuestionOk);
        popupMessageOk.setOnClickListener(v -> {
            soundManager.playSoundIn();
            openPopupSelectionNew(line);
            dialog.dismiss();
        });

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }

    void openPopupSelectionNew(int line)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_cake_sel);
        ImageView popupMessageCancel = dialog.findViewById(R.id.popupCakeSelBack);
        popupMessageCancel.setOnClickListener(v -> dialog.dismiss());

        for (int i=0; i<3; i++){
            int id = i;
            ImageButton cakeSelect = dialog.findViewById(ViewContent.popUpCakeSell[i]);
            cakeSelect.setOnClickListener(v -> {
                score = shr.getInt("score",0);
                BuyLineTask buyLine = new BuyLineTask(userId, line+1, id, score, context, factory, FactoryActivity.this);
                buyLine.execute();
                dialog.dismiss();
            });
        }

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }

    void openPopupSell(Context context)
    {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_market_sell);
        ImageView popupMessageCancel = dialog.findViewById(R.id.popupMarketSellBack);
        popupMessageCancel.setOnClickListener(v -> {
            soundManager.playSoundOut();
            dialog.dismiss();
        });

        TextView marketSellAlert = dialog.findViewById(R.id.marketSellAlert);

        final String[] text = new String[1]; //pour le warning : on ne doit pas avoir de concaténation dans le xxx.setText(xxx);
        for(int i=0; i<3; i++)
        {
            TextView stock = dialog.findViewById(ViewContent.sellMarketCakeText[i]);
            text[0] = factory.getCurrentStocks().get(i)+"";
            stock.setText(text[0]);

            ImageButton cakeSell = dialog.findViewById(ViewContent.sellCakeButtons[i]);
            int cake = i;
            cakeSell.setOnClickListener(v -> {
                soundManager.playSoundSell();
                SellStockTask sellStockTask = new SellStockTask(userId, factory, cake, context, stock, userScore, stockText, marketSellAlert);
                sellStockTask.execute();
            });
        }

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }
}
