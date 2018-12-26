package hackaton.interventure.com.interventurehackaton.main.team

import android.support.v17.leanback.widget.ArrayObjectAdapter
import android.support.v17.leanback.widget.HeaderItem
import android.support.v17.leanback.widget.ListRow
import hackaton.interventure.com.interventurehackaton.CardPresenter
import hackaton.interventure.com.interventurehackaton.ItemData
import hackaton.interventure.com.interventurehackaton.R
import hackaton.interventure.com.interventurehackaton.database.AppDatabase
import hackaton.interventure.com.interventurehackaton.main.MainFragment.Companion.HEADER_TEAMS_ID
import hackaton.interventure.com.interventurehackaton.main.MediaRowsFragment

class TeamFragment : MediaRowsFragment() {

    override fun createRows() {
        activity?.let { activity ->
            val rows = AppDatabase.getAppDataBase(activity)?.teamsDao()?.getTeams()

            val presenterSelector = CardPresenter()
            val adapter = ArrayObjectAdapter(presenterSelector)
            rows?.let {
                for (row in it) {
                    val itemData = ItemData(row.id, row.name, row.desc, row.thumb, row.background)
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