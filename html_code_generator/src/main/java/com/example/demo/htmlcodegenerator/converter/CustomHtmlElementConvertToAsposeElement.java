package com.example.demo.htmlcodegenerator.converter;

import com.aspose.html.*;
import com.aspose.html.dom.Element;
import com.example.demo.htmlcodegenerator.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomHtmlElementConvertToAsposeElement {


    public List<Element> convert(List<HtmlElement> htmlElementList, HTMLDocument htmlDocument) {
        List<Element> elementList = new ArrayList<>();
        for (HtmlElement e : htmlElementList) {
            if (e instanceof H1Element) {
                Element h1 = createElement(e, htmlDocument);
                elementList.add(h1);
            } else if (e instanceof ParagraphElement) {
                Element p = createElement(e, htmlDocument);
                if (!((ParagraphElement) e).getChildElementList().isEmpty()) {
                    for (HtmlElement htmlElement : ((ParagraphElement) e).getChildElementList()) {
                        Element child = createElement(htmlElement, htmlDocument);
                        p.appendChild(child);
                    }
                }
                elementList.add(p);
            } else if (e instanceof HyperLinkElement) {
                Element a = createElement(e, htmlDocument);
                elementList.add(a);
            } else if (e instanceof TableElement) {
                Element table = createElement(e, htmlDocument);
                if (!((TableElement) e).getTableRowElementList().isEmpty()) {
                    for (TableRowElement tableRowElement : ((TableElement) e).getTableRowElementList()) {
                        Element tableRowChild = createElement(tableRowElement, htmlDocument);
                        if (!tableRowElement.getTableDataElementList().isEmpty()) {
                            for (TableDataElement tableDataElement : tableRowElement.getTableDataElementList()) {
                                Element tableDataChild = createElement(tableDataElement, htmlDocument);
                                tableRowChild.appendChild(tableDataChild);
                            }
                        }
                        table.appendChild(tableRowChild);
                    }
                }
                elementList.add(table);
            }
        }

        return elementList;
    }

    private Element createElement(HtmlElement htmlElement, HTMLDocument htmlDocument) {
        var element = htmlDocument.createElement(htmlElement.getName());
        var text = htmlDocument.createTextNode(htmlElement.getText());
        if (!htmlElement.getAttribute().getName().isEmpty()) {
            element.setAttribute(htmlElement.getAttribute().getName(), htmlElement.getAttribute().getValue());
        }
        element.appendChild(text);
        return element;
    }
}
