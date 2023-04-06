package com.example.webflux.handler;

import com.example.webflux.dao.CustomerDao;
import com.example.webflux.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {

    @Autowired
    private CustomerDao customerDao;

    public Mono<ServerResponse> loadCustomer(ServerRequest request){
        Flux<Customer> customerFlux = customerDao.getCustomerList();
        return ServerResponse.ok().body(customerFlux, Customer.class);
    }

    public Mono<ServerResponse> findCustomer(ServerRequest request){
        int input = Integer.parseInt(request.pathVariable("input"));
        Mono<Customer> next = customerDao.getCustomerList().filter(c -> c.getId() == input).next();
        return ServerResponse.ok()
                .body(next, Customer.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request){
        Mono<Customer> customerMono = request.bodyToMono(Customer.class);
        Mono<String> map = customerMono.map(c -> "Save sucsess: " + c);
        return ServerResponse.ok().body(map, String.class);
    }

}
