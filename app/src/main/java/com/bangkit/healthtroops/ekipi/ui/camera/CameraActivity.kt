package com.bangkit.healthtroops.ekipi.ui.camera

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.ml.FaceModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class CameraActivity : AppCompatActivity() {
    private lateinit var select_image_button : Button
    private lateinit var make_prediction : Button
    private lateinit var img_view : ImageView
    private lateinit var text_view : TextView
    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        select_image_button = findViewById(R.id.button)
        make_prediction = findViewById(R.id.button2)
        img_view = findViewById(R.id.imageView)
        text_view = findViewById(R.id.textView)

        val labels = application.assets.open("labels.txt").bufferedReader().use { it.readText() }.split("\n")

        select_image_button.setOnClickListener(View.OnClickListener {
            Log.d("mssg", "button pressed")
            var intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"

            startActivityForResult(intent, 100)
        })

        make_prediction.setOnClickListener(View.OnClickListener {
            var resized = Bitmap.createScaledBitmap(bitmap, 48, 48, true)
            //var byteBuffer = getByteBuffer(resized)
            var byteBuffer = ByteBuffer.allocateDirect(3*4 * 48*48).order(ByteOrder.nativeOrder())
            val pixels = IntArray(48*48)
            bitmap.getPixels(pixels, 0, 48, 0, 0, 48,48)
            for (pixel in pixels) {
                byteBuffer.putInt(pixel as Int)
            }


            val model = FaceModel.newInstance(this)

//            var tbuffer = TensorImage.fromBitmap(resized)
//            var byteBuffer = tbuffer.buffer
//// Creates inputs for reference.
//// Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1,48, 48, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

// Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            println("================================================================")
            println(outputFeature0.floatArray)
            var max = getMax(outputFeature0.floatArray)
            println(max)

            text_view.text = labels[max]

// Releases model resources if no longer used.
            model.close()
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        img_view.setImageURI(data?.data)

        var uri : Uri ?= data?.data
        bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)

    }

    fun getMax(arr:FloatArray) : Int{
        var ind = 0;
        var min = 0.0f;

        for(i in 0..6)
        {   println("!!===================================!!")
            println(arr[i])
            if(arr[i] > min)
            {
                min = arr[i]
                ind = i;
            }
        }
        return ind
    }

}