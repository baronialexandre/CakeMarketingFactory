package l3info.projet.cakemarketingfactory.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;
import java.util.Objects;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.activity.manager.SoundManager;
import l3info.projet.cakemarketingfactory.activity.view.MarketGraph;
import l3info.projet.cakemarketingfactory.activity.view.MarketTimeView;
import l3info.projet.cakemarketingfactory.model.Market;
import l3info.projet.cakemarketingfactory.model.Votes;
import l3info.projet.cakemarketingfactory.task.CastVoteTask;
import l3info.projet.cakemarketingfactory.task.GetStockTask;
import l3info.projet.cakemarketingfactory.task.SellAllStockTask;
import l3info.projet.cakemarketingfactory.utils.Contents;
import l3info.projet.cakemarketingfactory.utils.FunctionUtil;

public class MarketActivity extends AppCompatActivity
{

    Market market;
    Votes votes;
    TextView userScoreDisplay;
    SharedPreferences shr;
    SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        Context context = this;
        soundManager = new SoundManager(this);

        shr = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);

        market = (Market) getIntent().getSerializableExtra("market");
        votes = (Votes) getIntent().getSerializableExtra("votes");
        int userScore = getIntent().getIntExtra("userScore", 0);

        // USER SCORE DISPLAY
        userScoreDisplay = findViewById(R.id.marketCapital);
        userScoreDisplay.setText(String.format(Locale.ROOT, "%s$", FunctionUtil.scoreShorten(userScore)));

        // GRAPH
        MarketGraph marketGraph = findViewById(R.id.marketGraph);
        marketGraph.setMarket(market);

        // SELECTED PRODUCT PRICE DISPLAY
        TextView marketSelectedPrice = findViewById(R.id.marketSelectedPrice);

        // DEFAULT DISPLAY = COOKIES (0)
        setProductToDisplay(0, marketGraph, marketSelectedPrice);

        // SELECT PRODUCT BUTTONS
        ImageButton marketCookie = findViewById(R.id.marketCookie);
        ImageButton marketCupcake = findViewById(R.id.marketCupcake);
        ImageButton marketDonut = findViewById(R.id.marketDonut);

        marketCookie.setOnClickListener(v   -> {
            soundManager.playSoundIn();
            setProductToDisplay(0, marketGraph, marketSelectedPrice);
        });
        marketCupcake.setOnClickListener(v    -> {
            soundManager.playSoundIn();
            setProductToDisplay(1, marketGraph, marketSelectedPrice);
        });
        marketDonut.setOnClickListener(v  -> {
            soundManager.playSoundIn();
            setProductToDisplay(2, marketGraph, marketSelectedPrice);
        });



        // COUNTDOWN
        MarketTimeView marketTimeView = findViewById(R.id.marketTimeView);
        marketTimeView.setOnClickListener(v -> {
            soundManager.playSoundIn();
            openPopupTimeInfo(context);
        });
        marketTimeView.start();



        Button sellAll = findViewById(R.id.marketSell);
        sellAll.setOnClickListener(v -> {
            soundManager.playSoundIn();
            openPopupSell(context);
        });

        Button advertising = findViewById(R.id.marketAdvertising);
        advertising.setOnClickListener(v -> {
            soundManager.playSoundIn();
            openPopupAds(context, votes);
        });

        ImageView marketBack = findViewById(R.id.marketBack);
        marketBack.setOnClickListener(view -> {
            //Changer d'activity
            soundManager.playSoundOut();
            MarketActivity.this.finish(); //dépile la stack d'activity
        });
    }


    //exemple
    void openPopupSell(Context context)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_market_sell_all);
        ImageView popupMessageCancel = dialog.findViewById(R.id.popupMarketSellBack);
        popupMessageCancel.setOnClickListener(v -> {
            soundManager.playSoundOut();
            dialog.dismiss();
        });

        SharedPreferences shr = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
        long userId = shr.getLong("userId",0L);

        ImageButton popupMarketSellAllCookies = dialog.findViewById(R.id.popupMarketSellAllCookies);
        ImageButton popupMarketSellAllCupcakes = dialog.findViewById(R.id.popupMarketSellAllCupcakes);
        ImageButton popupMarketSellAllDonuts = dialog.findViewById(R.id.popupMarketSellAllDonuts);

        popupMarketSellAllCookies.setOnClickListener(v -> {
            soundManager.playSoundSell();
            SellAllStockTask sellAllStockTask = new SellAllStockTask(userId, 0, dialog, context, userScoreDisplay);
            sellAllStockTask.execute();
        });
        popupMarketSellAllCupcakes.setOnClickListener(v -> {
            soundManager.playSoundSell();
            SellAllStockTask sellAllStockTask = new SellAllStockTask(userId, 1, dialog, context, userScoreDisplay);
            sellAllStockTask.execute();
        });
        popupMarketSellAllDonuts.setOnClickListener(v -> {
            soundManager.playSoundSell();
            SellAllStockTask sellAllStockTask = new SellAllStockTask(userId, 2, dialog, context, userScoreDisplay);
            sellAllStockTask.execute();
        });

        GetStockTask getStockTask = new GetStockTask(userId, dialog);
        getStockTask.execute();

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }

    //exemple
    void openPopupAds(Context context, Votes votes)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_market_advertising);
        ImageView popupMessageCancel = dialog.findViewById(R.id.popupMarketAdvertisingBack);
        popupMessageCancel.setOnClickListener(v -> {
            soundManager.playSoundOut();
            dialog.dismiss();
        });

        ImageButton infos = dialog.findViewById(R.id.popupMarketAdvertisingInfo);
        infos.setOnClickListener(v -> {
            soundManager.playSoundIn();
            openInfos(context);
        });

        TextView popupMarketCookieDisplay = dialog.findViewById(R.id.popupMarketCookieDisplay);
        TextView popupMarketCupcakeDisplay = dialog.findViewById(R.id.popupMarketCupcakeDisplay);
        TextView popupMarketDonutDisplay = dialog.findViewById(R.id.popupMarketDonutDisplay);

        popupMarketCookieDisplay.setText(String.format(Locale.ROOT,"%.0f%%", votes.getPercentage(0)));
        popupMarketCupcakeDisplay.setText(String.format(Locale.ROOT,"%.0f%%", votes.getPercentage(1)));
        popupMarketDonutDisplay.setText(String.format(Locale.ROOT,"%.0f%%", votes.getPercentage(2)));

        //add bars
        RelativeLayout cookieBarBg = dialog.findViewById(R.id.popupMarketAdvertisingCookieBarBg);
        RelativeLayout cookieBar   = dialog.findViewById(R.id.popupMarketAdvertisingCookieBar);
        float calcul = votes.getPercentage(0)*cookieBarBg.getLayoutParams().width/100;
        cookieBar.getLayoutParams().width= (int) calcul;
        //cookieBar.requestLayout();

        RelativeLayout cupcakeBarBg = dialog.findViewById(R.id.popupMarketAdvertisingCupcakeBarBg);
        RelativeLayout cupcakeBar   = dialog.findViewById(R.id.popupMarketAdvertisingCupcakeBar);
        calcul = votes.getPercentage(1)*cupcakeBarBg.getLayoutParams().width/100;
        cupcakeBar.getLayoutParams().width= (int) calcul;

        RelativeLayout DonutBarBg = dialog.findViewById(R.id.popupMarketAdvertisingDonutBarBg);
        RelativeLayout DonutBar   = dialog.findViewById(R.id.popupMarketAdvertisingDonutBar);
        calcul = votes.getPercentage(2)*DonutBarBg.getLayoutParams().width/100;
        DonutBar.getLayoutParams().width= (int) calcul;
        //end bars

        SharedPreferences shr = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
        long userId = shr.getLong("userId",0L);

        ImageView popupMarketAdvertisingCookie = dialog.findViewById(R.id.popupMarketAdvertisingCookie);
        ImageView popupMarketAdvertisingCupcake = dialog.findViewById(R.id.popupMarketAdvertisingCupcake);
        ImageView popupMarketAdvertisingDonut = dialog.findViewById(R.id.popupMarketAdvertisingDonut);

        popupMarketAdvertisingCookie.setOnClickListener(v -> {
            CastVoteTask castVoteTask = new CastVoteTask(0, userId, dialog, context);
            castVoteTask.execute();
        });
        popupMarketAdvertisingCupcake.setOnClickListener(v -> {
            CastVoteTask castVoteTask = new CastVoteTask(1, userId, dialog, context);
            castVoteTask.execute();
        });
        popupMarketAdvertisingDonut.setOnClickListener(v -> {
            CastVoteTask castVoteTask = new CastVoteTask(2, userId, dialog, context);
            castVoteTask.execute();
        });

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }

    //exemple
    void openInfos(Context context)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_notification);
        Button popupMessageCancel = dialog.findViewById(R.id.popupNotificationOk);
        popupMessageCancel.setOnClickListener(v -> {
            soundManager.playSoundOut();
            dialog.dismiss();
        });

        TextView message = dialog.findViewById(R.id.popupNotificationMessage);

        String string = "Vous ne pouvez voter que pour un seul gateau par heure !\n" +
                "A la fin de l'heure, le gateau ayant le plus de vote aura droit à une publicité !\n" +
                "Les ventes des gateaux ayant une publicité vous rapporteront plus.\n" +
                "Le gateau ayant le moins de votes sera le produit le moins populaire et redescendra dans les ventes...\n";
        message.setText(string);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }

    void openPopupTimeInfo(Context context)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_notification);
        Button popupMessageCancel = dialog.findViewById(R.id.popupNotificationOk);
        popupMessageCancel.setOnClickListener(v -> {
            soundManager.playSoundOut();
            dialog.dismiss();
        });

        TextView message = dialog.findViewById(R.id.popupNotificationMessage);

        String string = "Temps restant avant la fin de la semaine.\n" +
                "A la fin de chaque semaine, toutes les entreprises sont remises à zéro : capital, usines, stocks ...\n" +
                "Les 3 meilleurs joueurs auront une place dans la galerie des gagnants.\n" +
                "Votre meilleur capital est conservé, et votre niveau augmente en fonction de votre capital final.";
        message.setText(string);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        dialog.show();
    }

    void setProductToDisplay(int productId, MarketGraph marketGraph, TextView priceDisplay)
    {
        marketGraph.setProductIdToDisplay(productId);
        priceDisplay.setText(String.format(Locale.ROOT,"%d$",market.lastProductPrice(productId)));
    }
}
