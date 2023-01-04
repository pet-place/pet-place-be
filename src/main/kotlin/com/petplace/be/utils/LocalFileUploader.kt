package com.petplace.be.utils

import com.petplace.be.common.enums.ErrorCode
import com.petplace.be.common.exception.CommonException
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Component
@Profile(value = ["local"])
class LocalFileUploader: FileUploader {
    override val destinationFileUriPrefix: String = "/Users/mc/Desktop/study/pet-place-be/src/main/resources/upload/"

    // TODO: 파일 이름에 아이디를 넣는 것에 대한 협의 필요(폴더 생성 어려움) -> S3 업로드 로직에서 폴더 생성 문제 없다면 문제 없음
    override fun upload(sourceFile: MultipartFile, destinationFileName: String): String {
        val filename = sourceFile.originalFilename!!
        val ext = filename.substring(filename.lastIndexOf(".") + 1)
        val destinationFile = File("${destinationFileUriPrefix}${File.separator}${destinationFileName}.${ext}")
        destinationFile.mkdirs()
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
