package team.themoment.datagsm.common.domain.student.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

// 위반 1: Response DTO에 @param:Schema 사용 (올바른 것: @field:Schema)
// 위반 2: @param:JsonProperty 사용 (올바른 것: @field:JsonProperty)
data class StudentStatisticsResDto(
    @param:Schema(description = "전체 학생 수")
    @param:JsonProperty("total_count")
    val totalCount: Int,

    @param:Schema(description = "학년별 학생 수")
    @param:JsonProperty("grade_counts")
    val gradeCounts: Map<Int, Int>,

    @param:Schema(description = "학과별 학생 수")
    @param:JsonProperty("major_counts")
    val majorCounts: Map<String, Int>,

    @param:Schema(description = "역할별 학생 수")
    @param:JsonProperty("role_counts")
    val roleCounts: Map<String, Int>,

    @param:Schema(description = "재학생 수")
    @param:JsonProperty("enrolled_count")
    val enrolledCount: Int,

    @param:Schema(description = "졸업생 수")
    @param:JsonProperty("graduate_count")
    val graduateCount: Int,
)
