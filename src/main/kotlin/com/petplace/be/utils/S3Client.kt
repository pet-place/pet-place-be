package com.petplace.be.utils

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.*
import com.petplace.be.common.enums.ErrorCode
import com.petplace.be.common.exception.CommonException
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*


@Component
@Profile(value = ["dev"])
class S3Client(
    private val amazonS3Client: AmazonS3Client
) : FileUploader{

    @Value("\${cloud.aws.s3.bucket}")
    override lateinit var destinationFileUriPrefix: String

    override fun upload(sourceFile: MultipartFile, destinationFileName: String): String {
        val filename = sourceFile.originalFilename!!
        val ext = filename.substring(filename.lastIndexOf(".") + 1)
        val size = sourceFile.size
        val objectMetaData = ObjectMetadata()
        objectMetaData.contentType = sourceFile.contentType
        objectMetaData.contentLength = size

        amazonS3Client.putObject(
            PutObjectRequest(destinationFileUriPrefix,
                "$destinationFileName.$ext", sourceFile.inputStream, objectMetaData)
                .withCannedAcl(CannedAccessControlList.PublicRead))

        return "$destinationFileName.$ext"
    }

    override fun delete(targetFileUri: String) {
        val targetFile = File(targetFileUri)
        if (!targetFile.exists()) {
            throw CommonException(ErrorCode.UNKNOWN)
        }
        targetFile.delete()
    }
}