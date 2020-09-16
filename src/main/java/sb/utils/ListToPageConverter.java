package sb.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

public class ListToPageConverter<T> {

    public Page<T> toPage(List<T> tList, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end =   (int) ((start + pageable.getPageSize()) > tList.size() ? tList.size()
                : (start + pageable.getPageSize()));
        Page<T> page
                = new PageImpl<T>(tList.subList(start, end), pageable, tList.size());
        return page;
    }
}
