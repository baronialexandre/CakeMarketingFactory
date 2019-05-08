package l3info.projet.cakemarketingfactory;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MarketActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        Context context = this;

        ImageView marketBack = findViewById(R.id.marketBack);
        marketBack.setOnClickListener(view -> {
            //Changer d'activity
            Intent intentApp = new Intent(MarketActivity.this, WorldActivity.class);
            MarketActivity.this.startActivity(intentApp);
        });

        Button sellAll = findViewById(R.id.marketSell);
        sellAll.setOnClickListener(v -> openPopupSell(context));

        Button advertising = findViewById(R.id.marketAdvertising);
        advertising.setOnClickListener(v -> openPopupAds(context));
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
}
