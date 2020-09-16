package sb.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ListToPageConverter<T> {

    public Page<T> toPage(List<T> tList, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end =   (int) (Math.min((start + pageable.getPageSize()), tList.size()));

        return new PageImpl<T>(tList.subList(start, end), pageable, tList.size());
    }
}
