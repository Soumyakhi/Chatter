package com.example.demo.serviceInterface;

import com.example.demo.dto.FetchedTextDTO;
import com.example.demo.entity.TextEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface FetchAllTextService {
    List<FetchedTextDTO> fetchTexts(HttpServletRequest request, long groupId);
}
