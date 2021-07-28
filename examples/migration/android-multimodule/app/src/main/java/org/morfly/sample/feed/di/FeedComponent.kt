package org.morfly.sample.feed.di

import dagger.Component
import org.morfly.sample.AppComponent
import org.morfly.sample.feed.FeedFragment
import org.morfly.sample.feed.FeedViewModel


@FeedScoped
@Component(dependencies = [AppComponent::class])
interface FeedComponent {

    val feedViewModel: FeedViewModel

    fun inject(fragment: FeedFragment)
}