package com.mkirdev.unsplash.workers

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresPermission
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.mkirdev.unsplash.di.DaggerProvider
import com.mkirdev.unsplash.services.PhotoDownloadService
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.rx3.await
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Locale

private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss"
private const val PHOTO = "Photo"
private const val WORKER_TAG = "PHOTO.DOWNLOAD.WORKER"
private const val WORKER_NAME = "DOWNLOADED PHOTO"
private const val DATA = "DATA"
private const val EMPTY_URL = ""

class PhotoDownloadWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val downloadApi = DaggerProvider.appComponent.downloadApi
    private val dispatcher = DaggerProvider.appComponent.coroutineDispatcher

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {
        return withContext(dispatcher) {
            try {
                val name = StringBuilder()
                    .append("$PHOTO ")
                    .append(
                        SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                            .format(System.currentTimeMillis())
                    )

                val url = inputData.getString(DATA) ?: return@withContext Result.failure()

                val responseBody = downloadApi
                    .download(url)
                    .subscribeOn(Schedulers.io())
                    .await()

                saveToMediaStore(responseBody.byteStream(), context, fileName = name.toString())

                val intent = Intent(
                    applicationContext,
                    PhotoDownloadService::class.java
                )

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    applicationContext.startForegroundService(intent)
                } else {
                    applicationContext.startService(intent)
                }

                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        }
    }

    private fun saveToMediaStore(
        input: InputStream,
        context: Context,
        fileName: String
    ) {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Unsplash")
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val uri = context.contentResolver
            .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            ?: return

        context.contentResolver.openOutputStream(uri)?.use { output ->
            input.copyTo(output)
        }

        values.clear()
        values.put(MediaStore.Images.Media.IS_PENDING, 0)
        context.contentResolver.update(uri, values, null, null)
    }

    companion object {
        private val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        private fun createWorkRequest(photoUrl: String): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<PhotoDownloadWorker>()
                .setInputData(workDataOf(DATA to photoUrl))
                .addTag(WORKER_TAG)
                .setConstraints(constraints)
                .build()
        }

        internal fun enqueueUniqueWork(workManager: WorkManager, photoUrl: String?) {
            val workRequest = createWorkRequest(photoUrl ?: EMPTY_URL)
            workManager.enqueueUniqueWork(WORKER_NAME, ExistingWorkPolicy.REPLACE, workRequest)
        }
    }
}