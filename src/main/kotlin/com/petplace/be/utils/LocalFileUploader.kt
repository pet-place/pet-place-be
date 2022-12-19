package com.petplace.be.utils

import com.petplace.be.common.enums.ErrorCode
import com.petplace.be.common.exception.CommonException
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

    override fun delete(targetFileUri: String) {
        val targetFile = File(targetFileUri)
        if (!targetFile.exists()) {
            throw CommonException(ErrorCode.UNKNOWN)
        }
        targetFile.delete()
    }
}
