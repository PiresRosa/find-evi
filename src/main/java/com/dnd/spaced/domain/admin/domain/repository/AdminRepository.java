package com.dnd.spaced.domain.admin.domain.repository;

import com.dnd.spaced.domain.admin.domain.repository.dto.request.AdminWordConditionDto;
import com.dnd.spaced.domain.word.domain.Word;

import java.util.List;

public interface AdminRepository {
    List<Word> findAllBy(AdminWordConditionDto adminWordConditionDto);

}
