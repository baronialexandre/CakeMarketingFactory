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

public class FactoryActivity extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factory);

        context = this;

        ImageView factoryBack = findViewById(R.id.factoryBack);
        factoryBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Changer d'activity
                Intent intentApp = new Intent(FactoryActivity.this, WorldActivity.class);
                FactoryActivity.this.startActivity(intentApp);
            }
        });


        Button factoryTravelatorButtonLine1 = findViewById(R.id.factoryTravelatorButtonLine1);
        factoryTravelatorButtonLine1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPopupUpgrade();
            }
        });
    }

    void openPopupUpgrade()
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_upgrade);
        Button popupUpgradeCancel = dialog.findViewById(R.id.popupUpgradeCancel);
        popupUpgradeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextView popupUpgradeMessage = dialog.findViewById(R.id.popupUpgradeMessage);
        popupUpgradeMessage.setText("Le prix c'est le prix !\nSi tu veux acheter... vend\nDU GATEAU !");

        TextView popupUpgradeLevel = dialog.findViewById(R.id.popupUpgradeLevel);
        popupUpgradeLevel.setText(getString(R.string.level) + " 1");

        ImageView popupUpgradeImage = dialog.findViewById(R.id.popupUpgradeImage);
        popupUpgradeImage.setImageResource(R.drawable.factory_travelator_1);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }
}
