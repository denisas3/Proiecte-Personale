package curs3.io.repository;

public interface PageObserver {
    void onPageChanged(int page, int totalPages);
}