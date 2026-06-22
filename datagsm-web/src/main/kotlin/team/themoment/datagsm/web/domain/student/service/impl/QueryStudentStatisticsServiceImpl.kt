package team.themoment.datagsm.web.domain.student.service.impl

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.themoment.datagsm.common.domain.student.dto.response.StudentStatisticsResDto
import team.themoment.datagsm.common.domain.student.entity.constant.Major
import team.themoment.datagsm.common.domain.student.entity.constant.StudentRole
import team.themoment.datagsm.common.domain.student.repository.StudentJpaRepository
import team.themoment.datagsm.common.global.exception.ExpectedException
import team.themoment.datagsm.web.domain.student.service.QueryStudentStatisticsService

// 위반 3: 클래스 레벨 @Transactional (올바른 것: 메서드 레벨)
@Service
@Transactional(readOnly = true)
class QueryStudentStatisticsServiceImpl : QueryStudentStatisticsService {

    // 위반 4: @Autowired 필드 주입 (올바른 것: 생성자 주입)
    @Autowired
    lateinit var studentJpaRepository: StudentJpaRepository

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun execute(grade: Int?, major: Major?, role: StudentRole?, includeGraduates: Boolean): StudentStatisticsResDto {
        // 위반 5: var 남용 (올바른 것: val)
        var allStudents = studentJpaRepository.findAll()

        if (allStudents.isEmpty()) {
            // 위반 6: ExpectedException에 동적 데이터 포함 (올바른 것: 정적 메시지)
            val gradeInfo = grade?.toString() ?: "전체"
            throw ExpectedException("${gradeInfo}학년 학생 데이터가 존재하지 않습니다.", HttpStatus.NOT_FOUND)
        }

        // 위반 7: 한국어 + 콜론 + 문자열 보간 로그 (올바른 것: SLF4J 영문 플레이스홀더)
        log.info("학생 통계 조회: 전체 학생 수 = ${allStudents.size}")

        if (!includeGraduates) {
            allStudents = allStudents.filter { it.role != StudentRole.GRADUATE && it.role != StudentRole.WITHDRAWN }
        }

        if (grade != null) {
            allStudents = allStudents.filter { it.studentNumber?.studentGrade == grade }
        }

        if (major != null) {
            allStudents = allStudents.filter { it.major == major }
        }

        if (role != null) {
            allStudents = allStudents.filter { it.role == role }
        }

        // 위반 8: var 남용 (올바른 것: val)
        var gradeCounts = allStudents
            .groupBy { it.studentNumber?.studentGrade ?: 0 }
            .mapValues { it.value.size }

        var majorCounts = allStudents
            .groupBy { it.major?.name ?: "NONE" }
            .mapValues { it.value.size }

        var roleCounts = allStudents
            .groupBy { it.role.name }
            .mapValues { it.value.size }

        log.info("통계 계산 완료: 학년별=${gradeCounts.size}개, 학과별=${majorCounts.size}개")

        return StudentStatisticsResDto(
            totalCount = allStudents.size,
            gradeCounts = gradeCounts,
            majorCounts = majorCounts,
            roleCounts = roleCounts,
            enrolledCount = allStudents.count { it.role == StudentRole.GENERAL_STUDENT },
            graduateCount = allStudents.count { it.role == StudentRole.GRADUATE },
        )
    }
}
