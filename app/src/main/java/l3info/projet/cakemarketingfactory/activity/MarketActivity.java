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
import l3info.projet.cakemarketingfactory.utils.ViewContent;

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


        for(int i = 0; i < 3; i++)
        {
            SharedPreferences shr = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
            long userId = shr.getLong("userId",0L);

            // Votes percentage
            TextView marketAdvertisingCakeText = dialog.findViewById(ViewContent.marketAdvertisingCakeText[i]);
            marketAdvertisingCakeText.setText(String.format(Locale.ROOT,"%.0f%%", votes.getPercentage(i)));

            // Vote bar
            RelativeLayout barBg = dialog.findViewById(ViewContent.marketAdvertisingVoteBarBg[i]);
            RelativeLayout bar = dialog.findViewById(ViewContent.marketAdvertisingVoteBar[i]);
            float barWidth = votes.getPercentage(i)*barBg.getLayoutParams().width/100;
            bar.getLayoutParams().width = (int)barWidth;

            // Image buttons
            ImageView popupMarketAdvertisingCakeImage = dialog.findViewById(ViewContent.popupMarketAdvertisingCakeImage[i]);
            int finalI = i;
            popupMarketAdvertisingCakeImage.setOnClickListener(v -> {
                CastVoteTask castVoteTask = new CastVoteTask(finalI, userId, dialog, context, votes);
                castVoteTask.execute();
            });
        }
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

        message.setText(R.string.market_votes_info);

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

        message.setText(R.string.market_time_info);

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
