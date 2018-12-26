package hackaton.interventure.com.interventurehackaton.main

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.support.v17.leanback.app.BackgroundManager
import android.support.v17.leanback.app.RowsSupportFragment
import android.support.v17.leanback.widget.*
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import hackaton.interventure.com.interventurehackaton.BrowseErrorActivity
import hackaton.interventure.com.interventurehackaton.DetailsActivity
import hackaton.interventure.com.interventurehackaton.ItemData
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
            onItemViewClickedListener = ItemViewClickedListener(it)
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
            mBackgroundUri?.let {
                mHandler.post { updateBackground(it) }
            }
        }
    }

    private fun updateBackground(uri: String) {
        val imageUrl: String = if (uri.startsWith("http")) {
            uri
        } else {
            "http://192.168.0.110:8080/$uri"
        }
        val width = mMetrics.widthPixels
        val height = mMetrics.heightPixels
        Glide.with(activity)
            .load(imageUrl)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(mDefaultBackground)
            .into<SimpleTarget<GlideDrawable>>(
                object : SimpleTarget<GlideDrawable>(width, height) {
                    override fun onResourceReady(
                        resource: GlideDrawable,
                        glideAnimation: GlideAnimation<in GlideDrawable>
                    ) {
                        mBackgroundManager.drawable = resource
                    }
                })
        mBackgroundTimer?.cancel()
    }

    private inner class ItemViewSelectedListener : OnItemViewSelectedListener {
        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder?, item: Any?,
            rowViewHolder: RowPresenter.ViewHolder, row: Row
        ) {
            if (item is ItemData) {
                mBackgroundUri = item.background
                startBackgroundTimer()
            }
        }
    }

    inner class ItemViewClickedListener(val activity: Activity) : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder,
            item: Any,
            rowViewHolder: RowPresenter.ViewHolder,
            row: Row
        ) {

            if (item is ItemData) {
                val intent = Intent(activity, DetailsActivity::class.java)
                intent.putExtra(DetailsActivity.ITEM_DATA, item)

                val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity,
                    (itemViewHolder.view as ImageCardView).mainImageView,
                    DetailsActivity.SHARED_ELEMENT_NAME
                )
                    .toBundle()
                activity.startActivity(intent, bundle)
            } else if (item is String) {
                if (item.contains(activity.getString(R.string.error_fragment))) {
                    val intent = Intent(activity, BrowseErrorActivity::class.java)
                    activity.startActivity(intent)
                } else {
                    Toast.makeText(activity, item, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        private val TAG = MediaRowsFragment::class.java.simpleName
        private const val BACKGROUND_UPDATE_DELAY = 300
    }
}