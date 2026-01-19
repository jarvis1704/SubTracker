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
        SubscriptionService("Discord Nitro", iconRes = R.drawable.discord_logo),
        SubscriptionService("Hulu", R.drawable.hulu_logo),
        SubscriptionService("HBO Max", R.drawable.hbo_max),
        SubscriptionService("Google One", R.drawable.google_one),
        SubscriptionService("Microsoft 365", R.drawable.microsoft_365),
        SubscriptionService("Dropbox", R.drawable.dropbox),
        SubscriptionService("Slack", R.drawable.slack),
        SubscriptionService("Tidal", R.drawable.tidal),
        SubscriptionService("Audible", R.drawable.audible),
        SubscriptionService("Canva", R.drawable.canva),
        SubscriptionService("Evernote", R.drawable.evernote),
        SubscriptionService("LinkedIn Premium", R.drawable.linkedin_logo),
        SubscriptionService("Zoom", R.drawable.zoom),
        SubscriptionService("Adobe Creative Cloud", R.drawable.adobe_logo),
        SubscriptionService("NordVPN", R.drawable.nordvpn),
        SubscriptionService("LastPass", R.drawable.lastpass),
        SubscriptionService("ProtonVPN", R.drawable.protonvpn),
        SubscriptionService("Skillshare", R.drawable.skill_share),
        SubscriptionService("Coursera", R.drawable.coursera),
        SubscriptionService("Udemy", R.drawable.udemy),
        SubscriptionService("Twitch Turbo", R.drawable.twitch),
        SubscriptionService("Fitbit Premium", R.drawable.fitbit),
        SubscriptionService("Headspace", R.drawable.headspace),
        SubscriptionService("Calm", R.drawable.calm),
        SubscriptionService("Strava", R.drawable.strava),
        SubscriptionService("Peloton", R.drawable.peloton),
        SubscriptionService("Scribd", R.drawable.scribd),
        SubscriptionService("Blinkist", R.drawable.blinkist),
        SubscriptionService("Duolingo Plus", R.drawable.duolingo),
        SubscriptionService("MyFitnessPal Premium", R.drawable.nyfitnesspal),
        SubscriptionService("Amazon Prime", R.drawable.amazon_prime),
        SubscriptionService("Apple Music", R.drawable.apple_music)
    )
}