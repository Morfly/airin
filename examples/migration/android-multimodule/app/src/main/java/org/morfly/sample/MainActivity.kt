package org.morfly.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import org.morfly.sample.details.DetailsFragment
import org.morfly.sample.details.DetailsRoute
import org.morfly.sample.feed.FeedFragment


class MainActivity : AppCompatActivity(R.layout.activity_main), Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            navigateToFeed()
        }
    }

    override fun navigateTo(route: Route) {
        when (route) {
            is DetailsRoute -> navigateToDetails(route)
            else -> error("Invalid route.")
        }
    }

    private fun navigateToFeed() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<FeedFragment>(R.id.content)
        }
    }

    private fun navigateToDetails(detailsRoute: DetailsRoute) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<DetailsFragment>(R.id.content, args = detailsRoute.args)
            addToBackStack(DetailsRoute::class.qualifiedName)
        }
    }
}