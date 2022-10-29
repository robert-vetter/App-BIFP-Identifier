package com.imagerecognizer.myapplication;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.imagerecognizer.myapplication.ml.InsectModel;
import com.imagerecognizer.myapplication.ml.PlantModel;

import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.label.Category;

import java.io.IOException;
import java.util.List;

public class PlantIdentifier extends AppCompatActivity {

    private Button btLoadImage;
    private Button btSearchGoogle;
    private TextView tvResult;
    private ImageView ivAddImage;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private ActivityResultLauncher<String> mGetContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_identifier);

        ivAddImage = findViewById(R.id.iv_add_image);
        tvResult = findViewById(R.id.tv_result);
        btLoadImage = findViewById(R.id.bt_load_image);
        btSearchGoogle = findViewById(R.id.bt_search_google);




        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                Bitmap imageBitmap = null;
                try{
                    imageBitmap = UriToBitmap(result);
                } catch (IOException e){
                    e.printStackTrace();
                }
                ivAddImage.setImageBitmap(imageBitmap);
                outputGenerator(imageBitmap);
                Log.d("TAG_URI", "" + result);
            }
        });

        btLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });

        btSearchGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + tvResult.getText().toString()));
                startActivity(intent);

            }
        });
    }

    private void outputGenerator(Bitmap imageBitmap){
        try{
            PlantModel model = PlantModel.newInstance(PlantIdentifier.this);

            // Creates inputs for reference.
            TensorImage image = TensorImage.fromBitmap(imageBitmap);

            // Runs model inference and gets result.
            PlantModel.Outputs outputs = model.process(image);
            List<Category> probability = outputs.getProbabilityAsCategoryList();

            int index = 0;
            float max = probability.get(0).getScore();

            for (int i = 0; i < probability.size(); i++){
                if (max < probability.get(i).getScore()){
                    max=probability.get(i).getScore();
                    index = i;
                }
            }

            Category output = probability.get(index);
            tvResult.setText(output.getLabel());

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }

    }

    private  Bitmap UriToBitmap(Uri result) throws IOException {
        return MediaStore.Images.Media.getBitmap(this.getContentResolver(), result);
    }
}