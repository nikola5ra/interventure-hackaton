package hackaton.interventure.com.interventurehackaton.main.team

import android.content.Intent
import android.support.v17.leanback.widget.ArrayObjectAdapter
import android.support.v17.leanback.widget.HeaderItem
import android.support.v17.leanback.widget.ListRow
import hackaton.interventure.com.interventurehackaton.CardPresenter
import hackaton.interventure.com.interventurehackaton.ItemData
import hackaton.interventure.com.interventurehackaton.R
import hackaton.interventure.com.interventurehackaton.database.AppDatabase
import hackaton.interventure.com.interventurehackaton.detail.team.TeamDetailActivity
import hackaton.interventure.com.interventurehackaton.main.MainFragment.Companion.HEADER_TEAMS_ID
import hackaton.interventure.com.interventurehackaton.main.MediaRowsFragment

class VideoFragment : MediaRowsFragment() {

    override fun getDetailActivityIntent(): Intent {
        return Intent(activity, TeamDetailActivity::class.java)
    }

    override fun createRows() {
        activity?.let { activity ->
            val rows = AppDatabase.getAppDataBase(activity)?.videoDao()?.getVideos()

            val presenterSelector = CardPresenter()
            val adapter = ArrayObjectAdapter(presenterSelector)
            rows?.let {
                for (row in it) {
                    val itemData = ItemData(row.id, row.name, row.desc, row.thumb, row.thumb)
                    adapter.add(itemData)
                }
            }
            val headerItem = HeaderItem(
                HEADER_TEAMS_ID, getString(
                    R.string.browse_fragment_header_teams
                )
            )
            mRowsAdapter.add(ListRow(headerItem, adapter))
        }
    }
}