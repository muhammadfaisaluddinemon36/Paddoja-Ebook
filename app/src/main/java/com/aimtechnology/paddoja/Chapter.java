package com.aimtechnology.paddoja;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

public class Chapter implements Serializable {

    private String chapterTitle;
    private String chapterDescription;
    private String chapterFileUrl;
    private Context context;

    public Chapter() {
    }

    public Chapter(String chapterTitle, String chapterDescription, String chapterFileUrl) {
        this.chapterTitle = chapterTitle;
        this.chapterDescription = chapterDescription;
        this.chapterFileUrl = chapterFileUrl;
    }

//    private String readFirstTwoLinesFromChapter() {
//        AssetManager assetManager = context.getAssets(); // Access getAssets() via the Context object
//        StringBuilder stringBuilder = new StringBuilder();
//        try {
//            InputStream inputStream = assetManager.open(chapterFileName);
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            String line;
//            int lineCount = 0;
//            while ((line = bufferedReader.readLine()) != null && lineCount < 2) {
//                stringBuilder.append(line).append("\n");
//                lineCount++;
//            }
//            inputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "Error loading chapter description";
//        }
//        return stringBuilder.toString();
//    }

    // Getters and setters
    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public String getChapterDescription() {
        return chapterDescription;
    }

    public void setChapterDescription(String chapterDescription) {
        this.chapterDescription = chapterDescription;
    }

    public String getChapterFileUrl() {
        return chapterFileUrl;
    }

    public void setChapterFileUrl(String chapterFileUrl) {
        this.chapterFileUrl = chapterFileUrl;
    }

}
