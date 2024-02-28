package com.example.demo.restapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.stereotype.Component;

@Component
@OpenAPIDefinition(info = @Info(title = "HTML Code Application"
        , version = "1.0-SNAPSHOT"
        , description = """
                        - This REST API generates random HTML code. The API support the following HTML elements: 
                        h1, p, a, table, tr and td. Only the body of HTML can be edited and each element can handle only 
                        one attribute. Some elements can have child elements like p, table and tr. 
                        Before saving the document the elements can be removed and the final document will be created 
                        based on the order in which the elements were saved.
                        
                        The application has 3 required program argument to start: 
                        - Last name
                        - First name
                        - Email address
                        
                        - The HTML document is saved by the Aspose library. 
                        """,
        termsOfService = "https://lpsolutions.hu/terms",
        license = @License(name = "Apache 2.0")
))

public class ApiDocumentation {
}
