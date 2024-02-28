package com.example.demo.htmlcodegenerator.model;

import java.util.ArrayList;
import java.util.List;

public class ParagraphElement extends HtmlElement {

    private List<HtmlElement> childElementList;

    public ParagraphElement(String name, String text, Attribute attribute) {
        super(name, text, attribute);
        this.childElementList = new ArrayList<>();
    }

    public List<HtmlElement> getChildElementList() {
        return childElementList;
    }

    @Override
    public String toString() {
        String attribute = "";
        if (!this.attribute.getName().isEmpty()) {
            attribute += this.attribute.getName() + "=" + this.attribute.getValue();
        }

        StringBuilder childElements = new StringBuilder();
        if (!childElementList.isEmpty()) {
            for (HtmlElement e : childElementList) {
                childElements.append(e.toString());
            }
        }

        return "<" + name + " " + attribute + ">" + (text.isEmpty() ? "" : text) +
                childElements +
                "</" + name + ">";
    }
}
