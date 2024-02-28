package com.example.demo.htmlcodegenerator.model;

import java.util.ArrayList;
import java.util.List;

public class TableRowElement extends HtmlElement {

    private List<TableDataElement> tableDataElementList;

    public TableRowElement(String name, String text, Attribute attribute) {
        super(name, text, attribute);
        this.tableDataElementList = new ArrayList<>();
    }


    public List<TableDataElement> getTableDataElementList() {
        return tableDataElementList;
    }

    @Override
    public String toString() {
        StringBuilder attribute = new StringBuilder();
        if (!this.attribute.getName().isEmpty()) {
            attribute.append(this.attribute.getName()).append("=").append(this.attribute.getValue());
        }

        StringBuilder childElements = new StringBuilder();
        if (!tableDataElementList.isEmpty()) {
            for (HtmlElement e : tableDataElementList) {
                childElements.append(e.toString());
            }
        }

        return "<" + name + " " + attribute + ">" + (text.isEmpty() ? "" : text) +
                childElements +
                "</" + name + ">";
    }
}
