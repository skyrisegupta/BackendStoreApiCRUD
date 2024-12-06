package com.akash.gupta.BackendStoreCRUD.helper;

import com.akash.gupta.BackendStoreCRUD.dtos.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

// Co
public class Helper {
    //       user entity and user dto                              * we are passing the page , which type of class we are passing at v
    public static <U, V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type) {

        List<U> entity = page.getContent();
        // getting daata fom the page content and putting thoes into a map and
        List<V> dtoList = entity.stream().map(object -> new ModelMapper().map(object, type)).collect(Collectors.toList());

        PageableResponse response = new PageableResponse<>();

        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastpage(page.isLast());


        return response;
    }
}
