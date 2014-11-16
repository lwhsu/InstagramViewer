package org.lwhsu.android.instagramviewer;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class PhotoActivity extends Activity {

    public static final String CLIENT_ID = "2a1576e7d2b049119c5315148398d774";

    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        fetchPopularPhotos();
    }

    private void fetchPopularPhotos() {

        photos = new ArrayList<InstagramPhoto>();

        // Create adapter bind it to the data in arraylist
        aPhotos = new InstagramPhotosAdapter(this, photos);
        // Populate the data into the listview
        final ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        // set the adapter to the listview (population of items)
        lvPhotos.setAdapter(aPhotos);

        // https://api.instagram.com/v1/media/popular?client_id=clientid<>
        // { "data" => [x] => "images" => "standard_resolution" => url }

        // Setup popular url endpoint
        final String popularUrl = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;

        // Create the network client
        final AsyncHttpClient client = new AsyncHttpClient();

        // Trigger the network request
        client.get(popularUrl, new JsonHttpResponseHandler() {
            // define success and failure callbacks
            // Handle the successful response (popular photos JSON)
            @Override
            public void onSuccess(final int statusCode, final Header[] headers, final JSONObject response) {
                // fired once the successful response back
                // response is == popular photos json
                // url, height, username, caption
                // { "data" => [x] => "images" => "standard_resolution" => url }
                // { "data" => [x] => "images" => "standard_resolution" => height }
                // { "data" => [x] => "user" => "username" }
                // { "data" => [x] => "caption" => "text" }
                JSONArray photosJSON = null;
                try {
                    photos.clear();
                    photosJSON = response.getJSONArray("data");
                    for (int i = 0; i < photosJSON.length(); ++i) {
                        final JSONObject photoJSON = photosJSON.getJSONObject(i);
                        final InstagramPhoto photo = new InstagramPhoto();
                        photo.username = photoJSON.getJSONObject("user").getString("username");
                        photo.caption = photoJSON.getJSONObject("caption").getString("text");
                        photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
                        photos.add(photo);
                    }
                    aPhotos.notifyDataSetChanged();
                } catch (final JSONException e) {
                    // Fire if things fail, json parsing is invalid
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(final int statusCode, final Header[] headers, final String responseString,
                    final Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
