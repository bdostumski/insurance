package com.insurance.application.events;

import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpSession;

public class OnRequestModelsEvent extends ApplicationEvent {

    private int brandId;
    HttpSession session;

    public OnRequestModelsEvent(int brandId, HttpSession session) {
        super(brandId);

        this.brandId = brandId;
    }

    public int getBrandId() {
        return brandId;
    }

    public HttpSession getSession() {
        return session;
    }
}
