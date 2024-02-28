package com.example.demo.htmlcodegenerator.model;

public abstract class HtmlElement {

    protected Integer id;
    protected String name;
    protected String text;
    protected Attribute attribute;

    public HtmlElement(String name, String text, Attribute attribute) {
        this.name = name;
        this.text = text;
        this.attribute = attribute;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String attribute = "";
        if (!this.attribute.getName().isEmpty()) {
            attribute += this.attribute.getName() + "=" + this.attribute.getValue();
        }
        return "<" + name + " " + attribute + ">" + (text.isEmpty() ? "" : text) + "</" + name + ">";
    }
}
