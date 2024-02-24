package com.example.gallery20

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gallery20.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var picsAdapter: PicsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (permissions()){
            val picList = loadAllPictures()
            picsAdapter= PicsAdapter(this, picList)
            binding.recycler.adapter = picsAdapter
            binding.recycler.layoutManager = GridLayoutManager(this,3)
        }
        else{
            askForPermission()
        }
    }

    private fun askForPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_MEDIA_IMAGES),100)
    }

    private fun loadAllPictures(): List<PicModel> {
        var tempList = mutableListOf<PicModel>()

        // uri for whole images as one
        val uri = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            }
            else -> {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }
        }

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        contentResolver.query(uri, projection, null, null, sortOrder)
            .use { cursor ->
                cursor?.let {
                    while (cursor.moveToNext()) {
                        val picId =
                            cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                        val dispName =
                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))

                        // uri for each individual image based on id
                        val uri =
                            ContentUris.withAppendedId(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                picId
                            )
                        val model = PicModel(picId, dispName, uri)
                        tempList.add(model)
                    }
                }
            }
        return tempList
    }


    private fun permissions(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)==PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 100){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                loadAllPictures()
            }
        }
        else{
            askForPermission()
        }
    }
}