package com.mindsplash.afterlogin.common.learn

import com.mindsplash.afterlogin.fragments.TypeAsk

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.mindsplash.R
import com.mindsplash.afterlogin.common.HomeActivity
import com.mindsplash.afterlogin.common.HomeActivity.OnBackPressedListener
import com.mindsplash.afterlogin.fragments.home.Home
import com.mindsplash.helper.*
import com.mindsplash.network.model.CommonResponse
import com.mindsplash.network.model.ConceptResponse
import com.mindsplash.network.model.Student
import com.mindsplash.services.ask_ques.AskQuesService
import com.mindsplash.services.ask_ques.AskQuesServiceImpl
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AskActivity : AppCompatActivity(), OnBackPressedListener {
    var backButton: ImageView? = null
    var header_title: TextView? = null
    var galleryView: CircleImageView? = null
    var typeBtn: CircleImageView? = null
    var captureBtn: CircleImageView? = null
    var cameraFinder: PreviewView? = null
    private var mLogo: ImageView? = null
    private val PICK_FROM_GALLERY = 1
    private val REQUEST_GALLERY_PHOTO = 2
    var profileAttachment: File? = null
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService
    private val REQUEST_CODE_PERMISSIONS = 20
    private var progressDialog: Dialog? = null
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_ask)
        progressDialog = AppUtils().getProgressDialog(this)
        galleryView = findViewById(R.id.galleryView)
        cameraFinder = findViewById(R.id.cameraFinder)
        captureBtn = findViewById(R.id.captureBtn)
        typeBtn = findViewById(R.id.typeBtn)
        backButton = findViewById(R.id.backButton)
        header_title = findViewById<View>(R.id.header_title) as TextView
        header_title!!.text = "ASK"
        mLogo = findViewById(R.id.logo)
        mLogo!!.visibility = View.GONE
        backButton!!.setOnClickListener(View.OnClickListener { //                getActivity().onBackPressed();
            doBack()
        })
        galleryView!!.setOnClickListener(View.OnClickListener {
            dispatchGalleryIntent()
        })
        captureBtn!!.setOnClickListener(View.OnClickListener {
            takePhoto()
        })
        typeBtn!!.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, TypeAsk::class.java)
                        startActivity (intent)
        })
        cameraExecutor = Executors.newSingleThreadExecutor()
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {

            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(cameraFinder!!.createSurfaceProvider())
                }

            imageCapture = ImageCapture.Builder().build()

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

            } catch (exc: Exception) {
//                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        // Get a stable reference of the
        // modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        profileAttachment = getOutputDirectory()
        val photoFile = File(
            profileAttachment,
            SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener,
        // which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e("TAG", "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    Log.e("TAG", photoFile.absolutePath)
                    // set the saved uri to the image view
                    profileAttachment = photoFile
                    postDialog(photoFile)


                }
            })
    }

    private fun dispatchGalleryIntent() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                PICK_FROM_GALLERY
            )
        } else {
            val pickPhoto = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PICK_FROM_GALLERY) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val pickPhoto = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO)
            } else Toast.makeText(
                this,
                "Permissions not granted by the user.",
                Toast.LENGTH_SHORT
            ).show()

        } else if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
                cameraExecutor = Executors.newSingleThreadExecutor()
            } else {
                // If permissions are not granted,
                // present a toast to notify the user that
                // the permissions were not granted.
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_GALLERY_PHOTO && resultCode == RESULT_OK) {
            val selectedImage = data!!.data
            var mCompressor = FileCompressor(this)
            try {
                profileAttachment = mCompressor.compressToFile(
                    File(
                        getRealPathFromUri(
                            this,
                            selectedImage!!
                        )
                    )
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
            val filePat: String = profileAttachment!!.absolutePath
            Log.e("filepath", profileAttachment!!.absolutePath)
//            postQiest()
            postDialog(profileAttachment!!)
            //  strImagePath = filePat.substring(filePat.lastIndexOf("/") + 1)
        }

    }

    private fun postQiest(text: String) {
        progressDialog!!.show()
        var appSharedPreference = AppSharedPreference.getMInstance()
        val student: Student = Gson().fromJson<Student>(
            appSharedPreference.studentDetails,
            Student::class.java
        )

        val conceptService: AskQuesService = AskQuesServiceImpl()
        conceptService.postAskQues(
            student.id,
            text,
            profileAttachment,
            object : CallBack() {
                override fun onSuccess(`object`: Any) {
                    progressDialog!!.cancel()
                    if (`object` != null) {
                        val conceptResponse: CommonResponse = `object` as CommonResponse
                        Toast.makeText(
                            applicationContext,
                            conceptResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()

//                        setDefConcepts(conceptResponse.data as ArrayList<Concept?>)
                    }
                }

                override fun onError(`object`: Any) {
                    progressDialog!!.cancel()
                }
            })
    }

    fun getRealPathFromUri(mContext: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf<String>(MediaStore.Images.Media.DATA)
            cursor = mContext.contentResolver.query(contentUri, proj, null, null, null)
            assert(cursor != null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            this,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getOutputDirectory(): File {
        val mediaDir = this.externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else this.filesDir
    }

    fun postDialog(file: File) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.setContentView(R.layout.dialog_ask_ques_img)
        val displayMetrics = DisplayMetrics()

        this.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        val layoutParams = WindowManager.LayoutParams()

        val img = dialog.findViewById<ImageView>(R.id.imagepost)
        img.setImageBitmap(ImageUtil.decodeSampledBitmapFromFile(file, 100, 100))
        val button: MaterialButton = dialog.findViewById(R.id.postbtn)
        button.setOnClickListener {
            dialog.dismiss()
            postQiest("")
        }

        dialog.setCancelable(true)
        layoutParams.copyFrom(dialog.window!!.attributes)
        val dialogWindowWidth = (height * 0.5f).toInt()
        val dialogWindowHeight = (width * 0.5f).toInt()
        layoutParams.width = dialogWindowWidth
        layoutParams.gravity = Gravity.CENTER
        dialog.window!!.attributes = layoutParams
        dialog.show()

    }

    override fun doBack() {

    }
}