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
1. have the HTML form - `enctype="multipart/form-data"` without it, the uploaded file will ALWAYS be null. This is a browser rule.

2. in the controller, if its **Servlet** have:

```
@MultipartConfig 
       public class MainController extends HttpServlet {
       
```

- if its **SpringBoot**, have 
        
```
@PostMapping("/save")
        public String save( @RequestParam("file") MultipartFile file) {
            // file.getOriginalFilename(), file.getBytes(), etc.
        }    
```

4. Check if file is present, then u can upload it and also can run some validations eg, checking file type.

5. Set up an method for downloading the file. This endpoint will link to your view download link.
                   
6. Also prior to this, u will have set up a `FileUtil` class that links to/ creates a directory where the upload files will be stored. Define the directory path in `application.properties` and obtain it in this class via the `@Value` annotation from `Lombok`. Also u can also define attrubutes like max upload size here in `application.properties`.

7. Also, u can add client side validations like accepted file types extensions like:
```
<label for="file" class="font-weight-bold">Upload any vehicle file</label>
								 <input type="file" name="file" id="file"
								class="form-control"
								accept=".png,.jpg,.jpeg,.pdf">
								<small class="form-text text-muted">Max 10MB. PNG, JPG, JPEG, PDF only</small>
```
*pay attention to name & id, thats how it should be, file* that is if you want to avoid problems in the controller.

- Yea, thats it. application.properties-> FileUtil-> Controller-> View(form and display page). U can put the saving logic in the controller but for now lets KISS.:



