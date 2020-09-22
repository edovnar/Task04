package sb.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


@Component
public class PaginationUtil {

    public static String addPaging(String query, Pageable pageable) {
        String[] sort = pageable.getSort().toString().split(":");

        String orderBy = "";
        String direction = "";

        if (sort.length > 0) {
            orderBy = sort[0];
        }
        if (sort.length > 1) {
            direction = sort[1];
        }

        return query + String.format(" order by %s %s limit %s offset %s",
                orderBy, direction, pageable.getPageSize(), pageable.getOffset());
    }
}
