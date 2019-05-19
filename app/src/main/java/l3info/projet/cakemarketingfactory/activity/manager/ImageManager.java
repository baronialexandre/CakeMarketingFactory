package l3info.projet.cakemarketingfactory.activity.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import l3info.projet.cakemarketingfactory.task.UploadImageTask;
import l3info.projet.cakemarketingfactory.utils.FunctionUtil;

public class ImageManager {

    public ImageManager() {
        //classe permetant de gérer les images
    }

    public void combineAndSave(ArrayList<Bitmap> bitmaps)
    {
        Bitmap bmp = combine(bitmaps);
        saveImage(bmp);
    }

    //développement non terminé
    public void combineAndUpload(ArrayList<Bitmap> bitmaps, Context context, int userId)
    {
        Bitmap bmp = combine(bitmaps);
        upload(bmp, context, userId);
    }

    // algorithme du peintre, la premiere image est le background
    private Bitmap combine(ArrayList<Bitmap> bitmaps)
    {
        Bitmap bitmap = Bitmap.createBitmap(
                bitmaps.get(0).getWidth(),
                bitmaps.get(0).getHeight(),
                Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(bitmap);
        for(Bitmap bmp : bitmaps)
            canvas.drawBitmap(bmp, 0f, 0f, null);
        return bitmap;
    }

    private void saveImage(Bitmap finalBitmap) {
        String imagName = FunctionUtil.getCurrent("yyyyMMdd_HHmmss");
        String root = Environment.getExternalStorageDirectory().toString() + "/CMF/";
        File myDir = new File(root);
        if (!myDir.exists())
            myDir.mkdirs();
        String fname = imagName + ".png";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void upload(Bitmap bmp, Context context, int userId) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        String imagName = FunctionUtil.getCurrent("yyyyMMdd_HHmmss");
        File file = bitmapToFile(bmp, context, imagName+".png");


        UploadImageTask uploadImageTask = new UploadImageTask(context, file, userId);
        uploadImageTask.execute();
    }

    private File bitmapToFile(Bitmap bitmap, Context context, String filename)
    {
        try {
            File file = new File(context.getCacheDir(), filename);
            file.createNewFile();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new File("");
    }

}