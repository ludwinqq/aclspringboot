package bo.academia.carvic.infrastructure.common.libraries;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import bo.academia.carvic.domain.common.helpers.pagination.PageResponse;
import bo.academia.carvic.domain.common.helpers.pagination.PageableRequest;

public class PaginationHelper {

    // Una sola "librería" interna que traduce cualquier Page de Spring a tu PageResponse de Clean Architecture
    public static <T, E> PageResponse<T> mapPaging(Page<E> springPage, Function<E, T> mapper) {
        var dtoList = springPage.getContent().stream()
                .map(mapper)
                .collect(Collectors.toList());

        return new PageResponse<>(
                dtoList,
                springPage.getNumber(),
                springPage.getTotalPages(),
                springPage.getTotalElements(),
                springPage.getSize()
        );
    }

    public static Pageable toSpringPageable(PageableRequest request) {
        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        return PageRequest.of(request.getPage(), request.getSize(), sort);
    }
}
