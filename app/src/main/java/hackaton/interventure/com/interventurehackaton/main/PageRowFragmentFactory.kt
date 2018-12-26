package hackaton.interventure.com.interventurehackaton.main

import android.content.Context
import android.support.v17.leanback.app.BackgroundManager
import android.support.v17.leanback.app.BrowseSupportFragment
import android.support.v17.leanback.widget.Row
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import hackaton.interventure.com.interventurehackaton.R
import hackaton.interventure.com.interventurehackaton.main.events.EventsFragment
import hackaton.interventure.com.interventurehackaton.main.interventure.InterventureFragment
import hackaton.interventure.com.interventurehackaton.main.people.PeopleFragment
import hackaton.interventure.com.interventurehackaton.main.team.TeamFragment
import hackaton.interventure.com.interventurehackaton.main.team.VideoFragment

internal class PageRowFragmentFactory internal constructor(
    private val mBackgroundManager: BackgroundManager,
    private val context: Context
) : BrowseSupportFragment.FragmentFactory<Fragment>() {

    override fun createFragment(rowObj: Any?): Fragment {
        val row = rowObj as Row
        if (row.headerItem.id == MainFragment.HEADER_INTERVENTURE_ID) {
            mBackgroundManager.drawable = ContextCompat.getDrawable(context, R.drawable.interventure_background)
        } else {
            mBackgroundManager.drawable = null
        }
        return when {
            row.headerItem.id == MainFragment.HEADER_INTERVENTURE_ID -> InterventureFragment()
            row.headerItem.id == MainFragment.HEADER_TEAMS_ID -> TeamFragment()
            row.headerItem.id == MainFragment.HEADER_PEOPLE_ID -> PeopleFragment()
            row.headerItem.id == MainFragment.HEADER_EVENTS_ID -> EventsFragment()
            row.headerItem.id == MainFragment.HEADER_VIDEOS_ID -> VideoFragment()
            else -> throw IllegalArgumentException(String.format("Invalid row %s", rowObj))
        }
    }
}