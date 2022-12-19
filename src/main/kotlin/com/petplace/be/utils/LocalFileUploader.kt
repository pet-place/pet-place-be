package com.petplace.be.utils

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Component
@Profile(value = ["default"])
class LocalFileUploader: FileUploader {
    override val destinationFileUriPrefix: String = "/Users/mc/Desktop/study/pet-place-be/src/main/resources/upload/"

    override fun upload(sourceFile: MultipartFile, destinationFileName: String): String {
        val ext = sourceFile.originalFilename!!.split(".")[1]
        val destinationFile = File("${destinationFileUriPrefix}${File.separator}${destinationFileName}.${ext}")
        sourceFile.transferTo(destinationFile)
        return destinationFile.absolutePath
    }
}
