package by.corporation.quest_fire.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class Paginator extends TagSupport {


    private int currentPage;
    private int numberOfPages;
    private int viewPageCount;
    private String url;

    /**
     * This method returns SKIP_BODY which means ignoring
     * any content between tags.
     * It is responsible for defining tag for the pagination.
     */
      public int doStartTag() throws JspException {
        if (viewPageCount > numberOfPages) {
            viewPageCount = numberOfPages;
        }

        int startIndex = 1;
        int endIndex = startIndex + viewPageCount - 1;

        if (currentPage > endIndex) {
            if (currentPage == numberOfPages) {
                startIndex = numberOfPages - viewPageCount + 1;
            } else {
                startIndex = startIndex + 1;
            }
            endIndex = currentPage;
        }

        if (currentPage < startIndex) {
            startIndex = currentPage;
            if (currentPage == 1) {
                endIndex = viewPageCount;
            } else {
                endIndex = endIndex - 1;
            }
        }

        try {
            JspWriter out = pageContext.getOut();
            if (startIndex > 1) {
                out.write(formLink(1, "<<"));
                out.write(formLink(currentPage - 1, "<"));
            }



            for (int pageIndex = startIndex; pageIndex <= endIndex; pageIndex++) {
                if (pageIndex == currentPage) {
                    out.write(formLink(pageIndex, String.valueOf(pageIndex)));
                } else {
                    out.write(formLink(pageIndex, String.valueOf(pageIndex)));
                }
            }

            if (endIndex < numberOfPages) {
                out.write(formLink(currentPage + 1, ">"));
                out.write(formLink(numberOfPages, ">>"));
            }

        } catch (IOException e) {
            throw new JspException("Error in tag", e);
        }
        return SKIP_BODY;
    }

    /**
     * This method returns {@link String} which is a link for pagination.
     * It is responsible for defining tag for the pagination.
     * @param pageIndex is a current page.
     * @param content is number of pages and arrows.
     */
    private String formLink(int pageIndex, String content) {
        StringBuilder link = new StringBuilder();
        link.append("<a  class=\"page-link\" href='");
        link.append(url);
        link.append("page=");
        link.append(pageIndex);
        link.append("'>");
        link.append(content);
        link.append("</a>&nbsp;");
        return link.toString();
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setNumberOfPages(int pageCount) {
        this.numberOfPages = pageCount;
    }

    public void setViewPageCount(int viewPageCount) {
        this.viewPageCount = viewPageCount;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}