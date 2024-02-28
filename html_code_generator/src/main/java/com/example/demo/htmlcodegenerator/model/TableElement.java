package com.example.demo.htmlcodegenerator.model;

import java.util.ArrayList;
import java.util.List;

public class TableElement extends HtmlElement {

    private List<TableRowElement> tableRowElementList;

    public TableElement(String name, String text, Attribute attribute) {
        super(name, text, attribute);
        this.tableRowElementList = new ArrayList<>();
    }

    public List<TableRowElement> getTableRowElementList() {
        return tableRowElementList;
    }

    @Override
    public String toString() {
        StringBuilder attribute = new StringBuilder();
        if (!this.attribute.getName().isEmpty()) {
            attribute.append(this.attribute.getName()).append("=").append(this.attribute.getValue());
        }

        StringBuilder childElements = new StringBuilder();
        if (!tableRowElementList.isEmpty()) {
            for (HtmlElement e : tableRowElementList) {
                childElements.append(e.toString());
            }
        }

        StringBuilder result = new StringBuilder();
        result.append("<").append(name).append(" ").append(attribute).append(">").append((text.isEmpty() ? "" : text)).append(childElements)
                .append("</").append(name).append(">");
        return result.toString();
    }
}
