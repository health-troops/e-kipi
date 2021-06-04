package com.bangkit.healthtroops.ekipi.ui.camera

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.ml.FaceModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class CameraActivity : AppCompatActivity() {
    private lateinit var selectImageButton: Button
    private lateinit var makePrediction: Button
    private lateinit var imgView: ImageView
    private lateinit var textView: TextView
    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        selectImageButton = findViewById(R.id.button)
        makePrediction = findViewById(R.id.button2)
        imgView = findViewById(R.id.imageView)
        textView = findViewById(R.id.textView)

        val labels =
            application.assets.open("labels.txt").bufferedReader().use { it.readText() }.split("\n")

        selectImageButton.setOnClickListener {
            Log.d(TAG, "button pressed")
            getContent.launch("image/*")
        }

        makePrediction.setOnClickListener {
//            var byteBuffer = getByteBuffer(resized)
            val byteBuffer =
                ByteBuffer.allocateDirect(3 * 4 * 48 * 48).order(ByteOrder.nativeOrder())
            val pixels = IntArray(48 * 48)
            bitmap.getPixels(pixels, 0, 48, 0, 0, 48, 48)
            for (pixel in pixels) {
                byteBuffer.putInt(pixel)
            }

            val model = FaceModel.newInstance(this)

//            var tBuffer = TensorImage.fromBitmap(resized)
//            var byteBuffer = tBuffer.buffer
            // Creates inputs for reference.
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 48, 48, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            println("================================================================")
            println(outputFeature0.floatArray)
            val max = getMax(outputFeature0.floatArray)
            println(max)

            textView.text = labels[max]

            // Releases model resources if no longer used.
            model.close()
        }
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imgView.setImageURI(uri)
        bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(this.contentResolver, uri))
                .copy(Bitmap.Config.RGBA_F16, true)
        } else {
            MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        }
    }

    private fun getMax(arr: FloatArray): Int {
        var ind = 0
        var min = 0.0f

        for (i in 0..6) {
            println("!!===================================!!")
            println(arr[i])
            if (arr[i] > min) {
                min = arr[i]
                ind = i
            }
        }
        return ind
    }

    companion object {
        private const val TAG = "CameraActivity"
    }
}
