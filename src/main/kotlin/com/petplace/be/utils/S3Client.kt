package com.petplace.be.utils

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*


@Component
class S3Client(
    private val amazonS3Client: AmazonS3Client
) {
    @Value("\${cloud.aws.s3.bucket}")
    private lateinit var bucket: String

    fun upload(multipartFile: MultipartFile, key: String) {
        val size = multipartFile.size
        val objectMetaData = ObjectMetadata()
        objectMetaData.contentType = multipartFile.contentType
        objectMetaData.contentLength = size

        amazonS3Client.putObject(
            PutObjectRequest(bucket, key, multipartFile.inputStream, objectMetaData)
            .withCannedAcl(CannedAccessControlList.PublicRead))
    }


    fun getUrl(key: String): String{
        return amazonS3Client.getUrl(bucket, key).toString()
    }
}