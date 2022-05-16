package com.github.lukasleppich.subscription;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

@ApplicationScoped
public class TestService {

    public BehaviorSubject<List<String>> subject = BehaviorSubject.createDefault(List.of());

}
