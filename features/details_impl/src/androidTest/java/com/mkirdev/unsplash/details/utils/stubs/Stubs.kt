package com.mkirdev.unsplash.details.utils.stubs

import com.mkirdev.unsplash.details.models.CoordinatesModel
import com.mkirdev.unsplash.details.models.ExifModel
import com.mkirdev.unsplash.details.models.LocationModel
import com.mkirdev.unsplash.details.models.DetailsModel
import com.mkirdev.unsplash.photo_item.models.PhotoItemModel
import com.mkirdev.unsplash.photo_item.models.UserModel

internal class PhotoDetailsStub {
    companion object {
        fun create() = DetailsModel(
            photoItemModel = PhotoItemStub.create(),
            shareLink = "",
            location = LocationStub.create(),
            tags = "#new #tests",
            exif = ExifStub.create(),
            bio = "Test bio"
        )
    }
}

private class PhotoItemStub {
    companion object {
        fun create() = PhotoItemModel(
            id = "IOsigyhdf",
            imageUrl = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            aspectRatioImage = 1f,
            user = UserModelStub.create(),
            downloadLink = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
            downloads = "100",
            likes = "3.4k",
            isLiked = false
        )
    }
}

private class UserModelStub {
    companion object {
        fun create() = UserModel(
            name = "Spenser Sembrat",
            userName = "@spensersembrat",
            userImage = "https://images.unsplash.com/photo-1738807992185-76ab3a0573c4?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        )
    }
}

private class LocationStub {
    companion object {
        fun create() = LocationModel(
            place = "San Francisco, CA",
            coordinatesModel = CoordinatesStub.create()
        )
    }
}

private class CoordinatesStub {
    companion object {
        fun create() = CoordinatesModel(
            latitude = 100.0,
            longitude = 200.0
        )
    }
}

private class ExifStub {
    companion object {
        fun create() = ExifModel(
            make = "Canon",
            model = "EOS Rebel SL3",
            exposureTime = "1/125",
            aperture = "3.5",
            focalLength = "50.0",
            iso = "100"
        )
    }
}

class ErrorStub {
    companion object {
        fun create() = "Test error"
    }
}

class UpdatedCountStub {
    companion object {
        fun create() = 0
    }
}