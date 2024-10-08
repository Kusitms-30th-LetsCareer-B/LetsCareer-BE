package com.letscareer.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ExceptionContent {

    /**
     * 계속해서 추가
     */


    BAD_REQUEST_STAGE_STATUS_TYPE(BAD_REQUEST, "잘못된 요청입니다. 유효하지 않은 채용 상태입니다. (준비중, 합격, 불합격으로 입력해주세요.)"),
    BAD_REQUEST_INTRODUCE_STATUS_TYPE(BAD_REQUEST, "잘못된 요청입니다. 유효하지 않은 자기소개 상태입니다. (잘했어요, 아쉬워요으로 입력해주세요.)"),
    BAD_REQUEST_EXPERIENCE_TYPE(BAD_REQUEST,"잘못된 필살기 경험 타입입니다."),
    BAD_REQUEST_INTERVIEW_STATUS_TYPE(BAD_REQUEST, "잘못된 면접 상태입니다. " ),
    BAD_REQUEST_REVIEW_TYPE(BAD_REQUEST, "잘못된 리뷰 타입입니다."),
    BAD_REQUEST_SATISFACTION_TYPE(BAD_REQUEST, "잘못된 만족도 타입입니다."),
    BAD_REQUEST_REVIEW_POINT_TYPE(BAD_REQUEST, "잘못된 리뷰 포인트 타입입니다."),
    BAD_REQUEST_DIFFICULTY_TYPE(BAD_REQUEST, "잘못된 난이도 타입입니다."),
    BAD_REQUEST_DOCUMENT(BAD_REQUEST, "서류 전형은 삭제할 수 없습니다." ),
    BAD_REQUEST_ACTIVITY_TYPE(BAD_REQUEST, "잘못된 활동 유형입니다."),
    BAD_REQUEST_EDUCATION_TYPE(BAD_REQUEST, "잘못된 교육 유형입니다."),
    BAD_REQUEST_EMPLOYMENT_TYPE(BAD_REQUEST, "잘못된 고용 유형입니다."),
    BAD_REQUEST_MAJOR_TYPE(BAD_REQUEST, "잘못된 전공 유형입니다."),
    BAD_REQUEST_SCHOOL_LOCATION(BAD_REQUEST, "잘못된 학교 위치입니다."),
    BAD_REQUEST_GRADUATION_STATUS(BAD_REQUEST, "잘못된 졸업 상태입니다."),
    BAD_REQUEST_GENDER(BAD_REQUEST, "잘못된 성별입니다."),

    NOT_FOUND_USER(NOT_FOUND, "존재하지 않는 사용자입니다."),
    NOT_FOUND_RECRUITMENT(NOT_FOUND, "존재하지 않는 일정입니다." ),
    NOT_FOUND_STAGE(NOT_FOUND, "존재하는 않는 일정 전형입니다."),
    NOT_FOUND_ARCHIVING(NOT_FOUND, "존재하지 않는 아카이빙입니다."),
    NOT_FOUND_FILE(NOT_FOUND,"S3 파일을 찾을 수 없습니다."),
    NOT_FOUND_TODO(NOT_FOUND, "해당 투두를 찾을 수 없습니다."),
    NOT_FOUND_ROUTINE(NOT_FOUND, "해당 루틴을 찾을 수 없습니다."),
    NOT_FOUND_SPECIAL_SKILL(NOT_FOUND,"필살기 경험을 찾을 수 없습니다."),
    NOT_FOUND_INTRODUCE(NOT_FOUND,"해당 자기소개 질문을 찾을 수 없습니다."),
    NOT_FOUND_INTERVIEW(NOT_FOUND, "해당 복기노트 면접 질문을 찾을 수 없습니다." ),
    NOT_FOUND_SCHEDULE(NOT_FOUND, "해당 전형에 스케줄을 찾을 수 없습니다." ),


    NO_VALID_STAGE_FOUND(NOT_FOUND, "채용 일정에 채용 전형이 존재하지 않습니다." );


    private final HttpStatus httpStatus;
    private final String message;
}
