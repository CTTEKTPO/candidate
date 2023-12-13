package com.example.candidate.model;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Filter {

    public List<PersonalCard> filter(List<PersonalCard> personalCards, Map<String, String> filters) {
        List<Predicate<PersonalCard>> predicates = new ArrayList<>();

        for (Map.Entry<String, String> entry : filters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            // Проверяем, начинается ли значение с оператора
            String operator = getOperator(value);
            value = removeOperator(value);

            // Создаем предикат для текущего фильтра
            predicates.add(createPredicate(key, value, operator));
        }

        // Объединяем предикаты с помощью логического ИЛИ
        Predicate<PersonalCard> combinedPredicate = predicates.stream().reduce(x -> false, Predicate::or);

        // Применяем фильтр
        return personalCards.stream().filter(combinedPredicate).collect(Collectors.toList());
    }

    private Predicate<PersonalCard> createPredicate(String fieldName, String value, String operator) {
        try {
            Field field = PersonalCard.class.getDeclaredField(fieldName);
            field.setAccessible(true);

            return card -> compareField(card, field, value, operator);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return x -> true;
        }
    }
    private boolean compareField(PersonalCard card, Field field, String value, String operator) {
        try {
            Object fieldValue = field.get(card);
            if(fieldValue!= null) {
                if (field.getType() == String.class) {
                    return compareString((String) fieldValue, value, operator);
                } else if (field.getType() == Integer.class) {
                    return compareInteger((Integer) fieldValue, Integer.parseInt(value), operator);
                } else if (field.getType() == Date.class) {
                    return compareDate((Date) fieldValue, value, operator);
                } else if (field.getType() == Long.class) {
                    return compareLong((Long) fieldValue, Long.parseLong(value), operator);
                } else if (field.getType() == JobTitle.class) {
                    return compareString(((JobTitle) fieldValue).getTitle(), value, operator);
                } else if (field.getType() == City.class) {
                    return compareString(((City) fieldValue).getName(), value, operator);
                } else if (field.getType() == Status.class) {
                    return compareString(((Status) fieldValue).getField(), value, operator);
                }
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean compareString(String actual, String target, String operator) {
        if (actual == null) return false;
        actual = actual.toLowerCase();
        target = target.toLowerCase();
        switch (operator) {
            case "=":
                return actual.equals(target);
            case "*":
                return actual.contains(target);
            default:
                return false;
        }
    }

    private boolean compareInteger(Integer actual, Integer target, String operator) {
        if (actual == null) return false;
        switch (operator) {
            case "=":
                return actual.equals(target);
            case "<":
                return actual < target;
            case ">":
                return actual > target;
            default:
                return false;
        }
    }

    private boolean compareDate(Date actual, String target, String operator) {
        if (actual == null) return false;
        try {
            SimpleDateFormat sdfUserInput = new SimpleDateFormat("dd.MM.yyyy");
            Date targetDate = sdfUserInput.parse(target);
            int comparison = actual.compareTo(targetDate);

            switch (operator) {
                case "=":
                    return comparison == 0;
                case "<":
                    return comparison < 0;
                case ">":
                    return comparison > 0;
                default:
                    return false;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean compareLong(Long actual, Long target, String operator) {
        if (actual == null) return false;
        switch (operator) {
            case "=":
                return actual.equals(target);
            case "<":
                return actual < target;
            case ">":
                return actual > target;
            default:
                return false;
        }
    }

    private String getOperator(String value) {
        if (value.startsWith("=")) {
            return "=";
        } else if (value.startsWith("<")) {
            return "<";
        } else if (value.startsWith(">")) {
            return ">";
        } else if (value.startsWith("*")) {
            return "*";
        } else {
            return "="; // По умолчанию
        }
    }

    private String removeOperator(String value) {
        return value.substring(1);
    }
    public class FieldMapper {
        private static final Map<String, String> FIELD_MAPPING = new HashMap<>();

        static {
            FIELD_MAPPING.put("id", "id");
            FIELD_MAPPING.put("фио", "fullName");
            FIELD_MAPPING.put("дата_рождения", "dateOfBirth");
            FIELD_MAPPING.put("возраст", "age");
            FIELD_MAPPING.put("желаемая_зарплата", "salary");
            FIELD_MAPPING.put("телефон", "phone");
            FIELD_MAPPING.put("опыт_работы", "experience");
            FIELD_MAPPING.put("образование", "education");
            FIELD_MAPPING.put("ключевые_навыки", "skills");
            FIELD_MAPPING.put("комментарии", "comments");
            FIELD_MAPPING.put("пол", "sex");
            FIELD_MAPPING.put("город", "city");
            FIELD_MAPPING.put("желаемая_должность", "jobTitle");
            FIELD_MAPPING.put("статус", "status");
            FIELD_MAPPING.put("дата_добавления_в_базу", "creationDate");
        }

        public static String mapField(String russianField) {
            return FIELD_MAPPING.get(russianField);
        }
    }
}
