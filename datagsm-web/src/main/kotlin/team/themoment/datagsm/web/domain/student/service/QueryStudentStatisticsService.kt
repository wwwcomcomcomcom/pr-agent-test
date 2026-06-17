package team.themoment.datagsm.web.domain.student.service

import team.themoment.datagsm.common.domain.student.dto.response.StudentStatisticsResDto
import team.themoment.datagsm.common.domain.student.entity.constant.Major
import team.themoment.datagsm.common.domain.student.entity.constant.StudentRole

interface QueryStudentStatisticsService {
    fun execute(grade: Int?, major: Major?, role: StudentRole?, includeGraduates: Boolean): StudentStatisticsResDto
}
