package hackaton.interventure.com.interventurehackaton.main.events

import android.support.v17.leanback.widget.ArrayObjectAdapter
import android.support.v17.leanback.widget.HeaderItem
import android.support.v17.leanback.widget.ListRow
import hackaton.interventure.com.interventurehackaton.CardPresenter
import hackaton.interventure.com.interventurehackaton.ItemData
import hackaton.interventure.com.interventurehackaton.R
import hackaton.interventure.com.interventurehackaton.database.AppDatabase
import hackaton.interventure.com.interventurehackaton.main.MainFragment
import hackaton.interventure.com.interventurehackaton.main.MediaRowsFragment

class EventsFragment: MediaRowsFragment() {

    override fun createRows() {
        activity?.let { activity ->
            val rows = AppDatabase.getAppDataBase(activity)?.eventsDao()?.getEvents()

            val presenterSelector = CardPresenter()
            val adapter = ArrayObjectAdapter(presenterSelector)
            rows?.let {
                for (row in it) {
                    val itemData = ItemData(row.id, row.name, row.desc, row.thumb)
                    adapter.add(itemData)
                }
            }
            val headerItem = HeaderItem(
                MainFragment.HEADER_EVENTS_ID, getString(
                    R.string.browse_fragment_header_events
                )
            )
            mRowsAdapter.add(ListRow(headerItem, adapter))
        }
    }
}