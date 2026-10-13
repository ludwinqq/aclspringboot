package bo.academia.carvic.domain.common.helpers.pagination;

public class PageableRequest {
    private int page;
    private int size;
    private String sortBy;
    private String direction; // "ASC" o "DESC"
    
    public PageableRequest(int page, int size, String sortBy, String direction) {
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.direction = direction;
    }
    
    public int getPage() {
        return page;
    }
    
    public int getSize() {
        return size;
    }
    
    public String getSortBy() {
        return sortBy;
    }
    
    public String getDirection() {
        return direction;
    }
    
}
