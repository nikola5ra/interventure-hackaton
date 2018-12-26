package hackaton.interventure.com.interventurehackaton.main.interventure

import android.content.Context
import android.support.v17.leanback.widget.Presenter
import android.view.LayoutInflater
import android.view.ViewGroup
import hackaton.interventure.com.interventurehackaton.R

class CustomInterventurePresenter(val context: Context) : Presenter() {

    override fun onCreateViewHolder(viewGroup: ViewGroup?): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.custom_interventure_presenter, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder?, p1: Any?) {

    }

    override fun onUnbindViewHolder(p0: ViewHolder?) {
    }
}