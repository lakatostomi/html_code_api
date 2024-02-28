package com.example.demo.restapi.service;

import com.example.demo.restapi.dto.HtmlElementDTO;
import com.example.demo.htmlcodegenerator.model.HtmlElement;

import java.util.List;

public interface CodeService<T extends HtmlElement> {
    List<T> getAll();

    List<T> addHtmlCode(HtmlElementDTO htmlElementDTO);

    void deleteHtmlElement(int id);

    String save();

}
