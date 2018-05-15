package eu.cehj.cdb2.hub.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * From the solution found <a href="https://blog.thecookinkitchen.com/how-to-consume-page-response-from-a-service-in-spring-boot-97293c18ba">here</a>. </br>
 * Because Spring's RestTemplate isn't able to handle {@link Pageable}.
 */
public class RestResponsePage<T> extends PageImpl<T> {

    private static final long serialVersionUID = 8136926214679537400L;
    private int number;
    private int size;
    private int totalPages;
    private int numberOfElements;
    private long totalElements;
    private boolean previousPage;
    private boolean first;
    private boolean nextPage;
    private boolean last;
    private Sort sort;

    public RestResponsePage(final List<T> content, final Pageable pageable, final long total) {
        super(content, pageable, total);
    }

    public RestResponsePage(final List<T> content) {
        super(content);
    }

    public RestResponsePage() {
        super(new ArrayList<T>());
    }

    public PageImpl<T> pageImpl() {
        return new PageImpl<>(this.getContent(), new PageRequest(this.getNumber(),
                this.getSize(), this.getSort()), this.getTotalElements());
    }

    @Override
    public int getNumber() {
        return this.number;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    @Override
    public int getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(final int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public int getNumberOfElements() {
        return this.numberOfElements;
    }

    public void setNumberOfElements(final int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    @Override
    public long getTotalElements() {
        return this.totalElements;
    }

    public void setTotalElements(final long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isPreviousPage() {
        return this.previousPage;
    }

    public void setPreviousPage(final boolean previousPage) {
        this.previousPage = previousPage;
    }

    @Override
    public boolean isFirst() {
        return this.first;
    }

    public void setFirst(final boolean first) {
        this.first = first;
    }

    public boolean isNextPage() {
        return this.nextPage;
    }

    public void setNextPage(final boolean nextPage) {
        this.nextPage = nextPage;
    }

    @Override
    public boolean isLast() {
        return this.last;
    }

    public void setLast(final boolean last) {
        this.last = last;
    }

    @Override
    public Sort getSort() {
        return this.sort;
    }

    public void setSort(final Sort sort) {
        this.sort = sort;
    }
}