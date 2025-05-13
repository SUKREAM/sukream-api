package com.sukream.sukream.domains.user.domain.enums;

import lombok.Getter;
import org.hibernate.annotations.Comment;

@Comment("성별")
@Getter
public enum Gender {
    MALE("M", "남성"),
    FEMALE("F", "여성");

    private final String code;
    private final String description;

    Gender(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Gender fromCode(String code) {
        for (Gender gender : values()) {
            if (gender.code.equalsIgnoreCase(code)) {
                return gender;
            }
        }

        // TODO : Exception Handling 전역 설정 완료 시 변경
        throw new IllegalArgumentException(code);
    }
}

