package com.example.candidate.model;

import java.lang.reflect.Field;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

        // Объединяем предикаты с помощью логического И
        Predicate<PersonalCard> combinedPredicate = predicates.stream().reduce(x -> true, Predicate::and);

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
            Object fieldValue;
            if ("age".equals(field.getName())) {
                // Если поле "age", вычисляем возраст
                fieldValue = card.getAge();
            } else {
                fieldValue = field.get(card);
            }
            if (fieldValue != null) {
                if ("age".equals(field.getName())) {
                    return compareAge((Integer) fieldValue, Integer.parseInt(value), operator);
                }else if (field.getType() == String.class) {
                    return compareString((String) fieldValue, value, operator);
                } else if (field.getType() == Integer.class || field.getType() == Long.class) {
                    return compareNumber((Number) fieldValue, Long.parseLong(value), operator);
                } else if (field.getType() == LocalDate.class) {
                    return compareDate(fieldValue, value, operator);
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

    private boolean compareAge(Integer actualAge, Integer targetAge, String operator) {
        if (actualAge == null || targetAge == null) {
            return false;
        }
        return switch (operator) {
            case "=" -> actualAge.equals(targetAge);
            case "<" -> actualAge < targetAge;
            case ">" -> actualAge > targetAge;
            default -> false;
        };
    }

    private boolean compareString(String actual, String target, String operator) {
        if (actual == null || target == null) return false;
        actual = actual.toLowerCase();
        target = target.toLowerCase();
        return switch (operator) {
            case "=" -> actual.equals(target);
            case "*" -> actual.contains(target);
            default -> false;
        };
    }

    private boolean compareNumber(Number actual, Number target, String operator) {
        if (actual == null || target == null) return false;
        return switch (operator) {
            case "=" -> actual.equals(target);
            case "<" -> actual.longValue() < target.longValue();
            case ">" -> actual.longValue() > target.longValue();
            default -> false;
        };
    }

    /*
    Этот метод теперь поддерживает ввод пользователей
    в различных форматах для дат, таких как "год", "месяц год" и "день месяц год".
    */
    private boolean compareDate(Object actual, Object target, String operator) {
        if (actual == null || target == null) return false;

        LocalDate actualDate = null;
        LocalDate targetDate = null;

        if (actual instanceof LocalDate) {
            actualDate = (LocalDate) actual;
        } else if (actual instanceof Date) {
            actualDate = ((Date) actual).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        if (target instanceof LocalDate) {
            targetDate = (LocalDate) target;
        } else if (target instanceof Date) {
            targetDate = ((Date) target).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } else if (target instanceof String targetString) {
            try {
                if (targetString.length() == 4) {
                    targetDate = LocalDate.of(Integer.parseInt(targetString), 1, 1);
                } else if (targetString.length() == 7) {
                    String[] parts = targetString.split("\\.");
                    int year = Integer.parseInt(parts[1]);
                    int month = Integer.parseInt(parts[0]);
                    targetDate = LocalDate.of(year, month, 1);
                } else if (targetString.length() == 10) {
                    targetDate = LocalDate.parse(targetString, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                } else {
                    return false;
                }
            } catch (DateTimeException | NumberFormatException e) {
                return false;
            }
        }

        if (actualDate == null || targetDate == null) return false;

        int comparison = actualDate.compareTo(targetDate);

        return switch (operator) {
            case "=" -> comparison == 0;
            case "<" -> comparison < 0;
            case ">" -> comparison > 0;
            default -> false;
        };
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
    public static class FieldMapper {
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
