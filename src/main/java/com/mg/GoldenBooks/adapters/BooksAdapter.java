
package com.mg.GoldenBooks.adapters;

import com.mg.GoldenBooks.R;
import com.mg.GoldenBooks.domain.BookListItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BooksAdapter extends BaseAdapter {

    private BookListItem[] mBooksList;

    private final LayoutInflater mInflater;

    public BooksAdapter(final Context context, final BookListItem[] booksList) {
        super();
        mBooksList = booksList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mBooksList != null) {
            return mBooksList.length;
        } else {
            return 0;
        }
    }

    @Override
    public BookListItem getItem(final int position) {
        if (mBooksList != null && mBooksList.length > position) {
            return mBooksList[position];
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public void changeBooksList(final BookListItem[] booksList) {
        // Swap
        mBooksList = booksList;
        // update front end
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView,
            final ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.view_list_item_book,
                    parent, false);
            holder = getViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final BookListItem book = getItem(position);
        if (book != null) {
            holder.bookTitle.setText(book.getTitle());
        }

        return convertView;
    }

    /**
     * This method initialises the viewholder views
     *
     * @param viewToBind An inflated instance of the view_list_item_book view.
     * @return The initialised view holder
     */
    private static ViewHolder getViewHolder(final View viewToBind) {
        final ViewHolder holder = new ViewHolder();

        holder.bookTitle = (TextView) viewToBind
                .findViewById(R.id.view_list_item_book_title);

        return holder;
    }

    private static class ViewHolder {

        TextView bookTitle;
    }
}
