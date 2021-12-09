package com.example.myspacexdemoapp.ui.mappers

import com.example.myspacexdemoapp.ui.launches.LinkInfo
import com.example.spacexdemoapp.GetLaunchQuery
import com.example.spacexdemoapp.GetLaunchesQuery

fun GetLaunchesQuery.Links.toLinksInfo() = LinkInfo(
    badge = mission_patch() ?: "",
    picture = flickr_images()?.first() ?: "",
    pictures = flickr_images()?.takeUnless { it.isNullOrEmpty() } ?: emptyList()
)

fun GetLaunchQuery.Links.toLinksInfo() = with(fragments().linkInfo()) {
    LinkInfo(
        badge = mission_patch() ?: "",
        picture = flickr_images()?.first() ?: "",
        pictures = flickr_images()?.takeUnless { it.isNullOrEmpty() } ?: emptyList(),
        video = video_link() ?: ""
    )
}