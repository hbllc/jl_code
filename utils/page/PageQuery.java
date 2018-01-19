package com.xgd.boss.core.page;


/**
 * 分页查询类
 *
 * @author Neo
 */
public class PageQuery {

    /**
     * 默认的分页大小
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 分页大小的最大值
     */
    public static final int MAX_PAGE_SIZE = 100000;

    /**
     * 当前页码
     */
    private int pageIndex = 1;

    /**
     * 当前页码
     */
    private int page = 1;

    /**
     * 分页大小
     */
    private int pageSize = DEFAULT_PAGE_SIZE;
    /**
     * 是否查询总记录数，默认为false
     */
    private Boolean isQueryTotal = false;

    /**
     * 总页数(供Mybatis分页拦截器使用)
     */
    private int totalPage;

    /**
     * 总记录数(供Mybatis分页拦截器使用)
     */
    private int totalCount;

    public PageQuery() {

    }

    /**
     * @param pageIndexStr
     * @param pageSizeStr
     */
    public PageQuery(String pageIndexStr, String pageSizeStr) {
        int pageIndex = 1;
        int pageSize = DEFAULT_PAGE_SIZE;
        try {
            pageIndex = Integer.parseInt(pageIndexStr);
        } catch (Exception e) {
        }
        try {
            pageSize = Integer.parseInt(pageSizeStr);
        } catch (Exception e) {
        }

        if (pageIndex < 1) {
            pageIndex = 1;
        }

        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
        this.pageIndex = pageIndex;
        setPageSize(pageSize);
    }

    public PageQuery(int pageIndex, int pageSize) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
        this.pageIndex = pageIndex;
        setPageSize(pageSize);
    }

    /**
     * 获取起始行
     *
     * @return
     */
    public int getOffset() {
        return (pageIndex - 1) * pageSize;
    }

    /**
     * 获取每页大小
     *
     * @return
     */
    public int getLimit() {
        return pageSize;
    }

    /**
     * 把offset转换为pageIndex
     *
     * @param offset   offset
     * @param pageSize 每页显示的记录数
     * @return pageIndex
     */
    public static int convertOffsetToPageIndex(int offset, int pageSize) {
        return offset / pageSize + 1;
    }

    //-------------------------------------------------

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page < 1) {
            page = DEFAULT_PAGE_SIZE;
        }
        if (page > MAX_PAGE_SIZE) {
            page = MAX_PAGE_SIZE;
        }
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getIsQueryTotal() {
        return isQueryTotal;
    }

    public void setIsQueryTotal(Boolean isQueryTotal) {
        this.isQueryTotal = isQueryTotal;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PageQuery{");
        sb.append("pageIndex=").append(pageIndex);
        sb.append(", page=").append(page);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", isQueryTotal=").append(isQueryTotal);
        sb.append(", totalPage=").append(totalPage);
        sb.append(", totalCount=").append(totalCount);
        sb.append('}');
        return sb.toString();
    }
}
