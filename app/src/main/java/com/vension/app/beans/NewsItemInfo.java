package com.vension.app.beans;

import java.util.List;

public class NewsItemInfo {

    private String title;
    private List<String> imageUrls;
    private long publishDate;
    private String viewCount;
    private String commentCount;
    private String likeCount;

    public String getTitle() {
        return title;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public long getPublishDate() {
        return publishDate;
    }

    public String getViewCount() {
        return viewCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public String getLikeCount() {
        return likeCount;
    }
}
