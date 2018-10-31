package com.example.tam.cnpm.service.response;

public class DocBao {
    private String mTitle;
    private String mImage;
    private String mLink;

    public DocBao(String mTitle, String mImage, String mLink) {
        this.mTitle = mTitle;
        this.mImage = mImage;
        this.mLink = mLink;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmImage() {
        return mImage;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String mLink) {
        this.mLink = mLink;
    }

    @Override
    public String toString() {
        return "DocBao{" +
                "mTitle='" + mTitle + '\'' +
                ", mImage='" + mImage + '\'' +
                ", mLink='" + mLink + '\'' +
                '}';
    }
}

