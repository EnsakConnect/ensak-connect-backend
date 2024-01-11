package com.ensak.connect.report;

import com.ensak.connect.job_post.dto.JobPostRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReportController {
    final private ReportService reportService;

    @PostMapping("/reports")
    public ResponseEntity createReport( @RequestBody @Valid ReportRequestDTO request){
        reportService.createService(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin/reports")
    public ResponseEntity<Page<Report>> getReports(Pageable pageable){
        return   ResponseEntity.ok(reportService.getReports(pageable));
    }

    @DeleteMapping("/admin/reports/{reportId}")
    public ResponseEntity deleteReport(@PathVariable @Valid @Min(value = 1) Integer reportId){
        reportService.deleteReport(reportId);
        return ResponseEntity.noContent().build();
    }
}
