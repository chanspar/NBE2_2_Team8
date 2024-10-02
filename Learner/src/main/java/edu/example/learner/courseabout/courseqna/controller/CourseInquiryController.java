package edu.example.learner.courseabout.courseqna.controller;

import edu.example.learner.courseabout.courseqna.dto.CourseInquiryDTO;
import edu.example.learner.courseabout.courseqna.entity.CourseInquiry;
import edu.example.learner.courseabout.courseqna.service.CourseInquiryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/course/{courseId}/course-inquiry")
public class CourseInquiryController {
    private final CourseInquiryService courseInquiryService;

    //강의 문의 등록
    @PostMapping
    public ResponseEntity<CourseInquiryDTO> register(@RequestBody CourseInquiryDTO courseInquiryDTO){
        return ResponseEntity.ok(courseInquiryService.register(courseInquiryDTO));
    }

    //전체 강의 문의 조회
    @GetMapping
    public ResponseEntity<List<CourseInquiryDTO>> getList(){
        List<CourseInquiry> courseInquiries = courseInquiryService.readAll();
        List<CourseInquiryDTO> courseInquiryDTOS = courseInquiries
                .stream()
                .map(CourseInquiryDTO::new)
                .toList();

        return ResponseEntity.ok(courseInquiryDTOS);
    }

    //강의 문의 조회
    @GetMapping("/{inquiryId}")
    public ResponseEntity<CourseInquiryDTO> getInquiry( @PathVariable("inquiryId") Long inquiryId ){
        return ResponseEntity.ok(courseInquiryService.read(inquiryId));
    }

    //강의 문의 수정
    @PutMapping("/{inquiryId}")
    public ResponseEntity<CourseInquiryDTO> modify( @PathVariable("inquiryId") Long inquiryId ,
                                                    @RequestBody CourseInquiryDTO courseInquiryDTO){
        courseInquiryDTO.setInquiryId(inquiryId);
        return ResponseEntity.ok(courseInquiryService.update(courseInquiryDTO));
    }

    //강의 문의 상태 수정
    @PutMapping("/{inquiryId}/status")
    public ResponseEntity<CourseInquiryDTO> modifyStatus ( @PathVariable("inquiryId") Long inquiryId ,
                                                            @RequestBody CourseInquiryDTO courseInquiryDTO){
        courseInquiryDTO.setInquiryId(inquiryId);
        return ResponseEntity.ok(courseInquiryService.updateStatus(courseInquiryDTO));
    }

    //강의 문의 삭제
    @DeleteMapping("/{inquiryId}")
    public ResponseEntity<Void> remove( @PathVariable("inquiryId") Long inquiryId ){
        courseInquiryService.delete(inquiryId);
        return ResponseEntity.ok().build();
    }
}
