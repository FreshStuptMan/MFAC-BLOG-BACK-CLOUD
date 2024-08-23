package com.mfac.blog.pojo.vo;

import com.mfac.blog.pojo.entity.Classify;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassifyListVO extends Classify {
    private Integer blogTotal;
    private String creatorName;
}
