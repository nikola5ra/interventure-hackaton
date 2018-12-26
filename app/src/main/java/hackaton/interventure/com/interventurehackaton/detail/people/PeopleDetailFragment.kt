package hackaton.interventure.com.interventurehackaton.detail.people

import android.content.Intent
import android.net.Uri
import android.support.v17.leanback.widget.ArrayObjectAdapter
import android.support.v17.leanback.widget.HeaderItem
import android.support.v17.leanback.widget.ListRow
import android.support.v17.leanback.widget.ListRowPresenter
import hackaton.interventure.com.interventurehackaton.CardPresenter
import hackaton.interventure.com.interventurehackaton.ItemData
import hackaton.interventure.com.interventurehackaton.R
import hackaton.interventure.com.interventurehackaton.database.AppDatabase
import hackaton.interventure.com.interventurehackaton.detail.DetailsFragment

class PeopleDetailFragment : DetailsFragment() {

    override fun getDetailActivityIntent(): Intent {
        return Intent(activity, PeopleDetailActivity::class.java)
    }

    override fun setupRelatedListRow() {

        activity?.let { activity ->
            val list = AppDatabase.getAppDataBase(activity)?.isonDao()?.getIsons(mSelected?.id!!)
            val listRowAdapter = ArrayObjectAdapter(CardPresenter())
            list?.let {
                for (ison in it) {
                    listRowAdapter.add(ItemData(0, ison.image, "", ison.image))
                }
            }

            val header = HeaderItem(0, "Photos")
            mAdapter.add(ListRow(header, listRowAdapter))
            mPresenterSelector.addClassPresenter(ListRow::class.java, ListRowPresenter())
        }
    }
}