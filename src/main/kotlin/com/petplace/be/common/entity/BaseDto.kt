package com.petplace.be.common.entity

import com.petplace.be.common.S3Bucket

object BaseDto {
    fun getProfileUrl(profileImageUrl: String?): String? {
        if (!profileImageUrl.isNullOrEmpty()) {
            return S3Bucket.placePrefix+profileImageUrl
        }
        return null
    }
}