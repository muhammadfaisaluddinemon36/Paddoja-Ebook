package com.aimtechnology.paddoja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChapterAdapter adapter;
    private ShimmerFrameLayout shimmerFrameLayout;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference("chapters");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<Chapter> chapters = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chapters.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chapter chapter = dataSnapshot.getValue(Chapter.class);
                    chapters.add(chapter);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



//        // 11-20
//        reference.push().setValue(new Chapter("পর্ব-১১", "", "https://firebasestorage.googleapis.com/v0/b/paddoja-38c54.appspot.com/o/chapter_11.txt?alt=media&token=3e3e3e3e-3e3e-3e3e-3e3e-3e3e3e3e3e3e"));
//        reference.push().setValue(new Chapter("পর্ব-১২", "", "https://firebasestorage.googleapis.com/v0/b/paddoja-38c54.appspot.com/o/chapter_12.txt?alt=media&token=3e3e3e3e-3e3e-3e3e-3e3e-3e3e3e3e3e3e"));
//        reference.push().setValue(new Chapter("পর্ব-১৩", "", "https://firebasestorage.googleapis.com/v0/b/paddoja-38c54.appspot.com/o/chapter_13.txt?alt=media&token=3e3e3e3e-3e3e-3e3e-3e3e-3e3e3e3e3e3e"));
//        reference.push().setValue(new Chapter("পর্ব-১৪", "", "https://firebasestorage.googleapis.com/v0/b/paddoja-38c54.appspot.com/o/chapter_14.txt?alt=media&token=3e3e3e3e-3e3e-3e3e-3e3e-3e3e3e3e3e3e"));
//        reference.push().setValue(new Chapter("পর্ব-১৫", "", "https://firebasestorage.googleapis.com/v0/b/paddoja-38c54.appspot.com/o/chapter_15.txt?alt=media&token=3e3e3e3e-3e3e-3e3e-3e3e-3e3e3e3e3e3e"));
//        reference.push().setValue(new Chapter("পর্ব-১৬", "", "https://firebasestorage.googleapis.com/v0/b/paddoja-38c54.appspot.com/o/chapter_16.txt?alt=media&token=3e3e3e3e-3e3e-3e3e-3e3e-3e3e3e3e3e3e"));
//        reference.push().setValue(new Chapter("পর্ব-১৭", "", "https://firebasestorage.googleapis.com/v0/b/paddoja-38c54.appspot.com/o/chapter_17.txt?alt=media&token=3e3e3e3e-3e3e-3e3e-3e3e-3e3e3e3e3e3e"));
//        reference.push().setValue(new Chapter("পর্ব-১৮", "", "https://firebasestorage.googleapis.com/v0/b/paddoja-38c54.appspot.com/o/chapter_18.txt?alt=media&token=3e3e3e3e-3e3e-3e3e-3e3e-3e3e3e3e3e3e"));
//        reference.push().setValue(new Chapter("পর্ব-১৯", "", "https://firebasestorage.googleapis.com/v0/b/paddoja-38c54.appspot.com/o/chapter_19.txt?alt=media&token=3e3e3e3e-3e3e-3e3e-3e3e-3e3e3e3e3e3e"));
//        reference.push().setValue(new Chapter("পর্ব-২০", "", "https://firebasestorage.googleapis.com/v0/b/paddoja-38c54.appspot.com/o/chapter_20.txt?alt=media&token=3e3e3e3e-3e3e-3e3e-3e3e-3e3e3e3e3e3e"));
//

        adapter = new ChapterAdapter(chapters, new ChapterAdapter.ChapterClickListener() {
            @Override
            public void onChapterClick(Chapter chapter) {
                Intent intent = new Intent(MainActivity.this, ChapterActivity.class);
                intent.putExtra("title", chapter.getChapterTitle());
                intent.putExtra("fileUrl", chapter.getChapterFileUrl());
                startActivity(intent);
            }
        });
        shimmerFrameLayout.stopShimmer();
        recyclerView.setAdapter(adapter);
    }

}
