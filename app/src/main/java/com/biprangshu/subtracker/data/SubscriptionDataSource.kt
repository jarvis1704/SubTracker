package com.biprangshu.subtracker.data

import com.biprangshu.subtracker.R

data class SubscriptionService(
    val name: String,
    val iconRes: Int,
    val color: Long,
    val category: String // NEW FIELD
)

object SubscriptionDataSource {
    val popularServices = listOf(
        SubscriptionService("Netflix", R.drawable.netflix_logo, 0xFFE50914, "Entertainment"),
        SubscriptionService("Spotify", R.drawable.spotify_logo, 0xFF1DB954, "Music"),
        SubscriptionService("Disney+", R.drawable.disney, 0xFF113CCF, "Entertainment"),
        SubscriptionService("YouTube Premium", R.drawable.youtube, 0xFFFF0000, "Entertainment"),
        SubscriptionService("Discord Nitro", R.drawable.discord_logo, 0xFF5865F2, "Communication"),
        SubscriptionService("Hulu", R.drawable.hulu_logo, 0xFF1CE783, "Entertainment"),
        SubscriptionService("HBO Max", R.drawable.hbo_max, 0xFF9E86FF, "Entertainment"),
        SubscriptionService("Google One", R.drawable.google_one, 0xFF4285F4, "Productivity"),
        SubscriptionService("Microsoft 365", R.drawable.microsoft_365, 0xFFF25022, "Productivity"),
        SubscriptionService("Dropbox", R.drawable.dropbox, 0xFF0061FE, "Productivity"),
        SubscriptionService("Slack", R.drawable.slack, 0xFF4A154B, "Communication"),
        SubscriptionService("Tidal", R.drawable.tidal, 0xFF000000, "Music"),
        SubscriptionService("Audible", R.drawable.audible, 0xFFF79C1F, "Books"),
        SubscriptionService("Canva", R.drawable.canva, 0xFF00C4CC, "Design"),
        SubscriptionService("Evernote", R.drawable.evernote, 0xFF00A82D, "Productivity"),
        SubscriptionService("LinkedIn Premium", R.drawable.linkedin_logo, 0xFF0077B5, "Work"),
        SubscriptionService("Zoom", R.drawable.zoom, 0xFF2D8CFF, "Communication"),
        SubscriptionService("Adobe Creative Cloud", R.drawable.adobe_logo, 0xFFFF0000, "Design"),
        SubscriptionService("NordVPN", R.drawable.nordvpn, 0xFF4687FF, "Security"),
        SubscriptionService("LastPass", R.drawable.lastpass, 0xFFD32D27, "Security"),
        SubscriptionService("ProtonVPN", R.drawable.protonvpn, 0xFF6D4AFF, "Security"),
        SubscriptionService("Skillshare", R.drawable.skill_share, 0xFF00FF84, "Education"),
        SubscriptionService("Coursera", R.drawable.coursera, 0xFF0056D2, "Education"),
        SubscriptionService("Udemy", R.drawable.udemy, 0xFFA435F0, "Education"),
        SubscriptionService("Twitch Turbo", R.drawable.twitch, 0xFF9146FF, "Entertainment"),
        SubscriptionService("Fitbit Premium", R.drawable.fitbit, 0xFF00B0B9, "Health"),
        SubscriptionService("Headspace", R.drawable.headspace, 0xFFF47D31, "Health"),
        SubscriptionService("Calm", R.drawable.calm, 0xFF00A4FF, "Health"),
        SubscriptionService("Strava", R.drawable.strava, 0xFFFC5200, "Health"),
        SubscriptionService("Peloton", R.drawable.peloton, 0xFFDF1C2F, "Health"),
        SubscriptionService("Scribd", R.drawable.scribd, 0xFF1A7B88, "Books"),
        SubscriptionService("Blinkist", R.drawable.blinkist, 0xFF2CE080, "Books"),
        SubscriptionService("Duolingo Plus", R.drawable.duolingo, 0xFF58CC02, "Education"),
        SubscriptionService("MyFitnessPal Premium", R.drawable.nyfitnesspal, 0xFF0066EE, "Health"),
        SubscriptionService("Amazon Prime", R.drawable.amazon_prime, 0xFF00A8E1, "Shopping"),
        SubscriptionService("Apple Music", R.drawable.apple_music, 0xFFFC3C44, "Music")
    )
}