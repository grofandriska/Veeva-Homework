package hu.grofandriska.veeva.model.account.response;

public class ResponseDetails {

    private int pagesize;
    private int pageoffset;
    private int size;
    private int total;

    public int getPagesize() { return pagesize; }
    public void setPagesize(int pagesize) { this.pagesize = pagesize; }

    public int getPageoffset() { return pageoffset; }
    public void setPageoffset(int pageoffset) { this.pageoffset = pageoffset; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
}
