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

package hackaton.interventure.com.interventurehackaton.main

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.support.v17.leanback.app.BackgroundManager
import android.support.v17.leanback.app.BrowseSupportFragment
import android.support.v17.leanback.widget.ArrayObjectAdapter
import android.support.v17.leanback.widget.HeaderItem
import android.support.v17.leanback.widget.ListRowPresenter
import android.support.v17.leanback.widget.PageRow
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import hackaton.interventure.com.interventurehackaton.R
import java.util.*

/**
 * Loads a grid of cards with movies to browse.
 */
class MainFragment : BrowseSupportFragment() {

    private lateinit var mRowsAdapter: ArrayObjectAdapter
    private val mHandler = Handler()
    private lateinit var mBackgroundManager: BackgroundManager
    private var mDefaultBackground: Drawable? = null
    private lateinit var mMetrics: DisplayMetrics
    private var mBackgroundTimer: Timer? = null
    private var mBackgroundUri: String? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate")
        super.onActivityCreated(savedInstanceState)

        prepareBackgroundManager()
        setupUIElements()
        loadRows()
        setupEventListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: " + mBackgroundTimer?.toString())
        mBackgroundTimer?.cancel()
    }

    private fun prepareBackgroundManager() {
        mBackgroundManager = BackgroundManager.getInstance(activity)
        mBackgroundManager.attach(activity?.window)

        mainFragmentRegistry.registerFragment(
            PageRow::class.java,
            PageRowFragmentFactory(mBackgroundManager, activity!!)
        )
    }

    private fun setupUIElements() {
        title = getString(R.string.browse_title)
        // over title
        headersState = BrowseSupportFragment.HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true

        activity?.let {
            // set fastLane (or headers) background color
            brandColor = ContextCompat.getColor(
                it,
                R.color.fastlane_background
            )
            // set search icon color
            searchAffordanceColor = ContextCompat.getColor(
                it,
                R.color.search_opaque
            )
        }
    }

    private fun loadRows() {
        mRowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        adapter = mRowsAdapter
        createRows()
        startEntranceTransition()
    }

    private fun createRows() {
        val headerItemInterventure =
            HeaderItem(
                HEADER_INTERVENTURE_ID, getString(
                    R.string.browse_fragment_header_interventure
                )
            )
        val pageRowInterventure = PageRow(headerItemInterventure)
        mRowsAdapter.add(pageRowInterventure)

        val headerItemTeams = HeaderItem(
            HEADER_TEAMS_ID, getString(
                R.string.browse_fragment_header_teams
            )
        )
        val pageRowTeams = PageRow(headerItemTeams)
        mRowsAdapter.add(pageRowTeams)

        val headerItemPeople = HeaderItem(
            HEADER_PEOPLE_ID, getString(
                R.string.browse_fragment_header_people
            )
        )
        val pageRowPeople = PageRow(headerItemPeople)
        mRowsAdapter.add(pageRowPeople)

        val headerItemEvents = HeaderItem(
            HEADER_EVENTS_ID, getString(
                R.string.browse_fragment_header_events
            )
        )
        val pageRowEvents = PageRow(headerItemEvents)
        mRowsAdapter.add(pageRowEvents)

        val videoItemEvents = HeaderItem(
            HEADER_VIDEOS_ID,"Videos"
        )
        val pageRowVideos = PageRow(videoItemEvents)
        mRowsAdapter.add(pageRowVideos)
    }

    private fun setupEventListeners() {
//        setOnSearchClickedListener {
//            Toast.makeText(activity, "Implement your own in-app search", Toast.LENGTH_LONG)
//                .show()
//        }
    }

    private fun updateBackground(uri: String?) {
        val width = mMetrics.widthPixels
        val height = mMetrics.heightPixels
        Glide.with(activity)
            .load(uri)
            .centerCrop()
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


    companion object {
        private const val TAG = "MainFragment"

        const val HEADER_INTERVENTURE_ID = 1L
        const val HEADER_TEAMS_ID = 2L
        const val HEADER_PEOPLE_ID = 3L
        const val HEADER_EVENTS_ID = 4L
        const val HEADER_VIDEOS_ID = 5L
    }
}
