package l3info.projet.cakemarketingfactory.task;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.lang.ref.WeakReference;

import l3info.projet.cakemarketingfactory.activity.MessagesActivity;
import l3info.projet.cakemarketingfactory.utils.Contents;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SuppressLint("StaticFieldLeak")
public class UploadImageTask extends AsyncTask<String, Void, Boolean> {

    private final WeakReference<Context> ctx;
    private File file;
    private int userId;

    public UploadImageTask(Context ctx, File file, int userId) {
        this.ctx = new WeakReference<>(ctx);
        this.file = file;
        this.userId = userId;
    }

    @Override
    protected Boolean doInBackground(String... params) {

        //NON DEVELOPE PAR MANQUE DE TEMPS
        //CODE LAISSE ICI EN CAS DE REPRISE

        MediaType MEDIA_TYPE = MediaType.parse("image/png");
        RequestBody filebody = RequestBody.create(MEDIA_TYPE, file);
        String contentType = getMimeType(file.getPath());
        String filePath = file.getAbsolutePath();
        String filePathImageName = filePath.substring(filePath.lastIndexOf("/")+1);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("type", contentType)
                .addFormDataPart("uploadedFile",filePathImageName, filebody)
                .build();

        Request request = new Request.Builder()
                .url(Contents.RESULT_IMAGE_URL+ Contents.SAVE_FILE)
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();

        try {
            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean requestResult) {
        super.onPostExecute(requestResult);
        if(requestResult) {
            Context ctx = this.ctx.get();
            Intent intent = new Intent(ctx, MessagesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            ctx.startActivity(intent);
        }
    }

    private String getMimeType(String path)
    {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }
}
