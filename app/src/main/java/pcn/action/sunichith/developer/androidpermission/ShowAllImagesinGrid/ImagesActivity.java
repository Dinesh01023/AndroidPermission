package pcn.action.sunichith.developer.androidpermission.ShowAllImagesinGrid;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import pcn.action.sunichith.developer.androidpermission.MediaFilegetActivity;
import pcn.action.sunichith.developer.androidpermission.R;

public class ImagesActivity extends AppCompatActivity {


    String TAG="ImagesActivity";
    RecyclerView recyclerView_images;
    ListAdapter listAdapter;
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.images_activity);
        progressBar=findViewById(R.id.centerprogress);
        recyclerView_images=findViewById(R.id.images_recycler);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
        recyclerView_images.setLayoutManager(gridLayoutManager);
        listAdapter=new ListAdapter(this,null);

        Executors.newSingleThreadExecutor().execute(() -> {
            Handler mainHandler = new Handler(Looper.getMainLooper());
            List<MediaFilegetActivity.VideosModel> filess=MediaFilegetActivity.getAllMediaFilesOnDevice(this);

            List<MediaFilegetActivity.VideosModel> files=new ArrayList<>();
            for (int i=0;i<filess.size();i++){
                Log.e(TAG,filess.get(i).getFile().getName());
                if(filess.get(i).getFile().getName().toLowerCase().endsWith(".jpg") || filess.get(i).getFile().getName().toLowerCase().endsWith(".png") || filess.get(i).getFile().getName().toLowerCase().endsWith(".heic") || filess.get(i).getFile().getName().toLowerCase().endsWith(".jpeg") ||  filess.get(i).getFile().getName().toLowerCase().endsWith(".webp") ) {

                }else {
                    files.add(filess.get(i));
                }
            }
            mainHandler.post(() -> {
                 //listAdapter.setFile(filess);
                files.addAll(filess.subList(0,filess.size()));
                listAdapter=new ListAdapter(this,filess);
                recyclerView_images.setAdapter(listAdapter);
                progressBar.setVisibility(View.GONE);
            });

        });



    }
}
