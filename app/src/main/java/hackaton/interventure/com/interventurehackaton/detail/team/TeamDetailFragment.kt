package hackaton.interventure.com.interventurehackaton.detail.team

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
import hackaton.interventure.com.interventurehackaton.detail.people.PeopleDetailActivity

class TeamDetailFragment : DetailsFragment() {

    override fun getDetailActivityIntent(): Intent {
        return Intent(activity, PeopleDetailActivity::class.java)
    }

    override fun setupRelatedListRow() {
        val subcategories = arrayOf(getString(R.string.browse_fragment_header_people))
        activity?.let { activity ->
            val list = AppDatabase.getAppDataBase(activity)?.faceDao()?.getFaces(mSelected!!.id)
            val listRowAdapter = ArrayObjectAdapter(CardPresenter())
            list?.let {
                for (face in it) {

                        listRowAdapter.add(ItemData(face.id, face.name, face.desc, face.image))

                }
            }

            val header = HeaderItem(0, subcategories[0])
            mAdapter.add(ListRow(header, listRowAdapter))
            mPresenterSelector.addClassPresenter(ListRow::class.java, ListRowPresenter())
        }
    }
}