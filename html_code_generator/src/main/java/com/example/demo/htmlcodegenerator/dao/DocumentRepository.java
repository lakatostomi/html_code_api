package com.example.demo.htmlcodegenerator.dao;

import java.util.List;

import com.aspose.html.utils.ST;
import org.springframework.stereotype.Component;


public interface DocumentRepository<T, S> {

    void createDocument(List<S> contentList);

    String saveDocument();

    T getDocument();

    void setDocument(T document);
}
