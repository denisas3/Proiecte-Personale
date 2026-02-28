package curs3.io.repository;

public class Paging implements RepositoryPaging{

    private final int pageNumber;
    private final int pageSize;

    public Paging(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }
}
