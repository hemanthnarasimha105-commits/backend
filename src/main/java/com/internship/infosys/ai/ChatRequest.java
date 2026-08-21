package com.internship.infosys.ai;

import lombok.Data;

@Data
public class ChatRequest {

    private String message;

    private String currentPage;

    private String username;

    private String role;
}
