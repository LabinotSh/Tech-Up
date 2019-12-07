package org.auk.todo.service;

import org.auk.todo.model.Label;
import org.auk.todo.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LabelService {

    @Autowired
    private LabelRepository labelRepository;
    

    public List<Label> findBySlug(String slug){
        return labelRepository.findBySlug(slug);
    }

}
