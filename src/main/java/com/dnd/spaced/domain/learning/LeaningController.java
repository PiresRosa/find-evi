package com.dnd.spaced.domain.learning;

import com.dnd.spaced.domain.learning.dto.request.SolveTestRequest;
import com.dnd.spaced.domain.learning.dto.response.TestExplanationResponse;
import com.dnd.spaced.domain.learning.dto.response.TestExplanationResponse.ExplanationInfoResponse;
import com.dnd.spaced.domain.learning.dto.response.TestProblemResponse;
import com.dnd.spaced.domain.learning.dto.response.TestProblemResponse.ProblemInfoResponse;
import com.dnd.spaced.domain.learning.dto.response.TestProblemResponse.ProblemInfoResponse.OptionInfoResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO : 내부 로직 구현
@RestController
@RequestMapping("/learnings")
public class LeaningController {

    private final Map<Long, String> options = new HashMap<>();

    public LeaningController() {
        options.put(1L, "완수해야 하는 실무 내용을 사전에 정리해 둔 항목들");
        options.put(2L, "회의에 필요한 사무용품 리스트");
        options.put(3L, "문제점, 논점, 중요 포인트 등 비지니스 상의 과제");
        options.put(4L, "과거 사례 중 가장 효과적이었던 방안");
        options.put(5L, "스크럼 방법론에서 일정 기간 동안 수행되는 작업 단위");
        options.put(6L, "향후 처리할 작업 목록");
        options.put(7L, "실제 데이터를 대신하여 개발이나 테스트에 사용하는 가상의 데이터");
        options.put(8L, "완수해야 하는 실무 내용을 사전에 정리해 둔 항목들");
        options.put(9L, "스크럼 방법론에서 일정 기간 동안 수행되는 작업 단위");
        options.put(10L, "애자일 방법론 중 하나로, 일정 기간 동안 작은 단위의 작업을 반복하여 진행");
        options.put(11L, "스프린트가 끝난 후 팀이 완료된 작업을 검토하고 피드백을 주고받는 회의");
        options.put(12L, "비교 평가를 위한 기준점 또는 표준");
        options.put(13L, "사용자의 행동을 유도, 관찰된 동작에 대한 간접적인 힌트 제공으로 사용자 경험을 개선하는 개념");
        options.put(14L, "데이터나 정보를 그래프 등의 시각적 형태로 표현");
        options.put(15L, "비교 평가를 위한 기준점 또는 표준");
        options.put(16L, "실제 데이터를 대신하여 개발이나 테스트에 사용하는 가상의 데이터");
        options.put(17L, "회의에 필요한 사무용품 리스트");
        options.put(18L, "데이터나 정보를 그래프 등의 시각적 형태로 표현");
        options.put(19L, "스크럼 방법론에서 일정 기간 동안 수행되는 작업 단위");
        options.put(20L, "문제점, 논점, 중요 포인트 등 비지니스 상의 과제");
    }

