package com.petplace.be.utils

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
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

    fun upload(multipartFile: MultipartFile): String {
        val originalName = multipartFile.originalFilename // 파일 이름
        val size = multipartFile.size // 파일 크기

        val objectMetaData = ObjectMetadata()
        objectMetaData.contentType = multipartFile.contentType
        objectMetaData.contentLength = size

        // S3에 업로드
        amazonS3Client.putObject(
            PutObjectRequest(bucket, originalName, multipartFile.inputStream, objectMetaData)
                .withCannedAcl(CannedAccessControlList.PublicRead)
        )

        return amazonS3Client.getUrl(bucket, originalName).toString() // 접근가능한 URL 가져오기
    }
}