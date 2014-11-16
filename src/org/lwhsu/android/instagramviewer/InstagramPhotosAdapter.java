package org.lwhsu.android.instagramviewer;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(final Context context, final List<InstagramPhoto> photos) {
     super(context, android.R.layout.simple_list_item_1, photos);
    }

    // Takes a data item at a position, converts it to a row in the listview
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // Take the data source at position (i.e. 0)

        // Get the data item
        final InstagramPhoto photo = getItem(position);

        // Check if we are using a recycled view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        // Lookup the subview within the template
        final TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        final TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        final TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        final ImageView imgPhoto = (ImageView) convertView.findViewById(R.id.imgPhoto);

        // Populate the subviews (textfield, imageview) with the correct data
        tvCaption.setText(photo.caption);
        tvUsername.setText(photo.username);
        tvLikes.setText("" + photo.likesCount);
        // Set the image height before loading
        imgPhoto.getLayoutParams().height = photo.imageHeight;
        // Reset the image from the recycled view
        imgPhoto.setImageResource(0);
        // Ask for the photo to be added to the imageview based on the photo url
        // Background: Send a network request to the url, download the image bytes, convert into bitmap, resizing the image, insert bitmap into the imageview
        Picasso.with(getContext()).load(photo.imageUrl).into(imgPhoto);

        // Return the view for that data item
        return convertView;
    }

    // getView method (int position)
    // Default, takes the model (InstagramPhoto) toString()

}
