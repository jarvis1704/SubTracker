package com.biprangshu.subtracker.data

import com.biprangshu.subtracker.R

data class SubscriptionService(
    val name: String,
    val iconRes: Int,
    val color: Long // Stores ARGB Color as Long (e.g. 0xFFE50914)
)

/**
 * A centralized list of popular services that can be referenced throughout the app.
 */
object SubscriptionDataSource {
    val popularServices = listOf(
        SubscriptionService("Netflix", R.drawable.netflix_logo, 0xFFE50914),
        SubscriptionService("Spotify", R.drawable.spotify_logo, 0xFF1DB954),
        SubscriptionService("Disney+", R.drawable.disney, 0xFF113CCF),
        SubscriptionService("YouTube Premium", R.drawable.youtube, 0xFFFF0000),
        SubscriptionService("Discord Nitro", R.drawable.discord_logo, 0xFF5865F2),
        SubscriptionService("Hulu", R.drawable.hulu_logo, 0xFF1CE783),
        SubscriptionService("HBO Max", R.drawable.hbo_max, 0xFF9E86FF),
        SubscriptionService("Google One", R.drawable.google_one, 0xFF4285F4),
        SubscriptionService("Microsoft 365", R.drawable.microsoft_365, 0xFFF25022),
        SubscriptionService("Dropbox", R.drawable.dropbox, 0xFF0061FE),
        SubscriptionService("Slack", R.drawable.slack, 0xFF4A154B),
        SubscriptionService("Tidal", R.drawable.tidal, 0xFF000000),
        SubscriptionService("Audible", R.drawable.audible, 0xFFF79C1F),
        SubscriptionService("Canva", R.drawable.canva, 0xFF00C4CC),
        SubscriptionService("Evernote", R.drawable.evernote, 0xFF00A82D),
        SubscriptionService("LinkedIn Premium", R.drawable.linkedin_logo, 0xFF0077B5),
        SubscriptionService("Zoom", R.drawable.zoom, 0xFF2D8CFF),
        SubscriptionService("Adobe Creative Cloud", R.drawable.adobe_logo, 0xFFFF0000),
        SubscriptionService("NordVPN", R.drawable.nordvpn, 0xFF4687FF),
        SubscriptionService("LastPass", R.drawable.lastpass, 0xFFD32D27),
        SubscriptionService("ProtonVPN", R.drawable.protonvpn, 0xFF6D4AFF),
        SubscriptionService("Skillshare", R.drawable.skill_share, 0xFF00FF84),
        SubscriptionService("Coursera", R.drawable.coursera, 0xFF0056D2),
        SubscriptionService("Udemy", R.drawable.udemy, 0xFFA435F0),
        SubscriptionService("Twitch Turbo", R.drawable.twitch, 0xFF9146FF),
        SubscriptionService("Fitbit Premium", R.drawable.fitbit, 0xFF00B0B9),
        SubscriptionService("Headspace", R.drawable.headspace, 0xFFF47D31),
        SubscriptionService("Calm", R.drawable.calm, 0xFF00A4FF),
        SubscriptionService("Strava", R.drawable.strava, 0xFFFC5200),
        SubscriptionService("Peloton", R.drawable.peloton, 0xFFDF1C2F),
        SubscriptionService("Scribd", R.drawable.scribd, 0xFF1A7B88),
        SubscriptionService("Blinkist", R.drawable.blinkist, 0xFF2CE080),
        SubscriptionService("Duolingo Plus", R.drawable.duolingo, 0xFF58CC02),
        SubscriptionService("MyFitnessPal Premium", R.drawable.nyfitnesspal, 0xFF0066EE),
        SubscriptionService("Amazon Prime", R.drawable.amazon_prime, 0xFF00A8E1),
        SubscriptionService("Apple Music", R.drawable.apple_music, 0xFFFC3C44)
    )
}