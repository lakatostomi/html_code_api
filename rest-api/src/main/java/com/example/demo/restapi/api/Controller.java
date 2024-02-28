package com.example.demo.restapi.api;

import com.example.demo.restapi.dto.HtmlElementDTO;
import com.example.demo.restapi.service.CodeService;
import com.example.demo.restapi.service.impl.HtmlCodeServiceImpl;
import com.example.demo.htmlcodegenerator.model.HtmlElement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rest/v1/codes")
@Tag(name = "Controller", description = "Handles endpoints that are connected to saving and deleting HTML elements")
public class Controller {

    private CodeService<HtmlElement> htmlCodeService;

    public Controller(HtmlCodeServiceImpl htmlCodeService) {
        this.htmlCodeService = htmlCodeService;
    }

    @Operation(summary = "Returns a list of HTML elements")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Element list successfully returned to client.",
            content = @Content(mediaType = "application/json", schema = @Schema(allOf = {HtmlElement.class, List.class})))
    })
    @GetMapping
    public ResponseEntity<List<HtmlElement>> getAll() {
        return new ResponseEntity<>(htmlCodeService.getAll(), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "Saves a new HTML element")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Saving the element was successful",
            content = @Content(mediaType = "application/json", schema = @Schema(allOf = {HtmlElement.class, List.class}))),
            @ApiResponse(responseCode = "400", description = "Element type is not supported"
                    , content = @Content(mediaType = "application/problem+json")),
            @ApiResponse(responseCode = "404", description = "In case of invalid parent id"
                    , content = @Content(mediaType = "application/problem+json"))
    })
    @PostMapping
    public ResponseEntity<List<HtmlElement>> addHtmlCode(
            @Parameter(description = "DTO object to create HTML elements")
            @RequestBody HtmlElementDTO htmlElementDTO) {
        return new ResponseEntity<>(htmlCodeService.addHtmlCode(htmlElementDTO), HttpStatusCode.valueOf(201));
    }

    @Operation(summary = "Delete an HTML element")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Deleting the element was successful",
            content = @Content(mediaType = "application/json", schema = @Schema(allOf = {Void.class})))
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHtmlElement(
            @Parameter(description = "The id of HTML element")
            @PathVariable int id) {
        htmlCodeService.deleteHtmlElement(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Saves a the final HTML document")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Saving the document was successful",
            content = @Content(mediaType = "application/json", schema = @Schema(allOf = {String.class}))),
            @ApiResponse(responseCode = "500", description = "If saving of document fails"
                    , content = @Content(mediaType = "application/problem+json"))
    })
    @GetMapping("/save")
    public ResponseEntity<String> saveGeneratedCode() {
        return new ResponseEntity<>(htmlCodeService.save(), HttpStatusCode.valueOf(200));
    }
}
