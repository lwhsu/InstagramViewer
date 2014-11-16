package org.lwhsu.android.instagramviewer;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(final Context context, final List<InstagramPhoto> photos) {
     super(context, android.R.layout.simple_list_item_1, photos);
    }

    // getView method (int position)
    // Default, takes the model (InstagramPhoto) toString()

}
