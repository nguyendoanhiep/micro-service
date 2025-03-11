package com.example.identityservice.dto.response;

import com.example.identityservice.entity.Resource;
import lombok.Data;

import java.util.List;

@Data
public class ResourceResponse {
    private Long id;

    private Long parentId;

    private String name;

    private String path;

    private String method;

    List<Resource> resourceChildren;
}
