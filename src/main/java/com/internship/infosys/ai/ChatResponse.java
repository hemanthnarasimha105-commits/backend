package com.internship.infosys.ai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {

    private String message;

    private String action;

    private String route;

    private Object data;

    public static ChatResponse message(
            String message) {

        return new ChatResponse(
                message,
                "NONE",
                null,
                null
        );
    }

    public static ChatResponse navigate(
            String message,
            String route) {

        return new ChatResponse(
                message,
                "NAVIGATE",
                route,
                null
        );
    }
}
