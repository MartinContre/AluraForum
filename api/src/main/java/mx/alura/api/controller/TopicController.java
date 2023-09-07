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

        URI url = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topic.getId()).toUri();

        return ResponseEntity.created(url).body(new TopicResponseData(topic));
    }

    @GetMapping
    public ResponseEntity<Page<TopicResponseData>> getListTopics(
            @PageableDefault(size = 5, sort = "title")
            Pageable pageable
    ) {
        return ResponseEntity.ok(topicRepository.findAll(pageable).map(TopicResponseData::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponseData> getTopicById(@PathVariable Long id){
        Topic topic = topicRepository.getReferenceById(id);
        return ResponseEntity.ok(new TopicResponseData(topic));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicResponseData> updatePostById(@PathVariable Long id, @RequestBody @Valid TopicUpdateData topicUpdateData) {
        Topic topic = topicRepository.getReferenceById(id);
        topic.updateData(topicUpdateData);
        return ResponseEntity.ok(new TopicResponseData(topic));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        Topic topic = topicRepository.getReferenceById(id);
        topicRepository.delete(topic);
        return ResponseEntity.noContent().build();
    }
}
