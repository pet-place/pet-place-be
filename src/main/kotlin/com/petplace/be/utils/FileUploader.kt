package com.petplace.be.utils

import org.springframework.web.multipart.MultipartFile

interface FileUploader {
    val destinationFileUriPrefix: String

    fun upload(sourceFile: MultipartFile, destinationFileName: String): String
    fun delete(targetFileUri: String)
}
