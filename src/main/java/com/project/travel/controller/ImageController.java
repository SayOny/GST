package com.project.travel.controller;

import com.project.travel.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/s3", produces = APPLICATION_JSON_VALUE)
public class ImageController {

    private final AwsS3Service awsS3Service;
    /**
     * Amazon S3에 이미지 업로드
     * @return 성공 시 200 Success와 함께 업로드 된 파일의 파일명 리스트 반환
     */
    @PostMapping("/upload")
    public String uploadImage(@RequestPart("file") List<MultipartFile> multipartFile) {
        awsS3Service.uploadImage(multipartFile);
        return "upload success";
    }

    /**
     * Amazon S3에 이미지 업로드 된 파일을 삭제
     * @return 성공 시 200 Success
     */

    @PostMapping("/delete")
    public String deleteImage(@RequestParam("file") String fileName) {
        awsS3Service.deleteImage(fileName);
        return "delete success";
    }
}