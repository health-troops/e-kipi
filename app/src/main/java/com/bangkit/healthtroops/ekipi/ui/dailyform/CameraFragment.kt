package com.bangkit.healthtroops.ekipi.ui.dailyform

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bangkit.healthtroops.ekipi.R
import com.bangkit.healthtroops.ekipi.ml.FaceModel
import com.bangkit.healthtroops.ekipi.ui.dailyform.viewmodel.DailyFormViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder


/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class CameraFragment : Fragment() {
    private lateinit var selectImageButton: Button
    private lateinit var makePrediction: Button
    private lateinit var imgView: ImageView
    private lateinit var textView: TextView
    private var bitmap: Bitmap? = null

    private val viewModel by activityViewModels<DailyFormViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_camera, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selectImageButton = view.findViewById(R.id.button)
        makePrediction = view.findViewById(R.id.button2)
        imgView = view.findViewById(R.id.imageView)
        textView = view.findViewById(R.id.textView)

        val labels = requireActivity().application.assets.open("labels.txt").bufferedReader()
            .use { it.readText() }.split("\n")

        selectImageButton.setOnClickListener {
            Log.d(TAG, "button pressed")
            getContent.launch("image/*")
        }

        makePrediction.setOnClickListener {
            val byteBuffer =
                ByteBuffer.allocateDirect(3 * 4 * 48 * 48).order(ByteOrder.nativeOrder())
            val pixels = IntArray(48 * 48)

            if (bitmap != null) {
                bitmap!!.getPixels(pixels, 0, 48, 0, 0, 48, 48)
                for (pixel in pixels) {
                    byteBuffer.putInt(pixel)
                }

                val model = FaceModel.newInstance(requireContext())

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

                // Releases model resources if no longer used.
                model.close()

                textView.text = labels[max]
                viewModel.moodPrediction.postValue(labels[max])
                requireActivity().supportFragmentManager.popBackStack()
            } else {
                Toast.makeText(context, "Select image first!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            imgView.setImageURI(uri)
            bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(requireContext().contentResolver, uri)
                ).copy(Bitmap.Config.RGBA_F16, true)
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
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
