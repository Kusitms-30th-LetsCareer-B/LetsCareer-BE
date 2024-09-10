    package com.letscareer.career.domain.enums;

    import com.letscareer.global.exception.CustomException;
    import com.letscareer.global.exception.ExceptionContent;
    import lombok.Getter;
    import lombok.RequiredArgsConstructor;

    @Getter
    @RequiredArgsConstructor
    public enum MajorType {

        MAJOR("주전공"),
        MINOR("부전공"),
        DOUBLE_MAJOR("이중전공"),
        CONVERGED_MAJOR("복수전공");

        private final String name;

        public static MajorType of(String name) {
            for (MajorType type : MajorType.values()) {
                if (type.getName().equals(name)) {
                    return type;
                }
            }
            throw new CustomException(ExceptionContent.BAD_REQUEST_MAJOR_TYPE);
        }
    }
