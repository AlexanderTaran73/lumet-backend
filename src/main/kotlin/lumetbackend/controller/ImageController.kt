package lumetbackend.controller

import lumetbackend.service.imageservice.service.FileService
import org.springframework.core.io.FileSystemResource
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/images")
class ImageController(private val fileService: FileService) {
    @GetMapping("/getImage/{fileName}", produces = [MediaType.IMAGE_JPEG_VALUE])
    fun getImage(@PathVariable fileName: String): FileSystemResource {
        return fileService.getImage(fileName)
    }
}