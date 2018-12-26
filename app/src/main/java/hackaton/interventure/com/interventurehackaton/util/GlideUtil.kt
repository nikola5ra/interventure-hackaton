package hackaton.interventure.com.interventurehackaton.util

import android.content.Context
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.bumptech.glide.Glide
import hackaton.interventure.com.interventurehackaton.R

object GlideUtil {

    fun loadImage(context: Context, url: String, imageView: ImageView) {
        val imageUrl: String = if (url.startsWith("http")) {
            url
        } else {
            "http://192.168.0.110:8080/$url"
        }
        Glide.with(context)
            .load(imageUrl)
            .centerCrop()
            .error(ContextCompat.getDrawable(context, R.drawable.movie))
            .into(imageView)
    }
}