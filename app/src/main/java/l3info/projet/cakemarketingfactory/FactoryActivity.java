package l3info.projet.cakemarketingfactory;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import l3info.projet.cakemarketingfactory.utils.ImageContent;

public class FactoryActivity extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory);

        context = this;

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
        Button factoryRobotButtonLine2 = findViewById(R.id.factoryRobotButtonLine1);
        factoryRobotButtonLine2.setOnClickListener(view -> {
            int level = 0;
            openPopupUpgrade(level, ImageContent.robotImagesID[level]);
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

        TextView popupUpgradeMessage = dialog.findViewById(R.id.popupUpgradeMessage);
        popupUpgradeMessage.setText("");

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
