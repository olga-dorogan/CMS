package org.javatraining.model;

import java.util.List;

/**
 * Created by olga on 03.07.15.
 */
public class LessonWithDetailsVO extends LessonVO {
    private List<LessonLinkVO> links;

    public LessonWithDetailsVO() {
        super();
    }

    public List<LessonLinkVO> getLinks() {
        return links;
    }

    public void setLinks(List<LessonLinkVO> links) {
        this.links = links;
    }
}
