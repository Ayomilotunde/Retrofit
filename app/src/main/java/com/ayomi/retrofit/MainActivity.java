package com.ayomi.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textviewresult;

    private JsonPlaceHolderAPI jsonPlaceHolderAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textviewresult = findViewById(R.id.text_result_viewer);

        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);

        // getPost();

        getComment();
    }

    private void getComment() {
        Call<List<Comments>> call = jsonPlaceHolderAPI.getComments("posts/3/comments");

        call.enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(Call<List<Comments>> call, Response<List<Comments>> response) {
                if (!response.isSuccessful()) {
                    textviewresult.setText("Code: " + response.code());
                    return;
                }
                List<Comments> comment = response.body();

                for (Comments comments : comment){
                    String content = "";
                    content += "ID: " + comments.getId() + "\n";
                    content += "Post ID: " + comments.getPostId() + "\n";
                    content += "Name: " + comments.getName() + "\n";
                    content += "Email: " + comments.getEmail() + "\n";
                    content += "Text: " + comments.getText() + "\n\n";

                    textviewresult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comments>> call, Throwable t) {
                textviewresult.setText(t.getMessage());
            }
        });
    }

    private void getPost() {

       // Call<List<Post>> call = jsonPlaceHolderAPI.getPosts(4, "id", "desc");

        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");

        Call<List<Post>> call = jsonPlaceHolderAPI.getPosts(parameters);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    textviewresult.setText("Code: " + response.code());
                    return;
                }

                List<Post> posts = response.body();

                for (Post post : posts){
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "User ID: " + post.getUserId() + "\n";
                    content += "Title: " + post.getTitle() + "\n";
                    content += "Text: " + post.getText() + "\n\n";

                    textviewresult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textviewresult.setText(t.getMessage());
            }
        });
    }
}