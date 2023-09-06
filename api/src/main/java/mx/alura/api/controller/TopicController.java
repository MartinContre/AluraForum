package mx.alura.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import mx.alura.api.domain.topic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @PostMapping
    public ResponseEntity<TopicResponseData> registerTopic(@RequestBody @Valid TopicRecordData topicRecordData,
                                                           UriComponentsBuilder uriComponentsBuilder
    ) {
        Topic topic = topicRepository.save(new Topic(topicRecordData));
        TopicResponseData topicResponseData = new TopicResponseData(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreationDate(),
                topic.getStatus(),
                topic.getAuthorId(),
                topic.getCourse()
        );

        URI url = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();

        return ResponseEntity.created(url).body(topicResponseData);
    }

    @GetMapping
    public ResponseEntity<Page<TopicRecordData>> getListTopics(
            @PageableDefault(size = 10, sort = "title")
            Pageable pageable
    ) {
        return ResponseEntity.ok(topicRepository.findAll(pageable).map(TopicRecordData::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponseData> getTopic(@PathVariable Long id){
        Topic topic = topicRepository.getReferenceById(id);

        TopicResponseData topicResponseData = new TopicResponseData(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreationDate(),
                topic.getStatus(),
                topic.getAuthorId(),
                topic.getCourse()
        );

        return ResponseEntity.ok(topicResponseData);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicResponseData> updatePost(@PathVariable Long id, @RequestBody @Valid TopicUpdateData topicUpdateData) {
        Topic topic = topicRepository.getReferenceById(id);
        topic.updateData(topicUpdateData);
        return ResponseEntity.ok(new TopicResponseData(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreationDate(),
                topic.getStatus(),
                topic.getAuthorId(),
                topic.getCourse()
        ));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletePost(@PathVariable Long id) {
        Topic topic = topicRepository.getReferenceById(id);
        topicRepository.delete(topic);
        return ResponseEntity.noContent().build();
    }
}
