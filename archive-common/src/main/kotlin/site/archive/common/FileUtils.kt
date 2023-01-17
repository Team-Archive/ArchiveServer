@file:JvmName("FileUtils")

package site.archive.common

import org.springframework.http.MediaType.IMAGE_GIF_VALUE
import org.springframework.http.MediaType.IMAGE_JPEG_VALUE
import org.springframework.http.MediaType.IMAGE_PNG_VALUE
import org.springframework.web.multipart.MultipartFile

private const val HTTP = "http://"
private const val HTTPS = "https://"

fun verifyImageFile(imageFile: MultipartFile) {
    if (!listOf(IMAGE_PNG_VALUE, IMAGE_GIF_VALUE, IMAGE_JPEG_VALUE)
            .contains(imageFile.contentType)) {
        throw IllegalStateException("FIle uploaded is not an image")
    }
}

fun isFileUrl(file: String?) : Boolean{
    return file != null && (file.startsWith(HTTP) || file.startsWith(HTTPS))
}