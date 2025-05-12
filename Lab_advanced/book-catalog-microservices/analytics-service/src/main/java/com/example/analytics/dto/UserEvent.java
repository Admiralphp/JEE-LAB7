package com.example.analytics.dto;

import lombok.Data;

@Data
public class UserEvent {
    public enum EventType {
        REGISTERED, UPDATED, DELETED
    }

    private EventType eventType;
    private UserDto user;
}
