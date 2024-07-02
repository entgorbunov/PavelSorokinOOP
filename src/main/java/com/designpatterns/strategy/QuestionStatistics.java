package com.designpatterns.strategy;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString

public class QuestionStatistics {
    private final String questionTitle;
    private final Map<String, Integer> selectedVariantsCount;
}
