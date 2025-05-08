package com.mkirdev.unsplash.details.preview

import com.mkirdev.unsplash.details.models.CoordinatesModel
import com.mkirdev.unsplash.details.models.ExifModel
import com.mkirdev.unsplash.details.models.LocationModel
import com.mkirdev.unsplash.details.models.PhotoDetailsModel
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import com.mkirdev.unsplash.photo_item.models.UserModel

fun createPhotoDetailsPreview() = PhotoDetailsModel(
    photoItemModel = createPhotoItemPreview(),
    shareLink = createShareLink(),
    location = createLocationPreview(),
    tags = createTagsPreview(),
    exif = createExifPreview(),
    bio = createBioPreview()
)

private fun createPhotoItemPreview() = PhotoItemModel(
    id = "IOsig125yhdf",
    imageUrl = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    aspectRatioImage = 0.5f,
    user = UserModel(
        name = "Spenser Sembrat",
        userName = "spensersembrat",
        userImage = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    ),
    downloadLink = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
    downloads = "100",
    likes = "3.4k",
    isLiked = false
)

private fun createShareLink() = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
private fun createLocationPreview() = LocationModel(
    place = "San Francisco, CA",
    coordinatesModel = createCoordinatesPreview()
)

private fun createTagsPreview() = "#city #new"

private fun createExifPreview() = ExifModel(
    make = "Canon",
    model = "EOS Rebel SL3",
    exposureTime = "1/125",
    aperture = "3.5",
    focalLength = "50.0",
    iso = "100"
)
private fun createBioPreview() = "Dreamer, creator."

private fun createCoordinatesPreview() = CoordinatesModel(
    latitude = 100.0,
    longitude = 200.0
)
