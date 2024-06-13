package pcn.action.sunichith.developer.androidpermission;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.MergeCursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executors;

public class MediaFilegetActivity extends AppCompatActivity {

    String  TAG="MediaFilegetActivity";
    Button getfiles;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_access_activity);
        getfiles=findViewById(R.id.mediadget_btn);
        getfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getfiles.setText("getallMediafiles");

                Executors.newSingleThreadExecutor().execute(() -> {
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    List<VideosModel> filess=getAllMediaFilesOnDevice(MediaFilegetActivity.this);

                    List<VideosModel> files=new ArrayList<>();
                    for (int i=0;i<filess.size();i++){
                        Log.e(TAG,filess.get(i).getFile().getName());
                        if(filess.get(i).getFile().getName().toLowerCase().endsWith(".jpg") || filess.get(i).getFile().getName().toLowerCase().endsWith(".png") || filess.get(i).getFile().getName().toLowerCase().endsWith(".heic") || filess.get(i).getFile().getName().toLowerCase().endsWith(".jpeg") ||  filess.get(i).getFile().getName().toLowerCase().endsWith(".webp") ) {

                        }else {
                            files.add(filess.get(i));
                        }
                    }
                        mainHandler.post(() -> {
                          getfiles.setText("all file get done ");
                        });

                });


            }
        });

    }


    public static class VideosModel {
        File file;
        private String id;

        VideosModel(File file){
            this.file = file;

            this.id = UUID.randomUUID().toString();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }



        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            VideosModel that = (VideosModel) o;
            return Objects.equals(file, that.file);
        }

        @Override
        public int hashCode() {
            return Objects.hash(file);
        }

        public  DiffUtil.ItemCallback<VideosModel> itemCallback = new DiffUtil.ItemCallback<VideosModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull VideosModel oldItem, @NonNull VideosModel newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull VideosModel oldItem, @NonNull VideosModel newItem) {
                return oldItem.equals(newItem);
            }
        };

    }

    public static List<VideosModel> getAllMediaFilesOnDevice(Context context) {
        List<VideosModel> files = new ArrayList<>();
        try {

            final String[] columns = { MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DATE_ADDED,
                    MediaStore.Images.Media.BUCKET_ID,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

            MergeCursor cursor = new MergeCursor(new Cursor[]{context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, null),
                    context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, null, null, null),
                    context.getContentResolver().query(MediaStore.Images.Media.INTERNAL_CONTENT_URI, columns, null, null, null),
                    context.getContentResolver().query(MediaStore.Video.Media.INTERNAL_CONTENT_URI, columns, null, null, null)
            });
            cursor.moveToFirst();
            files.clear();
            while (!cursor.isAfterLast()){
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

                int lastPoint = path.lastIndexOf(".");
                path = path.substring(0, lastPoint) + path.substring(lastPoint).toLowerCase();
                File file=  new File(path);
                files.add(new VideosModel(new File(path)));
                cursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }



}
