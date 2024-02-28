package com.example.demo.restapi.factory;

import com.example.demo.htmlcodegenerator.model.*;
import com.example.demo.restapi.dto.HtmlElementDTO;
import com.example.demo.restapi.exception.ConstructorInvocationException;
import com.example.demo.restapi.exception.UnsupportedElementException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Component
public class HtmlElementFactory {

    private int id = 1;

    public HtmlElement createHtmlElement(HtmlElementDTO htmlElementDTO) {
        switch (htmlElementDTO.name()) {
            case "h1" -> {
                return createElement(H1Element.class, htmlElementDTO);
            }
            case "p" -> {
                return createElement(ParagraphElement.class, htmlElementDTO);
            }
            case "a" -> {
                return createElement(HyperLinkElement.class, htmlElementDTO);
            }
            case "table" -> {
                return createElement(TableElement.class, htmlElementDTO);
            }
            case "tr" -> {
                return createElement(TableRowElement.class, htmlElementDTO);
            }
            case "td" -> {
                return createElement(TableDataElement.class, htmlElementDTO);
            }
            default -> throw new UnsupportedElementException("This html element: \"" + htmlElementDTO.name() +
                    "\" is not supported! Only h1, p, a, table, tr and td are supported!");
        }
    }

    private HtmlElement createElement(Class<? extends HtmlElement> clazz, HtmlElementDTO htmlElementDTO) {
        try {
            Constructor<? extends HtmlElement> constructor = clazz.getDeclaredConstructor(String.class,
                    String.class, Attribute.class);
            HtmlElement htmlElement = constructor.newInstance(htmlElementDTO.name(), htmlElementDTO.text(),
                    new Attribute(htmlElementDTO.elementAttributeDTO().name(), htmlElementDTO.elementAttributeDTO().value()));
            htmlElement.setId(id++);
            return htmlElement;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new ConstructorInvocationException(e.getMessage());
        }
    }

    public void setId(int id) {
        this.id = id;
    }
}
