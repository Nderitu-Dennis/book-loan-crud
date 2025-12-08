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
                   


