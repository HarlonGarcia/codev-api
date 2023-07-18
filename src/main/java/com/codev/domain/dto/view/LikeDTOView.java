package com.codev.domain.dto.view;

import lombok.Data;

@Data
public class LikeDTOView {

    private boolean liked;

    public LikeDTOView(boolean liked){
        this.liked = liked;
    }

    public LikeDTOView(){}
}