package com.example.android.devbyteviewer.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.devbyteviewer.database.getDatabase
import com.example.android.devbyteviewer.repository.VideosRepository
import retrofit2.HttpException
import timber.log.Timber

class RefreshDataWorker(context : Context, params : WorkerParameters) : CoroutineWorker(context, params) {
    companion object {
        // unique name to identify this particular worker
        const val WORK_NAME = "com.example.android.devbyteviewer.work.RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = VideosRepository(database)

        try{
            repository.refreshVideos()
            Timber.d("Work request for sync is run")
        }catch (e : HttpException){
            return Result.retry()
        }
        return Result.success()
    }
}