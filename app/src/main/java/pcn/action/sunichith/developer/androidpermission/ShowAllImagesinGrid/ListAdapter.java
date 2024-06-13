package pcn.action.sunichith.developer.androidpermission.ShowAllImagesinGrid;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import pcn.action.sunichith.developer.androidpermission.MainActivity;
import pcn.action.sunichith.developer.androidpermission.MediaFilegetActivity;
import pcn.action.sunichith.developer.androidpermission.R;

public class ListAdapter extends RecyclerView.Adapter {

    Context context;
    List<MediaFilegetActivity.VideosModel> file;
    ListAdapter(Context context,List<MediaFilegetActivity.VideosModel> file){
        this.context = context;
        this.file = file;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.image,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RequestOptions option = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop();

        Glide.with(context).load(file.get(position).getFile().getAbsolutePath()).apply(option).placeholder(R.drawable.placeholder).into(((ListViewHolder)holder).image);

    }


    @Override
    public int getItemCount() {
        if (file==null){
            return 0;
        }  else {
            return file.size();
        }
    }


    class ListViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
        }
    }


    public void setFile(List<MediaFilegetActivity.VideosModel> file) {
        this.file = file;
        notifyDataSetChanged();
    }
}
