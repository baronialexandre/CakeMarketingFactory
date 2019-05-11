package l3info.projet.cakemarketingfactory.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Objects;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.activity.view.MarketGraph;
import l3info.projet.cakemarketingfactory.activity.view.MarketTimeView;
import l3info.projet.cakemarketingfactory.model.Market;

public class MarketActivity extends AppCompatActivity
{

    Market market;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        Context context = this;

        market = (Market) getIntent().getSerializableExtra("market");

        MarketGraph marketGraph = findViewById(R.id.marketGraph);
        marketGraph.setMarket(market);

        MarketTimeView marketTimeView = findViewById(R.id.marketTimeView);
        marketTimeView.setOnClickListener(v -> openPopupTimeInfo(context));
        marketTimeView.start();

        Button sellAll = findViewById(R.id.marketSell);
        sellAll.setOnClickListener(v -> openPopupSell(context));

        Button advertising = findViewById(R.id.marketAdvertising);
        advertising.setOnClickListener(v -> openPopupAds(context));

        ImageView marketBack = findViewById(R.id.marketBack);
        marketBack.setOnClickListener(view -> {
            //Changer d'activity
            MarketActivity.this.finish(); //dépile la stack d'activity
        });
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

    //exemple
    void openPopupAds(Context context)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_market_advertising);
        ImageView popupMessageCancel = dialog.findViewById(R.id.popupMarketAdvertisingBack);
        popupMessageCancel.setOnClickListener(v -> dialog.dismiss());

        ImageButton infos = dialog.findViewById(R.id.popupMarketAdvertisingInfo);
        infos.setOnClickListener(v -> openInfos(context));

        ProgressBar progressBarCookie = dialog.findViewById(R.id.popupMarketAdvertisingCookieBar);
        ProgressBar progressBarCupcake = dialog.findViewById(R.id.popupMarketAdvertisingCupcakeBar);
        ProgressBar progressBarDonut = dialog.findViewById(R.id.popupMarketAdvertisingDonutBar);

        progressBarCookie.setProgress(25);
        progressBarCupcake.setProgress(5);
        progressBarDonut.setProgress(70);

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
        popupMessageCancel.setOnClickListener(v -> dialog.dismiss());

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
        popupMessageCancel.setOnClickListener(v -> dialog.dismiss());

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
}
