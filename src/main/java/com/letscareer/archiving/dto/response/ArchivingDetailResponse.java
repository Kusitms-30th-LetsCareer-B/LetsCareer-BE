package com.letscareer.archiving.dto.response;

public record ArchivingDetailResponse(String title, String content, String fileName, byte[] fileData) {
}
