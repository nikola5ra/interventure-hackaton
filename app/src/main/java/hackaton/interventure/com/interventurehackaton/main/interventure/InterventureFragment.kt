package hackaton.interventure.com.interventurehackaton.main.interventure

import android.os.Bundle
import android.support.v17.leanback.app.RowsSupportFragment
import android.support.v17.leanback.widget.ArrayObjectAdapter
import android.support.v17.leanback.widget.HeaderItem
import android.support.v17.leanback.widget.ListRow
import android.support.v17.leanback.widget.ListRowPresenter
import hackaton.interventure.com.interventurehackaton.ItemData

class InterventureFragment: RowsSupportFragment() {

    private var mRowsAdapter: ArrayObjectAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        adapter = mRowsAdapter

        val adapter = ArrayObjectAdapter(CustomInterventurePresenter(activity!!))
        adapter.add(ItemData(1, "TRT"))
        val headerItem = HeaderItem(1, "")
        mRowsAdapter?.add(ListRow(headerItem, adapter))
    }
}