package curs3.io.repository;

import java.util.List;

public class Page<E> {

    private final List<E> content;
    private final int totalElements;

    public Page(List<E> content, int totalElements) {
        this.content = content;
        this.totalElements = totalElements;
    }

    public List<E> getContent() {
        return content;
    }

    public int getTotalElements() {
        return totalElements;
    }
}
