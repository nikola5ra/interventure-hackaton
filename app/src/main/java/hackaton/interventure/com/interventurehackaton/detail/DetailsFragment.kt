/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package hackaton.interventure.com.interventurehackaton.detail

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.support.v17.leanback.app.DetailsSupportFragment
import android.support.v17.leanback.app.DetailsSupportFragmentBackgroundController
import android.support.v17.leanback.widget.*
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import hackaton.interventure.com.interventurehackaton.ItemData
import hackaton.interventure.com.interventurehackaton.PlaybackActivity
import hackaton.interventure.com.interventurehackaton.R
import hackaton.interventure.com.interventurehackaton.detail.people.PeopleDetailActivity
import hackaton.interventure.com.interventurehackaton.main.MainActivity
import hackaton.interventure.com.interventurehackaton.util.GlideUtil

/**
 * A wrapper fragment for leanback details screens.
 * It shows a detailed view of video and its metadata plus related videos.
 */
abstract class DetailsFragment : DetailsSupportFragment() {

    internal var mSelected: ItemData? = null

    private lateinit var mDetailsBackground: DetailsSupportFragmentBackgroundController
    internal lateinit var mPresenterSelector: ClassPresenterSelector
    internal lateinit var mAdapter: ArrayObjectAdapter

    abstract fun setupRelatedListRow()

    abstract fun getDetailActivityIntent(): Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate DetailsFragment")
        super.onCreate(savedInstanceState)

        mDetailsBackground = DetailsSupportFragmentBackgroundController(this)

        mSelected = activity?.intent?.getSerializableExtra(DetailsActivity.ITEM_DATA) as ItemData
        if (mSelected != null) {
            mPresenterSelector = ClassPresenterSelector()
            mAdapter = ArrayObjectAdapter(mPresenterSelector)
            setupDetailsOverviewRow()
            setupDetailsOverviewRowPresenter()
            setupRelatedListRow()
            adapter = mAdapter
            initializeBackground(mSelected)
            onItemViewClickedListener = ItemViewClickedListener()
        } else {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initializeBackground(itemData: ItemData?) {
        mDetailsBackground.enableParallax()
        itemData?.background?.let {
            val imageUrl = GlideUtil.getImageUrl(it)
            Glide.with(activity)
                .load(imageUrl)
                .asBitmap()
                .centerCrop()
                .error(R.drawable.default_background)
                .into<SimpleTarget<Bitmap>>(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(
                        bitmap: Bitmap,
                        glideAnimation: GlideAnimation<in Bitmap>
                    ) {
                        Handler().postDelayed({
                            mDetailsBackground.coverBitmap = bitmap
                            mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size())
                        }, 600)
                    }
                })
        }
    }

    private fun setupDetailsOverviewRow() {
        Log.d(TAG, "doInBackground: " + mSelected?.toString())
        val row = DetailsOverviewRow(mSelected)
        activity?.let { activity ->
            row.imageDrawable = ContextCompat.getDrawable(
                activity,
                R.drawable.default_background
            )
            val width = convertDpToPixel(
                activity,
                DETAIL_THUMB_WIDTH
            )
            val height = convertDpToPixel(
                activity,
                DETAIL_THUMB_HEIGHT
            )

            mSelected?.image?.let {
                val imageUrl = GlideUtil.getImageUrl(it)
                Glide.with(activity)
                    .load(imageUrl)
                    .centerCrop()
                    .error(R.drawable.default_background)
                    .into<SimpleTarget<GlideDrawable>>(object : SimpleTarget<GlideDrawable>(width, height) {
                        override fun onResourceReady(
                            resource: GlideDrawable,
                            glideAnimation: GlideAnimation<in GlideDrawable>
                        ) {
                            Log.d(TAG, "details overview card image url ready: $resource")
                            row.imageDrawable = resource
                            mAdapter.notifyArrayItemRangeChanged(0, mAdapter.size())
                        }
                    })
            }
        }

        val actionAdapter = ArrayObjectAdapter()

        actionAdapter.add(
            Action(
                ACTION_WATCH,
                resources.getString(R.string.watch_trailer_1),
                ""
            )
        )
        row.actionsAdapter = actionAdapter

        mAdapter.add(row)
    }

    private fun setupDetailsOverviewRowPresenter() {
        // Set detail background.
        val detailsPresenter = FullWidthDetailsOverviewRowPresenter(DetailsDescriptionPresenter())
        activity?.let {
            detailsPresenter.backgroundColor =
                    ContextCompat.getColor(
                        it,
                        R.color.selected_background
                    )
        }

        // Hook up transition element.
        val sharedElementHelper = FullWidthDetailsOverviewSharedElementHelper()
        sharedElementHelper.setSharedElementEnterTransition(
            activity, DetailsActivity.SHARED_ELEMENT_NAME
        )
        detailsPresenter.setListener(sharedElementHelper)
        detailsPresenter.isParticipatingEntranceTransition = true

        detailsPresenter.onActionClickedListener = OnActionClickedListener { action ->
            if (action.id == ACTION_WATCH) {
                val intent = Intent(activity, PlaybackActivity::class.java)
                intent.putExtra(DetailsActivity.ITEM_DATA, mSelected)
                startActivity(intent)
            } else {
                Toast.makeText(activity, action.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        mPresenterSelector.addClassPresenter(DetailsOverviewRow::class.java, detailsPresenter)
    }

    private fun convertDpToPixel(context: Context, dp: Int): Int {
        val density = context.applicationContext.resources.displayMetrics.density
        return Math.round(dp.toFloat() * density)
    }

    private inner class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder?,
            item: Any?,
            rowViewHolder: RowPresenter.ViewHolder,
            row: Row
        ) {
            if (item is ItemData) {
                Log.d(TAG, "Item: " + item.toString())
                val intent = Intent(activity, PeopleDetailActivity::class.java)
                intent.putExtra(DetailsActivity.ITEM_DATA, item)

                activity?.let { activity ->
                    val bundle =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            activity,
                            (itemViewHolder?.view as ImageCardView).mainImageView,
                            DetailsActivity.SHARED_ELEMENT_NAME
                        )
                            .toBundle()
                    activity.startActivity(intent, bundle)
                }
            }
        }
    }

    companion object {
        private const val TAG = "DetailsFragment"

        private const val ACTION_WATCH = 1L

        private const val DETAIL_THUMB_WIDTH = 274
        private const val DETAIL_THUMB_HEIGHT = 274
    }
}