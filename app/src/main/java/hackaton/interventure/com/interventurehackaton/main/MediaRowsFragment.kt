package hackaton.interventure.com.interventurehackaton.main

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.support.v17.leanback.app.BackgroundManager
import android.support.v17.leanback.app.RowsSupportFragment
import android.support.v17.leanback.widget.*
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import hackaton.interventure.com.interventurehackaton.R
import java.util.*

/**
 * Page fragment embeds a rows fragment.
 */
abstract class MediaRowsFragment : RowsSupportFragment() {

    internal val mRowsAdapter: ArrayObjectAdapter = ArrayObjectAdapter(ListRowPresenter())
    private val mHandler = Handler()
    private lateinit var mBackgroundManager: BackgroundManager
    private var mBackgroundUri: String? = null
    private var mBackgroundTimer: Timer? = null
    private var mDefaultBackground: Drawable? = null
    private lateinit var mMetrics: DisplayMetrics

    abstract fun createRows()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createRows()
        mainFragmentAdapter.fragmentHost.notifyDataReady(mainFragmentAdapter)
        mBackgroundManager = BackgroundManager.getInstance(activity)
        mMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(mMetrics)

        activity?.let {
            mDefaultBackground = ContextCompat.getDrawable(it, R.drawable.default_background)
            onItemViewSelectedListener = ItemViewSelectedListener()
//            onItemViewClickedListener = ItemViewClickedListener(it, this.getDetailActivityIntent())
        }
        adapter = mRowsAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: " + mBackgroundTimer?.toString())
        mBackgroundTimer?.cancel()
    }

    private fun startBackgroundTimer() {
        mBackgroundTimer?.cancel()
        mBackgroundTimer = Timer()
        mBackgroundTimer?.schedule(UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY.toLong())
    }

    private inner class UpdateBackgroundTask : TimerTask() {

        override fun run() {
            mHandler.post { updateBackground(mBackgroundUri) }
        }
    }

    private fun updateBackground(uri: String?) {
        val width = mMetrics.widthPixels
        val height = mMetrics.heightPixels
        Glide.with(activity)
                .load(uri)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(mDefaultBackground)
                .into<SimpleTarget<GlideDrawable>>(
                        object : SimpleTarget<GlideDrawable>(width, height) {
                            override fun onResourceReady(resource: GlideDrawable,
                                                         glideAnimation: GlideAnimation<in GlideDrawable>) {
                                mBackgroundManager.drawable = resource
                            }
                        })
        mBackgroundTimer?.cancel()
    }

    private inner class ItemViewSelectedListener : OnItemViewSelectedListener {
        override fun onItemSelected(itemViewHolder: Presenter.ViewHolder?, item: Any?,
                                    rowViewHolder: RowPresenter.ViewHolder, row: Row) {
//            if (item is Media) {
//                mBackgroundUri = item.image
//                startBackgroundTimer()
//            }
        }
    }

    companion object {
        private val TAG = MediaRowsFragment::class.java.simpleName
        private const val BACKGROUND_UPDATE_DELAY = 300
    }
}