package lumetbackend.service.imageservice.web.rest


//@RestController
//@RequestMapping("/images")
//class FileController(private val fileService: FileService) {
//
//    @PostMapping
//    fun uploadImage(@RequestParam("imageFile") imageFile: MultipartFile, request: HttpServletRequest): ResponseEntity<String> {
//        val fileName = fileService.saveImage(imageFile)
//        val body = getFileAccessUrl(request.requestURL, fileName)
//        return ResponseEntity(body, HttpStatus.CREATED)
//    }
//
//    private fun getFileAccessUrl(requestURL: StringBuffer?, fileName: String): String {
//        val baseUrl = requestURL ?: Exception("Invalid requestURL")
//        return "${baseUrl}/$fileName"
//    }
//
//}