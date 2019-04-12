package ru.langservice.translator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
public class PageWrapper<T> {
    private Page<T> page;

    public int getCurrPage(){
        return this.page.getNumber() + 1;
    }

    public int getFirstPage() {
        return 1;
    }

    public int getLastPage() {
        return page.getTotalPages();
    }

    public boolean isSinglePage() {
        return page.getTotalPages() == 1;
    }

    public boolean isDoublePage() {
        return page.getTotalPages() == 2;
    }

    public boolean isMultiPage(){
        return !this.isSinglePage() && !this.isDoublePage();
    }

    public int[] getRange() {
        int[] range;
        if (this.isSinglePage()) {
            range = new int[]{1};
            return range;
        }

        if (this.isDoublePage()) {
            range = new int[]{1, 2};
            return range;
        }

        int curr = this.getCurrPage();
        if (curr == this.getFirstPage()) {
            range = new int[]{1, 2, 3};
            return range;
        }

        if (curr == this.getLastPage()) {
            range = new int[]{curr - 2, curr - 1, curr};
            return range;
        }

        range = new int[]{curr - 1, curr, curr + 1};
        return range;
    }
}