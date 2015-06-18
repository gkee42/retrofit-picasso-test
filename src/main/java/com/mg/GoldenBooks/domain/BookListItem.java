package com.mg.GoldenBooks.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mg on 7/25/13.
 */
public class BookListItem implements Parcelable {

    /**
     * The unique identifier of the item
     */
    private int id;

    /**
     * The path to the item details
     */
    private String link;

    /**
     * The title of the item
     */
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "BookListItem{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(link);
        dest.writeString(title);
    }

    public static final Parcelable.Creator<BookListItem> CREATOR
            = new Parcelable.Creator<BookListItem>() {
        public BookListItem createFromParcel(Parcel in) {
            return new BookListItem(in);
        }

        public BookListItem[] newArray(int size) {
            return new BookListItem[size];
        }
    };

    private BookListItem(Parcel in) {
        id = in.readInt();
        link = in.readString();
        title = in.readString();
    }

    public BookListItem() {
        super();
    }

}
