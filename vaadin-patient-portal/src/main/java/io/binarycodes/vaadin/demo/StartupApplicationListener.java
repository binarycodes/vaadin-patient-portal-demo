package io.binarycodes.vaadin.demo;

import io.binarycodes.vaadin.demo.dummy.FakerService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class StartupApplicationListener {

    private final FakerService fakerService;

    public StartupApplicationListener(FakerService fakerService) {
        this.fakerService = fakerService;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        fakerService.createAndPersistFakePatients(100, false);
    }
}