package br.com.fiap.paymentservice.application.dto.pagination;

import java.util.List;

public class PagedResult<T> {
    private final List<T> content;
    private final long totalElements;
    private final int page;
    private final int size;

    public PagedResult(List<T> content, long totalElements, int page) {
        this.content = content;
        this.totalElements = totalElements;
        this.page = page;
        this.size = content.size();
    }

    public List<T> getContent() {
        return content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}

