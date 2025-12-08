# book-loan-crud


## Multipart File Upload:
enctype="multipart/form-data"


## File Upload Specification
- Save file to local disk folder defined in application.properties
- Store only the path in DB
- Validate:
- Max size: 10MB
- Allowed types: .png / .jpg / .jpeg / .pdf
- Provide download link in list page

## steps
1. Have the HTML form - `enctype="multipart/form-data"` without it, the uploaded file will ALWAYS be null. This is a browser rule.
```html
<form id="serviceRequestForm" action="/vsreqs/requests/save"
					method="post" enctype="multipart/form-data">
```                    

2. In the controller, if its **Servlet** have:

```java
@MultipartConfig 
       public class MainController extends HttpServlet {
       
```

- if its **SpringBoot**, have 
        
```java
@PostMapping("/save")
        public String save( @RequestParam("file") MultipartFile file) {
            // file.getOriginalFilename(), file.getBytes(), etc.
        }    
```

4. Check if file is present, then u can upload it and also can run some validations eg, checking file type.
```java
@PostMapping("/save")

public String saveRequest(@Valid @ModelAttribute ServiceRequest request,
			BindingResult rs,
			@RequestParam("file") MultipartFile file,
			RedirectAttributes rd) {
//		run validations first

		if (rs.hasErrors()) {
			rd.addFlashAttribute("validationErrors", rs.getAllErrors());
			return "redirect:/requests/create";

		}

		 // Validate  child entities

		if (request.getVehicleModel().getModelId() == 0) {
		    rd.addFlashAttribute("error", "Please select a valid vehicle model");
		    return "redirect:/requests/create";
		}

		if (request.getServiceSubType().getServiceSubTypeId() == 0) {
		    rd.addFlashAttribute("error", "Please select a valid service sub type");
		    return "redirect:/requests/create";
		}


		//  Upload file only if present
		if(file != null && !file.isEmpty()) {
	        String contentType = file.getContentType();

			  // check file type
	        if (contentType == null ||
	            !(contentType.equals("image/png") ||
	              contentType.equals("image/jpg") ||
	              contentType.equals("image/jpeg") ||
	              contentType.equals("application/pdf"))) {

	            rd.addFlashAttribute("error", "Only PNG, JPG, JPEG, PDF files allowed");
	            return "redirect:/requests/create";
	        }
	    String uploadedFileName = fileUtil.uploadFile(file);
	    request.setAttachmentPath(uploadedFileName);
		}

//		proceed to save after validations
		ServiceRequest savedRequest = serviceRequestService.saveRequest(request);
		String msg = savedRequest.getCustomerName() + ", your request is being processed";
		rd.addFlashAttribute("msg", msg);
		return "redirect:/requests/create";

	}
```    

5. Set up a method for downloading the file.
```java
@GetMapping("/download")
	public ResponseEntity<Resource> downloadFile(@RequestParam("attachmentPath") String fileName) {

	    try {
	        Path filePath = Paths.get(fileUtil.getDirPath() + fileName);
	        Resource resource = new UrlResource(filePath.toUri());

	        if (!resource.exists()) {
	            return ResponseEntity.notFound().build();
	        }

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION,
	                        "attachment; filename=" + resource.getFilename())
	                .body(resource);

	    } catch (Exception e) {
	        throw new RuntimeException("Error downloading file", e);
	    }
	}
```    
- This endpoint will link to your view download link. In this case its a JSP.
```html
<td><a href="/vsreqs/requests/download?attachmentPath=${a.attachmentPath}">${a.attachmentPath} </a></td>
```
                   
6. Also prior to this, u will have set up a `FileUtil` class that links to/ creates a directory where the upload files will be stored.
```java
package tech.csm.vsreq.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
public class FileUtil {

    @Value("${upload.dir}")
    private String uploadDir;

    // return folder path
    public String getDirPath() {
        return uploadDir;
    }

    // upload the file and return filename
    public String uploadFile(MultipartFile file) {
    	  // Create directory if it doesn't exist
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = file.getOriginalFilename();
        Path destination = Paths.get(uploadDir + fileName);

        try {
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("File upload failed", e);
        }

        return fileName;
    }
}
```

Define the directory path in **application.properties**  and obtain it in this class via the `@Value` annotation from `Lombok` _(check FileUtil code)._ Also u can also define attributes like max upload size here in `application.properties`.
```
#Application properties
upload.dir=C:/uploads/vehicles/

#file upload max size
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```

7. Also, u can add client side validations like accepted file types extensions like:
```HTML
<label for="file" class="font-weight-bold">Upload any vehicle file</label>
			<input type="file" name="file" id="file"
								class="form-control"
								accept=".png,.jpg,.jpeg,.pdf">
			<small class="form-text text-muted">Max 10MB. PNG, JPG, JPEG, PDF only</small>
```
*pay attention to name & id, thats how it should be named*; **file**, that is, if you want to avoid problems in the controller.

- Yea, thats it. `application.properties`-> `FileUtil`-> `Controller`-> `View(form and display page)`. U can put the saving logic in the **Service layer** but for now lets KISS.



