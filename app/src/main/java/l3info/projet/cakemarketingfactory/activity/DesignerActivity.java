package l3info.projet.cakemarketingfactory.activity;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.activity.manager.ImageManager;
import l3info.projet.cakemarketingfactory.utils.Contents;
import l3info.projet.cakemarketingfactory.utils.ImageContent;
import l3info.projet.cakemarketingfactory.utils.RotationSelector;
import l3info.projet.cakemarketingfactory.utils.ViewContent;

//DESIGNER DOIT CHANGER MAIS JE N'AVAIS PAS LE TEMPS
//D'Y CONSACRER PLUS DE TEMPS !!

public class DesignerActivity extends AppCompatActivity {
    /*Permissions*/
    private int STORAGE_PERMISSION_CODE = 1;
    /**/

    RotationSelector<Drawable> rotationBackground;
    RelativeLayout designerFrame;

    boolean[] toolMask = {false,false,false,false,false};

    int MAX_LAYER = 3;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer);

        context = this;

        TextView layerText = findViewById(R.id.designerLayerCount);
        layerText.setText(layerUsed(toolMask)+"/"+MAX_LAYER);

        ArrayList<Drawable> backgrounds = new ArrayList<>();
        for(int i=0; i<3; i++)
            backgrounds.add(getResources().getDrawable(ImageContent.backgroundImageId[i]));
        rotationBackground = new RotationSelector<>(backgrounds);

        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2f);

        designerFrame = findViewById(R.id.designerFrame);

        ImageView background = new ImageView(this);
        background.setLayoutParams(layoutParams1);
        background.setImageBitmap(((BitmapDrawable) rotationBackground.next()).getBitmap());
        designerFrame.addView(background);

        ImageView back = findViewById(R.id.designerBack);
        back.setOnClickListener(v -> finish());

        Button save = findViewById(R.id.designerSaveOnDevice);
        save.setOnClickListener(v -> saveButton());

        Button publish = findViewById(R.id.designerPublish);
        publish.setOnClickListener(v -> publishButton());

        /*----------- tools -----------*/
        ImageButton toolCake = findViewById(R.id.designerToolCake);
        toolCake.setOnClickListener(v -> {
            background.setImageBitmap(((BitmapDrawable) rotationBackground.next()).getBitmap());
            //Toast.makeText(this, rotationBackgrounds.getIndex()+"", Toast.LENGTH_SHORT).show();
        });

        for(int i=0; i<ViewContent.tools.length; i++)
        {
            ImageView image = new ImageView(this);
            image.setLayoutParams(layoutParams1);
            if(i < ImageContent.toolsImageId.length)
                image.setImageBitmap(((BitmapDrawable) getResources().getDrawable(ImageContent.toolsImageId[i])).getBitmap());
            else
                image.setImageBitmap(((BitmapDrawable) getResources().getDrawable(ImageContent.toolsImageId[0])).getBitmap());
            designerFrame.addView(image);

            image.setVisibility(View.INVISIBLE);

            ImageButton tool = findViewById(ViewContent.tools[i]);

            if(i < ImageContent.toolsImageId.length)
                tool.setImageResource(ImageContent.toolsMiniImageId[i]);

            int finalI = i;
            tool.setOnClickListener(v -> {
                toolMask[finalI] = !toolMask[finalI];
                if (toolMask[finalI]) {
                    if(layerUsed(toolMask) <= MAX_LAYER) {
                        image.setVisibility(View.VISIBLE);
                    }
                    else {
                        toolMask[finalI] = false;
                    }
                } else {
                    image.setVisibility(View.INVISIBLE);
                }
                int layerUsednb = layerUsed(toolMask);
                layerText.setText(layerUsed(toolMask)+"/"+MAX_LAYER);
                if(layerUsednb == MAX_LAYER)
                    layerText.setTextColor(getResources().getColor(R.color.factoryButtonRed));
                else
                    layerText.setTextColor(getResources().getColor(R.color.factoryButtonGreen));
            });
        }
    }

    public int layerUsed(boolean[] mask)
    {
        int result = 0;
        for(boolean bool : mask)
        {
            if(bool) result++;
        }
        return result;
    }

    private void requestStoragePermission()
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            new AlertDialog.Builder(this)
                    .setTitle("Permissions")
                    .setMessage("Nous ne pouvons pas enregistrer vos chef d'oeuvre si vous refusez...")
                    .setPositiveButton("Ok", (dialogInterface, i) -> ActivityCompat.requestPermissions(DesignerActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE))
                    .setNegativeButton("Annuler", (dialogInterface, i) -> dialogInterface.dismiss())
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    private void saveButton()
    {
        requestStoragePermission();
        Thread thread = new Thread(){
            public void run(){
                ImageManager imageManager = new ImageManager();
                ArrayList<Bitmap> layers = new ArrayList<>();

                layers.add(((BitmapDrawable) rotationBackground.getCurrent()).getBitmap());
                for(int i=0; i<toolMask.length; i++)
                {
                    if(toolMask[i])
                        layers.add(((BitmapDrawable) getResources().getDrawable(ImageContent.toolsImageId[i])).getBitmap());
                }

                imageManager.combineAndSave(layers);
            }
        };
        thread.start();
        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
    }

    private void publishButton()
    {
        /*
        ImageManager imageManager = new ImageManager();
        ArrayList<Bitmap> layers = new ArrayList<>();

        layers.add(((BitmapDrawable) rotationBackground.getCurrent()).getBitmap());
        for(int i=0; i<toolMask.length; i++)
        {
            if(toolMask[i])
                layers.add(((BitmapDrawable) getResources().getDrawable(ImageContent.toolsImageId[i])).getBitmap());
        }
        SharedPreferences shr = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
        long userId = shr.getLong("userId",-1);
        imageManager.combineAndUpload(layers, context, (int) userId);
        Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show();
        */
        Toast.makeText(context, "Non implémenté", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE) {
            if(grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permissions refusées, sauvegarde indisponible...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
