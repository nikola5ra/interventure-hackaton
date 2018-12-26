package hackaton.interventure.com.interventurehackaton.detail.events

import android.os.Bundle
import hackaton.interventure.com.interventurehackaton.R
import hackaton.interventure.com.interventurehackaton.detail.DetailsActivity

class EventDetailActivity: DetailsActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
    }
}