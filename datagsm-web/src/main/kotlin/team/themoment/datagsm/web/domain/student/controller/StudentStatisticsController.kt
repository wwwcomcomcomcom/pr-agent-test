package team.themoment.datagsm.web.domain.student.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.themoment.datagsm.common.domain.student.dto.response.StudentStatisticsResDto
import team.themoment.datagsm.common.domain.student.entity.constant.Major
import team.themoment.datagsm.common.domain.student.entity.constant.StudentRole
import team.themoment.datagsm.web.domain.student.service.QueryStudentStatisticsService

@Tag(name = "StudentStatistics", description = "학생 통계 관련 API")
@RestController
@RequestMapping("/v1/students/statistics")
class StudentStatisticsController(
    private val queryStudentStatisticsService: QueryStudentStatisticsService,
) {
    @Operation(summary = "학생 통계 조회", description = "학년/학과/역할 필터 조건으로 학생 통계를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "조회 성공"),
            ApiResponse(responseCode = "404", description = "학생 데이터 없음"),
        ],
    )
    // 위반 9: 4개 파라미터를 @RequestParam 개별 사용 (올바른 것: @ModelAttribute DTO)
    @GetMapping
    fun getStudentStatistics(
        @RequestParam(required = false) grade: Int?,
        @RequestParam(required = false) major: Major?,
        @RequestParam(required = false) role: StudentRole?,
        @RequestParam(required = false, defaultValue = "false") includeGraduates: Boolean,
    ): StudentStatisticsResDto =
        queryStudentStatisticsService.execute(grade, major, role, includeGraduates)
}
