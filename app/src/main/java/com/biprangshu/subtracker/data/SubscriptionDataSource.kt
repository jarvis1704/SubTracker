package com.biprangshu.subtracker.data

import com.biprangshu.subtracker.R


data class SubscriptionService(
    val name: String,
    val iconRes: Int
)

/**
 * A centralized list of popular services that can be referenced throughout the app.
 */
object SubscriptionDataSource {
    val popularServices = listOf(
        SubscriptionService("Netflix", R.drawable.netflix_logo),
        SubscriptionService("Spotify", R.drawable.spotify_logo),
        SubscriptionService("Disney+", R.drawable.disney),
        SubscriptionService("YouTube Premium", R.drawable.youtube),
        // Add more services here easily
        // SubscriptionService("Amazon Prime", R.drawable.amazon_prime),
        // SubscriptionService("Apple Music", R.drawable.apple_music)
    )
}