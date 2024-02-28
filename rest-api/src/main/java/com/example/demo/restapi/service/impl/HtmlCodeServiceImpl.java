package com.example.demo.restapi.service.impl;

import com.aspose.html.HTMLDocument;
import com.aspose.html.dom.Element;
import com.example.demo.htmlcodegenerator.converter.CustomHtmlElementConvertToAsposeElement;
import com.example.demo.htmlcodegenerator.dao.DocumentRepository;
import com.example.demo.htmlcodegenerator.dao.impl.HtmlDocumentRepositoryImpl;
import com.example.demo.htmlcodegenerator.model.*;
import com.example.demo.restapi.dto.HtmlElementDTO;
import com.example.demo.restapi.factory.HtmlElementFactory;
import com.example.demo.restapi.service.CodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class HtmlCodeServiceImpl implements CodeService<HtmlElement> {

    private static final Logger log = LoggerFactory.getLogger(HtmlCodeServiceImpl.class);
    private final DocumentRepository htmlDocumentRepository;
    private final CustomHtmlElementConvertToAsposeElement convertToAsposeElement;
    private List<HtmlElement> htmlElementList;
    private final HtmlElementFactory htmlElementFactory;

    public HtmlCodeServiceImpl(HtmlDocumentRepositoryImpl htmlDocumentRepository, CustomHtmlElementConvertToAsposeElement convertToAsposeElement, HtmlElementFactory htmlElementFactory) {
        this.htmlDocumentRepository = htmlDocumentRepository;
        this.convertToAsposeElement = convertToAsposeElement;
        this.htmlElementFactory = htmlElementFactory;
        this.htmlElementList = new ArrayList<>();
    }

    @Override
    public List<HtmlElement> getAll() {
        return htmlElementList;
    }

    @Override
    public List<HtmlElement> addHtmlCode(HtmlElementDTO htmlElementDTO) {
        log.info("Saving new HTML element: {}", htmlElementDTO);
        HtmlElement htmlElement = htmlElementFactory.createHtmlElement(htmlElementDTO);
        addElementToList(htmlElement, htmlElementDTO.parentId());
        return htmlElementList;
    }

    private void addElementToList(HtmlElement htmlElement, int parentId) {
        if (parentId > 0) {
            HtmlElement parent = findElementById(parentId);
            if (parent.getName().equals("null")) {
                throw new NoSuchElementException("The html element with id: \"" + parentId + "\" is not exist");
            }
            if (parent instanceof ParagraphElement) {
                ((ParagraphElement) parent).getChildElementList().add(htmlElement);
            } else if (parent instanceof TableElement && htmlElement instanceof TableRowElement) {
                ((TableElement) parent).getTableRowElementList().add((TableRowElement) htmlElement);
            } else if (parent instanceof TableRowElement && htmlElement instanceof TableDataElement) {
                ((TableRowElement) parent).getTableDataElementList().add((TableDataElement) htmlElement);
            }
        } else {
            this.htmlElementList.add(htmlElement);
        }
    }

    @Override
    public void deleteHtmlElement(int id) {
        log.info("Deleting HTML element with id: {}", id);
        HtmlElement htmlElement = findElementById(id);
        if (htmlElement.getName().equals("null")) {
            throw new NoSuchElementException("The html element with id: \"" + id + "\" is not exist");
        }
        for (HtmlElement e : htmlElementList) {
            if (e instanceof ParagraphElement) {
                ((ParagraphElement) e).getChildElementList().remove(htmlElement);
            } else if (e instanceof TableElement && htmlElement instanceof TableRowElement) {
                ((TableElement) e).getTableRowElementList().remove(htmlElement);
            } else if (e instanceof TableElement && htmlElement instanceof TableDataElement) {
                List<TableRowElement> tableRowElementList = ((TableElement) e).getTableRowElementList();
                for (TableRowElement tableRowElement : tableRowElementList) {
                    tableRowElement.getTableDataElementList().remove(htmlElement);
                }
            }
        }
    }

    private HtmlElement findElementById(int id) {
        HtmlElement htmlElement = htmlElementList.stream().filter(htmlElement1 -> Objects.equals(htmlElement1.getId(), id))
                .findAny().orElse(new H1Element("null", "null", null));
        if (htmlElement.getName().equals("null")) {
            for (HtmlElement e : htmlElementList) {
                if (e instanceof ParagraphElement) {
                    htmlElement = ((ParagraphElement) e).getChildElementList().stream().filter(htmlElement1 -> Objects.equals(htmlElement1.getId(), id))
                            .findAny().orElse(new H1Element("null", "null", null));
                }
            }
        }
        if (htmlElement.getName().equals("null")) {
            for (HtmlElement e : htmlElementList) {
                if (e instanceof TableElement) {
                    htmlElement = ((TableElement) e).getTableRowElementList().stream().filter(htmlElement1 -> Objects.equals(htmlElement1.getId(), id))
                            .findAny().orElse(new TableRowElement("null", "null", null));
                }
            }
        }

        if (htmlElement.getName().equals("null")) {
            for (HtmlElement e : htmlElementList) {
                if (e instanceof TableElement) {
                    List<TableRowElement> tableRowElementList = ((TableElement) e).getTableRowElementList();
                    for (TableRowElement tableRowElement : tableRowElementList) {
                        htmlElement = tableRowElement.getTableDataElementList().stream().filter(htmlElement1 -> Objects.equals(htmlElement1.getId(), id))
                                .findAny().orElse(new TableDataElement("null", "null", null));
                    }
                }
            }
        }

        return htmlElement;
    }

    @Override
    public String save() {
        log.info("Saving HTML document...");
        List<Element> elementList = convertToAsposeElement.convert(htmlElementList, (HTMLDocument) htmlDocumentRepository.getDocument());
        htmlDocumentRepository.createDocument(elementList);
        String htmlCode = htmlDocumentRepository.saveDocument();
        log.info("""
                ...saving finished successfully!
                generated code: {}
                """, htmlCode);
        clean();
        return htmlCode;
    }

    private void clean() {
        htmlElementList.clear();
        htmlElementFactory.setId(1);
        htmlDocumentRepository.setDocument(new HTMLDocument());
    }
}