    @GetMapping("/mock/tests/{id}")
    public ResponseEntity<TestProblemResponse> findTestBy(@PathVariable Long id) {
        OptionInfoResponse option1 = new OptionInfoResponse(1L, options.get(1L));
        OptionInfoResponse option2 = new OptionInfoResponse(2L, options.get(2L));
        OptionInfoResponse option3 = new OptionInfoResponse(3L, options.get(3L));
        OptionInfoResponse option4 = new OptionInfoResponse(4L, options.get(4L));
        ProblemInfoResponse problem1 = new ProblemInfoResponse(1L, "아젠다(Agenda) 정리한 것 오늘까지 보내주세요.", "아젠다(Agenda)", "비즈니스", 70.1d, 29.9d, List.of(option1, option2, option3, option4));

        OptionInfoResponse option5 = new OptionInfoResponse(5L, options.get(5L));
        OptionInfoResponse option6 = new OptionInfoResponse(6L, options.get(6L));
        OptionInfoResponse option7 = new OptionInfoResponse(7L, options.get(7L));
        OptionInfoResponse option8 = new OptionInfoResponse(8L, options.get(8L));
        ProblemInfoResponse problem2 = new ProblemInfoResponse(7L, "퀴즈를 위해 목 데이터(Mock Data)를 생성했습니다.", "목 데이터(Mock Data)", "개발", 29.9d, 70.1d, List.of(option5, option6, option7, option8));

        OptionInfoResponse option9 = new OptionInfoResponse(9L, options.get(9L));
        OptionInfoResponse option10 = new OptionInfoResponse(10L, options.get(10L));
        OptionInfoResponse option11 = new OptionInfoResponse(11L, options.get(11L));
        OptionInfoResponse option12 = new OptionInfoResponse(12L, options.get(12L));
        ProblemInfoResponse problem3 = new ProblemInfoResponse(10L, "이번 스프린트의 스크럼(Scrum) 회의를 내일 오전에 진행하도록 하겠습니다.", "스크럼(Scrum)", "비즈니스", 23.2d, 76.8d, List.of(option9, option10, option11, option12));

        OptionInfoResponse option13 = new OptionInfoResponse(13L, options.get(13L));
        OptionInfoResponse option14 = new OptionInfoResponse(14L, options.get(14L));
        OptionInfoResponse option15 = new OptionInfoResponse(15L, options.get(15L));
        OptionInfoResponse option16 = new OptionInfoResponse(16L, options.get(16L));
        ProblemInfoResponse problem4 = new ProblemInfoResponse(13L, "넛지(Nudge)를 활용해 사용자가 스크롤 다운할 수 있도록 유도해주세요.", "넛지(Nudge)", "디자인", 76.8d, 23.2d, List.of(option13, option14, option15, option16));

        OptionInfoResponse option17 = new OptionInfoResponse(17L, options.get(17L));
        OptionInfoResponse option18 = new OptionInfoResponse(18L, options.get(18L));
        OptionInfoResponse option19 = new OptionInfoResponse(19L, options.get(19L));
        OptionInfoResponse option20 = new OptionInfoResponse(20L, options.get(20L));
        ProblemInfoResponse problem5 = new ProblemInfoResponse(18L, "사용자가 정보를 쉽게 이해하고 해석할 수 있도록 가시화(Visualization) 기법을 사용해주세요.", "가시화(Visualization)", "디자인", 58.2d, 41.8d, List.of(option17, option18, option19, option20));

        TestProblemResponse testProblemResponse = new TestProblemResponse(id, List.of(problem1, problem2, problem3, problem4, problem5));

        return ResponseEntity.ok(testProblemResponse);
    }

    @PostMapping("/mock/tests/{id}")
    public ResponseEntity<TestExplanationResponse> solveTest(@PathVariable Long id, @RequestBody SolveTestRequest request) {
        Long[] answers = {1L, 7L, 10L, 13L, 18L};
        List<Long> requestAnswer = request.answer();

        ExplanationInfoResponse explanation1 = new ExplanationInfoResponse(answers[0], requestAnswer.get(0).equals(answers[0]), requestAnswer.get(0).equals(answers[0]), "아젠다(Agenda)", options.get(requestAnswer.get(0)), options.get(answers[0]));
        ExplanationInfoResponse explanation2 = new ExplanationInfoResponse(answers[1], requestAnswer.get(1).equals(answers[1]), requestAnswer.get(1).equals(answers[1]), "목 데이터(Mock Data)", options.get(requestAnswer.get(1)), options.get(answers[1]));
        ExplanationInfoResponse explanation3 = new ExplanationInfoResponse(answers[2], requestAnswer.get(2).equals(answers[2]), requestAnswer.get(2).equals(answers[2]), "스크럼(Scrum)", options.get(requestAnswer.get(2)), options.get(answers[2]));
        ExplanationInfoResponse explanation4 = new ExplanationInfoResponse(answers[3], requestAnswer.get(3).equals(answers[3]), requestAnswer.get(3).equals(answers[3]),"넛지(Nudge)", options.get(requestAnswer.get(3)), options.get(answers[3]));
        ExplanationInfoResponse explanation5 = new ExplanationInfoResponse(answers[4], requestAnswer.get(4).equals(answers[4]), requestAnswer.get(4).equals(answers[4]),"가시화(Visualization)", options.get(requestAnswer.get(4)), options.get(answers[4]));

        long correctCount = IntStream.range(0, Math.min(requestAnswer.size(), answers.length))
                                     .filter(i -> answers[i].equals(requestAnswer.get(i)))
                                     .count();

        TestExplanationResponse testExplanationResponse = new TestExplanationResponse(id, correctCount, List.of(explanation1, explanation2, explanation3, explanation4, explanation5));

        return ResponseEntity.ok(testExplanationResponse);
    }
}
