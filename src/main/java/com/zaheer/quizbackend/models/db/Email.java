package com.zaheer.quizbackend.models.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private String sender;
    private String[] receivers;
    private String subject;
    private String text;
}
