package com.aimtechnology.paddoja;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aimtechnology.paddoja.Chapter;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {

    private Context context;
    private List<Chapter> chapterList;
    private ChapterClickListener chapterClickListener;

    public ChapterAdapter(List<Chapter> chapterList, ChapterClickListener chapterClickListener) {
        this.chapterList = chapterList;
        this.chapterClickListener = chapterClickListener;
    }

    @NonNull
    @Override
    public ChapterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_chapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterAdapter.ViewHolder holder, int position) {
        Chapter chapter = chapterList.get(position);
        holder.bind(chapter);
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public  TextView title, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewChapterNumber);
            title = itemView.findViewById(R.id.textViewChapterTitle);
            description = itemView.findViewById(R.id.textViewChapterDescription);
        }

        public void bind(Chapter chapter) {
            title.setText(chapter.getChapterTitle());
            fetchChapterContent(chapter.getChapterFileUrl(), description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chapterClickListener.onChapterClick(chapter);
                }
            });
        }
    }

    private void fetchChapterContent(String chapterFileUrl, TextView description) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(chapterFileUrl);

        storageRef.getBytes(1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                String fileContent = new String(bytes);
                displayFileContent(fileContent, description);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed to fetch chapter content", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayFileContent(String fileContent, TextView description) {
        description.setText(fileContent);
    }

    public interface ChapterClickListener {
        void onChapterClick(Chapter chapter);
    }
}
