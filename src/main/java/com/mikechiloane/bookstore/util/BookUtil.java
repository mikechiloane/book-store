package com.mikechiloane.bookstore.util;

import com.mikechiloane.bookstore.model.Order;
import com.mikechiloane.bookstore.repository.BookRepository;
import com.mikechiloane.bookstore.web.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookUtil {
    private final BookRepository bookRepository;

    public boolean bookExists(Long id) {
        return bookRepository.findById(id).isPresent();
    }

    public boolean bookIsAvailable(Long id){
        return bookRepository.existsByIdAndQuantityGreaterThan(id,0);
    }

    public boolean bookExists(String title) {
        return bookRepository.existsByTitleIgnoreCase(title);
    }

    public static List<OrderResponse> mapToOrderResponse(List<Order> orders) {
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order order : orders) {
            orderResponses.add(OrderResponse.builder()
                    .id(order.getId())
                    .title(order.getBook().getTitle())
                    .author(order.getBook().getAuthor())
                    .build());
        }
        return orderResponses;
    }


}
