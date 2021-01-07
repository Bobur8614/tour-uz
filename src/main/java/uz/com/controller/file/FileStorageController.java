package uz.com.controller.file;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.com.dto.file.ResourceFileDto;
import uz.com.dto.file.ResourceFileUpdateDto;
import uz.com.response.DataDto;
import uz.com.service.file.IFileStorageService;
import uz.com.service.file.IResourceFileService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static uz.com.controller.ApiController.API_PATH;
import static uz.com.controller.ApiController.V_1;

@Api(value = "File Storage controller", description = "FileStorage")
@RestController
public class FileStorageController {
    private final IFileStorageService fileStorageService;
    private final IResourceFileService resourceFileService;

    @Autowired
    public FileStorageController(IFileStorageService fileStorageService, IResourceFileService resourceFileService) {
        this.fileStorageService = fileStorageService;
        this.resourceFileService = resourceFileService;
    }

    @RequestMapping(value = API_PATH + V_1 + "/resource/upload/file", method = RequestMethod.POST)
    public ResponseEntity<DataDto<ResourceFileDto>> uploadFile(@RequestParam("file") MultipartFile file,
                                                               @RequestParam(required = false, name = "minWidth") Integer minWidth,
                                                               @RequestParam(required = false, name = "minHeight") Integer minHeight) {
        return fileStorageService.storeFile(file, minWidth, minHeight);
    }

    @RequestMapping(value = API_PATH + V_1 + "/resource/upload/files", method = RequestMethod.POST)
    public ResponseEntity<DataDto<List<ResourceFileDto>>> uploadFiles(@RequestParam("files") MultipartFile[] files,
                                                                      @RequestParam(required = false, name = "minWidth") Integer minWidth,
                                                                      @RequestParam(required = false, name = "minHeight") Integer minHeight) {
        return fileStorageService.storeFiles(Arrays.asList(files), minWidth, minHeight);
    }

    @RequestMapping(value = API_PATH + V_1 + "/resource/uploads/{fileName:.+}", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName.replace("|", "/"));
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @RequestMapping(value = API_PATH + V_1 + "/resource/update", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<ResourceFileDto>> update(@RequestBody ResourceFileUpdateDto dto) {
        return resourceFileService.update(dto);
    }

    @RequestMapping(value = API_PATH + V_1 + "/resource/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DataDto<Boolean>> deleteResource(@PathVariable(value = "id") Long id) {
        return fileStorageService.delete(id);
    }

}
