package com.letscareer.calendar.dto.response;

public record ScheduleContentResponse(Long scheduleId, String content) {
	public ScheduleContentResponse(Long scheduleId, String companyName, String stageName, String additionalInfo) {
		this(
			scheduleId,
			companyName + " " + stageName + (additionalInfo != null ? " " + additionalInfo : "")
		);
	}
}
