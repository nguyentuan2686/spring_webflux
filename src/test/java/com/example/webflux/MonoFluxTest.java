package com.example.webflux;

import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class MonoFluxTest {

    @Test
    public void testMono(){
        Mono<?> mono = Mono.just(Arrays.asList("f", "u"))
                .then(Mono.error(new RuntimeException("Ex")))
                .log();
        Disposable subscribe = mono.subscribe(System.out::println, e -> System.out.println(e.getMessage()));
    }

    @Test
    public void testFlux(){
        Flux<String> just = Flux.just("Sb", "JAba", "Hiber")
                .concatWithValues("JPA")
                .concatWith(Flux.error(new RuntimeException("Ex in Flux")))
                .concatWithValues("cloud")
                .log();
        just.subscribe(System.out::println, e-> System.out.println(e.getMessage()));
    }
}
