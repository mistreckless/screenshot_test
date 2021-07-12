package ru.alfabank.sreenshot_library

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import android.view.View
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

internal object FileBuilder {

    fun buildMultipartFile(activity: Activity, view: View): MultipartBody.Part {
        val bitmap = buildBitmap(view)
        val file = buildFile(activity, bitmap)
        return MultipartBody.Part.createFormData(
            "file", file.name, RequestBody.create(
                MediaType.parse("image/png"), file
            )
        )
    }

    private fun buildBitmap(view: View): Bitmap {
        val resultBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(resultBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) bgDrawable.draw(canvas) else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return resultBitmap
    }

    private fun buildFile(activity: Activity, bitmap: Bitmap): File {
        val path = activity.externalMediaDirs.last().path + "/${UUID.randomUUID()}.png"
        Log.e("File path", path)
        val file = FileOutputStream(path)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, file)
        file.flush()
        file.close()
        return File(path)
    }
}