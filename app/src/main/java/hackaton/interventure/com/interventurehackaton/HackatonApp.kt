package hackaton.interventure.com.interventurehackaton

import android.app.Application
import android.content.Context
import hackaton.interventure.com.interventurehackaton.database.AppDatabase
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class HackatonApp: Application() {

    override fun onCreate() {
        super.onCreate()
        thread(start = true){
            downloadDatabase(this)
        }
    }

    private fun downloadDatabase(context: Context) {
        val dbPath = context.getDatabasePath(AppDatabase.DATABASE_NAME)
        dbPath.parentFile.mkdirs()

        val inputStream: InputStream?
        val outputStream: OutputStream?
        val connection: HttpURLConnection?
        try {
            val url = URL("http://192.168.0.110:8080/media.db")
            connection = url.openConnection() as HttpURLConnection
            connection.connect()

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                throw RuntimeException("Network error, response code:" + connection.responseCode)
            }

            // download the file
            inputStream = connection.inputStream
            outputStream = FileOutputStream(context.getDatabasePath(AppDatabase.DATABASE_NAME).path)

            inputStream.copyTo(outputStream)
            inputStream.close()

            outputStream.flush()
            outputStream.close()
        } catch (e: Exception) {
            throw RuntimeException("The ${AppDatabase.DATABASE_NAME} database couldn't be installed.", e)
        }
    }
}