package hackaton.interventure.com.interventurehackaton.detail.events

import android.content.Intent
import android.support.v17.leanback.widget.ArrayObjectAdapter
import android.support.v17.leanback.widget.HeaderItem
import android.support.v17.leanback.widget.ListRow
import android.support.v17.leanback.widget.ListRowPresenter
import hackaton.interventure.com.interventurehackaton.CardPresenter
import hackaton.interventure.com.interventurehackaton.ItemData
import hackaton.interventure.com.interventurehackaton.R
import hackaton.interventure.com.interventurehackaton.database.AppDatabase
import hackaton.interventure.com.interventurehackaton.detail.DetailsFragment

class EventDetailFragment : DetailsFragment() {

    override fun getDetailActivityIntent(): Intent {
        return Intent(activity, EventDetailActivity::class.java)
    }

    override fun setupRelatedListRow() {
        activity?.let { activity ->
            val list = AppDatabase.getAppDataBase(activity)?.imageDao()?.getImages(mSelected?.id!!)
            val listRowAdapter = ArrayObjectAdapter(CardPresenter())
            list?.let {
                for (image in it) {
                    listRowAdapter.add(ItemData(0, image.name, "", image.image))
                }
            }

            val header = HeaderItem(0, "Photos")
            mAdapter.add(ListRow(header, listRowAdapter))
            mPresenterSelector.addClassPresenter(ListRow::class.java, ListRowPresenter())
        }
    }
}