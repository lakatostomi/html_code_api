package com.example.demo.htmlcodegenerator.dao.impl;

import com.aspose.html.HTMLDocument;
import com.aspose.html.HTMLLinkElement;
import com.aspose.html.HTMLMetaElement;
import com.aspose.html.dom.Element;
import com.aspose.html.saving.HTMLSaveFormat;
import com.example.demo.htmlcodegenerator.dao.DocumentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class HtmlDocumentRepositoryImpl implements DocumentRepository<HTMLDocument, Element> {

    @Value("${file.output.source}")
    private String outputFileSource;
    private HTMLDocument htmlDocument;
    @Value("${website.title}")
    private String title;


    public HtmlDocumentRepositoryImpl() {
        this.htmlDocument = new HTMLDocument();
    }

    @Override
    public void createDocument(List<Element> contentList) {
        var doctype = htmlDocument.createDocumentType("html", null, null, null);
        htmlDocument.setTitle(title);
        var meta = (HTMLMetaElement) htmlDocument.createElement("meta");
        meta.setAttribute("charset", "UTF-8");
        var head = htmlDocument.getElementsByTagName("head");
        var link = (HTMLLinkElement) htmlDocument.createElement("link");
        link.setAttribute("href", "https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css");
        link.setAttribute("rel", "stylesheet");
        head.get_Item(0).appendChild(meta);
        head.get_Item(0).appendChild(link);
        htmlDocument.insertBefore(doctype, htmlDocument.getChildNodes().get_Item(0));
        contentList.forEach(htmlElement -> htmlDocument.getBody().appendChild(htmlElement));
    }

    @Override
    public String saveDocument() {
        try {
            htmlDocument.save(outputFileSource, HTMLSaveFormat.HTML);
            String htmlCode = Files.lines(Path.of(outputFileSource)).toList().get(0);
            return htmlCode;
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public HTMLDocument getDocument() {
        return this.htmlDocument;
    }

    @Override
    public void setDocument(HTMLDocument document) {
        this.htmlDocument = document;
    }
}
